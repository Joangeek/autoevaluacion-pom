package com.encuestas.configurar;

import encuestas.TblencAspecto;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencOpcione;
import encuestas.TblencTipoEvaluacion;
import encuestas.util.ResultadoEncuesta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import org.primefaces.event.FlowEvent;



import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;

import com.encuesta.TipoAspectoBO;
import com.encuesta.TipoEvaluacionBO;
import com.gestion.ParametrosBO;

import util.BOException;

@ManagedBean(name = "previsualizarMB")
@ViewScoped
public class PrevisualizarMB extends BaseControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5501186905206614466L;
	/**
	 * 
	 */

	// EJB
	@EJB
	private TipoEvaluacionBO tipoEvaluacionBO;
	@EJB
	private ParametrosBO parametrosBO;
	@EJB
	private TipoAspectoBO tipoAspectoBO;

	// CONTROLADORES
	private ConfigurarMB configurarMB;

	// VARIABLES
	private TblencTipoEvaluacion tipoEvaluacionSelected;
	private TblencTipoEvaluacion selected;
	private List<TblencTipoEvaluacion> listaEntuestas;

	private List<TblencOpcione> listaOpciones;

	private List<TblencGrupoAspecto> listaGrupos;

	private boolean mostrarVisualizacion;
	private boolean verRespuestas;
	private ArrayList<String> valores;
	private boolean skip;

	private Map<TblencAspecto, String> mapa;
	private List<ResultadoEncuesta> listaResultadoEncuesta;

	public void inicializar() {
		mostrarVisualizacion = false;
		verRespuestas = false;
		listaEntuestas = new ArrayList<TblencTipoEvaluacion>();
		valores = new ArrayList<String>();
		mapa = new HashMap<TblencAspecto, String>();
		setListaOpciones(new ArrayList<TblencOpcione>());
	}

	@PostConstruct
	public void init() throws BOException {
		try {

			LOGGER.debug("INIT CONTROLADOR");
			inicializar();
			lookup();
		} catch (Exception ex) {
			lanzarMensajeError(null, ex);
		}

	}

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
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
			// ...
		} catch (NamingException e) {
			// ...
		}
	}

	/**
	 * Método que consulta todos los datos relacionados a la entidad
	 */
	public void cargarEncuesta() {
		try {

			if (Utilidad.validaNulos(tipoEvaluacionSelected)) {
				LOGGER.info("oid encuesta=  " + tipoEvaluacionSelected.getOid());
				inicializar();
				listaEntuestas = tipoEvaluacionBO
						.buscarID(tipoEvaluacionSelected.getOid());
			}

		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			LOGGER.info("Null P ct ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarTodos: " + e.getMessage(), e);
		}

	}

	private void cargarGruposAspectos() {
		try {
			setListaGrupos(tipoAspectoBO.gruposPorEncuesta(UtilidadBean
					.serialize(selected)));

			LOGGER.info("cargarGruposAspectos : " + getListaGrupos().size());
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarGruposAspectos ");
		} catch (NullPointerException e) {
			LOGGER.info("Null P ct ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarDatos: " + e.getMessage(), e);
		}
	}

	public List<TblencAspecto> cargarDatos(TblencGrupoAspecto grupo) {
		try {
			List<TblencAspecto> listaPreguntas = new ArrayList<TblencAspecto>();

			listaPreguntas = tipoAspectoBO.buscarPorGrupo(UtilidadBean
					.serialize(grupo));
			for (TblencAspecto val : listaPreguntas) {
				LOGGER.info("    - VALLS :" + val.getPadre() + " --- cod : "
						+ val.getCodigo());
			}

			LOGGER.info("tamaño Grupo: " + listaPreguntas.size());
			return listaPreguntas;
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			LOGGER.info("Null P ct ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarDatos: " + e.getMessage(), e);
		}
		return null;

	}

	public void mostarEncuesta() {
		LOGGER.info("Nombre Encuestaa=  " + selected.getNombre());
		mostrarVisualizacion = true;
		cargarGruposAspectos();
		// cargarDatos();
		ejecutarUpdate("pnlEncuesta");
	}

	public void obtenerCalificacines() throws BOException {
		LOGGER.info("MAPA SIZE=  " + mapa.size());
		// for (Map.Entry<TblencAspecto, String> entry : mapa.entrySet()) {
		// LOGGER.info("    - VALLS map :" + entry.getKey().getCodigo()
		// + "---" + entry.getValue());
		//

		setListaResultadoEncuesta((tipoAspectoBO.verRespuestas(UtilidadBean
				.serialize(mapa))));

		verRespuestas = true;

	}
/*
	private boolean validar() {

		for (int i = 0; i < listaGrupos.size(); i++) {
			StringBuilder str = new StringBuilder();
			str.append("formprevisualizacion:acordion:");
			str.append(i);
			str.append(":tab:btnTab').click()");

			LOGGER.info("validar=  " + str.toString());
			Utilidad.ejecutarJavaScript(str.toString());
			// ejecutarUpdate(str.toString());
			// RequestContext.getCurrentInstance().update(str.toString());
		}
		return false;
	}*/

	public List<TblencOpcione> cargarOpciones(TblencAspecto aspecto)
			throws BOException {
		return setListaOpciones(tipoAspectoBO.buscarOpcAspecto(UtilidadBean
				.serialize(aspecto.getTblencTipoAspecto())));

	}

	public void cerrarVerRespuestas() {
		verRespuestas = false;
	}

	// SETTES AND GETTES
	public ConfigurarMB getConfigurarMB() {
		return configurarMB;
	}

	public void setConfigurarMB(ConfigurarMB configurarMB) {
		this.configurarMB = configurarMB;
	}

	public TblencTipoEvaluacion getTipoEvaluacionSelected() {
		return tipoEvaluacionSelected;
	}

	public void setTipoEvaluacionSelected(
			TblencTipoEvaluacion tipoEvaluacionSelected) {
		this.tipoEvaluacionSelected = tipoEvaluacionSelected;
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

	public boolean isMostrarVisualizacion() {
		return mostrarVisualizacion;
	}

	public void setMostrarVisualizacion(boolean mostrarVisualizacion) {
		this.mostrarVisualizacion = mostrarVisualizacion;
	}

	public ArrayList<String> getValores() {
		return valores;
	}

	public void setValores(ArrayList<String> valores) {
		this.valores = valores;
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

	public boolean isVerRespuestas() {
		return verRespuestas;
	}

	public void setVerRespuestas(boolean verRespuestas) {
		this.verRespuestas = verRespuestas;
	}

	public List<TblencGrupoAspecto> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<TblencGrupoAspecto> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

}
