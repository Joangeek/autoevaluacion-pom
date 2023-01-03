package com.autoevaluacion;

import java.util.List;

import javax.ejb.Remote;
import autoevaluacion.TblautMecanismo;
import util.BOException;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface MecanismosBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblautMecanismo> buscarTodos() throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public List<TblautMecanismo> buscarTodos(boolean estado)
			throws BOException;

	

	/**
	 * Método que crea un nuevo registro de tipo aspecto
	 * 
	 * @param tipoAspecto
	 * @throws BOException
	 */
	public void crear(TblautMecanismo entity) throws BOException;

	/**
	 * Método que edita un aspecto y retorna la entidad actualizada
	 * 
	 * @param selected
	 * @throws BOException
	 * 
	 */
	public TblautMecanismo editar(byte[] entity)
			throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminar(TblautMecanismo entity)
			throws BOException;

	public void eliminar(byte[] entity) throws BOException;

	public void editarEstado(byte[] serialize) throws BOException;

}
