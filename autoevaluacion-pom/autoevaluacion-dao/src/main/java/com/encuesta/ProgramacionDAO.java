package com.encuesta;

import java.util.List;

import academico.TblacaPeriodo;
import academico.TblacaPrograma;
import academico.TblacaSede;

import util.DAOException;

import encuestas.TblencProgramacionEncSedeProg;
import encuestas.TblencProgramacionEncuesta;
import encuestas.TblencTipoEvaluacion;

public interface ProgramacionDAO {

	void eliminar(TblencProgramacionEncuesta entity)
			throws DAOException;

	TblencProgramacionEncuesta editar(TblencProgramacionEncuesta entity)
			throws DAOException;

	void crear(TblencProgramacionEncuesta entity)
			throws DAOException;

	List<TblacaPeriodo> buscarPeriodos(TblencTipoEvaluacion entity)
			throws DAOException;

	List<TblencProgramacionEncuesta> buscarPorEvaluacion(
			TblencTipoEvaluacion entity) throws DAOException;

	Integer contarSedesProgramasConfig(TblencProgramacionEncuesta oid)
			throws DAOException;

	List<TblacaSede> buscarSedesPorSpIN(TblencProgramacionEncuesta entity)
			throws DAOException;

	List<TblacaPrograma> buscarProgramasPorSpIN(
			TblencProgramacionEncuesta entity)
			throws DAOException;

	List<TblencProgramacionEncSedeProg> sedesProgramasConfig(
			TblencProgramacionEncuesta entity)
			throws DAOException;

	TblencProgramacionEncSedeProg editarPsp(TblencProgramacionEncSedeProg entity)
			throws DAOException;

	void crearPsp(TblencProgramacionEncSedeProg entity)
			throws DAOException;

	void eliminarPsp(TblencProgramacionEncSedeProg entity)
			throws DAOException;

	List<TblacaPrograma> buscarTodosP()throws DAOException;

}
