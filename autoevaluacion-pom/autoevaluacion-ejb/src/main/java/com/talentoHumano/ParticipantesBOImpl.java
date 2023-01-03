package com.talentoHumano;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import talentoHumano.TblthParticipante;


import util.BOException;
import util.DAOException;

@Stateless(mappedName = "participantesBO")
public class ParticipantesBOImpl implements ParticipantesBO {

	@Inject
	private ParticipantesDAO participantesDAO;

	@Override
	public List<TblthParticipante> buscarTodos()
			throws BOException {
		try {
			return participantesDAO.buscarTodos();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblthParticipante> buscarTodos(boolean estado)
			throws BOException {
		try {
			return participantesDAO.buscarTodos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

}
