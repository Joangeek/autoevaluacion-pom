package com.autoevaluacion;

import gestion.MenuUserGruPer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import com.gestion.MenuUserGruPerBO;
import com.gestion.ParametrosBO;

import util.BOException;
import autoevaluacion.TblautFuente;
import gestion.Util;
import commons.util.Cfg;
import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;


@ManagedBean(name = "fuentesMB")
@ViewScoped
public class FuentesMB extends BaseControlador implements Serializable {

	private static final long serialVersionUID = 5969382270580629836L;
	private static final String CODMENU = "0010";
	private static final Integer ACTIVO = 1;
	
	private String titulo = obtenerResourceBundle(AUTOEVAL).getString(
			"title.page.fuentes");
	// EJB
	@EJB
	private FuentesBO fuentesBO;
	@EJB
	private MenuUserGruPerBO menuUserGruPerBO;
	@EJB
	private ParametrosBO parametrosBO;
	// Acciones
	private boolean acCrear;
	private boolean acEditar;
	private boolean acRegresar;
	private boolean acEliminar;
	private boolean acEstado;

	// VARIABLES
	private List<TblautFuente> listaFuentes;
	private List<TblautFuente> listaFuentesFilter;

	private TblautFuente fuente;
	private TblautFuente selected;

	private String user = Util.getUserSession();
	private boolean lanzarCrear;
	private boolean lanzarEditar;
	

	@Override
	public void inicializar() {
		fuente = new TblautFuente();
		selected = new TblautFuente();
		listaFuentes = new ArrayList<TblautFuente>();
		setLanzarCrear(false);
		setLanzarEditar(false);
		cargarTodos();
	}

	@PostConstruct
	public void init() throws BOException {
		try {

			LOGGER.info("INIT");
			inicializar();
			lookup();
			inicializar();
			cargarAccionesPerfil();

		} catch (Exception ex) {
			lanzarMensajeError(null, ex);
		}

	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {
			fuentesBO = LookupUtil.lookupRemoteStateless(FuentesBO.class);
			menuUserGruPerBO = LookupUtil
					.lookupRemoteStateless(MenuUserGruPerBO.class);
			//parametrosBO = LookupUtil.lookupRemoteStateless(ParametrosBO.class);
			// ...
		} catch (NamingException e) {
			// ...
		}
	}

	public void onRowSelect() {
		lanzarMensajeInfo("Item seleccionado: " + selected.getNombre());

	}

	/**
	 * Método que consulta todos los datos relacionados a la entidad
	 */
	public void cargarTodos() {
		try {
			listaFuentes = fuentesBO.buscarTodos();
		}  catch (NullPointerException e) {
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

			fuente.setUsuario(user);
			fuente.setEstado(ACTIVO);
			fuentesBO.crear(UtilidadBean.serialize(fuente));
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.creo"));
			inicializar();
			cargarTodos();

		} catch (Exception e) {
			LOGGER.info("Error al create: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que edita el registro
	 */
	public void editar() {
		try {
			selected.setUsuario(user);
			fuentesBO.editar(UtilidadBean.serialize(selected));
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.edito"));
			inicializar();
		} catch (Exception e) {
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
				TblautFuente entity = fuentesBO.editar(UtilidadBean
						.serialize(selected));
				// una ves actualixado se manda a eliminar el registro, la
				// acción se ejecuta para eveidenciar el usuario qe elimana el
				// registro
				fuentesBO.eliminar(UtilidadBean.serialize(entity));
				lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
						"global.msg.exito.elimino"));
				inicializar();
				cargarTodos();
			} else {
				msgNoSeleccionado();
			}
		}  catch (Exception e) {
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
		try {
			setLanzarCrear(val);
			reset();
			ejecutarUpdate("dlgNuevo");
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
				setLanzarEditar(val);
				if (!val)
					inicializar();
				ejecutarUpdate("dlgEditar");
			} else {
				msgNoSeleccionado();
			}
		} catch (NullPointerException e) {
			LOGGER.info("Null P le", e);
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
					fuentesBO.editarEstado(UtilidadBean.serialize(selected));
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
	 * Limpiar resetear entidad
	 */
	private void reset() {
		fuente = new TblautFuente();
	}

	public void configurar() {
		if (null != selected.getOid()) {
			// Utilidad.redireccionar(CONFIGURAR);
		} else {
			msgNoSeleccionado();
		}
	}

	private void cargarAccionesPerfil() {
		List<Integer> listMenuUsuGrupo = new ArrayList<Integer>();
		listMenuUsuGrupo = Util.getMenuUsuarioGrupo();
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

	// SETTES AND GETTES

	public List<TblautFuente> getListaFuentes() {
		return listaFuentes;
	}

	public void setListaFuentes(List<TblautFuente> listaFuentes) {
		this.listaFuentes = listaFuentes;
	}

	public List<TblautFuente> getListaFuentesFilter() {
		return listaFuentesFilter;
	}

	public void setListaFuentesFilter(List<TblautFuente> listaFuentesFilter) {
		this.listaFuentesFilter = listaFuentesFilter;
	}

	public TblautFuente getFuente() {
		return fuente;
	}

	public void setFuente(TblautFuente fuente) {
		this.fuente = fuente;
	}

	public TblautFuente getSelected() {
		return selected;
	}

	public void setSelected(TblautFuente selected) {
		this.selected = selected;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isLanzarCrear() {
		return lanzarCrear;
	}

	public void setLanzarCrear(boolean lanzarCrear) {
		this.lanzarCrear = lanzarCrear;
	}

	public boolean isLanzarEditar() {
		return lanzarEditar;
	}

	public void setLanzarEditar(boolean lanzarEditar) {
		this.lanzarEditar = lanzarEditar;
	}

}
