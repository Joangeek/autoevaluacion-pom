package com.autoevaluacion;

import gestion.MenuUserGruPer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import talentoHumano.TblthCargo;
import talentoHumano.TblthParticipante;
import autoevaluacion.TblautOtrasDependencia;
import commons.util.Cfg;
import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;
import util.BOException;
import gestion.Util;

import com.gestion.MenuUserGruPerBO;
import com.gestion.ParametrosBO;
import com.talentoHumano.CargosBO;
import com.talentoHumano.ParticipantesBO;

@ManagedBean(name = "otrasDepMB")
@ViewScoped
public class OtrasDependenciasMB extends BaseControlador implements
		Serializable {

	private static final long serialVersionUID = 5969382270580629836L;
	private static final String CODMENU = "0010";
	private static final Integer ACTIVO = 1;
	
	int year = Calendar.getInstance().get(Calendar.YEAR);
	private String titulo = obtenerResourceBundle(AUTOEVAL).getString(
			"title.page.otrasDependencias");
	// EJB
	@EJB
	private transient OtrasDependenciasBO otrasDependenciasBO;
	
	@EJB
	private transient MenuUserGruPerBO menuUserGruPerBO;
	
	@EJB
	private transient ParametrosBO parametrosBO;
	
	@EJB
	private transient ParticipantesBO participantesBO;
	@EJB
	private transient CargosBO cargosBO;

	// Acciones
	private boolean acCrear;
	private boolean acEditar;
	private boolean acRegresar;
	private boolean acEliminar;
	private boolean acEstado;

	// VARIABLES
	private List<TblautOtrasDependencia> listaOtrasDependencias;
	private List<TblautOtrasDependencia> listaOtrasDependenciasFilter;
	private List<TblthCargo> listaCargos;
	private List<TblthParticipante> listaPersonal;
	private List<Integer> listaVigencias;

	private TblautOtrasDependencia otraDependencia;
	private TblautOtrasDependencia selected;
	private TblautOtrasDependencia selectedClon;

	private String user = Util.getUserSession();
	private boolean lanzarCrear;
	private boolean lanzarEditar;

	@Override
	public void inicializar() {
		otraDependencia = new TblautOtrasDependencia();
		selected = new TblautOtrasDependencia();
		selectedClon = new TblautOtrasDependencia();
		listaOtrasDependencias = new ArrayList<TblautOtrasDependencia>();
		setLanzarCrear(false);
		setLanzarEditar(false);
		cargarTodos();
	}

	@PostConstruct
	public void init() throws BOException {
		try {

			LOGGER.info("INIT");
			inicializar();
			lookup();
			inicializar();
			cargarAccionesPerfil();
			cargarListas();
			cargarVigencias();

		} catch (Exception ex) {
			lanzarMensajeError(null, ex);
		}

	}

	private void cargarVigencias() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		listaVigencias = new ArrayList<Integer>();
		listaVigencias.add(0, (year));
		listaVigencias.add(1, (year + 1));

	}

	private void cargarListas() {
		try {
			listaCargos = cargosBO.buscarTodos(true);
			listaPersonal = participantesBO.buscarTodos(true);
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			LOGGER.info("Null P Conf ", e);
		} catch (Exception e) {
			LOGGER.info("Error al cargarListas: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {
			otrasDependenciasBO = LookupUtil
					.lookupRemoteStateless(OtrasDependenciasBO.class);
			cargosBO = LookupUtil.lookupRemoteStateless(CargosBO.class);
			participantesBO = LookupUtil
					.lookupRemoteStateless(ParticipantesBO.class);
			// ...
		} catch (NamingException e) {
			// ...
		}
	}

	public void onRowSelect() {
		lanzarMensajeInfo("Item seleccionado: "
				+ selected.getTblthCargo().getCargo());
		selectedClon = UtilidadBean.clonarObjecto(selected);

	}

	/**
	 * Método que consulta todos los datos relacionados a la entidad
	 */
	public void cargarTodos() {
		try {
			listaOtrasDependencias = otrasDependenciasBO.buscarTodos();
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			LOGGER.info("Null P Conf ", e);
		} catch (Exception e) {
			LOGGER.info("Error al cargarTodos: " + e.getMessage(), e);
		}

	}

	/**
	 * Método que crea un nuevo registro
	 */
	public void crear() {
		try {
			if (!validarCodigo()) {
				otraDependencia.setUsuario(user);
				otraDependencia.setEstado(ACTIVO);
				otrasDependenciasBO.crear(UtilidadBean
						.serialize(otraDependencia));
				lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
						"global.msg.exito.creo"));
				inicializar();
			}
		} catch (BOException e) {
			LOGGER.info("Error al create: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que valida que no se registresn datos relacionedos que ya estén
	 * configurados para y/o en una vigencia específica
	 * 
	 * @return
	 */
	public boolean validarCodigo() {
		boolean cod = false;

		for (TblautOtrasDependencia val : listaOtrasDependencias) {
			if ((val.getTblthCargo().getOid() == otraDependencia
					.getTblthCargo().getOid())
					&& val.getVigencia().equalsIgnoreCase(
							otraDependencia.getVigencia())) {
				lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
						"msg.warn.otra.dependencia.unico2"));

				cod = true;
				break;
			} else if (val.getTblthCargo().getOid() == otraDependencia
					.getTblthCargo().getOid()
					&& val.getTblthParticipante().getIdparticipante() == otraDependencia
							.getTblthParticipante().getIdparticipante()
					&& val.getVigencia().equalsIgnoreCase(
							otraDependencia.getVigencia())) {
				lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
						"msg.warn.otra.dependencia.unico2"));
				cod = true;
				break;
			} else if (val.getTblthCargo().getOid() == otraDependencia
					.getTblthCargo().getOid()
					&& val.getTblthParticipante().getIdparticipante() == otraDependencia
							.getTblthParticipante().getIdparticipante()
					&& val.getVigencia().equalsIgnoreCase(
							otraDependencia.getVigencia())
					&& val.getEstado() == otraDependencia.getEstado()) {
				lanzarMensajeWarn(obtenerResourceBundle(AUTOEVAL).getString(
						"msg.warn.otra.dependencia.unico3"));
				cod = true;
				break;
			}
		}
		return cod;
	}

	/**
	 * Método que valida las FK, en la edición si no se seleccionan nuevamente
	 * los valores vienen nulos
	 */
	private void validaciones() {
		if (!Utilidad.validaNulos(selected.getTblthCargo())) {
			selected.setTblthCargo(selectedClon.getTblthCargo());
		}
		if (!Utilidad.validaNulos(selected.getTblthParticipante())) {
			selected.setTblthParticipante(selectedClon.getTblthParticipante());
		}
		if (!Utilidad.validaNulos(selected.getVigencia())) {
			selected.setVigencia(selectedClon.getVigencia());
		}
	}

	/**
	 * Método que edita el registro
	 */
	public void editar() {
		try {
			validaciones();

			selected.setUsuario(user);
			otrasDependenciasBO.editar(UtilidadBean.serialize(selected));
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.edito"));
			inicializar();

		} catch (BOException e) {
			LOGGER.info("Error al edit: " + e.getMessage(), e);
		}
	}

	/**
	 * Metodo que elimina el registro
	 */
	public void eliminar() {

		try {
			if (Utilidad.validaNulos(selected.getOid())) {
				LOGGER.info("selected = " + selected.getTblthCargo().getCargo());
				// se setea el usuario que va a eliminar y se envía la
				// actualización del usuario selected.setUsuario(user);
				TblautOtrasDependencia entity = otrasDependenciasBO
						.editar(UtilidadBean.serialize(selected));
				// una ves actualixado se manda a eliminar el registro, la
				// acción se ejecuta para eveidenciar el usuario qe elimana el
				// registro
				LOGGER.info("selectedxxx");
				otrasDependenciasBO.eliminar(UtilidadBean.serialize(entity));
				lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
						"global.msg.exito.elimino"));
				inicializar();
				cargarTodos();
			} else {
				msgNoSeleccionado();
			}
		} catch (BOException e) {
			LOGGER.info("Error al delete: " + e.getMessage(), e);
		} catch (Exception e) {
			lanzarMensajeError(obtenerResourceBundle(COMUN).getString(
					"global.msg.error.eliminar.viola.forania.dependiente"));
		}

	}

	/**
	 * Método que permite evaluar al visivilidad del dialog(ventane emergente)
	 * de registro
	 * 
	 * @param val
	 */
	public void lanzarCrear(boolean val) {
		try {
			setLanzarCrear(val);
			reset();
			ejecutarUpdate("dlgNuevo");
		} catch (NullPointerException e) {
			LOGGER.info("Null P lc");
		}
	}

	/**
	 * Método que permite evaluar al visivilidad del dialog(ventane emergente)
	 * de edición
	 * 
	 * @param val
	 */
	public void lanzarEditar(boolean val) {
		try {
			if (null != selected.getOid()) {
				setLanzarEditar(val);
				if (!val)
					inicializar();
				ejecutarUpdate("dlgEditar");
			} else {
				msgNoSeleccionado();
			}
		} catch (NullPointerException e) {
			LOGGER.info("Null P le", e);
		}
	}

	/**
	 * Método que edita el registro
	 */
	public void cambiarEstado() {

		try {

			if (acEstado) {
				if (Utilidad.validaNulos(selected)) {
					LOGGER.info("selected = "
							+ selected.getTblthCargo().getCargo());
					otrasDependenciasBO.editarEstado(UtilidadBean
							.serialize(selected));
					inicializar();
					lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
							"global.msg.exito.edito"));
				} else {
					msgNoSeleccionado();
				}
			} else {
				lanzarMensajeWarn(obtenerResourceBundle(COMUN).getString(
						"global.msg.cambio.estado.sin.permisos"));
			}
		} catch (NullPointerException e) {
			LOGGER.info("NullPointerException " + e.getMessage(), e);
			// lanzarMensajeError("ERROR-", e);
		} catch (Exception e) {
			lanzarMensajeError("ERROR-", e);
			LOGGER.info("Exception " + e.getMessage(), e);
		}

	}

	/**
	 * Limpiar resetear entidad
	 */
	private void reset() {
		otraDependencia = new TblautOtrasDependencia();
	}

	public void configurar() {
		if (null != selected.getOid()) {
			// Utilidad.redireccionar(CONFIGURAR);
		} else {
			msgNoSeleccionado();
		}
	}

	private void cargarAccionesPerfil() {
		List<Integer> listMenuUsuGrupo = new ArrayList<Integer>();
		listMenuUsuGrupo = Util.getMenuUsuarioGrupo();
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
		}
	}

	// SETTES AND GETTES

	public List<TblautOtrasDependencia> getListaOtrasDependencias() {
		return listaOtrasDependencias;
	}

	public void setListaOtrasDependencias(
			List<TblautOtrasDependencia> listaOtrasDependencias) {
		this.listaOtrasDependencias = listaOtrasDependencias;
	}

	public List<TblautOtrasDependencia> getListaOtrasDependenciasFilter() {
		return listaOtrasDependenciasFilter;
	}

	public void setListaOtrasDependenciasFilter(
			List<TblautOtrasDependencia> listaOtrasDependenciasFilter) {
		this.listaOtrasDependenciasFilter = listaOtrasDependenciasFilter;
	}

	public TblautOtrasDependencia getSelected() {
		return selected;
	}

	public TblautOtrasDependencia getOtraDependencia() {
		return otraDependencia;
	}

	public void setOtraDependencia(TblautOtrasDependencia otraDependencia) {
		this.otraDependencia = otraDependencia;
	}

	public void setSelected(TblautOtrasDependencia selected) {
		this.selected = selected;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isLanzarCrear() {
		return lanzarCrear;
	}

	public void setLanzarCrear(boolean lanzarCrear) {
		this.lanzarCrear = lanzarCrear;
	}

	public boolean isLanzarEditar() {
		return lanzarEditar;
	}

	public void setLanzarEditar(boolean lanzarEditar) {
		this.lanzarEditar = lanzarEditar;
	}

	public List<TblthCargo> getListaCargos() {
		return listaCargos;
	}

	public void setListaCargos(List<TblthCargo> listaCargos) {
		this.listaCargos = listaCargos;
	}

	public List<TblthParticipante> getListaPersonal() {
		return listaPersonal;
	}

	public void setListaPersonal(List<TblthParticipante> listaPersonal) {
		this.listaPersonal = listaPersonal;
	}

	public List<Integer> getListaVigencias() {
		return listaVigencias;
	}

	public void setListaVigencias(List<Integer> listaVigencias) {
		this.listaVigencias = listaVigencias;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
