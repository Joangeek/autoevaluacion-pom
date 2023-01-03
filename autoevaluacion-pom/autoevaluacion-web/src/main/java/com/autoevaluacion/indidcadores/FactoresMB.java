package com.autoevaluacion.indidcadores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import autoevaluacion.TblautFactore;

import com.autoevaluacion.FactoresBO;

import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;
import util.BOException;
import gestion.Util;

@ManagedBean(name = "factoresMB")
@ViewScoped
public class FactoresMB extends BaseControlador implements Serializable {

	private static final long serialVersionUID = -8856762902531025504L;

	// EJB
	@EJB
	private FactoresBO factoresBO;

	// CONTROLADORES
	private ConfigurarMB configurarMB;

	// VARIABLES
	private List<TblautFactore> listaFactores;
	private List<TblautFactore> listaFactoresFilter;

	private TblautFactore factor;
	private TblautFactore selected;

	private String user = Util.getUserSession();

	private boolean crear;
	private boolean editar;

	@Override
	public void inicializar() {

		setFactor(new TblautFactore());
		selected = new TblautFactore();
		crear = false;
		editar = false;
	}

	@PostConstruct
	public void init() throws BOException {
		try {
			listaFactores = new ArrayList<TblautFactore>();
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
			factoresBO = LookupUtil.lookupRemoteStateless(FactoresBO.class);

			// ...
		} catch (NamingException e) {
			// ...
		}
	}

	public void onRowSelect() {
		lanzarMensajeInfo("Item seleccionado: " + selected.getNombre());
		// configurarMB.cargargrupoAspectoSeleccionado(selected);
	}

	/**
	 * Método que consulta todos los datos relacionados a la entidad
	 */
	public void cargarTodos() {
		try {
			listaFactores = new ArrayList<TblautFactore>();
			listaFactores = factoresBO.buscarTodos();
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
			factor.setUsuario(user);
			factoresBO.crear(UtilidadBean.serialize(factor));
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.creo"));
			inicializar();
			cargarTodos();
		} catch (BOException e) {
			LOGGER.info("Error al create: " + e.getMessage(), e);
		}
	}

	public boolean validarOrden() {
		boolean result = false;
		for (TblautFactore val : listaFactores) {
			if (factor.getOrden() == val.getOrden()) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * Método que edita el registro
	 */
	public void editar() {
		try {
			selected.setUsuario(user);
			factoresBO.editar(UtilidadBean.serialize(selected));
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.edito"));
			inicializar();
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
				// se setea el usuario que va a eliminar y se envía la
				// actualización del usuario selected.setUsuario(user);
				TblautFactore entity = factoresBO.editar(UtilidadBean
						.serialize(selected));
				// una ves actualixado se manda a eliminar el registro, la
				// acción se ejecuta para eveidenciar el usuario qe elimana el
				// registro
				factoresBO.eliminar(UtilidadBean.serialize(entity));
				lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
						"global.msg.exito.elimino"));
				inicializar();
				cargarTodos();
			} else {
				msgNoSeleccionado();
			}
		} catch (BOException e) {
			LOGGER.info("Error al delete: " + e.getMessage(), e);
		} catch (Exception e) {
			lanzarMensajeError(obtenerResourceBundle(COMUN).getString(
					"global.msg.error.eliminar.viola.forania.dependiente"));
		}

	}

	/**
	 * Método que edita el registro
	 */
	public void cambiarEstado() {
		try {
			if (configurarMB.isAcEstado()) {
				if (Utilidad.validaNulos(selected)) {
					selected.setUsuario(user);
					factoresBO.editarEstado(UtilidadBean.serialize(selected));
					inicializar();
					cargarTodos();
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

		crear = val;
		try {
			ejecutarUpdate("dlgNuevoFactor");
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
				editar = val;
				if (!val)
					inicializar();
				ejecutarUpdate("dlgEditarFactor");
			} else {
				editar = false;
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
		setFactor(new TblautFactore());
	}

	public void configurar() {
		if (null != selected.getOid()) {
			// Utilidad.redireccionar(CONFIGURAR);
		} else {
			msgNoSeleccionado();
		}
	}

	// SETTES AND GETTES

	public boolean isCrear() {
		return crear;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public ConfigurarMB getConfigurarMB() {
		return configurarMB;
	}

	public void setConfigurarMB(ConfigurarMB configurarMB) {
		this.configurarMB = configurarMB;
	}

	public List<TblautFactore> getListaFactores() {
		return listaFactores;
	}

	public void setListaFactores(List<TblautFactore> listaFactores) {
		this.listaFactores = listaFactores;
	}

	public List<TblautFactore> getListaFactoresFilter() {
		return listaFactoresFilter;
	}

	public void setListaFactoresFilter(List<TblautFactore> listaFactoresFilter) {
		this.listaFactoresFilter = listaFactoresFilter;
	}

	public TblautFactore getFactor() {
		return factor;
	}

	public void setFactor(TblautFactore factor) {
		this.factor = factor;
	}

	public TblautFactore getSelected() {
		return selected;
	}

	public void setSelected(TblautFactore selected) {
		this.selected = selected;
	}

}
