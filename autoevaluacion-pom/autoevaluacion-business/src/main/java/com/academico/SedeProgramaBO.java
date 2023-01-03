/**
 * 
 */
package com.academico;

import java.util.List;

import javax.ejb.Remote;

import util.BOException;

import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblacaSedePrograma;
import academico.TblestEstudiantePrograma;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface SedeProgramaBO {

	public List<TblacaSede> buscarSedes() throws BOException;

	public void eliminarSede(byte[] selected) throws BOException;

	public void crearSede(byte[] programa) throws BOException;

	public void editarSede(byte[] selected) throws BOException;

	public List<TblacaPrograma> buscarProgramas()
			throws BOException;

	public void eliminarPrograma(byte[] selected)
			throws BOException;

	public void crearPrograma(byte[] sede) throws BOException;

	public void editarPrograma(byte[] selected)
			throws BOException;

	public void eliminarSedePrograma(byte[] serialize)
			throws BOException;

	public void crearSedePrograma(byte[] serialize)
			throws BOException;

	public void editarSedePrograma(byte[] serialize)
			throws BOException;

	public List<TblacaPrograma> buscarProgramasActivos(boolean estado)
			throws BOException;

	public List<TblacaSede> buscarSedesActivas(boolean estado)
			throws BOException;

	public List<TblacaSedePrograma> buscarPorSede(byte[] serialize)
			throws BOException;

	public List<TblacaSede> buscarSedesEstadoPorSp(boolean estado)
			throws BOException;

	public List<TblacaSedePrograma> buscarProgramasEstadoSp(Integer serialize,
			boolean true1) throws BOException;

	public List<TblestEstudiantePrograma> buscarPorEstudiante(byte[] estudiante)
			throws BOException;

	public List<TblestEstudiantePrograma> buscarPorEstudiante(byte[] estudiante,
			Integer estados)throws BOException;

}
