package com.autoevaluacion;

import java.util.List;

import javax.ejb.Remote;

import talentoHumano.TblthParticipante;
import academico.TblacaSedePrograma;
import autoevaluacion.TblautFactore;
import autoevaluacion.TblautFuente;
import autoevaluacion.TblautLecturaIndicadore;
import autoevaluacion.TblautLecturaIndicadoresOt;
import autoevaluacion.TblautMecanismo;
import autoevaluacion.TblautOtrasDependencia;

import util.BOException;

/**
 * @author EDUAR
 * 
 */
@Remote
public interface LecturaIndicadoresBO {
	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblautLecturaIndicadore> buscarTLi()
			throws BOException;

	/**
	 * Método que lista todos los registros
	 * 
	 * @return
	 * @throws BOException
	 */
	public List<TblautLecturaIndicadore> buscarTLiOd()
			throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public List<TblautLecturaIndicadore> buscarTodosLi(boolean estado)
			throws BOException;

	/**
	 * Método que lista todos los registros por estado
	 * 
	 * @param estado
	 * @return
	 * @throws BOException
	 */
	public List<TblautLecturaIndicadore> buscarTodosLiOd(boolean estado)
			throws BOException;

	/**
	 * Método que crea un nuevo registro de tipo aspecto
	 * 
	 * @param tipoAspecto
	 * @throws BOException
	 */
	public void crearLi(byte[] bs) throws BOException;

	/**
	 * Método que crea un nuevo registro de tipo aspecto
	 * 
	 * @param tipoAspecto
	 * @throws BOException
	 */
	public void crearLiOd(byte[] bs) throws BOException;

	/**
	 * Método que edita un aspecto
	 * 
	 * @param bs
	 * @throws BOException
	 */
	public void editarLi(byte[] bs) throws BOException;

	/**
	 * Método que edita un aspecto
	 * 
	 * @param bs
	 * @throws BOException
	 */
	public void editarLiOd(byte[] bs) throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminarLi(byte[] selected) throws BOException;

	/**
	 * Método que elimina un aspecto
	 * 
	 * @param selected
	 * @throws BOException
	 */
	public void eliminarLiOd(byte[] selected) throws BOException;

	/**
	 * Método que edita un aspecto y retorna la entidad actualizada
	 * 
	 * @param entity
	 * @throws BOException
	 * 
	 */
	public TblautLecturaIndicadore editarLe(byte[] entity)
			throws BOException;

	/**
	 * Método que edita un aspecto y retorna la entidad actualizada
	 * 
	 * @param entity
	 * @throws BOException
	 * 
	 */
	public TblautLecturaIndicadoresOt editarLeOt(byte[] entity)
			throws BOException;

	TblthParticipante buscarParticipanteOid(String oid)
			throws BOException;

	/**
	 * Método que busca los distintos mecanismos de acuerdo a la configuración
	 * para otras dependencias
	 * 
	 * @param oidParticipante
	 * @param vigenciaSelected
	 * @param dependencia 
	 * @param oidPrograma
	 * @param oidSede
	 * 
	 * @return
	 */
	public List<TblautMecanismo> buscarMecanismosLiOd(Integer oidParticipante,
			Integer vigenciaSelected, Integer dependencia) throws BOException;

	/**
	 * Método que busca los distintos mecanismos de acuerdo a la configuración
	 * para dire3ctores de programa
	 * 
	 * @param oidParticipante
	 * @param oidPrograma
	 * @param oidSede
	 * @param vigenciaSelected
	 * 
	 * @return
	 */
	public List<TblautMecanismo> buscarMecanismosLi(Integer oidSede,
			Integer oidPrograma, Integer oidParticipante,
			Integer vigenciaSelected) throws BOException;

	/**
	 * Método que búsca las diferentes dependencias configuradas para el proceso
	 * de autoevaluación y asignadas al participante logueado
	 * 
	 * @param idparticipante
	 * @return
	 * @throws BOException
	 */
	public List<TblautOtrasDependencia> buscarDependencias(
			Integer idparticipante) throws BOException;

	/**
	 * Método que búsca los factores cobfigurados/asociados a la dependencia
	 * seleccionada de acuaerdo al mecanismo
	 * 
	 * @param oidmecanismo
	 * @param oidParticipante
	 * @param oidPrograma
	 * @param oidSede
	 * @return
	 * @throws BOException
	 */
	public List<TblautFactore> buscarfactores(Integer oidmecanismo,
			Integer oidSede, Integer oidPrograma, Integer oidParticipante)
			throws BOException;

	/**
	 * Método que búsca los factores cobfigurados/asociados a la dependencia
	 * seleccionada de acuaerdo al mecanismo
	 * 
	 * @param oidmecanismo
	 * @param oidParticipante
	 * @param dependencia 
	 * @return
	 * @throws BOException
	 */
	public List<TblautFactore> buscarfactoresOd(Integer oidMecanismo,
			Integer oidParticipante, Integer dependencia) throws BOException;

	/**
	 * Método que búsca el listado de los indicadores de lectura configurados
	 * para directores de programa
	 * 
	 * @param oidFactor
	 * @param oidMecanismo
	 * @param oidParticipante
	 * @param oidDependencia 
	 * @return
	 * @throws BOException
	 */
	public List<TblautLecturaIndicadoresOt> buscarIndicadoresLecOd(
			Integer oidFactor, Integer oidMecanismo, Integer oidParticipante, Integer oidDependencia)
			throws BOException;

	/**
	 * Método que búsca el listado de los indicadores de lectura configurados
	 * para directores otas dependencias
	 * 
	 * @param factor
	 * @param sede
	 * @param programa
	 * @param mecanismo
	 * @param participante
	 * @return
	 * @throws BOException
	 */
	public List<TblautLecturaIndicadore> buscarIndicadoresLec(
			Integer oidFactor, Integer oidSede, Integer oidPrograma,
			Integer oidMecanismo, Integer oidParticipante)
			throws BOException;

	public List<Integer> buscarVigencias() throws BOException;

	/**
	 * Método que busca las sedes programas asociadas a los participantes por
	 * vigencia del proceso de autoeavaluación
	 * 
	 * @param participante
	 * @param vigenciaSelected
	 * @return
	 * @throws BOException
	 */
	public List<TblacaSedePrograma> buscarSedesprogramasParticipante(
			Integer participante, Integer vigenciaSelected)
			throws BOException;

	public List<TblautFuente> buscarFuentes()
			throws BOException;

	public List<TblautMecanismo> buscarMecanismos()
			throws BOException;

}
