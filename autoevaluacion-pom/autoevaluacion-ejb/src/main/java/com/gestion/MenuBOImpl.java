package com.gestion;

import gestion.Menu;
import gestion.MenuUsuarioGrupo;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.gestion.MenuBO;
import com.gestion.MenuDAO;

import commons.exceptions.AutoevaluacionBOException;
import commons.exceptions.AutoevaluacionDAOException;

@Stateless(mappedName = "menuBO")
public class MenuBOImpl implements MenuBO {

	@Inject
	private MenuDAO menuDAO;

	@Override
	public List<Menu> buscarMenu(String codigo)
			throws AutoevaluacionBOException {
		try {
			return menuDAO.buscarMenu(codigo);
		} catch (AutoevaluacionDAOException e) {
			throw new AutoevaluacionBOException(e.getMessage(), e);
		}
	}

	@Override
	public List<MenuUsuarioGrupo> buscarSub(Integer oid, String userGrup) throws AutoevaluacionBOException {
		try {
			return menuDAO.buscarSub(oid,userGrup);
		} catch (AutoevaluacionDAOException e) {
			throw new AutoevaluacionBOException(e.getMessage(), e);
		}
	}

}
