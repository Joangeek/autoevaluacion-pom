/**
 * 
 */
package com.encuesta;

import java.util.Date;

import javax.ejb.Remote;

import talentoHumano.TblthParticipante;
import academico.TblestEstudiante;

import util.BOException;

import comun.Tblempleadore;
import docente.TbldocDocenteH;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface ProcesarEvaluacionBO {

	TblthParticipante buscarParticipanteOid(String oid)
			throws BOException;

	TblestEstudiante buscarEstudioanteOid(String oidAdmin)
			throws BOException;

	/**
	 * 
	 * @param mapa
	 * @param partici
	 *            = participante
	 * @param estud
	 *            = estudiante
	 * @param egre
	 *            = egresado
	 * @param tipoEvaluacion
	 * @param sedeprograma
	 *            = sede programa
	 * @param fechaFinalizacion
	 * @param user
	 * @return
	 * @throws BOException
	 */
	boolean registrar(byte[] mapa, byte[] partici, byte[] estud, byte[] egre,
			byte[] tipoEvaluacion, byte[] sede, byte[] programa,
			byte[] sedePrograma, Date fechaFinalizacion, String user)
			throws BOException;

	/**
	 * Metodo para registrar una encuesta de empleadores
	 * 
	 * @param mapa
	 * @param partici
	 *            = empleador
	 * 
	 * @param tipoEvaluacion
	 * 
	 * @param fechaFinalizacion
	 * @param user
	 * @param sede
	 * @param program
	 * @return
	 * @throws AutoevaluacionBOException
	 */
	/**
 */
	boolean registrar(byte[] map, byte[] empleador, byte[] encuesta,
			Date fechaFinalizacion, String user)
			throws BOException;

	Tblempleadore buscarEmpleadorOid(String oidAdmin)
			throws BOException;

	/**
	 * Método que consulta sobre docente H, la sede y el programa en elk año y
	 * periodo de acuerdo al participante
	 * 
	 * @param participante
	 * @param periodo
	 * @return
	 * @throws BOException
	 */
	TbldocDocenteH buscarDocente(byte[] participante, byte[] periodo)
			throws BOException;;

}
