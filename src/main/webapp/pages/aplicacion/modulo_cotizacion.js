function moduloCotizacion (){
  this.obj={};
  this.SEPARADOR_MILES=",";
  this.NUMERO_REGISTROS_PAGINA = constantes.NUMERO_REGISTROS_PAGINA;
  this.TOPES_PAGINACION = constantes.TOPES_PAGINACION;
  this.URL_LENGUAJE_GRILLA=  {
    "sProcessing":cadenas.GRILLA_PROCESANDO,
    "sLengthMenu":cadenas.GRILLA_LONGITUD_MENU,
    "sZeroRecords":cadenas.GRILLA_SIN_REGISTROS,
    "sEmptyTable": cadenas.GRILLA_TABLA_VACIA,
    "sInfo":cadenas.GRILLA_INFO,
    "sInfoEmpty": cadenas.GRILLA_INFO_VACIA,
    "sInfoFiltered": cadenas.GRILLA_INFO_FILTRADA,
    "sInfoPostFix":   cadenas.GRILLA_INFO_POST_FIX,
    "sSearch": cadenas.GRILLA_BUSCAR,
    "sUrl":          cadenas.GRILLA_URL,
    "sInfoThousands":  cadenas.GRILLA_MILES,
    "sLoadingRecords": cadenas.GRILLA_CARGANDO_REGISTROS,
    "oPaginate": {
      "sFirst":   cadenas.GRILLA_PAGINACION_PRIMERO,
      "sLast":    cadenas.GRILLA_PAGINACION_ULTIMO,
      "sNext":     cadenas.GRILLA_PAGINACION_SIGUIENTE,
      "sPrevious": cadenas.GRILLA_PAGINACION_ANTERIOR,
    },
    "oAria": {
      "sSortAscending":  cadenas.GRILLA_ORDEN_ASCENDENTE,
      "sSortDescending": cadenas.GRILLA_ORDEN_DESCENDENTE,
    }
  };
  this.TIPO_CONTENIDO=constantes.TIPO_CONTENIDO_JSON;
  this.NOMBRE_EVENTO_CLICK=constantes.NOMBRE_EVENTO_CLICK;
  this.modoEdicion=constantes.MODO_LISTAR;
  this.depuracionActivada=true;
  this.estaCargadaInterface=false;
  this.urlBase='';  
  this.mensajeEsMostrado=false;
  this.idRegistro = 0;
  this.columnasGrilla={};
  this.definicionColumnas=[];
  this.definicionColumnasDetalle=[];
  this.reglasValidacionFormulario={};
  this.mensajesValidacionFormulario={};
  this.ordenGrilla=[[ 1, 'asc' ]];
  this.columnasGrilla=[{ "data": null} ];
  this.columnasGrillaDetalle=[{ "data": null} ];
  this.definicionColumnas=[{
    "targets": 0,
    "searchable": false,
    "orderable": false,      
    "visible":false,
    "render": function ( datos, tipo, fila, meta ) {
    var configuracion =meta.settings;
    return configuracion._iDisplayStart + meta.row + 1;
    }
  }];  
};

moduloCotizacion.prototype.mostrarDepuracion=function(mensaje,titulo){
  var ref=this;
  if (ref.depuracionActivada === true){
    if ((typeof titulo != "undefined") && (titulo != null)) {
      console.log(titulo);
    }
    console.log(mensaje);
  }
};

moduloCotizacion.prototype.actualizarBandaInformacion=function(tipo, mensaje){
  var referenciaModulo=this;
	referenciaModulo.obj.bandaInformacion.removeClass(constantes.CLASE_MENSAJE_ERROR);
	referenciaModulo.obj.bandaInformacion.removeClass(constantes.CLASE_MENSAJE_EXITO);
	referenciaModulo.obj.bandaInformacion.removeClass(constantes.CLASE_MENSAJE_INFORMACION);
	if (tipo==constantes.TIPO_MENSAJE_INFO){
		referenciaModulo.obj.bandaInformacion.addClass(constantes.CLASE_MENSAJE_INFORMACION );
	} else if (tipo==constantes.TIPO_MENSAJE_EXITO){
		referenciaModulo.obj.bandaInformacion.addClass(constantes.CLASE_MENSAJE_EXITO );
	} else if (tipo==constantes.TIPO_MENSAJE_ERROR){
		referenciaModulo.obj.bandaInformacion.addClass(constantes.CLASE_MENSAJE_ERROR );
	}
	referenciaModulo.obj.bandaInformacion.text(mensaje);
};

