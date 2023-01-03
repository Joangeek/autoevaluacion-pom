package com.talentoHumano.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import talentoHumano.TblthParticipante;
import autoevaluacion.TblautMecanismo;

import com.autoevaluacion.MecanismosDAO;
import util.DAOException;
import commons.util.UtilidadBean;
import com.talentoHumano.ParticipantesDAO;

@SuppressWarnings("unused")
public class ParticipantesDAOImpl implements ParticipantesDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<TblthParticipante> buscarTodos(boolean estado)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblthParticipante.class.getSimpleName());
		jpql.append(" te WHERE te.estado=:estado");
		jpql.append(" ORDER BY te.nombre ASC ");

		List<TblthParticipante> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("estado", UtilidadBean.booleano(estado));
		listaEntidad = (List<TblthParticipante>) query.getResultList();
		return listaEntidad;

	}

	public void crear(TblautMecanismo grupoAspecto)
			throws DAOException {
		em.persist(grupoAspecto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblthParticipante> buscarTodos()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblthParticipante.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" ORDER BY te.nombre DESC ");

		List<TblthParticipante> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblthParticipante>) query.getResultList();
		return listaEntidad;
	}

}
