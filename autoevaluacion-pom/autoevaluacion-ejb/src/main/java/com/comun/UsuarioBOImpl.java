package com.comun;

import gestion.Usuario;
import gestion.UsuarioGrupo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.comun.dao.UsuarioDAO;

import academico.TblestEstudiante;
import commons.exceptions.AutoevaluacionBOException;
import commons.exceptions.AutoevaluacionDAOException;
import comun.Tblempleadore;

@Stateless(mappedName = "UsuarioBO")
public class UsuarioBOImpl implements UsuarioBO {

	@Inject
	private UsuarioDAO usuarioDAO;

	@Override
	public Usuario buscarCodigo(String codigo) throws AutoevaluacionBOException {
		try {
			return usuarioDAO.buscarCodigo(codigo.trim());
		} catch (AutoevaluacionDAOException e) {
			throw new AutoevaluacionBOException(e.getMessage(), e);
		}
	}

	@Override
	public UsuarioGrupo validarAcceso(String uname, String tipoU)
			throws AutoevaluacionBOException {
		try {
			return usuarioDAO.validarAcceso(uname.trim(), tipoU.trim());
		} catch (AutoevaluacionDAOException e) {
			throw new AutoevaluacionBOException(e.getMessage(), e);
		}
	}

	@Override
	public TblestEstudiante validarAcceso(String uname)
			throws AutoevaluacionBOException {

		try {
			return usuarioDAO.validarAcceso(uname.trim());
		} catch (AutoevaluacionDAOException e) {
			throw new AutoevaluacionBOException(e.getMessage(), e);
		}

	}
	/**
	 * Estado en Estudiante programa
	 */
	@Override
	public TblestEstudiante validarAcceso(String uname, Integer estado)
			throws AutoevaluacionBOException {
		try {
			return usuarioDAO.validarAcceso(uname.trim(), estado);
		} catch (AutoevaluacionDAOException e) {
			throw new AutoevaluacionBOException(e.getMessage(), e);
		}

	}
	
	@Override
	public Tblempleadore validarAccesEmpleador(String uname)
			throws AutoevaluacionBOException {

		try {
			return usuarioDAO.validarAccesEmpleador(uname.trim());
		} catch (AutoevaluacionDAOException e) {
			throw new AutoevaluacionBOException(e.getMessage(), e);
		}

	}

}
