package com.encuestas.configurar;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import comun.controlador.util.core.BaseControlador;

@ManagedBean(name = "encSedeProgramaMB")
@ViewScoped
public class EncuestaSedeProgramaMB extends BaseControlador implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9070318381513290629L;

	// CONTROLADORES
	private ConfigurarMB configurarMB;

	@Override
	public void inicializar() {
		
	}

	
	
	// settes and gettes
	public ConfigurarMB getConfigurarMB() {
		return configurarMB;
	}

	public void setConfigurarMB(ConfigurarMB configurarMB) {
		this.configurarMB = configurarMB;
	}

}
