package com.autoevaluacion;

import java.util.List;

import academico.TblacaSedePrograma;
import autoevaluacion.TblautCaracteristica;
import autoevaluacion.TblautCaracteristicaVigencia;
import autoevaluacion.TblautFactorVigencia;
import autoevaluacion.TblautFactore;
import autoevaluacion.TblautFuente;
import autoevaluacion.TblautIndicadorFuente;
import autoevaluacion.TblautIndicadorVigencia;
import autoevaluacion.TblautVigencia;

import util.DAOException;

public interface MatrizIndicadoresDAO {

	List<TblacaSedePrograma> buscarSedesprogramas(Integer oidParticipante,
			Integer vigencia) throws DAOException;

	List<TblautIndicadorFuente> buscarIndFuentes(TblautVigencia vigenciaSelected)
			throws DAOException;

	List<TblautFactorVigencia> resultadoMatriz(TblautVigencia vigencia)
			throws DAOException;

	List<TblautCaracteristicaVigencia> buscarCacateristicas(TblautFactore fact,
			TblautVigencia vigenciaSelected) throws DAOException;

	List<TblautIndicadorVigencia> buscarIndicadores(TblautFactore factor,
			TblautCaracteristica caracteristica, TblautVigencia vigenciaSelected)
			throws DAOException;

	TblautFuente buscarFuente(Integer fuente, Integer indicador,
			Integer vigencia) throws DAOException;

	Integer buscarLectura(Integer fuente, Integer indicador,
			Integer caracteristica, Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws DAOException;

	Double buscarPromedioCalificacion(Integer indicador,
			Integer caracteristica, Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws DAOException;

	Double buscarPorcentajeCalificacion(Integer indicador,
			Integer caracteristica, Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws DAOException;

	Double buscarPromedioCalificacion(Integer caracteristica, Integer factor,
			Integer vigencia, Integer sede, Integer programa)
			throws DAOException;

	Double buscarPorcentajeCalificacion(Integer caracteristica, Integer factor,
			Integer vigencia, Integer sede, Integer programa)
			throws DAOException;

	Double buscarPromedioCalificacion(Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws DAOException;

	Double buscarPorcentajeCalificacion(Integer factor, Integer vigencia,
			Integer sede, Integer programa) throws DAOException;

	Double buscarPromedioCalificacion(Integer vigencia, Integer sede,
			Integer programa) throws DAOException;

	Double buscarPorcentajeCalificacion(Integer vigencia, Integer sede,
			Integer programa) throws DAOException;

}
