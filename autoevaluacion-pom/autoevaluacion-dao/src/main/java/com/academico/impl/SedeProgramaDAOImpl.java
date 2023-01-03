package com.academico.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblacaSedePrograma;
import academico.TblestEstudiante;
import academico.TblestEstudiantePrograma;

import com.academico.SedeprogramaDAO;
import util.DAOException;
import commons.util.UtilidadBean;

@SuppressWarnings("unchecked")
public class SedeProgramaDAOImpl implements SedeprogramaDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@Override
	public List<TblacaSede> buscarSedes() throws DAOException {
		try {

			Query q = null;
			q = em.createNamedQuery("TblacaSede.findAll");
			return q.getResultList();

		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void eliminarSede(TblacaSede entity)
			throws DAOException {
		em.remove(em.merge(entity));

	}

	@Override
	public void crearSede(TblacaSede entity) throws DAOException {
		em.persist(entity);

	}

	@Override
	public void editarSede(TblacaSede entity) throws DAOException {
		em.merge(entity);

	}

	@Override
	public void eliminarPrograma(TblacaPrograma entity)
			throws DAOException {
		em.remove(em.merge(entity));

	}

	@Override
	public void crearPrograma(TblacaPrograma entity)
			throws DAOException {
		em.persist(entity);
	}

	@Override
	public void editarPrograma(TblacaPrograma entity)
			throws DAOException {
		em.merge(entity);

	}

	@Override
	public void eliminarSedePrograma(TblacaSedePrograma entity)
			throws DAOException {
		em.remove(em.merge(entity));

	}

	@Override
	public void crearSedePrograma(TblacaSedePrograma entity)
			throws DAOException {
		em.persist(entity);

	}

	@Override
	public void editarSedePrograma(TblacaSedePrograma entity)
			throws DAOException {
		em.merge(entity);

	}

	@Override
	public List<TblacaPrograma> buscarProgramas()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblacaPrograma.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" ORDER BY te.nombre, te.oid DESC ");

		List<TblacaPrograma> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblacaPrograma>) query.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblacaPrograma> buscarProgramasActivos(boolean estado)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblacaPrograma.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblacaPrograma> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());

		listaEntidad = (List<TblacaPrograma>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblacaSede> buscarSedesActivas(boolean estado)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblacaSede.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" WHERE te.estado=:estado ");
		jpql.append(" ORDER BY te.oid ASC ");

		List<TblacaSede> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("estado", UtilidadBean.booleano(estado));

		listaEntidad = (List<TblacaSede>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblacaSedePrograma> buscarPorSede(TblacaSede sede)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblacaSedePrograma.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblenc_dirigidoa d ");
		jpql.append(" JOIN FETCH te.tblenc_modulo_tipo_evaluacion m ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblacaSedePrograma> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("sede", sede);
		listaEntidad = (List<TblacaSedePrograma>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblacaSede> buscarSedesEstadoPorSp(boolean estado)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT se FROM ");
		jpql.append(TblacaSede.class.getSimpleName());
		jpql.append(" se ");
		jpql.append(" WHERE  se IN ( ");
		jpql.append(" SELECT DISTINCT te.tblacaSede FROM ");
		jpql.append(TblacaSedePrograma.class.getSimpleName());
		jpql.append(" te )");
		jpql.append(" AND se.estado=:estado ORDER BY se.oid ASC ");

		List<TblacaSede> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("estado", UtilidadBean.booleano(estado));
		listaEntidad = (List<TblacaSede>) query.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblacaSedePrograma> buscarProgramasEstadoSp(Integer entity,
			boolean estado) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT sp FROM ");
		jpql.append(TblacaSedePrograma.class.getSimpleName());
		jpql.append(" sp ");
		jpql.append(" JOIN FETCH sp.tblacaPrograma pr");
		jpql.append(" JOIN FETCH sp.tblacaSede se ");
		jpql.append(" WHERE  se.oid=:entity AND sp.estado=:estado ");

		List<TblacaSedePrograma> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("entity", entity);
		q.setParameter("estado", UtilidadBean.booleano(estado));
		listaEntidad = (List<TblacaSedePrograma>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblestEstudiantePrograma> buscarPorEstudiante(TblestEstudiante entity)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT sp FROM ");
		jpql.append(TblestEstudiantePrograma.class.getSimpleName());
		jpql.append(" sp ");
		jpql.append(" JOIN FETCH sp.tblacaPrograma pr");
		jpql.append(" JOIN FETCH sp.tblacaSede se ");
		jpql.append(" JOIN  sp.tblestEstudiante es ");
		jpql.append(" WHERE  es.oid=:oid ");

		List<TblestEstudiantePrograma> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("oid", entity.getOid());

		listaEntidad = (List<TblestEstudiantePrograma>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblestEstudiantePrograma> buscarPorEstudiante(
			TblestEstudiante entity, Integer estados)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT sp FROM ");
		jpql.append(TblestEstudiantePrograma.class.getSimpleName());
		jpql.append(" sp ");
		jpql.append(" JOIN FETCH sp.tblacaPrograma pr");
		jpql.append(" JOIN FETCH sp.tblacaSede se ");
		jpql.append(" JOIN  sp.tblestEstudiante es ");
		jpql.append(" WHERE  es.oid=:oid AND sp.estado IN(:estados) ");

		List<TblestEstudiantePrograma> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("oid", entity.getOid());
		q.setParameter("estados", estados);

		listaEntidad = (List<TblestEstudiantePrograma>) q.getResultList();
		return listaEntidad;
	}

}
