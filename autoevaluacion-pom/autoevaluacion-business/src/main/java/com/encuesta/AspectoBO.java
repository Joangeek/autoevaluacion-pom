package com.encuesta;

import java.util.List;

import javax.ejb.Remote;

import util.BOException;
import encuestas.TblencAspecto;
import encuestas.TblencTipoAspecto;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface AspectoBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblencAspecto> buscarT() throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public List<TblencAspecto> buscarTodos(boolean estado)
			throws BOException;

	/**
	 * Método que crea un nuevo registro de tipo aspecto
	 * 
	 * @param tipoAspecto
	 * @throws BOException
	 */
	public void crear(byte[] bs)
			throws BOException;

	/**
	 * Método que edita un aspecto
	 * 
	 * @param bs
	 * @throws BOException
	 */
	public void editar(byte[] bs) throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminar(byte[] selected)
			throws BOException;

	/**
	 * Método que edita un aspecto y retorna la entidad actualizada
	 * 
	 * @param bs
	 * @throws BOException
	 * 
	 */
	public TblencAspecto editarR(byte[] bs) throws BOException;

	public List<TblencAspecto> buscarPorGrupoAs(byte[] bs)
			throws BOException;

	public List<TblencTipoAspecto> buscarTipoAspectosFiltro(byte[] serialize)
			throws BOException;

}
