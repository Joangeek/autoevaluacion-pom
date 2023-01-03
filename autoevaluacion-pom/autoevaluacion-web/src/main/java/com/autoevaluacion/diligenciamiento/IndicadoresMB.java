package com.autoevaluacion.diligenciamiento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import talentoHumano.TblthParticipante;

import util.BOException;

import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblacaSedePrograma;
import autoevaluacion.TblautFactore;
import autoevaluacion.TblautLecturaIndicadore;
import autoevaluacion.TblautLecturaIndicadoresOt;
import autoevaluacion.TblautMecanismo;
import autoevaluacion.TblautOtrasDependencia;
import autoevaluacion.TblautVigencia;

import com.academico.SedeProgramaBO;
import com.autoevaluacion.LecturaIndicadoresBO;
import com.autoevaluacion.VigenciasBO;
import util.BOException;

import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;
import gestion.Util;

@SuppressWarnings("unused")
@ManagedBean(name = "dilIndicadorMB")
@ViewScoped
public class IndicadoresMB extends BaseControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8944056322849831404L;
	private String tituloPage = obtenerResourceBundle(AUTOEVAL).getString(
			"title.page.proceso.auto");

	// EJB
	@EJB
	private LecturaIndicadoresBO lecturaIndicadoresBO;
	@EJB
	private SedeProgramaBO sedeProgramaBO;
	@EJB
	private VigenciasBO vigenciasBO;

	// CONTROLADORES
	private DiligenciamientoMB diligenciamientoMB;

	// VARIABLES

	private List<TblautMecanismo> listMecanismos;
	private List<TblautLecturaIndicadore> listaIndicadores;
	private List<TblautLecturaIndicadoresOt> listaIndicadoresOd;
	private List<TblacaSede> listaSedes;
	private List<TblacaSedePrograma> listaSedesProgramas;
	private List<TblautOtrasDependencia> listaDependencias;
	private TblacaSede sedeSelected;
	private TblacaSedePrograma programaSelected;
	private TblautOtrasDependencia dependenciaSelected;
	private boolean mostrarIndicadores;
	private Integer lectura;
	private Date fechaLectura = new Date();
	private TblautVigencia vigenciaSelected;
	private List<TblautVigencia> listVigencias;

	@PostConstruct
	public void init() throws BOException {
		try {
			LOGGER.info("INIT CONTROLADOR");
			lookup();
		} catch (Exception e) {
			msgError(e);
		}

	}

	public void msgError(Throwable e) {
		lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
				"msg.warn.lectura.guardo.error"));
		LOGGER.info(null, e);
	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {

			lecturaIndicadoresBO = LookupUtil
					.lookupRemoteStateless(LecturaIndicadoresBO.class);
			sedeProgramaBO = LookupUtil
					.lookupRemoteStateless(SedeProgramaBO.class);
			vigenciasBO = LookupUtil.lookupRemoteStateless(VigenciasBO.class);
		} catch (NamingException e) {
			// ...
			msgError(e);
		}
	}

	// -------------------- PROCESO COMUN-------------------------------
	@Override
	public void inicializar() {
		try {

			listMecanismos = new ArrayList<TblautMecanismo>();
			vigencias();
			if (diligenciamientoMB.isEsAdministrador()) {
				inicializarDirPrograma();
			} else if (diligenciamientoMB.isEsDirDependencia()) {

				inicializarOtrasDependencias();
			} else if (diligenciamientoMB.isEsAdministrador()) {

				inicializarOtrasDependencias();
			} else {
				// Mensaje acceso indebído o sin privilegios
				LOGGER.info("------NN-----");
			}
		} catch (Exception e) {
			LOGGER.info("Exception - inicializar()");
			msgError(e);
		}
	}

	private void vigencias() {
		try {
			listVigencias = vigenciasBO.buscarTodos();
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException inicializarOtrasDependencias ");
			msgError(e);
		}

	}

	public void inicializarOtrasDependencias() {
		listaIndicadoresOd = new ArrayList<TblautLecturaIndicadoresOt>();
		listaDependencias = new ArrayList<TblautOtrasDependencia>();

		try {
			listaDependencias = lecturaIndicadoresBO
					.buscarDependencias(diligenciamientoMB.getParticipante()
							.getIdparticipante());
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException inicializarOtrasDependencias ");
			msgError(e);
		} catch (NullPointerException e) {
			LOGGER.info(" inicializarOtrasDependencias()  NullP ");
			LOGGER.info(null, e);
			msgError(e);
		} catch (Exception e) {
			LOGGER.info(" inicializarOtrasDependencias()  Exception() ");
			LOGGER.info(null, e);
			msgError(e);
		}

	}

	public void inicializarDirPrograma() {
		listaIndicadores = new ArrayList<TblautLecturaIndicadore>();
		listaSedes = new ArrayList<TblacaSede>();
		listaSedesProgramas = new ArrayList<TblacaSedePrograma>();
		try {
			listaSedes = sedeProgramaBO.buscarSedesActivas(true);

		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException inicializarOtrasDependencias ");
		} catch (NullPointerException e) {
			LOGGER.info(" inicializarOtrasDependencias()  NullP ");
			msgError(e);
		} catch (Exception e) {
			LOGGER.info(" inicializarOtrasDependencias()  Exception() ");
			msgError(e);
		}

	}

	public void cargarInformacion() {
		mostrarIndicadores = true;

	}

	public boolean validarVigencia() {
		if (fechaLectura.after(vigenciaSelected.getFechaApertura())
				&& fechaLectura.before(vigenciaSelected.getFechaCierre())) {
			return true;
		}

		return false;
	}

	// -------------------- PROCESO PARA OTRAS DEPENDENCIAS-------------

	public List<TblautMecanismo> cargarMecanismosOd() {
		try {

			listMecanismos = lecturaIndicadoresBO.buscarMecanismosLiOd(
					diligenciamientoMB.getParticipante().getIdparticipante(),
					vigenciaSelected.getOid(), dependenciaSelected.getOid());
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarMecanismosOd ");
			msgError(e);
		} catch (NullPointerException e) {
			LOGGER.info(" cargarMecanismosOd()  NullP ");
			msgError(e);
		} catch (Exception e) {
			LOGGER.info(" cargarMecanismosOd()  Exception() ");
			msgError(e);
		}
		if (listMecanismos.size() == 0) {
			lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
					"msg.warn.lectura.permisos"));
		}

		return listMecanismos;
	}

	public List<TblautFactore> cargarFactoresOd(TblautMecanismo mecanismo) {
		List<TblautFactore> lista = new ArrayList<TblautFactore>();
		try {
			lista = lecturaIndicadoresBO.buscarfactoresOd(mecanismo.getOid(),
					diligenciamientoMB.getParticipante().getIdparticipante(),
					dependenciaSelected.getOid());
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarFactores ");
			msgError(e);
		} catch (NullPointerException e) {
			LOGGER.info(" cargarGruposAspectos()  NullP ");
			msgError(e);
		} catch (Exception e) {
			LOGGER.info(" cargarGruposAspectos()  Exception() ");
			msgError(e);
		}
		if (lista.size() == 0) {
			lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
					"msg.warn.lectura.permisos"));
		}
		return lista;

	}

	public List<TblautLecturaIndicadoresOt> cargarIndicadoresOd(
			TblautFactore factor, TblautMecanismo mecanismo) {
		List<TblautLecturaIndicadoresOt> lista = new ArrayList<TblautLecturaIndicadoresOt>();
		try {

			lista = lecturaIndicadoresBO.buscarIndicadoresLecOd(
					factor.getOid(), mecanismo.getOid(), diligenciamientoMB
							.getParticipante().getIdparticipante(),
					dependenciaSelected.getOid());

		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarIndicadoresOd ");
			msgError(e);
		} catch (NullPointerException e) {
			LOGGER.info(" cargarIndicadoresOd()  NullP ");
			msgError(e);
		} catch (Exception e) {
			LOGGER.info(" cargarIndicadoresOd()  Exception() ");
			msgError(e);
		}
		if (lista.size() == 0) {
			lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
					"msg.warn.lectura.permisos"));
		}
		return lista;

	}

	// -------------------- PROCESO PARA DIRECTORES DE PROGRAMA---------

	public void cargarProgramas() {
		try {

			listaSedesProgramas = lecturaIndicadoresBO
					.buscarSedesprogramasParticipante(diligenciamientoMB
							.getParticipante().getIdparticipante(),
							vigenciaSelected.getOid());
			/*
			 * listaSedesProgramas = sedeProgramaBO.buscarProgramasEstadoSp(
			 * sedeSelected.getOid(), true);
			 */

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

	public List<TblautMecanismo> cargarMecanismos() {
		try {

			listMecanismos = lecturaIndicadoresBO.buscarMecanismosLi(
					programaSelected.getTblacaSede().getOid(), programaSelected
							.getTblacaPrograma().getOid(), diligenciamientoMB
							.getParticipante().getIdparticipante(),
					vigenciaSelected.getOid());
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarMecanismos ");
			msgError(e);
		} catch (NullPointerException e) {
			LOGGER.info(" cargarMecanismos()  NullP ");
			msgError(e);
			LOGGER.info(null, e);
		} catch (Exception e) {
			LOGGER.info(" cargarMecanismos()  Exception() ");
			msgError(e);
		}
		if (listMecanismos.size() == 0) {
			lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
					"msg.warn.lectura.permisos"));
		}
		return listMecanismos;
	}

	public List<TblautFactore> cargarFactores(TblautMecanismo mecanismo) {
		List<TblautFactore> lista = new ArrayList<TblautFactore>();
		try {
			lista = lecturaIndicadoresBO.buscarfactores(mecanismo.getOid(),
					programaSelected.getTblacaSede().getOid(), programaSelected
							.getTblacaPrograma().getOid(), diligenciamientoMB
							.getParticipante().getIdparticipante());
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarFactores ");
			msgError(e);
		} catch (NullPointerException e) {
			LOGGER.info(" cargarGruposAspectos()  NullP ");
			msgError(e);
		} catch (Exception e) {
			LOGGER.info(" cargarGruposAspectos()  Exception() ");
			msgError(e);
		}

		if (lista.size() == 0) {
			lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
					"msg.warn.lectura.permisos"));
		}
		return lista;

	}

	public List<TblautLecturaIndicadore> cargarIndicadores(
			TblautFactore factor, TblautMecanismo mecanismo) {
		List<TblautLecturaIndicadore> lista = new ArrayList<TblautLecturaIndicadore>();
		try {

			lista = lecturaIndicadoresBO.buscarIndicadoresLec(factor.getOid(),
					programaSelected.getTblacaSede().getOid(), programaSelected
							.getTblacaPrograma().getOid(), mecanismo.getOid(),
					diligenciamientoMB.getParticipante().getIdparticipante());
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarIndicadores ");
			msgError(e);
		} catch (NullPointerException e) {
			LOGGER.info(" cargarIndicadores()  NullP ");
			msgError(e);
		} catch (Exception e) {
			LOGGER.info(" cargarIndicadores()  Exception() ");
			msgError(e);

		}

		if (lista.size() == 0) {
			lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
					"msg.warn.lectura.permisos"));
		}
		return lista;

	}

	public void onRowCancelOd(RowEditEvent event) {
		LOGGER.info("Cancel");
	}

	public void onRowEditOd(RowEditEvent event) {
		LOGGER.info("Edit");
		TblautLecturaIndicadoresOt entidad = new TblautLecturaIndicadoresOt();
		entidad = (TblautLecturaIndicadoresOt) event.getObject();
		LOGGER.info("Edit --OD= "
				+ entidad.getTblautIndicadoresCaracteristica().getIndicador()
				+ "--- " + getLectura());
		try {
			if (UtilidadBean.validaNulos(entidad)
					&& UtilidadBean.validaNulos(lectura)) {
				entidad.setLectura(getLectura());
				entidad.setUsuario(diligenciamientoMB.getUser());
				entidad.setFechaLectura(fechaLectura);
				lecturaIndicadoresBO
						.editarLiOd(UtilidadBean.serialize(entidad));
				lanzarMensajeInfo(obtenerResourceBundle(AUTOEVAL).getString(
						"msg.info.lectura.guardo.exito"));
			} else {
				lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
						"msg.info.lectura.guardo.exito"));
			}

		} catch (BOException e) {
			lanzarMensajeError(obtenerResourceBundle(AUTOEVAL).getString(
					"msg.warn.lectura.guardo.error"));
			LOGGER.info(null, e);
		}
	}

	public void onRowEdit(RowEditEvent event) {
		LOGGER.info("Edit");
		TblautLecturaIndicadore entidad = new TblautLecturaIndicadore();
		entidad = (TblautLecturaIndicadore) event.getObject();
		LOGGER.info("Edit --= "
				+ entidad.getTblautIndicadoresCaracteristica().getIndicador()
				+ "--- " + getLectura());
		try {
			if (UtilidadBean.validaNulos(entidad)
					&& UtilidadBean.validaNulos(lectura)) {
				entidad.setLectura(getLectura());
				entidad.setUsuario(diligenciamientoMB.getUser());
				entidad.setFechaLectura(fechaLectura);
				lecturaIndicadoresBO.editarLi(UtilidadBean.serialize(entidad));
				lanzarMensajeInfo(obtenerResourceBundle(AUTOEVAL).getString(
						"msg.info.lectura.guardo.exito"));
			} else {
				lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
						"msg.info.lectura.guardo.exito"));
			}

		} catch (BOException e) {
			lanzarMensajeError(obtenerResourceBundle(AUTOEVAL).getString(
					"msg.warn.lectura.guardo.error"));
			LOGGER.info(null, e);
		}
	}

	// SETTES AND GETTTES
	public DiligenciamientoMB getDiligenciamientoMB() {
		return diligenciamientoMB;
	}

	public void setDiligenciamientoMB(DiligenciamientoMB diligenciamientoMB) {
		this.diligenciamientoMB = diligenciamientoMB;
	}

	public List<TblautMecanismo> getListMecanismos() {
		return listMecanismos;
	}

	public void setListMecanismos(List<TblautMecanismo> listMecanismos) {
		this.listMecanismos = listMecanismos;
	}

	public String getTituloPage() {
		return tituloPage;
	}

	public void setTituloPage(String tituloPage) {
		this.tituloPage = tituloPage;
	}

	public List<TblautLecturaIndicadore> getListaIndicadores() {
		return listaIndicadores;
	}

	public void setListaIndicadores(
			List<TblautLecturaIndicadore> listaIndicadores) {
		this.listaIndicadores = listaIndicadores;
	}

	public List<TblautLecturaIndicadoresOt> getListaIndicadoresOd() {
		return listaIndicadoresOd;
	}

	public void setListaIndicadoresOd(
			List<TblautLecturaIndicadoresOt> listaIndicadoresOd) {
		this.listaIndicadoresOd = listaIndicadoresOd;
	}

	/**
	 * @return the listaSedes
	 */
	public List<TblacaSede> getListaSedes() {
		return listaSedes;
	}

	/**
	 * @param listaSedes
	 *            the listaSedes to set
	 */
	public void setListaSedes(List<TblacaSede> listaSedes) {
		this.listaSedes = listaSedes;
	}

	/**
	 * @return the listaSedesProgramas
	 */
	public List<TblacaSedePrograma> getListaSedesProgramas() {
		return listaSedesProgramas;
	}

	/**
	 * @param listaSedesProgramas
	 *            the listaSedesProgramas to set
	 */
	public void setListaSedesProgramas(
			List<TblacaSedePrograma> listaSedesProgramas) {
		this.listaSedesProgramas = listaSedesProgramas;
	}

	/**
	 * @return the listaDependencias
	 */
	public List<TblautOtrasDependencia> getListaDependencias() {
		return listaDependencias;
	}

	/**
	 * @param listaDependencias
	 *            the listaDependencias to set
	 */
	public void setListaDependencias(
			List<TblautOtrasDependencia> listaDependencias) {
		this.listaDependencias = listaDependencias;
	}

	/**
	 * @return the sedeProgramaBO
	 */
	public SedeProgramaBO getSedeProgramaBO() {
		return sedeProgramaBO;
	}

	/**
	 * @param sedeProgramaBO
	 *            the sedeProgramaBO to set
	 */
	public void setSedeProgramaBO(SedeProgramaBO sedeProgramaBO) {
		this.sedeProgramaBO = sedeProgramaBO;
	}

	/**
	 * @return the sedeSelected
	 */
	public TblacaSede getSedeSelected() {
		return sedeSelected;
	}

	/**
	 * @param sedeSelected
	 *            the sedeSelected to set
	 */
	public void setSedeSelected(TblacaSede sedeSelected) {
		this.sedeSelected = sedeSelected;
	}

	public TblacaSedePrograma getProgramaSelected() {
		return programaSelected;
	}

	public void setProgramaSelected(TblacaSedePrograma programaSelected) {
		this.programaSelected = programaSelected;
	}

	/**
	 * @return the dependenciaSelected
	 */
	public TblautOtrasDependencia getDependenciaSelected() {
		return dependenciaSelected;
	}

	/**
	 * @param dependenciaSelected
	 *            the dependenciaSelected to set
	 */
	public void setDependenciaSelected(
			TblautOtrasDependencia dependenciaSelected) {
		this.dependenciaSelected = dependenciaSelected;
	}

	public boolean isMostrarIndicadores() {
		return mostrarIndicadores;
	}

	public void setMostrarIndicadores(boolean mostrarIndicadores) {
		this.mostrarIndicadores = mostrarIndicadores;
	}

	public Integer getLectura() {
		return lectura;
	}

	public void setLectura(Integer lectura) {
		this.lectura = lectura;
	}

	public TblautVigencia getVigenciaSelected() {
		return vigenciaSelected;
	}

	public void setVigenciaSelected(TblautVigencia vigenciaSelected) {
		this.vigenciaSelected = vigenciaSelected;
	}

	public List<TblautVigencia> getListVigencias() {
		return listVigencias;
	}

	public void setListVigencias(List<TblautVigencia> listVigencias) {
		this.listVigencias = listVigencias;
	}

}
