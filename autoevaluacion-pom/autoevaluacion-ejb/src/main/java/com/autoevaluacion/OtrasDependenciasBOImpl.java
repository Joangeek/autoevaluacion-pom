package com.autoevaluacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import commons.util.UtilidadBean;

import autoevaluacion.TblautOtrasDependencia;


import util.BOException;
import util.DAOException;

@Stateless(mappedName = "otrasDependenciasBO")
public class OtrasDependenciasBOImpl implements OtrasDependenciasBO {

	@Inject
	private OtrasDependenciasDAO otrasdependenciasDAO;

	@Override
	public List<TblautOtrasDependencia> buscarTodos()
			throws BOException {
		try {
			return otrasdependenciasDAO.buscarTodos();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautOtrasDependencia> buscarTodos(boolean estado)
			throws BOException {
		try {
			return otrasdependenciasDAO.buscarTodos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(byte[] entity) throws BOException {
		try {
			TblautOtrasDependencia dependencia = UtilidadBean
					.deserialize(entity);
			otrasdependenciasDAO.crear(dependencia);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblautOtrasDependencia editar(byte[] entity)
			throws BOException {

		try {
			TblautOtrasDependencia entityNew = UtilidadBean.deserialize(entity);
			return otrasdependenciasDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(TblautOtrasDependencia entity)
			throws BOException {
		try {
			otrasdependenciasDAO.eliminar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(byte[] entity) throws BOException {
		try {
			TblautOtrasDependencia entityNew = UtilidadBean.deserialize(entity);
			otrasdependenciasDAO.eliminar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void editarEstado(byte[] entity) throws BOException {
		try {

			TblautOtrasDependencia entityNew = UtilidadBean.deserialize(entity);
			if (entityNew.getEstado() == 1) {
				entityNew.setEstado(0);
			} else {
				entityNew.setEstado(1);
			}
			otrasdependenciasDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

}
