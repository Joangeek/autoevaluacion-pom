package com.autoevaluacion;

import java.util.List;

import util.DAOException;
import autoevaluacion.TblautCaracteristica;
import autoevaluacion.TblautFactore;


public interface CaracteristicasDAO {

	public List<TblautCaracteristica> buscarTodos(boolean estado)
			throws DAOException;

	public void crear(TblautCaracteristica caracteristica)
			throws DAOException;

	public TblautCaracteristica editar(TblautCaracteristica selected)
			throws DAOException;

	public void eliminar(TblautCaracteristica selected)
			throws DAOException;

	public List<TblautCaracteristica> buscarTodos()
			throws DAOException;

	public List<TblautCaracteristica> buscarPorFactor(TblautFactore factor)
			throws DAOException;

}
