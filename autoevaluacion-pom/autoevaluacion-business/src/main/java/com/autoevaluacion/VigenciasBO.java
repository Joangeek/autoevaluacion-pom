package com.autoevaluacion;

import java.util.List;

import javax.ejb.Remote;

import util.BOException;
import autoevaluacion.TblautVigencia;


/**
 * @author EDUAR
 * 
 */
@Remote
public interface VigenciasBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblautVigencia> buscarTodos() throws BOException;

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
	public TblautVigencia editar(byte[] entity)
			throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminar(byte[] entity)
			throws BOException;

}
