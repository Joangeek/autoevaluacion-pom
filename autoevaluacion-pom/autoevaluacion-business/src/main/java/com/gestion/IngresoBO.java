package com.gestion;

import javax.ejb.Remote;

import commons.exceptions.AutoevaluacionBOException;

import auditoria.Tblingreso;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface IngresoBO {
	/**
	 * Método que de registro
	 * 
	 * @return
	 * @throws AutoevaluacionBOException
	 */
	public void ingreso(Tblingreso ingreso) throws AutoevaluacionBOException;

}
