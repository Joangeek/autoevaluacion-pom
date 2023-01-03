package com.autoevaluacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import commons.util.UtilidadBean;

import autoevaluacion.TblautMecanismo;


import util.BOException;
import util.DAOException;

@Stateless(mappedName = "mecanismosBO")
public class MecanismosBOImpl implements MecanismosBO {

	@Inject
	private MecanismosDAO mecanimosDAO;

	@Override
	public List<TblautMecanismo> buscarTodos() throws BOException {
		try {
			return mecanimosDAO.buscarTodos();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautMecanismo> buscarTodos(boolean estado)
			throws BOException {
		try {
			return mecanimosDAO.buscarTodos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(TblautMecanismo entity) throws BOException {
		try {
			mecanimosDAO.crear(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblautMecanismo editar(byte[] entity)
			throws BOException {

		try {
			TblautMecanismo entityNew = UtilidadBean.deserialize(entity);
			return mecanimosDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(TblautMecanismo entity)
			throws BOException {
		try {
			mecanimosDAO.eliminar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(byte[] entity) throws BOException {
		try {
			TblautMecanismo entityNew = UtilidadBean.deserialize(entity);
			mecanimosDAO.eliminar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void editarEstado(byte[] entity) throws BOException {
		try {

			TblautMecanismo entityNew = UtilidadBean.deserialize(entity);
			if (entityNew.getEstado() == 1) {
				entityNew.setEstado(0);
			} else {
				entityNew.setEstado(1);
			}
			mecanimosDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}
}
