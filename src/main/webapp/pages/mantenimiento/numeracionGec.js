/**
 * Agregado por req 9000003068
 */
$(document).ready(function(){
	
	var moduloActual = new moduloBase();
	
	moduloActual.urlBase='numeracionGec';
	moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
	moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
	moduloActual.URL_GUARDAR = moduloActual.urlBase + '/crear';
	moduloActual.URL_ACTUALIZAR = moduloActual.urlBase + '/actualizar';
	moduloActual.URL_ACTUALIZAR_ESTADO = moduloActual.urlBase + '/actualizarEstado';
	moduloActual.ordenGrilla=[[ 2, 'asc' ]];
	
	moduloActual.columnasGrilla.push({ "data": 'id'}); 					//Target1
	moduloActual.columnasGrilla.push({ "data": 'nombreCliente'});		//Target2
	moduloActual.columnasGrilla.push({ "data": 'nombreOperacion'});		//Target3
	moduloActual.columnasGrilla.push({ "data": 'anio'});				//Target4
	moduloActual.columnasGrilla.push({ "data": 'correlativo'});			//Target5
	moduloActual.columnasGrilla.push({ "data": 'estado'});				//Target6
	
	moduloActual.definicionColumnas.push({ "targets": 1,  "searchable": true, "orderable": true, "visible":false });
	moduloActual.definicionColumnas.push({ "targets": 2,  "searchable": true, "orderable": true, "visible":true  });
	moduloActual.definicionColumnas.push({ "targets": 3,  "searchable": true, "orderable": true, "visible":true  });
	moduloActual.definicionColumnas.push({ "targets": 4,  "searchable": true, "orderable": true, "visible":true, "className": "text-center" });
	moduloActual.definicionColumnas.push({ "targets": 5,  "searchable": true, "orderable": true, "visible":true, "className": "text-center" });
	moduloActual.definicionColumnas.push({ "targets": 6,  "searchable": true, "orderable": true, "visible":true, "render": utilitario.formatearEstado });
	
	moduloActual.grillaDespuesSeleccionar= function(indice){
		var referenciaModulo=this;
		var estadoRegistro = referenciaModulo.obj.datClienteApi.cell(indice,6).data();
		referenciaModulo.estadoRegistro=estadoRegistro;
		if (estadoRegistro == constantes.ESTADO_ACTIVO) {
			referenciaModulo.obj.btnModificarEstado.html('<i class="fa fa-cloud-download"></i> '+constantes.TITULO_DESACTIVAR_REGISTRO);			
		} else {
			referenciaModulo.obj.btnModificarEstado.html('<i class="fa fa-cloud-upload"></i> '+constantes.TITULO_ACTIVAR_REGISTRO);			
	    }
	};
	
	moduloActual.llenarDetalles = function(registro){
		this.idRegistro= registro.id;
		
		this.obj.vistaId.text(this.idRegistro);
		
	    this.obj.vistaOperacion.text(registro.idOperacion);
	    this.obj.vistaAnio.text(registro.anio);
	    this.obj.vistaNumero.text(registro.correlativo);
	    this.obj.vistaEstado.text(registro.estado);
	    
	    this.obj.vistaCreadoEl.text(registro.fechaCreacion);
	    this.obj.vistaCreadoPor.text(registro.usuarioCreacion);
	    this.obj.vistaActualizadoEl.text(registro.fechaActualizacion);
	    this.obj.vistaActualizadoPor.text(registro.usuarioActualizacion);
	    this.obj.vistaIpCreacion.text(registro.ipCreacion);
	    this.obj.vistaIpActualizacion.text(registro.ipActualizacion);
	    
	};
	
	moduloActual.resetearFormulario = function(){
		
		console.log('resetearFormularioo');
		
		var referenciaModulo=this;
		referenciaModulo.obj.frmPrincipal[0].reset();
		
		moduloActual.obj.cmbOperacion.empty();
		
		$('#cmbOperacion').on("change",function(){
			console.log('entro')
			console.log($('#cmbOperacion option:selected').text().trim());
			
			moduloActual.obj.cmpAlias.val($('#cmbOperacion option:selected').text().trim().substring(0, 3));
		});
		
	    moduloActual.obj.cmpAlias.val('');
	    moduloActual.obj.cmpAnio.val('');
	    moduloActual.obj.cmpNumero.val('0');
	    
	    this.obj.cmbOperacion.prop('disabled', false);
	    this.obj.cmpAnio.prop('disabled', false);
	    
	    $.ajax({
		    type: constantes.PETICION_TIPO_GET,
		    url: "./operacion/listar", 
		    dataType: 'json',
		    data: { filtroEstado: 1,
	    			paginacion: 0
	    		  },
		    success: function (respuesta) {
		    	if(respuesta.contenido.carga.length > 0){
		    		$('#cmbOperacion').append("<option value="+ 0 +">SELECCIONAR...</option>");
		    		for(var cont = 0; cont < respuesta.contenido.carga.length; cont++){
		    			var registro = respuesta.contenido.carga[cont];
		    			$('#cmbOperacion').append("<option value="+ registro.id +"> " + registro.nombre + "</option>");
    		    	}
		    	} else {
		    		var elemento2 =constantes.PLANTILLA_OPCION_SELECTBOX;
		    	    elemento2 = elemento2.replace(constantes.ID_OPCION_CONTENEDOR, -1);
		    	    elemento2 = elemento2.replace(constantes.VALOR_OPCION_CONTENEDOR, "SELECCIONAR...");
		  	        moduloActual.obj.cmbOperacion.empty().append(elemento2).val(-1).trigger('change');
		  	        $('#cmbOperacion').find("option:selected").val(-1);
		    	    moduloActual.obj.cmbOperacion.val(-1);
		    	}
		    },			    		    
		    error: function(xhr,estado,error) {
	        referenciaModulo.mostrarErrorServidor(xhr,estado,error);        
		    }
		});
	    
	    
		
	};
	
	moduloActual.reglasValidacionFormulario={ 		
		cmpAlias:		{ required: true, maxlength: 3,},
		cmpAnio:		{ required: true, maxlength: 4,},
		cmpNumero:		{ required: true, maxlength: 5,}
	};
		
	moduloActual.mensajesValidacionFormulario={		
		cmpAlias:		{ required: "El campo Alias de la Operación es un dato obligatorio", maxlength: "El campo Alias de la Operación debe contener 3 caracteres como máximo." },
		cmpAnio:		{ required: "El campo Año es un dato obligatorio", maxlength: "El campo Año debe contener 4 dígitos como máximo." },
		cmpNumero:		{ required: "El campo Número es un dato obligatorio", maxlength: "El campo Número debe contener 5 dígitos como máximo." }
	};
	
	moduloActual.inicializarCampos= function(){
		
		this.obj.vistaId		=	$("#vistaId");
		
		this.obj.vistaOperacion	=	$("#vistaOperacion");
		this.obj.vistaAnio		=	$("#vistaAnio");
		this.obj.vistaNumero	=	$("#vistaNumero");
		this.obj.vistaEstado	=	$("#vistaEstado");
		
		this.obj.vistaCreadoEl			=	$("#vistaCreadoEl");
		this.obj.vistaCreadoPor			=	$("#vistaCreadoPor");
		this.obj.vistaActualizadoPor	=	$("#vistaActualizadoPor");
		this.obj.vistaActualizadoEl		=	$("#vistaActualizadoEl");
		this.obj.vistaIpCreacion		=	$("#vistaIpCreacion");
		this.obj.vistaIpActualizacion	=	$("#vistaIpActualizacion");
		
		this.obj.cmbOperacion	=	$("#cmbOperacion");
		this.obj.cmpAlias		=	$("#cmpAlias");
		this.obj.cmpAnio		=	$("#cmpAnio");
		this.obj.cmpAnio.inputmask('integer');
		
		this.obj.cmpNumero		=	$("#cmpNumero");
		this.obj.cmpNumero.inputmask('integer');
		
		this.obj.btnGuardarNumeracionGec	=	$("#btnGuardarNumeracionGec");		
		
		moduloActual.guardarNumeracionGec = function(){
			
			try {
				var mensajeError = moduloActual.validarFormNumeracionGec();
		        
				if (mensajeError == '') {
					moduloActual.iniciarGuardar();
				} else {
					moduloActual.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR, mensajeError);
				}
			} catch (error) {
				moduloActual.mostrarDepuracion(error.message);
				moduloActual.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR, error.message);
			};
			
		};
		
		moduloActual.obj.btnGuardarNumeracionGec.on(constantes.NOMBRE_EVENTO_CLICK, function() {
			
			moduloActual.guardarNumeracionGec();			 

		});
		
		moduloActual.validarFormNumeracionGec = function() {
			
			referenciaModulo = this;
			
			var idOperacion = $("#cmbOperacion").val();
			
			if(idOperacion == 0) return "Debe ingresar la operación";
			
			var alias = $("#cmpAlias").val();
			
			if(alias == '') return "El alias de la operación es un dato obligatorio";
			
			var anio = $("#cmpAnio").val();
			
			if(anio == '') return "El año es un dato obligatorio";

			var numero = $("#cmpNumero").val();
			
			if(numero == '') return "El número es un dato obligatorio";
			
			
			return "";
			
		};
		
	};
	
	moduloActual.recuperarValores = function(registro){
		
		 var eRegistro = {};
		 var referenciaModulo=this;
		 
		 try {
			 eRegistro.id = parseInt(referenciaModulo.idRegistro);
		     
			 eRegistro.idOperacion 		=  	parseInt(referenciaModulo.obj.cmbOperacion.val());
		     eRegistro.aliasOperacion 	= 	referenciaModulo.obj.cmpAlias.val();
		     eRegistro.anio 			= 	parseInt(referenciaModulo.obj.cmpAnio.val());
		     eRegistro.correlativo 		= 	parseInt(referenciaModulo.obj.cmpNumero.val());
		     

			 console.log(eRegistro);
		 } catch(error){
			 referenciaModulo.mostrarDepuracion(error.message);
		 }
		 return eRegistro;
	};
	
	moduloActual.llenarFormulario = function(registro){
		
		console.log('llenarFormulario');
		
		 this.idRegistro= registro.id;
		
		 var idOperacion = registro.idOperacion;
		 
		 console.log(idOperacion);
		 console.log(registro.nombreOperacion);
		 
		 var elemento2 =constantes.PLANTILLA_OPCION_SELECTBOX;
 	     elemento2 = elemento2.replace(constantes.ID_OPCION_CONTENEDOR, idOperacion);
 	     elemento2 = elemento2.replace(constantes.VALOR_OPCION_CONTENEDOR, registro.nombreOperacion); 	    
 	     this.obj.cmbOperacion.empty().append(elemento2).val(idOperacion).trigger('change');
	     $('#cmbOperacion').find("option:selected").val(idOperacion);
	     $('#cmbOperacion').val(idOperacion);
		 
		 this.obj.cmbOperacion.prop('disabled', true);
		 
		 this.obj.cmpAlias.val(registro.aliasOperacion);
		 
		 this.obj.cmpAnio.val(registro.anio);
		 this.obj.cmpAnio.prop('disabled', true);
		 
		 this.obj.cmpNumero.val(registro.correlativo);

	};
	
	moduloActual.inicializar();
});