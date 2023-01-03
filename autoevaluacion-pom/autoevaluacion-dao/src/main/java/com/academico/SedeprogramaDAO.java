package com.academico;

import java.util.List;

import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblacaSedePrograma;
import academico.TblestEstudiante;
import academico.TblestEstudiantePrograma;

import util.DAOException;

public interface SedeprogramaDAO {

	public List<TblacaSede> buscarSedes() throws DAOException;

	public void eliminarSede(TblacaSede entity)
			throws DAOException;

	public void crearSede(TblacaSede entity) throws DAOException;

	public void editarSede(TblacaSede entity) throws DAOException;

	public List<TblacaPrograma> buscarProgramas()
			throws DAOException;

	public void eliminarPrograma(TblacaPrograma entity)
			throws DAOException;

	public void crearPrograma(TblacaPrograma entity)
			throws DAOException;

	public void editarPrograma(TblacaPrograma entity)
			throws DAOException;

	public void eliminarSedePrograma(TblacaSedePrograma entity)
			throws DAOException;

	public void crearSedePrograma(TblacaSedePrograma entity)
			throws DAOException;

	public void editarSedePrograma(TblacaSedePrograma entity)
			throws DAOException;

	public List<TblacaPrograma> buscarProgramasActivos(boolean estado)
			throws DAOException;

	public List<TblacaSede> buscarSedesActivas(boolean estado)
			throws DAOException;

	public List<TblacaSedePrograma> buscarPorSede(TblacaSede entity)
			throws DAOException;

	public List<TblacaSede> buscarSedesEstadoPorSp(boolean estado)
			throws DAOException;

	public List<TblacaSedePrograma> buscarProgramasEstadoSp(Integer entity,
			boolean estado) throws DAOException;

	public List<TblestEstudiantePrograma> buscarPorEstudiante(
			TblestEstudiante entity) throws DAOException;

	public List<TblestEstudiantePrograma> buscarPorEstudiante(
			TblestEstudiante entity, Integer estados)
			throws DAOException;

}
