package comun;

import java.io.Serializable;

import javax.persistence.*;

import academico.TblestEstudiante;

import java.util.List;


/**
 * The persistent class for the tblgenero database table.
 * 
 */
@Entity
@Table(name="Tbl_genero", schema="academico")
@NamedQuery(name="Tblgenero.findAll", query="SELECT t FROM Tblgenero t")
public class Tblgenero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLGENERO_GENEROCODE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLGENERO_GENEROCODE_GENERATOR")
	@Column(name="genero_code")
	private String generoCode;

	@Column(name="genero_descr")
	private String generoDescr;

	//bi-directional many-to-one association to TblestEstudiante
	@OneToMany(mappedBy="tblgenero")
	private List<TblestEstudiante> tblestEstudiantes;

	public Tblgenero() {
	}

	public String getGeneroCode() {
		return this.generoCode;
	}

	public void setGeneroCode(String generoCode) {
		this.generoCode = generoCode;
	}

	public String getGeneroDescr() {
		return this.generoDescr;
	}

	public void setGeneroDescr(String generoDescr) {
		this.generoDescr = generoDescr;
	}

	public List<TblestEstudiante> getTblestEstudiantes() {
		return this.tblestEstudiantes;
	}

	public void setTblestEstudiantes(List<TblestEstudiante> tblestEstudiantes) {
		this.tblestEstudiantes = tblestEstudiantes;
	}

	public TblestEstudiante addTblestEstudiante(TblestEstudiante tblestEstudiante) {
		getTblestEstudiantes().add(tblestEstudiante);
		tblestEstudiante.setTblgenero(this);

		return tblestEstudiante;
	}

	public TblestEstudiante removeTblestEstudiante(TblestEstudiante tblestEstudiante) {
		getTblestEstudiantes().remove(tblestEstudiante);
		tblestEstudiante.setTblgenero(null);

		return tblestEstudiante;
	}

}