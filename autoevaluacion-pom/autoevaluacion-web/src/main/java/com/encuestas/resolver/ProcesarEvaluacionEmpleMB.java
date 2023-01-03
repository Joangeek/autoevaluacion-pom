package com.encuestas.resolver;

import encuestas.TblencAspecto;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencOpcione;
import encuestas.TblencProgramacionEncuesta;
import encuestas.util.ResultadoEncuesta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import com.academico.PeriodoBO;

import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;

import com.encuesta.ProcesarEvaluacionBO;
import com.encuesta.TipoAspectoBO;
import com.encuesta.TipoEvaluacionBO;
import com.gestion.ParametrosBO;

import util.BOException;
import gestion.Util;
import comun.Tblempleadore;

@ManagedBean(name = "procesarEvaluacionEmpleMB")
@ViewScoped
public class ProcesarEvaluacionEmpleMB extends BaseControlador implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5501186905206614466L;

	private static final String EMPLEADOR = "0004";
	private String user = Util.getUserSession();

	private String tituloPage = obtenerResourceBundle(ENCUESTA).getString(
			"tit.page.resolver");

	private Date fehcaHoy = new Date();
	// EJB
	@EJB
	private TipoEvaluacionBO tipoEvaluacionBO;
	@EJB
	private ParametrosBO parametrosBO;
	@EJB
	private TipoAspectoBO tipoAspectoBO;
	@EJB
	private ProcesarEvaluacionBO procesarEvaluacionBO;
	@EJB
	private PeriodoBO periodoBO;

	// VARIABLES

	private TblencProgramacionEncuesta selected;
	private List<TblencProgramacionEncuesta> listaEntuestas;

	private List<TblencOpcione> listaOpciones;

	private List<TblencGrupoAspecto> listaGrupos;

	private boolean mostrarVisualizacion;
	private boolean cargando;

	private Map<TblencAspecto, String> mapa;
	private List<ResultadoEncuesta> listaResultadoEncuesta;
	private List<TblencAspecto> listaPreguntas;
	private Tblempleadore empleador;

	@SuppressWarnings("unused")
	private String usuarioOid;

	private boolean mostrarMSG;

	@PostConstruct
	public void init() throws BOException {
		try {
			LOGGER.info("INIT CONTROLADOR EMPLE - " + Util.getOidAdmin());
			lookup();
			empleador = procesarEvaluacionBO.buscarEmpleadorOid(Util
					.getOidAdmin());
			
			inicializar();
		
			consultarEncuestas();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
			// lanzarMensajeError(null, ex);
		}

	}

	public void inicializar() {
		mostrarMSG = false;
		mostrarVisualizacion = false;
		setCargando(false);
		listaEntuestas = new ArrayList<TblencProgramacionEncuesta>();
		mapa = new HashMap<TblencAspecto, String>();
		setListaOpciones(new ArrayList<TblencOpcione>());
		listaPreguntas = new ArrayList<TblencAspecto>();
		listaGrupos = new ArrayList<TblencGrupoAspecto>();
		selected = new TblencProgramacionEncuesta();
	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {
			tipoEvaluacionBO = LookupUtil
					.lookupRemoteStateless(TipoEvaluacionBO.class);
		//	parametrosBO = LookupUtil.lookupRemoteStateless(ParametrosBO.class);

			tipoAspectoBO = LookupUtil
					.lookupRemoteStateless(TipoAspectoBO.class);

			procesarEvaluacionBO = LookupUtil
					.lookupRemoteStateless(ProcesarEvaluacionBO.class);

			periodoBO = LookupUtil.lookupRemoteStateless(PeriodoBO.class);

			// ...
		} catch (NamingException e) {
			// ...
		}
	}

	/**
	 * Método que optiene a variable de la URL para identificar perfil de
	 * ingreso, con lo anterior valida los tipos a quien van DIRIGIDAS LAS
	 * ENVUESTAS
	 * 
	 * @throws BOException
	 */
	private void consultarEncuestas() {
		try {
			cargarEncuesta(tipoEvaluacionBO.buscarDirigidoA(
					parametrosBO.buscarCodigo(EMPLEADOR).getValString())
					.getOid());

		} catch (Exception e) {
			LOGGER.info("Exception -->consultarEncuestas()", e);
		}
	}

	/**
	 * Método que consulta todos los datos relacionados a la entidad
	 */
	private void cargarEncuesta(Integer dirigidoA) {
		try {

			LOGGER.info("cargarEncuesta");
			listaEntuestas = tipoEvaluacionBO.buscarEncuesta(fehcaHoy,
					dirigidoA);

		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			lanzarMensajeError(obtenerResourceBundle(COMUN).getString(
					"global.msg.error.exception"));
		} catch (Exception e) {
			lanzarMensajeError(obtenerResourceBundle(COMUN).getString(
					"global.msg.error.exception"));
		}

	}

	private void cargarGruposAspectos() {
		try {
			setListaGrupos(tipoAspectoBO.gruposPorEncuesta(UtilidadBean
					.serialize(selected.getTblencTipoEvaluacion())));

			LOGGER.info("cargarGruposAspectos : " + getListaGrupos().size());
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarGruposAspectos ");
		} catch (NullPointerException e) {
			LOGGER.info(" cargarGruposAspectos()  Null P ct ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarDatos: " + e.getMessage(), e);
		}
	}

	public List<TblencAspecto> cargarDatos(TblencGrupoAspecto grupo) {

		//LOGGER.info("tamaño Grupo: " + listaGrupos.size());
		try {

			if (grupo.getOid() != null) {
				listaPreguntas = new ArrayList<TblencAspecto>();

				listaPreguntas = tipoAspectoBO.buscarPorGrupo(UtilidadBean
						.serialize(grupo));

				//LOGGER.info("tamaño Grupo: " + listaPreguntas.size());
				return listaPreguntas;
			}
		} catch (NullPointerException e) {
			LOGGER.info(" cargarDatos P ct ");
			// e.printStackTrace();
		} catch (Exception e) {
			LOGGER.info("Error al cargarDatos: " + e.getMessage(), e);
		}
		return null;

	}

	public void mostarEncuesta() throws BOException {
		try {

			/*
			 * if (tipoEvaluacionBO.EncuestaRealizadaEmpleador(
			 * UtilidadBean.serialize(selected),
			 * UtilidadBean.serialize(empleador)) > 0) {
			 */
			mostrarVisualizacion = true;
			cargarGruposAspectos();
			ejecutarUpdate("pnlEncuesta");

			/*
			 * } else {
			 * lanzarMensajeInfo(obtenerResourceBundle(ENCUESTA).getString(
			 * "msg.encuesta.ya.realizada")); }
			 */

		} catch (Exception e) {
			LOGGER.info("Exception --> mostarEncuesta()", e);
		}
	}

	/**
	 * Método para encuesta estudiantes, valida matrícula en el periodo vigente
	 * y realización
	 * 
	 * @throws BOException
	 */

	public void obtenerCalificacines() {
		try {
			if (registrarResultados()) {
				LOGGER.info("EXITO");
				inicializar();
				lanzarMensajeInfo(obtenerResourceBundle(ENCUESTA).getString(
						"msg.exito.encuestas.registro"));
				consultarEncuestas();
			} else {
				lanzarMensajeWarn(obtenerResourceBundle(ENCUESTA).getString(
						"msg.error.encuestas.no.registro"));
			}

			setCargando(true);
			setMostrarVisualizacion(false);
		} catch (Exception e) {
			LOGGER.info("Exception --> obtenerCalificacines() ");
		}
	}

	private boolean registrarResultados() {
		try {
			
			Date fechaFinalizacion = new Date();

			return procesarEvaluacionBO.registrar(UtilidadBean.serialize(mapa),
					UtilidadBean.serialize(empleador),
					UtilidadBean.serialize(selected), fechaFinalizacion, user);
		} catch (BOException e) {

			lanzarMensajeWarn(obtenerResourceBundle(ENCUESTA).getString(
					"msg.warn.encuestas.no.registro"));
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			lanzarMensajeWarn(obtenerResourceBundle(ENCUESTA).getString(
					"msg.error.encuestas.no.registro"));
			return false;
		}

		
	}

	public List<TblencOpcione> cargarOpciones(TblencAspecto aspecto)
			throws BOException {
		return setListaOpciones(tipoAspectoBO.buscarOpcAspecto(UtilidadBean
				.serialize(aspecto.getTblencTipoAspecto())));

	}

	public void cerrarVerRespuestas() {
		setCargando(false);
	}

	public void home() {
		Utilidad.redireccionarDir("/empleador.xhtml");
		inicializar();
	}

	// SETTES AND GETTES

	public boolean isMostrarVisualizacion() {
		return mostrarVisualizacion;
	}

	public TblencProgramacionEncuesta getSelected() {
		return selected;
	}

	public void setSelected(TblencProgramacionEncuesta selected) {
		this.selected = selected;
	}

	public List<TblencProgramacionEncuesta> getListaEntuestas() {
		return listaEntuestas;
	}

	public void setListaEntuestas(
			List<TblencProgramacionEncuesta> listaEntuestas) {
		this.listaEntuestas = listaEntuestas;
	}

	public void setMostrarVisualizacion(boolean mostrarVisualizacion) {
		this.mostrarVisualizacion = mostrarVisualizacion;
	}

	public Map<TblencAspecto, String> getMapa() {
		return mapa;
	}

	public void setMapa(Map<TblencAspecto, String> mapa) {
		this.mapa = mapa;
	}

	public List<TblencOpcione> getListaOpciones() {
		return listaOpciones;
	}

	public List<TblencOpcione> setListaOpciones(
			List<TblencOpcione> listaOpciones) {
		this.listaOpciones = listaOpciones;
		return listaOpciones;
	}

	public List<ResultadoEncuesta> getListaResultadoEncuesta() {
		return listaResultadoEncuesta;
	}

	public void setListaResultadoEncuesta(
			List<ResultadoEncuesta> listaResultadoEncuesta) {
		this.listaResultadoEncuesta = listaResultadoEncuesta;
	}

	public List<TblencGrupoAspecto> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<TblencGrupoAspecto> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public String getTituloPage() {
		return tituloPage;
	}

	public void setTituloPage(String tituloPage) {
		this.tituloPage = tituloPage;
	}

	public boolean isCargando() {
		return cargando;
	}

	public void setCargando(boolean cargando) {
		this.cargando = cargando;
	}

	public boolean isMostrarMSG() {
		return mostrarMSG;
	}

	public void setMostrarMSG(boolean mostrarMSG) {
		this.mostrarMSG = mostrarMSG;
	}

	public List<TblencAspecto> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(List<TblencAspecto> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}

}
