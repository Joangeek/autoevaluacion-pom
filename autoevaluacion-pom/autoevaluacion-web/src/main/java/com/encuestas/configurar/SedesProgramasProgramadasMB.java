package com.encuestas.configurar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import org.primefaces.event.RowEditEvent;

import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblacaSedePrograma;

import com.academico.SedeProgramaBO;

import commons.util.UtilidadBean;
import comun.controlador.util.Utilidad;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;

import com.encuesta.ProgramacionBO;

import util.BOException;
import encuestas.TblencProgramacionEncSedeProg;
import encuestas.TblencProgramacionEncuesta;
import gestion.Util;

@ManagedBean(name = "spProgramadasMB")
@ViewScoped
public class SedesProgramasProgramadasMB extends BaseControlador implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5501186905206614466L;
	/**
	 * 
	 */

	private static final String DATATABLE = "dataTableFechas";
	private static final boolean TRUE = true;
	/**
     * 
     */

	// EJB
	@EJB
	private SedeProgramaBO sedeProgramaBO;

	@EJB
	private ProgramacionBO programacionBO;

	// CONTROLADORES
	private ConfigurarMB configurarMB;

	// VARIABLES
	private List<TblencProgramacionEncSedeProg> listaSedesProgramas;
	private List<TblencProgramacionEncSedeProg> listaSedesProgramasFilter;
	private List<TblacaSedePrograma> sedesProgramasSelectes;
	private List<Integer> sedesProgramasSelectesI;
	private TblencProgramacionEncSedeProg sedeProgramaProg;
	private TblencProgramacionEncSedeProg selected;

	private TblencProgramacionEncuesta programacionSelected;
	private TblacaSede sedeSelected;

	private List<TblacaSede> listaSedes;
	private List<TblacaSede> listaSedesFilter;

	private List<TblacaSedePrograma> listaProgramas;
	private List<TblacaSedePrograma> listaProgramasSelected;
	private List<TblacaPrograma> listaProgramasFilter;

	private TblacaSedePrograma sedeprog;

	private List<TblacaPrograma> listaP;
	private List<TblacaPrograma> listaPs;

	private String user = Util.getUserSession();

	private boolean crearProSp;
	private boolean editarProSp;
	private boolean estadoAC;

	@Override
	public void inicializar() {
		listaSedesProgramas = new ArrayList<TblencProgramacionEncSedeProg>();
		sedesProgramasSelectes = new ArrayList<TblacaSedePrograma>();

		listaSedes = new ArrayList<TblacaSede>();
		listaSedesFilter = new ArrayList<TblacaSede>();

		listaProgramas = new ArrayList<TblacaSedePrograma>();
		listaProgramasFilter = new ArrayList<TblacaPrograma>();
		listaProgramasSelected = new ArrayList<TblacaSedePrograma>();

		sedeProgramaProg = new TblencProgramacionEncSedeProg();
		selected = new TblencProgramacionEncSedeProg();
		crearProSp = false;
		editarProSp = false;
	}

	@PostConstruct
	public void init() throws BOException {
		try {
			LOGGER.debug("INIT prgramacion sedesProgramas");
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
			sedeProgramaBO = LookupUtil
					.lookupRemoteStateless(SedeProgramaBO.class);
			// ...
		} catch (NamingException e) {
			// ...
		}
	}

	public void cargarListas() {
		try {
			listaSedes = sedeProgramaBO.buscarSedesEstadoPorSp(TRUE);
			listaSedesFilter = programacionBO.buscarSedesPorSpIN(UtilidadBean
					.serialize(programacionSelected));

			listaProgramasFilter = programacionBO
					.buscarProgramasPorSpIN(UtilidadBean
							.serialize(programacionSelected));

		} catch (BOException e) {
			LOGGER.info("AutoevaluacionBOException cargarT ");
		} catch (NullPointerException e) {
			LOGGER.info("Null P lis ");
		} catch (Exception e) {
			LOGGER.info("Error al cargarListas: " + e.getMessage(), e);
		}
	}

	public void cargarProgramas() {
		try {
			LOGGER.info("-----------------------"
					+ listaProgramas.size()
					+ "   sede id"
					+ ((sedeSelected.getOid() == null) ? "null" : sedeSelected
							.getOid()));
			listaProgramas = sedeProgramaBO.buscarProgramasEstadoSp(
					sedeSelected.getOid(), TRUE);
			LOGGER.info("-----------------------" + listaProgramas.size());
			// LOGGER.info("cargarProgramas 1");
			// listaPs = programacionBO.buscarTodosP();
			// / LOGGER.info("cargarProgramas 2");

		} catch (Exception e) {
			LOGGER.info("Error al cargarProgramas: ");
		}

	}

	/**
	 * Método que consulta todos los datos relacionados a la entidad
	 */
	public void cargarTodos() {
		try {
			listaSedesProgramas = programacionBO
					.sedesProgramasConfig(UtilidadBean
							.serialize(programacionSelected));

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
			LOGGER.info("selected progra = " + programacionSelected.getOid());
			LOGGER.info("SIZE = " + sedesProgramasSelectes.size());
			if (Utilidad.validaNulos(programacionSelected.getOid())
					&& sedesProgramasSelectes.size() != 0) {

				for (TblacaSedePrograma sp : sedesProgramasSelectes) {
					sedeProgramaProg.setUsuario(user);
					sedeProgramaProg
							.setTblencProgramacionEncuesta(programacionSelected);
					sedeProgramaProg.setTblacaSedePrograma(sp);
					programacionBO.crearPsp(UtilidadBean
							.serialize(sedeProgramaProg));
					sedeProgramaProg = new TblencProgramacionEncSedeProg();
				}

				lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString(
						"global.msg.exito.creo"));
				inicializar();
				cargarTodos();
				cargarListas();
				lanzarCrear(crearProSp);
				ejecutarUpdate(DATATABLE);

			} else {
				LOGGER.info("noooooooooooooooooooooooooooooo");
			}
		} catch (BOException e) {
			LOGGER.info("Error al create: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que edita el registro
	 */
	public void onRowEdit(RowEditEvent event) {
		try {
			selected = (TblencProgramacionEncSedeProg) event.getObject();
			selected.setUsuario(user);

			programacionBO.editarPsp(UtilidadBean.serialize(selected));
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
	 * Metodo que elimina el registro
	 */
	public void eliminar() {

		try {

			if (Utilidad.validaNulos(selected.getOid())) {
				LOGGER.info("selected = " + selected.getOid());
				// se setea el usuario que va a eliminar y se envía la
				// actualización del usuario selected.setUsuario(user);
				TblencProgramacionEncSedeProg entity = programacionBO
						.editarPsp(UtilidadBean.serialize(selected));
				// una ves actualixado se manda a eliminar el registro, la
				// acción se ejecuta para eveidenciar el usuario qe elimana el
				// registro
				programacionBO.eliminarPsp(UtilidadBean.serialize(entity));
				// lanzarValidacion(val);

				inicializar();
				cargarTodos();
				cargarListas();
				ejecutarUpdate(DATATABLE);
			} else {
				msgNoSeleccionado();
			}
		} catch (BOException e) {
			LOGGER.info("Error al delete: " + e.getMessage(), e);
			if (e.getMessage().equals("HIJOS")) {
				lanzarValidacion(1);
			}
		} catch (NullPointerException e) {
			msgNoSeleccionado();
			LOGGER.info("NullPointerException delete: ");
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
		LOGGER.info("selected ID= " + selected.getOid());
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
		crearProSp = val;
		try {
			cargarListas();
			ejecutarUpdate("dlgNuevoProSP");
			reset();
		} catch (NullPointerException e) {
			LOGGER.info("Null P lcPsp");
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
				editarProSp = val;
				if (!val) {
					inicializar();
					cargarTodos();
				}
				ejecutarUpdate("dlgEditarProSP");
			} else {
				editarProSp = false;
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
		sedeProgramaProg = new TblencProgramacionEncSedeProg();
		sedeSelected = new TblacaSede();
		listaProgramas = new ArrayList<TblacaSedePrograma>();
		sedesProgramasSelectes = new ArrayList<TblacaSedePrograma>();
	}

	// SETTES AND GETTES

	public ConfigurarMB getConfigurarMB() {
		return configurarMB;
	}

	public void setConfigurarMB(ConfigurarMB configurarMB) {
		this.configurarMB = configurarMB;
	}

	public List<TblencProgramacionEncSedeProg> getListaSedesProgramas() {
		return listaSedesProgramas;
	}

	public void setListaSedesProgramas(
			List<TblencProgramacionEncSedeProg> listaSedesProgramas) {
		this.listaSedesProgramas = listaSedesProgramas;
	}

	public List<TblencProgramacionEncSedeProg> getListaSedesProgramasFilter() {
		return listaSedesProgramasFilter;
	}

	public void setListaSedesProgramasFilter(
			List<TblencProgramacionEncSedeProg> listaSedesProgramasFilter) {
		this.listaSedesProgramasFilter = listaSedesProgramasFilter;
	}

	public TblencProgramacionEncSedeProg getSedeProgramaProg() {
		return sedeProgramaProg;
	}

	public void setSedeProgramaProg(
			TblencProgramacionEncSedeProg sedeProgramaProg) {
		this.sedeProgramaProg = sedeProgramaProg;
	}

	public TblencProgramacionEncSedeProg getSelected() {
		return selected;
	}

	public void setSelected(TblencProgramacionEncSedeProg selected) {
		this.selected = selected;
	}

	public TblencProgramacionEncuesta getProgramacionSelected() {
		return programacionSelected;
	}

	public void setProgramacionSelected(
			TblencProgramacionEncuesta programacionSelected) {
		this.programacionSelected = programacionSelected;
	}

	public List<TblacaSede> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<TblacaSede> listaSedes) {
		this.listaSedes = listaSedes;
	}

	public List<TblacaSede> getListaSedesFilter() {
		return listaSedesFilter;
	}

	public void setListaSedesFilter(List<TblacaSede> listaSedesFilter) {
		this.listaSedesFilter = listaSedesFilter;
	}

	public List<TblacaSedePrograma> getListaProgramas() {
		return listaProgramas;
	}

	public void setListaProgramas(List<TblacaSedePrograma> listaProgramas) {
		this.listaProgramas = listaProgramas;
	}

	public List<TblacaPrograma> getListaProgramasFilter() {
		return listaProgramasFilter;
	}

	public void setListaProgramasFilter(
			List<TblacaPrograma> listaProgramasFilter) {
		this.listaProgramasFilter = listaProgramasFilter;
	}

	public boolean isCrearProSp() {
		return crearProSp;
	}

	public void setCrearProSp(boolean crearProSp) {
		this.crearProSp = crearProSp;
	}

	public boolean isEditarProSp() {
		return editarProSp;
	}

	public void setEditarProSp(boolean editarProSp) {
		this.editarProSp = editarProSp;
	}

	public boolean isEstadoAC() {
		return estadoAC;
	}

	public void setEstadoAC(boolean estadoAC) {
		this.estadoAC = estadoAC;
	}

	public TblacaSede getSedeSelected() {
		return sedeSelected;
	}

	public void setSedeSelected(TblacaSede sedeSelected) {
		this.sedeSelected = sedeSelected;
	}

	public List<TblacaSedePrograma> getSedesProgramasSelectes() {
		return sedesProgramasSelectes;
	}

	public void setSedesProgramasSelectes(
			List<TblacaSedePrograma> sedesProgramasSelectes) {
		this.sedesProgramasSelectes = sedesProgramasSelectes;
	}

	public List<Integer> getSedesProgramasSelectesI() {
		return sedesProgramasSelectesI;
	}

	public void setSedesProgramasSelectesI(List<Integer> sedesProgramasSelectesI) {
		this.sedesProgramasSelectesI = sedesProgramasSelectesI;
	}

	public List<TblacaPrograma> getListaP() {
		return listaP;
	}

	public void setListaP(List<TblacaPrograma> listaP) {
		this.listaP = listaP;
	}

	public List<TblacaPrograma> getListaPs() {
		return listaPs;
	}

	public void setListaPs(List<TblacaPrograma> listaPs) {
		this.listaPs = listaPs;
	}

	public List<TblacaSedePrograma> getListaProgramasSelected() {
		return listaProgramasSelected;
	}

	public void setListaProgramasSelected(
			List<TblacaSedePrograma> listaProgramasSelected) {
		this.listaProgramasSelected = listaProgramasSelected;
	}

	public TblacaSedePrograma getSedeprog() {
		return sedeprog;
	}

	public void setSedeprog(TblacaSedePrograma sedeprog) {
		this.sedeprog = sedeprog;
	}

}
