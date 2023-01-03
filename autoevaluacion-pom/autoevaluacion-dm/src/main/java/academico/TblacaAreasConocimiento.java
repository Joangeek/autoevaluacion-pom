package academico;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tblaca_areas_conocimiento database table.
 * 
 */
@Entity
@Table(name="tblaca_areas_conocimiento",schema="academico")
@NamedQuery(name="TblacaAreasConocimiento.findAll", query="SELECT t FROM TblacaAreasConocimiento t")
public class TblacaAreasConocimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLACA_AREAS_CONOCIMIENTO_ARCOCODE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLACA_AREAS_CONOCIMIENTO_ARCOCODE_GENERATOR")
	@Column(name="arco_code")
	private long arcoCode;

	@Column(name="arco_descripcion")
	private String arcoDescripcion;

	//bi-directional many-to-one association to TblacaPrograma
	@OneToMany(mappedBy="tblacaAreasConocimiento")
	private List<TblacaPrograma> tblacaProgramas;

	public TblacaAreasConocimiento() {
	}

	public long getArcoCode() {
		return this.arcoCode;
	}

	public void setArcoCode(long arcoCode) {
		this.arcoCode = arcoCode;
	}

	public String getArcoDescripcion() {
		return this.arcoDescripcion;
	}

	public void setArcoDescripcion(String arcoDescripcion) {
		this.arcoDescripcion = arcoDescripcion;
	}

	public List<TblacaPrograma> getTblacaProgramas() {
		return this.tblacaProgramas;
	}

	public void setTblacaProgramas(List<TblacaPrograma> tblacaProgramas) {
		this.tblacaProgramas = tblacaProgramas;
	}

	public TblacaPrograma addTblacaPrograma(TblacaPrograma tblacaPrograma) {
		getTblacaProgramas().add(tblacaPrograma);
		tblacaPrograma.setTblacaAreasConocimiento(this);

		return tblacaPrograma;
	}

	public TblacaPrograma removeTblacaPrograma(TblacaPrograma tblacaPrograma) {
		getTblacaProgramas().remove(tblacaPrograma);
		tblacaPrograma.setTblacaAreasConocimiento(null);

		return tblacaPrograma;
	}

}