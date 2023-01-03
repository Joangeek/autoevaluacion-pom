package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * The persistent class for the tblaut_caracteristica_vigencia database table.
 * 
 */
@Entity
@Table(name = "tblaut_caracteristica_vigencia", schema = "academico")
@NamedQuery(name = "TblautCaracteristicaVigencia.findAll", query = "SELECT t FROM TblautCaracteristicaVigencia t")
public class TblautCaracteristicaVigencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TblautCaracteristicaVigenciaPK id;

	private BigDecimal ponderacion;

	// bi-directional many-to-one association to TblautVigencia
	@ManyToOne
	@JoinColumn(name = "vigencia", insertable = false, updatable = false)
	private TblautVigencia tblautVigencia;

	// bi-directional many-to-one association to TblautCaracteristica
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcaracteristica", insertable = false, updatable = false)
	private TblautCaracteristica tblautCaracteristica;

	public TblautCaracteristicaVigencia() {
	}

	public TblautCaracteristicaVigenciaPK getId() {
		return this.id;
	}

	public void setId(TblautCaracteristicaVigenciaPK id) {
		this.id = id;
	}

	public BigDecimal getPonderacion() {
		return this.ponderacion;
	}

	public void setPonderacion(BigDecimal ponderacion) {
		this.ponderacion = ponderacion;
	}

	public TblautVigencia getTblautVigencia() {
		return this.tblautVigencia;
	}

	public void setTblautVigencia(TblautVigencia tblautVigencia) {
		this.tblautVigencia = tblautVigencia;
	}

	public TblautCaracteristica getTblautCaracteristica() {
		return this.tblautCaracteristica;
	}

	public void setTblautCaracteristica(
			TblautCaracteristica tblautCaracteristica) {
		this.tblautCaracteristica = tblautCaracteristica;
	}

}