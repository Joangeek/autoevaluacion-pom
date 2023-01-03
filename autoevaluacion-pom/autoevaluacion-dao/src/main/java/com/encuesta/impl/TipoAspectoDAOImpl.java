package com.encuesta.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mockito.exceptions.Reporter;

import util.DAOException;
import commons.util.UtilidadBean;
import com.encuesta.TipoAspectoDAO;

import encuestas.TblencAspecto;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencOpcione;
import encuestas.TblencTipoAspecto;
import encuestas.TblencTipoEvaluacion;
import encuestas.util.ReporteEncuestaResultados;
import encuestas.util.ResultadoEncuesta;

@SuppressWarnings("unused")
public class TipoAspectoDAOImpl implements TipoAspectoDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	static Logger LOGGER = Logger.getLogger(TipoAspectoDAOImpl.class
			.getSimpleName());
	static final String WHERE = " WHERE ";
	static final String AND = " AND ";

	@SuppressWarnings("unchecked")
	public List<TblencTipoAspecto> buscarTodos(boolean estado)
			throws DAOException {
		Query q = null;
		q = em.createNamedQuery("TblencTipoAspecto.findAllEstado")
				.setParameter("estado", UtilidadBean.booleano(estado));
		return q.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<TblencTipoAspecto> buscarTodos()
			throws DAOException {
		Query q = null;
		q = em.createNamedQuery("TblencTipoAspecto.findAll");
		return q.getResultList();
	}

	public void crear(TblencTipoAspecto tipoAspecto)
			throws DAOException {
		em.persist(tipoAspecto);
	}

	public void editar(TblencTipoAspecto selected)
			throws DAOException {
		em.merge(selected);
	}

	public void eliminar(TblencTipoAspecto selected)
			throws DAOException {
		em.remove(em.merge(selected));
	}

	@Override
	public TblencTipoAspecto editarR(TblencTipoAspecto selected)
			throws DAOException {
		return em.merge(selected);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencAspecto> buscarPorEncuesta(TblencTipoEvaluacion entity)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT asp FROM ");
		jpql.append(TblencAspecto.class.getSimpleName());
		jpql.append(" asp ");
		jpql.append(" LEFT JOIN FETCH asp.padre pad ");
		jpql.append(" JOIN FETCH asp.tblencTipoAspecto tas ");
		jpql.append(" JOIN FETCH asp.tblencGrupoAspecto gasp ");
		jpql.append(" JOIN FETCH gasp.tblencTipoEvaluacion te ");
		jpql.append(" WHERE te=:entity AND asp.estado=1 ");
		jpql.append(" ORDER BY gasp.orden, asp.orden asc ");

		List<TblencAspecto> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("entity", entity);
		listaEntidad = (List<TblencAspecto>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencOpcione> buscarOpcAspecto(TblencTipoAspecto entity)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT opc FROM ");
		jpql.append(TblencOpcione.class.getSimpleName());
		jpql.append(" opc ");
		jpql.append(" JOIN FETCH opc.tblencTipoAspecto tas ");
		jpql.append(" WHERE tas=:entity AND opc.estado=1 ");
		jpql.append(" ORDER BY opc.orden ASC ");

		List<TblencOpcione> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("entity", entity);
		listaEntidad = (List<TblencOpcione>) query.getResultList();
		return listaEntidad;
	}

	@Override
	public List<ResultadoEncuesta> verRespuestas(Map<TblencAspecto, String> mapa)
			throws DAOException {
		List<ResultadoEncuesta> lista = new ArrayList<ResultadoEncuesta>();

		for (Map.Entry<TblencAspecto, String> val : mapa.entrySet()) {
			StringBuilder jpql = new StringBuilder();
			jpql.append("SELECT opc FROM ");
			jpql.append(TblencOpcione.class.getSimpleName());
			jpql.append(" opc ");
			jpql.append(" WHERE  opc.oid=:oid ");

			try {
				Query query = em.createQuery(jpql.toString());
				query.setParameter("oid", Integer.parseInt(val.getValue()));

				TblencOpcione entity = (TblencOpcione) query.getSingleResult();

				ResultadoEncuesta res = new ResultadoEncuesta();
				res.setAspecto(val.getKey());
				res.setRespuesta(entity);
				lista.add(res);

			} catch (NoResultException e) {

			} catch (Exception e) {

			}

		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencGrupoAspecto> gruposPorEncuesta(
			TblencTipoEvaluacion entity) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT gasp FROM ");
		jpql.append(TblencGrupoAspecto.class.getSimpleName());
		jpql.append(" gasp ");
		jpql.append(" JOIN FETCH gasp.tblencTipoEvaluacion te ");
		jpql.append(WHERE);
		jpql.append(" te=:entity ");
		jpql.append(AND);
		jpql.append(" EXISTS( ");
		jpql.append("SELECT  DISTINCT(asp.tblencGrupoAspecto) FROM ");
		jpql.append(TblencAspecto.class.getSimpleName());
		jpql.append(" asp ");
		jpql.append(WHERE);
		jpql.append(" asp.tblencGrupoAspecto=gasp AND asp.estado=1 ");
		jpql.append(" ) ");
		jpql.append(" ORDER BY gasp.orden ASC ");

		List<TblencGrupoAspecto> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("entity", entity);
		listaEntidad = (List<TblencGrupoAspecto>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblencAspecto> buscarPorGrupo(TblencGrupoAspecto entity)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT asp FROM ");
		jpql.append(TblencAspecto.class.getSimpleName());
		jpql.append(" asp ");
		jpql.append(" LEFT JOIN FETCH asp.padre pad ");
		jpql.append(" JOIN FETCH asp.tblencTipoAspecto tas ");
		jpql.append(" JOIN FETCH asp.tblencGrupoAspecto gasp ");
		jpql.append(" JOIN FETCH gasp.tblencTipoEvaluacion te ");
		jpql.append(" WHERE gasp=:entity AND asp.estado=1 ");
		jpql.append(" ORDER BY  asp.orden ASC ");

		List<TblencAspecto> listaEntidad = null;

		Query query = em.createQuery(jpql.toString());
		query.setParameter("entity", entity);
		listaEntidad = (List<TblencAspecto>) query.getResultList();
		return listaEntidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteEncuestaResultados> buscarOpcAspecto(
			TblencAspecto aspecto, Integer oidTipoEval, Integer oidPeriodo)
			throws DAOException {

		List<ReporteEncuestaResultados> listaUtil = new ArrayList<ReporteEncuestaResultados>();

		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT asp.oid,opc.nombre, "

				+ " (SELECT COUNT(1) FROM academico.tblenc_aspecto_evaluacion aspval"
				+ " INNER JOIN academico.tblenc_evaluacion_institucion eval ON(eval.oid=aspval.id_evaluacion_inst)"
				+ " INNER JOIN academico.tblaca_periodo per ON(per.oid=eval.idperiodo) "
				+ " WHERE aspval.idaspecto=asp.oid "
				+ " AND aspval.idopcion=opc.oid "
				+ " AND per.oid=?1 "
				+ " AND   eval.idtipo_evaluacion=?2 "
				+ " ) AS cantidad"

				+ " FROM  academico.tblenc_tipo_aspectos tas "
				+ " INNER JOIN academico.tblenc_opciones opc ON(tas.oid=opc.idtipo_aspecto) "
				+ " INNER JOIN academico.tblenc_aspectos asp ON (tas.oid= asp.idtipo_aspecto) "
				+ " INNER JOIN academico.tblenc_grupo_aspecto gru ON(gru.oid=asp.idgrupo_aspecto)"
				+ " WHERE asp.oid=?3 "
				+ " AND gru.oid=?4 "
				+ " GROUP BY asp.oid,opc.oid,opc.nombre, opc.orden ORDER BY opc.orden ");

		Query query = em.createNativeQuery(jpql.toString());
		query.setParameter(1, oidPeriodo);
		query.setParameter(2, oidTipoEval);
		query.setParameter(3, aspecto.getOid());
		query.setParameter(4, aspecto.getTblencGrupoAspecto().getOid());

		// LOGGER.info("---------------------------------------------------------------");
		List<Object[]> result = query.getResultList();
		for (Object[] val : result) {
			ReporteEncuestaResultados resultados = new ReporteEncuestaResultados(
					Integer.parseInt(val[0].toString()), val[1].toString(),
					Integer.parseInt(val[2].toString()));
			listaUtil.add(resultados);

		}
		/*
		 * LOGGER.info("---"); LOGGER.info("---FOR DE ENTIDAD CREADA"); for
		 * (ReporteEncuestaResultados val : listaUtil) { LOGGER.info(" ---> " +
		 * val.getOid()); LOGGER.info(" ---> " + val.getNombre());
		 * LOGGER.info(" ---> " + val.getCantidad()); }
		 * LOGGER.info("-----------------------END");
		 */
		return listaUtil;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteEncuestaResultados> buscarOpcAspecto(
			TblencAspecto aspecto, Integer oidTipoEval, Integer oidPeriodo,
			Integer oidSede, Integer oidPrograma)
			throws DAOException {
		List<ReporteEncuestaResultados> listaUtil = new ArrayList<ReporteEncuestaResultados>();

		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT asp.oid,opc.nombre, "

				+ " (SELECT COUNT(1) FROM academico.tblenc_aspecto_evaluacion aspval "
				+ " INNER JOIN academico.tblenc_evaluacion_institucion eval ON(eval.oid=aspval.id_evaluacion_inst)"
				+ " INNER JOIN academico.tblaca_periodo per ON(per.oid=eval.idperiodo) "
				+ " LEFT OUTER JOIN academico.tblaca_sede sed ON(eval.idsede=sed.oid)"
				+ " LEFT OUTER JOIN academico.tblaca_programa prog ON(eval.idprograma=prog.oid)"
				+ " WHERE aspval.idaspecto=asp.oid "
				+ " AND aspval.idopcion=opc.oid " + " AND per.oid=?1 "
				+ " AND eval.idtipo_evaluacion=?2 ");
		if (oidSede > 0) {
			jpql.append(" AND sed.oid=?5");
		}
		if (oidPrograma > 0) {
			jpql.append(" AND prog.oid=?6");
		}
		jpql.append(" ) AS cantidad"

				+ " FROM  academico.tblenc_tipo_aspectos tas "
				+ " INNER JOIN academico.tblenc_opciones opc ON(tas.oid=opc.idtipo_aspecto) "
				+ " INNER JOIN academico.tblenc_aspectos asp ON (tas.oid= asp.idtipo_aspecto) "
				+ " INNER JOIN academico.tblenc_grupo_aspecto gru ON(gru.oid=asp.idgrupo_aspecto)"
				+ " WHERE asp.oid=?3 "
				+ " AND gru.oid=?4 "
				+ " GROUP BY asp.oid,opc.oid,opc.nombre, opc.orden ORDER BY opc.orden ");
		Query query = em.createNativeQuery(jpql.toString());
		query.setParameter(1, oidPeriodo);
		query.setParameter(2, oidTipoEval);
		query.setParameter(3, aspecto.getOid());
		query.setParameter(4, aspecto.getTblencGrupoAspecto().getOid());

		if (oidSede > 0) {
			query.setParameter(5, oidSede);
		}

		if (oidPrograma > 0) {
			query.setParameter(6, oidPrograma);
		}

		// LOGGER.info("---------------------------------------------------------------");

		List<Object[]> result = query.getResultList();
		for (Object[] val : result) {
			ReporteEncuestaResultados resultados = new ReporteEncuestaResultados(
					Integer.parseInt(val[0].toString()), val[1].toString(),
					Integer.parseInt(val[2].toString()));
			listaUtil.add(resultados);

		}
//		LOGGER.info("---");
//		LOGGER.info("---FOR DE ENTIDAD CREADA");
//		for (ReporteEncuestaResultados val : listaUtil) {
//			LOGGER.info(" ---> " + val.getOid());
//			LOGGER.info(" ---> " + val.getNombre());
//			LOGGER.info(" ---> " + val.getCantidad());
//		}
//		LOGGER.info("-----------------------END");
		return listaUtil;
	}
}
