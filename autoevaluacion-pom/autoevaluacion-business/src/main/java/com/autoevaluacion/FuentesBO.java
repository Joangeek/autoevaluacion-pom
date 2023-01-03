package com.autoevaluacion;

import java.util.List;

import javax.ejb.Remote;

import util.BOException;
import autoevaluacion.TblautFuente;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface FuentesBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return @
	 */
	public List<TblautFuente> buscarTodos() throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return @
	 */
	public List<TblautFuente> buscarTodos(boolean estado) throws BOException;

	/**
	 * Método que crea un nuevo registro de tipo aspecto
	 * 
	 * @param tipoAspecto
	 *            @
	 */
	public void crear(byte[] entity) throws BOException;

	/**
	 * Método que edita un aspecto y retorna la entidad actualizada
	 * 
	 * @param selected
	 *            @
	 * 
	 */
	public TblautFuente editar(byte[] entity) throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 *            @
	 */
	public void eliminar(TblautFuente entity) throws BOException;

	public void eliminar(byte[] entity) throws BOException;

	public void editarEstado(byte[] serialize) throws BOException;

}
