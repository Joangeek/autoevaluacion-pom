package encuestas;

import java.io.Serializable;

import javax.persistence.*;

import academico.TblacaSedePrograma;

import java.util.Date;

/**
 * The persistent class for the tblenc_programacion_enc_sede_prog database
 * table.
 * 
 */
@Entity
@Table(name = "tblenc_programacion_enc_sede_prog", schema="academico")
@NamedQuery(name = "TblencProgramacionEncSedeProg.findAll", query = "SELECT t FROM TblencProgramacionEncSedeProg t")
public class TblencProgramacionEncSedeProg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLENC_PROGRAMACION_ENC_SEDE_PROG_OID_GENERATOR", sequenceName = "ACADEMICO.TBLENC_PROGRAMACION_ENC_SEDE_PROG_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLENC_PROGRAMACION_ENC_SEDE_PROG_OID_GENERATOR")
	private Integer oid;

	@Temporal(TemporalType.DATE)
	private Date desde;

	@Temporal(TemporalType.DATE)
	private Date hasta;

	private String usuario;

	// bi-directional many-to-one association to TblacaSedePrograma
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idsede_programa")
	private TblacaSedePrograma tblacaSedePrograma;

	// bi-directional many-to-one association to TblencProgramacionEncuesta
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idprogramacion_encuesta")
	private TblencProgramacionEncuesta tblencProgramacionEncuesta;

	public TblencProgramacionEncSedeProg() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Date getDesde() {
		return this.desde;
	}

	public void setDesde(Date desde) {
		this.desde = desde;
	}

	public Date getHasta() {
		return this.hasta;
	}

	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public TblacaSedePrograma getTblacaSedePrograma() {
		return this.tblacaSedePrograma;
	}

	public void setTblacaSedePrograma(TblacaSedePrograma tblacaSedePrograma) {
		this.tblacaSedePrograma = tblacaSedePrograma;
	}

	public TblencProgramacionEncuesta getTblencProgramacionEncuesta() {
		return this.tblencProgramacionEncuesta;
	}

	public void setTblencProgramacionEncuesta(
			TblencProgramacionEncuesta tblencProgramacionEncuesta) {
		this.tblencProgramacionEncuesta = tblencProgramacionEncuesta;
	}

}