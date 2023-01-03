package encuestas;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the tblenc_modulo_tipo_evaluacion database table.
 * 
 */
@Entity
@Table(name = "tblenc_modulo_tipo_evaluacion", schema = "academico")
@NamedQuery(name = "TblencModuloTipoEvaluacion.findAll", query = "SELECT t FROM TblencModuloTipoEvaluacion t")
public class TblencModuloTipoEvaluacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLENC_MODULO_TIPO_EVALUACION_OID_GENERATOR", sequenceName = "ACADEMICO.TBLENC_MODULO_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLENC_MODULO_TIPO_EVALUACION_OID_GENERATOR")
	private Integer oid;

	private String descripcion;

	private Integer estado;

	private String usuario;

	@OneToMany(mappedBy = "tblenc_modulo_tipo_evaluacion")
	private List<TblencTipoEvaluacion> tblenc_tipo_evaluaciones;

	public TblencModuloTipoEvaluacion() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<TblencTipoEvaluacion> getTblenc_tipo_evaluaciones() {
		return tblenc_tipo_evaluaciones;
	}

	public void setTblenc_tipo_evaluaciones(
			List<TblencTipoEvaluacion> tblenc_tipo_evaluaciones) {
		this.tblenc_tipo_evaluaciones = tblenc_tipo_evaluaciones;
	}


}