package com.gestion;

import gestion.Parametros;

import javax.ejb.Remote;

import commons.exceptions.AutoevaluacionBOException;


/**
 * @author EDUAR
 * 
 */
@Remote
public interface ParametrosBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws AutoevaluacionBOException
	 */
	public Parametros buscarCodigo(String codigo)
			throws AutoevaluacionBOException;


}
