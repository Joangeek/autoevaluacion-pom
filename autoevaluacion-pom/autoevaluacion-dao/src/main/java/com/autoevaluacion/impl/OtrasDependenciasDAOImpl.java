package com.autoevaluacion.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import autoevaluacion.TblautOtrasDependencia;
import autoevaluacion.TblautOtrasDependencia;

import com.autoevaluacion.MecanismosDAO;
import com.autoevaluacion.OtrasDependenciasDAO;
import util.DAOException;

@SuppressWarnings("unused")
public class OtrasDependenciasDAOImpl implements OtrasDependenciasDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@SuppressWarnings({ "unchecked" })
	public List<TblautOtrasDependencia> buscarTodos(boolean estado)
			throws DAOException {
		Query q = null;
		q = em.createNamedQuery("TblautOtrasDependencia.findAllEstado")
				.setParameter("estado", estado);
		return q.getResultList();

	}

	public void crear(TblautOtrasDependencia grupoAspecto)
			throws DAOException {
		em.persist(grupoAspecto);
	}

	public TblautOtrasDependencia editar(TblautOtrasDependencia selected)
			throws DAOException {
		return em.merge(selected);
	}

	public void eliminar(TblautOtrasDependencia selected)
			throws DAOException {
		em.remove(em.merge(selected));
	}

	@SuppressWarnings("unchecked")
	public List<TblautOtrasDependencia> buscarTodos()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautOtrasDependencia.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblthCargo  car ");
		jpql.append(" JOIN FETCH te.tblthParticipante  par ");
		jpql.append(" ORDER BY te.vigencia DESC, par.nombre ASC ");

		List<TblautOtrasDependencia> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblautOtrasDependencia>) query.getResultList();
		return listaEntidad;
	}

}
