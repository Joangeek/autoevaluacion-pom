package com.encuesta.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import util.DAOException;
import com.encuesta.AspectoDAO;
import com.encuesta.GrupoAspectoDAO;
import com.encuesta.TipoAspectoDAO;
import com.encuesta.TipoEvaluacionDAO;

import encuestas.TblencDirigidoa;
import encuestas.TblencModuloTipoEvaluacion;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencTipoEvaluacion;

@SuppressWarnings("unused")
public class GrupoAspectoDAOImpl implements GrupoAspectoDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<TblencGrupoAspecto> buscarTodos(boolean estado)
			throws DAOException {
		Query q = null;
		q = em.createNamedQuery("TblencGrupoAspecto.findAllEstado")
				.setParameter("estado", estado);
		return q.getResultList();

	}

	public void crear(TblencGrupoAspecto grupoAspecto)
			throws DAOException {
		em.persist(grupoAspecto);
	}

	public TblencGrupoAspecto editar(TblencGrupoAspecto selected)
			throws DAOException {
		return em.merge(selected);
	}

	public void eliminar(TblencGrupoAspecto selected)
			throws DAOException {
		em.remove(em.merge(selected));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencGrupoAspecto> buscarT() throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblencGrupoAspecto.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblenc_dirigidoa d ");
		jpql.append(" JOIN FETCH te.tblenc_modulo_tipo_evaluacion m ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblencGrupoAspecto> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblencGrupoAspecto>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencGrupoAspecto> buscarPorTipoEval(
			TblencTipoEvaluacion tipoEval) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblencGrupoAspecto.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblencTipoEvaluacion d ");
		jpql.append(" WHERE  te.tblencTipoEvaluacion =:tipoEval ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblencGrupoAspecto> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("tipoEval", tipoEval);
		listaEntidad = (List<TblencGrupoAspecto>) query.getResultList();
		return listaEntidad;
	}
}
