package com.encuesta;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import util.BOException;

import encuestas.TblencDirigidoa;
import encuestas.TblencModuloTipoEvaluacion;
import encuestas.TblencProgramacionEncuesta;
import encuestas.TblencTipoEvaluacion;
import encuestas.util.ReporteEncuestaRealizadas;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface TipoEvaluacionBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblencTipoEvaluacion> buscarT()
			throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public List<TblencTipoEvaluacion> buscarTodos(boolean estado)
			throws BOException;

	/**
	 * Método que crea un nuevo registro de tipo aspecto
	 * 
	 * @param tipoAspecto
	 * @throws BOException
	 */
	public void crear(TblencTipoEvaluacion tipoEvaluacion)
			throws BOException;

	/**
	 * Método que edita un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void editar(TblencTipoEvaluacion selected)
			throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminar(TblencTipoEvaluacion selected)
			throws BOException;

	/**
	 * Método que edita un aspecto y retorna la entidad actualizada
	 * 
	 * @param selected
	 * @throws BOException
	 * 
	 */
	public TblencTipoEvaluacion editarR(TblencTipoEvaluacion selected)
			throws BOException;

	/**
	 * Método que busca los diferentes dirigidos a que estén ya relacionados en
	 * las evaluacions
	 * 
	 * @return
	 */
	public List<TblencDirigidoa> buscarDirigidoAs()
			throws BOException;

	/**
	 * Método que busca los diferentes modulos a que estén ya relacionados en
	 * las evaluacions
	 * 
	 * @return
	 */
	public List<TblencModuloTipoEvaluacion> buscarModulosAs()
			throws BOException;

	/**
	 * Método que busca todas la opciones de la entidada que se encuentren
	 * activos
	 * 
	 * @return
	 */
	public List<TblencDirigidoa> buscarTDirigidoA(boolean val)
			throws BOException;

	/**
	 * Método que busca todas la opciones de la entidada que se encuentren
	 * activos
	 * 
	 * @return
	 */
	public List<TblencModuloTipoEvaluacion> buscarTModulos(boolean val)
			throws BOException;

	public Integer eliminar(byte[] serialize) throws BOException;

	public List<TblencTipoEvaluacion> buscarID(Integer oid)
			throws BOException;

	public TblencDirigidoa buscarDirigidoA(String descripcion)
			throws BOException;

	public List<TblencProgramacionEncuesta> buscarEncuesta(Date fechaHoy,
			Integer dirigidoA) throws BOException;

	public List<TblencTipoEvaluacion> buscarEstadoByModulo(boolean b, int modulo)
			throws BOException;

	public List<ReporteEncuestaRealizadas> buscarParticipantes(
			byte[] programacion, byte[] periodo, byte[] sede)
			throws BOException;

	public List<ReporteEncuestaRealizadas> buscarParticipantes(
			byte[] programacion, byte[] periodo, byte[] sede, byte[] programa)
			throws BOException;

	public List<ReporteEncuestaRealizadas> buscarEstudiantes(byte[] prog,
			byte[] periodo, byte[] sede, byte[] programa)
			throws BOException;

	public Integer buscarEncuestaRealizada(byte[] programacionEncuesta,
			byte[] participante) throws BOException;

	public Integer buscarEncuestaRealizadaEstudiante(
			byte[] programacionEncuesta, byte[] participante, Integer sede,
			Integer programa) throws BOException;

	public Integer EncuestaRealizadaEmpleador(byte[] tipoEncuesta,
			byte[] empleador) throws BOException;

	public List<ReporteEncuestaRealizadas> buscarEmpleadores(
			byte[] tipoEncuesta, byte[] periodo, byte[] sede, byte[] programa)
			throws BOException;

}
