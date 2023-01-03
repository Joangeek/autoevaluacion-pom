/**
 * 
 */
package com.autoevaluacion.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import academico.TblacaSedePrograma;
import autoevaluacion.TblautCaracteristica;
import autoevaluacion.TblautCaracteristicaVigencia;
import autoevaluacion.TblautFactorVigencia;
import autoevaluacion.TblautFactore;
import autoevaluacion.TblautFuente;
import autoevaluacion.TblautIndicadorFuente;
import autoevaluacion.TblautIndicadorVigencia;
import autoevaluacion.TblautLecturaIndicadore;
import autoevaluacion.TblautVigencia;

import com.autoevaluacion.MatrizIndicadoresDAO;
import util.DAOException;
import commons.util.UtilidadBean;

/**
 * @author EDUAR
 * 
 */
@SuppressWarnings("unchecked")
public class MatrizIndicadoresDAOImpl implements MatrizIndicadoresDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@Override
	public List<TblacaSedePrograma> buscarSedesprogramas(Integer oidSede,
			Integer vigencia) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblacaSedePrograma.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblacaSede se ");
		jpql.append(" JOIN FETCH te.tblacaPrograma pr ");

		jpql.append(" WHERE  se.oid IN(SELECT DISTINCT(s.oid) FROM ");
		jpql.append(TblautLecturaIndicadore.class.getSimpleName());
		jpql.append(" le JOIN le.tblacaSede s ");
		jpql.append("  JOIN le.tblautVigencia vig ");
		jpql.append(" WHERE s.oid=:oidSede AND vig.anioVigencia=:vigencia  )");

		jpql.append(" AND pr.oid IN(SELECT DISTINCT(p.oid) FROM ");
		jpql.append(TblautLecturaIndicadore.class.getSimpleName());
		jpql.append(" le JOIN le.tblacaPrograma p JOIN le.tblacaSede s JOIN le.tblautVigencia vig");
		jpql.append(" WHERE s.oid=:oidSede AND vig.anioVigencia=:vigencia  )");
		jpql.append(" ORDER BY te.oid DESC ");

		List<TblacaSedePrograma> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("oidSede", oidSede);
		q.setParameter("vigencia", vigencia);
		listaEntidad = (List<TblacaSedePrograma>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblautIndicadorFuente> buscarIndFuentes(TblautVigencia vigencia)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautIndicadorFuente.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblautFuente fu ");
		jpql.append(" JOIN FETCH te.tblautMecanismo me ");
		jpql.append(" JOIN FETCH te.tblautIndicadoresCaracteristica ind ");
		jpql.append(" JOIN FETCH ind.tblautCaracteristica car ");
		jpql.append(" JOIN FETCH car.tblautFactore fac ");
		jpql.append(" JOIN te.tblautVigencia vig ");

		jpql.append(" WHERE vig=:vigencia  )");
		jpql.append(" ORDER BY fac.orden,car.orden, ind.orden ");

		List<TblautIndicadorFuente> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("vigencia", vigencia);
		listaEntidad = (List<TblautIndicadorFuente>) q.getResultList();
		return listaEntidad;
	}

	public List<TblautIndicadorVigencia> buscarIndicadores(Integer vigencia,
			TblautFactorVigencia factor) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautIndicadorVigencia.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblautIndicadoresCaracteristica ind ");
		jpql.append(" JOIN FETCH ind.tblautCaracteristica car ");
		jpql.append(" JOIN car.tblautFactore fac ");
		jpql.append(" JOIN te.tblautVigencia vig ");

		jpql.append(" WHERE vig.anioVigencia=:vigencia  AND fac=:factor");
		jpql.append(" ORDER BY car.orden, ind.orden ");

		List<TblautIndicadorVigencia> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("vigencia", vigencia);
		q.setParameter("factor", factor.getTblautFactore());
		listaEntidad = (List<TblautIndicadorVigencia>) q.getResultList();
		return listaEntidad;
	}

	public List<TblautFactorVigencia> buscarFactores(TblautVigencia vigencia)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautFactorVigencia.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblautFactore fac ");
		jpql.append(" JOIN FETCH te.tblautVigencia vig ");

		jpql.append(" WHERE vig=:vigencia ");
		jpql.append(" ORDER BY fac.orden");

		List<TblautFactorVigencia> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("vigencia", vigencia);
		listaEntidad = (List<TblautFactorVigencia>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblautFactorVigencia> resultadoMatriz(TblautVigencia vigencia)
			throws DAOException {

		List<TblautFactorVigencia> listaEntidad = new ArrayList<TblautFactorVigencia>();
		listaEntidad = buscarFactores(vigencia);
		return listaEntidad;
	}

	@Override
	public List<TblautCaracteristicaVigencia> buscarCacateristicas(
			TblautFactore factor, TblautVigencia vigencia)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautCaracteristicaVigencia.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblautCaracteristica car ");
		jpql.append(" JOIN car.tblautFactore fac ");
		jpql.append(" JOIN te.tblautVigencia vig ");

		jpql.append(" WHERE vig=:vigencia  ");
		jpql.append(" AND fac=:factor  ");
		jpql.append(" ORDER BY car.orden ASC ");

		List<TblautCaracteristicaVigencia> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("factor", factor);
		q.setParameter("vigencia", vigencia);
		listaEntidad = (List<TblautCaracteristicaVigencia>) q.getResultList();
		return listaEntidad;
	}

	@Override
	public List<TblautIndicadorVigencia> buscarIndicadores(
			TblautFactore factor, TblautCaracteristica caracteristica,
			TblautVigencia vigencia) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT te FROM ");
		jpql.append(TblautIndicadorVigencia.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN FETCH te.tblautIndicadoresCaracteristica ind ");
		jpql.append(" JOIN ind.tblautCaracteristica car ");
		jpql.append(" JOIN car.tblautFactore fac ");
		jpql.append(" JOIN te.tblautVigencia vig ");

		jpql.append(" WHERE vig=:vigencia  ");
		jpql.append(" AND fac=:factor  ");
		jpql.append(" AND car=:caracteristica  ");
		jpql.append(" ORDER BY ind.orden ASC ");

		List<TblautIndicadorVigencia> listaEntidad = null;

		Query q = em.createQuery(jpql.toString());
		q.setParameter("factor", factor);
		q.setParameter("caracteristica", caracteristica);
		q.setParameter("vigencia", vigencia);

		listaEntidad = (List<TblautIndicadorVigencia>) q.getResultList();
		List<TblautIndicadorVigencia> nuevaLista = new ArrayList<TblautIndicadorVigencia>();

		int i = 0;
		for (TblautIndicadorVigencia item : listaEntidad) {
			TblautIndicadorVigencia entity = new TblautIndicadorVigencia();
			entity = UtilidadBean.clonarObjecto(item);
			entity.setFuentes(buscarFuente(item
					.getTblautIndicadoresCaracteristica().getOid(), vigencia));

			nuevaLista.add(i, entity);
			i++;
		}
		return nuevaLista;
	}

	@Override
	public TblautFuente buscarFuente(Integer fuente, Integer indicador,
			Integer vigencia) throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT fue FROM ");
		jpql.append(TblautIndicadorFuente.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN te.tblautFuente fue ");
		jpql.append(" JOIN te.tblautIndicadoresCaracteristica ind ");
		jpql.append(" JOIN te.tblautVigencia vig ");

		jpql.append(" WHERE vig.oid=:vigencia  ");
		jpql.append(" AND ind.oid=:indicador  ");
		jpql.append(" AND fue.oid=:fuente  ");
		jpql.append(" ORDER BY ind.orden ASC ");

		Query q = em.createQuery(jpql.toString());
		q.setParameter("indicador", indicador);
		q.setParameter("fuente", fuente);
		q.setParameter("vigencia", vigencia);

		try {
			return (TblautFuente) q.getSingleResult();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			return null;
		} catch (NoResultException e) {
			// e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Integer> buscarFuente(Integer indicador, TblautVigencia vigencia)
			throws DAOException {
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT fue.oid FROM ");
		jpql.append(TblautIndicadorFuente.class.getSimpleName());
		jpql.append(" te ");
		jpql.append(" JOIN te.tblautFuente fue ");
		jpql.append(" JOIN te.tblautIndicadoresCaracteristica ind ");
		jpql.append(" JOIN te.tblautVigencia vig ");

		jpql.append(" WHERE vig=:vigencia  ");
		jpql.append(" AND ind.oid=:indicador  ");
		jpql.append(" ORDER BY ind.orden ASC ");

		Query q = em.createQuery(jpql.toString());
		q.setParameter("indicador", indicador);
		q.setParameter("vigencia", vigencia);

		return q.getResultList();

	}

	@Override
	public Integer buscarLectura(Integer fuente, Integer indicador,
			Integer caracteristica, Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws DAOException {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT lectura  ");
		sql.append("FROM ");
		sql.append("academico.tblaut_lectura_ind_final ");
		sql.append("WHERE vigencia =?1 ");
		sql.append("AND idindicador=?2 ");
		sql.append("AND idcaracteristica=?3 ");
		sql.append("AND idfactor=?4 ");
		sql.append("AND idfuente=?5  ");
		sql.append("AND idsede=?6  ");
		sql.append("AND idprograma=?7 ");

		try {
			Query q = em.createNativeQuery(sql.toString());
			q.setParameter(1, vigencia);
			q.setParameter(2, indicador);
			q.setParameter(3, caracteristica);
			q.setParameter(4, factor);
			q.setParameter(5, fuente);
			q.setParameter(6, sede);
			q.setParameter(7, programa);

			return (Integer) q.getSingleResult();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			return null;
		} catch (NoResultException e) {
			// e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * INDICADOR
	 */
	@Override
	public Double buscarPromedioCalificacion(Integer indicador,
			Integer caracteristica, Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws DAOException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ROUND(AVG(lectura))  ");
		sql.append("FROM ");
		sql.append("academico.tblaut_lectura_ind_final ");
		sql.append("WHERE vigencia =?1 ");
		sql.append("AND idindicador=?2 ");
		sql.append("AND idcaracteristica=?3 ");
		sql.append("AND idfactor=?4 ");
		sql.append("AND idsede=?5  ");
		sql.append("AND idprograma=?6 ");
		BigDecimal resul = new BigDecimal(0.0);
		try {
			Query q = em.createNativeQuery(sql.toString());
			q.setParameter(1, vigencia);
			q.setParameter(2, indicador);
			q.setParameter(3, caracteristica);
			q.setParameter(4, factor);
			q.setParameter(5, sede);
			q.setParameter(6, programa);

			resul = (BigDecimal) q.getSingleResult();
			return resul.doubleValue();
		} catch (NullPointerException e) {
			return 0.0;
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			return 0.0;
		} catch (NoResultException e) {
			return 0.0;

		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}

	}

	/**
	 * INDICADOR
	 */
	@Override
	public Double buscarPorcentajeCalificacion(Integer indicador,
			Integer caracteristica, Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws DAOException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ((AVG(lectura)* (SELECT ponderacion FROM academico.tblaut_indicador_vigencia WHERE OID=fi.idindicador AND vigencia= fi.vigencia))/100) AS valor ");
		sql.append("FROM ");
		sql.append("academico.tblaut_lectura_ind_final fi ");
		sql.append("WHERE vigencia =?1 ");
		sql.append("AND fi.idindicador=?2 ");
		sql.append("AND fi.idcaracteristica=?3 ");
		sql.append("AND fi.idfactor=?4 ");
		sql.append("AND fi.idsede=?5  ");
		sql.append("AND fi.idprograma=?6 ");
		sql.append("GROUP BY fi.idindicador,fi.vigencia  ");
		BigDecimal resul = new BigDecimal(0.0);
		try {
			Query q = em.createNativeQuery(sql.toString());
			q.setParameter(1, vigencia);
			q.setParameter(2, indicador);
			q.setParameter(3, caracteristica);
			q.setParameter(4, factor);
			q.setParameter(5, sede);
			q.setParameter(6, programa);

			resul = (BigDecimal) q.getSingleResult();
			return resul.doubleValue();
		} catch (NullPointerException e) {
			return 0.0;
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			return 0.0;
		} catch (NoResultException e) {
			return 0.0;

		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}

	}

	/**
	 * CARACTERÍSTICA
	 */
	@Override
	public Double buscarPromedioCalificacion(Integer caracteristica,
			Integer factor, Integer vigencia, Integer sede, Integer programa)
			throws DAOException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT AVG(lectura)  ");
		sql.append("FROM ");
		sql.append("academico.tblaut_lectura_ind_final ");
		sql.append("WHERE vigencia =?1 ");
		sql.append("AND idcaracteristica=?3 ");
		sql.append("AND idfactor=?4 ");
		sql.append("AND idsede=?5  ");
		sql.append("AND idprograma=?6 ");
		BigDecimal resul = new BigDecimal(0.0);
		try {
			Query q = em.createNativeQuery(sql.toString());
			q.setParameter(1, vigencia);

			q.setParameter(3, caracteristica);
			q.setParameter(4, factor);
			q.setParameter(5, sede);
			q.setParameter(6, programa);

			resul = (BigDecimal) q.getSingleResult();
			return resul.doubleValue();
		} catch (NullPointerException e) {
			return 0.0;
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			return 0.0;
		} catch (NoResultException e) {
			return 0.0;

		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}

	}

	/**
	 * CARACTERÍSTICA
	 */
	@Override
	public Double buscarPorcentajeCalificacion(Integer caracteristica,
			Integer factor, Integer vigencia, Integer sede, Integer programa)
			throws DAOException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT SUM(x.valor)FROM (SELECT ((AVG(lectura)* (SELECT ponderacion FROM academico.tblaut_indicador_vigencia WHERE oid=fi.idindicador AND vigencia= fi.vigencia))/100) AS valor ");
		sql.append("FROM ");
		sql.append("academico.tblaut_lectura_ind_final fi ");
		sql.append("WHERE vigencia =?1 ");
		sql.append("AND fi.idcaracteristica=?3 ");
		sql.append("AND fi.idfactor=?4 ");
		sql.append("AND fi.idsede=?5  ");
		sql.append("AND fi.idprograma=?6 ");
		sql.append("GROUP BY fi.idindicador,fi.vigencia) X  ");
		BigDecimal resul = new BigDecimal(0.0);
		try {
			Query q = em.createNativeQuery(sql.toString());
			q.setParameter(1, vigencia);
			q.setParameter(3, caracteristica);
			q.setParameter(4, factor);
			q.setParameter(5, sede);
			q.setParameter(6, programa);

			resul = (BigDecimal) q.getSingleResult();
			return resul.doubleValue();
		} catch (NullPointerException e) {
			return 0.0;
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			return 0.0;
		} catch (NoResultException e) {
			return 0.0;

		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}

	}

	/**
	 * FACTOR
	 */
	@Override
	public Double buscarPromedioCalificacion(Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws DAOException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT SUM(x.valor)FROM (SELECT ((AVG(lectura)* (SELECT ponderacion FROM academico.tblaut_caracteristica_vigencia WHERE idcaracteristica=fi.idcaracteristica AND vigencia= fi.vigencia))/100) AS valor ");
		sql.append("FROM ");
		sql.append("academico.tblaut_lectura_ind_final fi ");
		sql.append("WHERE vigencia =?1 ");
		sql.append("AND fi.idfactor=?4 ");
		sql.append("AND fi.idsede=?5  ");
		sql.append("AND fi.idprograma=?6 ");
		sql.append("GROUP BY fi.idcaracteristica,fi.vigencia) X  ");
		BigDecimal resul = new BigDecimal(0.0);
		try {
			Query q = em.createNativeQuery(sql.toString());
			q.setParameter(1, vigencia);
			q.setParameter(4, factor);
			q.setParameter(5, sede);
			q.setParameter(6, programa);

			resul = (BigDecimal) q.getSingleResult();
			return resul.doubleValue();
		} catch (NullPointerException e) {
			return 0.0;
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			return 0.0;
		} catch (NoResultException e) {
			return 0.0;

		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}

	}

	/**
	 * FACTOR
	 */
	@Override
	public Double buscarPorcentajeCalificacion(Integer factor,
			Integer vigencia, Integer sede, Integer programa)
			throws DAOException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT SUM(Y.val)FROM ("
				+ " SELECT SUM(X.valor)* ((SELECT ponderacion FROM academico.tblaut_caracteristica_vigencia WHERE idcaracteristica=X.idcaracteristica AND vigencia= X.vigencia)/100) AS VAL FROM ( "
				+ " SELECT fi.idcaracteristica, fi.vigencia,((AVG(lectura)* (SELECT ponderacion FROM academico.tblaut_indicador_vigencia WHERE OID=fi.idindicador AND vigencia= fi.vigencia))/100) AS valor ");

		sql.append("FROM ");
		sql.append("academico.tblaut_lectura_ind_final fi ");
		sql.append("WHERE vigencia =?1 ");
		sql.append("AND fi.idfactor=?4 ");
		sql.append("AND fi.idsede=?5  ");
		sql.append("AND fi.idprograma=?6 ");
		sql.append(" GROUP BY fi.idcaracteristica,fi.idindicador,fi.vigencia order by fi.idindicador"
				+ "	) X GROUP BY X.vigencia,X.idcaracteristica" + "	) Y ");
		BigDecimal resul = new BigDecimal(0.0);
		try {
			Query q = em.createNativeQuery(sql.toString());
			q.setParameter(1, vigencia);
			q.setParameter(4, factor);
			q.setParameter(5, sede);
			q.setParameter(6, programa);

			resul = (BigDecimal) q.getSingleResult();
			return resul.doubleValue();
		} catch (NullPointerException e) {
			return 0.0;
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			return 0.0;
		} catch (NoResultException e) {
			return 0.0;

		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}

	}

	/**
	 * TOTAL
	 */
	@Override
	public Double buscarPromedioCalificacion(Integer vigencia, Integer sede,
			Integer programa) throws DAOException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT SUM(x.valor)FROM (SELECT ((AVG(lectura)* (SELECT ponderacion FROM academico.tblaut_caracteristica_vigencia WHERE idcaracteristica=fi.idcaracteristica AND vigencia= fi.vigencia))/100) AS valor ");
		sql.append("FROM ");
		sql.append("academico.tblaut_lectura_ind_final fi ");
		sql.append("WHERE vigencia =?1 ");
		sql.append("AND fi.idsede=?5  ");
		sql.append("AND fi.idprograma=?6 ");
		sql.append("GROUP BY fi.idcaracteristica,fi.vigencia) X  ");
		BigDecimal resul = new BigDecimal(0.0);
		try {
			Query q = em.createNativeQuery(sql.toString());
			q.setParameter(1, vigencia);
			q.setParameter(5, sede);
			q.setParameter(6, programa);

			resul = (BigDecimal) q.getSingleResult();
			return resul.doubleValue();
		} catch (NullPointerException e) {
			return 0.0;
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			return 0.0;
		} catch (NoResultException e) {
			return 0.0;

		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}

	}

	/**
	 * TOTAL
	 */
	@Override
	public Double buscarPorcentajeCalificacion(Integer vigencia, Integer sede,
			Integer programa) throws DAOException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT SUM(Y.val)FROM ("
				+ " SELECT SUM(X.valor)* ((SELECT ponderacion FROM academico.tblaut_caracteristica_vigencia WHERE idcaracteristica=X.idcaracteristica AND vigencia= X.vigencia)/100) AS VAL FROM ( "
				+ " SELECT fi.idcaracteristica, fi.vigencia,((AVG(lectura)* (SELECT ponderacion FROM academico.tblaut_indicador_vigencia WHERE OID=fi.idindicador AND vigencia= fi.vigencia))/100) AS valor ");

		sql.append("FROM ");
		sql.append("academico.tblaut_lectura_ind_final fi ");
		sql.append("WHERE vigencia =?1 ");
		sql.append("AND fi.idsede=?5  ");
		sql.append("AND fi.idprograma=?6 ");
		sql.append(" GROUP BY fi.idcaracteristica,fi.idindicador,fi.vigencia order by fi.idindicador"
				+ "	) X GROUP BY X.vigencia,X.idcaracteristica" + "	) Y ");
		BigDecimal resul = new BigDecimal(0.0);
		try {
			Query q = em.createNativeQuery(sql.toString());
			q.setParameter(1, vigencia);
			q.setParameter(5, sede);
			q.setParameter(6, programa);

			resul = (BigDecimal) q.getSingleResult();
			return resul.doubleValue();
		} catch (NullPointerException e) {
			return 0.0;
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			return 0.0;
		} catch (NoResultException e) {
			return 0.0;

		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}

	}

}
