package com.comun.dao;

import gestion.Usuario;
import gestion.UsuarioGrupo;
import academico.TblestEstudiante;


import commons.exceptions.AutoevaluacionDAOException;
import comun.Tblempleadore;

public interface UsuarioDAO {

	public Usuario buscarCodigo(String codigo)
			throws AutoevaluacionDAOException;

	public UsuarioGrupo validarAcceso(String uname, String password)
			throws AutoevaluacionDAOException;

	public TblestEstudiante validarAcceso(String trim)
			throws AutoevaluacionDAOException;

	public TblestEstudiante validarAcceso(String trim, Integer estado)
			throws AutoevaluacionDAOException;

	public Tblempleadore validarAccesEmpleador(String trim)
			throws AutoevaluacionDAOException;

}
