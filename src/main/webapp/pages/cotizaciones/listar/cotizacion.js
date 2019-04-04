$(document).ready(function(){
  var _cotizacionSesion = null;
  var moduloActual = new moduloCotizacion();  
  moduloActual.urlBase='cotizacion';
//  moduloActual.NUMERO_REGISTROS_PAGINA=15;
  moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
  moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
//  moduloActual.URL_ELIMINAR = moduloActual.urlBase + '/eliminar';
  moduloActual.URL_GENERAR = moduloActual.urlBase + '/generar';

  moduloActual.URL_LISTAR_DETALLE = moduloActual.urlBase + '/listarDetalle';
  //combos
  moduloActual.URL_LISTAR_CLIENTES = moduloActual.urlBase + '/listarClientes';
  moduloActual.URL_LISTAR_CANALSECTOR = moduloActual.urlBase + '/listarCanalSector';
  moduloActual.URL_LISTAR_DESTINATARIO = moduloActual.urlBase + '/listarInterlocutor';
  moduloActual.URL_LISTAR_PRODUCTO = moduloActual.urlBase + '/listarProductoHab';
  moduloActual.URL_LISTAR_PLANTA =  moduloActual.urlBase + "/listarPlantaHab";
  
  moduloActual.URL_REPORTE = moduloActual.urlBase + "/crear-reporte";
  
  moduloActual.VALOR_MAXIMO = 999999999.999;
  
  moduloActual.ordenGrilla=[[ 3, 'asc' ]];
  //Agregar columnas a la grilla Principal
  moduloActual.columnasGrilla.push({ "data": 'idProforma'}); 
  moduloActual.columnasGrilla.push({ "data": 'nroCotizacion'});
  moduloActual.columnasGrilla.push({ "data": 'fechaCotizacion'});
  moduloActual.columnasGrilla.push({ "data": 'cliente.razonSocial'});  
  moduloActual.columnasGrilla.push({ "data": 'interlocutor.nomInterlocutorSap'});
  moduloActual.columnasGrilla.push({ "data": 'moneda'});
  moduloActual.columnasGrilla.push({ "data": 'montoVista'});

  moduloActual.definicionColumnas.push({"targets": 1, "searchable": false, "orderable": false, "visible":false});
  moduloActual.definicionColumnas.push({"targets": 2, "searchable": false, "orderable": false, "visible":true, width:100 });
  moduloActual.definicionColumnas.push({"targets": 3, "searchable": false, "orderable": false, "visible":true, width:100});
  moduloActual.definicionColumnas.push({"targets": 4, "searchable": false, "orderable": false, "visible":true });
  moduloActual.definicionColumnas.push({"targets": 5, "searchable": false, "orderable": false, "visible":true});
  moduloActual.definicionColumnas.push({"targets": 6, "searchable": false, "orderable": false, "visible":true, width:100});
  moduloActual.definicionColumnas.push({"targets": 7, "searchable": false, "orderable": false, "visible":true, width:100});

  moduloActual.reglasValidacionFormulario={
		  cmpCodCliente: { required: true},
		  cmpCanalSector: { required: true },
		  cmpDestinatario: { required: true },
		  cmpMoneda: { required: true }
  };

  
  moduloActual.mensajesValidacionFormulario={
	cmpCodCliente: {
		required: "El campo 'Cliente' es obligatorio",
	},
	cmpCanalSector: {
		required: "El campo 'Canal / Sector' es obligatorio",
	},
	cmpDestinatario: {
		required: "El campo 'Destinatario' es obligatorio",
	},
	cmpMoneda: {
		required: "El campo 'Moneda' es obligatorio",
	}
  };
  
  /**********************//*phf*/
  
  //Agregar columnas a la grilla DETALLE
  moduloActual.columnasGrillaDetalle.push({ "data": 'idDetalleProforma'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'planta.descripcion'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'producto.nombre'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'volumenVista'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'precioVista'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'descuentoVista'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'precioNetoVista'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'rodajeVista'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'iscVista'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'otrosVista'}); // ticket 9000003025
  moduloActual.columnasGrillaDetalle.push({ "data": 'acumuladoVista'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'igvVista'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'fiseVista'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'precioDescuentoVista'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'precioPercepcionVista'});
  moduloActual.columnasGrillaDetalle.push({ "data": 'importeTotalVista'});

  moduloActual.definicionColumnasDetalle.push({"targets": 0, "searchable": false, "orderable": false, "visible":false });
  moduloActual.definicionColumnasDetalle.push({"targets": 1, "searchable": false, "orderable": false, "visible":false });
  moduloActual.definicionColumnasDetalle.push({"targets": 2, "searchable": false, "orderable": false, "visible":true });
  moduloActual.definicionColumnasDetalle.push({"targets": 3, "searchable": false, "orderable": false, "visible":true });
  moduloActual.definicionColumnasDetalle.push({"targets": 4, "searchable": false, "orderable": false, "visible":true, "class": "text-right"});
  moduloActual.definicionColumnasDetalle.push({"targets": 5, "searchable": false, "orderable": false, "visible":true, "class": "text-right" });
  moduloActual.definicionColumnasDetalle.push({"targets": 6, "searchable": false, "orderable": false, "visible":true, "class": "text-right"});
  moduloActual.definicionColumnasDetalle.push({"targets": 7, "searchable": false, "orderable": false, "visible":true, "class": "text-right"});
  moduloActual.definicionColumnasDetalle.push({"targets": 8, "searchable": false, "orderable": false, "visible":true, });
  moduloActual.definicionColumnasDetalle.push({"targets": 9, "searchable": false, "orderable": false, "visible":true, "class": "text-right"});
  moduloActual.definicionColumnasDetalle.push({"targets": 10, "searchable": false, "orderable": false, "visible":true, "class": "text-right"});
  moduloActual.definicionColumnasDetalle.push({"targets": 11, "searchable": false, "orderable": false, "visible":true, "class": "text-right"});
  moduloActual.definicionColumnasDetalle.push({"targets": 12, "searchable": false, "orderable": false, "visible":true, "class": "text-right"});
  moduloActual.definicionColumnasDetalle.push({"targets": 13, "searchable": false, "orderable": false, "visible":true, "class": "text-right"});
  moduloActual.definicionColumnasDetalle.push({"targets": 14, "searchable": false, "orderable": false, "visible":true, "class": "text-right"});
  moduloActual.definicionColumnasDetalle.push({"targets": 15, "searchable": false, "orderable": false, "visible":true, "class": "text-right"});
  moduloActual.definicionColumnasDetalle.push({"targets": 16, "searchable": false, "orderable": false, "visible":true, "class": "text-right"});
  
  
  
  moduloActual.inicializarCampos= function(){
    //Campos de formulario busqueda
	this.obj.cmpFiltroCliente = $("#cmpFiltroCliente");
	this.obj.cmpFecha=$("#cmpFecha");
    
    this.obj.cmpCodCliente=$("#cmpCodCliente");
    this.obj.cmpCodCliente.tipoControl = constantes.TIPO_CONTROL_SELECT2;
    this.obj.cmpCodClienteSapHid=$("#cmpCodClienteSapHid");
    this.obj.cmpClaveRamoSapHid=$("#cmpClaveRamoSapHid");
    
    this.obj.cmpCanalSector=$("#cmpCanalSector");
    this.obj.cmpCanalSector.tipoControl = constantes.TIPO_CONTROL_SELECT2;
    
    this.obj.cmpCodSectorHid=$("#cmpCodSectorHid");
    this.obj.cmpCodCanalHid=$("#cmpCodCanalHid");
    this.obj.cmpDestinatario=$("#cmpDestinatario");
    this.obj.cmpDestinatario.tipoControl = constantes.TIPO_CONTROL_SELECT2;
    this.obj.cmpCodInterlocutorSapHid=$("#cmpCodInterlocutorSapHid");
    this.obj.cmpMoneda=$("#cmpMoneda");
    this.obj.cmpMoneda.tipoControl = constantes.TIPO_CONTROL_SELECT2;
    this.obj.cmpDesContrato=$("#cmpDesContrato");
//    this.obj.cmpNumCotizacion=$("#cmpNumCotizacion");
    
    //Campos de vista
    this.obj.vistaId=$("#vistaId");
    this.obj.vistaRazonSocial=$("#vistaRazonSocial");
    this.obj.vistaFechaCotizacion=$("#vistaFechaCotizacion");
    this.obj.vistaCanalSector=$("#vistaCanalSector");
    this.obj.vistaDestinatario=$("#vistaDestinatario");
    this.obj.vistaMoneda=$("#vistaMoneda");
    this.obj.vistaNroCotizacion=$("#vistaNroCotizacion");
    this.obj.vistaCreadoEl=$("#vistaCreadoEl");
    this.obj.vistaCreadoPor=$("#vistaCreadoPor");
    this.obj.vistaActualizadoEl=$("#vistaActualizadoEl");
    this.obj.vistaActualizadoPor=$("#vistaActualizadoPor");
    this.obj.vistaIPCreacion=$("#vistaIPCreacion");
    this.obj.vistaIPActualizacion=$("#vistaIPActualizacion");
    
    // 
    this.obj.btnAgregarFila=$("#btnAgregarFila");
    this.obj.btnConfirmarModificar=$("#btnConfirmarModificar");
    this.obj.btnConfirmarSincronizar=$("#btnConfirmarSincronizar");
    
    this.obj.modalConfirmarModificarSincronizado=$("#modalConfirmarModificarSincronizado");
    
    this.obj.cmpSelect2CodCanalSector=crearCombo2("#cmpCanalSector",moduloActual.URL_LISTAR_CANALSECTOR);
    this.obj.cmpSelect2CodMOneda = $("#cmpMoneda").select2();
    this.obj.cmpSelect2CodDestinatario=crearCombo2("#cmpDestinatario",moduloActual.URL_LISTAR_DESTINATARIO);

    this.obj.cmpSelect2FilCliente=crearCombo2("#cmpFiltroCliente",moduloActual.URL_LISTAR_CLIENTES);
    this.obj.cmpSelect2CodCliente=crearCombo2("#cmpCodCliente",moduloActual.URL_LISTAR_CLIENTES);
    
    //fecha
    var miLocale = { 
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
      };
    this.obj.txtFiltroFecha = $("#txtFiltroFecha");
    this.obj.txtFiltroFecha.daterangepicker({
        singleDatePicker: false,        
        showDropdowns: false,
        locale: miLocale
    });
//    this.obj.cmpFecha.daterangepicker({
//        singleDatePicker: true,        
//        locale:miLocale
//    });
  };

	function crearCombo2(idCombo, unaUrl) {
		return $(idCombo).select2({
		 	language: "es",
			minimumResultsForSearch: Infinity,
	  	  	ajax: {
				url: unaUrl,
				dataType: 'json',
				delay: 250,
				cache: true,
				data: function (parametros) {
					debugger;
					return {
						valorBuscado: parametros.term, // search term
						page: parametros.page,
						paginacion: 0,
						id: moduloActual.obj.cmpCodCliente.val(),
						codCanal: moduloActual.obj.cmpCodCanalHid.val(),
						codSector: moduloActual.obj.cmpCodSectorHid.val()
					};
				},
				processResults: function (respuesta, pagina) {
					debugger;
					var resultados = respuesta.contenido.carga;
					return {
						results: resultados
					};
				},
	  	  	},
			escapeMarkup: function (markup) {
				return markup;
			},
	  		templateResult: function (registro) {
	  			debugger;
	  			
	  			if (registro.loading) {
	  				return registro.text;
	  			}
	  			
		        if (unaUrl == moduloActual.URL_LISTAR_CLIENTES) {
		        	return "<div class='select2-user-result'>" + registro.razonSocial + "</div>";
		        } else if(unaUrl == moduloActual.URL_LISTAR_CANALSECTOR) {
		        	return "<div class='select2-user-result'>" + registro.descripcionCanalSector + "</div>";
		        } else if(unaUrl == moduloActual.URL_LISTAR_DESTINATARIO) {
		        	return "<div class='select2-user-result'>" + registro.codInterlocutorSap + "-" + registro.nomInterlocutorSap + "</div>";
		        }
	  		},
			templateSelection: function (registro) {
				debugger;
				
				if (registro.loading) {
					return registro.text;
				}
				
				if (unaUrl == moduloActual.URL_LISTAR_CLIENTES) {
					
					moduloActual.obj.cmpCodClienteSapHid.val(registro.codigoSap);
					moduloActual.obj.cmpClaveRamoSapHid.val(registro.ramaSAP);
					moduloActual.limpiarCombosHijosDetalle(3);
					return registro.razonSocial || registro.text;
					
				} else if (unaUrl == moduloActual.URL_LISTAR_CANALSECTOR) {
					
					moduloActual.obj.cmpCodCanalHid.val(registro.canalDistribucionSap);
					moduloActual.obj.cmpCodSectorHid.val(registro.sectorSap);
					moduloActual.limpiarCombosHijosDetalle(2);
					return registro.descripcionCanalSector || registro.text;
					
				} else if (unaUrl == moduloActual.URL_LISTAR_DESTINATARIO) {
					
					moduloActual.obj.cmpCodInterlocutorSapHid.val(registro.codInterlocutorSap);
					moduloActual.limpiarCombosHijosDetalle(1);
					return registro.codInterlocutorSap+"-"+registro.nomInterlocutorSap || registro.text;
				
				} else {
					return registro.text;
				}
			},
	  		
	    });
	}
  
  moduloActual.limpiarCombosHijosDetalle = function(nivel){
	  if(nivel >= 3 && moduloActual.obj.cmpSelect2CodCanalSector != undefined){
		$('#cmpCanalSector option').remove();
		moduloActual.obj.cmpSelect2CodCanalSector.trigger('change');
	  }
	  if(nivel >= 2 && moduloActual.obj.cmpSelect2CodDestinatario != undefined){
		$('#cmpDestinatario option').remove();
		moduloActual.obj.cmpSelect2CodDestinatario.trigger('change');
	  }
	  if(nivel >= 1){
		  moduloActual.limpiarTablaDetalle();
	  } 
  };
 moduloActual.limpiarTablaDetalle = function() {
	//eliminar filas
	var numeroFormularios = moduloActual.obj.grupoDetallePro.getFormsCount();
	if( 0 < numeroFormularios){
 		moduloActual.obj.grupoDetallePro.removeAllForms();
	}
};

  
  moduloActual.resetearFormulario= function(){
	  var referenciaModulo= this;
	  referenciaModulo.obj.frmPrincipal[0].reset();
	  console.log("Verificador");
	  referenciaModulo.obj.verificadorFormulario.resetForm();
	  jQuery.each( this.obj, function( i, val ) {
	    if (typeof referenciaModulo.obj[i].tipoControl != constantes.CONTROL_NO_DEFINIDO ){
	      if (referenciaModulo.obj[i].tipoControl == constantes.TIPO_CONTROL_SELECT2){
	    	  console.log("data-valor-inicial:"+referenciaModulo.obj[i].attr("data-valor-inicial"));
	        debugger;
	    	  var id=referenciaModulo.obj[i].attr("id");
	    	  var opcion1ra = $("#"+id+" option")[0];
	    	  console.log("opcion1ra:"+opcion1ra);
	    	  if(opcion1ra != undefined){
			      referenciaModulo.obj[i].select2("val", opcion1ra.value);
		      }
	      }
	    }
	  });
  };
  
  moduloActual.inicializarFormularioPrincipal= function(){
  var referenciaModulo=this;
    referenciaModulo.obj.verificadorFormulario = referenciaModulo.obj.frmPrincipal.validate({
      rules: referenciaModulo.reglasValidacionFormulario,
      messages: referenciaModulo.mensajesValidacionFormulario,
    highlight: function(element, errorClass, validClass) {
      //$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);
      $("#cnt" + $(element).attr("id")).removeClass(validClass).addClass(errorClass);
    },
    unhighlight: function(element, errorClass, validClass) {
      $("#cnt" + $(element).attr("id")).removeClass(errorClass).addClass(validClass);
      //$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);
    },
    errorPlacement: function(error, element) {
      console.log("errorPlacement");
      console.log(error);
      //referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,mensaje);      
    },
    errorClass: "has-error",
    validClass: "has-success",
    showErrors: function(errorMap, errorList) {
      // if (($.isEmptyObject(this.errorMap))) {
        // console.log("checkForm");
        // this.checkForm();
      // }
      console.log("Custom showErrors");
      console.log("checkForm");
      this.checkForm();
      console.log("this.errorMap");
      console.log(this.errorMap);
      console.log("this.errorList");
      console.log(this.errorList);
      this.defaultShowErrors();
      console.log("this.errorList.length");
      console.log(this.errorList.length);
      var numeroErrores = this.errorList.length;
      if (numeroErrores > 0) {
        var mensaje = numeroErrores == 1 ? 'Existe un campo con error.' : 'Existen ' + numeroErrores + ' campos con errores';
        for (var indice in this.errorMap){
          console.log(indice);
          mensaje+=". " + this.errorMap[indice];    
        }        
        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,mensaje);
      } else {
        mensaje="Todos los campos son validos";
        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,mensaje);
      }
    }
  });
    
};

  moduloActual.llenarDetalles = function(registro){
    this.idRegistro= registro.idProforma;
    this.obj.vistaId.text(registro.idProforma);
    this.obj.vistaRazonSocial.text(registro.cliente.razonSocial);
    if((""+registro.fechaCotizacion).indexOf('-') >= 0){
    	this.obj.vistaFechaCotizacion.text(utilitario.formatearFecha(registro.fechaCotizacion));
    
	  }else {
		  this.obj.vistaFechaCotizacion.text(utilitario.formatearTimestampToString(registro.fechaCotizacion));
	  }
    this.obj.vistaCanalSector.text(registro.canalSector.descripcionCanalSector);
    this.obj.vistaDestinatario.text(registro.interlocutor.codInterlocutorSap+"-"+registro.interlocutor.nomInterlocutorSap);
    this.obj.vistaMoneda.text(registro.moneda);
    this.obj.vistaNroCotizacion.text(registro.nroCotizacion);
    this.obj.vistaCreadoEl.text(registro.fechaCreacion);
    this.obj.vistaCreadoPor.text(registro.usuarioCreacion);
    this.obj.vistaActualizadoEl.text(registro.fechaActualizacion);
    this.obj.vistaActualizadoPor.text(registro.usuarioActualizacion);
    this.obj.vistaIPCreacion.text(registro.ipCreacion);
    this.obj.vistaIPActualizacion.text(registro.ipActualizacion);
    
    $("#vistaTotal").val(registro.montoVista);
    debugger;
  };

  moduloActual.recuperarValores = function(modo){
	  debugger;
	  var referenciaModulo=this;
    var eRegistro = {};
    try {
//	    eRegistro.id = parseInt(referenciaModulo.idRegistro);
	    eRegistro.cliente ={
	    		id: referenciaModulo.obj.cmpCodCliente.val(),
	    		codigoSap:moduloActual.obj.cmpCodClienteSapHid.val(),
	    		claveRamoSap:moduloActual.obj.cmpClaveRamoSapHid.val()};
	    eRegistro.canalSector ={idCanalSector : referenciaModulo.obj.cmpCanalSector.val()};
	    eRegistro.interlocutor = {
	    		idDatosInter:referenciaModulo.obj.cmpDestinatario.val(),
	    		codInterlocutorSap:moduloActual.obj.cmpCodInterlocutorSapHid.val()};
	    eRegistro.moneda = referenciaModulo.obj.cmpMoneda.val();
	    eRegistro.fechaCotizacion=utilitario.formatearStringToDate(referenciaModulo.obj.cmpFecha.val());
	    eRegistro.proceso = modo;
	    
	    eRegistro.items=[];
	    //datos para detalle
        var numeroFormularios = referenciaModulo.obj.grupoDetallePro.getForms().length;
        for(var contador = 0; contador < numeroFormularios; contador++){
          var detalles={};
          var formulario = referenciaModulo.obj.grupoDetallePro.getForm(contador);
          var cmpElementoPlantaTerminal = formulario.find("select[elemento-grupo='plantaTerminal']");
          var cmpElementoProducto = formulario.find("select[elemento-grupo='producto']");
          var cmpElementoVolumen  = formulario.find("input[elemento-grupo='volumen']");
          	detalles.posicion     = contador;
            detalles.planta  = {id:parseInt(cmpElementoPlantaTerminal.val())};
            detalles.producto= {id:parseInt(cmpElementoProducto.val())};
            debugger;
            detalles.volumen = parseFloat(cmpElementoVolumen.val().replaceAll(',',''));
//            detalles.precio = formulario.find("td[elemento-grupo='precio']").text();
//            detalles.descuento = formulario.find("td[elemento-grupo='descuento']").text();
//            detalles.precioNeto = formulario.find("td[elemento-grupo='precioNeto']").text();
//            detalles.rodaje = formulario.find("td[elemento-grupo='rodaje']").text();
//            detalles.isc = formulario.find("td[elemento-grupo='isc']").text();
//            detalles.acumulado = formulario.find("td[elemento-grupo='acumulado']").text();
//            detalles.igv = formulario.find("td[elemento-grupo='igv']").text();
//            detalles.fise = formulario.find("td[elemento-grupo='fise']").text();
//            detalles.precioDescuento = formulario.find("td[elemento-grupo='precioDescuento']").text();
//            detalles.precioPercepcion = formulario.find("td[elemento-grupo='precioPercepcion']").text();
//            detalles.importeTotal = formulario.find("td[elemento-grupo='importeTotal']").text();
            eRegistro.items.push(detalles);
        }
	    console.log(eRegistro);
	    
    }  catch(error){
      console.log(error.message);
    }
    return eRegistro;
  };
  
  
  ///////////////////////////////

  moduloActual.obj.grupoDetallePro = $('#GrupoDetallePro').sheepIt({
	  separator: '',
  allowRemoveLast: true,
  allowRemoveCurrent: true,
  allowRemoveAll: true,
  allowAdd: true,
  allowAddN: false,
  //maxFormsCount: 6,
  minFormsCount: 0,
  iniFormsCount: 0,
  afterAdd: function(origen, formularioNuevo) {

    var cmpElementoPlanta=$(formularioNuevo).find("select[elemento-grupo='plantaTerminal']");
    cmpElementoPlanta.select2({
  	  data: moduloActual.dataPlantas
    });
    cmpElementoPlanta.tipoControl="select2";
    
    var cmpElementoProducto=$(formularioNuevo).find("select[elemento-grupo='producto']");
    cmpElementoProducto.select2();          
    cmpElementoProducto.tipoControl="select2";
    
    cmpElementoPlanta.on('change', function(e){
	    moduloActual.obj.cmpSelect2Cisterna=$(formularioNuevo).find("select[elemento-grupo='producto']").select2({
		  ajax: {
			url: moduloActual.URL_LISTAR_PRODUCTO, 
			dataType: 'json',
			delay: 250,
			"data": {id:moduloActual.obj.cmpCodCliente.val(),
			    		idCanalSap:moduloActual.obj.cmpCodCanalHid.val(),/*1 ok*/
			    		idPlanta:cmpElementoPlanta.val(),
			    		codInterlocutorSap:moduloActual.obj.cmpCodInterlocutorSapHid.val(),
			    		claveRamoSap:moduloActual.obj.cmpClaveRamoSapHid.val()
			    		},
			processResults: function (respuesta, pagina) {
			    	var resultados= respuesta.contenido.carga;
			        return { results: resultados};
			    },
			cache: true
			},
			"language": "es",
			"escapeMarkup": function (markup) {
				return markup; },
			"templateResult": function (registro) {
				if (registro.loading) {
					return registro.text;
				}
		        return "<div class='select2-user-result'>" + registro.nombre + "</div>";},
		    "templateSelection": function (registro) {
		    	debugger;
		    	return registro.nombre || registro.text;
		    },
	    });
    });
    
    var cmpElementoVolumen=$(formularioNuevo).find("input[elemento-grupo='volumen']");
    cmpElementoVolumen.inputmask({
        'alias': 'decimal',
        rightAlign: true,
        'groupSeparator': ',',
        'autoGroup': true,
        //integerDigits: 9,
//        min:0.000,
//        max:999999999.999,
        digits: 3,
        digitsOptional: false,
        placeholder: "0.00",
        allowPlus: false,
        allowMinus: false,
        onKeyUp: function (e, buffer, opts) {
        	debugger;
        	var cant = parseFloat(this.value.replaceAll(",",""));
        	if(e.keyCode != 8 && e.keyCode != 46 && e.keyCode != 37 && e.keyCode != 39){
        		//ESCRIBIENDO
	        	if(cant > moduloActual.VALOR_MAXIMO){
	        		this.value = this.value.substring(0,this.value.length-1);
	//        		e.originalEvent.stopImmediatePropagation();
	//        		return false;
	        	}
        	} else if(e.ctrlKey == true && (e.keyCode == 86 || e.key =="v" || e.key =="V")){
        		//PEGANDO
        		if(cant > moduloActual.VALOR_MAXIMO){
        			this.value = moduloActual.VALOR_MAXIMO;
        		}
        	}
        }
      });
   
    
    var cmpElimina=$(formularioNuevo).find("[elemento-grupo='botonElimina']");
    cmpElimina.on("click", function(){
  	  
        try{
        	debugger;
//      	  	moduloActual.indiceFormulario = ($(formularioNuevo).attr('id')).substring(24);
//            var numeroFormularios = moduloActual.obj.grupoDetallePro.getForms().length;
//            for(var contador = 0; contador < numeroFormularios; contador++){
//                var formulario = moduloActual.obj.grupoDetallePro.getForm(contador);
//                var indice=(formulario.attr('id')).substring(24);//indice html
//                if(moduloActual.indiceFormulario == indice){
//          		  moduloActual.obj.grupoDetallePro.removeForm(contador);
//          		  break; 
//                }
//            }
        	moduloActual.indiceFormulario = ($(formularioNuevo).attr('id')).substring(24);
        	moduloActual.obj.grupoDetallePro.removeForm(moduloActual.indiceFormulario);
        } catch(error){
          console.log(error.message);
        }
    });
    var cmpVerMasDetalle=$(formularioNuevo).find("[elemento-grupo='botonVerMas']");
    cmpVerMasDetalle.on("click", function(){
  	  
        try{
        	debugger;
      	  	var contador = ($(formularioNuevo).attr('id')).substring(24);
      	  	
			var item = _cotizacionSesion.contenido.carga[0].items[contador];
			$("#vistaPlanta").text(item.planta.descripcion); 
			$("#vistaProducto").text(item.producto.nombre); 
			$("#vistaVolumen").text(item.volumenVista); 
			$("#vistaPrecio").text(item.precioVista); 
			$("#vistaDescuento").text(item.descuentoVista);
			$("#vistaPrecioNeto").text(item.precioNetoVista);
			$("#vistaRodaje").text(item.rodajeVista);
			$("#vistaISC").text(item.iscVista);
			$("#vistaOtros").text(item.otrosVista); // ticket 9000003025
			$("#vistaAcumulado").text(item.acumuladoVista);
			$("#vistaIgv").text(item.igvVista);
			$("#vistaFise").text(item.fiseVista);
			$("#vistaPrecioDescuento").text(item.precioDescuentoVista);
			$("#vistaPrecioPercepcion").text(item.precioPercepcionVista);
			$("#vistaImporteTotal").text(item.importeTotalVista);
  
            moduloActual.obj.modalConfirmarModificarSincronizado.modal("show");
        } catch(error){
          console.log(error.message);
        }
    });
  	},
	afterRemoveCurrent : function(control) {
		if (control.hasForms() == false) {
		}
	}
 });
  
  moduloActual.recuperarPlantas= function(){
	  var referenciaModulo = this;
	  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,"Procesando petici\u00f3n...");
	  return $.ajax({
	      type: constantes.PETICION_TIPO_GET,
	      url: referenciaModulo.URL_LISTAR_PLANTA, 
	      contentType: referenciaModulo.TIPO_CONTENIDO, 
	      data: {id:moduloActual.obj.cmpCodCliente.val(),
	    	  idCanalSap:moduloActual.obj.cmpCodCanalHid.val(),/*2 ok*/
	    	  codInterlocutorSap:moduloActual.obj.cmpCodInterlocutorSapHid.val(),
	    	  claveRamoSap:moduloActual.obj.cmpClaveRamoSapHid.val()},
	      
	      success: function(respuesta) {
	        if (!respuesta.estado) {
	          referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
	        }   else {     
	          referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
	          referenciaModulo.llenarPlantas(respuesta.contenido.carga);//llena el datasource combo producto
//	          referenciaModulo.obj.ocultaContenedorVista.show();
	        }
	      },                  
	      error: function() {
	        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Hubo un error en la petici\u00f3n");
	        referenciaModulo.obj.ocultaContenedorVista.show();
	      }
	  });
	};
	moduloActual.llenarPlantas = function(registros){
		debugger;
		  var referenciaModulo=this;	  
		  referenciaModulo.dataPlantas=[];
		  if(registros!=null){
			  var numeroDetalles= registros.length;
			  for(var contador=0; contador < numeroDetalles; contador++){ 
				  var opcionCombo={};
				  opcionCombo.id=registros[contador].id;
				  opcionCombo.text=registros[contador].descripcion;
				  referenciaModulo.dataPlantas.push(opcionCombo);  
			  }  
		  }
	  };
	  
  moduloActual.agregarDetalle = function(){
	  var referenciaModulo=this;
      try {
    	  referenciaModulo.obj.cntFormulario.show();
    	  referenciaModulo.recuperarPlantas().then(function(){
    	  referenciaModulo.obj.grupoDetallePro.addForm();});
      	
      } catch(error){
      	console.log(error.message);
      }
  };
  
  
  
  // JAFETH -- ESTE PINTA ADELANTE
  // JAFETH -- ESTE PINTA ADELANTE
  // JAFETH -- ESTE PINTA ADELANTE
  moduloActual.iniciarVerSimulado= function(respCotizacion){
	  debugger;
	  var referenciaModulo=this;
	  
	  _cotizacionSesion = respCotizacion;
	//datos para detalle
      var numeroFormularios = referenciaModulo.obj.grupoDetallePro.getForms().length;
      
      
      for(var contador = 0; contador < numeroFormularios; contador++){
        var formulario = referenciaModulo.obj.grupoDetallePro.getForm(contador);
        var item = respCotizacion.contenido.carga[0].items[contador];
        
        formulario.find("td[elemento-grupo='precio']").text(item.precioVista);
        formulario.find("td[elemento-grupo='descuento']").text(item.descuentoVista);
        formulario.find("td[elemento-grupo='precioNeto']").text(item.precioNetoVista);
        formulario.find("td[elemento-grupo='rodaje']").text(item.rodajeVista);
        formulario.find("td[elemento-grupo='isc']").text(item.iscVista);
        formulario.find("td[elemento-grupo='otros']").text(item.otrosVista); // ticket 9000003025
        formulario.find("td[elemento-grupo='acumulado']").text(item.acumuladoVista);
        formulario.find("td[elemento-grupo='igv']").text(item.igvVista);
        formulario.find("td[elemento-grupo='fise']").text(item.fiseVista);
        formulario.find("td[elemento-grupo='precioDescuento']").text(item.precioDescuentoVista);
        formulario.find("td[elemento-grupo='precioPercepcion']").text(item.precioPercepcionVista);
        formulario.find("td[elemento-grupo='precioTotal']").text(item.importeTotalVista);
      }
      
      $("#total").val(respCotizacion.contenido.carga[0].montoVista);
  };
  // JAFETH -- ESTE PINTA ADELANTE
  // JAFETH -- ESTE PINTA ADELANTE
  // JAFETH -- ESTE PINTA ADELANTE
  
  
  
  
  moduloActual.verificarDetalle = function(){
	  var referenciaModulo=this;
	  var numeroFormularios = referenciaModulo.obj.grupoDetallePro.getForms().length;
	  if(referenciaModulo.obj.grupoDetallePro.getForms().length > 0){
		  for(var contador = 0; contador < numeroFormularios; contador++){
			var formulario = referenciaModulo.obj.grupoDetallePro.getForm(contador);
			var cmpElementoPlantaTerminal = formulario.find("select[elemento-grupo='plantaTerminal']");
			var cmpElementoProducto = formulario.find("select[elemento-grupo='producto']");
			var cmpElementoVolumen  = formulario.find("input[elemento-grupo='volumen']");
		      
			if(cmpElementoPlantaTerminal.val() == "" || cmpElementoProducto.val() ==""
			  || cmpElementoVolumen.val() =="" || parseInt(cmpElementoVolumen.val()) == 0){
			  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Debe seleccionar una Planta, un Producto y un Volumen");
			  return false;
			}
		  }
		  return true;
	  } else {
		  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Debe ingresar al menos un producto");
		  return false;
	  }
  };
  moduloActual.inicializar();

  //Acciones de boton
  moduloActual.obj.btnAgregar.on(moduloActual.NOMBRE_EVENTO_CLICK,function(){
	  moduloActual.obj.btnAgregarFila.removeClass("disabled");
  });
  
  moduloActual.obj.btnAgregarFila.on(moduloActual.NOMBRE_EVENTO_CLICK,function(){
	  if(moduloActual.obj.cmpCodCliente.val() != null && moduloActual.obj.cmpCodCliente.val() != ""
		  && moduloActual.obj.cmpCanalSector.val() != null && moduloActual.obj.cmpCanalSector.val() != ""
			&& moduloActual.obj.cmpDestinatario.val() != null && moduloActual.obj.cmpCodInterlocutorSapHid.val() !=""){
		  moduloActual.agregarDetalle();
	  } else {
		  moduloActual.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Seleccione Cliente, Canal/Sector y Destinatario");
	  }
  });
  
  //////exportacion PDF
	function exportacion(e) {
		e.preventDefault();

		var url = moduloActual.URL_REPORTE + '?formato=pdf';
		url = url + "&id=" + moduloActual.idRegistro;
		window.open(url);
	}
   moduloActual.obj.btnExportar.on("click",function (e){
	   exportacion(e); });
   moduloActual.obj.btnExportar2.on("click",function (e){
	   exportacion(e); });
  
   
});

