package com.autoevaluacion.impl;

import java.util.List;

import javassist.expr.Instanceof;

import javax.ejb.EJBTransactionRequiredException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.hibernate.exception.ConstraintViolationException;

import autoevaluacion.TblautCaracteristica;
import autoevaluacion.TblautFactore;
import autoevaluacion.TblautIndicadoresCaracteristica;
import autoevaluacion.TblautIndicadoresCaracteristica;

import com.autoevaluacion.FuentesDAO;
import com.autoevaluacion.IndicadoresDAO;
import com.autoevaluacion.MecanismosDAO;
import util.DAOException;
import commons.util.UtilidadBean;

@SuppressWarnings("unused")
public class IndicadoresDAOImpl implements IndicadoresDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<TblautIndicadoresCaracteristica> buscarTodos(boolean estado)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautIndicadoresCaracteristica.class.getSimpleName());
		jpql.append(" te WHERE te.estado=:estado ");
		jpql.append(" ORDER BY te.orden ASC ");

		List<TblautIndicadoresCaracteristica> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("estado", UtilidadBean.booleano(estado));
		listaEntidad = (List<TblautIndicadoresCaracteristica>) query
				.getResultList();
		return listaEntidad;
	}

	public void crear(TblautIndicadoresCaracteristica entity)
			throws DAOException {
		em.persist(entity);
	}

	public TblautIndicadoresCaracteristica editar(
			TblautIndicadoresCaracteristica selected)
			throws DAOException {
		return em.merge(selected);
	}

	public void eliminar(TblautIndicadoresCaracteristica selected)
			throws DAOException {
		try {
			em.remove(em.merge(selected));

		} catch (ConstraintViolationException e) {
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}

	}

	public String eliminar2(TblautIndicadoresCaracteristica selected)
			throws DAOException {
		try {
			em.remove(em.merge(selected));
			return null;
		} catch (ConstraintViolationException e) {
			return "er";
		}

	}

	@SuppressWarnings("unchecked")
	public List<TblautIndicadoresCaracteristica> buscarTodos()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautIndicadoresCaracteristica.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" ORDER BY te.orden ASC ");

		List<TblautIndicadoresCaracteristica> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblautIndicadoresCaracteristica>) query
				.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblautIndicadoresCaracteristica> listado() {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautIndicadoresCaracteristica.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" LEFT JOIN FETCH te.tblautCaracteristica car ");
		jpql.append(" LEFT JOIN FETCH car.tblautFactore fac ");
		jpql.append(" ORDER BY fac.orden, car.orden, te.orden ");

		List<TblautIndicadoresCaracteristica> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblautIndicadoresCaracteristica>) query
				.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblautIndicadoresCaracteristica> buscarPorCaracteristica(
			TblautCaracteristica caracteristica) {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautIndicadoresCaracteristica.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN te.tblautCaracteristica ca");
		jpql.append(" WHERE ca.oid=:caracteristica");
		jpql.append(" ORDER BY te.orden ASC ");

		List<TblautIndicadoresCaracteristica> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("caracteristica", caracteristica.getOid());
		listaEntidad = (List<TblautIndicadoresCaracteristica>) query
				.getResultList();
		return listaEntidad;
	}

}
