/**
 * 
 */
package com.autoevaluacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import commons.util.UtilidadBean;

import talentoHumano.TblthParticipante;
import academico.TblacaSedePrograma;
import autoevaluacion.TblautFactore;
import autoevaluacion.TblautFuente;
import autoevaluacion.TblautLecturaIndicadore;
import autoevaluacion.TblautLecturaIndicadoresOt;
import autoevaluacion.TblautMecanismo;
import autoevaluacion.TblautOtrasDependencia;


import util.BOException;
import util.DAOException;

/**
 * @author EDUAR
 * 
 */
@Stateless(mappedName = "lecturaIndicadoresBO")
public class LecturaIndicadoresBOImpl implements LecturaIndicadoresBO {

	@Inject
	private LecturaIndicadoresDAO lecturaIndicadoresDAO;

	@Override
	public List<TblautLecturaIndicadore> buscarTLi()
			throws BOException {

		return null;
	}

	@Override
	public List<TblautLecturaIndicadore> buscarTLiOd()
			throws BOException {

		return null;
	}

	@Override
	public List<TblautLecturaIndicadore> buscarTodosLi(boolean estado)
			throws BOException {

		return null;
	}

	@Override
	public List<TblautLecturaIndicadore> buscarTodosLiOd(boolean estado)
			throws BOException {

		return null;
	}

	@Override
	public void crearLi(byte[] bs) throws BOException {

	}

	@Override
	public void crearLiOd(byte[] bs) throws BOException {

	}

	@Override
	public void editarLi(byte[] entity) throws BOException {
		try {
			TblautLecturaIndicadore lectura = UtilidadBean.deserialize(entity);
			lecturaIndicadoresDAO.editarLi(lectura);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void editarLiOd(byte[] entity) throws BOException {
		try {
			TblautLecturaIndicadoresOt lectura = UtilidadBean
					.deserialize(entity);
			lecturaIndicadoresDAO.editarLiOd(lectura);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminarLi(byte[] selected) throws BOException {

	}

	@Override
	public void eliminarLiOd(byte[] selected) throws BOException {

	}

	@Override
	public TblautLecturaIndicadore editarLe(byte[] entity)
			throws BOException {

		return null;
	}

	@Override
	public TblautLecturaIndicadoresOt editarLeOt(byte[] entity)
			throws BOException {

		return null;
	}

	@Override
	public TblthParticipante buscarParticipanteOid(String oidPaticipante)
			throws BOException {
		try {
			return lecturaIndicadoresDAO.buscarParticipanteOid(oidPaticipante);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautMecanismo> buscarMecanismosLiOd(Integer oidParticipante,
			Integer vigencia, Integer dependencia)
			throws BOException {
		try {
			return lecturaIndicadoresDAO.buscarMecanismosLiOd(oidParticipante,
					vigencia, dependencia);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautMecanismo> buscarMecanismosLi(Integer oidSede,
			Integer oidPrograma, Integer oidParticipante, Integer vigencia)
			throws BOException {
		try {
			return lecturaIndicadoresDAO.buscarMecanismosLi(oidSede,
					oidPrograma, oidParticipante, vigencia);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautOtrasDependencia> buscarDependencias(
			Integer idparticipante) throws BOException {
		try {
			return lecturaIndicadoresDAO.buscarDependencias(idparticipante);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautFactore> buscarfactores(Integer oidMecanismo,
			Integer oidSede, Integer oidPrograma, Integer oidParticipante)
			throws BOException {
		try {
			return lecturaIndicadoresDAO.buscarfactores(oidMecanismo, oidSede,
					oidPrograma, oidParticipante);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautFactore> buscarfactoresOd(Integer oidMecanismo,
			Integer oidParticipante, Integer oidDependencia)
			throws BOException {
		try {
			return lecturaIndicadoresDAO.buscarfactoresOd(oidMecanismo,
					oidParticipante, oidDependencia);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautLecturaIndicadoresOt> buscarIndicadoresLecOd(
			Integer oidFactor, Integer oidMecanismo, Integer oidParticipante,
			Integer oidDependencia) throws BOException {
		try {
			return lecturaIndicadoresDAO.buscarIndicadoresLecOd(oidFactor,
					oidMecanismo, oidParticipante, oidDependencia);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautLecturaIndicadore> buscarIndicadoresLec(
			Integer oidFactor, Integer oidSede, Integer oidPrograma,
			Integer oidMecanismo, Integer oidParticipante)
			throws BOException {
		try {
			return lecturaIndicadoresDAO.buscarIndicadoresLec(oidFactor,
					oidSede, oidPrograma, oidMecanismo, oidParticipante);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<Integer> buscarVigencias() throws BOException {
		try {
			return lecturaIndicadoresDAO.buscarVigencias();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblacaSedePrograma> buscarSedesprogramasParticipante(
			Integer oidParticipante, Integer vigencia)
			throws BOException {
		try {
			return lecturaIndicadoresDAO.buscarSedesprogramasParticipante(
					oidParticipante, vigencia);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautFuente> buscarFuentes() throws BOException {
		try {
			return lecturaIndicadoresDAO.buscarFuentes();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblautMecanismo> buscarMecanismos()
			throws BOException {
		try {
			return lecturaIndicadoresDAO.buscarMecanismos();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}
}
