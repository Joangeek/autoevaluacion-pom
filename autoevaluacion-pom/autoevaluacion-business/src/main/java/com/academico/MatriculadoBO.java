package com.academico;

import java.util.List;

import javax.ejb.Remote;

import academico.TblacaMatriculado;

import util.BOException;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface MatriculadoBO {

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public TblacaMatriculado buscarEstado(boolean estado)
			throws BOException;

	public List<TblacaMatriculado> buscarTodos()
			throws BOException;

	/**
	 * Método que crea un nuevo registro de tipo aspecto
	 * 
	 * @param matriculado
	 * @throws BOException
	 */
	public void crear(byte[] matriculado) throws BOException;

	/**
	 * Método que edita un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void editar(byte[] matriculado) throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminar(byte[] matriculado) throws BOException;

	List<TblacaMatriculado> buscarTodosLimite(Integer limit, Integer offSet)
			throws BOException;

	public boolean validarEnPeriodo(Integer idEp, Integer oid)
			throws BOException;

}
