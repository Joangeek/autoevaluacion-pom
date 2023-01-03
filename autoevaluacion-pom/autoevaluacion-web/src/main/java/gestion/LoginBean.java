package gestion;

import gestion.Parametros;
import gestion.UsuarioGrupo;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.jboss.security.annotation.SecurityDomain;

import com.comun.UsuarioBO;
import com.gestion.IngresoBO;
import com.gestion.ParametrosBO;

import academico.TblestEstudiante;
import auditoria.Tblingreso;


import commons.exceptions.AutoevaluacionBOException;
import comun.controlador.util.UtilidadGenerico;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;
import comun.Tblempleadore;

@ManagedBean(name = "loginBean")
@SessionScoped
@SecurityDomain(value = "jBossJaasPostgresqlRealm")
public class LoginBean extends BaseControlador implements Serializable {

	private static final long serialVersionUID = 1L;
	private String password;
	private String message, uname, usuario;
	private static final String ADMINISTRATIVO = "0001";
	private static final String ESTUDIANTE = "0002";
	private static final String EGRESADO = "0003";
	private static final String OTRO = "0004";
	private static final String ANDMINEGRESADOS = "0008";
	public static String MD5 = "MD5";

	/*
	 * public String getPrincipal() { EJBContext context = null; Principal pp =
	 * context.getCallerPrincipal(); System.out.println(pp.toString());
	 * 
	 * return (String) (context.getCallerPrincipal().getName()); }
	 */

	// EJB
	@EJB
	private ParametrosBO parametrosBO;
	@EJB
	private UsuarioBO usuarioBO;
	@EJB
	private IngresoBO ingresoBO;
	// VARIABLES
	private String tipoUsuario;

	@PostConstruct
	public void init() {
		LOGGER.info("init - LoginBean");
		tipoUsuario = ADMINISTRATIVO;
		lookup();
	}

	@Override
	public void inicializar() {
		password = new String();
		usuario = new String();
		uname = new String();
	}

	private void lookup() {
		try {
			parametrosBO = LookupUtil.lookupRemoteStateless(ParametrosBO.class);
			usuarioBO = LookupUtil.lookupRemoteStateless(UsuarioBO.class);
			ingresoBO = LookupUtil.lookupRemoteStateless(IngresoBO.class);
		} catch (NamingException e) {
			LOGGER.info("init - LoginBean");
		}
	}

	/**
	 * Método que carga la lista de selección del loguéo que permite identificar
	 * el tipo de usuario q solicita acceso al sistema
	 * 
	 * @return
	 */
	public List<String> tipoAcceso() {
		List<String> lista = new ArrayList<String>();
		try {
			lista.add(0, parametrosBO.buscarCodigo(ADMINISTRATIVO)
					.getValString());
			lista.add(1, parametrosBO.buscarCodigo(ESTUDIANTE).getValString());
			lista.add(2, parametrosBO.buscarCodigo(EGRESADO).getValString());

			Parametros param = parametrosBO.buscarCodigo(OTRO);
			if (param.getEstado() == 1) {
				lista.add(3, param.getValString());
			}
			return lista;
		} catch (AutoevaluacionBOException e) {
			LOGGER.info("ERROR -- ", e);
			return new ArrayList<String>();
		}

	}

	/*
	 * public String validaSession() { LOGGER.debug("loginProject");
	 * System.out.println("----------" + uname);
	 * 
	 * System.out.println("A login request was issued from " +
	 * Util.getRequest().getRemoteHost() + ":" +
	 * Util.getRequest().getRemotePort() +":" +
	 * Util.getRequest().getRemoteUser()); try { Principal userPrincipal =
	 * Util.getRequest().getUserPrincipal(); if
	 * (Util.getRequest().getUserPrincipal() != null) {
	 * Util.getRequest().logout(); } //Util.getRequest().login(getUname(),
	 * getPassword()); //userPrincipal = Util.getRequest().getUserPrincipal();
	 * //authUser = userDao.findByLogin(userPrincipal.getName());
	 * usuario="EDUAR ALEJANDRO TROYANO VELÁSQUEZ"; return "admin"; /* if
	 * (Util.getRequest().isUserInRole("ADMIN")) { System.out
	 * .println("The logged in user is an administrator (admin)"); return
	 * "admin"; } else { System.out
	 * .println("The logged in user is NOT an administrator (admin)"); return
	 * "login"; }
	 */
	/*
	 * } catch (ServletException e) {
	 * System.out.println("Failed to login with username (" + uname +
	 * ") and password. " +password+"---"+ e.getMessage());
	 * FacesContext.getCurrentInstance().addMessage( null, new
	 * FacesMessage(FacesMessage.SEVERITY_WARN,
	 * "Login failed when tying to authenticate.", null)); // If this fails,
	 * return null.
	 * java.util.logging.Logger.getLogger(LoginBean.class.getName()).log(
	 * Level.SEVERE, null, e); return "login"; } // getPrincipal(); // Personal
	 * resultPersonal = personalDAO.validarAcceso(uname, password); // if
	 * (resultPersonal != null) { /* if (null != uname) { // usuario =
	 * resultPersonal.getPrimerNombre() + " " // +
	 * resultPersonal.getPrimerApellido(); usuario = "Eduar Troyano"; // get
	 * Http Session and store username HttpSession session = Util.getSession();
	 * Util.setAttribute("username", usuario);
	 * Util.setAttribute("userSession", uname);
	 * 
	 * return "admin"; } else {
	 * 
	 * FacesContext.getCurrentInstance() .addMessage( null, new
	 * FacesMessage(FacesMessage.SEVERITY_WARN, "Ingreso inválido!",
	 * "Por favor Intente de nuevo!")); return "login"; }
	 */
	// }

