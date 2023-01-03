package com.talentoHumano;

import java.util.List;

import util.DAOException;

import talentoHumano.TblthParticipante;

public interface ParticipantesDAO {

	List<TblthParticipante> buscarTodos() throws DAOException;

	List<TblthParticipante> buscarTodos(boolean estado)
			throws DAOException;

}
