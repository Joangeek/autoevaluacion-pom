package com.encuestas;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import academico.TblacaPeriodo;
import academico.TblacaPrograma;
import academico.TblacaSede;


import util.BOException;
import util.DAOException;

import com.encuesta.ProgramacionBO;
import com.encuesta.ProgramacionDAO;
import commons.util.UtilidadBean;

import encuestas.TblencProgramacionEncSedeProg;
import encuestas.TblencProgramacionEncuesta;
import encuestas.TblencTipoEvaluacion;

@Stateless(mappedName = "programacionBO")
public class ProgramacionBOImpl implements ProgramacionBO {

	@Inject
	private ProgramacionDAO programacionDAO;

	@Override
	public List<TblencProgramacionEncuesta> buscarPorEvaluacion(
			byte[] evaluacion) throws BOException {
		try {
			TblencTipoEvaluacion entity = UtilidadBean.deserialize(evaluacion);
			return programacionDAO.buscarPorEvaluacion(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblacaPrograma> buscarTodosP() throws BOException {
		try {
			return programacionDAO.buscarTodosP();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblacaPeriodo> buscarPeriodos(byte[] evaluacion)
			throws BOException {
		try {
			TblencTipoEvaluacion entity = UtilidadBean.deserialize(evaluacion);
			return programacionDAO.buscarPeriodos(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(byte[] programacion) throws BOException {
		try {
			TblencProgramacionEncuesta entity = UtilidadBean
					.deserialize(programacion);
			programacionDAO.crear(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crearPsp(byte[] programacion) throws BOException {
		try {
			TblencProgramacionEncSedeProg entity = UtilidadBean
					.deserialize(programacion);
			programacionDAO.crearPsp(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblencProgramacionEncuesta editar(byte[] programacion)
			throws BOException {
		try {
			TblencProgramacionEncuesta entity = UtilidadBean
					.deserialize(programacion);
			return programacionDAO.editar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblencProgramacionEncSedeProg editarPsp(byte[] programacion)
			throws BOException {
		try {
			TblencProgramacionEncSedeProg entity = UtilidadBean
					.deserialize(programacion);
			return programacionDAO.editarPsp(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(byte[] programacion) throws BOException {
		try {
			TblencProgramacionEncuesta entity = UtilidadBean
					.deserialize(programacion);
			programacionDAO.eliminar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminarPsp(byte[] programacion)
			throws BOException {
		try {
			TblencProgramacionEncSedeProg entity = UtilidadBean
					.deserialize(programacion);
			programacionDAO.eliminarPsp(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Integer contarSedesProgramasConfig(byte[] oid)
			throws BOException {
		try {
			TblencProgramacionEncuesta entity = UtilidadBean.deserialize(oid);
			return programacionDAO.contarSedesProgramasConfig(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblacaSede> buscarSedesPorSpIN(byte[] serialize)
			throws BOException {
		try {
			TblencProgramacionEncuesta entity = UtilidadBean
					.deserialize(serialize);
			return programacionDAO.buscarSedesPorSpIN(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblacaPrograma> buscarProgramasPorSpIN(byte[] serialize)
			throws BOException {
		try {
			TblencProgramacionEncuesta entity = UtilidadBean
					.deserialize(serialize);
			return programacionDAO.buscarProgramasPorSpIN(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencProgramacionEncSedeProg> sedesProgramasConfig(
			byte[] serialize) throws BOException {
		try {
			TblencProgramacionEncuesta entity = UtilidadBean
					.deserialize(serialize);
			return programacionDAO.sedesProgramasConfig(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}
}