	/**
	 * Método que evalua si los datos de acceso son inicialmente corerectos,
	 * crea la sessión y carga los parámetros
	 * 
	 * @return
	 * @throws AutoevaluacionBOException
	 */
	public String loginEmpleador() throws AutoevaluacionBOException {
		usuario = new String();

		Tblempleadore resultPersonal = new Tblempleadore();
		try {

			resultPersonal = usuarioBO.validarAccesEmpleador(uname);
		} catch (AutoevaluacionBOException e) {
			LOGGER.info("Error LOGIn ON- ", e);
		}

		// /condicion de validar login para usuario:

		if (resultPersonal != null && validarPass(resultPersonal.getPassword())) {
			usuario = resultPersonal.getNombreEmpresa().toUpperCase();
			LOGGER.info(usuario);
			// Http Session and store username
			varSession(resultPersonal.getOid(), resultPersonal.getOid(),
					tipoUsuario);
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.acceso.correcto"));
			// Registro de ingreso - auditoria
			ingresoAU(resultPersonal.getOid());
			return "empleador";
		} else {
			lanzarMensajeError(obtenerResourceBundle(COMUN).getString(
					"global.msg.acceso.incorrecto"));

			return "login";
		}

	}

	/**
	 * Método que evalua si los datos de acceso son inicialmente corerectos,
	 * crea la sessión y carga los parámetros
	 * 
	 * @return
	 * @throws AutoevaluacionBOException
	 */
	public String loginProject() throws AutoevaluacionBOException {

		LOGGER.info("ACCEDE COMO_" + tipoUsuario);

		if (tipoUsuario.equals(parametrosBO.buscarCodigo(ADMINISTRATIVO)
				.getValString())) {

			LOGGER.info("ADMINISTRATIVO");

			return validarAdministrativo();

		}

		else if (tipoUsuario.equals(parametrosBO.buscarCodigo(ESTUDIANTE)
				.getValString())) {

			LOGGER.info("ESTUDIANTE");

			return validarEstudiante();

		}

		else if (tipoUsuario.equals(parametrosBO.buscarCodigo(EGRESADO)
				.getValString())) {

			LOGGER.info("EGRESADO");

			return validarEgresado();

		} else if (tipoUsuario.equals(parametrosBO.buscarCodigo(OTRO)
				.getValString())) {

			LOGGER.info("EMPLEADOR OTRO");

			return loginEmpleador();

		} else {

			LOGGER.info("mensaje error");

			return "login";
		}

	}

	private String validarEgresado() throws AutoevaluacionBOException {

		usuario = new String();

		TblestEstudiante resultPersonal = new TblestEstudiante();
		try {

			resultPersonal = usuarioBO.validarAcceso(uname,
					UtilidadGenerico.epEstadoGraduado);
		} catch (AutoevaluacionBOException e) {
			LOGGER.info("Error LOGIn ON- ", e);
		}

		// /condicion de validar login para usuario:

		if (resultPersonal != null && validarPass(resultPersonal.getClave())) {
			usuario = resultPersonal.getNombres();
			LOGGER.info(usuario);
			// Http Session and store username
			varSession(resultPersonal.getOid(), resultPersonal.getOid(),
					tipoUsuario);
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.acceso.correcto"));
			// Registro de ingreso - auditoria
			ingresoAU(resultPersonal.getOid());
			return "egresado";
		} else {
			lanzarMensajeError(obtenerResourceBundle(COMUN).getString(
					"global.msg.acceso.incorrecto"));

			return "login";
		}

	}

