package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the tblaut_mecanismos database table.
 * 
 */
@Entity
@Table(name="tblaut_mecanismos",schema="academico")
@NamedQuery(name="TblautMecanismo.findAll", query="SELECT t FROM TblautMecanismo t")
public class TblautMecanismo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLAUT_MECANISMOS_OID_GENERATOR",sequenceName="ACADEMICO.TBLAUT_MECANISMOS_OID_SEQ", allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLAUT_MECANISMOS_OID_GENERATOR")
	private Integer oid;

	private String codigo;

	private String descripcion;

	private Integer estado;
	
	private String ayuda;
	
	private String usuario;

	//bi-directional many-to-one association to TblautIndicadorFuente
	@OneToMany(mappedBy="tblautMecanismo")
	private List<TblautIndicadorFuente> tblautIndicadorFuentes;

	public TblautMecanismo(Integer oid, String codigo, String descripcion,
			Integer estado, String escala) {
		this.oid = oid;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.estado = estado;
		this.ayuda = escala;
	}

	public TblautMecanismo() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo.trim();
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public List<TblautIndicadorFuente> getTblautIndicadorFuentes() {
		return this.tblautIndicadorFuentes;
	}

	public void setTblautIndicadorFuentes(List<TblautIndicadorFuente> tblautIndicadorFuentes) {
		this.tblautIndicadorFuentes = tblautIndicadorFuentes;
	}

	public TblautIndicadorFuente addTblautIndicadorFuente(TblautIndicadorFuente tblautIndicadorFuente) {
		getTblautIndicadorFuentes().add(tblautIndicadorFuente);
		tblautIndicadorFuente.setTblautMecanismo(this);

		return tblautIndicadorFuente;
	}

	public TblautIndicadorFuente removeTblautIndicadorFuente(TblautIndicadorFuente tblautIndicadorFuente) {
		getTblautIndicadorFuentes().remove(tblautIndicadorFuente);
		tblautIndicadorFuente.setTblautMecanismo(null);

		return tblautIndicadorFuente;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getAyuda() {
		return ayuda;
	}

	public void setAyuda(String ayuda) {
		this.ayuda = ayuda;
	}

}