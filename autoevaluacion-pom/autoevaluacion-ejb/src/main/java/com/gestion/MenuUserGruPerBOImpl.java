package com.gestion;

import gestion.MenuUserGruPer;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.gestion.MenuUserGruPerBO;
import com.gestion.MenuUserGruPerDAO;

import commons.exceptions.AutoevaluacionBOException;
import commons.exceptions.AutoevaluacionDAOException;

@Stateless(mappedName = "menuUserGruPerBO")
public class MenuUserGruPerBOImpl implements MenuUserGruPerBO {

	@Inject
	private MenuUserGruPerDAO menuUserGruPerDAO;

	@Override
	public List<MenuUserGruPer> buscaPerfiles(Integer codigo)
			throws AutoevaluacionBOException {
		try {
			return menuUserGruPerDAO.buscarPerfiles(codigo);
		} catch (AutoevaluacionDAOException e) {
			throw new AutoevaluacionBOException(e.getMessage(), e);
		}
	}

	@Override
	public List<MenuUserGruPer> buscaPerfiles(List<Integer> listMenuUsuGrupo,
			Integer codmenu) throws AutoevaluacionBOException {
		try {
			return menuUserGruPerDAO.buscarPerfiles(listMenuUsuGrupo,codmenu);
		} catch (AutoevaluacionDAOException e) {
			throw new AutoevaluacionBOException(e.getMessage(), e);
		}
	}
}
