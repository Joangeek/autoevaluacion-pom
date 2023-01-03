package autoevaluacion;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tblaut_caracteristica_vigencia database table.
 * 
 */
@Embeddable
public class TblautCaracteristicaVigenciaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private Integer idcaracteristica;

	@Column(insertable=false, updatable=false)
	private Integer vigencia;

	public TblautCaracteristicaVigenciaPK() {
	}
	public Integer getIdcaracteristica() {
		return this.idcaracteristica;
	}
	public void setIdcaracteristica(Integer idcaracteristica) {
		this.idcaracteristica = idcaracteristica;
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
		if (!(other instanceof TblautCaracteristicaVigenciaPK)) {
			return false;
		}
		TblautCaracteristicaVigenciaPK castOther = (TblautCaracteristicaVigenciaPK)other;
		return 
			this.idcaracteristica.equals(castOther.idcaracteristica)
			&& this.vigencia.equals(castOther.vigencia);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idcaracteristica.hashCode();
		hash = hash * prime + this.vigencia.hashCode();
		
		return hash;
	}
}