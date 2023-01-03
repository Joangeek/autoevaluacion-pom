package com.autoevaluacion;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;



import util.BOException;
import academico.TblacaSede;

import com.academico.SedeProgramaBO;
import comun.controlador.util.core.BaseControlador;

@ManagedBean(name = "controlador")
@ViewScoped
public class Controlador<T> extends BaseControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8434534413696267700L;

	/**
     * 
     */

	// EJB
	// @EJB
	// private CrudBO<T> crudBO;

	@EJB
	private transient SedeProgramaBO sedeBO;

	// PROPERTIES
	@ManagedProperty(value = "#{comun}")
	private transient ResourceBundle lanzarMensajes;

	// VARIABLES
	private List<TblacaSede> listaSedes;

	@PostConstruct
	public void init() throws BOException {
		try {
			LOGGER.info("INIT CONTROLADOR");
			cargarSedes();

			// lanzarMensajeInfo(sedeBO.conect("voy----"));
		} catch (Exception ex) {
			lanzarMensajeError(null, ex);
		}

	}

	public void lanzar() {
		try {
			// lanzarMensajeInfo(lanzarMensajes.getString("page.title"));
			lanzarMensajeInfo(obtenerResourceBundle(COMUN).getString("page.title"));
		} catch (Exception e) {
			// LOGGER.info("Error al lanzar: " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Override
	public void inicializar() {

	}

	private void cargarSedes() {
		try {
			listaSedes = sedeBO.buscarSedes();

			// listaSedes = (List<TblacaSede>) crudBO
			// .buscarTodos((Class<T>) TblacaSede.class);
			LOGGER.info(listaSedes.size());
		} catch (BOException e) {
			LOGGER.info("Error al lanzar: " + e.getMessage(), e);
		}

	}

	// SETTES AND GETTES
	public List<TblacaSede> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<TblacaSede> listaSedes) {
		this.listaSedes = listaSedes;
	}

	public ResourceBundle getLanzarMensajes() {
		return lanzarMensajes;
	}

	public void setLanzarMensajes(ResourceBundle lanzarMensajes) {
		this.lanzarMensajes = lanzarMensajes;
	}

}
