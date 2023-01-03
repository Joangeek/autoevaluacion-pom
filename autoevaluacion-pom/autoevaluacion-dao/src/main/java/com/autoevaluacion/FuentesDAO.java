package com.autoevaluacion;

import java.util.List;

import autoevaluacion.TblautFuente;

import util.DAOException;

public interface FuentesDAO {

	public List<TblautFuente> buscarTodos(boolean estado)
			throws DAOException;

	public void crear(TblautFuente aspecto)
			throws DAOException;

	public TblautFuente editar(TblautFuente selected)
			throws DAOException;

	public void eliminar(TblautFuente selected)
			throws DAOException;

	public List<TblautFuente> buscarTodos()
			throws DAOException;

}
