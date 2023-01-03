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
import com.encuesta.GrupoAspectoBO;
import util.BOException;
import gestion.Util;

import encuestas.TblencGrupoAspecto;
import encuestas.TblencTipoEvaluacion;

@ManagedBean(name = "grupoAspectoMB")
@ViewScoped
public class GrupoAspectosMB extends BaseControlador implements Serializable {

	private static final long serialVersionUID = 8179324389594802270L;
	private static final String DATATABLE = "dataTableConF";

	// EJB
	@EJB
	private GrupoAspectoBO grupoAspectoBO;

	// CONTROLADORES
	private ConfigurarMB configurarMB;

	// VARIABLES
	private TblencTipoEvaluacion tipoEvaluacionSelected;
	private List<TblencGrupoAspecto> listaGrupoAspecto;
	private List<TblencGrupoAspecto> listaGrupoAspectoFilter;

	private TblencGrupoAspecto grupoAspecto;
	private TblencGrupoAspecto selected;

	private String user = Util.getUserSession();

	private boolean crearG;
	private boolean editarG;

	@Override
	public void inicializar() {

		grupoAspecto = new TblencGrupoAspecto();
		selected = new TblencGrupoAspecto();
		crearG = false;
		editarG = false;
	}

	@PostConstruct
	public void init() throws BOException {
		try {
			listaGrupoAspecto = new ArrayList<TblencGrupoAspecto>();
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
			grupoAspectoBO = LookupUtil
					.lookupRemoteStateless(GrupoAspectoBO.class);

			// ...
		} catch (NamingException e) {
			// ...
		}
	}

	public void onRowSelect() {
		lanzarMensajeInfo("Item seleccionado: " + selected.getNombre());
		configurarMB.cargargrupoAspectoSeleccionado(selected);
	}

	/**
	 * Método que consulta todos los datos relacionados a la entidad
	 */
	public void cargarTodos() {
		try {
			listaGrupoAspecto = new ArrayList<TblencGrupoAspecto>();
			listaGrupoAspecto = grupoAspectoBO
					.buscarPorTipoEval(tipoEvaluacionSelected);
			ejecutarUpdate(DATATABLE);
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			LOGGER.info("Null P Conf ", e);
		} catch (Exception e) {
			LOGGER.info("Error al cargarTodos: " + e.getMessage(), e);
		}

	}

	/**
	 * Método que crea un nuevo registro
	 */
	public void crear() {
		try {
			grupoAspecto.setUsuario(user);
			grupoAspecto.setTblencTipoEvaluacion(getTipoEvaluacionSelected());
			grupoAspectoBO.crear(grupoAspecto);
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.creo"));
			inicializar();
			cargarTodos();
		} catch (BOException e) {
			LOGGER.info("Error al create: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que edita el registro
	 */
	public void editar() {
		try {
			selected.setUsuario(user);
			grupoAspectoBO.editar(UtilidadBean.serialize(selected));
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.edito"));
			inicializar();
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
				LOGGER.info("selected = " + selected.getNombre());
				// se setea el usuario que va a eliminar y se envía la
				// actualización del usuario selected.setUsuario(user);
				TblencGrupoAspecto entity = grupoAspectoBO.editar(UtilidadBean.serialize(selected));
				// una ves actualixado se manda a eliminar el registro, la
				// acción se ejecuta para eveidenciar el usuario qe elimana el
				// registro
				LOGGER.info("selectedxxx");
				grupoAspectoBO.eliminar(UtilidadBean.serialize(entity));
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
	 * Método que permite evaluar al visivilidad del dialog(ventane emergente)
	 * de registro
	 * 
	 * @param val
	 */
	public void lanzarCrear(boolean val) {
		LOGGER.info("lanzarCrear " + val);
		crearG = val;
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
				editarG = val;
				if (!val)
					inicializar();
				ejecutarUpdate("dlgEditarTE");
			} else {
				editarG = false;
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
		grupoAspecto = new TblencGrupoAspecto();
	}

	public void configurar() {
		if (null != selected.getOid()) {
			// Utilidad.redireccionar(CONFIGURAR);
		} else {
			msgNoSeleccionado();
		}
	}

	// SETTES AND GETTES

	public TblencGrupoAspecto getSelected() {
		return selected;
	}

	public boolean isCrearG() {
		return crearG;
	}

	public void setCrearG(boolean crearG) {
		this.crearG = crearG;
	}

	public boolean isEditarG() {
		return editarG;
	}

	public void setEditarG(boolean editarG) {
		this.editarG = editarG;
	}

	public void setSelected(TblencGrupoAspecto selected) {
		this.selected = selected;
	}

	public List<TblencGrupoAspecto> getListaGrupoAspecto() {
		return listaGrupoAspecto;
	}

	public void setListaGrupoAspecto(List<TblencGrupoAspecto> listaGrupoAspecto) {
		this.listaGrupoAspecto = listaGrupoAspecto;
	}

	public List<TblencGrupoAspecto> getListaGrupoAspectoFilter() {
		return listaGrupoAspectoFilter;
	}

	public void setListaGrupoAspectoFilter(
			List<TblencGrupoAspecto> listaGrupoAspectoFilter) {
		this.listaGrupoAspectoFilter = listaGrupoAspectoFilter;
	}

	public TblencGrupoAspecto getGrupoAspecto() {
		return grupoAspecto;
	}

	public void setGrupoAspecto(TblencGrupoAspecto grupoAspecto) {
		this.grupoAspecto = grupoAspecto;
	}

	public TblencTipoEvaluacion getTipoEvaluacionSelected() {
		return tipoEvaluacionSelected;
	}

	public void setTipoEvaluacionSelected(
			TblencTipoEvaluacion tipoEvaluacionSelected) {
		this.tipoEvaluacionSelected = tipoEvaluacionSelected;
	}

	public ConfigurarMB getConfigurarMB() {
		return configurarMB;
	}

	public void setConfigurarMB(ConfigurarMB configurarMB) {
		this.configurarMB = configurarMB;
	}

}
