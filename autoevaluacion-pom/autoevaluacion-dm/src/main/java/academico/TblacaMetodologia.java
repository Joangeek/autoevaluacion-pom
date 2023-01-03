package academico;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tblaca_metodologia database table.
 * 
 */
@Entity
@Table(name="tblaca_metodologia",schema="academico")
@NamedQuery(name="TblacaMetodologia.findAll", query="SELECT t FROM TblacaMetodologia t")
public class TblacaMetodologia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLACA_METODOLOGIA_METODOLOGIACODE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLACA_METODOLOGIA_METODOLOGIACODE_GENERATOR")
	@Column(name="metodologia_code")
	private String metodologiaCode;

	@Column(name="metodologia_descr")
	private String metodologiaDescr;

	//bi-directional many-to-one association to TblacaPrograma
	@OneToMany(mappedBy="tblacaMetodologia")
	private List<TblacaPrograma> tblacaProgramas;

	public TblacaMetodologia() {
	}

	public String getMetodologiaCode() {
		return this.metodologiaCode;
	}

	public void setMetodologiaCode(String metodologiaCode) {
		this.metodologiaCode = metodologiaCode;
	}

	public String getMetodologiaDescr() {
		return this.metodologiaDescr;
	}

	public void setMetodologiaDescr(String metodologiaDescr) {
		this.metodologiaDescr = metodologiaDescr;
	}

	public List<TblacaPrograma> getTblacaProgramas() {
		return this.tblacaProgramas;
	}

	public void setTblacaProgramas(List<TblacaPrograma> tblacaProgramas) {
		this.tblacaProgramas = tblacaProgramas;
	}

	public TblacaPrograma addTblacaPrograma(TblacaPrograma tblacaPrograma) {
		getTblacaProgramas().add(tblacaPrograma);
		tblacaPrograma.setTblacaMetodologia(this);

		return tblacaPrograma;
	}

	public TblacaPrograma removeTblacaPrograma(TblacaPrograma tblacaPrograma) {
		getTblacaProgramas().remove(tblacaPrograma);
		tblacaPrograma.setTblacaMetodologia(null);

		return tblacaPrograma;
	}

}