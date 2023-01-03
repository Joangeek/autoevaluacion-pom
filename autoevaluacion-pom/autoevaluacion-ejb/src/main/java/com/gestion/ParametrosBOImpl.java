package com.gestion;

import gestion.Parametros;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.gestion.ParametrosBO;
import com.gestion.ParametrosDAO;

import commons.exceptions.AutoevaluacionBOException;
import commons.exceptions.AutoevaluacionDAOException;

@Stateless(mappedName = "parametrosBO")
public class ParametrosBOImpl implements ParametrosBO {

	@Inject
	private ParametrosDAO parametrosDAO;

	@Override
	public Parametros buscarCodigo(String codigo)
			throws AutoevaluacionBOException {
		try {
			return parametrosDAO.buscarCodigo(codigo);
		} catch (AutoevaluacionDAOException e) {
			throw new AutoevaluacionBOException(e.getMessage(), e);
		}
	}

}
