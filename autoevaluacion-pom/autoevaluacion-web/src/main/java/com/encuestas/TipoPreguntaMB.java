package com.encuestas;

import encuestas.TblencTipoAspecto;
import gestion.MenuUserGruPer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import commons.util.Cfg;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;

import com.encuesta.TipoAspectoBO;
import com.gestion.MenuUserGruPerBO;
import com.gestion.ParametrosBO;

import util.BOException;
import gestion.Util;


@ManagedBean(name = "tipoPregunta")
@ViewScoped
public class TipoPreguntaMB<T> extends BaseControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8434534413696267700L;
	private static final String CODMENU = "0005";
	private static final String DATATABLE = "dataTable";

	/**
     * 
     */

	// EJB
	@EJB
	private TipoAspectoBO tipoAspectoBO;
	@EJB
	private MenuUserGruPerBO menuUserGruPerBO;
	@EJB
	private ParametrosBO parametrosBO;

	// VARIABLES
	private List<TblencTipoAspecto> listaTipoAspecto;
	private List<TblencTipoAspecto> listaTipoAspectoFilter;
	private TblencTipoAspecto tipoAspecto;
	private TblencTipoAspecto selected;

	private String user = Util.getUserSession();

	private boolean crear;
	private boolean editar;

	// ACCIONES
	private boolean acCrear;
	private boolean acEditar;
	private boolean acRegresar;
	private boolean acEliminar;
	private boolean acEstado;

	@Override
	public void inicializar() {
		listaTipoAspecto = new ArrayList<TblencTipoAspecto>();
		tipoAspecto = new TblencTipoAspecto();
		selected = new TblencTipoAspecto();
		crear = false;
		editar = false;
		cargarTodos();
	}

	@PostConstruct
	public void init() throws BOException {
		try {
			LOGGER.debug("INIT CONTROLADOR");
			inicializar();
			lookup();
			cargarAccionesPerfil();

		} catch (Exception ex) {
			lanzarMensajeError(null, ex);
		}

	}

	/**
	 * Método que evalúa las acciones activas de la página, ésta se encuentra
	 * referenciada como menú, se inicializan lolos botónes y se valida en menú
	 * y el menu_grupo_usuario: ésta última cargada desde loginBean en una
	 * variable de sessión
	 */

	private void cargarAccionesPerfil() {
		// HttpSession session = Util.getSession();
		List<Integer> listMenuUsuGrupo = new ArrayList<Integer>();
		listMenuUsuGrupo = Util.getMenuUsuarioGrupo();
		// listMenuUsuGrupo = (List<Integer>) session
		// .getAttribute("menuUsuarioGrupo");
		LOGGER.info(listMenuUsuGrupo.size());
		List<MenuUserGruPer> lista = new ArrayList<MenuUserGruPer>();
		try {

			// consulta los permisos que tiene de acuerdo al menú y al
			// menu_grupo_usuario logueado
			lista = menuUserGruPerBO.buscaPerfiles(listMenuUsuGrupo,
					parametrosBO.buscarCodigo(CODMENU).getVal_int());

		} catch (Exception e) {
			LOGGER.info(e);
		}
		/**
		 * ciclo que evalua las acciones permitidas al usuario. Renderiza
		 * componentes en la interfaz
		 */
		for (MenuUserGruPer grup : lista) {
			String permiso = grup.getPermiso().getAccion();
			if (permiso.equals(Cfg.REGRESAR)) {
				acRegresar = true;
			}
			if (permiso.equals(Cfg.CREAR)) {
				acCrear = true;
			}
			if (permiso.equals(Cfg.EDITAR)) {
				acEditar = true;
			}
			if (permiso.equals(Cfg.ELIMINAR)) {
				acEliminar = true;
			}
			if (permiso.equals(Cfg.ESTADO)) {
				acEstado = true;
			}

		}
	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		// InitialContext context;
		try {
			// context = new InitialContext();
			// java:global[/application name]/module name/enterprise bean
			// name[/interface name]
			// tipoAspectoBO = (TipoAspectoBO) context
			// .lookup("java:global/autoevaluacion-ear/autoevaluacion-ejb-0.0.1-SNAPSHOT/TipoAspectoBOimpl!com.encuesta.TipoAspectoBO");
			tipoAspectoBO = LookupUtil
					.lookupRemoteStateless(TipoAspectoBO.class);
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
			listaTipoAspecto = tipoAspectoBO.buscarTodos();
			ejecutarUpdate(DATATABLE);
		} catch (NullPointerException e) {
			LOGGER.info("Null P al cargarTodos: ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarTodos: " + e.getMessage(), e);
		}

	}

	/**
	 * Método que crea un nuevo registro
	 */
	public void crear() {
		try {
			tipoAspecto.setUsuario(user);
			tipoAspectoBO.crear(tipoAspecto);
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.creo"));
			inicializar();
			lanzarCrear(crear);
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
			selected.setUsuario(user);
			tipoAspectoBO.editar(selected);
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.edito"));
			inicializar();
			ejecutarUpdate("dlgEditar");
			ejecutarUpdate(DATATABLE);
		} catch (BOException e) {
			LOGGER.info("Error al edit: " + e.getMessage(), e);
		}
	}

	public void onRowSelect() {
		lanzarMensajeInfo("Item seleccionado: " + selected.getNombre());
	}

	/**
	 * Metodo que elimina el registro
	 */
	public void eliminar() {
		try {
			LOGGER.info("eliminar: " + selected == null);
			if (Utilidad.validaNulos(selected)) {
				LOGGER.info("selected = " + selected.getNombre());
				// se setea el usuario que va a eliminar y se envía la
				// actualización del usuario
				selected.setUsuario(user);
				TblencTipoAspecto entity = tipoAspectoBO.editarR(selected);
				// una ves actualixado se manda a eliminar el registro, la
				// acción se ejecuta para eveidenciar el usuario qe elimana el
				// registro
				tipoAspectoBO.eliminar(entity);
				lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
						"global.msg.exito.elimino"));
				inicializar();
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

			if (acEstado) {
				if (Utilidad.validaNulos(selected)) {
					LOGGER.info("selected = " + selected.getNombre());

					tipoAspectoBO.editar(selected);
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
			lanzarMensajeError("ERROR-", e);
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
		ejecutarUpdate("dlgNuevo");
		reset();
	}

	/**
	 * Método que permite evaluar al visivilidad del dialog(ventane emergente)
	 * de edición
	 * 
	 * @param val
	 */
	public void lanzarEditar(boolean val) {
		if (null != selected.getOid()) {
			editar = val;
			ejecutarUpdate("dlgEditar");
		} else {
			editar = false;
			msgNoSeleccionado();
		}
	}

	/**
	 * Limpiar resetear entidad
	 */
	private void reset() {
		tipoAspecto = new TblencTipoAspecto();
	}

	// SETTES AND GETTES
	public List<TblencTipoAspecto> getListaTipoAspecto() {
		return listaTipoAspecto;
	}

	public void setListaTipoAspecto(List<TblencTipoAspecto> listaTipoAspecto) {
		this.listaTipoAspecto = listaTipoAspecto;
	}

	public TblencTipoAspecto getTipoAspecto() {
		return tipoAspecto;
	}

	public void setTipoAspecto(TblencTipoAspecto tipoAspecto) {
		this.tipoAspecto = tipoAspecto;
	}

	public TblencTipoAspecto getSelected() {
		return selected;
	}

	public void setSelected(TblencTipoAspecto selected) {
		this.selected = selected;
	}

	public List<TblencTipoAspecto> getListaTipoAspectoFilter() {
		return listaTipoAspectoFilter;
	}

	public void setListaTipoAspectoFilter(
			List<TblencTipoAspecto> listaTipoAspectoFilter) {
		this.listaTipoAspectoFilter = listaTipoAspectoFilter;
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

	public boolean isAcCrear() {
		return acCrear;
	}

	public void setAcCrear(boolean acCrear) {
		this.acCrear = acCrear;
	}

	public boolean isAcEditar() {
		return acEditar;
	}

	public void setAcEditar(boolean acEditar) {
		this.acEditar = acEditar;
	}

	public boolean isAcRegresar() {
		return acRegresar;
	}

	public void setAcRegresar(boolean acRegresar) {
		this.acRegresar = acRegresar;
	}

	public boolean isAcEliminar() {
		return acEliminar;
	}

	public void setAcEliminar(boolean acEliminar) {
		this.acEliminar = acEliminar;
	}

	public boolean isAcEstado() {
		return acEstado;
	}

	public void setAcEstado(boolean acEstado) {
		this.acEstado = acEstado;
	}

	/*
	 * public void lanzar() { try { //
	 * lanzarMensajeInfo(lanzarMensajes.getString("page.title"));
	 * lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString("page.title"));
	 * } catch (Exception e) { // LOGGER.info("Error al lanzar: " +
	 * e.getMessage(), e); e.printStackTrace(); } }
	 */

}
