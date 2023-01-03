package com.autoevaluacion;

import java.util.List;

import autoevaluacion.TblautOtrasDependencia;

import util.DAOException;

public interface OtrasDependenciasDAO {

	public List<TblautOtrasDependencia> buscarTodos(boolean estado)
			throws DAOException;

	public void crear(TblautOtrasDependencia aspecto)
			throws DAOException;

	public TblautOtrasDependencia editar(TblautOtrasDependencia selected)
			throws DAOException;

	public void eliminar(TblautOtrasDependencia selected)
			throws DAOException;

	public List<TblautOtrasDependencia> buscarTodos()
			throws DAOException;

}
