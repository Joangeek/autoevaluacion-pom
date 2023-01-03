package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the tblaut_fuentes database table.
 * 
 */
@Entity
@Table(name="tblaut_fuentes",schema="academico")
@NamedQuery(name="TblautFuente.findAll", query="SELECT t FROM TblautFuente t")
public class TblautFuente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLAUT_FUENTES_OID_GENERATOR",sequenceName="ACADEMICO.TBLAUT_FUENTES_OID_SEQ",allocationSize=1  )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLAUT_FUENTES_OID_GENERATOR")
	private Integer oid;

	private Integer estado;

	private String nombre;

	private String usuario;
	
	//bi-directional many-to-one association to TblautIndicadorFuente
	@OneToMany(mappedBy="tblautFuente")
	private List<TblautIndicadorFuente> tblautIndicadorFuentes;

	public TblautFuente() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<TblautIndicadorFuente> getTblautIndicadorFuentes() {
		return this.tblautIndicadorFuentes;
	}

	public void setTblautIndicadorFuentes(List<TblautIndicadorFuente> tblautIndicadorFuentes) {
		this.tblautIndicadorFuentes = tblautIndicadorFuentes;
	}

	public TblautIndicadorFuente addTblautIndicadorFuente(TblautIndicadorFuente tblautIndicadorFuente) {
		getTblautIndicadorFuentes().add(tblautIndicadorFuente);
		tblautIndicadorFuente.setTblautFuente(this);

		return tblautIndicadorFuente;
	}

	public TblautIndicadorFuente removeTblautIndicadorFuente(TblautIndicadorFuente tblautIndicadorFuente) {
		getTblautIndicadorFuentes().remove(tblautIndicadorFuente);
		tblautIndicadorFuente.setTblautFuente(null);

		return tblautIndicadorFuente;
	}
	
	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}