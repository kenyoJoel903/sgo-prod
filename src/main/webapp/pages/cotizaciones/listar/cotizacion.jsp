<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Date"%>
<%
	HashMap<?, ?> mapaValores = (HashMap<?, ?>) request.getAttribute("mapaValores");
%>

<link href="tema/app/css/cotizacion.css" rel="stylesheet" type="text/css" />  
<script src="aplicacion/modulo_cotizacion.js" type="text/javascript"></script>
<div class="content-wrapper">
	<section class="content-header">
		<h1>
			Cotizaciones / <small id="tituloSeccion"><%=mapaValores.get("TITULO_LISTADO_REGISTROS")%></small>
		</h1>
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
					<div class="box-header">
						<form id="frmBuscar" class="form-inline" role="form">
							<div class="col-md-4">
								<div class="col-md-4">
									<label for="txtFiltro" class="espaciado">Cliente: </label>
								</div>
								<div class="col-md-8">
									<%
										if (Boolean.TRUE == (Boolean) request.getAttribute("esAdminComercial")) {
									%>
									<select id="cmpFiltroCliente" name="cmpFiltroCliente"
										class="form-control input-sm"
										data-minimum-results-for-search="Infinity" style="width: 100%">
										<option value="">Todos</option>
									</select>
									<%
										}
									%>
									<%
										if (Boolean.TRUE != (Boolean) request.getAttribute("esAdminComercial")) {
									%>
									<select id="cmpFiltroCliente" name="cmpFiltroCliente"
										class="form-control input-sm"
										data-minimum-results-for-search="Infinity" style="width: 100%"
										disabled="disabled">
										<option value="<%=request.getAttribute("idCliente")%>"
											selected="selected"><%=request.getAttribute("rzCliente")%></option>
									</select>
									<%
										}
									%>

								</div>
							</div>
							<div class="col-md-4">
								<div class="col-md-4"
									style="padding-left: 4px; padding-right: 4px">
									<label class="etiqueta-titulo-horizontal">Fecha
										Cotizaci&oacute;n</label>
								</div>
								<div class="col-md-8"
									style="padding-left: 4px; padding-right: 4px">
									<input id="txtFiltroFecha" type="text" style="width: 100%"
										class="form-control input-sm" data-fecha-actual="" />
								</div>
							</div>
							<a id="btnFiltrar" class="btn btn-default btn-sm"><i
								class="fa fa-refresh"></i> <%=mapaValores.get("ETIQUETA_BOTON_FILTRAR")%></a>
						</form>
					</div>
					<div class="box-header">
						<div>
							<a id="btnAgregar" class="btn btn-default btn-sm espaciado"><i
								class="fa fa-plus"></i> <%=mapaValores.get("ETIQUETA_BOTON_AGREGAR")%></a>
							<%--               <a id="btnModificar" class="btn btn-default btn-sm disabled espaciado"><i class="fa fa-edit"></i>  <%=mapaValores.get("ETIQUETA_BOTON_MODIFICAR")%></a> --%>
							<a id="btnVer" class="btn btn-default btn-sm disabled espaciado"><i
								class="fa fa-search"></i> <%=mapaValores.get("ETIQUETA_BOTON_VER")%></a>
							<a id="btnExportar"
								class="btn btn-default btn-sm disabled espaciado"><i
								class="fa fa-search"></i> <%=mapaValores.get("ETIQUETA_BOTON_EXPORTAR")%></a>
						</div>
					</div>
					<div class="box-body">
						<table id="tablaPrincipal" class="sgo-table table table-striped"
							style="width: 100%;">
							<thead>
								<tr>
									<th>N#</th>
									<th>ID</th>
									<th>Nro Cotizaci&oacute;n</th>
									<th>Fecha Cotizaci&oacute;n</th>
									<th>Cliente</th>
									<th>Destinatario</th>
									<th>Moneda</th>
									<th>Monto</th>
								</tr>
							</thead>
						</table>
						<div id="frmConfirmarModificarEstado" class="modal"
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
										<p><%=mapaValores.get("MENSAJE_CAMBIAR_ESTADO")%></p>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default pull-left"
											data-dismiss="modal"><%=mapaValores.get("ETIQUETA_BOTON_CERRAR")%></button>
										<button id="btnConfirmarModificarEstado" type="button"
											class="btn btn-primary"><%=mapaValores.get("ETIQUETA_BOTON_CONFIRMAR")%></button>
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

						<form id="frmPrincipal">
							<div class="form-group col-sm-6 " id="cntcmpCodCliente">
								<label>Cliente</label> <input id="cmpCodClienteSapHid"
									name="cmpCodClienteSapHid" type="hidden" value="" /> <input
									id="cmpClaveRamoSapHid" name="cmpClaveRamoSapHid" type="hidden"
									value="" />

								<%
									if (Boolean.TRUE == (Boolean) request.getAttribute("esAdminComercial")) {
								%>
									<select 
										id="cmpCodCliente" 
										name="cmpCodCliente"
										class="form-control input-sm"
										data-minimum-results-for-search="Infinity" 
										style="width: 100%">
										<option value="" selected="selected">Seleccionar...</option>
									</select>
								<%
									}
								%>
								<%
									if (Boolean.TRUE != (Boolean) request.getAttribute("esAdminComercial")) {
								%>
									<select 
										id="cmpCodCliente" 
										name="cmpCodCliente"
										class="form-control input-sm"
										data-minimum-results-for-search="Infinity" 
										style="width: 100%"
										disabled="disabled">
										<option value="<%=request.getAttribute("idCliente")%>"
											selected="selected"><%=request.getAttribute("rzCliente")%>
										</option>
									</select>
								<%
									}
								%>
							</div>

							<div class="form-group col-sm-6" id="cntcmpCanalSector">
								<label>Canal / Sector</label> 
								
								<input 
									id="cmpCanalSectorHid"
									name="cmpCanalSectorHid" 
									type="hidden" value="" /> 
								<input
									id="cmpCodCanalHid" 
									name="cmpCodCanalHid" 
									type="hidden" value="" /> 
								<input 
									id="cmpCodSectorHid" 
									name="cmpCodSectorHid"
									type="hidden" value="" /> 
								<select 
									id="cmpCanalSector"
									name="cmpCanalSector" 
									class="form-control input-sm"
									data-minimum-results-for-search="Infinity" 
									style="width: 100%" required>
									<option value="" selected="selected">Seleccionar...</option>
								</select>
							</div>
							<div class="form-group col-sm-6" id="cntcmpDestinatario">
								<label>Destinatario</label> <input id="cmpCodInterlocutorSapHid"
									name="cmpCodInterlocutorSapHid" type="hidden" value="" /> <select
									id="cmpDestinatario" name="cmpDestinatario"
									class="form-control input-sm"
									data-minimum-results-for-search="Infinity" style="width: 100%"
									required>
									<option value="" selected="selected">Seleccionar...</option>
								</select>
							</div>
							<div class="form-group col-sm-6" id="cntcmpMoneda">
								<label>Moneda</label> <select id="cmpMoneda" name="cmpMoneda"
									class="form-control input-sm"
									data-minimum-results-for-search="Infinity" style="width: 100%"
									required>
									<option value="PEN" selected="selected">PEN</option>
									<option value="USD">USD</option>
								</select>
							</div>
							<div class="form-group col-sm-6" id="cntcmpFecha">
								<label>Fecha</label> <input name="cmpFecha" id="cmpFecha"
									type="text" class="form-control text-uppercase input-sm"
									maxlength="10" readonly="readonly"
									value="<%=request.getAttribute("fechaActual")%>" />
							</div>
							<div class="form-group col-sm-6" id="cntcmpNumCotizacion">
								<label>&nbsp;</label>&nbsp;
								<!--                 <label>Nº de Cotizaci&oacute;n </label> -->
								<!--                 <input name="cmpNumCotizacion" id="cmpNumCotizacion" type="text" class="form-control text-uppercase input-sm" maxlength="20" readonly="readonly"  /> -->

							</div>
							<div class="form-group col-sm-12" id="">
								<a id="btnAgregarFila"
									class="btn btn-default btn-sm disabled espaciado"> <i
									class="fa fa-edit"></i><%=mapaValores.get("ETIQUETA_BOTON_AGREGAR")%></a>
							</div>
						</form>

						<!-- Agregar detalle -->

						<div class="">

							<table class="sgo-table table-striped" style="width: 100%;"
								id="tablaDetalleProforma">
								<thead>
									<tr>
										<td><label class="text-center">Planta / Terminal</label></td>
										<td><label class="text-center">Producto </label></td>
										<td><label class="text-center">Volumen </label></td>
										<td><label class="text-center">Precio </label></td>
										<td><label class="text-center">Descuento </label></td>
										<td style="display: none;"><label
											class="text-center col_detalle_extra">Precio Neto</label></td>
										<td style="display: none;"><label
											class="text-center col_detalle_extra">Rodaje </label></td>
										<td style="display: none;"><label
											class="text-center col_detalle_extra">ISC </label></td>
										<td style="display: none;"><label
											class="text-center col_detalle_extra">Acumulado </label></td>
										<td style="display: none;"><label
											class="text-center col_detalle_extra">IGV Neto </label></td>
										<td style="display: none;"><label
											class="text-center col_detalle_extra">FISE </label></td>
										<td style="display: none;"><label
											class="text-center col_detalle_extra">Precio
												Descuento</label></td>
										<td style="display: none;"><label
											class="text-center col_detalle_extra">Precio
												Percepci&oacute;n</label></td>
										<td><label class="text-center">Importe Total</label></td>
										<td></td>
										<td></td>
									</tr>
								</thead>
								<tbody id="GrupoDetallePro">
									<tr id="GrupoDetallePro_template">
										<td class="celda-detalle" style="width: 28%;"><input
											elemento-grupo="hidentificador"
											id="GrupoDetallePro_#index#_Hidentificador"
											name="programacion[detalle][#index#][id_dprogramacion]"
											type="hidden" class="form-control input-sm text-left"
											value="" /> <input elemento-grupo="hcodigoScop"
											id="GrupoDetallePro_#index#_HcodigoScop"
											name="programacion[detalle][#index#][codigoScop]"
											type="hidden" class="form-control input-sm text-left"
											value="" /> <select tipo-control="select2"
											elemento-grupo="plantaTerminal"
											id="GrupoDetallePro_#index#_PlantaTerminal"
											style="width: 100%"
											name="programacion[detalle][#index#][id_producto]"
											class="form-control input-sm text-uppercase">
												<option value="" selected="selected">Seleccionar...</option>
										</select></td>
										<td class="celda-detalle" style="width: 28%;"><select
											tipo-control="select2" elemento-grupo="producto"
											id="GrupoDetallePro_#index#_Producto"
											data-minimum-results-for-search="Infinity"
											style="width: 100%"
											name="programacion[detalle][#index#][id_producto]"
											class="form-control input-sm text-uppercase">
												<option value="" selected="selected">Seleccionar...</option>
										</select></td>
										<td class="celda-detalle" style="width: 14%;"><input
											type="text" elemento-grupo="volumen"
											id="GrupoDetallePro_#index#_Volumen" style="width: 100%"
											name="programacion[detalle][#index#][volumen]"
											class="form-control input-sm text-uppercase" maxlength="13" />
										</td>
										<td class="celda-detalle texto-derecha"
											id="celPrecio[#index#]" elemento-grupo="precio"
											style="width: 10%;">0.0</td>
										<td class="celda-detalle texto-derecha"
											id="celDescuento[#index#]" elemento-grupo="descuento"
											style="width: 10%;">0.0</td>
										<td class="celda-detalle texto-derecha col_detalle_extra"
											id="celPrecioNeto[#index#]" elemento-grupo="precioNeto"
											style="display: none; width: 10%;">0.0</td>
										<td class="celda-detalle texto-derecha col_detalle_extra"
											id="celRodaje[#index#]" elemento-grupo="rodaje"
											style="display: none; width: 6%;">0.0</td>
										<td class="celda-detalle texto-derecha col_detalle_extra"
											id="celISC[#index#]" elemento-grupo="isc"
											style="display: none; width: 6%;">0.0</td>
										<td class="celda-detalle texto-derecha col_detalle_extra"
											id="celAcumulado[#index#]" elemento-grupo="acumulado"
											style="display: none; width: 6%;">0.0</td>
										<td class="celda-detalle texto-derecha col_detalle_extra"
											id="celIGV[#index#]" elemento-grupo="igv"
											style="display: none; width: 6%;">0.0</td>
										<td class="celda-detalle texto-derecha col_detalle_extra"
											id="celFISE[#index#]" elemento-grupo="fise"
											style="display: none; width: 6%;">0.0</td>
										<td class="celda-detalle texto-derecha col_detalle_extra"
											id="celPrecioDescuento" elemento-grupo="precioDescuento"
											style="display: none; width: 6%;">0.0</td>
										<td class="celda-detalle texto-derecha col_detalle_extra"
											id="celPrecioPercepcion" elemento-grupo="precioPercepcion"
											style="display: none; width: 6%;">0.0</td>
										<td class="celda-detalle texto-derecha" id="celImporteTotal"
											elemento-grupo="precioTotal" style="width: 10%;">0.0</td>
										<td class="celda-detalle" style="width: 21.6px;"><a
											elemento-grupo="botonVerMas"
											id="GrupoDetallePro_#index#_vermas"
											class="btn btn-default btn-sm"
											style="width: 100%; display: inline-block"><i
												class="fa fa-info-circle"></i></a></td>
										<td class="celda-detalle" style="width: 21.6px;"><a
											elemento-grupo="botonElimina"
											id="GrupoDetallePro_#index#_elimina"
											class="btn btn-default btn-sm"
											style="width: 100%; display: inline-block"><i
												class="fa fa-remove"></i></a></td>
									</tr>
									<tr id="GrupoDetallePro_noforms_template">
										<td></td>
										<td></td>
										<td></td>
										<td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<br>
						<div style="width: 100%;" class="texto-derecha">
							<label>Total</label> <input id="total" class="texto-derecha"
								style="margin-right: 20px;" readonly="readonly">
						</div>
						<br>
					</div>
					<div id="modalConfirmarModificarSincronizado" class="modal"
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
									Item Detallado
									<table class="sgo-table table table-striped"
										style="width: 100%;">
										<tbody>
											<tr>
												<td>Planta / Terminal:</td>
												<td><span id='vistaPlanta'></span></td>
											</tr>
											<tr>
												<td>Producto:</td>
												<td><span id='vistaProducto'></span></td>
											</tr>
											<tr>
												<td>Volumen:</td>
												<td class="texto-derecha"><span id="vistaVolumen"></span>
												</td>
											</tr>
											<tr>
												<td>Precio:</td>
												<td class="texto-derecha"><span id="vistaPrecio"></span>
												</td>
											</tr>
											<tr>
												<td>Descuento:</td>
												<td class="texto-derecha"><span id="vistaDescuento"></span>
												</td>
											</tr>
											<tr>
												<td>Precio Neto:</td>
												<td class="texto-derecha"><span id="vistaPrecioNeto"></span>
												</td>
											</tr>
											<tr>
												<td>Rodaje:</td>
												<td class="texto-derecha"><span id="vistaRodaje"></span>
												</td>
											</tr>
											<tr>
												<td>ISC:</td>
												<td class="texto-derecha"><span id="vistaISC"></span></td>
											</tr>
											<tr>
												<!-- ticket 9000003025 -->
												<td>Otros:</td>
												<td class="texto-derecha"><span id="vistaOtros"></span></td>
											</tr>
											<tr>
												<td>Acumulado:</td>
												<td class="texto-derecha"><span id="vistaAcumulado"></span>
												</td>
											</tr>
											<tr>
												<td>IGV Neto:</td>
												<td class="texto-derecha"><span id="vistaIgv"></span></td>
											</tr>
											<tr>
												<td>Fise:</td>
												<td class="texto-derecha"><span id="vistaFise"></span>
												</td>
											</tr>
											<tr>
												<td>Precio Descuento:</td>
												<td class="texto-derecha"><span
													id="vistaPrecioDescuento"></span></td>
											</tr>
											<tr>
												<td>Precio Percepci&oacute;n:</td>
												<td class="texto-derecha"><span
													id="vistaPrecioPercepcion"></span></td>
											</tr>
											<tr>
												<td>Importe Total:</td>
												<td class="texto-derecha"><span id="vistaImporteTotal"></span>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default pull-left"
										data-dismiss="modal"><%=mapaValores.get("ETIQUETA_BOTON_CERRAR")%></button>
								</div>
							</div>
						</div>
					</div>
					<div class="box-footer">
						<a id="btnSimular" class="btn btn-primary btn-sm"><i
							class="fa fa-save"></i> <%=mapaValores.get("ETIQUETA_BOTON_SIMULAR")%></a>
						<a id="btnGuardar" class="btn btn-primary btn-sm"><i
							class="fa fa-save"></i> <%=mapaValores.get("ETIQUETA_BOTON_GENERAR")%></a>
						<a id="btnCancelarGuardar" class="btn btn-danger btn-sm"><i
							class="fa fa-close"></i> <%=mapaValores.get("ETIQUETA_BOTON_CANCELAR")%></a>
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
					<div class="box-body">
						<table class="sgo-table table table-striped" style="width: 100%;">
							<tbody>
								<tr>
									<td>ID:</td>
									<td><span id='vistaId'></span></td>
								</tr>
								<tr>
									<td>Raz&oacute;n Social Cliente:</td>
									<td><span id='vistaRazonSocial'></span></td>
								</tr>
								<tr>
									<td>Fecha:</td>
									<td><span id="vistaFechaCotizacion"></span></td>
								</tr>
								<tr>
									<td>Canal / Sector:</td>
									<td><span id="vistaCanalSector"></span></td>
								</tr>
								<tr>
									<td>Destinatario:</td>
									<td><span id="vistaDestinatario"></span></td>
								</tr>
								<tr>
									<td>Moneda:</td>
									<td><span id="vistaMoneda"></span></td>
								</tr>
								<tr>
									<td>N&uacute;m. Cotizaci&oacute;n:</td>
									<td><span id="vistaNroCotizacion"></span></td>
								</tr>
								<tr>
									<td>Creado el:</td>
									<td><span id="vistaCreadoEl"></span></td>
								</tr>
								<!--                 <tr> -->
								<!--                   <td>Creado por:</td> -->
								<!--                   <td> -->
								<!--                     <span id="vistaCreadoPor"></span> -->
								<!--                   </td> -->
								<!--                 </tr> -->
								<tr>
									<td>IP Creaci&oacute;n:</td>
									<td><span id="vistaIPCreacion"></span></td>
								</tr>
								<!--                 <tr> -->
								<!--                   <td>Actualizado el:</td> -->
								<!--                   <td> -->
								<!--                     <span id="vistaActualizadoEl"></span> -->
								<!--                   </td> -->
								<!--                 </tr> -->
								<!--                 <tr> -->
								<!--                   <td>Actualizado por:</td> -->
								<!--                   <td> -->
								<!--                     <span id="vistaActualizadoPor"></span> -->
								<!--                   </td> -->
								<!--                 </tr> -->
								<!--                 <tr> -->
								<!--                   <td>IP Actualizaci&oacute;n:</td> -->
								<!--                   <td> -->
								<!--                     <span id="vistaIPActualizacion"></span> -->
								<!--                   </td> -->
								<!--                 </tr> -->
							</tbody>
						</table>
						<table id="tablaDetalleVista"
							class="sgo-table table table-striped" style="width: 100%;">
							<thead>
								<tr>
									<th>N#</th>
									<th>ID</th>
									<th>Planta Terminal</th>
									<th>Producto</th>
									<th>Volumen</th>
									<th>Precio</th>
									<th>Descuento</th>
									<th>Precio Neto</th>
									<th>Rodaje</th>
									<th>ISC</th>
									<th>Otros</th>
									<th>Acumulado</th>
									<th>IGV Neto</th>
									<th>FISE</th>
									<th>Precio Descuento</th>
									<th>Precio Percepci&oacute;n</th>
									<th>Importe Total</th>
								</tr>
							</thead>
						</table>
						
						<div style="width: 100%;" class="texto-derecha">
							<label>Total</label>
							<input id="vistaTotal" class="texto-derecha" readonly="readonly">
						</div>
						
					</div>
					<div class="box-footer">
						<button id="btnCerrarVista" class="btn btn-danger btn-sm"><%=mapaValores.get("ETIQUETA_BOTON_CERRAR")%></button>
						<a id="btnExportar2" class="btn btn-default btn-sm espaciado"><i
							class="fa fa-search"></i> <%=mapaValores.get("ETIQUETA_BOTON_EXPORTAR")%></a>
					</div>
					<div class="overlay" id="ocultaContenedorVista">
						<i class="fa fa-refresh fa-spin"></i>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>