moduloCotizacion.prototype.mostrarErrorServidor=function(xhr,estado,error){
  var referenciaModulo=this;
  if (xhr.status === constantes.ERROR_SIN_CONEXION) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_NO_CONECTADO);
  } else if (xhr.status == constantes.ERROR_RECURSO_NO_DISPONIBLE) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_RECURSO_NO_DISPONIBLE);
  } else if (xhr.status == constantes.ERROR_INTERNO_SERVIDOR) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_INTERNO_SERVIDOR);
  } else if (estado === constantes.ERROR_INTERPRETACION_DATOS) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_GENERICO_SERVIDOR);
  } else if (estado === constantes.ERROR_TIEMPO_AGOTADO) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_TIEMPO_AGOTADO);
  } else if (estado === constantes.ERROR_CONEXION_ABORTADA) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_GENERICO_SERVIDOR);
  } else {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_GENERICO_SERVIDOR);
  }
};

moduloCotizacion.prototype.inicializar=function(){
	console.log("inicializar en modulo base ");
	this.configurarAjax();
  this.inicializarControlesGenericos();
  this.inicializarCampos();
  this.inicializarGrilla();
  this.inicializarGrillaDetalle();
  this.inicializarFormularioPrincipal();
  
};

moduloCotizacion.prototype.configurarAjax=function(){
	console.log("configurarAjax");
	var csrfConfiguracion = $("#csrf-token");
	var nombreParametro = csrfConfiguracion.attr("name");
	var valorParametro = csrfConfiguracion.val();
	var parametros = {};
	parametros[nombreParametro]=valorParametro;
	console.log(parametros);
	$.ajaxSetup({
        data: parametros,
        headers : {'X-CSRF-TOKEN' : valorParametro}
    });
};

moduloCotizacion.prototype.resetearFormulario= function(){
  var referenciaModulo= this;
  referenciaModulo.obj.frmPrincipal[0].reset();
  jQuery.each( this.obj, function( i, val ) {
  if (typeof referenciaModulo.obj[i].tipoControl != constantes.CONTROL_NO_DEFINIDO ){
      if (referenciaModulo.obj[i].tipoControl == constantes.TIPO_CONTROL_SELECT2){
    	  console.log(referenciaModulo.obj[i].attr("data-valor-inicial"));
        referenciaModulo.obj[i].select2("val", referenciaModulo.obj[i].attr("data-valor-inicial"));
      }
    }
  });
};

moduloCotizacion.prototype.inicializarControlesGenericos=function(){
  var referenciaModulo=this; 
  //valida permisos de botones
  this.descripcionPermiso=$("#descripcionPermiso");
  //Formularios y contenedores
  this.obj.tituloSeccion=$("#tituloSeccion");
  this.obj.tablaPrincipal=$('#tablaPrincipal');
  this.obj.tablaDetalleVista=$('#tablaDetalleVista');
  this.obj.frmPrincipal = $("#frmPrincipal");
  this.obj.cntTabla=$("#cntTabla");
  this.obj.cntFormulario=$("#cntFormulario");  
  this.obj.cntVistaRegistro=$("#cntVistaRegistro");
  this.obj.cmpTituloFormulario=$("#cmpTituloFormulario");	
  this.obj.frmConfirmarModificarEstado=$("#frmConfirmarModificarEstado");	
  this.obj.frmConfirmarEliminar=$("#frmConfirmarEliminar");
  this.obj.ocultaContenedorTabla=$("#ocultaContenedorTabla");
  this.obj.ocultaContenedorFormulario=$("#ocultaContenedorFormulario");
  this.obj.ocultaContenedorVista=$("#ocultaContenedorVista");
  this.obj.bandaInformacion=$("#bandaInformacion");
  
  this.obj.cntFormulario=$("#cntFormulario");
  //Botones	
  this.obj.btnListar=$("#btnListar");
  this.obj.btnAgregar=$("#btnAgregar");
//  this.obj.btnModificar=$("#btnModificar");
//  this.obj.btnModificarEstado=$("#btnModificarEstado");	
//  this.obj.btnConfirmarModificarEstado=$("#btnConfirmarModificarEstado");
  this.obj.btnVer=$("#btnVer");
//  this.obj.btnConfirmarEliminar=$("#btnConfirmarEliminar");
  this.obj.btnSimular=$("#btnSimular");
  this.obj.btnGuardar=$("#btnGuardar");
  this.obj.btnCancelarGuardar=$("#btnCancelarGuardar");
  this.obj.btnCerrarVista=$("#btnCerrarVista");
  this.obj.btnFiltrar=$("#btnFiltrar");
  this.obj.btnExportar=$("#btnExportar");
  this.obj.btnExportar2=$("#btnExportar2");
  //Filtros
  this.obj.filtroFechaPlanificada=$("#filtroFechaPlanificada");
  this.obj.filtroFecha=$("#filtroFecha");
  this.obj.txtFiltro=$("#txtFiltro");
  //Campos
  this.obj.cmpFiltroEstado=$("#cmpFiltroEstado");
  this.obj.cmpFechaInicial=$("#cmpFechaInicial");
  this.obj.cmpFechaFinal=$("#cmpFechaFinal");
  this.obj.cmpFiltroUsuario=$("#cmpFiltroUsuario");
  this.obj.cmpFiltroTabla=$("#cmpFiltroTabla");
  this.obj.cmpFiltroTipoFecha=$("#cmpFiltroTipoFecha");	
  //Eventos para Botones
  this.obj.btnFiltrar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){ 
	if (!referenciaModulo.validaFormularioXSS("#frmBuscar")){
	  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR, cadenas.ERROR_VALORES_FORMULARIO);
	} else {
	  referenciaModulo.modoEdicion=constantes.MODO_LISTAR;
	  referenciaModulo.listarRegistros();
  	}
  });

  this.obj.btnListar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.modoEdicion=constantes.MODO_LISTAR;
  referenciaModulo.listarRegistros();
  });

  this.obj.btnAgregar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.iniciarAgregar();
  });

