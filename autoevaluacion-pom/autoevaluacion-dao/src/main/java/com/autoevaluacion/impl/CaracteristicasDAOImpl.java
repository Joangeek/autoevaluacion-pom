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

import util.DAOException;
import autoevaluacion.TblautFactore;
import autoevaluacion.TblautCaracteristica;
import autoevaluacion.TblautCaracteristica;

import com.autoevaluacion.CaracteristicasDAO;
import com.autoevaluacion.FuentesDAO;
import com.autoevaluacion.MecanismosDAO;
import commons.util.UtilidadBean;

@SuppressWarnings("unused")
public class CaracteristicasDAOImpl implements CaracteristicasDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<TblautCaracteristica> buscarTodos(boolean estado)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautCaracteristica.class.getSimpleName());
		jpql.append(" te WHERE estado=:estado");
		jpql.append(" ORDER BY te.orden ASC ");

		List<TblautCaracteristica> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("estado", UtilidadBean.booleano(estado));
		listaEntidad = (List<TblautCaracteristica>) query.getResultList();
		return listaEntidad;
	}

	public void crear(TblautCaracteristica entity)
			throws DAOException {
		em.persist(entity);
	}

	public TblautCaracteristica editar(TblautCaracteristica selected)
			throws DAOException {
		return em.merge(selected);
	}

	public void eliminar(TblautCaracteristica selected)
			throws DAOException {
		try {
			em.remove(em.merge(selected));

		} catch (ConstraintViolationException e) {
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}

	}

	public String eliminar2(TblautCaracteristica selected)
			throws DAOException {
		try {
			em.remove(em.merge(selected));
			return null;
		} catch (ConstraintViolationException e) {
			return "er";
		}

	}

	@SuppressWarnings("unchecked")
	public List<TblautCaracteristica> buscarTodos()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautCaracteristica.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" ORDER BY te.orden ASC ");

		List<TblautCaracteristica> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblautCaracteristica>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblautCaracteristica> buscarPorFactor(TblautFactore factor)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautCaracteristica.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN te.tblautFactore fa");
		jpql.append(" WHERE fa.oid=:factor");
		jpql.append(" ORDER BY te.orden ASC ");

		List<TblautCaracteristica> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("factor", factor.getOid());
		listaEntidad = (List<TblautCaracteristica>) query.getResultList();
		return listaEntidad;
	}

}
