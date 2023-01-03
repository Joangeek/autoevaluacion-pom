package com.encuestas.configurar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;
import com.encuesta.TipoEvaluacionBO;
import util.BOException;
import gestion.Util;

import encuestas.TblencDirigidoa;
import encuestas.TblencModuloTipoEvaluacion;
import encuestas.TblencTipoEvaluacion;

@ManagedBean(name = "encuestasMB")
@ViewScoped
public class EncuestasMB extends BaseControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5501186905206614466L;
	/**
	 * 
	 */

	private static final String DATATABLE = "dataTableTE";

	/**
     * 
     */

	// EJB
	@EJB
	private TipoEvaluacionBO tipoEvaluacionBO;

	// CONTROLADORES
	private ConfigurarMB configurarMB;

	// VARIABLES
	private List<TblencTipoEvaluacion> listaEncuesta;
	private List<TblencTipoEvaluacion> listaEncuestaFilter;
	private TblencTipoEvaluacion tipoEvaluacion;
	private TblencTipoEvaluacion selected;
	private TblencTipoEvaluacion selectedClon;

	private List<TblencDirigidoa> listaDirigidoAfilter;
	private List<TblencModuloTipoEvaluacion> listaModuloFilter;
	private List<TblencDirigidoa> listaDirigidoA;
	private List<TblencModuloTipoEvaluacion> listaModulo;

	private String user = Util.getUserSession();

	private boolean crearE;
	private boolean editarE;
	private boolean estadoAC;

	@Override
	public void inicializar() {
		listaEncuesta = new ArrayList<TblencTipoEvaluacion>();
		listaDirigidoAfilter = new ArrayList<TblencDirigidoa>();
		listaModuloFilter = new ArrayList<TblencModuloTipoEvaluacion>();

		tipoEvaluacion = new TblencTipoEvaluacion();
		selected = new TblencTipoEvaluacion();
		selectedClon = new TblencTipoEvaluacion();
		crearE = false;
		editarE = false;
		cargarTodos();
	}

	@PostConstruct
	public void init() throws BOException {
		try {

			LOGGER.debug("INIT CONTROLADOR");
			// configuracionMB.setConfEncuestasMB(this);
			inicializar();
			lookup();
		} catch (Exception ex) {
			lanzarMensajeError(null, ex);
		}

	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {
			tipoEvaluacionBO = LookupUtil
					.lookupRemoteStateless(TipoEvaluacionBO.class);
			// ...
		} catch (NamingException e) {
			// ...
		}
	}

	/**
	 * Método que consulta todos los datos relacionados a la entidad
	 */
	private void cargarTodos() {
		try {
			listaEncuesta = tipoEvaluacionBO.buscarT();

			listaDirigidoAfilter = tipoEvaluacionBO.buscarDirigidoAs();
			listaModuloFilter = tipoEvaluacionBO.buscarModulosAs();

			listaDirigidoA = tipoEvaluacionBO.buscarTDirigidoA(true);
			listaModulo = tipoEvaluacionBO.buscarTModulos(true);

			ejecutarUpdate(DATATABLE);
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			LOGGER.info("Null P ct ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarTodos: " + e.getMessage(), e);
		}

	}

	/**
	 * Método que crea un nuevo registro
	 */
	public void crear() {
		try {
			tipoEvaluacion.setUsuario(user);
			tipoEvaluacionBO.crear(tipoEvaluacion);
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.creo"));
			inicializar();
			lanzarCrear(crearE);
			ejecutarUpdate(DATATABLE);
		} catch (BOException e) {
			LOGGER.info("Error al create: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que edita el registro
	 */
	public void editar() {
		try {
			validaciones();
			selected.setUsuario(user);
			tipoEvaluacionBO.editar(selected);
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.edito"));
			inicializar();
			ejecutarUpdate("dlgEditarTE");
			ejecutarUpdate(DATATABLE);
		} catch (BOException e) {
			LOGGER.info("Error al edit: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que valida las FK, en la edición si no se seleccionan nuevamente
	 * los valores vienen nulos
	 */
	private void validaciones() {
		LOGGER.info("***********++++++++++++++************* "
				+ selected.getTblenc_dirigidoa() == null);

		if (!Utilidad.validaNulos(selected.getTblenc_dirigidoa())) {
			selected.setTblenc_dirigidoa(selectedClon.getTblenc_dirigidoa());
		}

		if (!Utilidad.validaNulos(selected.getTblenc_modulo_tipo_evaluacion())) {
			selected.setTblenc_modulo_tipo_evaluacion(selectedClon
					.getTblenc_modulo_tipo_evaluacion());
		}
	}

	/**
	 * Metodo que elimina el registro
	 */
	public void eliminar() {

		try {
			selectedClon = new TblencTipoEvaluacion();
			if (Utilidad.validaNulos(selected.getOid())) {
				LOGGER.info("selected = " + selected.getNombre());
				// se setea el usuario que va a eliminar y se envía la
				// actualización del usuario selected.setUsuario(user);
				TblencTipoEvaluacion entity = tipoEvaluacionBO
						.editarR(selected);
				// una ves actualixado se manda a eliminar el registro, la
				// acción se ejecuta para eveidenciar el usuario qe elimana el
				// registro
				Integer val = tipoEvaluacionBO.eliminar(UtilidadBean
						.serialize(entity));
				lanzarValidacion(val);

				inicializar();
			} else {
				msgNoSeleccionado();
			}
		} catch (BOException e) {
			LOGGER.info("Error al delete: " + e.getMessage(), e);
			if (e.getMessage().equals("HIJOS")) {
				lanzarValidacion(1);
			}
		}

	}

	private void lanzarValidacion(Integer val) {

		if (val == 0) {
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.elimino"));
		} else if (val == 1) {
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.error.eliminar.viola.forania.dependiente"));
		}
	}

	public void onRowSelect() {
		lanzarMensajeInfo("Item seleccionado: " + selected.getNombre());
		configurarMB.cargarTipoEncuestaSeleccionada(selected);
		selectedClon = UtilidadBean.clonarObjecto(selected);
	}

	/**
	 * Método que edita el registro
	 */
	public void cambiarEstado() {

		try {

			if (estadoAC) {
				if (Utilidad.validaNulos(selected)) {
					LOGGER.info("selected = " + selected.getNombre());

					tipoEvaluacionBO.editar(selected);
					inicializar();
					lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
							"global.msg.exito.edito"));
				} else {
					msgNoSeleccionado();
				}
			} else {
				lanzarMensajeWarn(obtenerResourceBundle(COMUN).getString(
						"global.msg.cambio.estado.sin.permisos"));
			}
		} catch (NullPointerException e) {
			LOGGER.info("NullPointerException " + e.getMessage(), e);
			// lanzarMensajeError("ERROR-", e);
		} catch (Exception e) {
			lanzarMensajeError("ERROR-", e);
			LOGGER.info("Exception " + e.getMessage(), e);
		}

	}

	/**
	 * Método que permite evaluar al visivilidad del dialog(ventane emergente)
	 * de registro
	 * 
	 * @param val
	 */
	public void lanzarCrear(boolean val) {
		LOGGER.info("lanzarCrear -- " + val);
		crearE = val;
		try {
			ejecutarUpdate("dlgNuevoTE");
			reset();
		} catch (NullPointerException e) {
			LOGGER.info("Null P lc");
		}
	}

	/**
	 * Método que permite evaluar al visivilidad del dialog(ventane emergente)
	 * de edición
	 * 
	 * @param val
	 */
	public void lanzarEditar(boolean val) {
		try {
			if (null != selected.getOid()) {
				editarE = val;
				if (!val)
					inicializar();
				ejecutarUpdate("dlgEditarTE");
			} else {
				editarE = false;
				msgNoSeleccionado();
			}
		} catch (NullPointerException e) {
			LOGGER.info("Null P le", e);
		}
	}

	/**
	 * Limpiar resetear entidad
	 */
	private void reset() {
		tipoEvaluacion = new TblencTipoEvaluacion();
	}

	// SETTES AND GETTES

	public TblencTipoEvaluacion getTipoEvaluacion() {
		return tipoEvaluacion;
	}

	public boolean isCrearE() {
		return crearE;
	}

	public void setCrearE(boolean crearE) {
		this.crearE = crearE;
	}

	public boolean isEditarE() {
		return editarE;
	}

	public void setEditarE(boolean editarE) {
		this.editarE = editarE;
	}

	public void setTipoEvaluacion(TblencTipoEvaluacion tipoEvaluacion) {
		this.tipoEvaluacion = tipoEvaluacion;
	}

	public TblencTipoEvaluacion getSelected() {
		return selected;
	}

	public void setSelected(TblencTipoEvaluacion selected) {
		this.selected = selected;
	}

	public List<TblencTipoEvaluacion> getListaEncuesta() {
		return listaEncuesta;
	}

	public void setListaEncuesta(List<TblencTipoEvaluacion> listaEncuesta) {
		this.listaEncuesta = listaEncuesta;
	}

	public List<TblencTipoEvaluacion> getListaEncuestaFilter() {
		return listaEncuestaFilter;
	}

	public void setListaEncuestaFilter(
			List<TblencTipoEvaluacion> listaEncuestaFilter) {
		this.listaEncuestaFilter = listaEncuestaFilter;
	}

	public List<TblencDirigidoa> getListaDirigidoAfilter() {
		return listaDirigidoAfilter;
	}

	public void setListaDirigidoAfilter(
			List<TblencDirigidoa> listaDirigidoAfilter) {
		this.listaDirigidoAfilter = listaDirigidoAfilter;
	}

	public List<TblencModuloTipoEvaluacion> getListaModuloFilter() {
		return listaModuloFilter;
	}

	public void setListaModuloFilter(
			List<TblencModuloTipoEvaluacion> listaModuloFilter) {
		this.listaModuloFilter = listaModuloFilter;
	}

	public List<TblencDirigidoa> getListaDirigidoA() {
		return listaDirigidoA;
	}

	public void setListaDirigidoA(List<TblencDirigidoa> listaDirigidoA) {
		this.listaDirigidoA = listaDirigidoA;
	}

	public List<TblencModuloTipoEvaluacion> getListaModulo() {
		return listaModulo;
	}

	public void setListaModulo(List<TblencModuloTipoEvaluacion> listaModulo) {
		this.listaModulo = listaModulo;
	}

	public ConfigurarMB getConfigurarMB() {
		return configurarMB;
	}

	public void setConfigurarMB(ConfigurarMB configurarMB) {
		this.configurarMB = configurarMB;
	}

	public boolean isEstadoAC() {
		return estadoAC;
	}

	public void setEstadoAC(boolean estadoAC) {
		this.estadoAC = estadoAC;
	}

}
