/**
 * 
 */
package com.autoevaluacion.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import talentoHumano.TblthParticipante;
import academico.TblacaSedePrograma;
import autoevaluacion.TblautFactorVigencia;
import autoevaluacion.TblautFactore;
import autoevaluacion.TblautFuente;
import autoevaluacion.TblautLecturaIndicadore;
import autoevaluacion.TblautLecturaIndicadoresOt;
import autoevaluacion.TblautMecanismo;
import autoevaluacion.TblautOtrasDependencia;

import com.autoevaluacion.LecturaIndicadoresDAO;
import util.DAOException;

/**
 * @author EDUAR
 * 
 */
@SuppressWarnings("unchecked")
public class LecturaIndicadoresDAOImpl implements LecturaIndicadoresDAO {
	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.autoevaluacion.LecturaIndicadoresDAO#buscarParticipanteOid(java.lang
	 * .String)
	 */
	@Override
	public TblthParticipante buscarParticipanteOid(String oidPaticipante)
			throws DAOException {

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT pa FROM ");
		jpql.append(TblthParticipante.class.getSimpleName());
		jpql.append(" pa ");
		jpql.append(" WHERE  pa.idparticipante =:oidParticipante ");

		Query q = em.createQuery(jpql.toString());
		q.setParameter("oidParticipante", Integer.parseInt(oidPaticipante));
		try {
			return (TblthParticipante) q.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.autoevaluacion.LecturaIndicadoresDAO#buscarMecanismosLiOd()
	 */
	@Override
	public List<TblautMecanismo> buscarMecanismosLiOd(Integer oidParticipante,
			Integer vigencia, Integer dependencia)
			throws DAOException {

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT DISTINCT(me) FROM ");
		jpql.append(TblautLecturaIndicadoresOt.class.getSimpleName());
		jpql.append(" lec ");
		jpql.append(" JOIN lec.tblautMecanismo me ");
		jpql.append(" JOIN lec.tblthParticipante pa ");
		jpql.append(" JOIN lec.tblautVigencia vig ");
		jpql.append(" JOIN lec.tblautOtrasDependencia dep ");
		jpql.append(" WHERE  pa.idparticipante =:oidParticipante ");
		jpql.append(" AND dep.oid=:dependencia ");
		jpql.append(" AND vig.oid=:vigencia ");
		List<TblautMecanismo> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("oidParticipante", oidParticipante);
		q.setParameter("vigencia", vigencia);
		q.setParameter("dependencia", dependencia);

		listaEntidad = (List<TblautMecanismo>) q.getResultList();
		return listaEntidad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.autoevaluacion.LecturaIndicadoresDAO#buscarMecanismosLi()
	 */
	@Override
	public List<TblautMecanismo> buscarMecanismosLi(Integer oidSede,
			Integer oidPrograma, Integer oidParticipante, Integer vigencia)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT DISTINCT(me) FROM ");
		jpql.append(TblautLecturaIndicadore.class.getSimpleName());
		jpql.append(" lec ");
		jpql.append(" JOIN lec.tblautMecanismo me ");
		jpql.append(" JOIN lec.tblacaSede se ");
		jpql.append(" JOIN lec.tblacaPrograma pr ");
		jpql.append(" JOIN lec.tblthParticipante pa ");
		jpql.append(" JOIN lec.tblautVigencia vig ");
		jpql.append(" WHERE  se.oid =:oidSede ");
		jpql.append(" AND pr.oid =:oidPrograma ");
		jpql.append(" AND pa.idparticipante =:oidParticipante ");
		jpql.append(" AND vig.oid=:vigencia ");
		List<TblautMecanismo> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());

		q.setParameter("oidSede", oidSede);
		q.setParameter("oidPrograma", oidPrograma);
		q.setParameter("oidParticipante", oidParticipante);
		q.setParameter("vigencia", vigencia);

		listaEntidad = (List<TblautMecanismo>) q.getResultList();
		return listaEntidad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.autoevaluacion.LecturaIndicadoresDAO#buscarDependencias(java.lang
	 * .Integer)
	 */
	@Override
	public List<TblautOtrasDependencia> buscarDependencias(
			Integer idparticipante) throws DAOException {

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT dep FROM ");
		jpql.append(TblautOtrasDependencia.class.getSimpleName());
		jpql.append(" dep ");
		jpql.append(" JOIN FETCH dep.tblthCargo car ");
		jpql.append(" LEFT JOIN FETCH dep.tblthParticipante pa ");
		jpql.append(" WHERE  pa.idparticipante =:oidParticipante ");
		jpql.append(" ORDER BY car.oid ASC ");

		List<TblautOtrasDependencia> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("oidParticipante", idparticipante);

		listaEntidad = (List<TblautOtrasDependencia>) q.getResultList();
		return listaEntidad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.autoevaluacion.LecturaIndicadoresDAO#buscarfactores(java.lang.Integer
	 * )
	 */
	@Override
	public List<TblautFactore> buscarfactores(Integer oidMecanismo,
			Integer oidSede, Integer oidPrograma, Integer oidParticipante)
			throws DAOException {

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT DISTINCT(fa) FROM ");
		jpql.append(TblautLecturaIndicadore.class.getSimpleName());
		jpql.append(" lec ");
		jpql.append(" JOIN lec.tblautFactore fa ");
		jpql.append(" JOIN lec.tblthParticipante pa ");
		jpql.append(" JOIN lec.tblautMecanismo me ");
		jpql.append(" JOIN lec.tblacaSede se ");
		jpql.append(" JOIN lec.tblacaPrograma pr ");
		jpql.append(" WHERE  me.oid =:oidMecanismo ");
		jpql.append(" AND  se.oid =:oidSede ");
		jpql.append(" AND  pr.oid =:oidPrograma ");
		jpql.append(" AND  pa.idparticipante =:oidParticipante ");
		jpql.append(" ORDER BY fa.orden ASC ");

		List<TblautFactore> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("oidMecanismo", oidMecanismo);
		q.setParameter("oidSede", oidSede);
		q.setParameter("oidPrograma", oidPrograma);
		q.setParameter("oidParticipante", oidParticipante);

		listaEntidad = (List<TblautFactore>) q.getResultList();
		return listaEntidad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.autoevaluacion.LecturaIndicadoresDAO#buscarfactores(java.lang.Integer
	 * )
	 */
	@Override
	public List<TblautFactore> buscarfactoresOd(Integer oidMecanismo,
			Integer oidParticipante, Integer oidDependencia)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT DISTINCT(fa) FROM ");
		jpql.append(TblautLecturaIndicadoresOt.class.getSimpleName());
		jpql.append(" lec ");
		jpql.append(" JOIN lec.tblautFactore fa ");
		jpql.append(" JOIN lec.tblthParticipante pa ");
		jpql.append(" JOIN lec.tblautMecanismo me ");
		jpql.append(" JOIN lec.tblautOtrasDependencia dep ");
		jpql.append(" WHERE  me.oid =:oidMecanismo ");
		jpql.append(" AND  dep.oid =:dependencia ");
		jpql.append(" AND  pa.idparticipante =:oidParticipante ");
		jpql.append(" ORDER BY fa.orden ASC ");

		List<TblautFactore> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("oidMecanismo", oidMecanismo);
		q.setParameter("oidParticipante", oidParticipante);
		q.setParameter("dependencia", oidDependencia);

		listaEntidad = (List<TblautFactore>) q.getResultList();
		return listaEntidad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.autoevaluacion.LecturaIndicadoresDAO#buscarIndicadoresLecOd(java.
	 * lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<TblautLecturaIndicadoresOt> buscarIndicadoresLecOd(
			Integer oidFactor, Integer oidMecanismo, Integer oidParticipante,
			Integer oidDependencia) throws DAOException {

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT lec FROM ");
		jpql.append(TblautLecturaIndicadoresOt.class.getSimpleName());
		jpql.append(" lec ");
		jpql.append(" JOIN  lec.tblautFactore fa ");
		jpql.append(" JOIN FETCH lec.tblautCaracteristica ca ");
		jpql.append(" JOIN  lec.tblautMecanismo me ");
		jpql.append(" JOIN  lec.tblthParticipante pa ");
		jpql.append(" JOIN lec.tblautOtrasDependencia dep ");
		jpql.append(" JOIN FETCH lec.TblautIndicadoresCaracteristica ind ");

		jpql.append(" WHERE  me.oid =:oidMecanismo ");
		jpql.append(" AND  fa.oid =:oidFactor ");
		jpql.append(" AND  dep.oid =:dependencia ");
		jpql.append(" AND  pa.idparticipante =:oidParticipante ");

		jpql.append(" ORDER BY  ind.orden ASC ");

		List<TblautLecturaIndicadoresOt> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("oidMecanismo", oidMecanismo);
		q.setParameter("oidFactor", oidFactor);
		q.setParameter("oidParticipante", oidParticipante);
		q.setParameter("dependencia", oidDependencia);

		listaEntidad = (List<TblautLecturaIndicadoresOt>) q.getResultList();
		return listaEntidad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.autoevaluacion.LecturaIndicadoresDAO#buscarIndicadoresLec(java.lang
	 * .Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public List<TblautLecturaIndicadore> buscarIndicadoresLec(
			Integer oidFactor, Integer oidSede, Integer oidPrograma,
			Integer oidMecanismo, Integer oidParticipante)
			throws DAOException {

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT lec FROM ");
		jpql.append(TblautLecturaIndicadore.class.getSimpleName());
		jpql.append(" lec ");
		jpql.append(" JOIN FETCH lec.tblautFactore fa ");
		jpql.append(" JOIN FETCH lec.tblautCaracteristica ca ");
		jpql.append(" LEFT JOIN FETCH lec.tblautMecanismo me ");
		jpql.append(" LEFT JOIN FETCH lec.tblacaSede se ");
		jpql.append(" LEFT JOIN FETCH lec.tblacaPrograma pr ");
		jpql.append(" LEFT JOIN FETCH lec.tblthParticipante pa ");
		jpql.append(" JOIN FETCH lec.TblautIndicadoresCaracteristica ind ");

		jpql.append(" WHERE  me.oid =:oidMecanismo ");
		jpql.append(" AND  fa.oid =:oidFactor ");
		jpql.append(" AND  se.oid =:oidSede ");
		jpql.append(" AND  pr.oid =:oidPrograma ");
		jpql.append(" AND  pa.idparticipante =:oidParticipante ");

		jpql.append(" ORDER BY ca.oid ASC, ind.orden ASC ");

		List<TblautLecturaIndicadore> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("oidMecanismo", oidMecanismo);
		q.setParameter("oidFactor", oidFactor);
		q.setParameter("oidSede", oidSede);
		q.setParameter("oidPrograma", oidPrograma);
		q.setParameter("oidParticipante", oidParticipante);

		listaEntidad = (List<TblautLecturaIndicadore>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public void editarLi(TblautLecturaIndicadore lectura)
			throws DAOException {
		em.merge(lectura);
	}

	@Override
	public void editarLiOd(TblautLecturaIndicadoresOt lectura)
			throws DAOException {
		em.merge(lectura);
	}

	@Override
	public List<Integer> buscarVigencias() throws DAOException {
		// academico.tblaut_factor_vigencia
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT DISTINCT(fa.id.vigencia) FROM ");
		jpql.append(TblautFactorVigencia.class.getSimpleName());
		jpql.append(" fa ");
		jpql.append(" ORDER BY fa.id.vigencia DESC ");

		List<Integer> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());

		listaEntidad = (List<Integer>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblacaSedePrograma> buscarSedesprogramasParticipante(
			Integer oidParticipante, Integer vigencia)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblacaSedePrograma.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblacaSede se ");
		jpql.append(" JOIN FETCH te.tblacaPrograma pr ");

		jpql.append(" WHERE  se.oid IN(SELECT DISTINCT(s.oid) FROM ");
		jpql.append(TblautLecturaIndicadore.class.getSimpleName());
		jpql.append(" le JOIN le.tblacaSede s JOIN le.tblthParticipante pa JOIN le.tblautVigencia vig ");
		jpql.append(" WHERE pa.idparticipante=:idparticipante AND vig.oid=:vigencia  )");

		jpql.append(" AND pr.oid IN(SELECT DISTINCT(p.oid) FROM ");
		jpql.append(TblautLecturaIndicadore.class.getSimpleName());
		jpql.append(" le JOIN le.tblacaPrograma p JOIN le.tblthParticipante pa  JOIN le.tblautVigencia vig ");
		jpql.append(" WHERE pa.idparticipante=:idparticipante AND vig.oid=:vigencia  )");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblacaSedePrograma> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("idparticipante", oidParticipante);
		q.setParameter("vigencia", vigencia);
		listaEntidad = (List<TblacaSedePrograma>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblautFuente> buscarFuentes() throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT fu FROM ");
		jpql.append(TblautFuente.class.getSimpleName());
		jpql.append(" fu ORDER BY fu.oid ASC ");

		List<TblautFuente> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		listaEntidad = (List<TblautFuente>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblautMecanismo> buscarMecanismos()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT me FROM ");
		jpql.append(TblautMecanismo.class.getSimpleName());
		jpql.append(" me ORDER BY me.oid ASC ");

		List<TblautMecanismo> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		listaEntidad = (List<TblautMecanismo>) q.getResultList();
		return listaEntidad;
	}
}