//  this.obj.btnModificar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
//  referenciaModulo.iniciarModificar();
//  });

  this.obj.btnVer.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
	  referenciaModulo.iniciarVer(null);		
  });
  
//  this.obj.btnModificarEstado.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
//  referenciaModulo.solicitarActualizarEstado();
//  });

//  this.obj.btnConfirmarModificarEstado.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
//  //referenciaModulo.actualizarEstadoRegistro();
//  });

  this.obj.btnCancelarGuardar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.iniciarCancelar();
  });

  this.obj.btnCerrarVista.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.iniciarCerrarVista();
  });
  
  this.obj.btnSimular.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
	  referenciaModulo.iniciarGuardar(constantes.MODO_SIMULAR_COTIZACION);
  });
  this.obj.btnGuardar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
	  referenciaModulo.iniciarGuardar(constantes.MODO_GUARDAR_COTIZACION);
  });
};

moduloCotizacion.prototype.iniciarGuardar = function(modo){
  var referenciaModulo=this;
  try {
	  if(referenciaModulo.verificarDetalle() == true){
		  referenciaModulo.guardarRegistro(modo);
	  }
  } catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  };
};

moduloCotizacion.prototype.iniciarAgregar= function(){  
	console.log("entra en iniciarAgregar del modulo base");
	var referenciaModulo=this;
	try {
    referenciaModulo.modoEdicion=constantes.MODO_NUEVO;
    referenciaModulo.obj.tituloSeccion.text(cadenas.TITULO_AGREGAR_REGISTRO);
    referenciaModulo.resetearFormulario();
    referenciaModulo.obj.cntTabla.hide();
    referenciaModulo.obj.cntVistaRegistro.hide();
    referenciaModulo.obj.cntFormulario.show();
    referenciaModulo.obj.ocultaContenedorFormulario.hide();
    referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.INICIAR_OPERACION);
	} catch(error){
		referenciaModulo.mostrarDepuracion(error.message);
	};
};

moduloCotizacion.prototype.iniciarModificar= function(){
	debugger;
  var referenciaModulo=this;  
  referenciaModulo.modoEdicion=constantes.MODO_ACTUALIZAR;
  referenciaModulo.obj.tituloSeccion.text(cadenas.TITULO_MODIFICA_REGISTRO);
  referenciaModulo.obj.cntTabla.hide();
  referenciaModulo.obj.cntVistaRegistro.hide();
  referenciaModulo.obj.ocultaContenedorFormulario.show();
  referenciaModulo.obj.cntFormulario.show();  
  referenciaModulo.recuperarRegistro();
};

