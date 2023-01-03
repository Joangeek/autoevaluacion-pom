package com.autoevaluacion;

import java.util.List;

import autoevaluacion.TblautVigencia;

import util.DAOException;

public interface VigenciasDAO {

	public void crear(TblautVigencia aspecto)
			throws DAOException;

	public TblautVigencia editar(TblautVigencia selected)
			throws DAOException;

	public void eliminar(TblautVigencia selected)
			throws DAOException;

	public List<TblautVigencia> buscarTodos()
			throws DAOException;

}
