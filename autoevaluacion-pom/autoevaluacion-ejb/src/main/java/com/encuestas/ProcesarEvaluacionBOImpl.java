/**
 * 
 */
package com.encuestas;

import java.util.Date;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import talentoHumano.TblthParticipante;
import academico.TblacaPeriodo;
import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblestEstudiante;
import academico.TblestEstudiantePrograma;


import util.BOException;
import util.DAOException;

import com.encuesta.ProcesarEvaluacionBO;
import com.encuesta.ProcesarEvaluacionDAO;

import commons.util.UtilidadBean;
import comun.Tblempleadore;
import docente.TbldocDocenteH;
import encuestas.TblencAspecto;
import encuestas.TblencProgramacionEncuesta;

/**
 * @author EDUAR
 * 
 */
@Stateless(mappedName = "procesarEvaluacionBO")
public class ProcesarEvaluacionBOImpl implements ProcesarEvaluacionBO {
	@Inject
	private ProcesarEvaluacionDAO procesarEvaluacionDAO;

	@Override
	public TblthParticipante buscarParticipanteOid(String oid)
			throws BOException {
		try {
			return procesarEvaluacionDAO.buscarParticipanteOid(oid);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblestEstudiante buscarEstudioanteOid(String oid)
			throws BOException {
		try {
			return procesarEvaluacionDAO.buscarEstudioanteOid(oid);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public boolean registrar(byte[] mapa, byte[] partici, byte[] estud,
			byte[] egre, byte[] tipoEvaluacion, byte[] sed, byte[] prog,
			byte[] sedeprograma, Date fechaFinalizacion, String user)
			throws BOException {
		try {
			Map<TblencAspecto, String> map = UtilidadBean.deserialize(mapa);
			TblthParticipante participante = new TblthParticipante();
			TblestEstudiante estudiante = new TblestEstudiante();
			TblestEstudiante egresado = new TblestEstudiante();
			TblacaPrograma programa = new TblacaPrograma();
			TblacaSede sede = new TblacaSede();
			TblencProgramacionEncuesta evaluacion = new TblencProgramacionEncuesta();
			TblestEstudiantePrograma sedeprog = new TblestEstudiantePrograma();

			if (UtilidadBean.validaNulos(partici)) {
				participante = UtilidadBean.deserialize(partici);
			}

			if (UtilidadBean.validaNulos(estud)) {
				estudiante = UtilidadBean.deserialize(estud);
			}
			if (UtilidadBean.validaNulos(egre)) {
				egresado = UtilidadBean.deserialize(egre);
			}

			evaluacion = UtilidadBean.deserialize(tipoEvaluacion);

			if (UtilidadBean.validaNulos(sedeprograma)) {
				sedeprog = UtilidadBean.deserialize(sedeprograma);
			}
			if (UtilidadBean.validaNulos(sed)) {
				sede = UtilidadBean.deserialize(sed);
			}
			if (UtilidadBean.validaNulos(prog)) {
				programa = UtilidadBean.deserialize(prog);
			}
			return procesarEvaluacionDAO.registrar(map, participante,
					estudiante, egresado, evaluacion, sede, programa, sedeprog,
					fechaFinalizacion, user);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public boolean registrar(byte[] mapa, byte[] emple, byte[] encuesta,
			Date fechaFinalizacion, String user)
			throws BOException {
		try {
			Map<TblencAspecto, String> map = UtilidadBean.deserialize(mapa);
			Tblempleadore empleador = new Tblempleadore();
			TblencProgramacionEncuesta evaluacion = new TblencProgramacionEncuesta();

			evaluacion = UtilidadBean.deserialize(encuesta);
			empleador = UtilidadBean.deserialize(emple);

			return procesarEvaluacionDAO.registrar(map, empleador, evaluacion,
					fechaFinalizacion, user);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Tblempleadore buscarEmpleadorOid(String oid)
			throws BOException {
		try {
			return procesarEvaluacionDAO.buscarEmpleadorOid(oid);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TbldocDocenteH buscarDocente(byte[] participante, byte[] periodo)
			throws BOException {
		try {
			TblthParticipante parti = UtilidadBean.deserialize(participante);
			TblacaPeriodo per = UtilidadBean.deserialize(periodo);
			return procesarEvaluacionDAO.buscarDocente(parti, per);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

}