	private String validarEstudiante() throws AutoevaluacionBOException {

		usuario = new String();

		TblestEstudiante resultPersonal = new TblestEstudiante();
		try {

			resultPersonal = usuarioBO.validarAcceso(uname);
		} catch (AutoevaluacionBOException e) {
			LOGGER.info("Error LOGIn ON- ", e);
		}

		// /condicion de validar login para usuario:

		if (resultPersonal != null && validarPass(resultPersonal.getClave())) {
			usuario = resultPersonal.getNombres();
			LOGGER.info(usuario);
			// Http Session and store username
			varSession(resultPersonal.getOid(), resultPersonal.getOid(),
					tipoUsuario);
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.acceso.correcto"));
			// Registro de ingreso - auditoria
			ingresoAU(resultPersonal.getOid());
			return "estudiante";
		} else {
			lanzarMensajeError(obtenerResourceBundle(COMUN).getString(
					"global.msg.acceso.incorrecto"));

			return "login";
		}

	}

	private String validarAdministrativo() throws AutoevaluacionBOException {

		usuario = new String();

		UsuarioGrupo resultPersonal = new UsuarioGrupo();
		try {

			resultPersonal = usuarioBO.validarAcceso(uname, tipoUsuario);
		} catch (AutoevaluacionBOException e) {
			LOGGER.info("Error LOGIn ON- ", e);
		}
		//
		// LOGGER.info(resultPersonal == null);
		// LOGGER.info("---------------------------------->"
		// + resultPersonal.getUsuario().getUsuLogin());
		// /condicion de validar login para usuario:

		if (resultPersonal != null
				&& validarPass(resultPersonal.getUsuario().getUsuPassword())) {
			usuario = resultPersonal.getUsuario().getIdparticipante()
					.getNombre();
			LOGGER.info(usuario);
			// Http Session and store username
			varSession(resultPersonal.getUsgCodigo(), resultPersonal
					.getUsuario().getIdparticipante().getIdparticipante(),
					tipoUsuario);
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.acceso.correcto"));
			// Registro de ingreso - auditoria
			ingresoAU(resultPersonal.getUsuario().getIdparticipante()
					.getIdparticipante());
			return "home";
		} else {
			lanzarMensajeError(obtenerResourceBundle(COMUN).getString(
					"global.msg.acceso.incorrecto"));

			return "login";
		}

	}

	/**
	 * Método que carga las variables de sessión- en MenuMB se carga una
	 * variable adicional que establece los menu_usuario_grupo
	 * 
	 * @param code
	 * @param OidAdmin
	 */
	private void varSession(Integer code, Integer OidAdmin, String tipoUsuario) {
		// get Http Session and store username
		HttpSession session = Util.getSession();
		session.setAttribute("username", usuario);
		session.setAttribute("userGroup", code);
		session.setAttribute("userSession", uname);
		session.setAttribute("OidAdmin", OidAdmin);
		session.setAttribute("tipoUsuario", tipoUsuario);
	}

	/**
	 * Método que registra auditoria ingreso del usuario
	 * 
	 * @param idparticipante
	 */
	private void ingresoAU(Integer idparticipante) {
		Tblingreso ingreso = new Tblingreso();

		try {
			ingreso.setUsuario(idparticipante.toString());
			ingresoBO.ingreso(ingreso);
		} catch (AutoevaluacionBOException e) {
			LOGGER.info(e);
		}

	}

	/**
	 * Método que valida que el password ingresado corresponda en base de datos
	 * según encriptación MD%
	 * 
	 * @param usuPassword
	 * @return
	 * @throws AutoevaluacionBOException
	 */
	private boolean validarPass(String usuPassword)
			throws AutoevaluacionBOException {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(MD5);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.info(e);
		}
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		// System.out.println("Digest(in hex format):: " + sb.toString());

		// convert the byte to hex format method 2
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		// System.out.println("Digest(in hex format):: " +
		// hexString.toString());
		// System.out.println(usuPassword);
		if (hexString.toString().equals(usuPassword)) {
			return true;
		} else if (password.equals(parametrosBO.buscarCodigo(ANDMINEGRESADOS)
				.getValString())) {
			LOGGER.info("LOGUEO COMO ADMIN EGRESADOS ----> ");
			return true;
		}

		return false;

	}

	/**
	 * Método que termina sessión y redirecciona a login
	 * 
	 * @return
	 */
	public String logout() {
		LOGGER.info(" logout()------");
		HttpSession session = Util.getSession();
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {

			inicializar();
			context.redirect("login.xhtml");

		} catch (IOException e) {
			LOGGER.info(" logout() IOException--------->>");

		} catch (Exception e) {
			LOGGER.info(" logout() Exception--------->>");

		}
		session.invalidate();
		return "login";
	}

	// SETTES AND GETTES
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}