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

import autoevaluacion.TblautFactore;
import autoevaluacion.TblautFactore;
import autoevaluacion.TblautFactore;

import com.autoevaluacion.FactoresDAO;
import com.autoevaluacion.FuentesDAO;
import com.autoevaluacion.MecanismosDAO;

import util.DAOException;
import commons.util.UtilidadBean;

@SuppressWarnings("unused")
public class FactoresDAOImpl implements FactoresDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<TblautFactore> buscarTodos(boolean estado)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautFactore.class.getSimpleName());
		jpql.append(" te WHERE te.estado=:estado");
		jpql.append(" ORDER BY te.orden ");

		List<TblautFactore> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("estado", UtilidadBean.booleano(estado));
		listaEntidad = (List<TblautFactore>) query.getResultList();
		return listaEntidad;

	}

	public void crear(TblautFactore entity) throws DAOException {
		em.persist(entity);
	}

	public TblautFactore editar(TblautFactore selected)
			throws DAOException {
		return em.merge(selected);
	}

	public void eliminar(TblautFactore selected)
			throws DAOException {
		try {
			em.remove(em.merge(selected));
		} catch (ConstraintViolationException e) {
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

	public String eliminar2(TblautFactore selected)
			throws DAOException {
		try {
			em.remove(em.merge(selected));
			return null;
		} catch (ConstraintViolationException e) {

			return "er";
		}
	}

	@SuppressWarnings("unchecked")
	public List<TblautFactore> buscarTodos() throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautFactore.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" ORDER BY te.orden ASC ");

		List<TblautFactore> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblautFactore>) query.getResultList();
		return listaEntidad;
	}

}
