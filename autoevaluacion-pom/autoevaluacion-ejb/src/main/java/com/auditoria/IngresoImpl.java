package com.auditoria;

import javax.ejb.Stateless;
import javax.inject.Inject;

import auditoria.Tblingreso;

import com.auditoria.IngresoDAO;
import com.gestion.IngresoBO;

import commons.exceptions.AutoevaluacionBOException;
import commons.exceptions.AutoevaluacionDAOException;

@Stateless(mappedName = "ingresoBO")
public class IngresoImpl implements IngresoBO {

	@Inject
	private IngresoDAO ingresoDAO;

	@Override
	public void ingreso(Tblingreso ingreso) throws AutoevaluacionBOException {
		try {
			ingresoDAO.ingreso(ingreso);
		} catch (AutoevaluacionDAOException e) {
			throw new AutoevaluacionBOException(e.getMessage(), e);
		}
	}

}
