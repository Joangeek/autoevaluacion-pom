package com.academico;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import commons.util.UtilidadBean;

import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblacaSedePrograma;
import academico.TblestEstudiante;
import academico.TblestEstudiantePrograma;
import util.BOException;
import util.DAOException;

@Stateless(mappedName = "sedeProgramaBO")
public class SedeProgramaBOImpl implements SedeProgramaBO {

	@Inject
	private SedeprogramaDAO sedeProgramaDAO;

	@Override
	public List<TblacaSede> buscarSedes() throws BOException {
		try {
			return sedeProgramaDAO.buscarSedes();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminarSede(byte[] selected) throws BOException {
		try {
			TblacaSede entity = UtilidadBean.deserialize(selected);
			sedeProgramaDAO.eliminarSede(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}

	}

	@Override
	public void crearSede(byte[] sede) throws BOException {
		try {
			TblacaSede entity = UtilidadBean.deserialize(sede);
			sedeProgramaDAO.crearSede(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void editarSede(byte[] selected) throws BOException {
		try {
			TblacaSede entity = UtilidadBean.deserialize(selected);
			sedeProgramaDAO.editarSede(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblacaPrograma> buscarProgramas()
			throws BOException {
		try {
			return sedeProgramaDAO.buscarProgramas();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminarPrograma(byte[] selected)
			throws BOException {
		try {
			TblacaPrograma entity = UtilidadBean.deserialize(selected);
			sedeProgramaDAO.eliminarPrograma(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crearPrograma(byte[] programa) throws BOException {
		try {
			TblacaPrograma entity = UtilidadBean.deserialize(programa);
			sedeProgramaDAO.crearPrograma(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void editarPrograma(byte[] selected)
			throws BOException {
		try {
			TblacaPrograma entity = UtilidadBean.deserialize(selected);
			sedeProgramaDAO.editarPrograma(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminarSedePrograma(byte[] selected)
			throws BOException {
		try {
			TblacaSedePrograma entity = UtilidadBean.deserialize(selected);
			sedeProgramaDAO.eliminarSedePrograma(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crearSedePrograma(byte[] selected)
			throws BOException {
		try {
			TblacaSedePrograma entity = UtilidadBean.deserialize(selected);
			sedeProgramaDAO.crearSedePrograma(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void editarSedePrograma(byte[] selected)
			throws BOException {
		try {
			TblacaSedePrograma entity = UtilidadBean.deserialize(selected);
			sedeProgramaDAO.editarSedePrograma(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblacaPrograma> buscarProgramasActivos(boolean estado)
			throws BOException {
		try {
			return sedeProgramaDAO.buscarProgramasActivos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblacaSede> buscarSedesActivas(boolean estado)
			throws BOException {
		try {
			return sedeProgramaDAO.buscarSedesActivas(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblacaSedePrograma> buscarPorSede(byte[] serialize)
			throws BOException {
		try {
			TblacaSede entity = UtilidadBean.deserialize(serialize);
			return sedeProgramaDAO.buscarPorSede(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblacaSede> buscarSedesEstadoPorSp(boolean estado)
			throws BOException {
		try {
			return sedeProgramaDAO.buscarSedesEstadoPorSp(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblacaSedePrograma> buscarProgramasEstadoSp(Integer serialize,
			boolean estado) throws BOException {
		try {
			//TblacaSede entity = UtilidadBean.deserialize(serialize);
			return sedeProgramaDAO.buscarProgramasEstadoSp(serialize, estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblestEstudiantePrograma> buscarPorEstudiante(byte[] estudiante)
			throws BOException {
		try {
			TblestEstudiante entity = UtilidadBean.deserialize(estudiante);
			return sedeProgramaDAO.buscarPorEstudiante(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblestEstudiantePrograma> buscarPorEstudiante(
			byte[] estudiante, Integer estados) throws BOException {
		try {
			TblestEstudiante entity = UtilidadBean.deserialize(estudiante);
			return sedeProgramaDAO.buscarPorEstudiante(entity,estados);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}
	
	

}
