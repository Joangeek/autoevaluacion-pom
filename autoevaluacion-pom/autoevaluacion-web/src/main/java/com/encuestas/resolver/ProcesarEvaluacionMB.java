package com.encuestas.resolver;

import docente.TbldocDocenteH;
import encuestas.TblencAspecto;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencOpcione;
import encuestas.TblencProgramacionEncuesta;
import encuestas.util.ResultadoEncuesta;
import gestion.Util;

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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import talentoHumano.TblthParticipante;
import util.BOException;
import academico.TblacaPrograma;
import academico.TblacaSede;

import com.academico.PeriodoBO;

import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;

import com.encuesta.ProcesarEvaluacionBO;
import com.encuesta.TipoAspectoBO;
import com.encuesta.TipoEvaluacionBO;
import com.gestion.ParametrosBO;

@ManagedBean(name = "procesarEvaluacionMB")
@ViewScoped
public class ProcesarEvaluacionMB extends BaseControlador implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5501186905206614466L;

	private static final String ADMINISTRATIVOS = "ADMINISTRATIVOS";
	private static final String DIRECTIVOS = "DIRECTIVOS";
	private static final String DOCENTES = "DOCENTES";
	private String user = Util.getUserSession();

	/**
	 * ingreso como profesor
	 */
	private static final String VAR_P = "p";
	/**
	 * ingreso como colaborador administrativos
	 */
	private static final String VAR_A = "c";
	/**
	 * ingreso como director directivo
	 */
	private static final String VAR_D = "d";
	/**
	 * 
	 */

	private String tituloPage = obtenerResourceBundle(ENCUESTA).getString(
			"tit.page.resolver");

	private Date fehcaHoy = new Date();
	// EJB
	@EJB
	private TipoEvaluacionBO tipoEvaluacionBO;
	@EJB/**/
	private ParametrosBO parametrosBO;
	@EJB
	private TipoAspectoBO tipoAspectoBO;
	@EJB
	private PeriodoBO periodoBO;
	@EJB
	private ProcesarEvaluacionBO procesarEvaluacionBO;

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
	private TblthParticipante participante;

	private String ingreso;
	@SuppressWarnings("unused")
	private String usuarioOid;

	private boolean mostrarMSG;

	private Map<String, String> param;

	private TblacaPrograma programa;

	private TblacaSede sede;

	@PostConstruct
	public void init() throws BOException {
		try {
			LOGGER.debug("INIT CONTROLADOR");
			participante = procesarEvaluacionBO.buscarParticipanteOid(Util
					.getOidAdmin());
			inicializar();
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			param = ec.getRequestParameterMap();
			ingreso = new String();
			lookup();
			consultarEncuestas();
			setMostrarMSG(false);

		} catch (Exception ex) {
			ex.printStackTrace();
			// lanzarMensajeError(null, ex);
		}

	}

	public void inicializar() {
		mostrarVisualizacion = false;
		setCargando(false);
		mapa = new HashMap<TblencAspecto, String>();
		setListaOpciones(new ArrayList<TblencOpcione>());
		listaPreguntas = new ArrayList<TblencAspecto>();
		selected = new TblencProgramacionEncuesta();
		listaEntuestas = new ArrayList<TblencProgramacionEncuesta>();
	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {
			tipoEvaluacionBO = LookupUtil
					.lookupRemoteStateless(TipoEvaluacionBO.class);
			//parametrosBO = LookupUtil.lookupRemoteStateless(ParametrosBO.class);

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
			LOGGER.info("INGRESO COMO =  " + (ingreso == null));
			if (Utilidad.validaNulos(ingreso)) {

				ingreso = param.get("id").toString();
				LOGGER.info("INGRESO COMO =  " + ingreso);
			}
			if (ingreso.equals(VAR_A)) {
				// INGRESO COMO COLABORADOR ADMINISTRATIVO
				cargarEncuesta(tipoEvaluacionBO
						.buscarDirigidoA(ADMINISTRATIVOS).getOid());
			} else if (ingreso.equals(VAR_D) && consultarSedePrograma()) {
				// INGRESO COMO DIRECTIVO DIRECTOR DE PROGRAMA
				cargarEncuesta(tipoEvaluacionBO.buscarDirigidoA(DIRECTIVOS)
						.getOid());
			} else if (ingreso.equals(VAR_P) && consultarSedePrograma()) {
				consultarSedePrograma();
				// INGRESO COMO DOCENTE
				cargarEncuesta(tipoEvaluacionBO.buscarDirigidoA(DOCENTES)
						.getOid());
			}

		} catch (NullPointerException e) {
			LOGGER.info("Null Pointer - consultarEncuestas");
			// e.printStackTrace();
		} catch (Exception e) {
			LOGGER.info("Exception - consultarEncuestas");
		}
	}

	private boolean consultarSedePrograma() {

		boolean correcto = false;
		try {
			TbldocDocenteH docente = procesarEvaluacionBO.buscarDocente(
					UtilidadBean.serialize(participante),
					UtilidadBean.serialize(periodoBO.buscarEstado(true)));
			sede = docente.getTblacaSede();
			programa = docente.getTblacaPrograma();
			correcto = true;
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			lanzarMensajeError(obtenerResourceBundle(ENCUESTA).getString(
					"msg.warn.encuestas.sin.sede.programa"));
		} catch (Exception e) {
			lanzarMensajeError(obtenerResourceBundle(COMUN).getString(
					"global.msg.error.exception"));
		}
		return correcto;
	}

	/**
	 * Método que consulta todos los datos relacionados a la entidad
	 */
	private void cargarEncuesta(Integer dirigidoA) {
		try {
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

			// LOGGER.info("cargarGruposAspectos : " + getListaGrupos().size());
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarGruposAspectos ");
		} catch (NullPointerException e) {
			LOGGER.info(" cargarGruposAspectos()  Null P ct ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarDatos: " + e.getMessage(), e);
		}
	}

	public List<TblencAspecto> cargarDatos(TblencGrupoAspecto grupo) {
		try {

			if (grupo.getOid() != null) {
				listaPreguntas = new ArrayList<TblencAspecto>();

				listaPreguntas = tipoAspectoBO.buscarPorGrupo(UtilidadBean
						.serialize(grupo));
				// LOGGER.info("tamaño: Grupo: " +
				// listaGrupos.size()+" preguntas"+listaPreguntas.size());
				return listaPreguntas;
			}
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			LOGGER.info(" listaPreguntasNull P ct ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarDatos: " + e.getMessage(), e);
		}
		return null;

	}

	public void mostarEncuesta() {

		try {
			if (Utilidad.validaNulos(selected)) {
				LOGGER.info("Nombre Encuestaa=  "
						+ selected.getTblencTipoEvaluacion().getNombre());
				if (tipoEvaluacionBO.buscarEncuestaRealizada(
						UtilidadBean.serialize(selected),
						UtilidadBean.serialize(participante)) == 0) {
					mostrarVisualizacion = true;
					cargarGruposAspectos();
					ejecutarUpdate("pnlEncuesta");

				} else {
					lanzarMensajeInfo(obtenerResourceBundle(ENCUESTA)
							.getString("msg.encuesta.ya.realizada"));
				}
			}

		} catch (Exception e) {
			LOGGER.info("Exception mostarEncuesta()  ");
		}
	}

	public void obtenerCalificacines() {
		try {
			LOGGER.info("MAPA SIZE=  " + mapa.size());

			if (registrarResultados()) {
				inicializar();
				consultarEncuestas();
				lanzarMensajeInfo(obtenerResourceBundle(ENCUESTA).getString(
						"msg.exito.encuestas.registro"));
				LOGGER.info("msg.exito.encuestas.registro");
			} else {
				lanzarMensajeWarn(obtenerResourceBundle(ENCUESTA).getString(
						"msg.error.encuestas.no.registro"));
			}

			setCargando(true);
			setMostrarVisualizacion(false);
		} catch (NullPointerException e) {
			LOGGER.info(" NullPointerException -->obtenerCalificacines");
		} catch (Exception e) {
			LOGGER.info(" Exception -->obtenerCalificacines");
		}
	}

	private boolean registrarResultados() {
		try {

			Date fechaFinalizacion = new Date();
			return procesarEvaluacionBO.registrar(UtilidadBean.serialize(mapa),
					UtilidadBean.serialize(participante), null, null,
					UtilidadBean.serialize(selected),
					UtilidadBean.serialize(sede),
					UtilidadBean.serialize(programa), null, fechaFinalizacion,
					user);
		} catch (BOException e) {
			lanzarMensajeWarn(obtenerResourceBundle(ENCUESTA).getString(
					"msg.warn.encuestas.no.registro"));
		} catch (Exception e) {
			lanzarMensajeWarn(obtenerResourceBundle(ENCUESTA).getString(
					"msg.error.encuestas.no.registro"));
		}
		return true;
	}

	public List<TblencOpcione> cargarOpciones(TblencAspecto aspecto)
			throws BOException {
		return setListaOpciones(tipoAspectoBO.buscarOpcAspecto(UtilidadBean
				.serialize(aspecto.getTblencTipoAspecto())));

	}

	public void home() {
		inicializar();
		LOGGER.info("  home()");
		Utilidad.redireccionarDir("/admin_acceso.xhtml");

	}

	public void cerrarVerRespuestas() {
		setCargando(false);
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

}
