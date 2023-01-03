package com.autoevaluacion.indidcadores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import autoevaluacion.TblautCaracteristica;
import autoevaluacion.TblautIndicadoresCaracteristica;

import com.autoevaluacion.IndicadoresBO;
import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;
import util.BOException;
import gestion.Util;

@ManagedBean(name = "indicadoresMB")
@ViewScoped
public class IndicadoresMB extends BaseControlador implements Serializable {

	private static final long serialVersionUID = -8571460150461701004L;

	// EJB
	@EJB
	private IndicadoresBO indicadoresBO;

	// CONTROLADORES
	private ConfigurarMB configurarMB;

	// VARIABLES
	private TblautCaracteristica caracteristicaSelected;
	private List<TblautIndicadoresCaracteristica> listaIndicadores;
	private List<TblautIndicadoresCaracteristica> listaIndicadoresFilter;
	private TblautIndicadoresCaracteristica indicador;
	private TblautIndicadoresCaracteristica selected;

	private String user = Util.getUserSession();

	private boolean crear;
	private boolean editar;

	@Override
	public void inicializar() {
		listaIndicadores = new ArrayList<TblautIndicadoresCaracteristica>();
		indicador = new TblautIndicadoresCaracteristica();
		selected = new TblautIndicadoresCaracteristica();

		crear = false;
		editar = false;

	}

	@PostConstruct
	public void init() throws BOException {
		try {
			LOGGER.debug("INIT CONTROLADOR");
			caracteristicaSelected = new TblautCaracteristica();
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
			indicadoresBO = LookupUtil
					.lookupRemoteStateless(IndicadoresBO.class);
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
			listaIndicadores = indicadoresBO
					.buscarPorCaracteristica(UtilidadBean
							.serialize(caracteristicaSelected));

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
			// valida que el código y el orden no se repitan
			if (!validarCodigo(true) && !validarOrden(true)) {
				indicador.setUsuario(user);
				indicador.setTblautCaracteristica(getCaracteristicaSelected());
				indicadoresBO.crear(UtilidadBean.serialize(indicador));
				lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
						"global.msg.exito.creo"));
				inicializar();
				cargarTodos();
				lanzarCrear(crear);
			}
		} catch (BOException e) {
			LOGGER.info("Error al create: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que valida que no se registre un indicador repetido, el parametro
	 * que pasa identifica la accion. Craer=true; editar:false
	 * 
	 * @param accion
	 * @return
	 */
	public boolean validarCodigo(boolean accion) {
		boolean result = false;
		for (TblautIndicadoresCaracteristica val : listaIndicadores) {
			if (accion
					&& UtilidadBean.validaNulos(indicador)
					&& indicador.getIndicador().toString().trim()
							.equals(val.getIndicador().trim())) {
				result = true;
				msgCodigo();
				break;
			} else if (!accion
					&& UtilidadBean.validaNulos(selected)
					&& selected.getOid() != val.getOid()
					&& selected.getIndicador().toString().trim()
							.equals(val.getIndicador().trim())) {
				result = true;
				msgCodigo();
				break;
			}
		}
		return result;
	}

	/**
	 * Método que valida que no se registre un orden repetido, el parametro que
	 * pasa identifica la accion. Craer=true; editar:false
	 * 
	 * @param accion
	 * @return
	 */
	public boolean validarOrden(boolean accion) {
		boolean result = false;
		for (TblautIndicadoresCaracteristica val : listaIndicadores) {
			if (accion && UtilidadBean.validaNulos(indicador)
					&& indicador.getOrden() == val.getOrden()) {
				result = true;
				msgorden();
				break;
			} else if (!accion && UtilidadBean.validaNulos(selected)
					&& selected.getOid() != val.getOid()
					&& selected.getOrden() == val.getOrden()) {
				result = true;
				msgorden();
				break;
			}
		}
		return result;
	}

	private void msgCodigo() {
		lanzarMensajeError(obtenerResourceBundle(AUTOEVAL).getString(
				"msg.warn.indicador.codigo"));
	}

	private void msgorden() {
		lanzarMensajeError(obtenerResourceBundle(AUTOEVAL).getString(
				"msg.warn.indicador.orden"));
	}

	/**
	 * Método que edita el registro
	 */
	public void editar() {
		try {
			// valida que el código y el orden no se repitan
			if (!validarCodigo(false) && !validarOrden(false)) {
				selected.setUsuario(user);
				indicadoresBO.editar(UtilidadBean.serialize(selected));
				lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
						"global.msg.exito.edito"));
				inicializar();
				cargarTodos();
				ejecutarUpdate("dlgEditarIndicadores");
			}
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
				TblautIndicadoresCaracteristica entity = indicadoresBO
						.editar(UtilidadBean.serialize(selected));
				// una ves actualixado se manda a eliminar el registro, la
				// acción se ejecuta para eveidenciar el usuario qe elimana el
				// registro
				indicadoresBO.eliminar(UtilidadBean.serialize(entity));
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
					indicadoresBO.editarEstado(UtilidadBean.serialize(selected));
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

	public void onRowSelect() {
		lanzarMensajeInfo("Item seleccionado: " + selected.getIndicador());

	}

	/**
	 * Método que permite evaluar al visivilidad del dialog(ventane emergente)
	 * de registro
	 * 
	 * @param val
	 */
	public void lanzarCrear(boolean val) {
		LOGGER.info("lanzarCrear " + val);
		crear = val;
		try {
			ejecutarUpdate("dlgNuevoIndicador");
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
				ejecutarUpdate("dlgEditarIndicador");
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
		indicador = new TblautIndicadoresCaracteristica();
	}

	public void configurar() {
		if (null != selected.getOid()) {
			// Utilidad.redireccionar(CONFIGURAR);
		} else {
			msgNoSeleccionado();
		}
	}

	// SETTES AND GETTES

	public ConfigurarMB getConfigurarMB() {
		return configurarMB;
	}

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

	public void setConfigurarMB(ConfigurarMB configurarMB) {
		this.configurarMB = configurarMB;
	}

	public List<TblautIndicadoresCaracteristica> getListaIndicadores() {
		return listaIndicadores;
	}

	public void setListaIndicadores(
			List<TblautIndicadoresCaracteristica> listaIndicadores) {
		this.listaIndicadores = listaIndicadores;
	}

	public List<TblautIndicadoresCaracteristica> getListaIndicadoresFilter() {
		return listaIndicadoresFilter;
	}

	public void setListaIndicadoresFilter(
			List<TblautIndicadoresCaracteristica> listaIndicadoresFilter) {
		this.listaIndicadoresFilter = listaIndicadoresFilter;
	}

	public TblautIndicadoresCaracteristica getIndicador() {
		return indicador;
	}

	public void setIndicador(TblautIndicadoresCaracteristica indicador) {
		this.indicador = indicador;
	}

	public TblautIndicadoresCaracteristica getSelected() {
		return selected;
	}

	public void setSelected(TblautIndicadoresCaracteristica selected) {
		this.selected = selected;
	}

	public TblautCaracteristica getCaracteristicaSelected() {
		return caracteristicaSelected;
	}

	public void setCaracteristicaSelected(
			TblautCaracteristica caracteristicaSelected) {
		this.caracteristicaSelected = caracteristicaSelected;
	}

}
