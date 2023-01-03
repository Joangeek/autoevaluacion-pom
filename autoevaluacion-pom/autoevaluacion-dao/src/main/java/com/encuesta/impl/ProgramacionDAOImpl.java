package com.encuesta.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import academico.TblacaPeriodo;
import academico.TblacaPrograma;
import academico.TblacaSede;

import util.DAOException;
import com.encuesta.ProgramacionDAO;

import encuestas.TblencProgramacionEncSedeProg;
import encuestas.TblencProgramacionEncuesta;
import encuestas.TblencTipoEvaluacion;

@SuppressWarnings("unchecked")
public class ProgramacionDAOImpl implements ProgramacionDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@Override
	public void eliminar(TblencProgramacionEncuesta entity)
			throws DAOException {
		em.remove(em.merge(entity));
	}

	@Override
	public void eliminarPsp(TblencProgramacionEncSedeProg entity)
			throws DAOException {
		em.remove(em.merge(entity));
	}

	@Override
	public TblencProgramacionEncuesta editar(TblencProgramacionEncuesta entity)
			throws DAOException {

		return em.merge(entity);
	}

	@Override
	public void crear(TblencProgramacionEncuesta entity)
			throws DAOException {
		em.persist(entity);
	}

	@Override
	public TblencProgramacionEncSedeProg editarPsp(
			TblencProgramacionEncSedeProg entity)
			throws DAOException {

		return em.merge(entity);
	}

	@Override
	public void crearPsp(TblencProgramacionEncSedeProg entity)
			throws DAOException {
		em.persist(entity);
	}

	@Override
	public List<TblacaPeriodo> buscarPeriodos(TblencTipoEvaluacion entity)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT d FROM ");
		jpql.append(TblacaPeriodo.class.getSimpleName());
		jpql.append(" d ");
		jpql.append(" WHERE  d IN ( ");
		jpql.append(" SELECT DISTINCT te.tblacaPeriodo FROM ");
		jpql.append(TblencProgramacionEncuesta.class.getSimpleName());
		jpql.append(" te  JOIN te.tblencTipoEvaluacion val ");
		jpql.append(" WHERE  te.tblencTipoEvaluacion=:evaluacion  )");
		jpql.append(" ORDER BY d.oid DESC ");

		List<TblacaPeriodo> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("evaluacion", entity);
		listaEntidad = (List<TblacaPeriodo>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblencProgramacionEncuesta> buscarPorEvaluacion(
			TblencTipoEvaluacion entity) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT d FROM ");
		jpql.append(TblencProgramacionEncuesta.class.getSimpleName());
		jpql.append(" d ");
		jpql.append(" JOIN FETCH d.tblencTipoEvaluacion val ");
		jpql.append(" JOIN FETCH d.tblacaPeriodo p");
		jpql.append(" WHERE  d.tblencTipoEvaluacion=:evaluacion  ");
		jpql.append(" ORDER BY p.oid DESC, d.fechaCreacion DESC ");

		List<TblencProgramacionEncuesta> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("evaluacion", entity);
		listaEntidad = (List<TblencProgramacionEncuesta>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public Integer contarSedesProgramasConfig(TblencProgramacionEncuesta oid)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT COUNT (d.oid) FROM ");
		jpql.append(TblencProgramacionEncSedeProg.class.getSimpleName());
		jpql.append(" d JOIN  d.tblencProgramacionEncuesta p");
		jpql.append(" WHERE  p=:oid  ");

		try {
			Query q = em.createQuery(jpql.toString());
			q.setParameter("oid", oid);
			Long val = (Long) q.getSingleResult();
			return val.intValue();
		} catch (NoResultException e) {
			return 0;
		}

	}

	@Override
	public List<TblacaSede> buscarSedesPorSpIN(TblencProgramacionEncuesta entity)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT se FROM ");
		jpql.append(TblencProgramacionEncSedeProg.class.getSimpleName());
		jpql.append(" d ");
		jpql.append(" JOIN d.tblencProgramacionEncuesta pen ");
		jpql.append(" JOIN d.tblacaSedePrograma sp ");
		jpql.append(" JOIN sp.tblacaPrograma pr");
		jpql.append(" JOIN sp.tblacaSede se ");
		jpql.append(" WHERE pen=:entity AND  se.estado=1 ");

		List<TblacaSede> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("entity", entity);
		listaEntidad = (List<TblacaSede>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblacaPrograma> buscarProgramasPorSpIN(
			TblencProgramacionEncuesta entity)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT pr FROM ");
		jpql.append(TblencProgramacionEncSedeProg.class.getSimpleName());
		jpql.append(" d ");
		jpql.append(" JOIN d.tblencProgramacionEncuesta pen ");
		jpql.append(" JOIN d.tblacaSedePrograma sp ");
		jpql.append(" JOIN sp.tblacaPrograma pr");
		jpql.append(" JOIN sp.tblacaSede se ");
		jpql.append(" WHERE  pen=:entity AND sp.estado=1 ");

		List<TblacaPrograma> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("entity", entity);
		listaEntidad = (List<TblacaPrograma>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblencProgramacionEncSedeProg> sedesProgramasConfig(
			TblencProgramacionEncuesta entity)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT d FROM ");
		jpql.append(TblencProgramacionEncSedeProg.class.getSimpleName());
		jpql.append(" d ");
		jpql.append(" JOIN FETCH d.tblencProgramacionEncuesta pen ");
		jpql.append(" JOIN FETCH d.tblacaSedePrograma sp ");
		jpql.append(" JOIN FETCH sp.tblacaPrograma pr");
		jpql.append(" JOIN FETCH sp.tblacaSede se ");
		jpql.append(" WHERE  pen=:entity  ");

		List<TblencProgramacionEncSedeProg> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("entity", entity);
		listaEntidad = (List<TblencProgramacionEncSedeProg>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblacaPrograma> buscarTodosP()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT d FROM ");
		jpql.append(TblacaPrograma.class.getSimpleName());
		jpql.append(" d  ");

		try {
			Query q = em.createQuery(jpql.toString());
			return  q.getResultList();

		} catch (NoResultException e) {
			return null;
		}
	}

}