moduloCotizacion.prototype.iniciarVer= function(respCotizacion){
  var referenciaModulo=this;
  referenciaModulo.modoEdicion=constantes.MODO_VER;
  referenciaModulo.obj.tituloSeccion.text(cadenas.TITULO_DETALLE_REGISTRO);
  referenciaModulo.obj.cntTabla.hide();
  referenciaModulo.obj.cntFormulario.hide();
  referenciaModulo.obj.ocultaContenedorVista.show();
  referenciaModulo.obj.cntVistaRegistro.show();
  if(respCotizacion == null){
	  referenciaModulo.recuperarRegistro();
  }else{
	  if (!respCotizacion.estado) {
  		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respCotizacion.mensaje);
  	} else {
  		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respCotizacion.mensaje);
  		//llenando datos de la proforma
  		referenciaModulo.llenarDetalles(respCotizacion.contenido.carga[0]);
  		//llenando detalle productos de la proforma
  		referenciaModulo.obj.datProformaDetalleApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);
  		referenciaModulo.obj.ocultaContenedorVista.hide();
  	}
  }
};

moduloCotizacion.prototype.iniciarVerSimulado= function(respCotizacion){
};
moduloCotizacion.prototype.iniciarCancelar=function(){
  var referenciaModulo=this;
  referenciaModulo.obj.tituloSeccion.text(cadenas.TITULO_LISTADO_REGISTROS);
  referenciaModulo.resetearFormulario();
  referenciaModulo.obj.cntFormulario.hide();
  referenciaModulo.obj.ocultaContenedorFormulario.show();
  referenciaModulo.obj.cntVistaRegistro.hide();
  referenciaModulo.obj.cntTabla.show();
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.CANCELAR_OPERACION);
};

moduloCotizacion.prototype.iniciarCerrarVista=function(){
  var referenciaModulo=this;
  referenciaModulo.obj.tituloSeccion.text(cadenas.TITULO_LISTADO_REGISTROS);
  referenciaModulo.obj.cntFormulario.hide();
  referenciaModulo.obj.cntVistaRegistro.hide();
  referenciaModulo.obj.ocultaContenedorVista.show();
  referenciaModulo.obj.cntTabla.show();
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.CERRAR_VISTA);
};

moduloCotizacion.prototype.llamadaAjaxGrilla=function(e,configuracion,json){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_AJAX);
  referenciaModulo.desactivarBotones();
  if (json.estado==true){
    json.recordsTotal=json.contenido.totalRegistros;
    json.recordsFiltered=json.contenido.totalEncontrados;
    json.data= json.contenido.carga;
    if (referenciaModulo.modoEdicion==constantes.MODO_LISTAR){
    referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,json.mensaje);
    }
  } else {
    json.recordsTotal=0;
    json.recordsFiltered=0;
    json.data= {};
    if (referenciaModulo.modoEdicion==constantes.MODO_LISTAR){
    referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,json.mensaje);
    } else {

    }
  }
  if (referenciaModulo.estaCargadaInterface==false){        
    referenciaModulo.estaCargadaInterface=true;
  }
  referenciaModulo.obj.ocultaContenedorTabla.hide();  
};

moduloCotizacion.prototype.llamadaAjax=function(d){
	 console.log("entra en llamadaAjax en modulo base");
	 debugger;
	var referenciaModulo =this;
    var indiceOrdenamiento = d.order[0].column;
    d.registrosxPagina =  d.length; 
    d.inicioPagina = d.start; 
    d.campoOrdenamiento= d.columns[indiceOrdenamiento].data;
    d.sentidoOrdenamiento=d.order[0].dir;
    d.valorBuscado=d.search.value;
    d.idCliente = referenciaModulo.obj.cmpFiltroCliente.val();
    
    var rangoFecha = referenciaModulo.obj.txtFiltroFecha.val().split("-");
    var fechaInicio = utilitario.formatearFecha2Iso(rangoFecha[0]) ;
    var fechaFinal = utilitario.formatearFecha2Iso(rangoFecha[1]);
    d.filtroFechaInicio= fechaInicio;
    d.filtroFechaFinal = fechaFinal;
    d.filtroUsuario= referenciaModulo.obj.cmpFiltroUsuario.val();
    d.filtroTabla= referenciaModulo.obj.cmpFiltroTabla.val();
};
moduloCotizacion.prototype.llamadaDetalleAjax=function(d){
	 console.log("entra en llamadaDetalleAjax en modulo base");
		var referenciaModulo =this;
	    var indiceOrdenamiento = d.order[0].column;
	    d.registrosxPagina =  d.length; 
	    d.inicioPagina = d.start; 
	    d.campoOrdenamiento= d.columns[indiceOrdenamiento].data;
	    d.sentidoOrdenamiento=d.order[0].dir;
	    d.filtroIdProforma=referenciaModulo.idRegistro;//
	    d.filtroUsuario= referenciaModulo.obj.cmpFiltroUsuario.val();
	    d.filtroTabla= referenciaModulo.obj.cmpFiltroTabla.val();
	};

