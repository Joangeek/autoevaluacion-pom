package com.talentoHumano;

import java.util.List;

import javax.ejb.Remote;

import talentoHumano.TblthParticipante;

import util.BOException;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface ParticipantesBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblthParticipante> buscarTodos() throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public List<TblthParticipante> buscarTodos(boolean estado)
			throws BOException;

	
}
