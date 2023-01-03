package com.autoevaluacion;

import java.util.List;

import javax.ejb.Remote;

import autoevaluacion.TblautFactore;

import util.BOException;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface FactoresBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblautFactore> buscarTodos() throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public List<TblautFactore> buscarTodos(boolean estado)
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
	public TblautFactore editar(byte[] entity) throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminar(TblautFactore entity) throws BOException;

	public void eliminar(byte[] entity) throws BOException;

	public void editarEstado(byte[] serialize) throws BOException;

}
