package comun;

import java.io.Serializable;

import javax.persistence.*;

import academico.TblacaPrograma;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the tblnbc database table.
 * 
 */
@Entity
@Table(name="Tbl_nbc",schema="academico")
@NamedQuery(name="Tblnbc.findAll", query="SELECT t FROM Tblnbc t")
public class Tblnbc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLNBC_NBCCODE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLNBC_NBCCODE_GENERATOR")
	@Column(name="nbc_code")
	private long nbcCode;

	@Column(name="nbc_area")
	private BigDecimal nbcArea;

	@Column(name="nbc_descr")
	private String nbcDescr;

	@Column(name="nbc_especif")
	private BigDecimal nbcEspecif;

	//bi-directional many-to-one association to TblacaPrograma
	@OneToMany(mappedBy="tblnbc1")
	private List<TblacaPrograma> tblacaProgramas1;

	//bi-directional many-to-one association to TblacaPrograma
	@OneToMany(mappedBy="tblnbc2")
	private List<TblacaPrograma> tblacaProgramas2;

	public Tblnbc() {
	}

	public long getNbcCode() {
		return this.nbcCode;
	}

	public void setNbcCode(long nbcCode) {
		this.nbcCode = nbcCode;
	}

	public BigDecimal getNbcArea() {
		return this.nbcArea;
	}

	public void setNbcArea(BigDecimal nbcArea) {
		this.nbcArea = nbcArea;
	}

	public String getNbcDescr() {
		return this.nbcDescr;
	}

	public void setNbcDescr(String nbcDescr) {
		this.nbcDescr = nbcDescr;
	}

	public BigDecimal getNbcEspecif() {
		return this.nbcEspecif;
	}

	public void setNbcEspecif(BigDecimal nbcEspecif) {
		this.nbcEspecif = nbcEspecif;
	}

	public List<TblacaPrograma> getTblacaProgramas1() {
		return this.tblacaProgramas1;
	}

	public void setTblacaProgramas1(List<TblacaPrograma> tblacaProgramas1) {
		this.tblacaProgramas1 = tblacaProgramas1;
	}

	public TblacaPrograma addTblacaProgramas1(TblacaPrograma tblacaProgramas1) {
		getTblacaProgramas1().add(tblacaProgramas1);
		tblacaProgramas1.setTblnbc1(this);

		return tblacaProgramas1;
	}

	public TblacaPrograma removeTblacaProgramas1(TblacaPrograma tblacaProgramas1) {
		getTblacaProgramas1().remove(tblacaProgramas1);
		tblacaProgramas1.setTblnbc1(null);

		return tblacaProgramas1;
	}

	public List<TblacaPrograma> getTblacaProgramas2() {
		return this.tblacaProgramas2;
	}

	public void setTblacaProgramas2(List<TblacaPrograma> tblacaProgramas2) {
		this.tblacaProgramas2 = tblacaProgramas2;
	}

	public TblacaPrograma addTblacaProgramas2(TblacaPrograma tblacaProgramas2) {
		getTblacaProgramas2().add(tblacaProgramas2);
		tblacaProgramas2.setTblnbc2(this);

		return tblacaProgramas2;
	}

	public TblacaPrograma removeTblacaProgramas2(TblacaPrograma tblacaProgramas2) {
		getTblacaProgramas2().remove(tblacaProgramas2);
		tblacaProgramas2.setTblnbc2(null);

		return tblacaProgramas2;
	}

}