moduloCotizacion.prototype.inicializarGrilla=function(){
  var referenciaModulo=this;
  console.log("entra en inicializarGrilla en modulo base");
  this.obj.tablaPrincipal.on(constantes.DT_EVENTO_AJAX, function (e,configuracion,json) {
	   referenciaModulo.llamadaAjaxGrilla(e,configuracion,json);
  });
  
  this.obj.tablaPrincipal.on(constantes.DT_EVENTO_PREAJAX, function (e,configuracion,datos) {
    if (referenciaModulo.estaCargadaInterface==true){
    referenciaModulo.obj.ocultaContenedorTabla.show();
    }
  });

  this.obj.tablaPrincipal.on(constantes.DT_EVENTO_PAGINACION, function () {
  });

  this.obj.tablaPrincipal.on(constantes.DT_EVENTO_ORDENACION, function () {
  });

  this.obj.dataProformaApi= this.obj.tablaPrincipal.DataTable({
    processing: true,
    responsive: true,
    dom: constantes.DT_ESTRUCTURA,
    iDisplayLength: referenciaModulo.NUMERO_REGISTROS_PAGINA,
    lengthMenu: referenciaModulo.TOPES_PAGINACION,
    language: referenciaModulo.URL_LENGUAJE_GRILLA,
    serverSide: true,
    ajax: {
      url: referenciaModulo.URL_LISTAR,
      type:constantes.PETICION_TIPO_GET,
      data: function (d) {
    	  console.log("referenciaModulo.llamadaAjax(d) modulo base");
    	  referenciaModulo.llamadaAjax(d);
      }
    },
    columns: referenciaModulo.columnasGrilla,
    columnDefs: referenciaModulo.definicionColumnas
    //"order": referenciaModulo.ordenGrilla
	});	
	
	$('#tablaPrincipal tbody').on(referenciaModulo.NOMBRE_EVENTO_CLICK, 'tr', function () {
		if (referenciaModulo.obj.dataProformaApi.data().length > 0){
        var indiceFila = referenciaModulo.obj.dataProformaApi.row( this ).index();
        var idRegistro = referenciaModulo.obj.dataProformaApi.cell(indiceFila,1).data();
		    if ( $(this).hasClass('selected') ) {
		        $(this).removeClass('selected');
            referenciaModulo.desactivarBotones();
        } else {
		        referenciaModulo.obj.dataProformaApi.$('tr.selected').removeClass('selected');
		        $(this).addClass('selected');
            referenciaModulo.idRegistro=idRegistro;		
            referenciaModulo.grillaDespuesSeleccionar(indiceFila);
            referenciaModulo.activarBotones();
        }
		}
	});
};

/*tabla con datos de detalle*/
moduloCotizacion.prototype.inicializarGrillaDetalle=function(){
  var referenciaModulo=this;
  console.log("entra en inicializarGrillaDetalle en modulo base");
  this.obj.tablaDetalleVista.on(constantes.DT_EVENTO_AJAX, function (e,configuracion,json) {
	   referenciaModulo.llamadaAjaxGrilla(e,configuracion,json);
  });
  
  this.obj.tablaDetalleVista.on(constantes.DT_EVENTO_PAGINACION, function () {
  });

  this.obj.tablaDetalleVista.on(constantes.DT_EVENTO_ORDENACION, function () {
  });

  this.obj.datProformaDetalleApi= this.obj.tablaDetalleVista.DataTable({
	  paging:false,
    processing: true,
    responsive: true,
    dom: constantes.DT_ESTRUCTURA,
//    iDisplayLength: referenciaModulo.NUMERO_REGISTROS_PAGINA,
    lengthMenu: referenciaModulo.TOPES_PAGINACION,
    language: referenciaModulo.URL_LENGUAJE_GRILLA,
    serverSide: true,
    ajax: {
      url: referenciaModulo.URL_LISTAR_DETALLE,
      type:constantes.PETICION_TIPO_GET,
      data: function (d) {
    	  console.log("referenciaModulo.llamadaDetalleAjax(d) modulo base");
    	  referenciaModulo.llamadaDetalleAjax(d);
      }
    },
    columns: referenciaModulo.columnasGrillaDetalle,
    columnDefs: referenciaModulo.definicionColumnasDetalle
	});	
};

