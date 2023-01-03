package com.encuesta;

import java.util.List;



import util.DAOException;
import encuestas.TblencAspecto;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencTipoAspecto;

public interface AspectoDAO {

	public List<TblencAspecto> buscarTodos(boolean estado)
			throws DAOException;

	public void crear(TblencAspecto aspecto) throws DAOException;

	public void editar(TblencAspecto selected)
			throws DAOException;

	public TblencAspecto editarR(TblencAspecto selected)
			throws DAOException;

	public void eliminar(TblencAspecto selected)
			throws DAOException;

	public List<TblencAspecto> buscarT() throws DAOException;

	public List<TblencAspecto> buscarPorGrupoAs(
			TblencGrupoAspecto grupoAspectoSelected)
			throws DAOException;

	public List<TblencTipoAspecto> buscarTipoAspectosFiltro(
			TblencGrupoAspecto entity) throws DAOException;
}
