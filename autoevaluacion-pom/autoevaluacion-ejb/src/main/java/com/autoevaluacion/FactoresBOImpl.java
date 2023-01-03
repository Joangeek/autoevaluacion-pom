package com.autoevaluacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import commons.util.UtilidadBean;

import autoevaluacion.TblautFactore;


import util.BOException;
import util.DAOException;

@Stateless(mappedName = "factoresBO")
public class FactoresBOImpl implements FactoresBO {

	@Inject
	private FactoresDAO factoresDAO;

	@Override
	public List<TblautFactore> buscarTodos() throws BOException {
		try {
			return factoresDAO.buscarTodos();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautFactore> buscarTodos(boolean estado)
			throws BOException {
		try {
			return factoresDAO.buscarTodos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(byte[] entity) throws BOException {
		try {
			TblautFactore entityNew = UtilidadBean.deserialize(entity);
			entityNew.setEstado(UtilidadBean.booleano(true));
			factoresDAO.crear(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblautFactore editar(byte[] entity) throws BOException {
		try {

			TblautFactore entityNew = UtilidadBean.deserialize(entity);
			return factoresDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(TblautFactore entity) throws BOException {
		try {

			factoresDAO.eliminar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(byte[] entity) throws BOException {
		try {
			TblautFactore entityNew = UtilidadBean.deserialize(entity);
			factoresDAO.eliminar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void editarEstado(byte[] entity) throws BOException {
		try {

			TblautFactore entityNew = UtilidadBean.deserialize(entity);
			if (entityNew.getEstado() == 1) {
				entityNew.setEstado(0);
			} else {
				entityNew.setEstado(1);
			}
			factoresDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}



}
