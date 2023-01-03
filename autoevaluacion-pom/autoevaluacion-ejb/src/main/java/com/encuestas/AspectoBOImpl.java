package com.encuestas;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;



import util.BOException;
import util.DAOException;

import com.encuesta.AspectoBO;
import com.encuesta.AspectoDAO;
import commons.util.UtilidadBean;

import encuestas.TblencAspecto;
import encuestas.TblencGrupoAspecto;
import encuestas.TblencTipoAspecto;

@Stateless(mappedName = "aspectoBO")
public class AspectoBOImpl implements AspectoBO {

	@Inject
	private AspectoDAO aspectoDAO;

	@Override
	public List<TblencAspecto> buscarTodos(boolean estado)
			throws BOException {
		try {
			return aspectoDAO.buscarTodos(estado);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void crear(byte[] aspecto) throws BOException {
		try {
			TblencAspecto entity = UtilidadBean.deserialize(aspecto);
			entity.setEstado(UtilidadBean.booleano(true));
			aspectoDAO.crear(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	public void editar(byte[] selected) throws BOException {
		try {
			TblencAspecto entity = UtilidadBean.deserialize(selected);
			aspectoDAO.editar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	public TblencAspecto editarR(byte[] selected)
			throws BOException {
		try {
			TblencAspecto entity = UtilidadBean.deserialize(selected);
			return aspectoDAO.editarR(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public void eliminar(byte[] selected) throws BOException {
		try {
			TblencAspecto entity = UtilidadBean.deserialize(selected);
			aspectoDAO.eliminar(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencAspecto> buscarT() throws BOException {
		try {
			return aspectoDAO.buscarT();
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencAspecto> buscarPorGrupoAs(byte[] grupoAspectoSelected)
			throws BOException {
		try {
			TblencGrupoAspecto entity = UtilidadBean
					.deserialize(grupoAspectoSelected);
			return aspectoDAO.buscarPorGrupoAs(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

	@Override
	public List<TblencTipoAspecto> buscarTipoAspectosFiltro(byte[] serialize)
			throws BOException {
		try {
			TblencGrupoAspecto entity = UtilidadBean.deserialize(serialize);
			return aspectoDAO.buscarTipoAspectosFiltro(entity);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}
}
