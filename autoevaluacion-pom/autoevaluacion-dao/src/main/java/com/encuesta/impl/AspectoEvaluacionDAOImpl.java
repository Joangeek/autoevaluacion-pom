/**
 * 
 */
package com.encuesta.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.encuesta.AspectoEvaluacionDAO;

/**
 * @author EDUAR
 * 
 */
public class AspectoEvaluacionDAOImpl implements AspectoEvaluacionDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;
}
