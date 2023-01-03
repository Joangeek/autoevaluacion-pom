package com.autoevaluacion.vigencias;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import util.BOException;

import comun.controlador.util.core.BaseControlador;

@ManagedBean(name = "vigenciaMB")
@ViewScoped
public class VigenciasMB extends BaseControlador implements Serializable {

	private static final long serialVersionUID = -7118382839292659137L;

	// EJB

	// CONTROLADORES
	private ConfigurarMB configurarMB;

	// VARIABLES

	@Override
	public void inicializar() {

	}

	@PostConstruct
	public void init() throws BOException {
		try {

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
		/*
		 * try { // vigenciasBO = //
		 * LookupUtil.lookupRemoteStateless(VigenciasBO.class);
		 * 
		 * // ... } catch (NamingException e) { // ... }
		 */
	}

	// SETTES AND GETTES

	public ConfigurarMB getConfigurarMB() {
		return configurarMB;
	}

	public void setConfigurarMB(ConfigurarMB configurarMB) {
		this.configurarMB = configurarMB;
	}

}
