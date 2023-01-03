/**
 * 
 */
package com.academico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblacaSedePrograma;

import util.BOException;
import commons.util.UtilidadBean;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;

/**
 * @author EDUAR
 * @param <T>
 * 
 */
@ManagedBean(name = "sedeProgramaMB")
@ViewScoped
public class SedeProgramaMB extends BaseControlador implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * EJB
	 */
	@EJB
	private SedeProgramaBO sedeProgramaBO;

	private TblacaSedeMB sedeMB;

	/*
	 * Entitys
	 */

	private TblacaSedePrograma sedePrograma;
	/*
	 * Varios
	 */
	private List<TblacaSedePrograma> listaSedeProgramas;
	private List<TblacaSedePrograma> listaSedeProgramasFilter;
	private List<TblacaPrograma> listaProgramas;
	private List<TblacaPrograma> listaProgramasFiter;
	private TblacaSedePrograma selected;
	private TblacaSedePrograma selectedClon;
	private TblacaSede sedeSelected;

	@PostConstruct
	public void listas() {

		try {
			lookup();
			selected = new TblacaSedePrograma();
			selectedClon = new TblacaSedePrograma();
			listaProgramas = new ArrayList<TblacaPrograma>();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que injecta los BO
	 */
	private void lookup() {
		try {
			sedeProgramaBO = LookupUtil
					.lookupRemoteStateless(SedeProgramaBO.class);
			// ...
		} catch (NamingException e) {
			// ...
		}
	}

	/**
	 * Metodo para buscar la lista de todos los datos de una entidad
	 */

	public void mostrarTodos() throws BOException {

		try {
			listaSedeProgramas = sedeProgramaBO.buscarPorSede(UtilidadBean
					.serialize(sedeSelected));
			listaProgramas = sedeProgramaBO.buscarProgramasActivos(true);

		} catch (Exception e) {
			LOGGER.info("Mostrar Todos Exception", e);

		}

	}

	public void editar() throws BOException {

		try {
			sedeProgramaBO.editarSedePrograma(UtilidadBean.serialize(selected));
		} catch (NullPointerException e) {
			//System.out.println("Editar ** NullPointerException Bean" + e);
			// e.printStackTrace();
		}

	}

	public void crear() throws BOException {
		try {
			sedeProgramaBO.crearSedePrograma(UtilidadBean
					.serialize(sedePrograma));
		} catch (Exception e) {
			LOGGER.info("crear() : ", e);
		}
	}

	public void eliminar() throws BOException {

		try {
			sedeProgramaBO.eliminarSedePrograma(UtilidadBean
					.serialize(selected));
		} catch (Exception e) {

		}
	}

	/*
	 * Sets y Gets
	 */

	public TblacaSedePrograma getSedePrograma() {
		return sedePrograma;
	}

	public void setSedePrograma(TblacaSedePrograma sedePrograma) {
		this.sedePrograma = sedePrograma;
	}

	public List<TblacaSedePrograma> getListaSedeProgramas() {
		return listaSedeProgramas;
	}

	public void setListaSedeProgramas(
			List<TblacaSedePrograma> listaSedeProgramas) {
		this.listaSedeProgramas = listaSedeProgramas;
	}

	public List<TblacaPrograma> getListaProgramas() {
		return listaProgramas;
	}

	public void setListaProgramas(List<TblacaPrograma> listaProgramas) {
		this.listaProgramas = listaProgramas;
	}

	public TblacaSedePrograma getSelected() {
		return selected;
	}

	public void setSelected(TblacaSedePrograma selected) {
		this.selected = selected;
	}

	public TblacaSedePrograma getSelectedClon() {
		return selectedClon;
	}

	public void setSelectedClon(TblacaSedePrograma selectedClon) {
		this.selectedClon = selectedClon;
	}

	public TblacaSede getSedeSelected() {
		return sedeSelected;
	}

	public void setSedeSelected(TblacaSede sedeSelected) {
		this.sedeSelected = sedeSelected;
	}

	@Override
	public void inicializar() {

	}

	public TblacaSedeMB getSedeMB() {
		return sedeMB;
	}

	public void setSedeMB(TblacaSedeMB sedeMB) {
		this.sedeMB = sedeMB;
	}

	public List<TblacaSedePrograma> getListaSedeProgramasFilter() {
		return listaSedeProgramasFilter;
	}

	public void setListaSedeProgramasFilter(
			List<TblacaSedePrograma> listaSedeProgramasFilter) {
		this.listaSedeProgramasFilter = listaSedeProgramasFilter;
	}

	public List<TblacaPrograma> getListaProgramasFiter() {
		return listaProgramasFiter;
	}

	public void setListaProgramasFiter(List<TblacaPrograma> listaProgramasFiter) {
		this.listaProgramasFiter = listaProgramasFiter;
	}

}
