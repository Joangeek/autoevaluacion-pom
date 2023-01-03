package com.academico;

import java.util.List;

import javax.ejb.Remote;

import academico.TblacaPeriodo;

import util.BOException;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface PeriodoBO {

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public TblacaPeriodo buscarEstado(boolean estado)
			throws BOException;

	public List<TblacaPeriodo> buscarTodos() throws BOException;

	/**
	 * Método que crea un nuevo registro de tipo aspecto
	 * 
	 * @param periodo
	 * @throws BOException
	 */
	public void crear(byte[] periodo) throws BOException;

	/**
	 * Método que edita un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void editar(byte[] periodo) throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminar(byte[] periodo) throws BOException;

	List<TblacaPeriodo> buscarTodosLimite(Integer limit, Integer offSet)
			throws BOException;

	/**
	 * Método busca los distintos anios que se referencian en la tabla periodo
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<Integer> buscarAnios() throws BOException;

}
