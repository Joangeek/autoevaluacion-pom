
package com.academico;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import academico.TblacaMatriculado;
import util.BOException;
import util.DAOException;

/**
 * @author EDUAR
 * 
 */
@Stateless(mappedName = "matriculadoBO")
public class MatriculadoBOImpl implements MatriculadoBO {

	@Inject
	private MatriculadoDAO matriculadoDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.academico.MatriculadoBO#buscarTodos()
	 */
	@Override
	public List<TblacaMatriculado> buscarTodos()
			throws BOException {
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.academico.MatriculadoBO#crear(byte[])
	 */
	@Override
	public void crear(byte[] periodo) throws BOException {
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.academico.MatriculadoBO#editar(byte[])
	 */
	@Override
	public void editar(byte[] periodo) throws BOException {
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.academico.MatriculadoBO#eliminar(byte[])
	 */
	@Override
	public void eliminar(byte[] periodo) throws BOException {
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.academico.MatriculadoBO#buscarTodosLimite(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public List<TblacaMatriculado> buscarTodosLimite(Integer limit,
			Integer offSet) throws BOException {
		
		return null;
	}

	@Override
	public TblacaMatriculado buscarEstado(boolean estado)
			throws BOException {
		
		return null;
	}

	@Override
	public boolean validarEnPeriodo(Integer idEp, Integer periodoOid)
			throws BOException {
		try {
			return matriculadoDAO.validarEnPeriodo(idEp, periodoOid);
		} catch (DAOException e) {
			throw new BOException(e.getMessage(), e);
		}
	}

}
