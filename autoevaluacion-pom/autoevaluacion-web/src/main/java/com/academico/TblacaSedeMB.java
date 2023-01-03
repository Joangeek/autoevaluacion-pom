package com.academico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;

import util.BOException;
import commons.util.UtilidadBean;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;
import academico.TblacaSede;

/**
 * @author EDUAR
 * @param <T>
 * 
 */
@ManagedBean(name = "sedeMB")
@SessionScoped
public class TblacaSedeMB extends BaseControlador implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * EJB
	 */
	@EJB
	private SedeProgramaBO sedeProgramaBO;

	@ManagedProperty(value = "#{sedeProgramaMB}")
	private SedeProgramaMB sedeProgramaMB;

	/*
	 * Entitys
	 */

	private TblacaSede sede;
	/*
	 * Varios
	 */
	private List<TblacaSede> listaSedes;
	private List<TblacaSede> listaSedesFilter;
	private TblacaSede selected;

	@PostConstruct
	public void init() throws BOException {
		lookup();
		sede = new TblacaSede();
		selected = new TblacaSede();
		listaSedes = new ArrayList<TblacaSede>();
		mostrarTodos();
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

	private void mostrarTodos() throws BOException {
		try {
			listaSedes = sedeProgramaBO.buscarSedes();

		} catch (Exception e) {
			LOGGER.info("Error mostrar todos : ", e);
		}

	}

	public void editar() throws BOException {

		try {
			sedeProgramaBO.editarSede(UtilidadBean.serialize(selected));
		} catch (NullPointerException e) {
			System.out.println("Editar ** NullPointerException Bean" + e);
			// e.printStackTrace();
		}

	}

	public void crear() throws BOException {
		try {
			sedeProgramaBO.crearSede(UtilidadBean.serialize(sede));
		} catch (Exception e) {
			LOGGER.info("crear Exception :", e);
		}

	}

	public void eliminar() throws BOException {

		try {
			sedeProgramaBO.eliminarSede(UtilidadBean.serialize(selected));
		} catch (Exception e) {
			LOGGER.info("eliminar Exception :", e);
		}
	}

	@Override
	public void inicializar() {

	}

	/*
	 * Sets y Gets
	 */

	public TblacaSede getSede() {
		return sede;
	}

	public void setSede(TblacaSede sede) {
		this.sede = sede;
	}

	public TblacaSede getSelected() {
		return selected;
	}

	public void setSelected(TblacaSede selected) {
		this.selected = selected;
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

	public SedeProgramaMB getSedeProgramaMB() {
		return sedeProgramaMB;
	}

	public void setSedeProgramaMB(SedeProgramaMB sedeProgramaMB) {
		this.sedeProgramaMB = sedeProgramaMB;
	}

}
