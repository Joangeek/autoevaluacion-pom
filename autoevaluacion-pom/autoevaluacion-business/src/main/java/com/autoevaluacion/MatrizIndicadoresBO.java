package com.autoevaluacion;

import java.util.List;

import javax.ejb.Remote;

import academico.TblacaSedePrograma;
import autoevaluacion.TblautCaracteristicaVigencia;
import autoevaluacion.TblautFactorVigencia;
import autoevaluacion.TblautFuente;
import autoevaluacion.TblautIndicadorFuente;
import autoevaluacion.TblautIndicadorVigencia;

import util.BOException;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface MatrizIndicadoresBO {

	/**
	 * Método que busca las sedes programas asociadas a los participantes por
	 * vigencia del proceso de autoeavaluación
	 * 
	 * @param participante
	 * @param vigenciaSelected
	 * @return
	 * @throws BOException
	 */
	public List<TblacaSedePrograma> buscarSedesprogramas(Integer sedeSelected,
			byte[] vigenciaSelected) throws BOException;

	public List<TblautIndicadorFuente> buscarIndFuentes(byte[] vigenciaSelected)
			throws BOException;

	public List<TblautFactorVigencia> resultadoMatriz(byte[] vigenciaSelected)
			throws BOException;

	/**
	 * Método que busca las caracteristicas por factor y vigencia
	 * 
	 * @param factor
	 * @param vigenciaSelected
	 * @return
	 */
	public List<TblautCaracteristicaVigencia> buscarCacateristicas(
			byte[] factor, byte[] vigenciaSelected)
			throws BOException;

	/**
	 * Método que busca los indicadores de acuerdo a la vigencia seleccionada, y
	 * a los parámetros enviados de interfaz(factor y caracteristica)
	 * 
	 * @param factor
	 * @param caracteristica
	 * @param vigenciaSelected
	 * @return
	 */
	public List<TblautIndicadorVigencia> buscarIndicadores(byte[] factor,
			byte[] caracteristica, byte[] vigenciaSelected)
			throws BOException;

	/**
	 * Métodp que obtiene la relación de la o las fuentes para las que aplica un
	 * indicador en particular de acuerdo a los parametros de interfaz
	 * 
	 * @param fuente
	 * @param indicador
	 * @param caracteristica
	 * @param factor
	 * @param vigencia
	 * @return
	 */
	public TblautFuente buscarFuente(Integer fuente, Integer indicador,
			Integer vigencia) throws BOException;

	/**
	 * Método que ontiene la lectura de cada indicador por fuente y demas
	 * parámetros de interfaz
	 * 
	 * @param fuente
	 * @param indicador
	 * @param caracteristica
	 * @param factor
	 * @param vigencia
	 * @param sede
	 * @param programa
	 * @return
	 */
	public Integer buscarLectura(Integer fuente, Integer indicador,
			Integer caracteristica, Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws BOException;

	/**
	 * Método que ontiene el promedio de calificacion de cada indicador por
	 * fuente y demas parámetros de interfaz
	 * 
	 * @param indicador
	 * @param caracteristica
	 * @param factor
	 * @param vigencia
	 * @param sede
	 * @param programa
	 * @return
	 * **/

	public Double buscarPromedioCalificacion(Integer oid, Integer oid2,
			Integer oid3,Integer vigenciaSelected, Integer oid4, Integer oid5)
			throws BOException;

	/**
	 * Método que ontiene el porcentaje de calificacion de cada indicador por
	 * fuente y demas parámetros de interfaz
	 * 
	 * @param indicador
	 * @param caracteristica
	 * @param factor
	 * @param vigencia
	 * @param sede
	 * @param programa
	 * @return
	 * **/
	public Double buscarPorcentajeCalificacion(Integer oid, Integer oid2,
			Integer oid3, Integer vigenciaSelected, Integer oid4, Integer oid5)
			throws BOException;

	/**
	 * Método que ontiene el promedio de calificacion de cada caracteristica por
	 * fuente y demas parámetros de interfaz
	 * 
	 * @param caracteristica
	 * @param factor
	 * @param vigencia
	 * @param sede
	 * @param programa
	 * @return
	 * **/

	public Double buscarPromedioCalificacion(Integer caracteristica,
			Integer factor, Integer vigencia, Integer sede, Integer programa)
			throws BOException;

	/**
	 * Método que ontiene el porcentaje de calificacion de cada caracteristica
	 * por fuente y demas parámetros de interfaz
	 * 
	 * @param caracteristica
	 * @param factor
	 * @param vigencia
	 * @param sede
	 * @param programa
	 * @return
	 * **/
	public Double buscarPorcentajeCalificacion(Integer caracteristica,
			Integer factor, Integer vigencia, Integer sede, Integer programa)
			throws BOException;

	/**
	 * 
	 * @param factor
	 * @param vigencia
	 * @param sede
	 * @param programa
	 * @return
	 * @throws BOException
	 */
	public Double buscarPromedioCalificacion(Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws BOException;

	/**
	 * 
	 * @param factor
	 * @param vigencia
	 * @param sede
	 * @param programa
	 * @return
	 */
	public Double buscarPorcentajeCalificacion(Integer factor,
			Integer vigencia, Integer sede, Integer programa)
			throws BOException;

	public Double buscarPorcentajeCalificacion(Integer vigencia, Integer sede,
			Integer programa) throws BOException;

	public Double buscarPromedioCalificacion(Integer vigencia, Integer sede,
			Integer programa) throws BOException;

}
