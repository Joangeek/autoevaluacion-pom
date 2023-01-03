package com.encuesta;

import java.util.List;

import javax.ejb.Remote;

import academico.TblacaPeriodo;
import academico.TblacaPrograma;
import academico.TblacaSede;

import util.BOException;

import encuestas.TblencProgramacionEncSedeProg;
import encuestas.TblencProgramacionEncuesta;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface ProgramacionBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblencProgramacionEncuesta> buscarPorEvaluacion(
			byte[] evaluacion) throws BOException;

	public List<TblacaPeriodo> buscarPeriodos(byte[] evaluacion)
			throws BOException;

	/**
	 * Método que crea un nuevo registro de tipo aspecto
	 * 
	 * @param tipoAspecto
	 * @throws BOException
	 */
	public void crear(byte[] programacion) throws BOException;

	/**
	 * Método que edita un aspecto
	 * 
	 * @param selected
	 * @return
	 * @throws BOException
	 */
	public TblencProgramacionEncuesta editar(byte[] programacion)
			throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminar(byte[] programacion) throws BOException;

	public Integer contarSedesProgramasConfig(byte[] oid)
			throws BOException;

	public List<TblacaSede> buscarSedesPorSpIN(byte[] serialize)
			throws BOException;

	public List<TblacaPrograma> buscarProgramasPorSpIN(byte[] serialize)
			throws BOException;

	public List<TblencProgramacionEncSedeProg> sedesProgramasConfig(
			byte[] serialize) throws BOException;

	public TblencProgramacionEncSedeProg editarPsp(byte[] serialize)
			throws BOException;

	public void crearPsp(byte[] serialize) throws BOException;

	public void eliminarPsp(byte[] serialize) throws BOException;

	public List<TblacaPrograma> buscarTodosP() throws BOException;

}
