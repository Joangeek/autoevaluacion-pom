package com.comun;

import gestion.Usuario;
import gestion.UsuarioGrupo;

import javax.ejb.Remote;

import academico.TblestEstudiante;


import commons.exceptions.AutoevaluacionBOException;
import comun.Tblempleadore;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface UsuarioBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws AutoevaluacionBOException
	 */
	public Usuario buscarCodigo(String codigo) throws AutoevaluacionBOException;

	public UsuarioGrupo validarAcceso(String uname, String tipoU)
			throws AutoevaluacionBOException;

	public TblestEstudiante validarAcceso(String uname)
			throws AutoevaluacionBOException;

	public TblestEstudiante validarAcceso(String uname, Integer estadoEP)
			throws AutoevaluacionBOException;

	public Tblempleadore validarAccesEmpleador(String uname)
			throws AutoevaluacionBOException;

}
