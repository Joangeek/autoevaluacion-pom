package com.autoevaluacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import commons.util.UtilidadBean;

import autoevaluacion.TblautVigencia;


import util.BOException;
import util.DAOException;

@Stateless(mappedName = "vigenciasBO")
public class VigenciasBOImpl implements VigenciasBO {

	@Inject
	private VigenciasDAO vigenciasDAO;

	@Override
	public List<TblautVigencia> buscarTodos() throws BOException {
		try {
			return vigenciasDAO.buscarTodos();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(byte[] entity) throws BOException {
		try {
			TblautVigencia entityNew = UtilidadBean.deserialize(entity);
			vigenciasDAO.crear(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblautVigencia editar(byte[] entity)
			throws BOException {

		try {
			TblautVigencia entityNew = UtilidadBean.deserialize(entity);
			return vigenciasDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(byte[] entity) throws BOException {
		try {
			TblautVigencia entityNew = UtilidadBean.deserialize(entity);
			vigenciasDAO.eliminar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}


}
