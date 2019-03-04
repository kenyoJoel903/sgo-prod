$(document).ready(function(){
  var _sincronizacion = false;
  var moduloActual = new moduloBase();  
  moduloActual.urlBase='cliente';
  //moduloActual.NUMERO_REGISTROS_PAGINA=15;
  moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
  moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
  moduloActual.URL_ELIMINAR = moduloActual.urlBase + '/eliminar';
  moduloActual.URL_GUARDAR = moduloActual.urlBase + '/crear';
  moduloActual.URL_ACTUALIZAR = moduloActual.urlBase + '/actualizar';
  moduloActual.URL_ACTUALIZAR_ESTADO = moduloActual.urlBase + '/actualizarEstado';
  moduloActual.URL_SINCRONIZAR = moduloActual.urlBase + '/sincronizar';
  moduloActual.URL_LIMPIAR = moduloActual.urlBase + '/limpiar';
  
  moduloActual.ordenGrilla=[[ 3, 'asc' ]];
  //Agregar columnas a la grilla
  moduloActual.columnasGrilla.push({ "data": 'id'}); 
  moduloActual.columnasGrilla.push({ "data": 'codigoSAP'});/*phf:*/
  moduloActual.columnasGrilla.push({ "data": 'nombreCorto'});
  moduloActual.columnasGrilla.push({ "data": 'razonSocial'});  
  moduloActual.columnasGrilla.push({ "data": 'ruc'});
  moduloActual.columnasGrilla.push({ "data": 'estado'});

  moduloActual.definicionColumnas.push({"targets": 1, "searchable": false, "orderable": true, "visible":false });
  moduloActual.definicionColumnas.push({"targets": 2, "searchable": false, "orderable": false, "visible":true });
  moduloActual.definicionColumnas.push({"targets": 3, "searchable": false, "orderable": true, "visible":true });
  moduloActual.definicionColumnas.push({"targets": 4, "searchable": false, "orderable": true, "visible":true });
  moduloActual.definicionColumnas.push({"targets": 5, "searchable": false, "orderable": true, "visible":true, "class": "text-right" });
  moduloActual.definicionColumnas.push({"targets": 6, "searchable": false, "orderable": true, "visible":true, "render": utilitario.formatearEstado });

  moduloActual.reglasValidacionFormulario={
	cmpRazonSocial: { required: true, maxlength: 150 },
	cmpNombreCorto: { required: true, maxlength: 20   },
	cmpRuc: { required: true, rangelength: [11, 11], number: true },
	cmpNumContrato: { required: true, maxlength: 20   },
	cmpDesContrato: { required: true, maxlength: 200   }
  };

  
  moduloActual.mensajesValidacionFormulario={
	cmpRazonSocial: {
		required: "El campo 'Razon Social' es obligatorio",
		maxlength: "El campo 'Razon Social' debe contener 150 caracteres como maximo"
	},
	cmpNombreCorto: {
		required: "El campo 'Nombre Corto' es obligatorio",
		maxlength: "El campo 'Nombre Corto' debe contener 20 caracteres como maximo"
	},
	cmpRuc: {
		required: "El campo 'RUC' es obligatorio",
		rangelength: "El campo 'RUC' debe contener 11 caracteres",
		number: "El campo 'RUC' solo debe contener caracteres numericos"
	},
	cmpNumContrato: {
		required: "El campo 'Nro. de Contrato' es obligatorio",
		maxlength: "El campo 'Nro. de Contrato' debe contener 20 caracteres como maximo"
	},
	cmpDesContrato: {
		required: "El campo 'Descripcion del Contrato' es obligatorio",
		maxlength: "El campo 'Descripcion del Contrato' debe contener 200 caracteres como maximo"
	}
  };
  
  /**********************//*phf*/
  var moduloCanalSector = new moduloBase();  
  moduloCanalSector.urlBase='cliente';
  moduloCanalSector.URL_LISTAR = moduloCanalSector.urlBase + '/listarCS';
  moduloCanalSector.URL_LISTAR_SINCRO = moduloCanalSector.urlBase + '/sincroCanalSector';
  moduloCanalSector.ordenGrilla=[[ 2, 'asc' ]];
  
  //Agregar columnas a la grilla
  moduloCanalSector.columnasGrilla.push({ "data": 'idCanalSector'}); 
  moduloCanalSector.columnasGrilla.push({ "data": 'canalDistribucionSap'});
  moduloCanalSector.columnasGrilla.push({ "data": 'descCanalDistribucionSap'});
  moduloCanalSector.columnasGrilla.push({ "data": 'sectorSap'});  
  moduloCanalSector.columnasGrilla.push({ "data": 'descSectorSap'});
  moduloCanalSector.columnasGrilla.push({ "data": 'descripcionCanalSector'});

  moduloCanalSector.definicionColumnas.push({"targets": 1, "searchable": false, "orderable": false, "visible":false });
  moduloCanalSector.definicionColumnas.push({"targets": 2, "searchable": false, "orderable": false, "visible":true });
  moduloCanalSector.definicionColumnas.push({"targets": 3, "searchable": false, "orderable": false, "visible":true });
  moduloCanalSector.definicionColumnas.push({"targets": 4, "searchable": false, "orderable": false, "visible":true });
  moduloCanalSector.definicionColumnas.push({"targets": 5, "searchable": false, "orderable": false, "visible":true, "class": "text-right" });
  moduloCanalSector.definicionColumnas.push({"targets": 6, "searchable": false, "orderable": false, "visible":true, "render": function(dato, tipo, fila, meta){
	  if(moduloCanalSector.modoEdicion==constantes.MODO_ACTUALIZAR
			 || moduloCanalSector.modoEdicion==constantes.MODO_NUEVO){
		  return "<input type='text' id='canSecDesc_"+fila.idCanalSector+"' canSecDesc='"+fila.idCanalSector+"' value='"+dato+"' readonly='readonly' style='width:100%' class='form-control text-uppercase input-sm'>";
	  } else if(moduloCanalSector.modoEdicion==constantes.MODO_LISTAR){
		  return dato;
	  }
  } });
  
  /*sobre escrito*/
  moduloCanalSector.inicializarControlesGenericos=function(idTabla){
    //valida permisos de botones
//	  moduloCanalSector.descripcionPermiso=$("#descripcionPermiso");
    //Formularios y contenedores
//	  moduloCanalSector.obj.tituloSeccion=$("#tituloSeccion");
	  moduloCanalSector.obj.tablaPrincipal=$('#'+idTabla);///////
	  moduloCanalSector.obj.bandaInformacion=$("#bandaInformacion");////////
  };
  /*sobre escrito*/
  moduloCanalSector.llamadaAjaxGrilla=function(e,configuracion,json){
	  if (json.estado==true){
	    json.recordsTotal=json.contenido.totalRegistros;
	    json.recordsFiltered=json.contenido.totalEncontrados;
	    json.data= json.contenido.carga;
	    if (moduloCanalSector.modoEdicion==constantes.MODO_LISTAR){
	    	moduloCanalSector.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,json.mensaje);
	    }
	  } else {
	    json.recordsTotal=0;
	    json.recordsFiltered=0;
	    json.data= {};
	    if (moduloCanalSector.modoEdicion==constantes.MODO_LISTAR){
	    	moduloCanalSector.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,json.mensaje);
	    }
	  }
	  if (moduloCanalSector.estaCargadaInterface==false){
		  moduloCanalSector.estaCargadaInterface=true;
	  }
	};
	/*sobre escrito*/
	moduloCanalSector.inicializarGrilla = function(urlAccion) {
		console.log("en inicializarGrilla SC");

		console.log("entra en inicializarGrilla en modulo base SC");
		moduloCanalSector.obj.tablaPrincipal.on(constantes.DT_EVENTO_AJAX, function(e,configuracion, json) {
			moduloCanalSector.llamadaAjaxGrilla(e, configuracion, json);
		});

		moduloCanalSector.obj.datClienteApi = moduloCanalSector.obj.tablaPrincipal.DataTable();
		moduloCanalSector.obj.datClienteApi.destroy();
		moduloCanalSector.obj.datClienteApi = moduloCanalSector.obj.tablaPrincipal.DataTable({
			processing : true,
			bPaginate : false,
			responsive : true,
			dom : constantes.DT_ESTRUCTURA,
			// iDisplayLength:
			// moduloCanalSector.NUMERO_REGISTROS_PAGINA,
			// lengthMenu:
			// moduloCanalSector.TOPES_PAGINACION,
			language : moduloCanalSector.URL_LENGUAJE_GRILLA,
			serverSide : true,
			ajax : {
				url : urlAccion,
				type : constantes.PETICION_TIPO_GET,
				data : function(d) {
					console.log("moduloCanalSector.llamadaAjax(d) modulo base SC");
					// moduloCanalSector.llamadaAjax(d);
					/* id sleccionado de la tabla */
					debugger;
					d.filtroIdUsuario = moduloActual.idRegistro;
				}
			},
			columns : moduloCanalSector.columnasGrilla,
			columnDefs : moduloCanalSector.definicionColumnas,
		});

	};
  /*sobre escrito*/
  moduloCanalSector.inicializar=function(idTabla, urlAccion){
		console.log("inicializar en modulo base SC");
//		moduloCanalSector.configurarAjax();
		moduloCanalSector.inicializarControlesGenericos(idTabla);
		moduloCanalSector.inicializarGrilla(urlAccion);
	  
  };
  /**********************/
  
  
  moduloActual.inicializarCampos= function(){
    //Campos de formulario
    this.obj.cmpCodigoSAP=$("#cmpCodigoSAP");
    this.obj.cmpRazonSocialSAP=$("#cmpRazonSocialSAP");
    this.obj.cmpRamaSAP=$("#cmpRamaSAP");
    
    this.obj.cmpRazonSocial=$("#cmpRazonSocial");
    this.obj.cmpNombreCorto=$("#cmpNombreCorto");
    this.obj.cmpRuc=$("#cmpRuc");
    this.obj.cmpRuc.inputmask("99999999999");
    this.obj.cmpNumContrato=$("#cmpNumContrato");
    this.obj.cmpDesContrato=$("#cmpDesContrato");
    //Campos de vista
    this.obj.vistaId=$("#vistaId");
    this.obj.vistaRazonSocial=$("#vistaRazonSocial");
    this.obj.vistaNombreCorto=$("#vistaNombreCorto");
    this.obj.vistaRuc=$("#vistaRuc");
    this.obj.vistaNumContrato=$("#vistaNumContrato");
    this.obj.vistaDesContrato=$("#vistaDesContrato");
    this.obj.vistaEstado=$("#vistaEstado");
    this.obj.vistaCreadoEl=$("#vistaCreadoEl");
    this.obj.vistaCreadoPor=$("#vistaCreadoPor");
    this.obj.vistaActualizadoEl=$("#vistaActualizadoEl");
    this.obj.vistaActualizadoPor=$("#vistaActualizadoPor");
    this.obj.vistaIPCreacion=$("#vistaIPCreacion");
    this.obj.vistaIPActualizacion=$("#vistaIPActualizacion");
    
    this.obj.vistaCodigoSAP=$("#vistaCodigoSAP");
    this.obj.vistaRazonSocialSAP=$("#vistaRazonSocialSAP");
    this.obj.vistaRama=$("#vistaRamaSAP");
    // campos sincronizar. phf
    this.obj.cmpCodigoSAPSincronizar=$("#cmpCodigoSAPSincronizar");
    this.obj.btnSincronizar=$("#btnSincronizar");
    this.obj.btnConfirmarModificar=$("#btnConfirmarModificar");
    this.obj.btnConfirmarSincronizar=$("#btnConfirmarSincronizar");
    
    this.obj.modalConfirmarModificarSincronizado=$("#modalConfirmarModificarSincronizado");
  };

  moduloActual.grillaDespuesSeleccionar= function(indice){
	  var referenciaModulo=this;
		var estadoRegistro = referenciaModulo.obj.datClienteApi.cell(indice,6).data();
		referenciaModulo.estadoRegistro=estadoRegistro;	
		if (estadoRegistro == constantes.ESTADO_ACTIVO) {
			console.log('1');
			referenciaModulo.obj.btnModificarEstado.html('<i class="fa fa-cloud-download"></i> '+constantes.TITULO_DESACTIVAR_REGISTRO);			
		} else {
			console.log('2');
			referenciaModulo.obj.btnModificarEstado.html('<i class="fa fa-cloud-upload"></i>  '+constantes.TITULO_ACTIVAR_REGISTRO);			
		}
  };

  moduloActual.llenarFormulario = function(registro){
	  debugger;
	var referenciaModulo= this;
	referenciaModulo.idRegistro= registro.id;
	referenciaModulo.obj.cmpRazonSocial.val(registro.razonSocial);
	referenciaModulo.obj.cmpNombreCorto.val(registro.nombreCorto);
	referenciaModulo.obj.cmpRuc.val(registro.ruc);
	referenciaModulo.obj.cmpNumContrato.val(registro.numeroContrato);
	referenciaModulo.obj.cmpDesContrato.val(registro.descripcionContrato);
	
	referenciaModulo.obj.cmpCodigoSAP.val(registro.codigoSAP);
	referenciaModulo.obj.cmpRazonSocialSAP.val(registro.razonSocialSAP);
	referenciaModulo.obj.cmpRamaSAP.val(registro.ramaSAP);

	referenciaModulo.obj.cmpCodigoSAPSincronizar.val(registro.codigoSAP);
	
	//configurando campos para la edicion
	if(registro.codigoSAP != null && registro.codigoSAP != ""){
		referenciaModulo.obj.cmpCodigoSAPSincronizar.attr("disabled","disabled");
	}
	/*metodo*/
	referenciaModulo.desabilitarCamposSincronizados();
	referenciaModulo.obj.btnSincronizar.removeClass("disabled");
  };
  
  moduloActual.desabilitarCamposSincronizados = function(){
	  moduloActual.obj.cmpCodigoSAP.attr("disabled","disabled");
	  moduloActual.obj.cmpRazonSocialSAP.attr("disabled","disabled");
	  moduloActual.obj.cmpRazonSocial.attr("disabled","disabled");
	  moduloActual.obj.cmpRamaSAP.attr("disabled","disabled");
	  moduloActual.obj.cmpRuc.attr("disabled","disabled");
  }

  moduloActual.llenarFormularioSincronizado = function(registro){
	  debugger;
	var referenciaModulo= this;
//	referenciaModulo.idRegistro= registro.id;
	referenciaModulo.obj.cmpRazonSocial.val(registro.razonSocial);
	referenciaModulo.obj.cmpRuc.val(registro.ruc);
	
	referenciaModulo.obj.cmpCodigoSAP.val(registro.codigoSAP);
	referenciaModulo.obj.cmpRazonSocialSAP.val(registro.razonSocialSAP);
	referenciaModulo.obj.cmpRamaSAP.val(registro.ramaSAP);

	referenciaModulo.obj.cmpCodigoSAPSincronizar.val(registro.codigoSAP);
  };
  
  moduloActual.resetearFormulario= function(){
  var referenciaModulo= this;
  referenciaModulo.obj.frmPrincipal[0].reset();
  console.log("Verificador");
  referenciaModulo.obj.verificadorFormulario.resetForm();
  jQuery.each( this.obj, function( i, val ) {
    if (typeof referenciaModulo.obj[i].tipoControl != constantes.CONTROL_NO_DEFINIDO ){
      if (referenciaModulo.obj[i].tipoControl == constantes.TIPO_CONTROL_SELECT2){
    	  console.log(referenciaModulo.obj[i].attr("data-valor-inicial"));
        referenciaModulo.obj[i].select2("val", referenciaModulo.obj[i].attr("data-valor-inicial"));
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

// es invocado por el boton btnGuardar
moduloActual.iniciarGuardar = function(){
	if(_sincronizacion ==  true){
		mostrarConfirmacionGuardar();
	} else {
		guardarCliente();
	}
	_sincronizacion = false;
};
function mostrarConfirmacionGuardar(){
	moduloActual.obj.modalConfirmarModificarSincronizado.modal("show");
	$("#msjSincronizar").hide();
	$("#msjActualizarSincronizado").show();
  	moduloActual.obj.btnConfirmarSincronizar.hide();
  	moduloActual.obj.btnConfirmarModificar.show();
}
  moduloActual.llenarDetalles = function(registro){
    this.idRegistro= registro.id;
    this.obj.vistaId.text(registro.id);
    this.obj.vistaRazonSocial.text(registro.razonSocial);
    this.obj.vistaNombreCorto.text(registro.nombreCorto);
    this.obj.vistaRuc.text(registro.ruc);
    this.obj.vistaNumContrato.text(registro.numeroContrato);
    this.obj.vistaDesContrato.text(registro.descripcionContrato);
    this.obj.vistaEstado.text(utilitario.formatearEstado(registro.estado));
    this.obj.vistaCreadoEl.text(registro.fechaCreacion);
    this.obj.vistaCreadoPor.text(registro.usuarioCreacion);
    this.obj.vistaActualizadoEl.text(registro.fechaActualizacion);
    this.obj.vistaActualizadoPor.text(registro.usuarioActualizacion);
    this.obj.vistaIPCreacion.text(registro.ipCreacion);
    this.obj.vistaIPActualizacion.text(registro.ipActualizacion);
    
    this.obj.vistaCodigoSAP.text(registro.codigoSAP);
    this.obj.vistaRazonSocialSAP.text(registro.razonSocialSAP);
    this.obj.vistaRama.text(registro.ramaSAP);
  };

  moduloActual.recuperarValores = function(registro){
    var referenciaModulo=this;
    var eRegistro = {};
    try {
	    eRegistro.id = parseInt(referenciaModulo.idRegistro);
	    eRegistro.razonSocial = referenciaModulo.obj.cmpRazonSocial.val().toUpperCase();
	    eRegistro.nombreCorto = referenciaModulo.obj.cmpNombreCorto.val().toUpperCase();
	    eRegistro.numeroContrato = referenciaModulo.obj.cmpNumContrato.val().toUpperCase();
	    eRegistro.descripcionContrato = referenciaModulo.obj.cmpDesContrato.val().toUpperCase();
	    eRegistro.ruc = referenciaModulo.obj.cmpRuc.val();
	    
	    eRegistro.codigoSAP = referenciaModulo.obj.cmpCodigoSAP.val().toUpperCase();
	    eRegistro.razonSocialSAP = referenciaModulo.obj.cmpRazonSocialSAP.val().toUpperCase();
	    eRegistro.ramaSAP = referenciaModulo.obj.cmpRamaSAP.val().toUpperCase();

	    eRegistro.descripcionCS = '[';
	    moduloCanalSector.obj.tablaPrincipal.find(':input').each(function() {
	    	var inpt = $(this);
	    	console.log(inpt.attr("id"));
	    	eRegistro.descripcionCS+='{"idCanalSector":"'+inpt.attr("canSecDesc")+'","descripcionCanalSector":"'+inpt.val()+'"},';
	    });
	    eRegistro.descripcionCS += "]";
	    
	    console.log(eRegistro);
	    
    }  catch(error){
      console.log(error.message);
    }
    return eRegistro;
  };
  
  /*sincronizar*/
  moduloActual.recuperarSincronizar = function(){
	  debugger;
  	var referenciaModulo = moduloActual;
  	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
  	return $.ajax({
  	    type: constantes.PETICION_TIPO_GET,
  	    url: referenciaModulo.URL_SINCRONIZAR, 
  	    contentType: referenciaModulo.TIPO_CONTENIDO, 
  	    data: {"codigoSap":referenciaModulo.obj.cmpCodigoSAPSincronizar.val()},	
  	    success: function(respuesta) {
  	    	if (!respuesta.estado) {
  	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
  	    	} else {		 
  	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
//  	    		if (referenciaModulo.modoEdicion == constantes.MODO_ACTUALIZAR){
  	    			referenciaModulo.llenarFormularioSincronizado(respuesta.contenido.carga[0]);
  	    			referenciaModulo.desabilitarCamposSincronizados();
  	    			referenciaModulo.obj.ocultaContenedorFormulario.hide();
  	    			
//  	    		} else if (referenciaModulo.modoEdicion == constantes.MODO_VER){
//  	    			referenciaModulo.llenarDetalles(respuesta.contenido.carga[0]);
//  	    			referenciaModulo.obj.ocultaContenedorVista.hide();
//  	    		}          
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
  };
  
  moduloActual.inicializar();

  //
  /*******/
  /*sobre escrito*//*se añaden acciones aparte de las que estan en modulo_base*/
  moduloActual.obj.btnVer.on(moduloActual.NOMBRE_EVENTO_CLICK,function(){
	  //CanalSectorVista
	  moduloCanalSector.modoEdicion=constantes.MODO_LISTAR;
	  moduloCanalSector.inicializar("tablaCanalSectorVista",moduloCanalSector.URL_LISTAR);
//	  moduloCanalSector.listarRegistros();
  });
  moduloActual.obj.btnAgregar.on(moduloActual.NOMBRE_EVENTO_CLICK,function(){
	  limpiar();
	  _sincronizacion = false;
	  //configurando campos para la creacion
	  moduloActual.obj.cmpCodigoSAPSincronizar.removeAttr("disabled");
	  moduloActual.obj.cmpCodigoSAP.removeAttr("disabled");
	  moduloActual.obj.cmpRazonSocialSAP.removeAttr("disabled");
	  moduloActual.obj.cmpRazonSocial.removeAttr("disabled");
	  moduloActual.obj.cmpRamaSAP.removeAttr("disabled");
	  moduloActual.obj.cmpRuc.removeAttr("disabled");
	  
	  moduloActual.obj.btnSincronizar.removeClass("disabled");
	  moduloCanalSector.obj.datClienteApi.destroy();
  });
  moduloActual.obj.btnModificar.on(moduloActual.NOMBRE_EVENTO_CLICK,function(){
	  _sincronizacion = false;
	  //CanalSectorEdicion
	  moduloCanalSector.modoEdicion=constantes.MODO_ACTUALIZAR;
	  moduloCanalSector.inicializar("tablaCanalSectorEdicion",moduloCanalSector.URL_LISTAR);
//	  moduloCanalSector.listarRegistros();
	  
  });
  moduloActual.obj.btnCancelarGuardar.on(moduloActual.NOMBRE_EVENTO_CLICK,function(){
	  moduloCanalSector.obj.datClienteApi.destroy();
  });
  
  moduloActual.obj.btnConfirmarSincronizar.on(moduloActual.NOMBRE_EVENTO_CLICK,function(){
	  moduloActual.recuperarSincronizar().then(function(){
		//CanalSectorEdicion
		moduloCanalSector.modoEdicion=constantes.MODO_NUEVO;
        moduloCanalSector.inicializar("tablaCanalSectorEdicion",moduloCanalSector.URL_LISTAR_SINCRO);
    	moduloCanalSector.listarRegistros();
    	moduloActual.obj.modalConfirmarModificarSincronizado.modal("hide");
    	
    	_sincronizacion = true;
	  });
	});
  moduloActual.obj.btnConfirmarModificar.on(moduloActual.NOMBRE_EVENTO_CLICK,function(){
	  guardarCliente();
	  moduloActual.obj.modalConfirmarModificarSincronizado.modal("hide");
  });
  function guardarCliente(){
	//guardar una modificacion o registro
	  var referenciaModulo=moduloActual;
	  try {
	    if (referenciaModulo.modoEdicion == constantes.MODO_NUEVO){
	      referenciaModulo.guardarRegistro();
	    } else if  (referenciaModulo.modoEdicion == constantes.MODO_ACTUALIZAR){
	      referenciaModulo.actualizarRegistro();
	    }
	  } catch(error){
	    referenciaModulo.mostrarDepuracion(error.message);
	  };
  }
  /*sincronizar phf*/
  moduloActual.obj.btnSincronizar.on(moduloActual.NOMBRE_EVENTO_CLICK,function(){
	  mostrarConfirmacionSincronizar();
  });
  /*******/
  
  function mostrarConfirmacionSincronizar(){
	  moduloActual.obj.modalConfirmarModificarSincronizado.modal("show");
	  	$("#msjSincronizar").show();
		$("#msjActualizarSincronizado").hide();
	  	moduloActual.obj.btnConfirmarSincronizar.show();
	  	moduloActual.obj.btnConfirmarModificar.hide();
  }
  function limpiar(){
	  $.ajax({
	  	    type: constantes.PETICION_TIPO_GET,
	  	    url: moduloActual.URL_LIMPIAR, 
	  	    contentType: moduloActual.TIPO_CONTENIDO, 
	  	    success: function(respuesta) {
	  	    },			    		    
	  	    error: function(xhr,estado,error) {
	  	    }
	  	});
  }
});

