package com.gestion;

import gestion.Menu;
import gestion.MenuUsuarioGrupo;

import java.util.List;

import commons.exceptions.AutoevaluacionDAOException;


public interface MenuDAO {

	public List<Menu> buscarMenu(String codigo)
			throws AutoevaluacionDAOException;

	public List<MenuUsuarioGrupo> buscarSub(Integer oid, String userGrup) throws AutoevaluacionDAOException;
}
