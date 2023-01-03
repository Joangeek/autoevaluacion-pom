package academico;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tblaca_periodicidad database table.
 * 
 */
@Entity
@Table(name="tblaca_periodicidad",schema="academico")
@NamedQuery(name="TblacaPeriodicidad.findAll", query="SELECT t FROM TblacaPeriodicidad t")
public class TblacaPeriodicidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLACA_PERIODICIDAD_PERIODICIDADCODE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLACA_PERIODICIDAD_PERIODICIDADCODE_GENERATOR")
	@Column(name="periodicidad_code")
	private String periodicidadCode;

	@Column(name="periodicidad_nombre")
	private String periodicidadNombre;

	//bi-directional many-to-one association to TblacaPrograma
	@OneToMany(mappedBy="tblacaPeriodicidad")
	private List<TblacaPrograma> tblacaProgramas;

	public TblacaPeriodicidad() {
	}

	public String getPeriodicidadCode() {
		return this.periodicidadCode;
	}

	public void setPeriodicidadCode(String periodicidadCode) {
		this.periodicidadCode = periodicidadCode;
	}

	public String getPeriodicidadNombre() {
		return this.periodicidadNombre;
	}

	public void setPeriodicidadNombre(String periodicidadNombre) {
		this.periodicidadNombre = periodicidadNombre;
	}

	public List<TblacaPrograma> getTblacaProgramas() {
		return this.tblacaProgramas;
	}

	public void setTblacaProgramas(List<TblacaPrograma> tblacaProgramas) {
		this.tblacaProgramas = tblacaProgramas;
	}

	public TblacaPrograma addTblacaPrograma(TblacaPrograma tblacaPrograma) {
		getTblacaProgramas().add(tblacaPrograma);
		tblacaPrograma.setTblacaPeriodicidad(this);

		return tblacaPrograma;
	}

	public TblacaPrograma removeTblacaPrograma(TblacaPrograma tblacaPrograma) {
		getTblacaProgramas().remove(tblacaPrograma);
		tblacaPrograma.setTblacaPeriodicidad(null);

		return tblacaPrograma;
	}

}