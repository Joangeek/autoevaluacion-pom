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

import academico.TblacaPrograma;

import util.BOException;
import commons.util.UtilidadBean;
import comun.controlador.util.core.BaseControlador;

/**
 * @author EDUAR
 * 
 * 
 */
@ManagedBean(name = "programaMB")
@ViewScoped
public class ProgramaMB extends BaseControlador implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * EJB
	 */
	@EJB
	private SedeProgramaBO sedeProgramaBO;

	/*
	 * Entitys
	 */

	private TblacaPrograma programa;
	/*
	 * Varios
	 */
	private List<TblacaPrograma> listaPrograma;
	private TblacaPrograma selected;

	@PostConstruct
	public void init() {
		programa = new TblacaPrograma();
		listaPrograma = new ArrayList<TblacaPrograma>();
		selected = new TblacaPrograma();
	}

	@Override
	public void inicializar() {

	}

	/**
	 * Metodo para buscar la lista de todos los datos de una entidad
	 */

	public List<TblacaPrograma> mostrarTodos() throws BOException {

		try {
			listaPrograma = sedeProgramaBO.buscarProgramas();

		} catch (NullPointerException e) {

		}

		return listaPrograma;
	}

	public void editar() throws BOException {

		try {
			sedeProgramaBO.editarPrograma(UtilidadBean.serialize(selected));
		} catch (NullPointerException e) {
			//System.out.println("Editar ** NullPointerException Bean" + e);
			// e.printStackTrace();
		}

	}

	public void crear() throws BOException {
		try {
			sedeProgramaBO.crearPrograma(UtilidadBean.serialize(programa));
		} catch (Exception e) {

		}

	}

	public void eliminar() throws BOException {

		try {
			sedeProgramaBO.eliminarPrograma(UtilidadBean.serialize(selected));
		} catch (Exception e) {

		}
	}

	/*
	 * Sets y Gets
	 */

	public TblacaPrograma getPrograma() {
		return programa;
	}

	public void setPrograma(TblacaPrograma programa) {
		this.programa = programa;
	}

	public List<TblacaPrograma> getListaPrograma() {
		return listaPrograma;
	}

	public void setListaPrograma(List<TblacaPrograma> listaPrograma) {
		this.listaPrograma = listaPrograma;
	}

	public TblacaPrograma getSelected() {
		return selected;
	}

	public void setSelected(TblacaPrograma selected) {
		this.selected = selected;
	}

}
