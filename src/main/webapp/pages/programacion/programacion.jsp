<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="sgo.entidad.Cliente"%>
<%@ page import="sgo.entidad.Operacion"%>
<%@ page import="sgo.entidad.Producto"%>
<%@ page import="java.util.HashMap"%>
<%
HashMap<?,?> mapaValores = (HashMap<?,?>) request.getAttribute("mapaValores"); 
%>

<!-- Contenedor de pagina-->
<div class="content-wrapper">
  <!-- Cabecera de pagina -->
  <section class="content-header">
    <h1>Registro de Programaci&oacute;n / <small id="tituloSeccion"><%=mapaValores.get("TITULO_LISTADO_DIA_PLANIFICADO")%></small>
    </h1>
  </section>
  <!-- Contenido principal -->
  <section class="content">
  <!-- El contenido debe incluirse aqui-->
    <div class="row">
      <div class="col-md-12">
        <div id="bandaInformacion" class="callout callout-info"><%=mapaValores.get("MENSAJE_CARGANDO")%></div>
      </div>
    </div>
  <div class="row" id="cntTabla">
    <div class="col-md-12">
      <div class="box">
        <div class="box-header">
          <form id="frmBuscar" class="form" role="form"  novalidate="novalidate">
            <div class="row">
              <div class="col-md-6">
                <div class="col-md-4" style="padding-left: 4px;padding-right: 4px">
                  <label class="etiqueta-titulo-horizontal">Operación / Cliente: </label>
                </div>
                <div class="col-md-8" style="padding-left: 4px;padding-right: 4px" >
                  <select id="filtroOperacion" name="filtroOperacion" class="form-control" style="width:100%;">
                  <%
                  ArrayList<Operacion> listaOperaciones = (ArrayList<Operacion>) request.getAttribute("operaciones"); 
                  String fechaActual = (String) request.getAttribute("fechaActual"); 
                  
                  int numeroOperaciones = listaOperaciones.size();
                  int indiceOperaciones=0;
                  
                  Operacion eOperacion=null;
                  Cliente eCliente=null;
                  String seleccionado="selected='selected'";
                  for(indiceOperaciones=0; indiceOperaciones < numeroOperaciones; indiceOperaciones++){ 
                  eOperacion = listaOperaciones.get(indiceOperaciones);
                  eCliente = eOperacion.getCliente();
                  %>
                  <option <%=seleccionado%> data-fecha-actual='<%=fechaActual%>' data-volumen-promedio-cisterna='<%=eOperacion.getVolumenPromedioCisterna()%>' data-nombre-operacion='<%=eOperacion.getNombre().trim()%>' data-nombre-cliente='<%=eCliente.getNombreCorto().trim()%>' value='<%=eOperacion.getId()%>'><%=eOperacion.getNombre().trim() + " / " + eCliente.getNombreCorto().trim() %></option>
                  <%
                  seleccionado="";
                  } %>
                  </select>
                </div>
              </div>
              <div class="col-md-4">
                <div class="col-md-4" style="padding-left: 4px;padding-right: 4px">
                  <label class="etiqueta-titulo-horizontal">F. Planificada: </label>
                </div>
                <div class="col-md-8" style="padding-left: 4px;padding-right: 4px">
                <!-- se agrego text-align:center al style por req 9000003068 -->
                  <input id="filtroFechaPlanificada" type="text" style="width:100%;text-align:center" class="form-control espaciado input-sm" data-fecha-actual="<%=fechaActual%>" />
                </div>
              </div>
              <div class="col-md-2">
                  <a id="btnFiltrar" class="btn btn-default btn-sm col-md-12"><i class="fa fa-refresh"></i>  Filtrar</a>
              </div>
            </div>
          </form>
        </div>

        <div class="box-header">
          <div>
            <a id="btnDetalle" class="btn btn-default btn-sm espaciado"><i class="fa fa-search"></i>  Detalle</a>
            <a id="btnExportarCSV" class="btn btn-default espaciado btn-sm "><i class="fa fa-file-excel-o"></i> Reporte</a>
            <a id="btnNotificar" class="btn btn-default btn-sm espaciado"><i class="fa fa-envelope-square"></i> Notificar</a>
          </div>
        </div>
          <div class="box-body">
            <table id="tablaPrincipal" class="sgo-table table table-striped" width="100%">
              <thead>
                <tr>
 					<th>N#</th>
	                <th>ID</th>
	                <th>F. Planificada</th>
	                <th>F. Carga</th>
	                <th>Cis. Planificados</th>
	                <th>Cis. Programados</th>
	                <th>U. Actualizaci&oacute;n</th>
	                <th>Usuario</th>
	                <th>Estado</th>
                </tr>
              </thead>
            </table>
            
              <div id="frmConfirmarNotificar" class="modal" data-keyboard="false" tabindex="-1" data-backdrop="static">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title"><%=mapaValores.get("TITULO_VENTANA_MODAL")%></h4>							
						</div>
						<div class="modal-body">
