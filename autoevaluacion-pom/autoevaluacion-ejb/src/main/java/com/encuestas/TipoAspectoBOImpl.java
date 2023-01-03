package com.encuestas;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import academico.TblacaPrograma;
import academico.TblacaSede;


import util.BOException;
import util.DAOException;

import com.encuesta.TipoAspectoBO;
import com.encuesta.TipoAspectoDAO;
import commons.util.UtilidadBean;

import encuestas.TblencAspecto;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencOpcione;
import encuestas.TblencTipoAspecto;
import encuestas.TblencTipoEvaluacion;
import encuestas.util.ReporteEncuestaResultados;
import encuestas.util.ReporteEncuestaResultadosFinal;
import encuestas.util.ResultadoEncuesta;

@Stateless(mappedName = "tipoAspectoBO")
public class TipoAspectoBOImpl implements TipoAspectoBO {

	static Logger LOGGER = Logger.getLogger(TipoAspectoBOImpl.class
			.getSimpleName());

	@Inject
	private TipoAspectoDAO tipoAspectoDAO;

	@Override
	public List<TblencTipoAspecto> buscarTodos()
			throws BOException {
		try {
			return tipoAspectoDAO.buscarTodos();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencTipoAspecto> buscarTodos(boolean estado)
			throws BOException {
		try {
			return tipoAspectoDAO.buscarTodos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(TblencTipoAspecto tipoAspecto)
			throws BOException {
		try {
			tipoAspectoDAO.crear(tipoAspecto);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	public void editar(TblencTipoAspecto selected)
			throws BOException {
		try {
			if (selected.getEstado() == 1) {
				selected.setEstado(0);
			} else {
				selected.setEstado(1);
			}
			tipoAspectoDAO.editar(selected);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	public TblencTipoAspecto editarR(TblencTipoAspecto selected)
			throws BOException {
		try {

			return tipoAspectoDAO.editarR(selected);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(TblencTipoAspecto selected)
			throws BOException {
		try {
			tipoAspectoDAO.eliminar(selected);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencAspecto> buscarPorEncuesta(byte[] bs)
			throws BOException {
		TblencTipoEvaluacion entity = UtilidadBean.deserialize(bs);
		try {
			return tipoAspectoDAO.buscarPorEncuesta(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencOpcione> buscarOpcAspecto(byte[] serialize)
			throws BOException {
		TblencTipoAspecto entity = UtilidadBean.deserialize(serialize);

		try {
			return tipoAspectoDAO.buscarOpcAspecto(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<ResultadoEncuesta> verRespuestas(byte[] serialize)
			throws BOException {
		Map<TblencAspecto, String> mapa = UtilidadBean.deserialize(serialize);

		try {
			return tipoAspectoDAO.verRespuestas(mapa);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencGrupoAspecto> gruposPorEncuesta(byte[] serialize)
			throws BOException {
		TblencTipoEvaluacion entity = UtilidadBean.deserialize(serialize);
		try {
			return tipoAspectoDAO.gruposPorEncuesta(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	public List<TblencAspecto> buscarPorGrupo(byte[] serialize)
			throws BOException {
		TblencGrupoAspecto entity = UtilidadBean.deserialize(serialize);
		try {
			return tipoAspectoDAO.buscarPorGrupo(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public ReporteEncuestaResultadosFinal buscarOpcAspecto(byte[] serialize,
			Integer oidTipoEval, Integer oidPeriodo)
			throws BOException {
		TblencAspecto entity = UtilidadBean.deserialize(serialize);
		try {

			return armarResultado(tipoAspectoDAO.buscarOpcAspecto(entity,
					oidTipoEval, oidPeriodo));
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	private ReporteEncuestaResultadosFinal armarResultado(
			List<ReporteEncuestaResultados> lista) {
		Integer total = 0;
		/**
		 * ciclo que obtiene el total de resultados encontrados a nivel de
		 * respuestas a el aspecto
		 */
		// int i = 0;

		for (ReporteEncuestaResultados val : lista) {
			// i++;
			total += val.getCantidad();

		}

		// LOGGER.info(i+"-****************************************-"+total);
		return new ReporteEncuestaResultadosFinal(total, lista);

	}

	@Override
	public ReporteEncuestaResultadosFinal buscarOpcAspecto(byte[] serialize,
			Integer oidTipoEval, Integer oidPeriodo, byte[] sede,
			byte[] programa) throws BOException {
		TblencAspecto entity = UtilidadBean.deserialize(serialize);
		TblacaPrograma entityPrograma = UtilidadBean.deserialize(programa);
		TblacaSede entitySede = UtilidadBean.deserialize(sede);
		Integer oidSede = 0;
		Integer oidPrograma = 0;

		if (UtilidadBean.validaNulos(entitySede)) {
			oidSede = entitySede.getOid();
		}
		if (UtilidadBean.validaNulos(entityPrograma)) {
			oidPrograma = entityPrograma.getOid();
		}
		try {

			return armarResultado(tipoAspectoDAO.buscarOpcAspecto(entity,
					oidTipoEval, oidPeriodo, oidSede, oidPrograma));
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}
}