moduloCotizacion.prototype.inicializarFormularioPrincipal= function(){  
  var referenciaModulo=this;
    this.obj.frmPrincipal.validate({
    rules: referenciaModulo.reglasValidacionFormulario,
    messages: referenciaModulo.mensajesValidacionFormulario,
    submitHandler: function(form) {
    // form.submit();
    }
  });
};

moduloCotizacion.prototype.activarBotones=function(){
//  this.obj.btnModificar.removeClass(constantes.CSS_CLASE_DESHABILITADA);
  this.obj.btnExportar.removeClass(constantes.CSS_CLASE_DESHABILITADA);
  this.obj.btnVer.removeClass(constantes.CSS_CLASE_DESHABILITADA);
};

moduloCotizacion.prototype.desactivarBotones=function(){
};

moduloCotizacion.prototype.listarRegistros = function(){
  var referenciaModulo=this;
  referenciaModulo.obj.dataProformaApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);	 	
};

moduloCotizacion.prototype.recuperarRegistro= function(){
	var referenciaModulo = this;
	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
	//cabecera
	$.ajax({
	    type: constantes.PETICION_TIPO_GET,
	    url: referenciaModulo.URL_RECUPERAR, 
	    contentType: referenciaModulo.TIPO_CONTENIDO, 
	    data: {ID:referenciaModulo.idRegistro},	
	    success: function(respuesta) {
	    	if (!respuesta.estado) {
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
	    	} else {
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
	    		if (referenciaModulo.modoEdicion == constantes.MODO_VER){
	    			referenciaModulo.llenarDetalles(respuesta.contenido.carga[0]);
	    			referenciaModulo.obj.ocultaContenedorVista.hide();
	    		}
    		}
	    },
	    error: function(xhr,estado,error) {
	        referenciaModulo.mostrarErrorServidor(xhr,estado,error);        
	        if (referenciaModulo.modoEdicion == constantes.MODO_ACTUALIZAR){
	          referenciaModulo.obj.ocultaContenedorFormulario.hide();
	        } else if (referenciaModulo.modoEdicion == constantes.MODO_VER){
	          referenciaModulo.obj.ocultaContenedorVista.hide();
	        }
	    }
	});
	//detalle
	referenciaModulo.obj.datProformaDetalleApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);	 
};

moduloCotizacion.prototype.verRegistro= function(){
	var referenciaModulo = this;
	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
	$.ajax({
	    type: constantes.PETICION_TIPO_GET,
	    url: referenciaModulo.URL_RECUPERAR, 
	    contentType: referenciaModulo.TIPO_CONTENIDO, 
	    data: {ID:referenciaModulo.idRegistro},	
	    success: function(respuesta) {
	    	if (!respuesta.estado) {
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
	    	} 	else {		 
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
	    		referenciaModulo.llenarDetalles(respuesta.contenido.carga[0]);
	    		referenciaModulo.obj.ocultaContenedorVista.show();
    		}
	    },			    		    
	    error: function() {
	    	referenciaModulo.mostrarErrorServidor(xhr,estado,error); 
	    	referenciaModulo.obj.ocultaContenedorVista.show();
	    }
	});
};

