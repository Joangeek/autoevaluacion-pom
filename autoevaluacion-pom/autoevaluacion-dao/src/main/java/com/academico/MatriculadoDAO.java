package com.academico;

import java.util.List;

import util.DAOException;
import academico.TblacaMatriculado;



public interface MatriculadoDAO {

	public void eliminar(TblacaMatriculado entity)
			throws DAOException;

	public void editar(TblacaMatriculado entity)
			throws DAOException;

	public void crear(TblacaMatriculado entity)
			throws DAOException;

	public List<TblacaMatriculado> buscarTodos()
			throws DAOException;

	List<TblacaMatriculado> buscarTodosLimite(Integer limit, Integer offSet)
			throws DAOException;

	public List<Integer> buscarAnios() throws DAOException;

	public boolean validarEnPeriodo(Integer idEp, Integer periodoOid) throws DAOException;

}
