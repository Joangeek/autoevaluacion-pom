package com.gestion;

import commons.exceptions.AutoevaluacionDAOException;

import gestion.Parametros;


public interface ParametrosDAO {

	public Parametros buscarCodigo(String codigo)
			throws AutoevaluacionDAOException;
}