<%-- 							<p><%=mapaValores.get("MENSAJE_NOTIFICAR_PROGRAMACION")%></p> --%>
							<table class="sgo-simple-table table table-condensed">
			                <thead>
			                <tr>
			                	<td class="celda-detalle" costyle="width:36%;" colspan="2"> <label class="etiqueta-titulo-horizontal"><%=mapaValores.get("MENSAJE_NOTIFICAR_PROGRAMACION")%></label></td>
			                	
			                </tr>
			                  <tr>
				                    <td class="celda-detalle" style="width:10%;"><label>Cliente/Operaci&oacute;n: </label></td>
				        			<td class="celda-detalle" style="width:26%;"><span id='notificarClienteOpe'>	</span></td>
			        		   </tr>
			        		   <tr>
			        		   		<td class="celda-detalle" style="width:10%;"><label>Planta Despacho: </label></td>
				        			<td class="celda-detalle" style="width:20%;"><span id='notificarPlanta'>	</span></td>
			        		   </tr>
			                   <tr>
				                    <td class="celda-detalle" style="width:10%;"><label>F. Planificada: </label></td>
				                    <td class="celda-detalle" style="width:7%;"><FONT COLOR=blue><B>	<span id='notificarFechaDescarga'>	</span></B></FONT></td>
				        		</tr>
				        		<tr>
				        			<td class="celda-detalle" style="width:10%;"><label>F. Carga: </label></td>
					                <td class="celda-detalle" style="width:7%;"><FONT COLOR=blue><B>	<span id='notificarFechaCarga'>		</span></B></FONT> </td>
			                	</tr>
			                </thead>
			              </table>
							
							
							
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default pull-left" data-dismiss="modal"><%=mapaValores.get("ETIQUETA_BOTON_CERRAR")%></button>
							<button id="btnConfirmarNotificar" type="button" class="btn btn-primary"><%=mapaValores.get("ETIQUETA_BOTON_CONFIRMAR")%></button>
						</div>
					</div>
				</div>
			</div>
            
            
            
            <div id="frmNotificar" class="modal" data-keyboard="false" tabindex="-1" data-backdrop="static">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title"><%=mapaValores.get("TITULO_VENTANA_MODAL")%></h4>
						</div>
						<div class="modal-body">						
							<form id="frmCorreo" method="post" class="form-horizontal">
								<table class="sgo-simple-table table table-condensed">
					      			<thead>
					      				<tr>
					      				<td class="celda-detalle" style="width:20%;"> <label class="etiqueta-titulo-horizontal">Para: </label></td>
					      				<td class="celda-detalle"> <input id="cmpPara" name="cmpPara" type="text"  class="form-control input-sm"/> 	</td>
					      				</tr>
					      				<tr>
					      				<td class="celda-detalle" style="width:20%;"> <label class="etiqueta-titulo-horizontal">Cc: </label> 					</td>
					      				<td class="celda-detalle"> <input id="cmpCC" name="cmpCC" type="text" class="form-control input-sm"/> </td>
					      				</tr>
					      				<tr>
					      				<td class="celda-detalle" style="width:20%;"> <label class="etiqueta-titulo-horizontal">Asunto: </label> 					</td>
					      				<td class="celda-detalle"> <input id="cmpAsunto" name="cmpAsunto" type="text" class="form-control input-sm" readonly/> </td>
					      				</tr>
					      			</thead>
					      			<tbody>      				
					      			</tbody>
				      			</table>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default pull-left" data-dismiss="modal"><%=mapaValores.get("ETIQUETA_BOTON_CANCELAR")%></button>
							<button id="btnEnviarCorreo" type="button" class="btn btn-primary"><%=mapaValores.get("MENSAJE_ENVIAR_CORREO")%></button>
						</div>
					</div>
				</div>
			</div>
            
            
          </div>
		 	<div class="overlay" id="ocultaContenedorTabla">
	            <i class="fa fa-refresh fa-spin"></i>
	        </div>           
        </div>
      </div>
    </div>

   <div class="row" id="cntDetalleProgramacion" style="display: none;">
    
     		<div class="col-md-12">
			<div class="box box-default">

				<div class="box-header with-border">
					<div class="col-xs-12">
						<table class="sgo-simple-table table table-condensed">
			                <thead>
			                  <tr>
			                    <td class="celda-detalle" style="width:10%;"><label>Cliente/Operaci&oacute;n: </label></td>
			        			<td class="celda-detalle" style="width:26%;">
<!-- 			      				se comento span y se agrego input por req 9000003068 -->
<!-- 			        			<span id='detalleClienteOpe'>	</span> -->
			        				<input id="detalleClienteOpe" type="text" class="form-control espaciado input-sm text-uppercase text-center" readonly/>
			        			</td>
			                    <td></td>
			                    <td class="celda-detalle" style="width:10%;"><label>Planta Despacho: </label></td>
			        			<td class="celda-detalle" style="width:20%;">
<!-- 			      				se comento span y se agrego input por req 9000003068 -->									
<!-- 			        			<span id='detallePlanta'>	</span> -->
									<input id="detallePlanta" type="text" class="form-control espaciado input-sm text-uppercase text-center" readonly/>
			        			</td>
			                    <td></td>
			                    <td class="celda-detalle" style="width:10%;"><label>F. Planificada: </label></td>
			                    <td class="celda-detalle" style="width:7%;"><FONT COLOR=blue><B>
<!-- 			      				se comento span y se agrego input por req 9000003068 -->			                    
<!-- 			                    <span id='detalleFechaDescarga'>	</span> -->
			                    	<input id="detalleFechaDescarga" type="text" style="color:blue;" class="form-control espaciado input-sm text-uppercase text-center" readonly/>
			                    </B></FONT></td>
			        			<td></td>
			        			<td class="celda-detalle" style="width:10%;"><label>F. Carga: </label></td>
				                <td class="celda-detalle" style="width:7%;"><FONT COLOR=blue><B>
