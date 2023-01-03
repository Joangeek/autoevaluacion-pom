package com.encuestas.informes;

import encuestas.TblencAspecto;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencOpcione;
import encuestas.TblencTipoEvaluacion;
import encuestas.util.ReporteEncuestaRealizadas;
import encuestas.util.ReporteEncuestaResultados;
import encuestas.util.ReporteEncuestaResultadosFinal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import org.primefaces.model.chart.PieChartModel;

import util.BOException;
import util.OpcionesInformes;
import academico.TblacaPeriodo;
import academico.TblacaPrograma;
import academico.TblacaSede;

import com.academico.PeriodoBO;
import com.academico.SedeProgramaBO;
import com.encuesta.TipoAspectoBO;
import com.encuesta.TipoEvaluacionBO;
import com.gestion.ParametrosBO;

import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;

@ManagedBean(name = "informesAutoevalMB")
@ViewScoped
public class informesAutoevalMB extends BaseControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3338740522931612793L;
	private static final String AUTOEVALUACION = "0007";
	private String tituloPage = obtenerResourceBundle(ENCUESTA).getString(
			"tit.page.informes");

	// EJB
	@EJB
	private TipoEvaluacionBO tipoEvaluacionBO;
	@EJB
	private ParametrosBO parametrosBO;
	@EJB
	private TipoAspectoBO tipoAspectoBO;
	@EJB
	private PeriodoBO periodoBO;
	@EJB
	private SedeProgramaBO sedeProgramaBO;

	// VARIABLES
	/**
	 * Lista class útil - lista las diferenets opciones de encuestas
	 */
	private List<OpcionesInformes> listaOpcionesReportes;
	private int modulo;
	private boolean mostrarGrid;
	private boolean mostrarDatos;
	private boolean mostrarResultados;
	private List<TblencGrupoAspecto> listaGrupos;
	private List<TblencTipoEvaluacion> listaEntuestas;
	private TblencTipoEvaluacion selected;
	private OpcionesInformes selectedOpc;
	private List<TblacaPeriodo> listaPeriodos;
	private TblacaPeriodo periodoSelected;
	private List<TblacaPrograma> listaProgramas;
	private TblacaPrograma programaSelected;
	private List<TblacaSede> listaSedes;
	private TblacaSede sedeSelected;
	private List<ReporteEncuestaRealizadas> listaReporteRealizados;
	private Integer realizadas;
	private boolean hayDatos;
	private List<TblencAspecto> listaPreguntas;
	private List<TblencOpcione> listaOpciones;
	private int total;
	private PieChartModel grafica;
	private boolean esEmpelador;
	private boolean esDocenteAdminDire;

	@PostConstruct
	public void init() throws BOException {
		try {
			LOGGER.debug("INIT CONTROLADOR");
			setListaSedes(new ArrayList<TblacaSede>());
			listaProgramas = new ArrayList<TblacaPrograma>();
			setListaPeriodos(new ArrayList<TblacaPeriodo>());

			modulo = parametrosBO.buscarCodigo(AUTOEVALUACION).getVal_int();
			inicializar();
			cargarListas();
			lookup();
		} catch (Exception ex) {
			lanzarMensajeError(null, ex);
		}

	}

	public void inicializar() {
		setMostrarGrid(false);
		setMostrarDatos(false);
		setMostrarResultados(false);
		setEsEmpelador(false);
		setEsDocenteAdminDire(false);

		setRealizadas(new Integer(0));
		cargarOpciones();
		listaEntuestas = new ArrayList<TblencTipoEvaluacion>();
		listaReporteRealizados = new ArrayList<ReporteEncuestaRealizadas>();
		listaGrupos = new ArrayList<TblencGrupoAspecto>();
		inicializarSelecteds();
	}

	private void cargarListas() {
		try {
			listaSedes = sedeProgramaBO.buscarSedes();
			listaProgramas = sedeProgramaBO.buscarProgramas();
			listaPeriodos = periodoBO.buscarTodos();

		} catch (BOException e) {
			LOGGER.info("cargarListas() Exception");
		} catch (Exception e) {
			LOGGER.info("cargarListas() AutoevaluacionBOException");
		}
	}

	private void cargarOpciones() {
		listaOpcionesReportes = new ArrayList<OpcionesInformes>();
		for (int i = 0; i <= Integer.parseInt(obtenerResourceBundle(ENCUESTA)
				.getString("opc.auto.cantidad")); i++) {

			OpcionesInformes opciones = new OpcionesInformes();
			opciones.setNumero(i);
			if (i == 0)
				opciones.setDescripcion(obtenerResourceBundle(ENCUESTA)
						.getString("opc.auto.uno"));
			else if (i == 1)
				opciones.setDescripcion(obtenerResourceBundle(ENCUESTA)
						.getString("opc.auto.dos"));

			listaOpcionesReportes.add(i, opciones);

		}

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

			periodoBO = LookupUtil.lookupRemoteStateless(PeriodoBO.class);

			sedeProgramaBO = LookupUtil
					.lookupRemoteStateless(SedeProgramaBO.class);
		} catch (NamingException e) {
			// ...
		}
	}

	/**
	 * Método que carga el listado de encuetas activas para el módulo
	 * (autoevaluación)
	 * 
	 * @throws BOException
	 */
	public void listarReportes() throws BOException {
		listaEntuestas = tipoEvaluacionBO.buscarEstadoByModulo(true, modulo);

		setMostrarGrid(true);
		setMostrarDatos(false);
		setMostrarResultados(false);
		setHayDatos(false);
		setEsEmpelador(false);
		setEsDocenteAdminDire(false);
		inicializarSelecteds();

		if (selectedOpc.getNumero() == 0) {
			// LOGGER.info("listarReportes 0-" + selectedOpc.getNumero());
			setRealizadas(0);
		} else if (selectedOpc.getNumero() == 1) {
			LOGGER.info("listarReportes 1-" + selectedOpc.getNumero());
			setRealizadas(1);
		}

	}

	private void inicializarSelecteds() {
		selected = new TblencTipoEvaluacion();
		periodoSelected = new TblacaPeriodo();
		sedeSelected = new TblacaSede();
		programaSelected = new TblacaPrograma();

	}

	public void generar() throws BOException {

		if (selectedOpc.getNumero() == 0) {

			generarReportRealizadas();
			setMostrarDatos(true);
		} else if (selectedOpc.getNumero() == 1) {
			cargarGruposAspectos();
			setMostrarResultados(true);
		}

		if (listaReporteRealizados.size() > 0 || listaGrupos.size() > 0) {
			setHayDatos(true);
		} else {
			setHayDatos(false);
		}
	}

	private void generarReportRealizadas() throws BOException {
		setListaReporteRealizados(new ArrayList<ReporteEncuestaRealizadas>());

		setEsEmpelador(false);
		setEsDocenteAdminDire(false);
		if (selected
				.getTblenc_dirigidoa()
				.getDescripcion()
				.equals(tipoEvaluacionBO.buscarDirigidoA("ESTUDIANTES")
						.getDescripcion())
				|| selected
						.getTblenc_dirigidoa()
						.getDescripcion()
						.equals(tipoEvaluacionBO.buscarDirigidoA("EGRESADOS")
								.getDescripcion())) {
			setListaReporteRealizados(tipoEvaluacionBO.buscarEstudiantes(
					UtilidadBean.serialize(selected),
					UtilidadBean.serialize(periodoSelected),
					UtilidadBean.serialize(sedeSelected),
					UtilidadBean.serialize(programaSelected)));

		} else if (selected
				.getTblenc_dirigidoa()
				.getDescripcion()
				.equals(tipoEvaluacionBO.buscarDirigidoA("ADMINISTRATIVOS")
						.getDescripcion())) {
			setListaReporteRealizados(tipoEvaluacionBO.buscarParticipantes(
					UtilidadBean.serialize(selected),
					UtilidadBean.serialize(periodoSelected),
					UtilidadBean.serialize(sedeSelected)));
			setEsDocenteAdminDire(true);
		} else if (selected
				.getTblenc_dirigidoa()
				.getDescripcion()
				.equals(tipoEvaluacionBO.buscarDirigidoA("DOCENTES")
						.getDescripcion())
				|| selected
						.getTblenc_dirigidoa()
						.getDescripcion()
						.equals(tipoEvaluacionBO.buscarDirigidoA("DIRECTIVOS")
								.getDescripcion())) {
			setListaReporteRealizados(tipoEvaluacionBO.buscarParticipantes(
					UtilidadBean.serialize(selected),
					UtilidadBean.serialize(periodoSelected),
					UtilidadBean.serialize(sedeSelected),UtilidadBean.serialize(programaSelected)));
			
		} else if (selected
				.getTblenc_dirigidoa()
				.getDescripcion()
				.equals(tipoEvaluacionBO.buscarDirigidoA("EMPLEADORES")
						.getDescripcion())) {
			setEsEmpelador(true);
			setListaReporteRealizados(tipoEvaluacionBO.buscarEmpleadores(
					UtilidadBean.serialize(selected),
					UtilidadBean.serialize(periodoSelected),
					UtilidadBean.serialize(sedeSelected),
					UtilidadBean.serialize(programaSelected)));

		} else {
			LOGGER.info("------------------------------"
					+ selected.getTblenc_dirigidoa().getDescripcion()
					+ "  --  "
					+ tipoEvaluacionBO.buscarDirigidoA("ESTUDIANTES")
							.getDescripcion());

		}
	}

	// ////////////////////// INFORME DE RESULTADOS ////////////////////////
	private void cargarGruposAspectos() {
		try {
			setListaGrupos(tipoAspectoBO.gruposPorEncuesta(UtilidadBean
					.serialize(selected)));

			// LOGGER.info("cargarGruposAspectos : " + getListaGrupos().size());
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarGruposAspectos ");
		} catch (NullPointerException e) {
			LOGGER.info(" cargarGruposAspectos Null P ct ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarDatos: " + e.getMessage(), e);
		}
	}

	public List<TblencAspecto> cargarDatos(TblencGrupoAspecto grupo) {
		try {
			// LOGGER.info("cargarDatos tamaño Grupo1: " + listaGrupos.size());
			if (grupo.getOid() != null) {

				listaPreguntas = tipoAspectoBO.buscarPorGrupo(UtilidadBean
						.serialize(grupo));
				return listaPreguntas;
			}
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			// LOGGER.info(" cargarDatos Null P ct ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarDatos: " + e.getMessage(), e);
		}
		return null;

	}

	public List<ReporteEncuestaResultados> cargarOpciones(TblencAspecto aspecto)
			throws BOException {
		/*
		 * LOGGER.info(
		 * "----------------------------------------------cargarOpciones: " +
		 * aspecto.getOid() + " -- " + selected.getOid() + " -- " +
		 * periodoSelected.getOid() + " -- "+(sedeSelected
		 * ==null)+"--"+(programaSelected==null));
		 */
		ReporteEncuestaResultadosFinal res = new ReporteEncuestaResultadosFinal();

		res = tipoAspectoBO.buscarOpcAspecto(UtilidadBean.serialize(aspecto),
				selected.getOid(), periodoSelected.getOid(),
				UtilidadBean.serialize(sedeSelected),
				UtilidadBean.serialize(programaSelected));

		graficar(res.getListaresultados());
		setTotal(res.getTotal());

		// LOGGER.info("----------------------------------------------");
		// LOGGER.info(res.getTotal());
		// LOGGER.info(res.getListaresultados().get(0).getCantidad());
		// LOGGER.info("----------------------------------------------");
		return res.getListaresultados();
	}

	private void graficar(List<ReporteEncuestaResultados> list) {
		grafica = new PieChartModel();

		for (ReporteEncuestaResultados val : list) {
			grafica.set(val.getNombre(), val.getCantidad());
		}
		grafica.setTitle(obtenerResourceBundle(ENCUESTA).getString(
				"msg.informe.resultados.titulo"));
		grafica.setLegendPosition("e");
		grafica.setFill(true);
		grafica.setShowDataLabels(true);
		grafica.setMouseoverHighlight(true);
		grafica.setMouseoverHighlight(true);
		
	}

	public void home() {
		inicializar();
		LOGGER.info("  home()");
		Utilidad.redireccionarDir("/admin_acceso.xhtml");

	}

	// SETTTES AND GETTES

	public String getTituloPage() {
		return tituloPage;
	}

	public void setTituloPage(String tituloPage) {
		this.tituloPage = tituloPage;
	}

	public boolean isMostrarGrid() {
		return mostrarGrid;
	}

	public void setMostrarGrid(boolean mostrarGrid) {
		this.mostrarGrid = mostrarGrid;
	}

	public OpcionesInformes getSelectedOpc() {
		return selectedOpc;
	}

	public void setSelectedOpc(OpcionesInformes selectedOpc) {
		this.selectedOpc = selectedOpc;
	}

	public TblacaPeriodo getPeriodoSelected() {
		return periodoSelected;
	}

	public void setPeriodoSelected(TblacaPeriodo periodoSelected) {
		this.periodoSelected = periodoSelected;
	}

	public List<TblacaPrograma> getListaProgramas() {
		return listaProgramas;
	}

	public void setListaProgramas(List<TblacaPrograma> listaProgramas) {
		this.listaProgramas = listaProgramas;
	}

	public List<TblacaPeriodo> getListaPeriodos() {
		return listaPeriodos;
	}

	public void setListaPeriodos(List<TblacaPeriodo> listaPeriodos) {
		this.listaPeriodos = listaPeriodos;
	}

	public List<TblacaSede> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<TblacaSede> listaSedes) {
		this.listaSedes = listaSedes;
	}

	public TblacaPrograma getProgramaSelected() {
		return programaSelected;
	}

	public void setProgramaSelected(TblacaPrograma programaSelected) {
		this.programaSelected = programaSelected;
	}

	public TblacaSede getSedeSelected() {
		return sedeSelected;
	}

	public void setSedeSelected(TblacaSede sedeSelected) {
		this.sedeSelected = sedeSelected;
	}

	public boolean isMostrarDatos() {
		return mostrarDatos;
	}

	public void setMostrarDatos(boolean mostrarDatos) {
		this.mostrarDatos = mostrarDatos;
	}

	public List<ReporteEncuestaRealizadas> getListaReporteRealizados() {
		return listaReporteRealizados;
	}

	public void setListaReporteRealizados(
			List<ReporteEncuestaRealizadas> listaReporteRealizados) {
		this.listaReporteRealizados = listaReporteRealizados;
	}

	public Integer getRealizadas() {
		return realizadas;
	}

	public void setRealizadas(Integer realizadas) {
		this.realizadas = realizadas;
	}

	public boolean isHayDatos() {
		return hayDatos;
	}

	public void setHayDatos(boolean hayDatos) {
		this.hayDatos = hayDatos;
	}

	public List<TblencTipoEvaluacion> getListaEntuestas() {
		return listaEntuestas;
	}

	public void setListaEntuestas(List<TblencTipoEvaluacion> listaEntuestas) {
		this.listaEntuestas = listaEntuestas;
	}

	public TblencTipoEvaluacion getSelected() {
		return selected;
	}

	public void setSelected(TblencTipoEvaluacion selected) {
		this.selected = selected;
	}

	public List<TblencGrupoAspecto> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<TblencGrupoAspecto> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public boolean isMostrarResultados() {
		return mostrarResultados;
	}

	public void setMostrarResultados(boolean mostrarResultados) {
		this.mostrarResultados = mostrarResultados;
	}

	public List<TblencAspecto> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(List<TblencAspecto> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}

	public List<OpcionesInformes> getListaOpcionesReportes() {
		return listaOpcionesReportes;
	}

	public void setListaOpcionesReportes(
			List<OpcionesInformes> listaOpcionesReportes) {
		this.listaOpcionesReportes = listaOpcionesReportes;
	}

	public List<TblencOpcione> getListaOpciones() {
		return listaOpciones;
	}

	public List<TblencOpcione> setListaOpciones(
			List<TblencOpcione> listaOpciones) {
		this.listaOpciones = listaOpciones;
		return listaOpciones;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public PieChartModel getGrafica() {
		return grafica;
	}

	public void setGrafica(PieChartModel grafica) {
		this.grafica = grafica;
	}

	public boolean isEsEmpelador() {
		return esEmpelador;
	}

	public void setEsEmpelador(boolean esEmpelador) {
		this.esEmpelador = esEmpelador;
	}

	public boolean isEsDocenteAdminDire() {
		return esDocenteAdminDire;
	}

	public void setEsDocenteAdminDire(boolean esDocenteAdminDire) {
		this.esDocenteAdminDire = esDocenteAdminDire;
	}

}