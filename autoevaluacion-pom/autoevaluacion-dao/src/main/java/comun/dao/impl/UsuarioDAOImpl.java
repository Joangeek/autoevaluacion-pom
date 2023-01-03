package comun.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.comun.dao.UsuarioDAO;

import commons.exceptions.AutoevaluacionDAOException;
import comun.Tblempleadore;
import academico.TblestEstudiante;
import academico.TblestEstudiantePrograma;
import encuestas.TblencTipoAspecto;
import gestion.Parametros;
import gestion.Usuario;
import gestion.UsuarioGrupo;

@SuppressWarnings("unused")
public class UsuarioDAOImpl implements UsuarioDAO {

	@PersistenceContext(unitName = "autoevaluacion-dm")
	private EntityManager em;
	static final String WHERE = " WHERE ";
	static final String AND = " AND ";

	public Usuario buscarCodigo(String codigo)
			throws AutoevaluacionDAOException {
		Query q = null;
		q = em.createNamedQuery("Usuario.findLogin").setParameter("codigo",
				codigo);
		try {
			return (Usuario) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public UsuarioGrupo validarAcceso(String uname, String tipoU)
			throws AutoevaluacionDAOException {
		Query q = null;
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ug FROM ");
		sql.append(UsuarioGrupo.class.getSimpleName() + " ug ");
		sql.append(" join fetch ug.usuario u join fetch u.idparticipante p join fetch ug.grupo g ");
		sql.append(WHERE);
		sql.append(" u.usuLogin=:login ");
		sql.append(AND);
		sql.append(" g.gruId=:id");
		sql.append(AND);
		sql.append(" u.estado=1");
		//System.out.println(sql.toString());
		//System.out.println(uname);
		//System.out.println(tipoU);

		q = em.createQuery(sql.toString());
		q.setParameter("login", uname);
		q.setParameter("id", tipoU);

		try {
			return (UsuarioGrupo) q.getSingleResult();
		} catch (NoResultException e) {
			// e.printStackTrace();
			return null;
		} catch (NonUniqueResultException e) {
			// e.printStackTrace();
			return (UsuarioGrupo) q.getResultList().get(0);
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}

	}

	@Override
	public TblestEstudiante validarAcceso(String uname)
			throws AutoevaluacionDAOException {
		Query q = null;
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT est FROM ");
		sql.append(TblestEstudiante.class.getSimpleName());
		sql.append(" est ");
		sql.append(WHERE);
		sql.append(" est.codigoUnico=:login ");
		q = em.createQuery(sql.toString());
		q.setParameter("login", uname);

		try {
			return (TblestEstudiante) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public TblestEstudiante validarAcceso(String uname, Integer estado)
			throws AutoevaluacionDAOException {
		StringBuilder sql = new StringBuilder();
		Query q = null;

		sql.append("SELECT est FROM ");
		sql.append(TblestEstudiante.class.getSimpleName());
		sql.append(" est ");
		sql.append(WHERE);
		sql.append("est.codigoUnico=:login ");
		sql.append(AND);
		sql.append(" est IN ( SELECT espro.tblestEstudiante FROM  ");
		sql.append(TblestEstudiantePrograma.class.getSimpleName());
		sql.append(" espro WHERE espro.tblestEstudiante=est AND espro.estado IN(:estados) ) ");

		q = em.createQuery(sql.toString());
		q.setParameter("login", uname);
		q.setParameter("estados", estado);

		try {
			return (TblestEstudiante) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			return (TblestEstudiante) q.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Tblempleadore validarAccesEmpleador(String user)
			throws AutoevaluacionDAOException {
		StringBuilder sql = new StringBuilder();
		Query q = null;
		Integer estado=1;

		sql.append("SELECT est FROM ");
		sql.append(Tblempleadore.class.getSimpleName());
		sql.append(" est ");
		sql.append(WHERE);
		sql.append("est.nit=:login ");
		sql.append(AND);
		sql.append("est.estado=:estado ");

		q = em.createQuery(sql.toString());
		q.setParameter("login", user);
		q.setParameter("estado", estado);

		try {
			return (Tblempleadore) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			return (Tblempleadore) q.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
