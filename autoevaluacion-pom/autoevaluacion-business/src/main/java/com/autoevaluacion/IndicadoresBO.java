package com.autoevaluacion;

import java.util.List;

import javax.ejb.Remote;

import util.BOException;
import autoevaluacion.TblautIndicadoresCaracteristica;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface IndicadoresBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblautIndicadoresCaracteristica> buscarTodos()
			throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public List<TblautIndicadoresCaracteristica> buscarTodos(boolean estado)
			throws BOException;

	/**
	 * Método que crea un nuevo registro de tipo aspecto
	 * 
	 * @param tipoAspecto
	 * @throws BOException
	 */
	public void crear(byte[] entity) throws BOException;

	/**
	 * Método que edita un aspecto y retorna la entidad actualizada
	 * 
	 * @param selected
	 * @throws BOException
	 * 
	 */
	public TblautIndicadoresCaracteristica editar(byte[] entity)
			throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminar(TblautIndicadoresCaracteristica entity)
			throws BOException;

	public void eliminar(byte[] entity) throws BOException;

	public void editarEstado(byte[] serialize) throws BOException;

	public List<TblautIndicadoresCaracteristica> listado()
			throws BOException;

	public List<TblautIndicadoresCaracteristica> buscarPorCaracteristica(
			byte[] entity) throws BOException;

}
