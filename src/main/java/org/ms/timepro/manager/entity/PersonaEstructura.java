package org.ms.timepro.manager.entity;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PersonaEstructura generated by hbm2java
 */
@Entity
@Table(schema = "public", name = "persona_estructura")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaEstructura implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "pee_id", unique = true, nullable = false)
    private int peeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estructura")
    private Estructura estructura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pee_fecha_inicio", length = 29)
    private Date peeFechaInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pee_fecha_fin", length = 29)
    private Date peeFechaFin;

    @Column(name = "pee_estado")
    private String peeEstado;
}
