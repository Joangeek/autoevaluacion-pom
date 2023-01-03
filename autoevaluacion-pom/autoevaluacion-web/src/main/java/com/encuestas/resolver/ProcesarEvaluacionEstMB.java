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

import academico.TblestEstudiante;
import academico.TblestEstudiantePrograma;

import com.academico.MatriculadoBO;
import com.academico.PeriodoBO;
import com.academico.SedeProgramaBO;

import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.UtilidadGenerico;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;

import com.encuesta.ProcesarEvaluacionBO;
import com.encuesta.TipoAspectoBO;
import com.encuesta.TipoEvaluacionBO;
import com.gestion.ParametrosBO;

import util.BOException;
import gestion.Util;


@ManagedBean(name = "procesarEvaluacionEstMB")
@ViewScoped
public class ProcesarEvaluacionEstMB extends BaseControlador implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5501186905206614466L;

	private static final String ESTUDIANTE = "0002";
	private static final String EGRESADO = "0003";
	private String estudianteIngreso;
	private String egresadoIngreso;
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
	private SedeProgramaBO sedeProgramaBO;
	@EJB
	private PeriodoBO periodoBO;
	@EJB
	private MatriculadoBO matriculadoBO;

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
	private TblestEstudiante estudiante;
	private List<TblestEstudiantePrograma> listaSedesprogramas;
	private TblestEstudiantePrograma sedeProgramaSelected;

	private String ingreso;
	@SuppressWarnings("unused")
	private String usuarioOid;

	private boolean mostrarMSG; 

	@PostConstruct
	public void init() throws BOException {
		try {
			LOGGER.info("INIT CONTROLADOR" + Util.getOidAdmin());
			estudiante = procesarEvaluacionBO.buscarEstudioanteOid(Util
					.getOidAdmin());
			estudianteIngreso = parametrosBO.buscarCodigo(ESTUDIANTE)
					.getValString();
			egresadoIngreso = parametrosBO.buscarCodigo(EGRESADO)
					.getValString();
			inicializar();
			lookup();
			consultarEncuestas();
			cargarSedesprogramasEstudiante();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
			// lanzarMensajeError(null, ex);
		}

	}

	private void cargarSedesprogramasEstudiante() {
		try {
			setListaSedesprogramas(new ArrayList<TblestEstudiantePrograma>());

			if (ingreso.equals(estudianteIngreso)) {
				// INGRESO COMO ESTUDIANTES

				setListaSedesprogramas(sedeProgramaBO.buscarPorEstudiante(
						UtilidadBean.serialize(estudiante),
						UtilidadGenerico.epEstadoEnCurso));
			} else if (ingreso.equals(egresadoIngreso)) {
				// INGRESO COMO EGRESADO

				setListaSedesprogramas(sedeProgramaBO.buscarPorEstudiante(
						UtilidadBean.serialize(estudiante),
						UtilidadGenerico.epEstadoGraduado));
			}

			if (listaSedesprogramas.size() == 1) {
				setSedeProgramaSelected(listaSedesprogramas.get(0));
			}
		} catch (Exception e) {
			LOGGER.info("Error al cargarDatos: " + e.getMessage(), e);
		}

	}

	public void inicializar() {
		mostrarMSG = false;
		mostrarVisualizacion = false;
		setCargando(false);
		ingreso = new String();
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

			sedeProgramaBO = LookupUtil
					.lookupRemoteStateless(SedeProgramaBO.class);
			periodoBO = LookupUtil.lookupRemoteStateless(PeriodoBO.class);

			matriculadoBO = LookupUtil
					.lookupRemoteStateless(MatriculadoBO.class);
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
			ingreso = Util.getTipoUsuario();

			if (ingreso.equals(estudianteIngreso)) {
				// INGRESO COMO ESTUDIANTES
				cargarEncuesta(tipoEvaluacionBO.buscarDirigidoA(
						parametrosBO.buscarCodigo(ESTUDIANTE).getValString())
						.getOid());
			} else if (ingreso.equals(egresadoIngreso)) {
				// INGRESO COMO EGRESADO
				cargarEncuesta(tipoEvaluacionBO.buscarDirigidoA(
						parametrosBO.buscarCodigo(EGRESADO).getValString())
						.getOid());
			}

			LOGGER.info("INGRESO COMO =  " + ingreso);
		} catch (Exception e) {
			LOGGER.info("Exception -->consultarEncuestas()");
		}
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

		try {

			if (grupo.getOid() != null) {
				listaPreguntas = new ArrayList<TblencAspecto>();

				listaPreguntas = tipoAspectoBO.buscarPorGrupo(UtilidadBean
						.serialize(grupo));

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
			LOGGER.info("Nombre Encuestaa=  "
					+ selected.getTblencTipoEvaluacion().getNombre());

			if (ingreso.equals(estudianteIngreso)) {
				// INGRESO COMO ESTUDIANTES
				encuestaEstudiante();
			} else if (ingreso.equals(egresadoIngreso)) {
				// INGRESO COMO EGRESADO
				encuestaEgresado();
			}
		} catch (Exception e) {
			LOGGER.info("Exception --> mostarEncuesta()");
		}
	}

	/**
	 * Método para encuesta estudiantes, valida matrícula en el periodo vigente
	 * y realización
	 * 
	 * @throws BOException
	 */
	private void encuestaEstudiante() {
		try {
			if (matriculaPeriodoVigente()) {

				if (tipoEvaluacionBO.buscarEncuestaRealizadaEstudiante(
						UtilidadBean.serialize(selected),
						UtilidadBean.serialize(estudiante),
						sedeProgramaSelected.getTblacaSede().getOid(),
						sedeProgramaSelected.getTblacaPrograma().getOid()) == 0) {
					mostrarVisualizacion = true;
					cargarGruposAspectos();
					ejecutarUpdate("pnlEncuesta");

				} else {

					lanzarMensajeInfo(obtenerResourceBundle(ENCUESTA)
							.getString("msg.encuesta.ya.realizada"));
				}

			} else {
				LOGGER.info("*************************** no maTRICULADO");

				lanzarMensajeWarn(obtenerResourceBundle(ENCUESTA).getString(
						"msg.encuesta.solo.matriculados.periodo"));
			}

		} catch (Exception e) {
			LOGGER.info("Exception --> encuestaEstudiante()");
		}
	}

	/**
	 * Método paara encuestas de eegresados, únicamnete valida realización
	 * 
	 * @throws BOException
	 */
	private void encuestaEgresado() {
		// LOGGER.info("---------------------------------------------------------------------------------------"
		// + selected.getOid() + "--" +
		// estudiante.getOid()+"--"+sedeProgramaSelected.getTblacaSede().getOid()+"--"+sedeProgramaSelected.getTblacaPrograma().getOid());
		try {
			if (tipoEvaluacionBO.buscarEncuestaRealizadaEstudiante(UtilidadBean
					.serialize(selected), UtilidadBean.serialize(estudiante),
					sedeProgramaSelected.getTblacaSede().getOid(),
					sedeProgramaSelected.getTblacaPrograma().getOid()) == 0) {
				mostrarVisualizacion = true;
				cargarGruposAspectos();
				ejecutarUpdate("pnlEncuesta");

			} else {

				lanzarMensajeInfo(obtenerResourceBundle(ENCUESTA).getString(
						"msg.encuesta.ya.realizada"));
			}

		} catch (Exception e) {
			LOGGER.info(" Exception --> encuestaEgresado()");
		}

	}

	private boolean matriculaPeriodoVigente() {
		try {
			return matriculadoBO.validarEnPeriodo(sedeProgramaSelected
					.getIdEp(), periodoBO.buscarEstado(true).getOid());
		} catch (BOException e) {
			LOGGER.info("matriculaPeriodoVigente() - ", e);
		}
		return false;

	}

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
					null, UtilidadBean.serialize(estudiante), null,
					UtilidadBean.serialize(selected), null, null,
					UtilidadBean.serialize(sedeProgramaSelected),
					fechaFinalizacion, user);
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

	public void cerrarVerRespuestas() {
		setCargando(false);
	}

	public void home() {
		if (ingreso.equals(estudianteIngreso)) {
			// INGRESO COMO ESTUDIANTES
			Utilidad.redireccionarDir("/indexe.xhtml");
		} else if (ingreso.equals(egresadoIngreso)) {
			// INGRESO COMO EGRESADO
			Utilidad.redireccionarDir("/indexg.xhtml");
		}
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

	public List<TblestEstudiantePrograma> getListaSedesprogramas() {
		return listaSedesprogramas;
	}

	public void setListaSedesprogramas(
			List<TblestEstudiantePrograma> listaSedesprogramas) {
		this.listaSedesprogramas = listaSedesprogramas;
	}

	public TblestEstudiantePrograma getSedeProgramaSelected() {
		return sedeProgramaSelected;
	}

	public void setSedeProgramaSelected(
			TblestEstudiantePrograma sedeProgramaSelected) {
		this.sedeProgramaSelected = sedeProgramaSelected;
	}

}
