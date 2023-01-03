package com.autoevaluacion;

import java.util.List;

import autoevaluacion.TblautCaracteristica;
import autoevaluacion.TblautIndicadoresCaracteristica;

import util.DAOException;

public interface IndicadoresDAO {

	public List<TblautIndicadoresCaracteristica> buscarTodos(boolean estado)
			throws DAOException;

	public void crear(TblautIndicadoresCaracteristica indicador)
			throws DAOException;

	public TblautIndicadoresCaracteristica editar(
			TblautIndicadoresCaracteristica selected)
			throws DAOException;

	public void eliminar(TblautIndicadoresCaracteristica selected)
			throws DAOException;

	public List<TblautIndicadoresCaracteristica> buscarTodos()
			throws DAOException;

	public List<TblautIndicadoresCaracteristica> listado()
			throws DAOException;

	public List<TblautIndicadoresCaracteristica> buscarPorCaracteristica(
			TblautCaracteristica entityNew)
			throws DAOException;

}
