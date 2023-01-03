package com.encuestas.configurar;

import encuestas.TblencGrupoAspecto;
import encuestas.TblencProgramacionEncuesta;
import encuestas.TblencTipoEvaluacion;
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

import com.encuesta.TipoEvaluacionBO;
import com.gestion.MenuUserGruPerBO;
import com.gestion.ParametrosBO;

import commons.util.Cfg;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;

@ManagedBean(name = "configurarMB")
@ViewScoped
public class ConfigurarMB extends BaseControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9154813722328390383L;

	// Variables

	private static final String CODMENU = "0006";
	private boolean mostrarAspectos;
	private boolean mostrarGrupoAspectos;
	private boolean mostrarEncuestas;
	private boolean mostrarFechas;
	private boolean mostrarPrevisualizar;
	private boolean mostrarSedeprogramas;

	public boolean isMostrarFechas() {
		return mostrarFechas;
	}

	public void setMostrarFechas(boolean mostrarFechas) {
		this.mostrarFechas = mostrarFechas;
	}

	private String titulo;
	//
	// Acciones

	private boolean acCrear;
	private boolean acEditar;
	private boolean acRegresar;
	private boolean acEliminar;
	private boolean acEstado;
	private boolean acConfigurar;
	private boolean acAspectos;
	private boolean acFechas;
	private boolean acPreVisualizar;
	private boolean acSedesProgramas;

	// Controladores

	@ManagedProperty(value = "#{grupoAspectoMB}")
	private GrupoAspectosMB grupoAspectoMB;

	@ManagedProperty(value = "#{encuestasMB}")
	private EncuestasMB encuestasMB;

	@ManagedProperty(value = "#{aspectoMB}")
	private AspectosMB aspectoMB;

	@ManagedProperty(value = "#{fechasMB}")
	private FechasMB fechasMB;

	@ManagedProperty(value = "#{previsualizarMB}")
	private PrevisualizarMB previsualizarMB;

	@ManagedProperty(value = "#{encSedeProgramaMB}")
	private EncuestaSedeProgramaMB encSedeProgramaMB;

	@ManagedProperty(value = "#{spProgramadasMB}")
	private SedesProgramasProgramadasMB spProgramadasMB;

	// EJB

	@EJB
	private transient TipoEvaluacionBO tipoEvaluacionBO;
	@EJB
	private transient MenuUserGruPerBO menuUserGruPerBO;
	@EJB
	private transient ParametrosBO parametrosBO;

	@Override
	public void inicializar() {

	}

	@PostConstruct
	public void init() {
		lookup();
		tituloEncuesta();
		cargarAccionesPerfil();
		encuestasMB.setEstadoAC(acEstado);
		if (isAcRegresar()) {
			acRegresar = false;
		}
		mostrarEncuestas = true;
		mostrarGrupoAspectos = false;
		mostrarAspectos = false;
		mostrarFechas = false;
		encuestasMB.setConfigurarMB(this);
		grupoAspectoMB.setConfigurarMB(this);
		aspectoMB.setConfigurarMB(this);
		fechasMB.setConfigurarMB(this);
		encuestasMB.inicializar();
	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {
			tipoEvaluacionBO = LookupUtil
					.lookupRemoteStateless(TipoEvaluacionBO.class);

			parametrosBO = LookupUtil.lookupRemoteStateless(ParametrosBO.class);
			menuUserGruPerBO = LookupUtil
					.lookupRemoteStateless(MenuUserGruPerBO.class);
		} catch (NamingException e) {
			// ...
		}
	}

	private void tituloEncuesta() {
		titulo = "ENCUESTAS";// obtenerResourceBundle(ENCUESTA)
								// .getString("tit.page.encuestas");
	}

	private void tituloGrupoAspectos() {
		titulo = "ENCUESTAS / CONF GRUPOS ASPECTOS";// obtenerResourceBundle(ENCUESTA).getString(
		// "tit.page.encuestas.grupo.aspect");
	}

	private void tituloAspectos() {
		titulo = "ENCUESTAS / CONF ASPECTOS";// obtenerResourceBundle(ENCUESTA).getString(
		// "tit.page.encuestas.grupo.aspect");
	}

	private void tituloFechas() {
		titulo = "ENCUESTAS / CONFIGURACIÓN DE FECHAS: "
				+ encuestasMB.getSelected().getNombre();// obtenerResourceBundle(ENCUESTA).getString(
		// "tit.page.encuestas.grupo.aspect");
	}

	private void tituloFechasSp() {
		titulo = "ENCUESTAS / CONFIGURACIÓN DE FECHAS: "
				+ spProgramadasMB.getProgramacionSelected().getTblacaPeriodo()
						.getDescripcion();// obtenerResourceBundle(ENCUESTA).getString(
		// "tit.page.encuestas.grupo.aspect");
	}

	private void tituloPrevisualizar() {
		titulo = "PREVISUALIZAR";
	}

	public void cargarFechas() {
		if (Utilidad.validaNulos(encuestasMB.getSelected().getOid())) {
			tituloFechas();
			LOGGER.info("cargarFechas() ");
			mostrarFechas = true;
			mostrarEncuestas = false;
			mostrarGrupoAspectos = false;
			mostrarAspectos = false;
			grupoAspectoMB.inicializar();
			acSedesProgramas = true;
			acRegresar = true;
			acAspectos = false;
			acConfigurar = false;
			acFechas = false;
			acPreVisualizar = false;
			fechasMB.cargarTodos();
			fechasMB.cargarListas();
		} else {
			msgNoSeleccionado();
		}
	}

	public void cargarSedesProgramas() {
		if (Utilidad.validaNulos(fechasMB.getSelected().getOid())) {
			spProgramadasMB.setProgramacionSelected(fechasMB.getSelected());
			tituloFechasSp();
			LOGGER.info("cargarSedesProgramas() ");
			mostrarSedeprogramas = true;
			mostrarPrevisualizar = false;
			mostrarFechas = false;
			mostrarEncuestas = false;
			mostrarGrupoAspectos = false;
			mostrarAspectos = false;
			acEditar = false;
			acEliminar = false;
			acRegresar = true;
			acAspectos = false;
			acConfigurar = false;
			acFechas = false;
			acSedesProgramas = false;
			acPreVisualizar = false;
			spProgramadasMB.cargarTodos();
		} else {
			msgNoSeleccionado();
		}
	}

	public void cargarTipoEvaluacion() {

		tituloEncuesta();
		LOGGER.info("cargarTipoEvaluacion() ");
		mostrarEncuestas = true;
		mostrarGrupoAspectos = false;
		mostrarAspectos = false;
		mostrarFechas = false;
		mostrarPrevisualizar = false;
		mostrarSedeprogramas = false;
		encuestasMB.inicializar();
		grupoAspectoMB.inicializar();
		aspectoMB.inicializar();
		fechasMB.inicializar();
		previsualizarMB.inicializar();
		acRegresar = false;
		acAspectos = false;
		acConfigurar = true;
		acFechas = true;
		acPreVisualizar = true;
		acSedesProgramas = false;
	}

	public void cargarGrupoAspectos() {

		// LOGGER.info("cargarGrupoAspectos() - "
		// + encuestasMB.getSelected().getOid() == null);
		if (Utilidad.validaNulos(encuestasMB.getSelected().getOid())) {
			tituloGrupoAspectos();
			acConfigurar = false;
			acFechas = false;
			acPreVisualizar = false;
			acSedesProgramas = false;
			acRegresar = true;
			acAspectos = true;
			mostrarAspectos = false;
			mostrarEncuestas = false;
			mostrarGrupoAspectos = true;
			LOGGER.info("cargarGrupoAspectos() ");
			grupoAspectoMB.cargarTodos();
		} else {
			msgNoSeleccionado();
		}
	}

	public void cargarPrevisualizacion() {
		if (Utilidad.validaNulos(encuestasMB.getSelected().getOid())) {
			tituloPrevisualizar();
			acConfigurar = false;
			acFechas = false;
			acPreVisualizar = false;
			acSedesProgramas = false;
			acRegresar = true;
			acEditar = false;
			acEliminar = false;
			acCrear = false;
			acAspectos = false;
			mostrarAspectos = false;
			mostrarEncuestas = false;
			mostrarGrupoAspectos = false;
			mostrarPrevisualizar = true;
			LOGGER.info("cargarPREVISUALIZACIÓN() ");
			previsualizarMB.cargarEncuesta();
		} else {
			msgNoSeleccionado();
		}
	}

	public void cargarAspectos() {
		if (Utilidad.validaNulos(encuestasMB.getSelected().getOid())) {
			tituloAspectos();
			acConfigurar = false;
			acFechas = false;
			acSedesProgramas = false;
			acPreVisualizar = false;
			acRegresar = true;
			acAspectos = false;
			mostrarEncuestas = false;
			mostrarGrupoAspectos = false;
			mostrarAspectos = true;
			LOGGER.info("cargarAspectos() ");
			aspectoMB.cargarTodos();
			aspectoMB.cargarTipoAspectos();
		} else {
			msgNoSeleccionado();
		}
	}

	public void cargarTipoEncuestaSeleccionada(TblencTipoEvaluacion eval) {
		LOGGER.info("cargarTipoEncuestaSeleccionada - " + eval == null ? "vacio"
				: eval.getNombre() + "---------------------------");
		grupoAspectoMB.setTipoEvaluacionSelected(eval);
		fechasMB.setTipoEvaluacionSelected(eval);
		previsualizarMB.setTipoEvaluacionSelected(eval);
	}

	public void cargargrupoAspectoSeleccionado(TblencGrupoAspecto grupo) {
		LOGGER.info("cargarTipoEncuestaSeleccionada - " + grupo == null ? "vacio"
				: grupo.getNombre() + "---------------------------");
		aspectoMB.setGrupoAspectoSelected(grupo);
	}

	public void cargarProgramacionSedesProgramas(
			TblencProgramacionEncuesta programacion) {
		spProgramadasMB.setProgramacionSelected(programacion);
	}

	/**
	 * Método quwe controla la redirección del botón crear dependia¿endo de la
	 * vista desplegada
	 */
	public void lanzarCrear(boolean val) {
		if (mostrarEncuestas && !mostrarGrupoAspectos && !mostrarAspectos
				&& !mostrarFechas && !mostrarPrevisualizar) {
			LOGGER.info("1");
			encuestasMB.lanzarCrear(val);
		} else if (!mostrarEncuestas && mostrarGrupoAspectos
				&& !mostrarAspectos && !mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("2");
			grupoAspectoMB.lanzarCrear(val);
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& mostrarAspectos && !mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("3");
			aspectoMB.lanzarCrear(val);
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& !mostrarAspectos && mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("4");
			fechasMB.lanzarCrear(val);
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& !mostrarAspectos && !mostrarFechas && mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("5");
			aspectoMB.lanzarCrear(val);
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& !mostrarAspectos && !mostrarFechas && !mostrarPrevisualizar
				&& mostrarSedeprogramas) {
			LOGGER.info("6");
			spProgramadasMB.lanzarCrear(val);
		} else {
			LOGGER.info("lanzarEditar Else" + val);
		}
	}

	/**
	 * Método quwe controla la redirección del botón ediar dependia¿endo de la
	 * vista desplegada
	 */
	public void lanzarEditar(boolean val) {
		if (mostrarEncuestas && !mostrarGrupoAspectos && !mostrarAspectos
				&& !mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("1");
			encuestasMB.lanzarEditar(val);
		} else if (!mostrarEncuestas && mostrarGrupoAspectos
				&& !mostrarAspectos && !mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("2");
			grupoAspectoMB.lanzarEditar(val);
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& mostrarAspectos && !mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("3");
			aspectoMB.lanzarEditar(val);
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& !mostrarAspectos && mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("4");
			fechasMB.lanzarEditar(val);
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& !mostrarAspectos && !mostrarFechas && mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("5");
			aspectoMB.lanzarEditar(val);
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& !mostrarAspectos && !mostrarFechas && !mostrarPrevisualizar
				&& mostrarSedeprogramas) {
			LOGGER.info("6");
			spProgramadasMB.lanzarCrear(val);
		} else {
			LOGGER.info("lanzarEditar Else" + val);
		}
	}

	/**
	 * Método quwe controla la redirección del botón regresar dependia¿endo de
	 * la vista desplegada
	 */
	public void lanzarRegresar() {
		if (!mostrarEncuestas && mostrarGrupoAspectos && !mostrarAspectos
				&& !mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("1");
			cargarTipoEvaluacion();
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& mostrarAspectos && !mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("2");
			cargarGrupoAspectos();
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& !mostrarAspectos && mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("3");
			cargarTipoEvaluacion();
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& !mostrarAspectos && !mostrarFechas && mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("4");
			cargarTipoEvaluacion();
			acCrear = true;
			acEditar = true;
			acEliminar = true;
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& !mostrarAspectos && !mostrarFechas && !mostrarPrevisualizar
				&& mostrarSedeprogramas) {
			LOGGER.info("5");
			cargarFechas();
			acEditar = true;
			acEliminar = true;
		}
	}

	/**
	 * Método quwe controla la redirección del botón eliminar dependia¿endo de
	 * la vista desplegada
	 */
	public void lanzarEliminar() {
		if (mostrarEncuestas && !mostrarGrupoAspectos && !mostrarAspectos
				&& !mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("1");
			encuestasMB.eliminar();
		} else if (!mostrarEncuestas && mostrarGrupoAspectos
				&& !mostrarAspectos && !mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("2");
			grupoAspectoMB.eliminar();
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& mostrarAspectos && !mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("3");
			aspectoMB.eliminar();
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& !mostrarAspectos && mostrarFechas && !mostrarPrevisualizar
				&& !mostrarSedeprogramas) {
			LOGGER.info("4");
			fechasMB.eliminar();
		} else if (!mostrarEncuestas && !mostrarGrupoAspectos
				&& !mostrarAspectos && !mostrarFechas && !mostrarPrevisualizar
				&& mostrarSedeprogramas) {
			LOGGER.info("5");
			spProgramadasMB.eliminar();
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
			if (permiso.equals(Cfg.CONFIGURAR)) {
				acConfigurar = true;
			}
			if (permiso.equals(Cfg.ASPECTOS)) {
				acConfigurar = true;
			}
			if (permiso.equals(Cfg.PROGRAMAR)) {
				acFechas = true;
			}
			if (permiso.equals(Cfg.PREVISUALIZAR)) {
				acPreVisualizar = true;
			}

		}
	}

	// Settes and Gettes
	public boolean isMostrarGrupoAspectos() {
		return mostrarGrupoAspectos;
	}

	public void setMostrarGrupoAspectos(boolean mostrarGrupoAspectos) {
		this.mostrarGrupoAspectos = mostrarGrupoAspectos;
	}

	public EncuestasMB getEncuestasMB() {
		return encuestasMB;
	}

	public void setEncuestasMB(EncuestasMB encuestasMB) {
		this.encuestasMB = encuestasMB;
	}

	public GrupoAspectosMB getGrupoAspectoMB() {
		return grupoAspectoMB;
	}

	public void setGrupoAspectoMB(GrupoAspectosMB grupoAspectoMB) {
		this.grupoAspectoMB = grupoAspectoMB;
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

	public boolean isAcConfigurar() {
		return acConfigurar;
	}

	public void setAcConfigurar(boolean acConfigurar) {
		this.acConfigurar = acConfigurar;
	}

	public boolean isMostrarEncuestas() {
		return mostrarEncuestas;
	}

	public void setMostrarEncuestas(boolean mostrarEncuestas) {
		this.mostrarEncuestas = mostrarEncuestas;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isAcAspectos() {
		return acAspectos;
	}

	public void setAcAspectos(boolean acAspectos) {
		this.acAspectos = acAspectos;
	}

	public boolean isAcFechas() {
		return acFechas;
	}

	public void setAcFechas(boolean acFechas) {
		this.acFechas = acFechas;
	}

	public boolean isAcPreVisualizar() {
		return acPreVisualizar;
	}

	public void setAcPreVisualizar(boolean acPreVisualizar) {
		this.acPreVisualizar = acPreVisualizar;
	}

	public AspectosMB getAspectoMB() {
		return aspectoMB;
	}

	public void setAspectoMB(AspectosMB aspectoMB) {
		this.aspectoMB = aspectoMB;
	}

	public boolean isMostrarAspectos() {
		return mostrarAspectos;
	}

	public void setMostrarAspectos(boolean mostrarAspectos) {
		this.mostrarAspectos = mostrarAspectos;
	}

	public boolean isMostrarPrevisualizar() {
		return mostrarPrevisualizar;
	}

	public void setMostrarPrevisualizar(boolean mostrarPrevisualizar) {
		this.mostrarPrevisualizar = mostrarPrevisualizar;
	}

	public FechasMB getFechasMB() {
		return fechasMB;
	}

	public void setFechasMB(FechasMB fechasMB) {
		this.fechasMB = fechasMB;
	}

	public PrevisualizarMB getPrevisualizarMB() {
		return previsualizarMB;
	}

	public void setPrevisualizarMB(PrevisualizarMB previsualizarMB) {
		this.previsualizarMB = previsualizarMB;
	}

	public EncuestaSedeProgramaMB getEncSedeProgramaMB() {
		return encSedeProgramaMB;
	}

	public void setEncSedeProgramaMB(EncuestaSedeProgramaMB encSedeProgramaMB) {
		this.encSedeProgramaMB = encSedeProgramaMB;
	}

	public boolean isMostrarSedeprogramas() {
		return mostrarSedeprogramas;
	}

	public void setMostrarSedeprogramas(boolean mostrarSedeprogramas) {
		this.mostrarSedeprogramas = mostrarSedeprogramas;
	}

	public SedesProgramasProgramadasMB getSpProgramadasMB() {
		return spProgramadasMB;
	}

	public void setSpProgramadasMB(SedesProgramasProgramadasMB spProgramadasMB) {
		this.spProgramadasMB = spProgramadasMB;
	}

	public boolean isAcSedesProgramas() {
		return acSedesProgramas;
	}

	public void setAcSedesProgramas(boolean acSedesProgramas) {
		this.acSedesProgramas = acSedesProgramas;
	}

}
