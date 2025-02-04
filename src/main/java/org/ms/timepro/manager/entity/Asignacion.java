package org.ms.timepro.manager.entity;
// Generated 14-09-2024 21:12:12 by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Asignacion generated by hbm2java
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public", name = "asignacion")
public class Asignacion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "asg_id", unique = true, nullable = false)
	private int asgId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona", nullable = false)
	private Persona persona;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	private Proyecto proyecto;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "asignacion")
	private Set<TimeReport> timeReports = new HashSet<TimeReport>(0);


}
