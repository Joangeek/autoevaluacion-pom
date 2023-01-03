package com.academico.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import academico.TblacaMatriculado;

import com.academico.MatriculadoDAO;
import util.DAOException;

@SuppressWarnings("unchecked")
public class MatriculadoDAOImpl implements MatriculadoDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@Override
	public void eliminar(TblacaMatriculado entity)
			throws DAOException {
		em.remove(em.merge(entity));
	}

	@Override
	public void editar(TblacaMatriculado entity)
			throws DAOException {
		em.merge(entity);
	}

	@Override
	public void crear(TblacaMatriculado entity)
			throws DAOException {
		em.persist(entity);
	}

	@Override
	public List<TblacaMatriculado> buscarTodos()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblacaMatriculado.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblacaMatriculado> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		listaEntidad = (List<TblacaMatriculado>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblacaMatriculado> buscarTodosLimite(Integer limit,
			Integer offSet) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblacaMatriculado.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblacaMatriculado> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setFirstResult(offSet);
		q.setMaxResults(limit);

		listaEntidad = (List<TblacaMatriculado>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<Integer> buscarAnios() throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT p.anio FROM ");
		jpql.append(TblacaMatriculado.class.getSimpleName());
		jpql.append(" p ");
		jpql.append(" ORDER BY p.anio DESC ");

		Query q = em.createQuery(jpql.toString());

		return q.getResultList();
	}

	@Override
	public boolean validarEnPeriodo(Integer idEp, Integer periodoOid)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();

		jpql.append("SELECT COUNT(m) FROM ");
		jpql.append(TblacaMatriculado.class.getSimpleName());
		jpql.append(" m ");
		jpql.append(" JOIN m.tblestEstudiantePrograma sp ");
		jpql.append(" JOIN m.tblacaPeriodo pe ");
		jpql.append(" WHERE  sp.idEp=:idep AND pe.oid=:oid ");

		Query q = em.createQuery(jpql.toString());

		q.setParameter("idep", idEp);
		q.setParameter("oid", periodoOid);

		Long cantidad = (Long) q.getSingleResult();
		if (cantidad.intValue() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