<!-- 			      				se comento span y se agrego input por req 9000003068 -->				                
<!-- 				                <span id='detalleFechaCarga'>		</span> -->
				                	<input id="detalleFechaCarga" type="text" style="color:red;" class="form-control espaciado input-sm text-uppercase text-center" readonly/>
				                </B></FONT> </td>
			                </tr>
			                </thead>
			              </table>
					</div>
				</div>
			</div>
		</div>
     
   
		<div class="col-md-12">
			<div class="box box-default">
	   			<div class="box-header">
          			<div>
           				<a id="btnAgregarProgramacion" class="btn btn-default btn-sm espaciado"><i class="fa fa-plus"></i>  Agregar</a>
           				<a id="btnModificar" class="btn btn-default disabled btn-sm espaciado"><i class="fa fa-edit"></i>  Modificar</a>
			            <a id="btnCompletar" class="btn btn-default disabled btn-sm espaciado"><i class="fa fa-download"></i>  Completar</a>
			            <a id="btnComentar" class="btn btn-default disabled btn-sm espaciado"><i class="fa fa-edit"></i>  Comentar</a>
			            <a id="btnVer" class="btn btn-default disabled btn-sm espaciado"><i class="fa fa-search"></i>  Ver</a>
          			</div>
        		</div>
				<div class="box-body">

							<table id="tablaDetalleProgramacion" class="sgo-table table table-striped" width="100%">
			       				<thead>
			          					<tr>
										    <th>N#</th>
										    <th>ID</th>
							                <th>Transportista</th>
							                <th>Cisternas Programados</th>
							                <th>U. Actualizaci&oacute;n</th>
							                <th>Usuario</th>
							                <th>Estado</th>
			          					</tr>
			        				</thead>
       						</table>
						
							<div id="frmComentar" class="modal" data-keyboard="false" tabindex="-1" data-backdrop="static">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title"><%=mapaValores.get("TITULO_VENTANA_MODAL")%></h4>
										</div>
										<div class="modal-body">						
											<form id="frmComentario" method="post" class="form-horizontal">
												<table class="sgo-simple-table table table-condensed">
									      			<thead>
									      				<tr>
									      				<td class="celda-detalle" style="width:20%;"> <label class="etiqueta-titulo-horizontal">Comentario: </label></td>
									      				<td class="celda-detalle" colspan="5"><textarea class="form-control text-uppercase espaciado input-sm" required id="cmpComentarComentario" name="cmpComentarComentario"  placeholder="Ingrese comentario..." rows="5" resize="none"></textarea></td>
									      				</tr>
									      			</thead>
									      			<tbody>      				
									      			</tbody>
								      			</table>
											</form>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default pull-left" data-dismiss="modal"><%=mapaValores.get("ETIQUETA_BOTON_CANCELAR")%></button>
											<button id="btnGuardarComentario" type="button" class="btn btn-primary"><%=mapaValores.get("ETIQUETA_BOTON_GUARDAR")%></button>
										</div>
									</div>
								</div>
							</div>
			
				</div>
				<div class="box-footer" align="right">
					<button id="btnCerrarDetalleProgramacion" class="btn btn-sm espaciado btn-danger">Cerrar</button>
				</div>
				<div class="overlay" id="ocultaContenedorDetalleProgramacion">
			    	<i class="fa fa-refresh fa-spin"></i>
			    </div>
			</div>
		</div>
	</div>
	
	
		<div class="row" id="cntFormulario" style="display: none;">
		<div class="col-md-12">
			<div class="box box-default">
				<div class="box-header">
					<form id="frmPrincipal" class="form form-horizontal" role="form form-horizontal" novalidate="novalidate">
						<table class="sgo-simple-table table table-condensed">
			      			<thead>
			      				<tr>
			      				<td class="celda-detalle" style="width:10%;"><label>Cliente/Operaci&oacute;n:</label></td>
			      				<td class="celda-detalle" style="width:26%;" >
<!-- 			      				se comento span y se agrego input por req 9000003068 -->
<!-- 			      				<span id="cmpFormularioClienteOperacion" class="espaciado input-sm text-uppercase"> 	</span> -->
			      					<input id="cmpFormularioClienteOperacion" type="text" class="form-control espaciado input-sm text-uppercase text-left" readonly/>
			      				</td>
			      				<td></td>
			      				<td class="celda-detalle" style="width:10%;"><label>Planta Despacho:</label></td>
			      				<td class="celda-detalle" style="width:20%;">
<!-- 			      				se comento span y se agrego input por req 9000003068 -->
<!-- 			      				<span id="cmpFormularioPlantaDespacho" class="espaciado input-sm text-uppercase"></span> -->
									<input id="cmpFormularioPlantaDespacho" type="text" class="form-control espaciado input-sm text-uppercase text-left" readonly/>
			      				</td>
<!-- 			      	Comentado por 9000003068			<td></td> -->
			      				<td class="celda-detalle" style="width:10%;"><label>F. Planificada:</label> </td>
			      				<td class="celda-detalle"  style="width:7%;"><B>
<!-- 			      				se comento span y se agrego input por req 9000003068 -->			      				
<!-- 			      				<span class="form-control alert-danger text-center espaciado input-sm" id='cmpFormularioFechaDescarga'>	</span> -->
			      					<input id="cmpFormularioFechaDescarga" type="text" class="form-control alert-danger espaciado input-sm text-uppercase text-center" readonly/>
			      				</B></td>
			      				<td></td>
			      				<td class="celda-detalle" style="width:10%;"><label>F. Carga:</label> </td>
			      				<td class="celda-detalle"  style="width:7%;"><B>
<!-- 			      				se comento span y se agrego input por req 9000003068 -->			      				
<!-- 			      				<span class="form-control alert-danger text-center espaciado input-sm" id='cmpFormularioFechaCarga'></span> -->
			      					<input id="cmpFormularioFechaCarga" type="text" class="form-control alert-danger espaciado input-sm text-uppercase text-center" readonly/>
			      				</B></td>
			      				</tr>
			      				
			      				<tr> 
			      				<td class="celda-detalle" style="width:10%;"><label class="etiqueta-titulo-horizontal">Transportista:</label></td>
			      				<td  class="celda-detalle" colspan="4">
			      					<select tipo-control="select2" id="cmpTransportista" name="cmpTransportista" class="form-control espaciado text-uppercase input-sm" style="width: 100%">
										<option value="" selected="selected">Seleccionar</option>
									</select> 
								</td>
