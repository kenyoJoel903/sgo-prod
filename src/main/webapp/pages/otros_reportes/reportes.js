$(document).ready(function(){
  $.fn.select2.defaults.set( "theme", "bootstrap" );
  var moduloActual = new moduloGestorReporte(); 
  moduloActual.urlBase='reporte';
  moduloActual.URL_EXPORTAR="./otros-reportes/reportes";
  moduloActual.URL_LIQ_CISTERNA = moduloActual.urlBase + '/liquidacion-cisterna';
  moduloActual.URL_CONC_VOL = moduloActual.urlBase + '/conciliacion-volumetrica';
  moduloActual.URL_CONC_VOL_ESTACION = moduloActual.urlBase + '/conciliacion-volumetrica-estacion';
  
  //Agregado por 9000002570====================================================================
  moduloActual.URL_TIEMPO_ETAPA = moduloActual.urlBase + '/tiempo_etapa-reporte';
  moduloActual.URL_ATENCION_PEDIDO = moduloActual.urlBase + '/atencion_pedido-reporte';
  //==========================================================================
  
  moduloActual.URL_VALIDA_PERMISO_REPORTE = moduloActual.urlBase + '/valida-permiso-reporte';
  //  moduloActual.URL_REPORTE4 = moduloActual.urlBase + '/reporte4'; 
  moduloActual.URL_OYP= moduloActual.urlBase + '/fecha-registro-operacion';//9000002443

  
  moduloActual.llamadaAjax=function(d){
		var referenciaModulo =this;
		d.filtroOperacion= referenciaModulo.obj.filtroOperacion.val();
		d.filtroFormato = referenciaModulo.obj.filtroFormato.val();
	    var rangoFecha = referenciaModulo.obj.filtroFechaPlanificada.val().split("-");
	    var fechaInicio = utilitario.formatearFecha2Iso(rangoFecha[0]) ;
	    var fechaFinal = utilitario.formatearFecha2Iso(rangoFecha[1]);
	    d.filtroFechaInicio= fechaInicio;	
	    d.filtroFechaFinal = fechaFinal;	 
	};

  moduloActual.inicializarCampos= function(){
	  var referenciaModulo=this;
    //Campos de formulario
	var sampleArray = [ { id: "1",text: 	"Liquidacion de Cisternas"},
             			{ id: "2",text: 	"Conciliacion Volumétrica"},
             			{ id: "3",text: 	"Conciliacion Volumétrica Estacion"}
             			,{id: "4",text: 	"Fecha Registro de Operaci\u00f3n"}//9000002443
             			,{ id: "5",text: 	"Reporte de Tiempos por Etapa"},
             			{ id: "6",text: 	"Reporte de Atención de Pedidos"}
             			] ;
    this.obj.cmpReporte=$("#cmpReporte");
    this.obj.cmpReporte.tipoControl="select2";
    this.obj.cmpSelect2Reporte=$("#cmpReporte").select2({ 
  		    data:  sampleArray 
    });
    moduloActual.nombre_reporte=$("#nombre_reporte");    
    $("#filtroOperacion").select2();
    if($("#cmpReporte").val()==0){
    	this.obj.ocultaContenedorTabla.hide();
	    this.obj.ocultaContenedorTabla01.hide();
	    referenciaModulo.obj.cntReporte01.hide();
	    this.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Debe seleccionar un reporte.");	    
    }

  this.obj.filtroFechaPlanificada = $("#filtroFechaPlanificada");
  var fechaActual = this.obj.filtroFechaPlanificada.attr('data-fecha-actual');
  var rangoSemana = utilitario.retornarRangoSemana(fechaActual);
  this.obj.filtroFechaPlanificada.val(utilitario.formatearFecha2Cadena(rangoSemana.fechaInicial) + " - " +utilitario.formatearFecha2Cadena(rangoSemana.fechaFinal));
  this.obj.filtroFechaPlanificada.daterangepicker({
      singleDatePicker: false,        
      showDropdowns: false,
      locale: { 
        "format": 'DD/MM/YYYY',
        "applyLabel": "Aceptar",
        "cancelLabel": "Cancelar",
        "fromLabel": "Desde",
        "toLabel": "Hasta",
        "customRangeLabel": "Seleccionar",
        "daysOfWeek": [
        "Dom",
        "Lun",
        "Mar",
        "Mie",
        "Jue",
        "Vie",
        "Sab"
        ],
        "monthNames": [
          "Enero",
          "Febrero",
          "Marzo",
          "Abril",
          "Mayo",
          "Junio",
          "Julio",
          "Agosto",
          "Septiembre",
          "Octubre",
          "Noviembre",
          "Diciembre"
        ]
      }
  }); 
  
  this.obj.cmpReporte.on('change', function(e){
  	try {
	  		//habilitar DIV segun id reporte
  		
	  		if($("#cmpReporte").val()==0){
	  			moduloActual.obj.cntReporte01.hide();
	        	moduloActual.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR, "Debe seleccionar un reporte");
	        	moduloActual.nombre_reporte="";	        	
	        } else{
	        	if($("#cmpReporte")!=null && $("#cmpReporte").val()==1){
		        	moduloActual.nombre_reporte=moduloActual.URL_LIQ_CISTERNA;
		        } else if($("#cmpReporte")!=null && $("#cmpReporte").val()==2){
		        	moduloActual.nombre_reporte=moduloActual.URL_CONC_VOL;	        	
		        } else if($("#cmpReporte")!=null && $("#cmpReporte").val()==3){
		        	moduloActual.nombre_reporte=moduloActual.URL_CONC_VOL_ESTACION;	        	
		        } else if($("#cmpReporte")!=null && $("#cmpReporte").val()==4){
		        	moduloActual.nombre_reporte=moduloActual.URL_OYP;	        	
		        } else if($("#cmpReporte")!=null && $("#cmpReporte").val()==5){
		        	moduloActual.nombre_reporte=moduloActual.URL_TIEMPO_ETAPA;	
		        	console.log("moduloActual.nombre_reporte: " + moduloActual.nombre_reporte);
		        } else if($("#cmpReporte")!=null && $("#cmpReporte").val()==6){
		        	moduloActual.nombre_reporte=moduloActual.URL_ATENCION_PEDIDO;	  
		        	console.log("moduloActual.nombre_reporte: " + moduloActual.nombre_reporte);
		        }	        	
	        	moduloActual.validaPermiso(); 	    	        	
	        }  
		} catch (e) {
			 console.log('e:'+e.message);
		}
  }); 
  
	  this.obj.btnExportar=$("#btnExportar");
	  this.obj.btnExportar.on("click", function(e){
	  	e.preventDefault();
	  	var fechaInicial = "2015-01-01";
	  	var fechaFinal="2015-02-01";
	  	var idOperacion = referenciaModulo.obj.filtroOperacion.val();
	  	var txtOperacion=referenciaModulo.obj.filtroOperacion.find("option:selected").attr('data-razon-social');
	  	var formato = $( "input:radio[name=filtroFormato]:checked" ).val();
	  	var rangoFecha = referenciaModulo.obj.filtroFechaPlanificada.val().split("-");
	    fechaInicial = utilitario.formatearFecha2Iso(rangoFecha[0]) ;
	    fechaFinal = utilitario.formatearFecha2Iso(rangoFecha[1]);	 
	  	var url=moduloActual.nombre_reporte+'?formato='+formato;
	  	url=url+"&fechaInicial="+fechaInicial + "&fechaFinal=" +fechaFinal + "&idOperacion="+idOperacion+"&txtOperacion="+txtOperacion;
	  	window.open(url);	  		
	  });
	  
  };
  moduloActual.OcultarMostrarControles=function(){
  //9000002443
	  if($("#cmpReporte").val()==4 ){
		  $("#filtroOperacion").prepend('<option id="opcion_especial" value="-1">[Todos]</option>');
		  $("#parametro_DO").css("display","none");  
		  $("#opcion_especial").css("display","none");
		  $("#filtroOperacion").val("-1").trigger('change');
	  }else{
		  $("#parametro_DO").css("display","");
		  $("#opcion_especial").css("display","");
		  $("#filtroOperacion option[value='-1']").remove();		  
		  $("#filtroOperacion").val($("#filtroOperacion option:first").val()).trigger('change');
	  }
	  
  };
  moduloActual.validaPermiso= function(){
	  moduloActual.obj.ocultaContenedorTabla01.show();
  	  try{
		  $.ajax({
		    type: constantes.PETICION_TIPO_GET,
		    url: moduloActual.URL_VALIDA_PERMISO_REPORTE, 
		    contentType: moduloActual.TIPO_CONTENIDO, 
		    data: {
		    		url_reporte:moduloActual.nombre_reporte
		    	},	
		    success: function(respuesta) {
		      if (!respuesta.estado) {
		    	  moduloActual.obj.cntReporte01.hide();
		    	  moduloActual.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);		    	  
		      } else {
		          moduloActual.obj.cntReporte01.show();
			  	  moduloActual.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.INICIAR_OPERACION); 
		      }		      
		      moduloActual.obj.ocultaContenedorTabla01.hide();
		      moduloActual.OcultarMostrarControles();
		    },			    		    
		    error: function() {
		    	moduloActual.mostrarErrorServidor(xhr,estado,error); 
		    }
		    });
		  } catch(error){
			  moduloActual.mostrarDepuracion(error.message);
		  }
  };
  moduloActual.inicializar();
});