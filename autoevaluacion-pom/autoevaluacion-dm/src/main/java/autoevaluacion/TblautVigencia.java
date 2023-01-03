package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tblaut_vigencias database table.
 * 
 */
@Entity
@Table(name="tblaut_vigencias",schema="academico")
@NamedQuery(name="TblautVigencia.findAll", query="SELECT t FROM TblautVigencia t")
public class TblautVigencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLAUT_VIGENCIAS_OID_GENERATOR", sequenceName="ACADEMICO.TBLAUT_VIGENCIAS_OID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLAUT_VIGENCIAS_OID_GENERATOR")
	private Integer oid;

	@Column(name="anio_vigencia")
	private Integer anioVigencia;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_apertura")
	private Date fechaApertura;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_cierre")
	private Date fechaCierre;

	private String usuario;

	//bi-directional many-to-one association to TblautCaracteristicaVigencia
	@OneToMany(mappedBy="tblautVigencia")
	private List<TblautCaracteristicaVigencia> tblautCaracteristicaVigencias;

	//bi-directional many-to-one association to TblautFactorVigencia
	@OneToMany(mappedBy="tblautVigencia")
	private List<TblautFactorVigencia> tblautFactorVigencias;

	//bi-directional many-to-one association to TblautIndicadorVigencia
	@OneToMany(mappedBy="tblautVigencia")
	private List<TblautIndicadorVigencia> tblautIndicadorVigencias;

	public TblautVigencia() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getAnioVigencia() {
		return this.anioVigencia;
	}

	public void setAnioVigencia(Integer anioVigencia) {
		this.anioVigencia = anioVigencia;
	}

	public Date getFechaApertura() {
		return this.fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaCierre() {
		return this.fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<TblautCaracteristicaVigencia> getTblautCaracteristicaVigencias() {
		return this.tblautCaracteristicaVigencias;
	}

	public void setTblautCaracteristicaVigencias(List<TblautCaracteristicaVigencia> tblautCaracteristicaVigencias) {
		this.tblautCaracteristicaVigencias = tblautCaracteristicaVigencias;
	}

	public TblautCaracteristicaVigencia addTblautCaracteristicaVigencia(TblautCaracteristicaVigencia tblautCaracteristicaVigencia) {
		getTblautCaracteristicaVigencias().add(tblautCaracteristicaVigencia);
		tblautCaracteristicaVigencia.setTblautVigencia(this);

		return tblautCaracteristicaVigencia;
	}

	public TblautCaracteristicaVigencia removeTblautCaracteristicaVigencia(TblautCaracteristicaVigencia tblautCaracteristicaVigencia) {
		getTblautCaracteristicaVigencias().remove(tblautCaracteristicaVigencia);
		tblautCaracteristicaVigencia.setTblautVigencia(null);

		return tblautCaracteristicaVigencia;
	}

	public List<TblautFactorVigencia> getTblautFactorVigencias() {
		return this.tblautFactorVigencias;
	}

	public void setTblautFactorVigencias(List<TblautFactorVigencia> tblautFactorVigencias) {
		this.tblautFactorVigencias = tblautFactorVigencias;
	}

	public TblautFactorVigencia addTblautFactorVigencia(TblautFactorVigencia tblautFactorVigencia) {
		getTblautFactorVigencias().add(tblautFactorVigencia);
		tblautFactorVigencia.setTblautVigencia(this);

		return tblautFactorVigencia;
	}

	public TblautFactorVigencia removeTblautFactorVigencia(TblautFactorVigencia tblautFactorVigencia) {
		getTblautFactorVigencias().remove(tblautFactorVigencia);
		tblautFactorVigencia.setTblautVigencia(null);

		return tblautFactorVigencia;
	}

	public List<TblautIndicadorVigencia> getTblautIndicadorVigencias() {
		return this.tblautIndicadorVigencias;
	}

	public void setTblautIndicadorVigencias(List<TblautIndicadorVigencia> tblautIndicadorVigencias) {
		this.tblautIndicadorVigencias = tblautIndicadorVigencias;
	}

	public TblautIndicadorVigencia addTblautIndicadorVigencia(TblautIndicadorVigencia tblautIndicadorVigencia) {
		getTblautIndicadorVigencias().add(tblautIndicadorVigencia);
		tblautIndicadorVigencia.setTblautVigencia(this);

		return tblautIndicadorVigencia;
	}

	public TblautIndicadorVigencia removeTblautIndicadorVigencia(TblautIndicadorVigencia tblautIndicadorVigencia) {
		getTblautIndicadorVigencias().remove(tblautIndicadorVigencia);
		tblautIndicadorVigencia.setTblautVigencia(null);

		return tblautIndicadorVigencia;
	}

}