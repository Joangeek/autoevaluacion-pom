package com.gestion;

import gestion.MenuUserGruPer;

import java.util.List;

import commons.exceptions.AutoevaluacionDAOException;

public interface MenuUserGruPerDAO {

	public List<MenuUserGruPer> buscarPerfiles(Integer oid)
			throws AutoevaluacionDAOException;

	public List<MenuUserGruPer> buscarPerfiles(List<Integer> listMenuUsuGrupo, Integer codmenu)
			throws AutoevaluacionDAOException;

}