<!-- 				Comentado por 		9000003068		<td></td> -->
								<td class="celda-detalle" colspan="1"><label class="etiqueta-titulo-horizontal">Orden de compra:</label></td>
								<td class="celda-detalle" colspan="4">
									<input id="cmpFormularioOrdenCompra" name="cmpFormularioOrdenCompra" value="" maxlength="20" class="form-control input-sm text-left" />
								</td>								
			      				</tr>
			      				
			      				<tr>
			      				
<!-- 			      				inicio de cambio se elimina td class="celda-detalle", los  <td colspan="1">&nbsp;&nbsp;&nbsp;</td> y los table y se invierten los tr cambiando cisternas por volumen por req 9000003068-->

<!-- 			      				se cambia Vol. Planificados por V.Planificados por req 9000003068 -->
	      							<td><label class="etiqueta-titulo-horizontal">V.Planificados:&nbsp;</label></td>
	      							<td><input id="cmpVolumenPlanificado" name="cmpVolumenPlanificado" type="text" class="form-control input-sm text-right"></td>		
	      							<td></td>	      							

<!-- 			      				se cambia Vol. Programados por V.Programados por req 9000003068 -->
	      							<td><label class="etiqueta-titulo-horizontal"> V.Programados:&nbsp;</label></td>
	      							<td><input id="cmpVolumenProgramado" name="cmpVolumenProgramado" 
	      														readonly="readonly"
	      														type="text" 
	      														class="form-control input-sm text-right"></td>
<!-- 	      			Comentado por 	9000003068			<td></td> -->
	      														
<!-- 			      				se cambia Cisternas Planificadas por C.Planificados por req 9000003068-->
	      							<td><label class="etiqueta-titulo-horizontal">C.Planificados:&nbsp;</label></td>
	      							<td><input id="cmpCisternasPlanificadas" name="cmpCisternasPlanificadas" type="text" class="form-control input-sm text-right"></td>
	      							<td></td>

<!-- 			      				se cambia Cisternas Programados por C.Programados por req 9000003068 -->
	      							<td><label class="etiqueta-titulo-horizontal"> C.Programados:&nbsp;</label></td>
	      							<td><input id="cmpCisternasProgramadas" name="cmpCisternasProgramadas" type="text" 
	      									 	readonly="readonly"
	      									    class="form-control input-sm text-right"></td>

<!-- 			      				hasta aqui es el cambio por req 9000003068 -->
								
								
			      				</tr>
			      							      				
			      			</thead>
			      			<tbody>      				
			      			</tbody>
			      		</table>
			      		
			    <table class="sgo-simple-table table table-condensed" style="width:100%;">
      			<thead>

      				<tr>
					<td><label class="text-center">Tracto/Cisterna</label></td>
					<td><label class="text-center">Conductor</label></td>
					
					<!-- Inicio: Atención Agregado Ticket 9000002841 -->
					<td><label class="text-center">Tarjeta Cub.</label></td>
					<td><label class="text-center">Inicio Vig. TC</label></td>
					<td><label class="text-center">Fin Vig. TC</label></td>
					<!-- Fin: Atención Agregado Ticket 9000002841 -->
					
					<td><label class="text-center">Compartimento</label></td>
					
					<!-- Inicio Atencion Ticket 9000002857 -->
					<td><label class="text-center">Aforo</label></td>
					<!-- Fin Atencion Ticket 9000002857 -->
					
					<td><label class="text-center">Volumen</label></td>
      				<td><label class="text-center">Producto</label></td>
      				<td></td>
      				</tr>
      			</thead>
      			<tbody id="GrupoProgramacion">
      				<tr id="GrupoProgramacion_template">
      					<td class="celda-detalle" style="width:30%;">
      					
      							<input elemento-grupo="tracto-id" type="hidden" id="GrupoProgramacion_#index#_Cisterna_id"
      															  name="programacion[detalle][#index#][cisterna]" />
      					
								<input elemento-grupo="tracto" style="width: 100%" 
      														class="form-control input-sm text-uppercase" 
      														id="GrupoProgramacion_#index#_Cisterna"
      														name="programacion[detalle][#index#][id_cisterna]"
      														readonly="readonly" />	
      					</td>
      					<td class="celda-detalle" style="width:30%;">
      					 <select tipo-control="select2" elemento-grupo="conductor" id="GrupoProgramacion_#index#_Conductor" style="width: 100%" 
						 		name="programacion[detalle][#index#][id_conductor]" class="form-control input-sm text-uppercase">
	                    	<option value="" selected="selected">Seleccionar...</option>
	                     </select>
      					</td>
      					
      					<!-- Inicio: Atención Agregado Ticket 9000002841 -->
      					<td class="celda-detalle" style="width:10%;">
      						<input elemento-grupo="tarjetaCub" style="width: 100%" 
      														 class="form-control input-sm text-uppercase text-center" 
      														 id="GrupoProgramacion_#index#_TarjetaCub"
      														 name="programacion[detalle][#index#][tarjetaCub]"
      														 readonly="readonly" />
      														 
						<td class="celda-detalle" style="width:10%;">
      						<input elemento-grupo="fechaIniTC" style="width: 100%" 
      														 class="form-control input-sm text-uppercase text-center" 
      														 id="GrupoProgramacion_#index#_FechaIniTC"
      														 name="programacion[detalle][#index#][fechaIniTC]"
      														 readonly="readonly" />
      														 
						<td class="celda-detalle" style="width:10%;">
      						<input elemento-grupo="fechaFinTC" style="width: 100%" 
      														 class="form-control input-sm text-uppercase text-center" 
      														 id="GrupoProgramacion_#index#_FechaFinTC"
      														 name="programacion[detalle][#index#][fechaFinTC]"
      														 readonly="readonly" />
      														
      					<!-- Fin: Atención Agregado Ticket 9000002841 -->
      					
      					<td class="celda-detalle" style="width:10%;">
      						<input elemento-grupo="compartimento" style="width: 100%" 
      														 class="form-control input-sm text-uppercase text-center" 
      														 id="GrupoProgramacion_#index#_Compartimentos"
      														 name="programacion[detalle][#index#][compartimentos]"
      														 readonly="readonly" />
      					</td>
      					
      					<!-- Inicio Atencion Ticket 9000002857 -->
	      				<td class="celda-detalle">
      					 <a elemento-grupo="botonAforo" id="GrupoProgramacion_#index#_aforo" class="btn btn-default btn-sm" style="width:100%;display:inline-block"><i class="fa fa-edit"></i></a>
      					</td>      					
      					<!-- Fin Atencion Ticket 9000002857 -->
      					
      					<td class="celda-detalle" style="width:10%;">
      						<input elemento-grupo="volumen" style="width: 100%" 
      														 class="form-control input-sm text-uppercase text-right" 
      														 id="GrupoProgramacion_#index#_Volumen"
      														 name="programacion[detalle][#index#][volumen]"
      														 readonly="readonly" />
      					<td class="celda-detalle" style="width:30%;">				
      						<!-- Inicio Atencion ticket 9000002608 -->
      						<input elemento-grupo="hrandomProgramacion" id="GrupoProgramacion_#index#_Hidentificador_random" name="programacion[detalle][#index#][id_hdprogramacion]" type="hidden" class="form-control input-sm text-left" value="" />
      						<input elemento-grupo="hidentificadorVolumen" id="GrupoProgramacion_#index#_HidentificadorVolumen" name="programacion[detalle][#index#][hidentificadorVolumen]" type="hidden" class="form-control input-sm text-left" value="0" />
      						
      						<!-- Fin Atencion Ticket 9000002608 -->
      						
      						<input elemento-grupo="hidentificador" id="GrupoProgramacion_#index#_Hidentificador" name="programacion[detalle][#index#][id_dprogramacion]" type="hidden" class="form-control input-sm text-left" value="" />
      						<input elemento-grupo="hcodigoScop" id="GrupoProgramacion_#index#_HcodigoScop" name="programacion[detalle][#index#][codigoScop]" type="hidden" class="form-control input-sm text-left" value="" />
      						<input elemento-grupo="hcodigoSapPedido" id="GrupoProgramacion_#index#_HcodigoSapPedido" name="programacion[detalle][#index#][codigoSapPedido]" type="hidden" class="form-control input-sm text-left" value="" />
      						<select tipo-control="select2" elemento-grupo="producto" id="GrupoProgramacion_#index#_Producto" style="width: 100%" 
						 		name="programacion[detalle][#index#][id_producto]" class="form-control input-sm text-uppercase">
	                    		<option value="" selected="selected">Seleccionar...</option>
	                    	</select>      					
      					
      					</td>
