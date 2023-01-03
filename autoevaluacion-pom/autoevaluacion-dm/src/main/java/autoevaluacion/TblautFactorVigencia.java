package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * The persistent class for the tblaut_factor_vigencia database table.
 * 
 */
@Entity
@Table(name = "tblaut_factor_vigencia", schema = "academico")
@NamedQuery(name = "TblautFactorVigencia.findAll", query = "SELECT t FROM TblautFactorVigencia t")
public class TblautFactorVigencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TblautFactorVigenciaPK id;

	private BigDecimal ponderacion;

	// bi-directional many-to-one association to TblautVigencia
	@ManyToOne
	@JoinColumn(name = "vigencia", insertable = false, updatable = false)
	private TblautVigencia tblautVigencia;

	// bi-directional many-to-one association to TblautFactore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idfactor", insertable = false, updatable = false)
	private TblautFactore tblautFactore;

	public TblautFactorVigencia() {
	}

	public TblautFactorVigenciaPK getId() {
		return this.id;
	}

	public void setId(TblautFactorVigenciaPK id) {
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

	public TblautFactore getTblautFactore() {
		return this.tblautFactore;
	}

	public void setTblautFactore(TblautFactore tblautFactore) {
		this.tblautFactore = tblautFactore;
	}

}