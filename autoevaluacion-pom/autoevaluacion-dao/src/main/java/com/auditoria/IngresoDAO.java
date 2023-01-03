package com.auditoria;



import commons.exceptions.AutoevaluacionDAOException;

import auditoria.Tblingreso;

public interface IngresoDAO {

	public void ingreso(Tblingreso ingreso) throws AutoevaluacionDAOException;
}
