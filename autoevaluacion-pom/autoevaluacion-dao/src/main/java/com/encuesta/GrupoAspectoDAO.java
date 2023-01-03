package com.encuesta;

import java.util.List;

import util.DAOException;

import encuestas.TblencGrupoAspecto;
import encuestas.TblencTipoEvaluacion;

public interface GrupoAspectoDAO {

	public List<TblencGrupoAspecto> buscarTodos(boolean estado)
			throws DAOException;

	public void crear(TblencGrupoAspecto aspecto)
			throws DAOException;

	public TblencGrupoAspecto editar(TblencGrupoAspecto selected)
			throws DAOException;

	public void eliminar(TblencGrupoAspecto selected)
			throws DAOException;

	public List<TblencGrupoAspecto> buscarT() throws DAOException;

	public List<TblencGrupoAspecto> buscarPorTipoEval(
			TblencTipoEvaluacion tipoEval) throws DAOException;
}
