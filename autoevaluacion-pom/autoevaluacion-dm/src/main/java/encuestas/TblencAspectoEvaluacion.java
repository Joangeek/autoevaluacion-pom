package encuestas;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the tblenc_aspecto_evaluacion database table.
 * 
 */
@Entity
@Table(name = "tblenc_aspecto_evaluacion", schema = "academico")
@NamedQuery(name = "TblencAspectoEvaluacion.findAll", query = "SELECT t FROM TblencAspectoEvaluacion t")
public class TblencAspectoEvaluacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLENC_ASPECTO_EVALUACION_OID_GENERATOR", sequenceName = "ACADEMICO.TBLENC_ASPECTO_EVALUACION_OID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLENC_ASPECTO_EVALUACION_OID_GENERATOR")
	private Integer oid;

	private String opinion;

	// bi-directional many-to-one association to TblencAspecto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idaspecto")
	private TblencAspecto tblencAspecto;

	// bi-directional many-to-one association to TblencEvaluacionInstitucion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_evaluacion_inst")
	private TblencEvaluacionInstitucion tblencEvaluacionInstitucion;

	// bi-directional many-to-one association to TblencOpcione
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idopcion")
	private TblencOpcione tblencOpcione;
	
	private String usuario;

	public TblencAspectoEvaluacion() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getOpinion() {
		return this.opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public TblencAspecto getTblencAspecto() {
		return this.tblencAspecto;
	}

	public void setTblencAspecto(TblencAspecto tblencAspecto) {
		this.tblencAspecto = tblencAspecto;
	}

	public TblencEvaluacionInstitucion getTblencEvaluacionInstitucion() {
		return this.tblencEvaluacionInstitucion;
	}

	public void setTblencEvaluacionInstitucion(
			TblencEvaluacionInstitucion tblencEvaluacionInstitucion) {
		this.tblencEvaluacionInstitucion = tblencEvaluacionInstitucion;
	}

	public TblencOpcione getTblencOpcione() {
		return this.tblencOpcione;
	}

	public void setTblencOpcione(TblencOpcione tblencOpcione) {
		this.tblencOpcione = tblencOpcione;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}