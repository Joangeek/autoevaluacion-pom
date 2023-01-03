package com.encuesta;

import java.util.List;

import javax.ejb.Remote;

import util.BOException;

import encuestas.TblencAspecto;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencOpcione;
import encuestas.TblencTipoAspecto;
import encuestas.util.ReporteEncuestaResultadosFinal;
import encuestas.util.ResultadoEncuesta;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface TipoAspectoBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblencTipoAspecto> buscarTodos()
			throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public List<TblencTipoAspecto> buscarTodos(boolean estado)
			throws BOException;

	/**
	 * Método que crea un nuevo registro de tipo aspecto
	 * 
	 * @param tipoAspecto
	 * @throws BOException
	 */
	public void crear(TblencTipoAspecto tipoAspecto)
			throws BOException;

	/**
	 * Método que edita un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void editar(TblencTipoAspecto selected)
			throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminar(TblencTipoAspecto selected)
			throws BOException;

	/**
	 * Método que edita un aspecto y retorna la entidad actualizada
	 * 
	 * @param selected
	 * @throws BOException
	 * 
	 */
	public TblencTipoAspecto editarR(TblencTipoAspecto selected)
			throws BOException;

	public List<TblencAspecto> buscarPorEncuesta(byte[] bs)
			throws BOException;

	public List<TblencOpcione> buscarOpcAspecto(byte[] serialize)
			throws BOException;

	public List<ResultadoEncuesta> verRespuestas(byte[] serialize)
			throws BOException;

	public List<TblencGrupoAspecto> gruposPorEncuesta(byte[] serialize)
			throws BOException;

	public List<TblencAspecto> buscarPorGrupo(byte[] serialize)
			throws BOException;

	public ReporteEncuestaResultadosFinal buscarOpcAspecto(byte[] serialize,
			Integer oidTipoEval, Integer oidPeriodo)
			throws BOException;

	public ReporteEncuestaResultadosFinal buscarOpcAspecto(byte[] serialize,
			Integer oidTipoEval, Integer oidperiodo, byte[] oidSede,
			byte[] oidPrograma) throws BOException;

}
