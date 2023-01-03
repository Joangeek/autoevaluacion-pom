package com.encuestas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import talentoHumano.TblthParticipante;
import academico.TblacaPeriodo;
import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblestEstudiante;


import util.BOException;
import util.DAOException;

import com.encuesta.TipoEvaluacionBO;
import com.encuesta.TipoEvaluacionDAO;

import commons.util.UtilidadBean;
import comun.Tblempleadore;
import encuestas.TblencDirigidoa;
import encuestas.TblencEvaluacionInstitucion;
import encuestas.TblencModuloTipoEvaluacion;
import encuestas.TblencProgramacionEncuesta;
import encuestas.TblencTipoEvaluacion;
import encuestas.util.ReporteEncuestaRealizadas;

@Stateless(mappedName = "tipoEvaluacionBO")
public class TipoEvaluacionBOImpl implements TipoEvaluacionBO {

	@Inject
	private TipoEvaluacionDAO tipoEvaluacionDAO;

	@SuppressWarnings("unused")
	private ReporteEncuestaRealizadas reporteEncuestaRealizadas;

	@Override
	public List<TblencTipoEvaluacion> buscarTodos(boolean estado)
			throws BOException {
		try {
			return tipoEvaluacionDAO.buscarTodos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(TblencTipoEvaluacion tipoAspecto)
			throws BOException {
		try {
			tipoEvaluacionDAO.crear(tipoAspecto);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	public void editar(TblencTipoEvaluacion selected)
			throws BOException {
		try {
			if (selected.getEstado() == 1) {
				selected.setEstado(0);
			} else {
				selected.setEstado(1);
			}
			tipoEvaluacionDAO.editar(selected);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	public TblencTipoEvaluacion editarR(TblencTipoEvaluacion selected)
			throws BOException {
		try {

			return tipoEvaluacionDAO.editarR(selected);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(TblencTipoEvaluacion selected)
			throws BOException {
		try {
			tipoEvaluacionDAO.eliminar(selected);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencTipoEvaluacion> buscarT()
			throws BOException {
		try {
			return tipoEvaluacionDAO.buscarT();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencDirigidoa> buscarDirigidoAs()
			throws BOException {
		try {
			return tipoEvaluacionDAO.buscarDirigidoAs();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencModuloTipoEvaluacion> buscarModulosAs()
			throws BOException {
		try {
			return tipoEvaluacionDAO.buscarModulosAs();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencDirigidoa> buscarTDirigidoA(boolean val)
			throws BOException {
		try {
			return tipoEvaluacionDAO.buscarTDirigidoA(val);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencModuloTipoEvaluacion> buscarTModulos(boolean val)
			throws BOException {
		try {
			return tipoEvaluacionDAO.buscarTModulos(val);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Integer eliminar(byte[] serialize) throws BOException {
		try {
			TblencTipoEvaluacion entity = UtilidadBean.deserialize(serialize);
			return tipoEvaluacionDAO.eliminar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencTipoEvaluacion> buscarID(Integer oid)
			throws BOException {
		try {
			return tipoEvaluacionDAO.buscarID(oid);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public TblencDirigidoa buscarDirigidoA(String descripcion)
			throws BOException {
		try {
			return tipoEvaluacionDAO.buscarDirigidoA(descripcion);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	/**
	 * Consulta las encuestas por a quiene sestá dirigida y por vigenca
	 */
	@Override
	public List<TblencProgramacionEncuesta> buscarEncuesta(Date fechaHoy,
			Integer dirigido) throws BOException {
		try {
			return tipoEvaluacionDAO.buscarEncuesta(fechaHoy, dirigido);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencTipoEvaluacion> buscarEstadoByModulo(boolean estado,
			int modulo) throws BOException {
		try {
			return tipoEvaluacionDAO.buscarEstadoByModulo(estado, modulo);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<ReporteEncuestaRealizadas> buscarParticipantes(
			byte[] programacion, byte[] periodo, byte[] sed)
			throws BOException {
		try {
			TblencTipoEvaluacion prog = UtilidadBean.deserialize(programacion);
			TblacaPeriodo peri = UtilidadBean.deserialize(periodo);
			TblacaSede sede = UtilidadBean.deserialize(sed);

			return formarReporteRealizados(
					tipoEvaluacionDAO.buscarParticipantes(prog, peri.getOid(),
							sede), false);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<ReporteEncuestaRealizadas> buscarParticipantes(
			byte[] programacion, byte[] periodo, byte[] sed, byte[] program)
			throws BOException {
		try {
			TblencTipoEvaluacion prog = UtilidadBean.deserialize(programacion);
			TblacaPeriodo peri = UtilidadBean.deserialize(periodo);
			TblacaSede sede = UtilidadBean.deserialize(sed);
			TblacaPrograma programa = UtilidadBean.deserialize(program);

			return formarReporteRealizados(
					tipoEvaluacionDAO.buscarParticipantes(prog, peri.getOid(),
							sede, programa), false);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<ReporteEncuestaRealizadas> buscarEstudiantes(
			byte[] programacion, byte[] periodo, byte[] sed, byte[] pro)
			throws BOException {
		try {
			TblencTipoEvaluacion prog = UtilidadBean.deserialize(programacion);
			TblacaPeriodo peri = UtilidadBean.deserialize(periodo);
			TblacaSede sede = UtilidadBean.deserialize(sed);
			TblacaPrograma programa = UtilidadBean.deserialize(pro);

			return formarReporteRealizados(tipoEvaluacionDAO.buscarEstudiantes(
					prog, peri.getOid(), sede, programa), true);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	private List<ReporteEncuestaRealizadas> formarReporteRealizados(
			List<TblencEvaluacionInstitucion> list, boolean tip) {
		List<ReporteEncuestaRealizadas> newList = new ArrayList<ReporteEncuestaRealizadas>();
		int i = 0;
		for (TblencEvaluacionInstitucion val : list) {
			ReporteEncuestaRealizadas entity = new ReporteEncuestaRealizadas();

			if (tip)
				entity.setPersona(val.getTblestEstudiante().getNombres());
			else
				entity.setPersona(val.getTblthParticipante().getNombre());

			if (tip)
				entity.setIdentificacion(val.getTblestEstudiante()
						.getCodigoUnico());
			else
				entity.setIdentificacion(val.getTblthParticipante()
						.getCodigoUnico());
			if (tip)
				entity.setCelular(val.getTblestEstudiante().getCelular());
			else
				entity.setCelular(val.getTblthParticipante().getCelular());
			if (tip)
				entity.setEmail(val.getTblestEstudiante().getEmail());
			else
				entity.setEmail(val.getTblthParticipante().getEmailInst());

			if (tip) {
				entity.setSede(val.getTblacaSede().getNombre());
				entity.setPrograma(val.getTblacaPrograma().getNombre());
			} else {
				if (UtilidadBean.validaNulos(val.getTblacaSede())) {
					entity.setSede(val.getTblacaSede().getNombre());
				}
				if (UtilidadBean.validaNulos(val.getTblacaPrograma())) {
					entity.setPrograma(val.getTblacaPrograma().getNombre());
				}
			}
			entity.setFecha(val.getFecha().toString());
			newList.add(i, entity);

			i++;
		}
		return newList;

	}

	@Override
	public Integer buscarEncuestaRealizada(byte[] programacionEncuesta,
			byte[] participante) throws BOException {
		try {
			TblencProgramacionEncuesta prog = UtilidadBean
					.deserialize(programacionEncuesta);
			TblthParticipante part = UtilidadBean.deserialize(participante);

			return tipoEvaluacionDAO.buscarEncuestaRealizada(prog, part);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Integer buscarEncuestaRealizadaEstudiante(
			byte[] programacionEncuesta, byte[] participante, Integer sede,
			Integer programa) throws BOException {
		try {
			TblencProgramacionEncuesta prog = UtilidadBean
					.deserialize(programacionEncuesta);
			TblestEstudiante part = UtilidadBean.deserialize(participante);

			return tipoEvaluacionDAO.buscarEncuestaRealizadaEstudiante(prog,
					part, sede, programa);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public Integer EncuestaRealizadaEmpleador(byte[] tipoEncuesta, byte[] emple)
			throws BOException {
		try {
			TblencProgramacionEncuesta prog = UtilidadBean
					.deserialize(tipoEncuesta);
			Tblempleadore empleador = UtilidadBean.deserialize(emple);

			return tipoEvaluacionDAO
					.EncuestaRealizadaEmpleador(prog, empleador);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<ReporteEncuestaRealizadas> buscarEmpleadores(
			byte[] programacion, byte[] periodo, byte[] sed, byte[] pro)
			throws BOException {
		try {
			TblencTipoEvaluacion prog = UtilidadBean.deserialize(programacion);
			TblacaPeriodo peri = UtilidadBean.deserialize(periodo);
			TblacaSede sede = UtilidadBean.deserialize(sed);
			TblacaPrograma programa = UtilidadBean.deserialize(pro);

			return formarReporteRealizados(tipoEvaluacionDAO.buscarEmpleador(
					prog, peri.getOid(), sede, programa));
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	private List<ReporteEncuestaRealizadas> formarReporteRealizados(
			List<TblencEvaluacionInstitucion> list) {
		List<ReporteEncuestaRealizadas> newList = new ArrayList<ReporteEncuestaRealizadas>();
		int i = 0;
		for (TblencEvaluacionInstitucion val : list) {
			ReporteEncuestaRealizadas entity = new ReporteEncuestaRealizadas();

			entity.setPersona(val.getTblempleadore().getNombreEmpresa());
			entity.setIdentificacion(val.getTblempleadore().getNit());
			entity.setCelular(val.getTblempleadore().getTelefono());
			entity.setEmail(val.getTblempleadore().getCorreo());
			entity.setSede(val.getTblacaSede().getNombre());
			entity.setPrograma(val.getTblacaPrograma().getNombre());

			entity.setFecha(val.getFecha().toString());
			newList.add(i, entity);

			i++;
		}
		return newList;

	}
}
