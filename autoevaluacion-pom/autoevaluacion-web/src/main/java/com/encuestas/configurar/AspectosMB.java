package com.encuestas.configurar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import util.BOException;
import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;



import com.encuesta.AspectoBO;
import com.encuesta.TipoAspectoBO;

import encuestas.TblencAspecto;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencTipoAspecto;
import gestion.Util;

@ManagedBean(name = "aspectoMB")
@ViewScoped
public class AspectosMB extends BaseControlador implements Serializable {

	private static final long serialVersionUID = 8179324389594802270L;
	private static final String DATATABLE = "dataTableConAs";

	// EJB
	@EJB
	private AspectoBO aspectoBO;
	@EJB
	private TipoAspectoBO tipoAspectoBO;

	// CONTROLADORES
	private ConfigurarMB configurarMB;

	// VARIABLES
	private TblencGrupoAspecto grupoAspectoSelected;
	private List<TblencAspecto> listaAspectos;
	private List<TblencAspecto> listaAspectosFilter;
	private List<TblencTipoAspecto> listaTipoAspectofilter;
	private List<TblencTipoAspecto> listaTipoAspecto;

	private TblencAspecto aspecto;
	private TblencAspecto selected;
	private TblencAspecto selectedClon;

	private String user = Util.getUserSession();

	private boolean crearAS;
	private boolean editarAS;

	@Override
	public void inicializar() {
		listaAspectos = new ArrayList<TblencAspecto>();
		aspecto = new TblencAspecto();
		selected = new TblencAspecto();
		selectedClon = new TblencAspecto();
		crearAS = false;
		editarAS = false;

	}

	@PostConstruct
	public void init() throws BOException {
		try {
			LOGGER.debug("INIT CONTROLADOR");
			inicializar();
			lookup();
			// cargarTodos();

		} catch (Exception ex) {
			lanzarMensajeError(null, ex);
		}

	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {
			aspectoBO = LookupUtil.lookupRemoteStateless(AspectoBO.class);
			// ...
		} catch (NamingException e) {
			// ...
		}
	}

	/**
	 * Método que consulta todos los datos relacionados a la entidad
	 */
	public void cargarTodos() {
		try {
			listaTipoAspectofilter = aspectoBO
					.buscarTipoAspectosFiltro(UtilidadBean
							.serialize(grupoAspectoSelected));
			listaAspectos = aspectoBO.buscarPorGrupoAs(UtilidadBean
					.serialize(grupoAspectoSelected));
			ejecutarUpdate(DATATABLE);
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			LOGGER.info("Null P Conf ", e);
		} catch (Exception e) {
			LOGGER.info("Error al cargarTodos: " + e.getMessage(), e);
		}

	}

	public void cargarTipoAspectos() {
		listaTipoAspecto = new ArrayList<TblencTipoAspecto>();
		try {
			listaTipoAspecto = tipoAspectoBO.buscarTodos(true);
		} catch (Exception e) {
			LOGGER.info("Exception -- cargarTipoAspectos()");
		}

	}

	/**
	 * Método que crea un nuevo registro
	 */
	public void crear() {
		try {
			aspecto.setUsuario(user);
			aspecto.setTblencGrupoAspecto(grupoAspectoSelected);
			aspectoBO.crear(UtilidadBean.serialize(aspecto));
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.creo"));
			inicializar();
			cargarTodos();
			lanzarCrear(crearAS);
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
			aspectoBO.editar(UtilidadBean.serialize(selected));
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.edito"));
			inicializar();
			cargarTodos();
			ejecutarUpdate("dlgEditarTE");
			ejecutarUpdate(DATATABLE);
		} catch (BOException e) {
			LOGGER.info("Error al edit: " + e.getMessage(), e);
		}
	}

