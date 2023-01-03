package com.auditoria.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import auditoria.Tblingreso;

import com.auditoria.IngresoDAO;
import commons.exceptions.AutoevaluacionDAOException;

public class IngresoDAOImpl implements IngresoDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;

	@Override
	public void ingreso(Tblingreso ingreso) throws AutoevaluacionDAOException {
		em.persist(ingreso);
	}

}
