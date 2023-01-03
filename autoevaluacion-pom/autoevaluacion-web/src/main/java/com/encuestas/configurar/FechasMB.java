package com.encuestas.configurar;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import academico.TblacaPeriodo;

import com.academico.PeriodoBO;

import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;
import com.encuesta.ProgramacionBO;
import util.BOException;
import gestion.Util;

import encuestas.TblencProgramacionEncuesta;
import encuestas.TblencTipoEvaluacion;

@ManagedBean(name = "fechasMB")
@ViewScoped
public class FechasMB extends BaseControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5501186905206614466L;
	/**
	 * 
	 */

	private static final String DATATABLE = "dataTableFechas";
	private static final Integer LIMITE = 2;
	private static final Integer OFFSET = 0;
	/**
     * 
     */

	// EJB
	@EJB
	private PeriodoBO periodoBO;

	@EJB
	private ProgramacionBO programacionBO;

	// CONTROLADORES
	private ConfigurarMB configurarMB;

	// VARIABLES
	private List<TblencProgramacionEncuesta> listaProgramacion;
	private List<TblencProgramacionEncuesta> listaProgramacionFilter;
	private TblencProgramacionEncuesta programacion;
	private TblencProgramacionEncuesta selected;
	private TblencProgramacionEncuesta selectedClon;

	private TblencTipoEvaluacion tipoEvaluacionSelected;

	private List<TblacaPeriodo> listaPeriodos;
	private List<TblacaPeriodo> listaPeriodosFilter;

	private String user = Util.getUserSession();
	private String fecha;

	private boolean crearProE;
	private boolean editarProE;
	private boolean estadoAC;

	@Override
	public void inicializar() {
		listaProgramacion = new ArrayList<TblencProgramacionEncuesta>();
		listaPeriodos = new ArrayList<TblacaPeriodo>();
		listaPeriodosFilter = new ArrayList<TblacaPeriodo>();

		programacion = new TblencProgramacionEncuesta();
		selected = new TblencProgramacionEncuesta();
		selectedClon = new TblencProgramacionEncuesta();
		crearProE = false;
		editarProE = false;
	}

	@PostConstruct
	public void init() throws BOException {
		try {
			LOGGER.debug("INIT FECHAS");
			SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
			fecha = format.format(new Date());
			inicializar();
			lookup();
		} catch (Exception ex) {
			lanzarMensajeError(null, ex);
		}

	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {
			programacionBO = LookupUtil
					.lookupRemoteStateless(ProgramacionBO.class);
			periodoBO = LookupUtil.lookupRemoteStateless(PeriodoBO.class);
			// ...
		} catch (NamingException e) {
			// ...
		}
	}

	public void cargarListas() {
		try {
			listaPeriodos = periodoBO.buscarTodosLimite(LIMITE, OFFSET);
			listaPeriodosFilter = programacionBO.buscarPeriodos(UtilidadBean
					.serialize(tipoEvaluacionSelected));
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			LOGGER.info("Null P lis ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarListas: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que consulta todos los datos relacionados a la entidad
	 */
	public void cargarTodos() {
		try {
			listaProgramacion = programacionBO.buscarPorEvaluacion(UtilidadBean
					.serialize(tipoEvaluacionSelected));

			ejecutarUpdate(DATATABLE);
		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			LOGGER.info("Null P ct ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarTodos: " + e.getMessage(), e);
		}

	}

	/**
	 * Método que crea un nuevo registro
	 */
	public void crear() {
		try {
			programacion.setUsuario(user);
			programacion.setTblencTipoEvaluacion(tipoEvaluacionSelected);
			programacionBO.crear(UtilidadBean.serialize(programacion));
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.creo"));
			inicializar();
			cargarTodos();
			cargarListas();
			lanzarCrear(crearProE);
			ejecutarUpdate(DATATABLE);
		} catch (BOException e) {
			LOGGER.info("Error al create: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que edita el registro
	 */
	public void editar() {
		try {
			validaciones();
			selected.setUsuario(user);
			programacionBO.editar(UtilidadBean.serialize(selected));
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.edito"));
			inicializar();
			cargarTodos();
			cargarListas();
			ejecutarUpdate("dlgEditarTE");
			ejecutarUpdate(DATATABLE);
		} catch (BOException e) {
			LOGGER.info("Error al edit: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que valida las FK, en la edición si no se seleccionan nuevamente
	 * los valores vienen nulos
	 */
	private void validaciones() {

		if (!Utilidad.validaNulos(selected.getTblencTipoEvaluacion())) {
			selected.setTblencTipoEvaluacion(selectedClon
					.getTblencTipoEvaluacion());
		}

		if (!Utilidad.validaNulos(selected.getTblacaPeriodo())) {
			selected.setTblacaPeriodo(selectedClon.getTblacaPeriodo());
		}
	}

	/**
	 * Metodo que elimina el registro
	 */
	public void eliminar() {

		try {
			selectedClon = new TblencProgramacionEncuesta();
			if (Utilidad.validaNulos(selected.getOid())) {
				LOGGER.info("selected = " + selected.getOid());
				// se setea el usuario que va a eliminar y se envía la
				// actualización del usuario selected.setUsuario(user);
				TblencProgramacionEncuesta entity = programacionBO
						.editar(UtilidadBean.serialize(selected));
				// una ves actualixado se manda a eliminar el registro, la
				// acción se ejecuta para eveidenciar el usuario qe elimana el
				// registro
				programacionBO.eliminar(UtilidadBean.serialize(entity));
				// lanzarValidacion(val);

				inicializar();
				cargarTodos();
				cargarListas();
			} else {
				msgNoSeleccionado();
			}
		} catch (BOException e) {
			LOGGER.info("Error al delete: " + e.getMessage(), e);
			if (e.getMessage().equals("HIJOS")) {
				lanzarValidacion(1);
			}
		}

	}

	private void lanzarValidacion(Integer val) {

		if (val == 0) {
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.exito.elimino"));
		} else if (val == 1) {
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
					"global.msg.error.eliminar.viola.forania.dependiente"));
		}
	}

	public void onRowSelect() {
		lanzarMensajeInfo("Item seleccionado" + selected == null ? "vacio"
				: "lleno");
		configurarMB.cargarProgramacionSedesProgramas(selected);
		selectedClon = UtilidadBean.clonarObjecto(selected);
	}

	/**
	 * Método que edita el registro
	 */
	public void cambiarEstado() {

		try {

			if (estadoAC) {
				if (Utilidad.validaNulos(selected)) {
					LOGGER.info("selected = " + selected.getOid());

					programacionBO.editar(UtilidadBean.serialize(selected));
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
	 * Método que permite evaluar al visivilidad del dialog(ventane emergente)
	 * de registro
	 * 
	 * @param val
	 */
	public void lanzarCrear(boolean val) {
		LOGGER.info("lanzarCrear -- " + val);
		crearProE = val;
		try {
			ejecutarUpdate("dlgNuevoPro");
			reset();
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
				editarProE = val;
				if (!val) {
					inicializar();
					cargarTodos();
				}
				ejecutarUpdate("dlgEditarPro");
			} else {
				editarProE = false;
				msgNoSeleccionado();
			}
		} catch (NullPointerException e) {
			LOGGER.info("Null P le", e);
		}
	}

	public String contar(TblencProgramacionEncuesta oid) {
		try {
			Integer val = programacionBO
					.contarSedesProgramasConfig(UtilidadBean.serialize(oid));
			return val.toString();
		} catch (BOException e) {
			LOGGER.info("calcular");
		}
		return "0";

	}

	/**
	 * Limpiar resetear entidad
	 */
	private void reset() {
		programacion = new TblencProgramacionEncuesta();
	}

	// SETTES AND GETTES

	public ConfigurarMB getConfigurarMB() {
		return configurarMB;
	}

	public void setConfigurarMB(ConfigurarMB configurarMB) {
		this.configurarMB = configurarMB;
	}

	public List<TblencProgramacionEncuesta> getListaProgramacion() {
		return listaProgramacion;
	}

	public void setListaProgramacion(
			List<TblencProgramacionEncuesta> listaProgramacion) {
		this.listaProgramacion = listaProgramacion;
	}

	public List<TblencProgramacionEncuesta> getListaProgramacionFilter() {
		return listaProgramacionFilter;
	}

	public void setListaProgramacionFilter(
			List<TblencProgramacionEncuesta> listaProgramacionFilter) {
		this.listaProgramacionFilter = listaProgramacionFilter;
	}

	public TblencProgramacionEncuesta getProgramacion() {
		return programacion;
	}

	public void setProgramacion(TblencProgramacionEncuesta programacion) {
		this.programacion = programacion;
	}

	public TblencProgramacionEncuesta getSelected() {
		return selected;
	}

	public void setSelected(TblencProgramacionEncuesta selected) {
		this.selected = selected;
	}

	public List<TblacaPeriodo> getListaPeriodos() {
		return listaPeriodos;
	}

	public void setListaPeriodos(List<TblacaPeriodo> listaPeriodos) {
		this.listaPeriodos = listaPeriodos;
	}

	public List<TblacaPeriodo> getListaPeriodosFilter() {
		return listaPeriodosFilter;
	}

	public void setListaPeriodosFilter(List<TblacaPeriodo> listaPeriodosFilter) {
		this.listaPeriodosFilter = listaPeriodosFilter;
	}

	public boolean isCrearProE() {
		return crearProE;
	}

	public void setCrearProE(boolean crearProE) {
		this.crearProE = crearProE;
	}

	public boolean isEditarProE() {
		return editarProE;
	}

	public void setEditarProE(boolean editarProE) {
		this.editarProE = editarProE;
	}

	public boolean isEstadoAC() {
		return estadoAC;
	}

	public void setEstadoAC(boolean estadoAC) {
		this.estadoAC = estadoAC;
	}

	public TblencTipoEvaluacion getTipoEvaluacionSelected() {
		return tipoEvaluacionSelected;
	}

	public void setTipoEvaluacionSelected(
			TblencTipoEvaluacion tipoEvaluacionSelected) {
		this.tipoEvaluacionSelected = tipoEvaluacionSelected;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
