/**
 * 
 */
package com.encuesta.impl;

import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import talentoHumano.TblthParticipante;
import academico.TblacaPeriodo;
import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblestEstudiante;
import academico.TblestEstudiantePrograma;

import util.DAOException;
import commons.util.UtilidadBean;
import com.encuesta.ProcesarEvaluacionDAO;

import comun.Tblempleadore;
import docente.TbldocDocenteH;
import encuestas.TblencAspecto;
import encuestas.TblencAspectoEvaluacion;
import encuestas.TblencEvaluacionInstitucion;
import encuestas.TblencOpcione;
import encuestas.TblencProgramacionEncuesta;

/**
 * @author EDUAR
 * 
 */
public class ProcesarEvaluacionDAOImpl implements ProcesarEvaluacionDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	static Logger LOOGER = Logger.getLogger(ProcesarEvaluacionDAOImpl.class
			.getSimpleName());

	/**
	 * Registra la realización de la encuesta
	 * 
	 * * @return
	 * 
	 * @param programa
	 * @param sede
	 * 
	 * @param user
	 */
	private TblencEvaluacionInstitucion regitrarEvaluacionInstitucion(
			TblthParticipante participante, TblestEstudiante estudiante,
			TblacaSede sede, TblacaPrograma programa,
			TblestEstudiantePrograma sedeprograma, TblestEstudiante egresado,
			Object empleador, TblencProgramacionEncuesta evaluacion,
			Date fechaFinalizacion, String user) {

		TblencEvaluacionInstitucion entity = new TblencEvaluacionInstitucion();

		if (UtilidadBean.validaNulos(participante.getIdparticipante())) {

			entity.setTblthParticipante(participante);
			if (UtilidadBean.validaNulos(participante.getTblacaSede())) {
				entity.setTblacaSede(participante.getTblacaSede());
			}
			if (UtilidadBean.validaNulos(sede.getOid())
					&& UtilidadBean.validaNulos(programa.getOid())) {
				entity.setTblacaPrograma(programa);
				entity.setTblacaSede(sede);
			}
		} else if (UtilidadBean.validaNulos(estudiante.getOid())) {

			entity.setTblestEstudiante(estudiante);
			entity.setTblacaPrograma(sedeprograma.getTblacaPrograma());
			entity.setTblacaSede(sedeprograma.getTblacaSede());

		} else if (UtilidadBean.validaNulos(egresado.getOid())) {

			entity.setEgresado(egresado);
			entity.setTblacaPrograma(sedeprograma.getTblacaPrograma());
			entity.setTblacaSede(sedeprograma.getTblacaSede());
		}

		entity.setTblencTipoEvaluacion(evaluacion.getTblencTipoEvaluacion());
		entity.setTblacaPeriodo(evaluacion.getTblacaPeriodo());
		entity.setVigencia(evaluacion.getTblacaPeriodo().getAnio());
		entity.setFecha(fechaFinalizacion);
		entity.setUsuario(user);

		try {
			em.persist(entity);
			em.flush();
		} catch (TransactionRequiredException e) {
			LOOGER.info("TransactionRequiredException--->");
			return null;
		} catch (PersistenceException e) {
			LOOGER.info("PersistenceException--->");
			return null;
		} catch (Exception e) {
			LOOGER.info("Exception---+>");
			e.printStackTrace();
			return null;
		}

		return entity;
	}

	/**
	 * Método que regiosra todas lsa opciones seleccinadas de una encuesta
	 * 
	 * @param opcion
	 * @param tblencAspecto
	 * @param evalIns
	 * @param user
	 * 
	 * @return
	 */
	private void regitrarAspectosEvaluacion(
			TblencEvaluacionInstitucion evalIns, TblencAspecto aspecto,
			TblencOpcione opcion, String user)
			throws DAOException {

		TblencAspectoEvaluacion entity = new TblencAspectoEvaluacion();

		entity.setTblencEvaluacionInstitucion(evalIns);
		entity.setTblencAspecto(aspecto);
		entity.setTblencOpcione(opcion);
		entity.setUsuario(user);

		em.persist(entity);
	}

	@Override
	public TblthParticipante buscarParticipanteOid(String oid)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT p FROM ");
		jpql.append(TblthParticipante.class.getSimpleName());
		jpql.append(" p ");
		jpql.append(" WHERE  p.idparticipante=:oid  ");

		try {
			Query q = em.createQuery(jpql.toString());
			q.setParameter("oid", Integer.parseInt(oid));
			return (TblthParticipante) q.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public TblestEstudiante buscarEstudioanteOid(String oid)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT p FROM ");
		jpql.append(TblestEstudiante.class.getSimpleName());
		jpql.append(" p ");
		jpql.append(" WHERE  p.oid=:oid  ");

		try {
			Query q = em.createQuery(jpql.toString());
			q.setParameter("oid", Integer.parseInt(oid));
			return (TblestEstudiante) q.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public boolean registrar(Map<TblencAspecto, String> mapa,
			TblthParticipante participante, TblestEstudiante estudiante,
			TblestEstudiante egresado, TblencProgramacionEncuesta evaluacion,
			TblacaSede sede, TblacaPrograma programa,
			TblestEstudiantePrograma sedeprograma, Date fechaFinalizacion,
			String user) throws DAOException {
		Object empleador = null;
		TblencEvaluacionInstitucion evalIns = new TblencEvaluacionInstitucion();
		evalIns = regitrarEvaluacionInstitucion(participante, estudiante, sede,
				programa, sedeprograma, egresado, empleador, evaluacion,
				fechaFinalizacion, user);

		if (UtilidadBean.validaNulos(evalIns)) {

			for (Map.Entry<TblencAspecto, String> val : mapa.entrySet()) {
				StringBuilder jpql = new StringBuilder();
				jpql.append("SELECT opc FROM ");
				jpql.append(TblencOpcione.class.getSimpleName());
				jpql.append(" opc ");
				jpql.append(" WHERE  opc.oid=:oid ");

				try {
					Query query = em.createQuery(jpql.toString());
					query.setParameter("oid", Integer.parseInt(val.getValue()));

					TblencOpcione opcion = (TblencOpcione) query
							.getSingleResult();

					regitrarAspectosEvaluacion(evalIns, val.getKey(), opcion,
							user);

				} catch (NoResultException e) {
					LOOGER.info("---------> NoResultException e");
					return false;
				} catch (Exception e) {

					LOOGER.info("---------> Exception");
					return false;
				}

			}
		} else {
			LOOGER.info("---------> false +++++");
			return false;
		}

		LOOGER.info("---------> EXITO +++++");
		return true;

	}

	@Override
	public Tblempleadore buscarEmpleadorOid(String oid)
			throws DAOException {

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT p FROM ");
		jpql.append(Tblempleadore.class.getSimpleName());
		jpql.append(" p ");
		jpql.append(" JOIN FETCH p.tblacaSede se ");
		jpql.append(" JOIN FETCH p.tblacaPrograma pr ");
		jpql.append(" WHERE  p.oid=:oid  ");

		try {

			Query q = em.createQuery(jpql.toString());
			q.setParameter("oid", Integer.parseInt(oid));
			return (Tblempleadore) q.getSingleResult();

		} catch (NoResultException e) {
			LOOGER.info("---------> NoResultException");
			return null;
		} catch (NonUniqueResultException e) {
			LOOGER.info("---------> NonUniqueResultException");
			return null;
		} catch (Exception e) {
			LOOGER.info("---------> Exception");
			return null;
		}

	}

	@Override
	public boolean registrar(Map<TblencAspecto, String> mapa,
			Tblempleadore empleador, TblencProgramacionEncuesta evaluacion,
			Date fechaFinalizacion, String user)
			throws DAOException {

		TblencEvaluacionInstitucion evalIns = new TblencEvaluacionInstitucion();
		evalIns = regitrarEvaluacionInstitucion(empleador, evaluacion,
				fechaFinalizacion, user);

		if (UtilidadBean.validaNulos(evalIns)) {

			for (Map.Entry<TblencAspecto, String> val : mapa.entrySet()) {
				StringBuilder jpql = new StringBuilder();
				jpql.append("SELECT opc FROM ");
				jpql.append(TblencOpcione.class.getSimpleName());
				jpql.append(" opc ");
				jpql.append(" WHERE  opc.oid=:oid ");

				try {
					Query query = em.createQuery(jpql.toString());
					query.setParameter("oid", Integer.parseInt(val.getValue()));

					TblencOpcione opcion = (TblencOpcione) query
							.getSingleResult();

					regitrarAspectosEvaluacion(evalIns, val.getKey(), opcion,
							user);

				} catch (NoResultException e) {
					LOOGER.info("---------> NoResultException e");
					return false;
				} catch (Exception e) {

					LOOGER.info("---------> Exception");
					return false;
				}

			}
		} else {
			LOOGER.info("---------> false +++++");
			return false;
		}

		LOOGER.info("---------> EXITO +++++");
		return true;

	}

	/**
	 * Registra la realización de la encuesta
	 * 
	 * * @return
	 * 
	 * @param user
	 * @param programa
	 * @param sede
	 */
	private TblencEvaluacionInstitucion regitrarEvaluacionInstitucion(
			Tblempleadore empleador, TblencProgramacionEncuesta evaluacion,
			Date fechaFinalizacion, String user) {

		TblencEvaluacionInstitucion entity = new TblencEvaluacionInstitucion();

		entity.setTblempleadore(empleador);
		entity.setTblencTipoEvaluacion(evaluacion.getTblencTipoEvaluacion());
		entity.setTblacaPeriodo(evaluacion.getTblacaPeriodo());
		entity.setVigencia(evaluacion.getTblacaPeriodo().getAnio());
		entity.setFecha(fechaFinalizacion);
		entity.setUsuario(user);
		entity.setTblacaSede(empleador.getTblacaSede());
		entity.setTblacaPrograma(empleador.getTblacaPrograma());

		try {
			em.persist(entity);
			em.flush();
		} catch (TransactionRequiredException e) {
			LOOGER.info("TransactionRequiredException--->");
			return null;
		} catch (PersistenceException e) {
			LOOGER.info("PersistenceException--->");
			return null;
		} catch (Exception e) {
			LOOGER.info("Exception---+>");
			e.printStackTrace();
			return null;
		}

		return entity;
	}

	@Override
	public TbldocDocenteH buscarDocente(TblthParticipante participante,
			TblacaPeriodo periodo) throws DAOException {

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT doc FROM ");
		jpql.append(TbldocDocenteH.class.getSimpleName());
		jpql.append(" doc ");
		jpql.append(" JOIN FETCH doc.tblacaSede se ");
		jpql.append(" JOIN FETCH doc.tblacaPrograma pr ");
		jpql.append(" WHERE  doc.codigoUnico=:codigo_unico  ");
		jpql.append(" AND    doc.annio=:annio");
		jpql.append(" AND    doc.semestre=:semestre");

		try {

			Query q = em.createQuery(jpql.toString());
			q.setParameter("codigo_unico", participante.getCodigoUnico());
			q.setParameter("annio", periodo.getAnio());
			q.setParameter("semestre", periodo.getSemestre());

			return (TbldocDocenteH) q.getSingleResult();

		} catch (NoResultException e) {
			// e.printStackTrace();
			LOOGER.info("---------> NoResultException buscarDocente");
			return null;
		} catch (NonUniqueResultException e) {
			// e.printStackTrace();
			LOOGER.info("---------> NonUniqueResultException buscarDocente");
			return null;
		} catch (Exception e) {
			LOOGER.info("---------> Exception buscarDocente");
			// e.printStackTrace();
			return null;
		}

	}
}
