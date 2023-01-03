package com.autoevaluacion.diligenciamiento;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import talentoHumano.TblthParticipante;
import util.BOException;

import com.autoevaluacion.LecturaIndicadoresBO;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;
import gestion.Util;

@ManagedBean(name = "diligenciamientoMB")
@ViewScoped
public class DiligenciamientoMB extends BaseControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5852150511842585860L;
	private String user = Util.getUserSession();
	/**
	 * ingreso como colaborador otra dependencia
	 */
	private static final String VAR_O = "c";
	/**
	 * ingreso como director directivo
	 */
	private static final String VAR_D = "d";
	/**
	 * ingreso como administrador del proceos de autoeval
	 */
	private static final String VAR_A = "a";

	public String lecturaInd = "LECTURA";
	public String matrizInd = "MATRIZ";

	private String tituloPage = obtenerResourceBundle(AUTOEVAL).getString(
			"title.page.proceso.auto");

	// @EJB
	private LecturaIndicadoresBO lecturaIndicadoresBO;
	// CONTROLADORES
	@ManagedProperty(value = "#{dilIndicadorMB}")
	private IndicadoresMB dilIndicadorMB;

	@ManagedProperty(value = "#{dilMatrizMB}")
	private MatrizMB dilMatrizMB;

	// VARIABLES
	private Map<String, String> param;
	private String ingreso;
	/**
	 * variable que controla el acceso como director o otra dependencia en la
	 * vista
	 */
	private boolean matriz;
	private boolean indicadores;
	private TblthParticipante participante;
	private boolean mostrarBtnMatriz;
	private boolean esDirPrograma;
	private boolean esDirDependencia;
	private boolean esAdministrador;

	@PostConstruct
	public void init() throws BOException {
		try {
			LOGGER.info("INIT CONTROLADOR-iduser*" + Util.getOidAdmin());
			lookup();
			dilIndicadorMB.setDiligenciamientoMB(this);
			dilMatrizMB.setDiligenciamientoMB(this);

			participante = new TblthParticipante();
			participante = lecturaIndicadoresBO.buscarParticipanteOid(Util
					.getOidAdmin());
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			param = ec.getRequestParameterMap();
			ingreso = new String();
			if (Utilidad.validaNulos(ingreso)) {
				ingreso = param.get("id").toString();
			}
			LOGGER.info("INGRESO COMO : ----" + ingreso);
			inicializar();
		} catch (Exception ex) {
			lanzarMensajeError(null, ex);
		}

	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {

			lecturaIndicadoresBO = LookupUtil
					.lookupRemoteStateless(LecturaIndicadoresBO.class);
		} catch (NamingException e) {
			// ...
			msgError(e);
		}
	}

	@Override
	public void inicializar() {
		matriz = false;
		indicadores = false;

		if (ingreso.equals(VAR_D)) {
			setEsDirPrograma(true);
			setEsDirDependencia(false);
			setEsAdministrador(false);
			setMostrarBtnMatriz(true);
		} else if (ingreso.equals(VAR_O)) {
			setEsDirDependencia(true);
			setEsDirPrograma(false);
			setEsAdministrador(false);
		} else if (ingreso.equals(VAR_A)) {
			setEsDirPrograma(false);
			setEsDirDependencia(false);
			setEsAdministrador(true);
			setMostrarBtnMatriz(true);
		} else {
			// Mensaje acceso indebído o sin privilegios
			LOGGER.info("----ACCESO ILICITO-------" + ingreso);
		}
	}

	public void lanzar() {
		try {
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"page.title"));
		} catch (Exception e) {
			// LOGGER.info("Error al lanzar: " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void mostrarIndicadores(String vista) {
		indicadores = false;
		matriz = false;
		if (vista.equals(lecturaInd)) {
			tituloPage = obtenerResourceBundle(AUTOEVAL).getString(
					"title.page.proceso.lectura.indicadores");
			dilIndicadorMB.inicializar();
			indicadores = true;
			matriz = false;
		} else if (vista.equals(matrizInd)) {
			tituloPage = obtenerResourceBundle(AUTOEVAL).getString(
					"title.page.proceso.lectura.matriz");
			dilMatrizMB.inicializar();
			indicadores = false;
			matriz = true;
		}

	}

	public void msgError(Throwable e) {
		lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
				"msg.warn.lectura.guardo.error"));
		
		LOGGER.info(null, e);
	}

	public void home() {
		inicializar();
		LOGGER.info("  home()");
		Utilidad.redireccionarDir("/admin_acceso.xhtml");

	}

	// SETTES AND GETTES

	public boolean isMatriz() {
		return matriz;
	}

	public void setMatriz(boolean matriz) {
		this.matriz = matriz;
	}

	public boolean isIndicadores() {
		return indicadores;
	}

	public void setIndicadores(boolean indicadores) {
		this.indicadores = indicadores;
	}

	public IndicadoresMB getDilIndicadorMB() {
		return dilIndicadorMB;
	}

	public void setDilIndicadorMB(IndicadoresMB dilIndicadorMB) {
		this.dilIndicadorMB = dilIndicadorMB;
	}

	public MatrizMB getDilMatrizMB() {
		return dilMatrizMB;
	}

	public void setDilMatrizMB(MatrizMB dilMatrizMB) {
		this.dilMatrizMB = dilMatrizMB;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getIngreso() {
		return ingreso;
	}

	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}

	public TblthParticipante getParticipante() {
		return participante;
	}

	public void setParticipante(TblthParticipante participante) {
		this.participante = participante;
	}

	public String getTituloPage() {
		return tituloPage;
	}

	public void setTituloPage(String tituloPage) {
		this.tituloPage = tituloPage;
	}

	public boolean isMostrarBtnMatriz() {
		return mostrarBtnMatriz;
	}

	public void setMostrarBtnMatriz(boolean mostrarBtnMatriz) {
		this.mostrarBtnMatriz = mostrarBtnMatriz;
	}

	public String getLecturaInd() {
		return lecturaInd;
	}

	public void setLecturaInd(String lecturaInd) {
		this.lecturaInd = lecturaInd;
	}

	public String getMatrizInd() {
		return matrizInd;
	}

	public void setMatrizInd(String matrizInd) {
		this.matrizInd = matrizInd;
	}

	public boolean isEsDirPrograma() {
		return esDirPrograma;
	}

	public void setEsDirPrograma(boolean esDirPrograma) {
		this.esDirPrograma = esDirPrograma;
	}

	public boolean isEsDirDependencia() {
		return esDirDependencia;
	}

	public void setEsDirDependencia(boolean esDirDependencia) {
		this.esDirDependencia = esDirDependencia;
	}

	public boolean isEsAdministrador() {
		return esAdministrador;
	}

	public void setEsAdministrador(boolean esAdministrador) {
		this.esAdministrador = esAdministrador;
	}

}
