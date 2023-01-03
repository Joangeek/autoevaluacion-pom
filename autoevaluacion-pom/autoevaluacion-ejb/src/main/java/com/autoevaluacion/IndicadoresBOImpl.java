package com.autoevaluacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import commons.util.UtilidadBean;

import autoevaluacion.TblautCaracteristica;
import autoevaluacion.TblautIndicadoresCaracteristica;


import util.BOException;
import util.DAOException;

@Stateless(mappedName = "indicadoresBO")
public class IndicadoresBOImpl implements IndicadoresBO {

	@Inject
	private IndicadoresDAO indicadoresDAO;

	@Override
	public List<TblautIndicadoresCaracteristica> buscarTodos()
			throws BOException {
		try {
			return indicadoresDAO.buscarTodos();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautIndicadoresCaracteristica> buscarTodos(boolean estado)
			throws BOException {
		try {
			return indicadoresDAO.buscarTodos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(byte[] entity) throws BOException {
		try {
			TblautIndicadoresCaracteristica entityNew = UtilidadBean
					.deserialize(entity);
			entityNew.setEstado(UtilidadBean.booleano(true));
			indicadoresDAO.crear(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblautIndicadoresCaracteristica editar(byte[] entity)
			throws BOException {
		try {

			TblautIndicadoresCaracteristica entityNew = UtilidadBean
					.deserialize(entity);
			return indicadoresDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(TblautIndicadoresCaracteristica entity)
			throws BOException {
		try {

			indicadoresDAO.eliminar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(byte[] entity) throws BOException {
		try {
			TblautIndicadoresCaracteristica entityNew = UtilidadBean
					.deserialize(entity);
			indicadoresDAO.eliminar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void editarEstado(byte[] entity) throws BOException {
		try {

			TblautIndicadoresCaracteristica entityNew = UtilidadBean
					.deserialize(entity);
			if (entityNew.getEstado() == 1) {
				entityNew.setEstado(0);
			} else {
				entityNew.setEstado(1);
			}
			indicadoresDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautIndicadoresCaracteristica> listado()
			throws BOException {
		try {
			return indicadoresDAO.listado();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautIndicadoresCaracteristica> buscarPorCaracteristica(
			byte[] entity) throws BOException {
		try {
			TblautCaracteristica entityNew = UtilidadBean
					.deserialize(entity);
			return indicadoresDAO.buscarPorCaracteristica(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

}
