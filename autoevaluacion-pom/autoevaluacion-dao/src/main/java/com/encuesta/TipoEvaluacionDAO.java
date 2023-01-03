package com.encuesta;

import java.util.Date;
import java.util.List;

import talentoHumano.TblthParticipante;
import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblestEstudiante;

import util.DAOException;

import comun.Tblempleadore;
import encuestas.TblencDirigidoa;
import encuestas.TblencEvaluacionInstitucion;
import encuestas.TblencModuloTipoEvaluacion;
import encuestas.TblencProgramacionEncuesta;
import encuestas.TblencTipoEvaluacion;

public interface TipoEvaluacionDAO {

	public List<TblencTipoEvaluacion> buscarTodos(boolean estado)
			throws DAOException;

	public void crear(TblencTipoEvaluacion tipoAspecto)
			throws DAOException;

	public void editar(TblencTipoEvaluacion selected)
			throws DAOException;

	public TblencTipoEvaluacion editarR(TblencTipoEvaluacion selected)
			throws DAOException;

	public Integer eliminar(TblencTipoEvaluacion selected)
			throws DAOException;

	public List<TblencTipoEvaluacion> buscarT()
			throws DAOException;

	public List<TblencDirigidoa> buscarDirigidoAs()
			throws DAOException;

	public List<TblencModuloTipoEvaluacion> buscarModulosAs()
			throws DAOException;

	public List<TblencDirigidoa> buscarTDirigidoA(boolean val)
			throws DAOException;

	public List<TblencModuloTipoEvaluacion> buscarTModulos(boolean val)
			throws DAOException;

	public List<TblencTipoEvaluacion> buscarID(Integer oid)
			throws DAOException;

	public TblencDirigidoa buscarDirigidoA(String descripcion)
			throws DAOException;

	public List<TblencProgramacionEncuesta> buscarEncuesta(Date fechaHoy,
			Integer dirigido) throws DAOException;

	public List<TblencTipoEvaluacion> buscarEstadoByModulo(boolean estado,
			int modulo) throws DAOException;

	public List<TblencEvaluacionInstitucion> buscarParticipantes(
			TblencTipoEvaluacion prog, Integer peri, TblacaSede sede)
			throws DAOException;

	public List<TblencEvaluacionInstitucion> buscarParticipantes(
			TblencTipoEvaluacion prog, Integer peri, TblacaSede sede,
			TblacaPrograma programa) throws DAOException;

	public List<TblencEvaluacionInstitucion> buscarEstudiantes(
			TblencTipoEvaluacion prog, Integer idPeriodo, TblacaSede sede,
			TblacaPrograma programa) throws DAOException;

	public Integer buscarEncuestaRealizada(TblencProgramacionEncuesta prog,
			TblthParticipante part) throws DAOException;

	public Integer buscarEncuestaRealizadaEstudiante(
			TblencProgramacionEncuesta prog, TblestEstudiante part,
			Integer sede, Integer programa) throws DAOException;

	public Integer EncuestaRealizadaEmpleador(TblencProgramacionEncuesta prog,
			Tblempleadore empleador) throws DAOException;

	public List<TblencEvaluacionInstitucion> buscarEmpleador(
			TblencTipoEvaluacion prog, Integer oid, TblacaSede sede,
			TblacaPrograma programa) throws DAOException;

}
