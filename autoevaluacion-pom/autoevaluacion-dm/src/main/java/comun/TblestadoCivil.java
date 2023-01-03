package comun;

import java.io.Serializable;

import javax.persistence.*;

import academico.TblestEstudiante;

import java.util.List;


/**
 * The persistent class for the tblestado_civil database table.
 * 
 */
@Entity
@Table(name="tbl_estado_civil",schema="academico")
@NamedQuery(name="TblestadoCivil.findAll", query="SELECT t FROM TblestadoCivil t")
public class TblestadoCivil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLESTADO_CIVIL_ESTCIVILCODE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLESTADO_CIVIL_ESTCIVILCODE_GENERATOR")
	@Column(name="est_civil_code")
	private String estCivilCode;

	@Column(name="est_civil_descr")
	private String estCivilDescr;

	//bi-directional many-to-one association to TblestEstudiante
	@OneToMany(mappedBy="tblestadoCivil")
	private List<TblestEstudiante> tblestEstudiantes;

	public TblestadoCivil() {
	}

	public String getEstCivilCode() {
		return this.estCivilCode;
	}

	public void setEstCivilCode(String estCivilCode) {
		this.estCivilCode = estCivilCode;
	}

	public String getEstCivilDescr() {
		return this.estCivilDescr;
	}

	public void setEstCivilDescr(String estCivilDescr) {
		this.estCivilDescr = estCivilDescr;
	}

	public List<TblestEstudiante> getTblestEstudiantes() {
		return this.tblestEstudiantes;
	}

	public void setTblestEstudiantes(List<TblestEstudiante> tblestEstudiantes) {
		this.tblestEstudiantes = tblestEstudiantes;
	}

	public TblestEstudiante addTblestEstudiante(TblestEstudiante tblestEstudiante) {
		getTblestEstudiantes().add(tblestEstudiante);
		tblestEstudiante.setTblestadoCivil(this);

		return tblestEstudiante;
	}

	public TblestEstudiante removeTblestEstudiante(TblestEstudiante tblestEstudiante) {
		getTblestEstudiantes().remove(tblestEstudiante);
		tblestEstudiante.setTblestadoCivil(null);

		return tblestEstudiante;
	}

}