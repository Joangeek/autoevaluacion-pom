package com.talentoHumano;

import java.util.List;

import javax.ejb.Remote;

import talentoHumano.TblthCargo;

import util.BOException;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface CargosBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblthCargo> buscarTodos() throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public List<TblthCargo> buscarTodos(boolean estado)
			throws BOException;

	
}