<!--       					<td class="celda-detalle"> -->
<!-- 	      					<a id="GrupoProgramacion_remove_current" class="btn btn-default btn-sm" style="display:inline-block"><i class="fa fa-remove"></i></a> -->
<!-- 	      				</td> -->
	      				<td class="celda-detalle">
      					 <a elemento-grupo="botonElimina" id="GrupoProgramacion_#index#_elimina" class="btn btn-default btn-sm" style="width:100%;display:inline-block"><i class="fa fa-remove"></i></a>
      					</td>
      				</tr>
      				<tr id="GrupoProgramacion_noforms_template">
      				<td></td>
      				</tr>    			
      			</tbody>
      		</table>

      		  <div id="frmConfirmarModificarEstado" class="modal" data-keyboard="false" data-backdrop="static">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title"><%=mapaValores.get("TITULO_VENTANA_MODAL")%></h4>
							</div>
							<div class="modal-body">
								<p><%=mapaValores.get("MENSAJE_ELIMINAR_REGISTRO")%></p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default pull-left" data-dismiss="modal"><%=mapaValores.get("ETIQUETA_BOTON_CERRAR")%></button>
								<button id="btnConfirmarEliminarRegistro" type="button" class="btn btn-primary"><%=mapaValores.get("ETIQUETA_BOTON_CONFIRMAR")%></button>
							</div>
						</div>
					</div>
	      		</div>
			</form>
			</div>
			<div class="box-footer">
				<a id="btnGuardar" class="btn btn-primary btn-sm"><i class="fa fa-save"></i>  <%=mapaValores.get("ETIQUETA_BOTON_GUARDAR")%></a>
	            <a id="btnAgregarCisterna" class="btn bg-purple btn-sm"><i class="fa fa-plus-circle"></i>  Agregar Cisterna</a>
	            <a id="btnCancelarGuardarFormulario" class="btn btn-danger btn-sm"><i class="fa fa-close"></i>  <%=mapaValores.get("ETIQUETA_BOTON_CANCELAR")%></a>
			</div>
			<div class="overlay" id="ocultaContenedorFormulario" style="display: none;">
				<i class="fa fa-refresh fa-spin"></i>
			</div>
			</div>
		</div>
	</div>
	<!-- Completar -->
	<div class="row" id="cntFormularioCompletar" style="display: none;">
		<div class="col-md-12">
			<div class="box box-default">
				<div class="box-header">
					<form id="frmCompletar" class="form form-horizontal" role="form form-horizontal" novalidate="novalidate">
						<table class="sgo-simple-table table table-condensed">
			      			<thead>
<!-- 			      			Inicio se modifican los width de los td por req 9000003068 -->
			      				<tr>
			      				<td class="celda-detalle" style="width:10%;"><label class="etiqueta-titulo-horizontal">Cliente / Operación: </label></td>
			      				<td class="celda-detalle" style="width:26%;">
<!-- 			      				se comento span y se agrego input por req 9000003068 -->
<!-- 			      				<span id="cmpCompletarClienteOperacion" class="espaciado input-sm text-uppercase" readonly></span>  -->
									<input id="cmpCompletarClienteOperacion" type="text" class="form-control espaciado input-sm text-uppercase text-center" readonly/>
			      				</td>
			      				<td></td>
			      				<td class="celda-detalle" style="width:10%;"><label class="etiqueta-titulo-horizontal">Planta Despacho: </label></td>
			      				<td class="celda-detalle" style="width:20%;">
