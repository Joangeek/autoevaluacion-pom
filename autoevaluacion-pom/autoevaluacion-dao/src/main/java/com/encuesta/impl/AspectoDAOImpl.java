package com.encuesta.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import util.DAOException;

import com.encuesta.AspectoDAO;
import com.encuesta.TipoAspectoDAO;
import com.encuesta.TipoEvaluacionDAO;

import encuestas.TblencDirigidoa;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencModuloTipoEvaluacion;
import encuestas.TblencAspecto;
import encuestas.TblencTipoAspecto;
import encuestas.TblencTipoEvaluacion;

@SuppressWarnings("unused")
public class AspectoDAOImpl implements AspectoDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<TblencAspecto> buscarTodos(boolean estado)
			throws DAOException {
		Query q = null;
		q = em.createNamedQuery("TblencAspecto.findAllEstado").setParameter(
				"estado", estado);
		return q.getResultList();

	}

	public void crear(TblencAspecto tipoAspecto)
			throws DAOException {
		em.persist(tipoAspecto);
	}

	public void editar(TblencAspecto selected)
			throws DAOException {
		em.merge(selected);
	}

	public void eliminar(TblencAspecto selected)
			throws DAOException {
		em.remove(em.merge(selected));
	}

	@Override
	public TblencAspecto editarR(TblencAspecto selected)
			throws DAOException {
		return em.merge(selected);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencAspecto> buscarT() throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblencAspecto.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblenc_dirigidoa d ");
		jpql.append(" JOIN FETCH te.tblenc_modulo_tipo_evaluacion m ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblencAspecto> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblencAspecto>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencAspecto> buscarPorGrupoAs(TblencGrupoAspecto selected)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblencAspecto.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblencGrupoAspecto d ");
		jpql.append(" JOIN FETCH te.tblencTipoAspecto ta ");
		jpql.append(" WHERE  te.tblencGrupoAspecto =:grupo ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblencAspecto> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("grupo", selected);
		listaEntidad = (List<TblencAspecto>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencTipoAspecto> buscarTipoAspectosFiltro(
			TblencGrupoAspecto entity) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT m FROM ");
		jpql.append(TblencTipoAspecto.class.getSimpleName());
		jpql.append(" m ");
		jpql.append(" WHERE  m IN ( ");
		jpql.append(" SELECT DISTINCT te.tblencTipoAspecto FROM ");
		jpql.append(TblencAspecto.class.getSimpleName());
		jpql.append(" te )");
		jpql.append(" ORDER BY m.oid DESC ");

		List<TblencTipoAspecto> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblencTipoAspecto>) query.getResultList();
		return listaEntidad;
	}
}
