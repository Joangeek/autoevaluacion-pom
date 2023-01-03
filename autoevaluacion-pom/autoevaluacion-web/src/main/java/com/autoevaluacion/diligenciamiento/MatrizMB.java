package com.autoevaluacion.diligenciamiento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import academico.TblacaSede;
import academico.TblacaSedePrograma;
import autoevaluacion.TblautCaracteristica;
import autoevaluacion.TblautCaracteristicaVigencia;
import autoevaluacion.TblautFactorVigencia;
import autoevaluacion.TblautFactore;
import autoevaluacion.TblautFuente;
import autoevaluacion.TblautIndicadorVigencia;
import autoevaluacion.TblautIndicadoresCaracteristica;
import autoevaluacion.TblautMecanismo;
import autoevaluacion.TblautVigencia;

import com.academico.SedeProgramaBO;
import com.autoevaluacion.LecturaIndicadoresBO;
import com.autoevaluacion.MatrizIndicadoresBO;
import com.autoevaluacion.VigenciasBO;

import commons.util.UtilidadBean;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;
import com.encuesta.TipoEvaluacionBO;
import util.BOException;

@ManagedBean(name = "dilMatrizMB")
@ViewScoped
public class MatrizMB extends BaseControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7800452831724793986L;

	// EJB
	@EJB
	private TipoEvaluacionBO tipoEvaluacionBO;
	@EJB
	private LecturaIndicadoresBO lecturaIndicadoresBO;
	@EJB
	private SedeProgramaBO sedeProgramaBO;
	@EJB
	private VigenciasBO vigenciasBO;

	// CONTROLADORES
	private DiligenciamientoMB diligenciamientoMB;

	// VARIABLES
	private List<TblautVigencia> listVigencias;
	private List<TblacaSede> listaSedes;
	private List<TblacaSedePrograma> listaSedesProgramas;
	private List<TblautMecanismo> listaMecanismos;
	private List<TblautFuente> listaFuentes;
	private List<TblautFactorVigencia> listaResulMatriz;

	private TblacaSede sedeSelected;
	private TblacaSedePrograma programaSelected;

	private TblautVigencia vigenciaSelected;

	private MatrizIndicadoresBO matrizIndicadoresBO;

	private boolean mostrarMatriz;
	private boolean exportar;

	@PostConstruct
	public void init() throws BOException {
		try {

			LOGGER.debug("INIT CONTROLADOR");
			lookup();
			inicializar();

		} catch (Exception ex) {
			lanzarMensajeError(null, ex);
		}

	}

	@Override
	public void inicializar() {
		setMostrarMatriz(false);
		vigencias();
		sedes();
		mecanismos();
		fuentes();
		setExportar(false);
	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {
			matrizIndicadoresBO = LookupUtil
					.lookupRemoteStateless(MatrizIndicadoresBO.class);
			lecturaIndicadoresBO = LookupUtil
					.lookupRemoteStateless(LecturaIndicadoresBO.class);
			sedeProgramaBO = LookupUtil
					.lookupRemoteStateless(SedeProgramaBO.class);
			vigenciasBO = LookupUtil.lookupRemoteStateless(VigenciasBO.class);
			// ...
		} catch (NamingException e) {
			// ...
			msgError(e);
		}
	}

	private void sedes() {
		try {
			listaSedes = sedeProgramaBO.buscarSedes();
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException sedes ");
			msgError(e);
		}

	}

	private void vigencias() {
		try {
			listVigencias = vigenciasBO.buscarTodos();
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException vigencias ");
			msgError(e);
		}

	}

	private void fuentes() {
		try {
			listaFuentes = lecturaIndicadoresBO.buscarFuentes();
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException fuentes ");
			msgError(e);
		}

	}

	private void mecanismos() {
		try {
			listaMecanismos = lecturaIndicadoresBO.buscarMecanismos();
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException mecanismos ");
			msgError(e);
		}

	}

	public void cargarProgramas() {
		try {
			setExportar(false);

			if (diligenciamientoMB.isEsAdministrador()) {
				listaSedesProgramas = matrizIndicadoresBO.buscarSedesprogramas(
						sedeSelected.getOid(),
						UtilidadBean.serialize(vigenciaSelected));
			} else if (diligenciamientoMB.isEsDirPrograma()) {
				listaSedesProgramas = lecturaIndicadoresBO
						.buscarSedesprogramasParticipante(diligenciamientoMB
								.getParticipante().getIdparticipante(),
								vigenciaSelected.getAnioVigencia());
			}

		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarProgramas ");
			msgError(e);
		} catch (NullPointerException e) {
			LOGGER.info(" cargarProgramas()  NullP ");
			msgError(e);
		} catch (Exception e) {
			LOGGER.info(" cargarProgramas()  Exception() ");
			msgError(e);
		}

	}

	public void cargarInformacion() {
		if (UtilidadBean.validaNulos(sedeSelected)) {
			setSedeSelected(programaSelected.getTblacaSede());
		}

		setExportar(true);
		setMostrarMatriz(true);
		cargarDatos();

	}

	private void cargarDatos() {
		try {
			listaResulMatriz = new ArrayList<TblautFactorVigencia>();
			listaResulMatriz = matrizIndicadoresBO.resultadoMatriz(UtilidadBean
					.serialize(vigenciaSelected));

		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException inicializarOtrasDependencias ");
			msgError(e);
		} catch (NullPointerException e) {
			LOGGER.info(" cargarDatos()  NullP ");
			msgError(e);
		} catch (Exception e) {
			LOGGER.info(" cargarDatos()  Exception() ");
			msgError(e);
		}
	}

	public List<TblautCaracteristicaVigencia> caracteristicasFactor(
			TblautFactore factor) {
		try {

			return matrizIndicadoresBO.buscarCacateristicas(
					UtilidadBean.serialize(factor),
					UtilidadBean.serialize(vigenciaSelected));

		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException caracteristicasFactor ");
			msgError(e);
			return null;
		} catch (NullPointerException e) {
			LOGGER.info(" caracteristicasFactor()  NullP ");
			msgError(e);
			return null;
		} catch (Exception e) {
			LOGGER.info(" caracteristicasFactor()  Exception() ");
			msgError(e);
			return null;
		}
	}

	public List<TblautIndicadorVigencia> listaIndicadores(TblautFactore factor,
			TblautCaracteristica caracteristica) {
		try {

			return matrizIndicadoresBO.buscarIndicadores(
					UtilidadBean.serialize(factor),
					UtilidadBean.serialize(caracteristica),
					UtilidadBean.serialize(vigenciaSelected));

		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException inicializarOtrasDependencias ");
			msgError(e);
			return null;
		} catch (NullPointerException e) {
			LOGGER.info(" cargarDatos()  NullP ");
			msgError(e);
			return null;
		} catch (Exception e) {
			LOGGER.info(" cargarDatos()  Exception() ");
			msgError(e);
			return null;
		}
	}

	public TblautFuente buscarFuente(TblautFuente fuente,
			TblautIndicadoresCaracteristica indicador) {
		try {

			return matrizIndicadoresBO.buscarFuente(fuente.getOid(),
					indicador.getOid(), vigenciaSelected.getOid());

		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException buscarFuente ");
			msgError(e);
			return null;
		} catch (NullPointerException e) {
			LOGGER.info(" buscarFuente()  NullP ");
			msgError(e);
			return null;
		} catch (Exception e) {
			LOGGER.info(" buscarFuente()  Exception() ");
			msgError(e);
			return null;
		}
	}

	public Integer lectura(TblautFuente fuente,
			TblautIndicadoresCaracteristica indicador,
			TblautCaracteristica caracteristica, TblautFactore factor) {

		try {
			return matrizIndicadoresBO.buscarLectura(fuente.getOid(), indicador
					.getOid(), caracteristica.getOid(), factor.getOid(),
					vigenciaSelected.getOid(), programaSelected.getTblacaSede()
							.getOid(), programaSelected.getTblacaPrograma()
							.getOid());

		} catch (NullPointerException e) {
			LOGGER.info(" buscarLectura()  NullP ");
			msgError(e);
			return 0;
		} catch (Exception e) {
			LOGGER.info(" buscarLectura()  Exception() ");
			msgError(e);
			return 0;
		}

	}

	public Double promedioCalificacion(
			TblautIndicadoresCaracteristica indicador,
			TblautCaracteristica caracteristica, TblautFactore factor) {

		try {
			return matrizIndicadoresBO.buscarPromedioCalificacion(indicador
					.getOid(), caracteristica.getOid(), factor.getOid(),
					vigenciaSelected.getOid(), programaSelected.getTblacaSede()
							.getOid(), programaSelected.getTblacaPrograma()
							.getOid());

		} catch (NullPointerException e) {
			LOGGER.info(" buscarLectura()  NullP ");
			msgError(e);
			return (double) 0;
		} catch (Exception e) {
			LOGGER.info(" buscarLectura()  Exception() ");
			msgError(e);
			return (double) 0;
		}

	}

	public Double porcentajeCalificacion(
			TblautIndicadoresCaracteristica indicador,
			TblautCaracteristica caracteristica, TblautFactore factor) {

		try {
			return matrizIndicadoresBO.buscarPorcentajeCalificacion(indicador
					.getOid(), caracteristica.getOid(), factor.getOid(),
					vigenciaSelected.getOid(), programaSelected.getTblacaSede()
							.getOid(), programaSelected.getTblacaPrograma()
							.getOid());

		} catch (NullPointerException e) {
			LOGGER.info(" buscarLectura()  NullP ");
			msgError(e);
			return (double) 0;
		} catch (Exception e) {
			LOGGER.info(" buscarLectura()  Exception() ");
			msgError(e);
			return (double) 0;
		}

	}

	public Double promedioCalificacionCar(TblautCaracteristica caracteristica,
			TblautFactore factor) {

		try {
			return matrizIndicadoresBO.buscarPromedioCalificacion(
					caracteristica.getOid(), factor.getOid(), vigenciaSelected
							.getOid(), programaSelected.getTblacaSede()
							.getOid(), programaSelected.getTblacaPrograma()
							.getOid());

		} catch (NullPointerException e) {
			LOGGER.info(" buscarLectura()  NullP ");
			msgError(e);
			return (double) 0;
		} catch (Exception e) {
			LOGGER.info(" buscarLectura()  Exception() ");
			msgError(e);
			return (double) 0;
		}

	}

	public Double porcentajeCalificacionCar(
			TblautCaracteristica caracteristica, TblautFactore factor) {

		try {
			return matrizIndicadoresBO.buscarPorcentajeCalificacion(
					caracteristica.getOid(), factor.getOid(), vigenciaSelected
							.getOid(), programaSelected.getTblacaSede()
							.getOid(), programaSelected.getTblacaPrograma()
							.getOid());

		} catch (NullPointerException e) {
			LOGGER.info(" buscarLectura()  NullP ");
			msgError(e);
			return (double) 0;
		} catch (Exception e) {
			LOGGER.info(" buscarLectura()  Exception() ");
			msgError(e);
			return (double) 0;
		}

	}

	public Double promedioCalificacionFac(TblautFactore factor) {

		try {
			return matrizIndicadoresBO.buscarPromedioCalificacion(factor
					.getOid(), vigenciaSelected.getOid(), programaSelected
					.getTblacaSede().getOid(), programaSelected
					.getTblacaPrograma().getOid());

		} catch (NullPointerException e) {
			LOGGER.info(" buscarLectura()  NullP ");
			msgError(e);
			return (double) 0;
		} catch (Exception e) {
			LOGGER.info(" buscarLectura()  Exception() ");
			msgError(e);
			return (double) 0;
		}

	}

	public Double porcentajeCalificacionFac(TblautFactore factor) {

		try {
			return matrizIndicadoresBO.buscarPorcentajeCalificacion(factor
					.getOid(), vigenciaSelected.getOid(), programaSelected
					.getTblacaSede().getOid(), programaSelected
					.getTblacaPrograma().getOid());

		} catch (NullPointerException e) {
			LOGGER.info(" buscarLectura()  NullP ");
			msgError(e);
			return (double) 0;
		} catch (Exception e) {
			LOGGER.info(" buscarLectura()  Exception() ");
			msgError(e);
			return (double) 0;
		}

	}

	public Double promedioCalificacionTotal() {

		try {
			return matrizIndicadoresBO.buscarPromedioCalificacion(
					vigenciaSelected.getOid(), programaSelected.getTblacaSede()
							.getOid(), programaSelected.getTblacaPrograma()
							.getOid());

		} catch (NullPointerException e) {
			LOGGER.info(" buscarLectura()  NullP ");
			msgError(e);
			return (double) 0;
		} catch (Exception e) {
			LOGGER.info(" buscarLectura()  Exception() ");
			msgError(e);
			return (double) 0;
		}

	}

	public Double porcentajeCalificacionTotal() {

		try {
			return matrizIndicadoresBO.buscarPorcentajeCalificacion(
					vigenciaSelected.getOid(), programaSelected.getTblacaSede()
							.getOid(), programaSelected.getTblacaPrograma()
							.getOid());

		} catch (NullPointerException e) {
			LOGGER.info(" buscarLectura()  NullP ");
			msgError(e);
			return (double) 0;
		} catch (Exception e) {
			LOGGER.info(" buscarLectura()  Exception() ");
			msgError(e);
			return (double) 0;
		}

	}

	public void msgError(Throwable e) {
		lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
				"msg.warn.lectura.guardo.error"));
		LOGGER.info(null, e);
	}

	// SETTES AND GETTTES
	public DiligenciamientoMB getDiligenciamientoMB() {
		return diligenciamientoMB;
	}

	public void setDiligenciamientoMB(DiligenciamientoMB diligenciamientoMB) {
		this.diligenciamientoMB = diligenciamientoMB;
	}

	public List<TblacaSede> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<TblacaSede> listaSedes) {
		this.listaSedes = listaSedes;
	}

	public List<TblacaSedePrograma> getListaSedesProgramas() {
		return listaSedesProgramas;
	}

	public void setListaSedesProgramas(
			List<TblacaSedePrograma> listaSedesProgramas) {
		this.listaSedesProgramas = listaSedesProgramas;
	}

	public TblacaSede getSedeSelected() {
		return sedeSelected;
	}

	public void setSedeSelected(TblacaSede sedeSelected) {
		this.sedeSelected = sedeSelected;
	}

	public TblacaSedePrograma getProgramaSelected() {
		return programaSelected;
	}

	public void setProgramaSelected(TblacaSedePrograma programaSelected) {
		this.programaSelected = programaSelected;
	}

	public boolean isMostrarMatriz() {
		return mostrarMatriz;
	}

	public void setMostrarMatriz(boolean mostrarMatriz) {
		this.mostrarMatriz = mostrarMatriz;
	}

	public List<TblautMecanismo> getListaMecanismos() {
		return listaMecanismos;
	}

	public void setListaMecanismos(List<TblautMecanismo> listaMecanismos) {
		this.listaMecanismos = listaMecanismos;
	}

	public List<TblautFuente> getListaFuentes() {
		return listaFuentes;
	}

	public void setListaFuentes(List<TblautFuente> listaFuentes) {
		this.listaFuentes = listaFuentes;
	}

	public List<TblautFactorVigencia> getListaResulMatriz() {
		return listaResulMatriz;
	}

	public void setListaResulMatriz(List<TblautFactorVigencia> listaResulMatriz) {
		this.listaResulMatriz = listaResulMatriz;
	}

	public boolean isExportar() {
		return exportar;
	}

	public void setExportar(boolean exportar) {
		this.exportar = exportar;
	}

	public List<TblautVigencia> getListVigencias() {
		return listVigencias;
	}

	public void setListVigencias(List<TblautVigencia> listVigencias) {
		this.listVigencias = listVigencias;
	}

	public TblautVigencia getVigenciaSelected() {
		return vigenciaSelected;
	}

	public void setVigenciaSelected(TblautVigencia vigenciaSelected) {
		this.vigenciaSelected = vigenciaSelected;
	}

}
