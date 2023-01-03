package com.autoevaluacion;

import java.util.List;

import autoevaluacion.TblautMecanismo;

import util.DAOException;

public interface MecanismosDAO {

	public List<TblautMecanismo> buscarTodos(boolean estado)
			throws DAOException;

	public void crear(TblautMecanismo aspecto)
			throws DAOException;

	public TblautMecanismo editar(TblautMecanismo selected)
			throws DAOException;

	public void eliminar(TblautMecanismo selected)
			throws DAOException;

	public List<TblautMecanismo> buscarTodos()
			throws DAOException;

}
