package com.talentoHumano.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import talentoHumano.TblthCargo;
import talentoHumano.TblthParticipante;
import util.DAOException;
import autoevaluacion.TblautMecanismo;

import com.autoevaluacion.MecanismosDAO;
import com.talentoHumano.CargosDAO;
import com.talentoHumano.ParticipantesDAO;
import commons.util.UtilidadBean;

@SuppressWarnings("unused")
public class CargosDAOImpl implements CargosDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<TblthCargo> buscarTodos(boolean estado)
			throws util.DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblthCargo.class.getSimpleName());
		jpql.append(" te WHERE te.estado=:estado");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblthCargo> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("estado", UtilidadBean.booleano(estado));
		listaEntidad = (List<TblthCargo>) query.getResultList();
		return listaEntidad;

	}

	public void crear(TblautMecanismo grupoAspecto)
			throws DAOException {
		em.persist(grupoAspecto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblthCargo> buscarTodos() throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblthParticipante.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblthCargo> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblthCargo>) query.getResultList();
		return listaEntidad;
	}

}
