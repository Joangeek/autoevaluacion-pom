package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the tblaut_indicador_vigencia database table.
 * 
 */
@Embeddable
public class TblautIndicadorVigenciaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private Integer oid;

	@Column(insertable=false, updatable=false)
	private Integer vigencia;

	public TblautIndicadorVigenciaPK() {
	}
	public Integer getOid() {
		return this.oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	
	public Integer getVigencia() {
		return vigencia;
	}
	public void setVigencia(Integer vigencia) {
		this.vigencia = vigencia;
	}
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TblautIndicadorVigenciaPK)) {
			return false;
		}
		TblautIndicadorVigenciaPK castOther = (TblautIndicadorVigenciaPK)other;
		return 
			this.oid.equals(castOther.oid)
			&& this.vigencia.equals(castOther.vigencia);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.oid.hashCode();
		hash = hash * prime + this.vigencia.hashCode();
		
		return hash;
	}
}