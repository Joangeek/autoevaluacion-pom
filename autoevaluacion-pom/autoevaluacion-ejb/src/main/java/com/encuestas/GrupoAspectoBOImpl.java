package com.encuestas;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;



import util.BOException;
import util.DAOException;

import com.encuesta.GrupoAspectoBO;
import com.encuesta.GrupoAspectoDAO;
import commons.util.UtilidadBean;

import encuestas.TblencGrupoAspecto;
import encuestas.TblencTipoEvaluacion;

@Stateless(mappedName = "grupoAspectoBO")
public class GrupoAspectoBOImpl implements GrupoAspectoBO {

	@Inject
	private GrupoAspectoDAO grupoAspectoDAO;

	@Override
	public List<TblencGrupoAspecto> buscarTodos(boolean estado)
			throws BOException {
		try {
			return grupoAspectoDAO.buscarTodos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(TblencGrupoAspecto aspecto)
			throws BOException {
		try {
			grupoAspectoDAO.crear(aspecto);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	public TblencGrupoAspecto editar(byte[] serialize)
			throws BOException {
		try {
			TblencGrupoAspecto entity= UtilidadBean.deserialize(serialize);
			return grupoAspectoDAO.editar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}


	@Override
	public void eliminar(TblencGrupoAspecto selected)
			throws BOException {
		try {
			grupoAspectoDAO.eliminar(selected);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencGrupoAspecto> buscarT()
			throws BOException {
		try {
			return grupoAspectoDAO.buscarT();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(byte[] serialize) throws BOException {
		try {
			TblencGrupoAspecto entity= UtilidadBean.deserialize(serialize);
			grupoAspectoDAO.eliminar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencGrupoAspecto> buscarPorTipoEval(
			TblencTipoEvaluacion tipoEval) throws BOException {
		try {
			return grupoAspectoDAO.buscarPorTipoEval(tipoEval);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

}
