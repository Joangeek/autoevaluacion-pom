package com.autoevaluacion.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import autoevaluacion.TblautMecanismo;

import com.autoevaluacion.MecanismosDAO;
import util.DAOException;

@SuppressWarnings("unused")
public class MecanismosDAOImpl implements MecanismosDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<TblautMecanismo> buscarTodos(boolean estado)
			throws DAOException {
		Query q = null;
		q = em.createNamedQuery("TblautMecanismo.findAllEstado").setParameter(
				"estado", estado);
		return q.getResultList();

	}

	public void crear(TblautMecanismo grupoAspecto)
			throws DAOException {
		em.persist(grupoAspecto);
	}

	public TblautMecanismo editar(TblautMecanismo selected)
			throws DAOException {
		return em.merge(selected);
	}

	public void eliminar(TblautMecanismo selected)
			throws DAOException {
		em.remove(em.merge(selected));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblautMecanismo> buscarTodos()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautMecanismo.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblautMecanismo> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblautMecanismo>) query.getResultList();
		return listaEntidad;
	}

}
