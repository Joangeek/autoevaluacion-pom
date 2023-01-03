package com.autoevaluacion.indidcadores;

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
import autoevaluacion.TblautIndicadoresCaracteristica;

import com.autoevaluacion.IndicadoresBO;
import com.gestion.MenuUserGruPerBO;
import com.gestion.ParametrosBO;

import commons.util.Cfg;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;

@ManagedBean(name = "confIndiMB")
@ViewScoped
public class ConfigurarMB extends BaseControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9154813722328390383L;

	// Variables

	private static final String CODMENU = "0012";
	private boolean mostrarIndicadores;
	private boolean mostrarFactores;
	private boolean mostrarCaracteristicas;
	private boolean mostrarConfigurados;

	private String titulo;
	//
	// Acciones

	private boolean acCrear;
	private boolean acEditar;
	private boolean acRegresar;
	private boolean acEliminar;
	private boolean acEstado;

	private boolean acFactores;
	private boolean acCaracteristicas;
	private boolean acIndicadores;
	private List<TblautIndicadoresCaracteristica> listado;
	private List<TblautIndicadoresCaracteristica> listadoFilter;

	// Controladores
	@ManagedProperty(value = "#{factoresMB}")
	private FactoresMB factoresMB;

	@ManagedProperty(value = "#{indicadoresMB}")
	private IndicadoresMB indicadoresMB;

	@ManagedProperty(value = "#{caracteristicasMB}")
	private CaracteristicasMB caracteristicasMB;

	// EJB
	@EJB
	private transient MenuUserGruPerBO menuUserGruPerBO;
	@EJB
	private transient ParametrosBO parametrosBO;
	@EJB
	private transient IndicadoresBO indicadoresBO;

	@Override
	public void inicializar() {

	}

	@PostConstruct
	public void init() {
		lookup();
		titulo();
		cargarAccionesPerfil();
		cargarListado();

		mostrarConfigurados = true;
		mostrarFactores = false;
		mostrarCaracteristicas = false;
		mostrarIndicadores = false;

		factoresMB.setConfigurarMB(this);
		indicadoresMB.setConfigurarMB(this);
		caracteristicasMB.setConfigurarMB(this);

	}

	private void cargarListado() {
		try {
			listado = new ArrayList<TblautIndicadoresCaracteristica>();
			listado = indicadoresBO.listado();
		} catch (BOException e) {
			LOGGER.info("-----> cargarListado()");
		}

	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {
			indicadoresBO = LookupUtil
					.lookupRemoteStateless(IndicadoresBO.class);

			parametrosBO = LookupUtil.lookupRemoteStateless(ParametrosBO.class);
			menuUserGruPerBO = LookupUtil
					.lookupRemoteStateless(MenuUserGruPerBO.class);

			// ...
		} catch (NamingException e) {
			// ...
		}
	}

	private void titulo() {
		titulo = obtenerResourceBundle(AUTOEVAL).getString(
				"title.page.configuracion.lista.indicadores");
	}

	private void tituloIndicadores() {
		titulo = obtenerResourceBundle(AUTOEVAL).getString(
				"title.page.configuracion.indicadores");
	}

	private void tituloCaracteristicas() {
		titulo = obtenerResourceBundle(AUTOEVAL).getString(
				"title.page.configuracion.caracteristicas");
	}

	private void tituloFactores() {
		titulo = obtenerResourceBundle(AUTOEVAL).getString(
				"title.page.configuracion.factores");
	}

	public void cargarFactores(boolean regresar) {

		if (regresar) {
			tituloFactores();
			mostrarConfigurados = false;
			mostrarIndicadores = false;
			mostrarCaracteristicas = false;
			mostrarFactores = true;
			factoresMB.cargarTodos();
		} else {
			mostrarConfigurados = true;
			mostrarIndicadores = false;
			mostrarCaracteristicas = false;
			mostrarFactores = false;
			factoresMB.inicializar();
			cargarListado();
		}
	}

	public void cargarCaracteristicas(boolean regresar) {
		if (regresar) {
			if (Utilidad.validaNulos(factoresMB.getSelected().getOid())) {
				tituloCaracteristicas();
				mostrarCaracteristicas = true;
				mostrarFactores = false;
				mostrarIndicadores = false;
				mostrarConfigurados = false;
				caracteristicasMB.setFactorSelected(factoresMB.getSelected());
				caracteristicasMB.cargarTodos();

				acRegresar = true;
			} else {
				msgNoSeleccionado();
			}
		} else {
			mostrarCaracteristicas = false;
			mostrarFactores = true;
			mostrarIndicadores = false;
			mostrarConfigurados = false;
			caracteristicasMB.inicializar();
		}
	}

	public void cargarIndicadores(boolean regresar) {
		if (regresar) {
			if (Utilidad.validaNulos(caracteristicasMB.getSelected().getOid())) {
				mostrarConfigurados = false;
				mostrarCaracteristicas = false;
				mostrarFactores = false;
				mostrarIndicadores = true;
				tituloIndicadores();
				indicadoresMB.setCaracteristicaSelected(caracteristicasMB
						.getSelected());
				indicadoresMB.cargarTodos();
			} else {
				msgNoSeleccionado();
			}
		} else {
			mostrarConfigurados = false;
			mostrarCaracteristicas = true;
			mostrarFactores = false;
			mostrarIndicadores = false;
			caracteristicasMB.inicializar();
		}
	}

	/**
	 * Método que controla la redirección del botón crear dependia¿endo de la
	 * vista desplegada
	 */
	public void lanzarCrear(boolean val) {
		if (!mostrarConfigurados && mostrarFactores && !mostrarIndicadores
				&& !mostrarCaracteristicas) {
			factoresMB.lanzarCrear(val);
		} else if (!mostrarConfigurados && !mostrarFactores
				&& !mostrarIndicadores && mostrarCaracteristicas) {
			caracteristicasMB.lanzarCrear(val);
		} else if (!mostrarConfigurados && !mostrarFactores
				&& mostrarIndicadores && !mostrarCaracteristicas) {
			indicadoresMB.lanzarCrear(val);
		} else {
			LOGGER.info("lanzarCrear Else" + val);
		}
	}

	/**
	 * Método quwe controla la redirección del botón ediar dependia¿endo de la
	 * vista desplegada
	 */
	public void lanzarEditar(boolean val) {
		if (!mostrarConfigurados && mostrarFactores && !mostrarIndicadores
				&& !mostrarCaracteristicas) {
			factoresMB.lanzarEditar(val);
		} else if (!mostrarConfigurados && !mostrarFactores
				&& !mostrarIndicadores && mostrarCaracteristicas) {
			caracteristicasMB.lanzarEditar(val);
		} else if (!mostrarConfigurados && !mostrarFactores
				&& mostrarIndicadores && !mostrarCaracteristicas) {
			indicadoresMB.lanzarEditar(val);
		} else {
			LOGGER.info("lanzarEditar Else" + val);
		}
	}

	/**
	 * Método quwe controla la redirección del botón regresar dependia¿endo de
	 * la vista desplegada
	 */
	public void lanzarRegresar() {
		if (!mostrarConfigurados && mostrarFactores && !mostrarIndicadores
				&& !mostrarCaracteristicas) {
			cargarFactores(false);
		} else if (!mostrarConfigurados && !mostrarFactores
				&& !mostrarIndicadores && mostrarCaracteristicas) {
			cargarCaracteristicas(false);
		} else if (!mostrarConfigurados && !mostrarFactores
				&& mostrarIndicadores && !mostrarCaracteristicas) {
			cargarIndicadores(false);
		}
	}

	/**
	 * Método que controla la redirección del botón eliminar dependia¿endo de la
	 * vista desplegada
	 */
	public void lanzarEliminar() {
		if (!mostrarConfigurados && mostrarFactores && !mostrarIndicadores
				&& !mostrarCaracteristicas) {
			factoresMB.eliminar();
		} else if (!mostrarConfigurados && !mostrarFactores
				&& !mostrarIndicadores && mostrarCaracteristicas) {
			caracteristicasMB.eliminar();
		} else if (!mostrarConfigurados && !mostrarFactores
				&& mostrarIndicadores && !mostrarCaracteristicas) {
			indicadoresMB.eliminar();
		} else {
			LOGGER.info("lanzarEliminar Else");
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
			if (permiso.equals(Cfg.CARACTERISTICAS)) {
				setAcCaracteristicas(true);
			}
			if (permiso.equals(Cfg.FACTORES)) {
				setAcFactores(true);
			}
			if (permiso.equals(Cfg.INDICADORES)) {
				setAcIndicadores(true);
			}

		}
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

	public boolean isAcFactores() {
		return acFactores;
	}

	public void setAcFactores(boolean acFactores) {
		this.acFactores = acFactores;
	}

	public boolean isAcCaracteristicas() {
		return acCaracteristicas;
	}

	public void setAcCaracteristicas(boolean acCaracteristicas) {
		this.acCaracteristicas = acCaracteristicas;
	}

	public boolean isAcIndicadores() {
		return acIndicadores;
	}

	public void setAcIndicadores(boolean acIndicadores) {
		this.acIndicadores = acIndicadores;
	}

	public boolean isMostrarIndicadores() {
		return mostrarIndicadores;
	}

	public void setMostrarIndicadores(boolean mostrarIndicadores) {
		this.mostrarIndicadores = mostrarIndicadores;
	}

	public boolean isMostrarFactores() {
		return mostrarFactores;
	}

	public void setMostrarFactores(boolean mostrarFactores) {
		this.mostrarFactores = mostrarFactores;
	}

	public boolean isMostrarCaracteristicas() {
		return mostrarCaracteristicas;
	}

	public void setMostrarCaracteristicas(boolean mostrarCaracteristicas) {
		this.mostrarCaracteristicas = mostrarCaracteristicas;
	}

	public boolean isMostrarConfigurados() {
		return mostrarConfigurados;
	}

	public void setMostrarConfigurados(boolean mostrarConfigurados) {
		this.mostrarConfigurados = mostrarConfigurados;
	}

	public List<TblautIndicadoresCaracteristica> getListado() {
		return listado;
	}

	public void setListado(List<TblautIndicadoresCaracteristica> listado) {
		this.listado = listado;
	}

	public List<TblautIndicadoresCaracteristica> getListadoFilter() {
		return listadoFilter;
	}

	public void setListadoFilter(
			List<TblautIndicadoresCaracteristica> listadoFilter) {
		this.listadoFilter = listadoFilter;
	}

	public FactoresMB getFactoresMB() {
		return factoresMB;
	}

	public void setFactoresMB(FactoresMB factoresMB) {
		this.factoresMB = factoresMB;
	}

	public IndicadoresMB getIndicadoresMB() {
		return indicadoresMB;
	}

	public void setIndicadoresMB(IndicadoresMB indicadoresMB) {
		this.indicadoresMB = indicadoresMB;
	}

	public CaracteristicasMB getCaracteristicasMB() {
		return caracteristicasMB;
	}

	public void setCaracteristicasMB(CaracteristicasMB caracteristicasMB) {
		this.caracteristicasMB = caracteristicasMB;
	}

}
