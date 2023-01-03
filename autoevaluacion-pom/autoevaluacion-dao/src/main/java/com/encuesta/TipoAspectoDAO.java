package com.encuesta;

import java.util.List;
import java.util.Map;

import util.DAOException;

import encuestas.TblencAspecto;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencOpcione;
import encuestas.TblencTipoAspecto;
import encuestas.TblencTipoEvaluacion;
import encuestas.util.ReporteEncuestaResultados;
import encuestas.util.ResultadoEncuesta;

public interface TipoAspectoDAO {

	public List<TblencTipoAspecto> buscarTodos(boolean estado)
			throws DAOException;

	public List<TblencTipoAspecto> buscarTodos()
			throws DAOException;

	public void crear(TblencTipoAspecto tipoAspecto)
			throws DAOException;

	public void editar(TblencTipoAspecto selected)
			throws DAOException;

	public TblencTipoAspecto editarR(TblencTipoAspecto selected)
			throws DAOException;

	public void eliminar(TblencTipoAspecto selected)
			throws DAOException;

	public List<TblencAspecto> buscarPorEncuesta(TblencTipoEvaluacion entity)
			throws DAOException;

	public List<TblencOpcione> buscarOpcAspecto(TblencTipoAspecto entity)
			throws DAOException;

	public List<ResultadoEncuesta> verRespuestas(Map<TblencAspecto, String> mapa)
			throws DAOException;

	public List<TblencGrupoAspecto> gruposPorEncuesta(
			TblencTipoEvaluacion entity) throws DAOException;

	public List<TblencAspecto> buscarPorGrupo(TblencGrupoAspecto entity)
			throws DAOException;

	public List<ReporteEncuestaResultados> buscarOpcAspecto(
			TblencAspecto entity, Integer oidTipoEval, Integer oidPeriodo)
			throws DAOException;

	public List<ReporteEncuestaResultados> buscarOpcAspecto(
			TblencAspecto entity, Integer oidTipoEval, Integer oidPeriodo,
			Integer oidSede, Integer oidPrograma)
			throws DAOException;

}
