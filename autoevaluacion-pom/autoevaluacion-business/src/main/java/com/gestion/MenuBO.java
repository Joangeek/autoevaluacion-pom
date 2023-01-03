package com.gestion;

import gestion.Menu;
import gestion.MenuUsuarioGrupo;

import java.util.List;

import javax.ejb.Remote;

import commons.exceptions.AutoevaluacionBOException;


/**
 * @author EDUAR
 * 
 */
@Remote
public interface MenuBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws AutoevaluacionBOException
	 */
	public List<Menu> buscarMenu(String codigo)
			throws AutoevaluacionBOException;

	public List<MenuUsuarioGrupo> buscarSub(Integer oid, String idGrupoUsuario) throws AutoevaluacionBOException;

}
