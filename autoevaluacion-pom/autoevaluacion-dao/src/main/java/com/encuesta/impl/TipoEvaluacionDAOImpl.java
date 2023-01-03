package com.encuesta.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

import talentoHumano.TblthParticipante;
import academico.TblacaPeriodo;
import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblestEstudiante;

import util.DAOException;
import commons.util.UtilidadBean;
import com.encuesta.TipoAspectoDAO;
import com.encuesta.TipoEvaluacionDAO;

import comun.Tblempleadore;
import encuestas.TblencDirigidoa;
import encuestas.TblencEvaluacionInstitucion;
import encuestas.TblencModuloTipoEvaluacion;
import encuestas.TblencProgramacionEncuesta;
import encuestas.TblencTipoEvaluacion;

@SuppressWarnings("unused")
public class TipoEvaluacionDAOImpl implements TipoEvaluacionDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<TblencTipoEvaluacion> buscarTodos(boolean estado)
			throws DAOException {
		Query q = null;
		q = em.createNamedQuery("TblencTipoEvaluacion.findAllEstado")
				.setParameter("estado", estado);
		return q.getResultList();

	}

	public void crear(TblencTipoEvaluacion tipoAspecto)
			throws DAOException {
		em.persist(tipoAspecto);
	}

	public void editar(TblencTipoEvaluacion selected)
			throws DAOException {
		em.merge(selected);
	}

	public Integer eliminar(TblencTipoEvaluacion selected)
			throws DAOException {
		try {
			em.remove(em.merge(selected));
			return 1;
		} catch (Exception e) {
			throw new DAOException("HIJOS", e);
		}

	}

	@Override
	public TblencTipoEvaluacion editarR(TblencTipoEvaluacion selected)
			throws DAOException {
		return em.merge(selected);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencTipoEvaluacion> buscarT()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblencTipoEvaluacion.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblenc_dirigidoa d ");
		jpql.append(" JOIN FETCH te.tblenc_modulo_tipo_evaluacion m ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblencTipoEvaluacion> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblencTipoEvaluacion>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencDirigidoa> buscarDirigidoAs()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT d FROM ");
		jpql.append(TblencDirigidoa.class.getSimpleName());
		jpql.append(" d ");
		jpql.append(" WHERE  d IN ( ");
		jpql.append(" SELECT DISTINCT te.tblenc_dirigidoa FROM ");
		jpql.append(TblencTipoEvaluacion.class.getSimpleName());
		jpql.append(" te )");
		jpql.append(" ORDER BY d.oid DESC ");

		List<TblencDirigidoa> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblencDirigidoa>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencModuloTipoEvaluacion> buscarModulosAs()
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT m FROM ");
		jpql.append(TblencModuloTipoEvaluacion.class.getSimpleName());
		jpql.append(" m ");
		jpql.append(" WHERE  m IN ( ");
		jpql.append(" SELECT DISTINCT te.tblenc_modulo_tipo_evaluacion FROM ");
		jpql.append(TblencTipoEvaluacion.class.getSimpleName());
		jpql.append(" te )");
		jpql.append(" ORDER BY m.oid DESC ");

		List<TblencModuloTipoEvaluacion> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblencModuloTipoEvaluacion>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencDirigidoa> buscarTDirigidoA(boolean val)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT E FROM ");
		jpql.append(TblencDirigidoa.class.getSimpleName());
		jpql.append(" E ORDER BY E.oid DESC");

		List<TblencDirigidoa> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblencDirigidoa>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencModuloTipoEvaluacion> buscarTModulos(boolean val)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT E FROM ");
		jpql.append(TblencModuloTipoEvaluacion.class.getSimpleName());
		jpql.append(" E ORDER BY E.oid DESC");

		List<TblencModuloTipoEvaluacion> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		listaEntidad = (List<TblencModuloTipoEvaluacion>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	public List<TblencTipoEvaluacion> buscarID(Integer oid)
			throws DAOException {
		Query q = null;
		q = em.createNamedQuery("TblencTipoEvaluacion.findById").setParameter(
				"oid", oid);
		return q.getResultList();

	}

	@Override
	public TblencDirigidoa buscarDirigidoA(String descripcion)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT E FROM ");
		jpql.append(TblencDirigidoa.class.getSimpleName());
		jpql.append(" E WHERE E.descripcion=:descripcion");

		Query query = em.createQuery(jpql.toString());
		query.setParameter("descripcion", descripcion);
		try {
			return (TblencDirigidoa) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencProgramacionEncuesta> buscarEncuesta(Date fechaHoy,
			Integer dirigido) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT p FROM ");
		jpql.append(TblencProgramacionEncuesta.class.getSimpleName());
		jpql.append(" p ");
		jpql.append(" JOIN FETCH p.tblencTipoEvaluacion te ");
		jpql.append(" JOIN FETCH  p.tblacaPeriodo per ");
		jpql.append(" JOIN FETCH te.tblenc_dirigidoa d ");
		jpql.append(" WHERE :fecha BETWEEN p.desde and p.hasta ");
		jpql.append(" AND d.oid=:oid ");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblencProgramacionEncuesta> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("fecha", fechaHoy);
		query.setParameter("oid", dirigido);

		listaEntidad = (List<TblencProgramacionEncuesta>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencTipoEvaluacion> buscarEstadoByModulo(boolean estado,
			int modulo) throws DAOException {

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblencTipoEvaluacion.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN te.tblenc_modulo_tipo_evaluacion mod ");
		jpql.append(" JOIN FETCH te.tblenc_dirigidoa d ");
		jpql.append(" WHERE mod.oid =:oid");
		jpql.append(" AND te.estado=:estado");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblencTipoEvaluacion> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("estado", UtilidadBean.booleano(estado));
		query.setParameter("oid", modulo);

		listaEntidad = (List<TblencTipoEvaluacion>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencEvaluacionInstitucion> buscarParticipantes(
			TblencTipoEvaluacion prog, Integer periodo, TblacaSede sede)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT ei FROM ");
		jpql.append(TblencEvaluacionInstitucion.class.getSimpleName());
		jpql.append(" ei ");
		jpql.append(" JOIN FETCH ei.tblthParticipante p ");
		jpql.append(" JOIN FETCH ei.tblencTipoEvaluacion te ");
		jpql.append(" JOIN FETCH ei.tblacaPeriodo per ");
		jpql.append(" JOIN FETCH ei.tblacaSede sed ");
		jpql.append(" WHERE te.oid =:oid");
		jpql.append(" AND per.oid=:periodo");

		List<TblencEvaluacionInstitucion> listaEntidad = null;
		if (UtilidadBean.validaNulos(sede)) {
			jpql.append(" AND sed.oid=:sede");
		}
		jpql.append(" ORDER BY sed.oid,p.nombre ");

		Query query = em.createQuery(jpql.toString());
		query.setParameter("oid", prog.getOid());

		query.setParameter("periodo", periodo);
		if (UtilidadBean.validaNulos(sede)) {
			query.setParameter("sede", sede.getOid());
		}

		listaEntidad = (List<TblencEvaluacionInstitucion>) query
				.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencEvaluacionInstitucion> buscarParticipantes(
			TblencTipoEvaluacion prog, Integer periodo, TblacaSede sede,
			TblacaPrograma programa) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT ei FROM ");
		jpql.append(TblencEvaluacionInstitucion.class.getSimpleName());
		jpql.append(" ei ");
		jpql.append(" JOIN FETCH ei.tblthParticipante p ");
		jpql.append(" JOIN FETCH ei.tblencTipoEvaluacion te ");
		jpql.append(" JOIN FETCH ei.tblacaPeriodo per ");
		jpql.append(" LEFT OUTER JOIN  ei.tblacaSede sed ");
		jpql.append(" LEFT OUTER JOIN  ei.tblacaPrograma pr ");
		jpql.append(" WHERE te.oid =:oid");
		jpql.append(" AND per.oid=:periodo");

		List<TblencEvaluacionInstitucion> listaEntidad = null;
		if (UtilidadBean.validaNulos(sede)) {
			jpql.append(" AND sed.oid=:sede");
		}
		if (UtilidadBean.validaNulos(programa)) {
			jpql.append(" AND pr.oid=:programa");
		}
		jpql.append(" ORDER BY ei.tblacaSede,ei.tblacaPrograma,p.nombre ");

		Query query = em.createQuery(jpql.toString());
		query.setParameter("oid", prog.getOid());
		query.setParameter("periodo", periodo);

		if (UtilidadBean.validaNulos(sede)) {
			query.setParameter("sede", sede.getOid());
		}
		if (UtilidadBean.validaNulos(programa)) {
			query.setParameter("programa", programa.getOid());
		}
		listaEntidad = (List<TblencEvaluacionInstitucion>) query
				.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencEvaluacionInstitucion> buscarEstudiantes(
			TblencTipoEvaluacion prog, Integer periodo, TblacaSede sede,
			TblacaPrograma programa) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT ei FROM ");
		jpql.append(TblencEvaluacionInstitucion.class.getSimpleName());
		jpql.append(" ei ");
		jpql.append(" JOIN FETCH ei.tblestEstudiante est ");
		jpql.append(" JOIN FETCH ei.tblencTipoEvaluacion te ");
		jpql.append(" JOIN FETCH ei.tblacaPeriodo per ");
		jpql.append(" JOIN FETCH ei.tblacaSede sed ");
		jpql.append(" JOIN FETCH ei.tblacaPrograma gra ");
		jpql.append(" WHERE te.oid =:oid");
		jpql.append(" AND per.oid=:periodo");

		if (UtilidadBean.validaNulos(sede)) {
			jpql.append(" AND sed.oid=:sede");
		}
		if (UtilidadBean.validaNulos(programa)) {
			jpql.append(" AND gra.oid=:programa");
		}

		jpql.append(" ORDER BY sed.oid ASC, gra.oid ASC, est.nombres ASC ");

		List<TblencEvaluacionInstitucion> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("oid", prog.getOid());
		query.setParameter("periodo", periodo);
		if (UtilidadBean.validaNulos(sede)) {
			query.setParameter("sede", sede.getOid());
		}
		if (UtilidadBean.validaNulos(programa)) {
			query.setParameter("programa", programa.getOid());
		}

		listaEntidad = (List<TblencEvaluacionInstitucion>) query
				.getResultList();
		return listaEntidad;
	}

	@Override
	public Integer buscarEncuestaRealizada(TblencProgramacionEncuesta prog,
			TblthParticipante part) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT COUNT(ei) FROM ");
		jpql.append(TblencEvaluacionInstitucion.class.getSimpleName());
		jpql.append(" ei ");
		jpql.append(" JOIN ei.tblthParticipante p ");
		jpql.append(" JOIN ei.tblencTipoEvaluacion te ");
		jpql.append(" JOIN ei.tblacaPeriodo per ");
		jpql.append(" WHERE te.oid =:oid");
		jpql.append(" AND per.oid=:periodo");
		jpql.append(" AND p.idparticipante=:participante");

		Query query = em.createQuery(jpql.toString());
		query.setParameter("oid", prog.getTblencTipoEvaluacion().getOid());
		query.setParameter("periodo", prog.getTblacaPeriodo().getOid());
		query.setParameter("participante", part.getIdparticipante());

		Long valor = (Long) query.getSingleResult();
		return valor.intValue();

	}

	@Override
	public Integer buscarEncuestaRealizadaEstudiante(
			TblencProgramacionEncuesta prog, TblestEstudiante part,
			Integer sede, Integer programa) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT COUNT(ei) FROM ");
		jpql.append(TblencEvaluacionInstitucion.class.getSimpleName());
		jpql.append(" ei ");
		jpql.append(" JOIN ei.tblestEstudiante es ");
		jpql.append(" JOIN ei.tblencTipoEvaluacion te ");
		jpql.append(" JOIN ei.tblacaPeriodo per ");
		jpql.append(" JOIN ei.tblacaSede sed ");
		jpql.append(" JOIN ei.tblacaPrograma pro ");
		jpql.append(" WHERE te.oid =:oid");
		jpql.append(" AND per.oid=:periodo");
		jpql.append(" AND es.oid=:estudiante");
		jpql.append(" AND sed.oid=:sede");
		jpql.append(" AND pro.oid=:programa");

		Query query = em.createQuery(jpql.toString());
		query.setParameter("oid", prog.getTblencTipoEvaluacion().getOid());
		query.setParameter("periodo", prog.getTblacaPeriodo().getOid());
		query.setParameter("estudiante", part.getOid());
		query.setParameter("sede", sede);
		query.setParameter("programa", programa);

		Long valor = (Long) query.getSingleResult();
		return valor.intValue();
	}

	@Override
	public Integer EncuestaRealizadaEmpleador(TblencProgramacionEncuesta prog,
			Tblempleadore empleador) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT COUNT(ei) FROM ");
		jpql.append(TblencEvaluacionInstitucion.class.getSimpleName());
		jpql.append(" ei ");
		jpql.append(" JOIN ei.tblempleadore emp ");
		jpql.append(" JOIN ei.tblencTipoEvaluacion te ");
		jpql.append(" JOIN ei.tblacaPeriodo per ");

		jpql.append(" WHERE te.oid =:oid");
		jpql.append(" AND per.oid=:periodo");
		jpql.append(" AND emp.oid=:empleador");

		Query query = em.createQuery(jpql.toString());
		query.setParameter("oid", prog.getTblencTipoEvaluacion().getOid());
		query.setParameter("periodo", prog.getTblacaPeriodo().getOid());
		query.setParameter("empleador", empleador.getOid());
		Long valor = (Long) query.getSingleResult();
		return valor.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencEvaluacionInstitucion> buscarEmpleador(
			TblencTipoEvaluacion prog, Integer periodo, TblacaSede sede,
			TblacaPrograma programa) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT ei FROM ");
		jpql.append(TblencEvaluacionInstitucion.class.getSimpleName());
		jpql.append(" ei ");
		jpql.append(" JOIN FETCH ei.tblencTipoEvaluacion te ");
		jpql.append(" JOIN FETCH ei.tblempleadore p ");
		jpql.append(" JOIN FETCH ei.tblacaPeriodo per ");
		jpql.append(" JOIN FETCH ei.tblacaSede sed ");
		jpql.append(" JOIN FETCH ei.tblacaPrograma gra ");
		jpql.append(" WHERE te.oid =:oid");
		jpql.append(" AND per.oid=:periodo");

		List<TblencEvaluacionInstitucion> listaEntidad = null;
		if (UtilidadBean.validaNulos(sede)) {
			jpql.append(" AND sed.oid=:sede");
		}
		if (UtilidadBean.validaNulos(programa)) {
			jpql.append(" AND gra.oid=:programa");
		}
		jpql.append(" ORDER BY sed.oid, gra.oid, p.nit DESC ");

		Query query = em.createQuery(jpql.toString());
		query.setParameter("oid", prog.getOid());
		query.setParameter("periodo", periodo);
		if (UtilidadBean.validaNulos(sede)) {
			query.setParameter("sede", sede.getOid());
		}
		if (UtilidadBean.validaNulos(programa)) {
			query.setParameter("programa", programa.getOid());
		}

		listaEntidad = (List<TblencEvaluacionInstitucion>) query
				.getResultList();
		return listaEntidad;
	}

}
