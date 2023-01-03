package com.academico;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import commons.util.UtilidadBean;

import academico.TblacaPeriodo;
import util.BOException;
import util.DAOException;


@Stateless(mappedName = "periodoBO")
public class PeriodoBOImpl implements PeriodoBO {

	@Inject
	private PeriodoDAO periodoDAO;

	@Override
	public List<TblacaPeriodo> buscarTodosLimite(Integer limit, Integer offSet)
			throws BOException {
		try {
			return periodoDAO.buscarTodosLimite(limit, offSet);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblacaPeriodo buscarEstado(boolean estado)
			throws BOException {
		try {
			return periodoDAO.buscarEstado(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblacaPeriodo> buscarTodos() throws BOException {
		try {
			return periodoDAO.buscarTodos();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(byte[] periodo) throws BOException {
		try {
			TblacaPeriodo entity = UtilidadBean.deserialize(periodo);
			periodoDAO.crear(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void editar(byte[] periodo) throws BOException {
		try {
			TblacaPeriodo entity = UtilidadBean.deserialize(periodo);
			periodoDAO.editar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(byte[] periodo) throws BOException {
		try {
			TblacaPeriodo entity = UtilidadBean.deserialize(periodo);
			periodoDAO.eliminar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<Integer> buscarAnios() throws BOException {
		try {
			return periodoDAO.buscarAnios();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

}
