<!-- Agregado por req 9000002857-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.HashMap"%>
<% HashMap<?,?> mapaValores = (HashMap<?,?>) request.getAttribute("mapaValores"); %>

<!-- Contenedor de pagina-->
<div class="content-wrapper">
	<section class="content-header">
		<h1> Numeracion Gec / <small id="tituloSeccion"><%=mapaValores.get("TITULO_LISTADO_REGISTROS")%></small></h1>
	</section>
	
	<section class="content" id="cntInterface">
		<div class="row">
			<div class="col-xs-12">
				<div id="bandaInformacion" class="callout callout-success">
					<%=mapaValores.get("MENSAJE_CARGANDO")%>
				</div>
			</div>
		</div>
		<div class="row" id="cntTabla">
			<div class="col-xs-12">
				<div class="box">
					<!-- BOTONES PARA EL MANTENIMIENTO -->
					<div class="box-header">
						<div>
						  <a id="btnAgregar" class="btn btn-default btn-sm espaciado"><i class="fa fa-plus"></i>  <%=mapaValores.get("ETIQUETA_BOTON_AGREGAR")%></a>
			              <a id="btnModificar" class="btn btn-default btn-sm disabled espaciado"><i class="fa fa-edit"></i>  <%=mapaValores.get("ETIQUETA_BOTON_MODIFICAR")%></a>
			              <a id="btnModificarEstado" class="btn btn-default btn-sm disabled espaciado"><i class="fa fa-cloud-upload"></i>  <%=mapaValores.get("ETIQUETA_BOTON_ACTIVAR")%></a>
			              <a id="btnVer" class="btn btn-default btn-sm disabled espaciado"><i class="fa fa-search"></i>  <%=mapaValores.get("ETIQUETA_BOTON_VER")%></a>
						</div>
					</div>
					<div class="box-body">
						<table id="tablaPrincipal" class="sgo-table table table-striped" style="width:100%;">
							<thead>
								<tr>
									<th>#</th>
									<th>ID</th>
									<th>Cliente</th>
									<th>Operación</th>
									<th>Año</th>
									<th>Número</th>
									<th>Estado</th>
								</tr>
							</thead>
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
										<p><%=mapaValores.get("MENSAJE_CAMBIAR_ESTADO")%></p>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default pull-left" data-dismiss="modal"><%=mapaValores.get("ETIQUETA_BOTON_CERRAR")%></button>
										<button id="btnConfirmarModificarEstado" type="button" class="btn btn-primary"><%=mapaValores.get("ETIQUETA_BOTON_CONFIRMAR")%></button>
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
		<div class="row" id="cntFormulario" style="display: none;">
			<div class="col-md-12">
				<div class="box box-default">
				
					<div class="box-body">
						<form id="frmPrincipal" role="form">
							<div class="form-group">
								<label  class="control-label">Operación</label> 
								<select tipo-control="select2" id="cmbOperacion" name="cmbOperacion" class="form-control input-sm" style="width: 100%;">									
								</select>								
							</div>	
							<div class="form-group">
								<label  class="control-label">Alias Operación</label> 
								<input name="cmpAlias" id="cmpAlias" type="text" class="form-control text-uppercase input-sm" maxlength="3" required placeholder="Alias de Operación" />
							</div>	
							<div class="form-group">
								<label  class="control-label">Año</label> 
								<input name="cmpAnio" id="cmpAnio" type="text" class="form-control text-uppercase input-sm" maxlength="4" required placeholder="Año" />
							</div>	
							<div class="form-group">
								<label  class="control-label">Número</label> 
								<input name="cmpNumero" id="cmpNumero" type="text" class="form-control text-uppercase input-sm" maxlength="5" required placeholder="Número" value="0" />
							</div>	
						</form>
					</div>
				
					<div class="box-footer">
						<a id="btnGuardarNumeracionGec" class="btn btn-primary btn-sm"><i class="fa fa-save"></i>  <%=mapaValores.get("ETIQUETA_BOTON_GUARDAR")%></a>			            
			            <a id="btnCancelarGuardar" class="btn btn-danger btn-sm"><i class="fa fa-close"></i>  <%=mapaValores.get("ETIQUETA_BOTON_CANCELAR")%></a>
					</div>
					
					
					<div class="overlay" id="ocultaContenedorFormulario">
						<i class="fa fa-refresh fa-spin"></i>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row" id="cntVistaRegistro" style="display: none;">
			<div class="col-md-12">
				<div class="box box-default">
					<div class="box-header with-border">
						<h3 class="box-title">Detalle del registro</h3>
					</div>
					<div class="box-body">
						<table class="sgo-table table table-striped" style="width:100%;">
							<tbody>
								<tr>
									<td>ID:</td>
									<td><span id='vistaId'></span></td>
								</tr>
								<tr>
									<td>Operación:</td>
									<td><span id='vistaOperacion'></span></td>
								</tr>
								<tr>
									<td>Año:</td>
									<td><span id='vistaAnio'></span></td>
								</tr>
								<tr>
									<td>Número:</td>
									<td><span id='vistaNumero'></span></td>
								</tr>
								<tr>
									<td>Estado:</td>
									<td><span id='vistaEstado'></span></td>
								</tr>
								<tr>
									<td>Creado el:</td>
									<td><span id='vistaCreadoEl'></span></td>
								</tr>
								<tr>
									<td>Creado por:</td>
									<td><span id='vistaCreadoPor'></span></td>
								</tr>
								<tr>
									<td>Actualizado por:</td>
									<td><span id='vistaActualizadoPor'></span></td>
								</tr>
								<tr>
									<td>Actualizado El:</td>
									<td><span id='vistaActualizadoEl'></span></td>
								</tr>
								<tr>
									<td>IP (Creación):</td>
									<td><span id='vistaIpCreacion'></span></td>
								</tr>
								<tr>
									<td>IP (Actualizacion):</td>
									<td><span id='vistaIpActualizacion'></span></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="box-footer">
						<button id="btnCerrarVista"  class="btn btn-danger btn-sm"><%=mapaValores.get("ETIQUETA_BOTON_CERRAR")%></button>
					</div>
					<div class="overlay" id="ocultaContenedorVista">
			            <i class="fa fa-refresh fa-spin"></i>
			        </div>
				</div>
			</div>
		</div>
	</section>
</div>