package com.gestion;

import gestion.MenuUserGruPer;

import java.util.List;

import javax.ejb.Remote;

import commons.exceptions.AutoevaluacionBOException;


/**
 * @author EDUAR
 * 
 */
@Remote
public interface MenuUserGruPerBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws AutoevaluacionBOException
	 */
	public List<MenuUserGruPer> buscaPerfiles(Integer oid)
			throws AutoevaluacionBOException;

	public List<MenuUserGruPer> buscaPerfiles(List<Integer> listMenuUsuGrupo, Integer integer)
			throws AutoevaluacionBOException;

}
