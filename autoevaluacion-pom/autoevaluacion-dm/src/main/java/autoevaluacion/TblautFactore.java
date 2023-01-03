package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the tblaut_factores database table.
 * 
 */
@Entity
@Table(name="tblaut_factores",schema="academico")
@NamedQuery(name="TblautFactore.findAll", query="SELECT t FROM TblautFactore t")
public class TblautFactore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLAUT_FACTORES_OID_GENERATOR",sequenceName="ACADEMICO.TBLAUT_FACTORES_OID_SEQ", allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLAUT_FACTORES_OID_GENERATOR")
	private Integer oid;

	private String nombre;
	
	private Integer orden;
	
	private Integer estado;
	
	private String usuario;

	//bi-directional many-to-one association to TblautCaracteristica
	@OneToMany(mappedBy="tblautFactore")
	private List<TblautCaracteristica> tblautCaracteristicas;

	//bi-directional many-to-one association to TblautFactorVigencia
	@OneToMany(mappedBy="tblautFactore")
	private List<TblautFactorVigencia> tblautFactorVigencias;

	public TblautFactore() {
	}

	public TblautFactore(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<TblautCaracteristica> getTblautCaracteristicas() {
		return this.tblautCaracteristicas;
	}

	public void setTblautCaracteristicas(List<TblautCaracteristica> tblautCaracteristicas) {
		this.tblautCaracteristicas = tblautCaracteristicas;
	}

	public TblautCaracteristica addTblautCaracteristica(TblautCaracteristica tblautCaracteristica) {
		getTblautCaracteristicas().add(tblautCaracteristica);
		tblautCaracteristica.setTblautFactore(this);

		return tblautCaracteristica;
	}

	public TblautCaracteristica removeTblautCaracteristica(TblautCaracteristica tblautCaracteristica) {
		getTblautCaracteristicas().remove(tblautCaracteristica);
		tblautCaracteristica.setTblautFactore(null);

		return tblautCaracteristica;
	}

	public List<TblautFactorVigencia> getTblautFactorVigencias() {
		return this.tblautFactorVigencias;
	}

	public void setTblautFactorVigencias(List<TblautFactorVigencia> tblautFactorVigencias) {
		this.tblautFactorVigencias = tblautFactorVigencias;
	}

	public TblautFactorVigencia addTblautFactorVigencia(TblautFactorVigencia tblautFactorVigencia) {
		getTblautFactorVigencias().add(tblautFactorVigencia);
		tblautFactorVigencia.setTblautFactore(this);

		return tblautFactorVigencia;
	}

	public TblautFactorVigencia removeTblautFactorVigencia(TblautFactorVigencia tblautFactorVigencia) {
		getTblautFactorVigencias().remove(tblautFactorVigencia);
		tblautFactorVigencia.setTblautFactore(null);

		return tblautFactorVigencia;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}