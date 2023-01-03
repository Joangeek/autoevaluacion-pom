package com.talentoHumano;

import java.util.List;

import talentoHumano.TblthCargo;
import util.DAOException;

public interface CargosDAO {

	List<TblthCargo> buscarTodos() throws DAOException;

	List<TblthCargo> buscarTodos(boolean estado)throws DAOException;

}
