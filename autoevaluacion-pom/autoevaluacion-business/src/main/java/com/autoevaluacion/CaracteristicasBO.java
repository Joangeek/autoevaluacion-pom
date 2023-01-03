package com.autoevaluacion;

import java.util.List;

import javax.ejb.Remote;




import util.BOException;
import autoevaluacion.TblautCaracteristica;


/**
 * @author EDUAR
 * 
 */
@Remote
public interface CaracteristicasBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblautCaracteristica> buscarTodos()
			throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public List<TblautCaracteristica> buscarTodos(boolean estado)
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
	public TblautCaracteristica editar(byte[] entity)
			throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminar(TblautCaracteristica entity)
			throws BOException;

	public void eliminar(byte[] entity) throws BOException;

	public void editarEstado(byte[] serialize) throws BOException;

	public List<TblautCaracteristica> buscarPorFactor(
			byte[] factorSelected) throws BOException;

}