<!-- 			      				se comento span y se agrego input por req 9000003068 -->			      				
<!-- 			      				<span id="cmpCompletarPlantaDespacho" class="espaciado input-sm text-uppercase" readonly /> -->
			      					<input id="cmpCompletarPlantaDespacho" type="text" class="form-control espaciado input-sm text-uppercase text-center" readonly/>
			      				</td>
<!-- 			    Comentado por   	9000003068			<td></td> -->
			      				<td class="celda-detalle" style="width:10%;"><label class="etiqueta-titulo-horizontal">F. Planificada: </label> </td>
			      				<td class="celda-detalle"  style="width:7%;">
<!-- 			      				se comento span y se agrego input por req 9000003068 -->			      					
<!-- 			      				<span id="cmpCompletarFechaDescarga" type="text" class="form-control alert-danger text-center espaciado input-sm text-uppercase" readonly /> -->
			      					<input id="cmpCompletarFechaDescarga" type="text" class="form-control alert-danger espaciado input-sm text-uppercase text-center" readonly/>
			      				</td>
<!-- 			se comenta por req       9000003068				<td></td> -->
			      				<td class="celda-detalle" style="width:10%;"><label class="etiqueta-titulo-horizontal">F. Carga: </label> </td>
			      				<td class="celda-detalle"  style="width:7%;">
<!-- 			      				se comento span y se agrego input por req 9000003068 -->			      				
<!-- 			      				<span id="cmpCompletarFechaCarga" type="text" class="form-control alert-danger text-center espaciado input-sm text-uppercase" readonly /> -->
									<input id="cmpCompletarFechaCarga" type="text" class="form-control alert-danger espaciado input-sm text-uppercase text-center" readonly/>
			      				</td>
			      				</tr>
<!-- 			      			Fin se modifican los width de los td por req 9000003068 -->
			      				<tr> 
			      				<td class="celda-detalle" style="width:10%;"><label class="etiqueta-titulo-horizontal">Transportista: </label></td>
<!-- 			      			se modifican colspan a 4 -->
			      				<td colspan="4" class="celda-detalle">
									<input id="cmpCompletarTransportista" name="cmpCompletarTransportista" value="" readonly class="form-control input-sm text-left" />
								</td>
								<td class="celda-detalle" style="width:10%;"><label class="etiqueta-titulo-horizontal">Orden de compra: </label></td>
<!-- 			      			se modifican colspan a 4 -->									
								<td colspan="4" class="celda-detalle">
									<input id="cmpCompletarOrdenCompra" name="cmpCompletarOrdenCompra" class="form-control input-sm text-left" maxlength="20" value="" />
								</td>								
			      				</tr>			      				
			      			</thead>
			      			<tbody>      				
			      			</tbody>
			      		</table>
			      		
					   	
					    <table class="sgo-simple-table table table-condensed" style="width:100%;">
		      			<thead>
		      				<tr>		      				
		      				<td class="celda-cabecera-detalle"><label class="etiqueta-titulo-horizontal">Producto</label></td>
		      				<td class="celda-cabecera-detalle"><label class="etiqueta-titulo-horizontal">Tracto/Cisterna</label></td>
		      				<td class="celda-cabecera-detalle"><label class="etiqueta-titulo-horizontal">Conductor</label></td>
		      				<td class="celda-cabecera-detalle"><label class="etiqueta-titulo-horizontal">SCOP</label></td>
     						<td class="celda-cabecera-detalle"><label class="etiqueta-titulo-horizontal">Pedido</label></td>
     						<td class="celda-cabecera-detalle"><label class="etiqueta-titulo-horizontal">Compartimento</label></td>
     						<td class="celda-cabecera-detalle"><label class="etiqueta-titulo-horizontal">Volumen</label></td>
     						<td class="celda-cabecera-detalle"><label class="etiqueta-titulo-horizontal">Planta</label></td>
		      				</tr>
		      			</thead>
		      			<tbody id="GrupoCompletar">
		      				<tr id="GrupoCompletar_template"> 
		      					
		      					<td class="celda-detalle">				
		      						<input elemento-grupo="producto" id="GrupoCompletar_#index#_Producto" name="programacion[detalle][#index#][id_producto]" type="text" class="form-control input-sm text-left" readonly value="" />
		      					</td>
		      					<td class="celda-detalle">
			                    	<input elemento-grupo="cisterna" id="GrupoCompletar_#index#_Cisterna" name="programacion[detalle][#index#][id_cisterna]" type="text" class="form-control input-sm text-left" readonly value="" />
			              		</td>
		      					<td class="celda-detalle">
			                     	<input elemento-grupo="conductor" id="GrupoCompletar_#index#_Conductor" name="programacion[detalle][#index#][id_conductor]" type="text" class="form-control input-sm text-left" readonly value="" />
		      					</td>
		      					<td class="celda-detalle">
			                     	<input elemento-grupo="codigoScop" id="GrupoCompletar_#index#_CodigoScop" name="programacion[detalle][#index#][codigo_scop]" type="text" class="form-control input-sm text-left" maxlength="20" value="" />
		      					</td>
		      					<td class="celda-detalle">
			                    	<input elemento-grupo="codigoSapPedido" id="GrupoCompletar_#index#_CodigoSapPedido" name="programacion[detalle][#index#][codigo_sap_pedido]" type="text" class="form-control input-sm text-left" maxlength="20" value="" />
			              		</td>
			              		<td class="celda-detalle" style="width:10%;">
		      						<input elemento-grupo="compartimento" style="width: 100%" 
		      														 class="form-control input-sm text-uppercase text-center" 
		      														 id="GrupoProgramacion_#index#_Compartimentos"
		      														 name="programacion[detalle][#index#][compartimentos]"
		      														 readonly="readonly" />
		      					</td>
		      					<td class="celda-detalle" style="width:10%;">
		      						<input elemento-grupo="volumen" style="width: 100%" 
		      														 class="form-control input-sm text-uppercase text-right" 
		      														 id="GrupoProgramacion_#index#_Volumen"
		      														 name="programacion[detalle][#index#][volumen]"
		      														 readonly="readonly" />
      							</td>
			              		<td class="celda-detalle">
									<select tipo-control="select2" elemento-grupo="planta" id="GrupoCompletar_#index#_Planta" style="width: 100%" name="programacion[detalle][#index#][id_planta]" class="form-control input-sm text-uppercase">
										<option value="" selected="selected">Seleccionar...</option>
			                    	</select>			
		      					</td>
		      				</tr>
		      				<tr id="GrupoCompletar_noforms_template">
		      				<td></td>
		      				</tr>    			
		      			</tbody>
      		</table>
			</form>
			</div>
			<div class="box-footer col-xs-12">
				<div class="col-md-1">
					<button id="btnGuardarCompletar" type="submit" class="btn btn-primary btn-sm">Guardar</button>
				</div>
				<div class="col-md-10">
					
				</div>
				<div class="col-md-1">
					<button id="btnCancelarGuardarCompletar" class="btn btn-danger btn-sm">Cancelar</button>
				</div>
			</div>
			<div class="overlay" id="ocultaContenedorCompletar" style="display: none;">
				<i class="fa fa-refresh fa-spin"></i>
			</div>
			</div>
		</div>
	</div>
	
	<!-- Ver programacion -->
	<div class="row" id="cntVistaRegistro" style="display: none;">
    <div class="col-md-12">
		<div class="box box-default">
			<div class="box-body">
				<table id="tablaVistaCabecera" class="sgo-table table" style="width:100%;">
					<tbody>
						<tr>
							<td class="tabla-vista-titulo" style="width:15%;">Cliente/Operaci&oacute;n: </td>
							<td style="width:35%;"><span id='vistaClienteOperacion'></span></td>
							
							<td class="tabla-vista-titulo" style="width:10%;">Planta Despacho: </td>
							<td style="width:15%;"><span id='vistaPlantaDespacho'></span></td>
							<td class="tabla-vista-titulo" style="width:10%;">F. Planificada: </td>
							<td class="text-center" style="width:10%;"><span id='vistaFechaPlanificada'></span></td>
						</tr>
						<tr>							
							<td class="tabla-vista-titulo" style="width:15%;">Trasportista:</td>
							<td style="width:15%;" colspan="3"><span id='vistaTransportista'></span></td>
							<td class="tabla-vista-titulo" style="width:10%;">F. Carga </td>
							<td class="text-center" style="width:10%;"><span id='vistaFechaCarga'></span></td>
						</tr>
						<tr> 
							<td class="tabla-vista-titulo" style="width:15%;">Comentario</td>   
