package org.ms.timepro.manager.entity;
// Generated 14-09-2024 21:12:12 by Hibernate Tools 4.3.6.Final

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MetodoPago generated by hbm2java
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public", name = "metodo_pago")
public class MetodoPago implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "mpa_id", unique = true, nullable = false)
	private int mpaId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona", nullable = false)
	private Persona persona;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_pago", nullable = false)
	private TipoPago tipoPago;

	@Column(name = "mpa_cuenta")
	private String mpaCuenta;

	@Column(name = "mpa_tipo_cuenta")
	private String mpaTipoCuenta;

	@Column(name = "mpa_email")
	private String mpaEmail;

	@Column(name = "mpa_banco")
	private String mpaBanco;
}
