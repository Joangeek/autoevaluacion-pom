package com.autoevaluacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import autoevaluacion.TblautFuente;
import commons.util.UtilidadBean;
import util.BOException;
import util.DAOException;

@Stateless(mappedName = "fuentesBO")
public class FuentesBOImpl implements FuentesBO {

	@Inject 
	private FuentesDAO fuentesDAO;

	@Override
	public List<TblautFuente> buscarTodos() throws BOException {
		try {
			return fuentesDAO.buscarTodos();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautFuente> buscarTodos(boolean estado)
			throws BOException {
		try {
			return fuentesDAO.buscarTodos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(byte[] entity) throws BOException {
		try {
			TblautFuente entityNew = UtilidadBean.deserialize(entity);
			entityNew.setEstado(UtilidadBean.booleano(true));
			fuentesDAO.crear(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblautFuente editar(byte[] entity) throws BOException {
		try {

			TblautFuente entityNew = UtilidadBean.deserialize(entity);
			return fuentesDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(TblautFuente entity) throws BOException {
		try {

			fuentesDAO.eliminar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(byte[] entity) throws BOException {
		try {
			TblautFuente entityNew = UtilidadBean.deserialize(entity);
			fuentesDAO.eliminar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void editarEstado(byte[] entity) throws BOException {
		try {

			TblautFuente entityNew = UtilidadBean.deserialize(entity);
			if (entityNew.getEstado() == 1) {
				entityNew.setEstado(0);
			} else {
				entityNew.setEstado(1);
			}
			fuentesDAO.editar(entityNew);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}



}