<!-- 							se quita cols="190" y se agrega width:100%; al style -->
							<td class="text-left"  colspan="5"><textarea  id='vistaComentario' style="resize: none;width:100%" disabled="disabled" rows="6"></textarea></td>  						
						</tr>
					</tbody>
				</table>
				<br>
		
				<table id="tablaVistaDetalle" class="sgo-table table table-striped" style="width:100%;">
      			<thead>
      				<tr>
		                <th>Pedido SAP</th>
		                <th>Orden de Compra</th>
		                <th>SCOP</th>
		                <th>Planta</th>
		                <th>Producto</th>
		                <th>Tracto/Cisterna</th>
		                
		            	<!-- Inicio: Atención Agregado Ticket 9000002841 -->
		               	<th>Tarjeta Cub.</th>
		               	<th>Inicio Vig. TC</th>
		               	<th>Fin Vig. TC</th>
						<!-- Fin: Atención Agregado Ticket 9000002841 -->
						
		                <th>Compartimento</th>
		                <th>Volumen</th>
		                <th>Conductor</th>
      				</tr>
      			</thead>			
				<tbody>					
				</tbody>
				</table>				
				
				<table id="tablaVistaPie" class="sgo-table table" style="width:100%;">
					<tbody>
						<tr>
							<td class="tabla-vista-titulo" style="width:15%;">Creado por: </td>
							<td style="width:20%;"><span id='vistaCreadoPor'></span></td>
							<td class="tabla-vista-titulo" style="width:15%;">Creado el:</td>
							<td style="width:15%;"><span id='vistaCreadoEl'></span></td>							
							<td class="tabla-vista-titulo" style="width:15%;">IP (Creaci&oacute;n): </td>
							<td style="width:15%;"><span id='vistaIpCreacion'></span></td>
						</tr>
						<tr>
							<td class="tabla-vista-titulo" style="width:15%;">Actualizado por: </td>
							<td style="width:20%;"><span id='vistaActualizadoPor'></span></td>
							<td class="tabla-vista-titulo" style="width:15%;">Actualizado el:</td>
							<td style="width:15%;"><span id='vistaActualizadoEl'></span></td>							
							<td class="tabla-vista-titulo" style="width:15%;">IP (Actualizaci&oacute;n): </td>
							<td style="width:15%;"><span id='vistaIpActualizacion'></span></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="box-footer">
				<button id="btnCerrarVista" class="btn btn-sm btn-danger"><i class="fa fa-close"></i> <%=mapaValores.get("ETIQUETA_BOTON_CERRAR")%> </button>
			</div>
		    <div class="overlay" id="ocultaContenedorVista">
		      <i class="fa fa-refresh fa-spin"></i>
		    </div>
    	</div>
    </div>
  </div>
  
  <!-- Inicio Atencion Ticket 9000002857 -->
  <div id="frmVerAforo" class="modal" data-keyboard="false" data-backdrop="static">
  	<div class="modal-dialog" style="width:1200px">
  		<div class="modal-content">
  			<div class="modal-header">
   				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Ver Aforo</h4>
        	</div>
        	<div class="modal-body">
        		<form id="frmAforo" >
       				<table align="center">
       					<tr>
       						<td width="17%">
       							<b>Tracto / Cisterna:</b>
       						</td>
       						<td width="18%">
       							<input type="text" class="form-control col-sm-1 text-center" id="txtTractoCisterna" name = "txtTractoCisterna" readonly="readonly"  >
       						</td>
       						<td width="15%">
       							&nbsp;&nbsp;&nbsp;&nbsp;<b>Cod. Cubicación:</b>
       						</td>
       						<td width="12%">
       							<input type="text" class="form-control col-sm-1 text-center" id="txtCodCubicacion" name = "txtCodCubicacion" readonly="readonly"  >
       						</td>
       						<td width="10%">
       							&nbsp;&nbsp;&nbsp;&nbsp;<b>Vigencia:</b>
       						</td>
       						<td>
       							<input type="text" class="form-control col-sm-1 text-center" id="txtVigencia" name = "txtVigencia" readonly="readonly"  >
       						</td>
       					</tr>
       					<tr>
       						<td>
       							<b>Nro. Compartimento:</b>
       						</td>
       						<td>
						        <select tipo-control="select2" id="cmbCompartimento" name="cmbCompartimento" class="form-control input-sm" style="width: 35%;">									
								</select>
								
       						</td>
       						<td>
       							&nbsp;&nbsp;&nbsp;&nbsp;<b>Cap. Volumétrica:</b>
       						</td>
       						<td>
       							<input type="text" class="form-control col-sm-1 text-center" id="txtCapVolumetrica" name = "txtCapVolumetrica" readonly="readonly"  >
       						</td>
       						<td>
       							&nbsp;&nbsp;&nbsp;&nbsp;<b>Alt. Flecha:</b>
       						</td>
       						<td>
       							<input type="text" class="form-control col-sm-1 text-center" id="txtAltFlecha" name = "txtAltFlecha" readonly="readonly"  >
       						</td>
       					</tr>
       				</table> 
       				
       				<table id="tablaDetalleAforo" class="sgo-table table table-striped" style="width:100%;">
							<thead>
								<tr>
									<th>#</th>
									<th>ID</th>
									<th>Altura</th>
									<th>Volumen</th>
									<th>Variacion (mm)</th>
									<th>Variacion (gal)</th>
								</tr>
							</thead>
						</table>
										        			
        		</form>
        	</div>
	        <div class="modal-footer">
				<button id="btnCancelarAforo" type="button" class="btn btn-default" ><%=mapaValores.get("ETIQUETA_BOTON_CANCELAR")%></button>			
			</div>
  		</div>
  	</div>
  </div>
  <!-- Fin Atencion Ticket 9000002857 -->
	
	<!-- Inicio: Atención Ticket Ticket 9000002608 - Modal -->
            <div id="frmAddCisterna" class="modal" data-keyboard="false" data-backdrop="static">
            	<div class="modal-dialog">
            		<div class="modal-content">
            			<div class="modal-header">
            				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Añadir Cisterna</h4>
            			</div>
            			<div class="modal-body">
            				<form id="frmCisterna" method="post" >
            					<div class="row">
            						<div class="col-sm-12">
            							<div class="form-group  row">
            								<label for="exampleInputEmail1" class="col-sm-2 col-form-label" >Cisterna/Tracto:</label>
            								<div class="col-sm-5">
										    	<select tipo-control="select2" id="cmbCisterna"	elemento-grupo="cisterna"
												 		name="cmbCisterna" class="form-control espaciado text-uppercase input-sm" style="width: 100%">
							                    	<option value="" selected="selected">Seleccionar...</option>
							                    </select>
										    </div>
										    <div class="col-sm-2">
										   	 	<input type="number" class="form-control col-sm-2 text-center" 
										   	 						 id="txtCompartimento" name = "txtCompartiment" readonly="readonly"  >
										    </div>
										    <label for="exampleInputEmail1" class="col-form-label col-sm-1" >Compartimento</label>
            							</div>
            							<div class="form-group  row">
										    <label for="exampleInputEmail1" class="col-sm-2 col-form-label">Conductor:</label>
										    <div class="col-sm-5">
											    <select tipo-control="select2" elemento-grupo="conductor" id="cmbConductor" style="width: 100%" 
												 		name="cmbConductor" class="form-control input-sm text-uppercase">
							                    	<option value="" selected="selected">Seleccionar...</option>
							                     </select>
						                     </div>
										  </div>
            						</div>
            					</div>  
            				</form>
            			</div>
            			<div class="modal-footer">
							<button id="btnCancelarCisterna" type="button" class="btn btn-default" ><%=mapaValores.get("ETIQUETA_BOTON_CANCELAR")%></button>
							<button id="btnAddCisterna" type="button" class="btn btn-primary">Aceptar</button>
						</div>
            		</div>
            	</div>
	            </div>
	
			<div id="frmConfirmarCisternasDuplicadas" class="modal"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title"><%=mapaValores.get("TITULO_VENTANA_MODAL")%></h4>
						</div>
						<div class="modal-body">
							<p><span class="nombre_cisterna"></span>
								con registros duplicados. ¿Está seguro que desea continuar?</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default pull-left"
								data-dismiss="modal"><%=mapaValores.get("ETIQUETA_BOTON_CERRAR")%></button>
							<button id="btnConfirmarDucplicidadRegistros" type="button"
								class="btn btn-primary"><%=mapaValores.get("ETIQUETA_BOTON_CONFIRMAR")%></button>
						</div>
					</div>
				</div>
			</div>
			<!-- Fin: Atención Ticket 9000002608 - Modal -->
	
	
	
	</section>
</div>