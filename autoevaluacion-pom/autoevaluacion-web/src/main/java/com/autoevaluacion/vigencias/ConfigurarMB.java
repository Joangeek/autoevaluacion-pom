package com.autoevaluacion.vigencias;

import gestion.MenuUserGruPer;
import gestion.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import util.BOException;
import autoevaluacion.TblautVigencia;

import com.autoevaluacion.VigenciasBO;
import com.gestion.MenuUserGruPerBO;
import com.gestion.ParametrosBO;

import commons.util.Cfg;
import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;



@ManagedBean(name = "confVigenciaMB")
@ViewScoped
public class ConfigurarMB extends BaseControlador implements Serializable {

	private static final long serialVersionUID = -1921305952278658683L;

	// Variables
	private static final String CODMENU = "0013";
	private String user = Util.getUserSession();
	private String titulo = obtenerResourceBundle(AUTOEVAL).getString(
			"title.page.configuracion.lista.indicadores");
	//
	// Acciones

	private boolean acCrear;
	private boolean acEditar;
	private boolean acRegresar;
	private boolean acEliminar;
	private boolean acEstado;

	private boolean acVigencias;

	// Controladores
	@ManagedProperty(value = "#{vigenciaMB}")
	private VigenciasMB vigenciaMB;

	// EJB
	@EJB
	private  transient MenuUserGruPerBO menuUserGruPerBO;
	@EJB
	private transient ParametrosBO parametrosBO;
	@EJB
	private transient VigenciasBO vigenciasBO;

	// VARIABLES
	private List<TblautVigencia> listado;
	private List<TblautVigencia> listadoFilter;

	private TblautVigencia vigencia;
	private TblautVigencia selected;

	private boolean crear;
	private boolean editar;

	@Override
	public void inicializar() {
		setVigencia(new TblautVigencia());
		selected = new TblautVigencia();
		crear = false;
		editar = false;
	}

	@PostConstruct
	public void init() {
		listado = new ArrayList<TblautVigencia>();
		inicializar();
		lookup();
		cargarAccionesPerfil();
		cargarTodos();
		vigenciaMB.setConfigurarMB(this);
	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {

			vigenciasBO = LookupUtil.lookupRemoteStateless(VigenciasBO.class);
			parametrosBO = LookupUtil.lookupRemoteStateless(ParametrosBO.class);
			menuUserGruPerBO= LookupUtil.lookupRemoteStateless(MenuUserGruPerBO.class);
			// ...
		} catch (NamingException e) {
			// ...
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
			if (permiso.equals(Cfg.VIGENCIAS)) {
				setAcVigencias(true);
			}

		}
	}

	/**
	 * Método que consulta todos los datos relacionados a la entidad
	 * 
	 * @return
	 */
	private void cargarTodos() {
		try {
			listado = new ArrayList<TblautVigencia>();
			listado = vigenciasBO.buscarTodos();
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
	public void crearNuevo() {
		try {
			vigencia.setUsuario(user);
			vigenciasBO.crear(UtilidadBean.serialize(vigencia));
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.creo"));
			inicializar();
			cargarTodos();
		} catch (BOException e) {
			LOGGER.info("Error al create: " + e.getMessage(), e);
		}
	}

	public boolean validarAnio() {
		boolean result = false;
		for (TblautVigencia val : listado) {
			if (vigencia.getAnioVigencia() == val.getAnioVigencia()) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * Método que edita el registro
	 */
	public void editarSeleccionado() {
		try {
			selected.setUsuario(user);
			vigenciasBO.editar(UtilidadBean.serialize(selected));
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
				TblautVigencia entity = vigenciasBO.editar(UtilidadBean
						.serialize(selected));
				// una ves actualixado se manda a eliminar el registro, la
				// acción se ejecuta para eveidenciar el usuario qe elimana el
				// registro
				vigenciasBO.eliminar(UtilidadBean.serialize(entity));
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
	 * Método que permite evaluar al visivilidad del dialog(ventane emergente)
	 * de registro
	 * 
	 * @param val
	 */
	public void lanzarCrear(boolean val) {

		crear = val;
		try {
			ejecutarUpdate("dlgNuevaVigencia");
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
			if (Utilidad.validaNulos(selected) && null != selected.getOid()) {
				editar = val;
				if (!val)
					inicializar();
				ejecutarUpdate("dlgEditarVigencia");
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
		setVigencia(new TblautVigencia());
	}

	public void configurar() {
		if (Utilidad.validaNulos(selected) && null != selected.getOid()) {
			// Utilidad.redireccionar(CONFIGURAR);
		} else {
			msgNoSeleccionado();
		}
	}

	public void onRowSelect() {
		lanzarMensajeInfo("Item seleccionado: " + selected.getAnioVigencia());
		// configurarMB.cargargrupoAspectoSeleccionado(selected);
	}

	// Settes and Gettes

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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public VigenciasMB getVigenciaMB() {
		return vigenciaMB;
	}

	public void setVigenciaMB(VigenciasMB vigenciaMB) {
		this.vigenciaMB = vigenciaMB;
	}

	public boolean isAcVigencias() {
		return acVigencias;
	}

	public void setAcVigencias(boolean acVigencias) {
		this.acVigencias = acVigencias;
	}

	public List<TblautVigencia> getListado() {
		return listado;
	}

	public void setListado(List<TblautVigencia> listado) {
		this.listado = listado;
	}

	public List<TblautVigencia> getListadoFilter() {
		return listadoFilter;
	}

	public void setListadoFilter(List<TblautVigencia> listadoFilter) {
		this.listadoFilter = listadoFilter;
	}

	public TblautVigencia getVigencia() {
		return vigencia;
	}

	public void setVigencia(TblautVigencia vigencia) {
		this.vigencia = vigencia;
	}

	public TblautVigencia getSelected() {
		return selected;
	}

	public void setSelected(TblautVigencia selected) {
		this.selected = selected;
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

}
