package com.autoevaluacion.indidcadores;

import gestion.Util;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import util.BOException;
import autoevaluacion.TblautCaracteristica;
import autoevaluacion.TblautFactore;

import com.autoevaluacion.CaracteristicasBO;

import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;

@ManagedBean(name = "caracteristicasMB")
@ViewScoped
public class CaracteristicasMB extends BaseControlador implements Serializable {

	private static final long serialVersionUID = -7426079486530699022L;

	// EJB
	@EJB
	private CaracteristicasBO caracteristicasBO;

	// CONTROLADORES
	private ConfigurarMB configurarMB;

	// VARIABLES
	private List<TblautCaracteristica> listaCaracteristicas;
	private List<TblautCaracteristica> listaCaracteristicasFilter;
	private TblautCaracteristica caracteristica;
	private TblautCaracteristica selected;
	private TblautFactore factorSelected;

	private String user = Util.getUserSession();

	private boolean crear;
	private boolean editar;

	@Override
	public void inicializar() {
		selected = new TblautCaracteristica();
		crear = false;
		editar = false;

	}

	@PostConstruct
	public void init() throws util.BOException {
		try {

			LOGGER.debug("INIT CONTROLADOR");
			factorSelected = new TblautFactore();
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
			caracteristicasBO = LookupUtil
					.lookupRemoteStateless(CaracteristicasBO.class);
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
			listaCaracteristicas = caracteristicasBO
					.buscarPorFactor(UtilidadBean.serialize(factorSelected));

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
			// valida que el código y el orden no se repitan
			if (!validarCodigo(true) && !validarOrden(true)) {
				caracteristica.setUsuario(user);
				caracteristica.setTblautFactore(getFactorSelected());
				caracteristicasBO.crear(UtilidadBean.serialize(caracteristica));
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

	public boolean validarCodigo(boolean accion) {
		boolean result = false;
		for (TblautCaracteristica val : listaCaracteristicas) {
			if (accion
					&& UtilidadBean.validaNulos(caracteristica)
					&& caracteristica.getCodigo().trim()
							.equals(val.getCodigo().trim())) {
				result = true;
				msgCodigo();
				break;
			} else if (!accion
					&& UtilidadBean.validaNulos(selected)
					&& selected.getOid() != val.getOid()
					&& selected.getCodigo().trim()
							.equals(val.getCodigo().trim())) {
				result = true;
				msgCodigo();
			}
		}
		return result;
	}

	/**
	 * El parametro acción representa si el llamado a la función se hace al
	 * crear=true o si se hace al editar=false, de tal modo se valida el campo
	 * 
	 * @param accion
	 * @return
	 */
	public boolean validarOrden(boolean accion) {
		boolean result = false;
		for (TblautCaracteristica val : listaCaracteristicas) {
			if (accion && UtilidadBean.validaNulos(caracteristica)
					&& caracteristica.getOrden() == val.getOrden()) {
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
				"msg.warn.caracteristica.codigo"));
	}

	private void msgorden() {
		lanzarMensajeError(obtenerResourceBundle(AUTOEVAL).getString(
				"msg.warn.caracteristica.orden"));
	}

	/**
	 * Método que edita el registro
	 */
	public void editar() {
		try {
			// valida que el código y el orden no se repitan
			if (!validarCodigo(false) && !validarOrden(false)) {
				selected.setUsuario(user);
				caracteristicasBO.editar(UtilidadBean.serialize(selected));
				lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
						"global.msg.exito.edito"));
				inicializar();
				ejecutarUpdate("dlgEditarCaracteristica");
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
				TblautCaracteristica entity = caracteristicasBO
						.editar(UtilidadBean.serialize(selected));
				// una ves actualixado se manda a eliminar el registro, la
				// acción se ejecuta para eveidenciar el usuario qe elimana el
				// registro
				caracteristicasBO.eliminar(UtilidadBean.serialize(entity));
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

	public void onRowSelect() {
		lanzarMensajeInfo("Item seleccionado: " + selected.getCaracteristica());

	}

	/**
	 * Método que edita el registro
	 */
	public void cambiarEstado() {
		try {
			if (configurarMB.isAcEstado()) {
				if (Utilidad.validaNulos(selected)) {
					selected.setUsuario(user);
					caracteristicasBO.editarEstado(UtilidadBean
							.serialize(selected));
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
			ejecutarUpdate("dlgNuevaCaracteristica");
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
				ejecutarUpdate("dlgEditarCaracteristica");
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
		caracteristica = new TblautCaracteristica();
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

	public TblautFactore getFactorSelected() {
		return factorSelected;
	}

	public void setFactorSelected(TblautFactore factorSelected) {
		this.factorSelected = factorSelected;
	}

	public List<TblautCaracteristica> getListaCaracteristicas() {
		return listaCaracteristicas;
	}

	public void setListaCaracteristicas(
			List<TblautCaracteristica> listaCaracteristicas) {
		this.listaCaracteristicas = listaCaracteristicas;
	}

	public List<TblautCaracteristica> getListaCaracteristicasFilter() {
		return listaCaracteristicasFilter;
	}

	public void setListaCaracteristicasFilter(
			List<TblautCaracteristica> listaCaracteristicasFilter) {
		this.listaCaracteristicasFilter = listaCaracteristicasFilter;
	}

	public TblautCaracteristica getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(TblautCaracteristica caracteristica) {
		this.caracteristica = caracteristica;
	}

	public TblautCaracteristica getSelected() {
		return selected;
	}

	public void setSelected(TblautCaracteristica selected) {
		this.selected = selected;
	}

}