	/**
	 * Metodo que elimina el registro
	 */
	public void eliminar() {

		try {
			if (Utilidad.validaNulos(selected.getOid())) {
				LOGGER.info("selected = " + selected.getDescripcion());
				// se setea el usuario que va a eliminar y se envía la
				// actualización del usuario selected.setUsuario(user);
				TblencAspecto entity = aspectoBO.editarR(UtilidadBean
						.serialize(selected));
				// una ves actualixado se manda a eliminar el registro, la
				// acción se ejecuta para eveidenciar el usuario qe elimana el
				// registro
				aspectoBO.eliminar(UtilidadBean.serialize(entity));
				lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
						"global.msg.exito.elimino"));
				inicializar();
				cargarTodos();
			} else {
				msgNoSeleccionado();
			}
		} catch (BOException e) {
			LOGGER.info("Error al delete: " + e.getMessage(), e);
		}

	}

	/**
	 * Método que edita el registro
	 */
	public void cambiarEstado() {

		try {

			if (configurarMB.isAcEstado()) {
				if (Utilidad.validaNulos(selected)) {
					LOGGER.info("selected = " + selected.getDescripcion());
					if (selected.getEstado() == 1) {
						selected.setEstado(0);
					} else {
						selected.setEstado(1);
					}
					aspectoBO.editar(UtilidadBean.serialize(selected));
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

	public void onRowSelect() {
		lanzarMensajeInfo("Item seleccionado: " + selected.getDescripcion());
		selectedClon = UtilidadBean.clonarObjecto(selected);
	}

	/**
	 * Método que permite evaluar al visivilidad del dialog(ventane emergente)
	 * de registro
	 * 
	 * @param val
	 */
	public void lanzarCrear(boolean val) {
		LOGGER.info("lanzarCrear " + val);
		crearAS = val;
		try {
			ejecutarUpdate("dlgNuevoAS");
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

				editarAS = val;
				if (!val)
					inicializar();
				ejecutarUpdate("dlgEditarGA");
			} else {
				editarAS = false;
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
		aspecto = new TblencAspecto();
	}

	public void configurar() {
		if (null != selected.getOid()) {
			// Utilidad.redireccionar(CONFIGURAR);
		} else {
			msgNoSeleccionado();
		}
	}

	/**
	 * Método que valida las FK, en la edición si no se seleccionan nuevamente
	 * los valores vienen nulos
	 */
	private void validaciones() {
		if (!Utilidad.validaNulos(selected.getTblencGrupoAspecto())) {
			selected.setTblencGrupoAspecto(selectedClon.getTblencGrupoAspecto());
		}

		if (!Utilidad.validaNulos(selected.getTblencTipoAspecto())) {
			selected.setTblencTipoAspecto(selectedClon.getTblencTipoAspecto());
		}
	}

	// SETTES AND GETTES

	public ConfigurarMB getConfigurarMB() {
		return configurarMB;
	}

	public boolean isCrearAS() {
		return crearAS;
	}

	public void setCrearAS(boolean crearAS) {
		this.crearAS = crearAS;
	}

	public boolean isEditarAS() {
		return editarAS;
	}

	public void setEditarAS(boolean editarAS) {
		this.editarAS = editarAS;
	}

	public void setConfigurarMB(ConfigurarMB configurarMB) {
		this.configurarMB = configurarMB;
	}

	public TblencGrupoAspecto getGrupoAspectoSelected() {
		return grupoAspectoSelected;
	}

	public void setGrupoAspectoSelected(TblencGrupoAspecto grupoAspectoSelected) {
		this.grupoAspectoSelected = grupoAspectoSelected;
	}

	public List<TblencAspecto> getListaAspectos() {
		return listaAspectos;
	}

	public void setListaAspectos(List<TblencAspecto> listaAspectos) {
		this.listaAspectos = listaAspectos;
	}

	public List<TblencAspecto> getListaAspectosFilter() {
		return listaAspectosFilter;
	}

	public void setListaAspectosFilter(List<TblencAspecto> listaAspectosFilter) {
		this.listaAspectosFilter = listaAspectosFilter;
	}

	public TblencAspecto getAspecto() {
		return aspecto;
	}

	public void setAspecto(TblencAspecto aspecto) {
		this.aspecto = aspecto;
	}

	public TblencAspecto getSelected() {
		return selected;
	}

	public void setSelected(TblencAspecto selected) {
		this.selected = selected;
	}

	public List<TblencTipoAspecto> getListaTipoAspectofilter() {
		return listaTipoAspectofilter;
	}

	public void setListaTipoAspectofilter(
			List<TblencTipoAspecto> listaTipoAspectofilter) {
		this.listaTipoAspectofilter = listaTipoAspectofilter;
	}

	public List<TblencTipoAspecto> getListaTipoAspecto() {
		return listaTipoAspecto;
	}

	public void setListaTipoAspecto(List<TblencTipoAspecto> listaTipoAspecto) {
		this.listaTipoAspecto = listaTipoAspecto;
	}

}
