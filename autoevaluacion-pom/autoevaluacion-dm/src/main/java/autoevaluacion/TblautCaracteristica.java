package autoevaluacion;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tblaut_caracteristicas database table.
 * 
 */
@Entity
@Table(name="tblaut_caracteristicas",schema="academico")
@NamedQuery(name="TblautCaracteristica.findAll", query="SELECT t FROM TblautCaracteristica t")
public class TblautCaracteristica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLAUT_CARACTERISTICAS_OID_GENERATOR",sequenceName="ACADEMICO.TBLAUT_CARACTERISTICAS_OID_SEQ",allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLAUT_CARACTERISTICAS_OID_GENERATOR")
	private Integer oid;

	private String caracteristica;

	private Integer estado;

	private String resumen;
	
	private Integer orden;
	
	private String usuario;
	
	private String codigo;

	//bi-directional many-to-one association to TblautCaracteristicaVigencia
	@OneToMany(mappedBy="tblautCaracteristica")
	private List<TblautCaracteristicaVigencia> tblautCaracteristicaVigencias;

	//bi-directional many-to-one association to TblautFactore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idfactor")
	private TblautFactore tblautFactore;

	public TblautCaracteristica() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCaracteristica() {
		return this.caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getResumen() {
		return this.resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public List<TblautCaracteristicaVigencia> getTblautCaracteristicaVigencias() {
		return this.tblautCaracteristicaVigencias;
	}

	public void setTblautCaracteristicaVigencias(List<TblautCaracteristicaVigencia> tblautCaracteristicaVigencias) {
		this.tblautCaracteristicaVigencias = tblautCaracteristicaVigencias;
	}

	public TblautCaracteristicaVigencia addTblautCaracteristicaVigencia(TblautCaracteristicaVigencia tblautCaracteristicaVigencia) {
		getTblautCaracteristicaVigencias().add(tblautCaracteristicaVigencia);
		tblautCaracteristicaVigencia.setTblautCaracteristica(this);

		return tblautCaracteristicaVigencia;
	}

	public TblautCaracteristicaVigencia removeTblautCaracteristicaVigencia(TblautCaracteristicaVigencia tblautCaracteristicaVigencia) {
		getTblautCaracteristicaVigencias().remove(tblautCaracteristicaVigencia);
		tblautCaracteristicaVigencia.setTblautCaracteristica(null);

		return tblautCaracteristicaVigencia;
	}

	public TblautFactore getTblautFactore() {
		return this.tblautFactore;
	}

	public void setTblautFactore(TblautFactore tblautFactore) {
		this.tblautFactore = tblautFactore;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}