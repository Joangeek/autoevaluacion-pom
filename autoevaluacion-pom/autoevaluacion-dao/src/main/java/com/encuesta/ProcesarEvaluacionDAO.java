/**
 * 
 */
package com.encuesta;

import java.util.Date;
import java.util.Map;

import talentoHumano.TblthParticipante;
import academico.TblacaPeriodo;
import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblestEstudiante;
import academico.TblestEstudiantePrograma;

import util.DAOException;

import comun.Tblempleadore;
import docente.TbldocDocenteH;
import encuestas.TblencAspecto;
import encuestas.TblencProgramacionEncuesta;

/**
 * @author EDUAR
 * 
 */
public interface ProcesarEvaluacionDAO {

	TblthParticipante buscarParticipanteOid(String oid)
			throws DAOException;

	TblestEstudiante buscarEstudioanteOid(String oid)
			throws DAOException;

	boolean registrar(Map<TblencAspecto, String> map,
			TblthParticipante participante, TblestEstudiante estudiante,
			TblestEstudiante egresado, TblencProgramacionEncuesta evaluacion,
			TblacaSede sede, TblacaPrograma programa,
			TblestEstudiantePrograma sedeprog, Date fechaFinalizacion,
			String user) throws DAOException;

	Tblempleadore buscarEmpleadorOid(String oid)
			throws DAOException;

	/**
	 * registrar encuesta empleadores
	 * 
	 * @param map
	 * @param empleador
	 * @param evaluacion
	 * @param fechaFinalizacion
	 * @param user
	 * @return
	 * @throws DAOException
	 */
	boolean registrar(Map<TblencAspecto, String> map, Tblempleadore empleador,
			TblencProgramacionEncuesta evaluacion, Date fechaFinalizacion,
			String user) throws DAOException;

	/**
	 * 
	 * @param parti
	 * @param per
	 * @return
	 */
	TbldocDocenteH buscarDocente(TblthParticipante participante,
			TblacaPeriodo periodo) throws DAOException;

}
