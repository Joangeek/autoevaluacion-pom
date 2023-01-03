package com.autoevaluacion;

import java.util.List;

import autoevaluacion.TblautFactore;

import util.DAOException;

public interface FactoresDAO {

	public List<TblautFactore> buscarTodos(boolean estado)
			throws DAOException;

	public void crear(TblautFactore factor)
			throws DAOException;

	public TblautFactore editar(TblautFactore selected)
			throws DAOException;

	public void eliminar(TblautFactore selected)
			throws DAOException;

	public List<TblautFactore> buscarTodos()
			throws DAOException;


}
