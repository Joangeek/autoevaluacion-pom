package com.autoevaluacion.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import autoevaluacion.TblautVigencia;

import com.autoevaluacion.MecanismosDAO;
import com.autoevaluacion.VigenciasDAO;
import util.DAOException;

@SuppressWarnings("unused")
public class VigenciasDAOImpl implements VigenciasDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	public void crear(TblautVigencia grupoAspecto)
			throws DAOException {
		em.persist(grupoAspecto);
	}

	public TblautVigencia editar(TblautVigencia selected)
			throws DAOException {
		return em.merge(selected);
	}

	public void eliminar(TblautVigencia selected)
			throws DAOException {
		em.remove(em.merge(selected));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblautVigencia> buscarTodos()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautVigencia.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblautVigencia> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblautVigencia>) query.getResultList();
		return listaEntidad;
	}

}
