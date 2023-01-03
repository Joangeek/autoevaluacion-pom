package com.talentoHumano;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import talentoHumano.TblthCargo;


import util.BOException;
import util.DAOException;

@Stateless(mappedName = "cargosBO")
public class CargosBOImpl implements CargosBO {

	@Inject
	private CargosDAO cargosDAO;

	@Override
	public List<TblthCargo> buscarTodos() throws BOException {
		try {
			return cargosDAO.buscarTodos();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblthCargo> buscarTodos(boolean estado)
			throws BOException {
		try {
			return cargosDAO.buscarTodos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

}
