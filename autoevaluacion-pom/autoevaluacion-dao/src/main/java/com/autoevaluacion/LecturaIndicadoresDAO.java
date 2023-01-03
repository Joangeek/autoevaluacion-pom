package com.autoevaluacion;

import java.util.List;

import talentoHumano.TblthParticipante;
import academico.TblacaSedePrograma;
import autoevaluacion.TblautFactore;
import autoevaluacion.TblautFuente;
import autoevaluacion.TblautLecturaIndicadore;
import autoevaluacion.TblautLecturaIndicadoresOt;
import autoevaluacion.TblautMecanismo;
import autoevaluacion.TblautOtrasDependencia;

import util.DAOException;

public interface LecturaIndicadoresDAO {

	TblthParticipante buscarParticipanteOid(String oidPaticipante)
			throws DAOException;

	List<TblautMecanismo> buscarMecanismosLiOd(Integer oidParticipante,
			Integer vigencia, Integer dependencia) throws DAOException;

	List<TblautMecanismo> buscarMecanismosLi(Integer oidSede,
			Integer oidPrograma, Integer oidParticipante, Integer vigencia)
			throws DAOException;

	List<TblautOtrasDependencia> buscarDependencias(Integer idparticipante)
			throws DAOException;

	List<TblautFactore> buscarfactores(Integer oidMecanismo, Integer oidSede,
			Integer oidPrograma, Integer oidParticipante)
			throws DAOException;

	List<TblautFactore> buscarfactoresOd(Integer oidMecanismo, Integer oidSede, Integer oidDependencia)
			throws DAOException;

	List<TblautLecturaIndicadoresOt> buscarIndicadoresLecOd(Integer oidFactor,
			Integer oidMecanismo, Integer oidParticipante, Integer oidDependencia)
			throws DAOException;

	List<TblautLecturaIndicadore> buscarIndicadoresLec(Integer oidFactor,
			Integer oidSede, Integer oidPrograma, Integer oidMecanismo,
			Integer oidParticipante) throws DAOException;

	void editarLi(TblautLecturaIndicadore lectura)
			throws DAOException;

	void editarLiOd(TblautLecturaIndicadoresOt lectura)
			throws DAOException;

	List<Integer> buscarVigencias() throws DAOException;

	List<TblacaSedePrograma> buscarSedesprogramasParticipante(
			Integer oidParticipante, Integer vigencia)
			throws DAOException;

	List<TblautFuente> buscarFuentes()
			throws DAOException;

	List<TblautMecanismo> buscarMecanismos()
			throws DAOException;

}