moduloCotizacion.prototype.guardarRegistro= function(modo){
	debugger;
	var referenciaModulo = this;
	try {
		if (!referenciaModulo.validaFormularioXSS("#frmPrincipal")){
			referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR, cadenas.ERROR_VALORES_FORMULARIO);
		} else if (referenciaModulo.obj.frmPrincipal.valid()){
			referenciaModulo.obj.ocultaContenedorFormulario.show();
			referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
			var eRegistro = referenciaModulo.recuperarValores(modo);
			
			$.ajax({
		      type: constantes.PETICION_TIPO_POST,
		      url: referenciaModulo.URL_GENERAR, 
		      contentType: referenciaModulo.TIPO_CONTENIDO, 
		      data: JSON.stringify(eRegistro),	
		      success: function(respuesta) {
		        if (!respuesta.estado) {
		          referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
		        } else {
		        	debugger;
		        	
		          if (modo == constantes.MODO_SIMULAR_COTIZACION){
		        	  referenciaModulo.iniciarVerSimulado(respuesta);///
		          } else if  (modo == constantes.MODO_GUARDAR_COTIZACION){
		        	  referenciaModulo.iniciarVer(respuesta);
		          }
		        }
		        referenciaModulo.obj.ocultaContenedorFormulario.hide();
		      },			    		    
		      error: function(er) {
		    	  debugger;
		        referenciaModulo.mostrarErrorServidor(xhr,estado,error); 
		      }
			});
		} else {
			referenciaModulo.obj.ocultaContenedorFormulario.hide();
		}
	} catch(error){
	    referenciaModulo.mostrarDepuracion(error.message);
	};
};

moduloCotizacion.prototype.iniciarListado= function(mensaje){
	var referenciaModulo = this;
	try {
		referenciaModulo.listarRegistros();
		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,mensaje);
		referenciaModulo.obj.cntFormulario.hide();	
		referenciaModulo.obj.cntTabla.show();
	} catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
	};
};

moduloCotizacion.prototype.iniciarContenedores = function(){
  var referenciaModulo = this;
  try {
    referenciaModulo.obj.cntFormulario.hide();	
    referenciaModulo.protegeFormulario(false);
    referenciaModulo.obj.cntTabla.show();
  } catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  };
};

moduloCotizacion.prototype.solicitarActualizarEstado= function(){
	try {
		this.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
		this.obj.frmConfirmarModificarEstado.modal("show");
	} catch(error){
		referenciaModulo.mostrarDepuracion(error.message);
	}
};

moduloCotizacion.prototype.actualizarEstadoRegistro= function(){
  var eRegistro = {};
  var referenciaModulo=this;
	referenciaModulo.obj.frmConfirmarModificarEstado.modal("hide");
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
  if (referenciaModulo.estadoRegistro==constantes.ESTADO_ACTIVO){
    referenciaModulo.estadoRegistro=constantes.ESTADO_INACTIVO;
  } else {
    referenciaModulo.estadoRegistro=constantes.ESTADO_ACTIVO;
  }

  try {
    eRegistro.id = parseInt(referenciaModulo.idRegistro);
    eRegistro.estado = parseInt(referenciaModulo.estadoRegistro);
  }  catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  }

  $.ajax({
    type: constantes.PETICION_TIPO_POST,
    url: referenciaModulo.URL_ACTUALIZAR_ESTADO, 
    contentType: referenciaModulo.TIPO_CONTENIDO, 
    data: JSON.stringify(eRegistro),	
    success: function(respuesta) {
      if (!respuesta.estado) {
        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
      } else {		    				    			    		
        referenciaModulo.listarRegistros();
        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
        referenciaModulo.obj.cntFormulario.hide();	
        referenciaModulo.obj.cntTabla.show();
      }
    },			    		    
    error: function() {
      referenciaModulo.mostrarErrorServidor(xhr,estado,error); 
    }
    });
	};
	

moduloCotizacion.prototype.inicializaCamposFormulario= function(){
//Implementar en cada caso
};
	
moduloCotizacion.prototype.inicializarCampos= function(){
//Implementar en cada caso
};

moduloCotizacion.prototype.llenarFormulario = function(registro){
//Implementar en cada caso	
};

moduloCotizacion.prototype.llenarDetalles = function(registro){
//Implementar en cada caso
};

moduloCotizacion.prototype.recuperarValores = function(registro){
var eRegistro = {};
//Implementar en cada caso
return eRegistro;
};

moduloCotizacion.prototype.grillaDespuesSeleccionar= function(indice){
//TODO implementar
};

moduloCotizacion.prototype.despuesListarRegistros=function(){
//Implementar en cada caso
	
	$("#tablaDetalleVista_info").hide();
};

moduloCotizacion.prototype.validaFormularioXSS= function(formulario){
//$(document).ready(function(){
	var retorno = true;
    $(formulario).find(':input').each(function() {
     var elemento= this;
     console.log($(this));
     if(!utilitario.validaCaracteresFormulario(elemento.value)){
    	 retorno = false;
     }
    });
    return retorno;
  // });
};

moduloCotizacion.prototype.validaPermisos=function(){
//Implementar en cada caso
};