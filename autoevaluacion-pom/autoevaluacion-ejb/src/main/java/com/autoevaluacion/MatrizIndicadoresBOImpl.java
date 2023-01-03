/**
 * 
 */
package com.autoevaluacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import commons.util.UtilidadBean;

import academico.TblacaSedePrograma;
import autoevaluacion.TblautCaracteristica;
import autoevaluacion.TblautCaracteristicaVigencia;
import autoevaluacion.TblautFactorVigencia;
import autoevaluacion.TblautFactore;
import autoevaluacion.TblautFuente;
import autoevaluacion.TblautIndicadorFuente;
import autoevaluacion.TblautIndicadorVigencia;
import autoevaluacion.TblautVigencia;


import util.BOException;
import util.DAOException;

/**
 * @author EDUAR
 * 
 */
@Stateless(mappedName = "matrizIndicadoresBO")
public class MatrizIndicadoresBOImpl implements MatrizIndicadoresBO {

	@Inject
	private MatrizIndicadoresDAO matrizIndicadoresDAO;

	@Override
	public List<TblacaSedePrograma> buscarSedesprogramas(Integer oidSede,
			byte[] vigencia) throws BOException {
		try {
			TblautVigencia vig = UtilidadBean.deserialize(vigencia);
			return matrizIndicadoresDAO.buscarSedesprogramas(oidSede, vig.getAnioVigencia());
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautIndicadorFuente> buscarIndFuentes(byte[] vigenciaSelected)
			throws BOException {
		try {
			TblautVigencia vig = UtilidadBean.deserialize(vigenciaSelected);
			return matrizIndicadoresDAO.buscarIndFuentes(vig);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautFactorVigencia> resultadoMatriz(byte[] vigenciaSelected)
			throws BOException {
		try {
			TblautVigencia vig = UtilidadBean.deserialize(vigenciaSelected);

			return matrizIndicadoresDAO.resultadoMatriz(vig);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautCaracteristicaVigencia> buscarCacateristicas(
			byte[] factor, byte[] vigenciaSelected)
			throws BOException {
		try {
			TblautFactore fact = UtilidadBean.deserialize(factor);
			TblautVigencia vig = UtilidadBean.deserialize(vigenciaSelected);

			return matrizIndicadoresDAO.buscarCacateristicas(fact, vig);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautIndicadorVigencia> buscarIndicadores(byte[] factor,
			byte[] caracteristica, byte[] vigenciaSelected)
			throws BOException {
		try {
			TblautFactore fact = UtilidadBean.deserialize(factor);
			TblautCaracteristica car = UtilidadBean.deserialize(caracteristica);
			TblautVigencia vig = UtilidadBean.deserialize(vigenciaSelected);

			return matrizIndicadoresDAO.buscarIndicadores(fact, car, vig);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblautFuente buscarFuente(Integer fuente, Integer indicador,
			Integer vigencia) throws BOException {
		try {

			return matrizIndicadoresDAO.buscarFuente(fuente, indicador,
					vigencia);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Integer buscarLectura(Integer fuente, Integer indicador,
			Integer caracteristica, Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws BOException {
		try {

			return matrizIndicadoresDAO.buscarLectura(fuente, indicador,
					caracteristica, factor, vigencia, sede, programa);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Double buscarPromedioCalificacion(Integer indicador,
			Integer caracteristica, Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws BOException {
		try {

			return matrizIndicadoresDAO.buscarPromedioCalificacion(indicador,
					caracteristica, factor, vigencia, sede, programa);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Double buscarPorcentajeCalificacion(Integer indicador,
			Integer caracteristica, Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws BOException {
		try {

			return matrizIndicadoresDAO.buscarPorcentajeCalificacion(indicador,
					caracteristica, factor, vigencia, sede, programa);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Double buscarPromedioCalificacion(Integer caracteristica,
			Integer factor, Integer vigencia, Integer sede, Integer programa)
			throws BOException {
		try {

			return matrizIndicadoresDAO.buscarPromedioCalificacion(
					caracteristica, factor, vigencia, sede, programa);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Double buscarPorcentajeCalificacion(Integer caracteristica,
			Integer factor, Integer vigencia, Integer sede, Integer programa)
			throws BOException {
		try {

			return matrizIndicadoresDAO.buscarPorcentajeCalificacion(
					caracteristica, factor, vigencia, sede, programa);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Double buscarPromedioCalificacion(Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws BOException {
		try {

			return matrizIndicadoresDAO.buscarPromedioCalificacion(factor,
					vigencia, sede, programa);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Double buscarPorcentajeCalificacion(Integer factor,
			Integer vigencia, Integer sede, Integer programa)
			throws BOException {
		try {

			return matrizIndicadoresDAO.buscarPorcentajeCalificacion(factor,
					vigencia, sede, programa);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Double buscarPromedioCalificacion(Integer vigencia, Integer sede,
			Integer programa) throws BOException {
		try {

			return matrizIndicadoresDAO.buscarPromedioCalificacion(vigencia,
					sede, programa);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Double buscarPorcentajeCalificacion(Integer vigencia, Integer sede,
			Integer programa) throws BOException {
		try {

			return matrizIndicadoresDAO.buscarPorcentajeCalificacion(vigencia,
					sede, programa);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}
}
