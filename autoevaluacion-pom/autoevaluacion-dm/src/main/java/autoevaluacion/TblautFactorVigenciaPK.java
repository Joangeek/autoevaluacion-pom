package autoevaluacion;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tblaut_factor_vigencia database table.
 * 
 */
@Embeddable
public class TblautFactorVigenciaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private Integer idfactor;

	@Column(insertable=false, updatable=false)
	private Integer vigencia;

	public TblautFactorVigenciaPK() {
	}
	public Integer getIdfactor() {
		return this.idfactor;
	}
	public void setIdfactor(Integer idfactor) {
		this.idfactor = idfactor;
	}
	public Integer getVigencia() {
		return this.vigencia;
	}
	public void setVigencia(Integer vigencia) {
		this.vigencia = vigencia;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TblautFactorVigenciaPK)) {
			return false;
		}
		TblautFactorVigenciaPK castOther = (TblautFactorVigenciaPK)other;
		return 
			this.idfactor.equals(castOther.idfactor)
			&& this.vigencia.equals(castOther.vigencia);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idfactor.hashCode();
		hash = hash * prime + this.vigencia.hashCode();
		
		return hash;
	}
}