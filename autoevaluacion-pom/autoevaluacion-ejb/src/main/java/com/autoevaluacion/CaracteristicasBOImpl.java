package com.autoevaluacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import commons.util.UtilidadBean;

import autoevaluacion.TblautCaracteristica;
import autoevaluacion.TblautFactore;


import util.BOException;
import util.DAOException;

@Stateless(mappedName = "caracteristicasBO")
public class CaracteristicasBOImpl implements CaracteristicasBO {

	@Inject
	private CaracteristicasDAO caracteristicasDAO;

	@Override
	public List<TblautCaracteristica> buscarTodos()
			throws BOException {
		try {
			return caracteristicasDAO.buscarTodos();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautCaracteristica> buscarTodos(boolean estado)
			throws BOException {
		try {
			return caracteristicasDAO.buscarTodos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(byte[] entity) throws BOException {
		try {
			TblautCaracteristica entityNew = UtilidadBean.deserialize(entity);
			entityNew.setEstado(UtilidadBean.booleano(true));
			caracteristicasDAO.crear(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblautCaracteristica editar(byte[] entity)
			throws BOException {
		try {

			TblautCaracteristica entityNew = UtilidadBean.deserialize(entity);
			return caracteristicasDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(TblautCaracteristica entity)
			throws BOException {
		try {

			caracteristicasDAO.eliminar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(byte[] entity) throws BOException {
		try {
			TblautCaracteristica entityNew = UtilidadBean.deserialize(entity);
			caracteristicasDAO.eliminar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void editarEstado(byte[] entity) throws BOException {
		try {

			TblautCaracteristica entityNew = UtilidadBean.deserialize(entity);
			if (entityNew.getEstado() == 1) {
				entityNew.setEstado(0);
			} else {
				entityNew.setEstado(1);
			}
			caracteristicasDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautCaracteristica> buscarPorFactor(
			byte[] factorSelected) throws BOException {
		try {
			TblautFactore factor = UtilidadBean.deserialize(factorSelected);
			return caracteristicasDAO.buscarPorFactor(factor);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}

	}

}
