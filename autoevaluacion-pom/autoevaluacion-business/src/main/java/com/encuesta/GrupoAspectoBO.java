package com.encuesta;

import java.util.List;

import javax.ejb.Remote;

import util.BOException;

import encuestas.TblencGrupoAspecto;
import encuestas.TblencTipoEvaluacion;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface GrupoAspectoBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblencGrupoAspecto> buscarT() throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public List<TblencGrupoAspecto> buscarTodos(boolean estado)
			throws BOException;

	/**
	 * Método que crea un nuevo registro de tipo aspecto
	 * 
	 * @param tipoAspecto
	 * @throws BOException
	 */
	public void crear(TblencGrupoAspecto tipoEvaluacion)
			throws BOException;

	/**
	 * Método que edita un aspecto y retorna la entidad actualizada
	 * 
	 * @param selected
	 * @throws BOException
	 * 
	 */
	public TblencGrupoAspecto editar(byte[] bs)
			throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminar(TblencGrupoAspecto selected)
			throws BOException;

	public void eliminar(byte[] serialize) throws BOException;

	public List<TblencGrupoAspecto> buscarPorTipoEval(
			TblencTipoEvaluacion tipoEval) throws BOException;

}
