package com.amitravel.aficion;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.amitravel.usuario.Usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "AFICIONES")
public class Aficion implements Serializable {
	private static final long serialVersionUID = -4655796036520134152L;

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(11)", nullable = false)
	private Long id;

	@Setter
	@Getter
	@ManyToOne
    @JoinColumn(name="usuario_id", nullable=false)
	private Usuario usuario;

	@Setter
	@Getter
	@Enumerated(EnumType.STRING)
	@Column(name = "movilidad", columnDefinition = "VARCHAR(150)", nullable = false)
	private TipoMovilidad movilidad;

	@Setter
	@Getter
	@Enumerated(EnumType.STRING)
	@Column(name = "sitio", columnDefinition = "VARCHAR(150)", nullable = false)
	private TipoSitio sitio;

	@Setter
	@Getter
	@Column(name = "presupuesto", columnDefinition = "DECIMAL", nullable = false)
	private Double presupuesto;

	@Setter
	@Getter
	@Enumerated(EnumType.STRING)
	@Column(name = "horario_actividad", columnDefinition = "VARCHAR(150)", nullable = false)
	private TipoHorarioActividad horarioActividad;
	
	@Setter
	@Getter
	@Column(name = "estatus", columnDefinition = "BIT(1)", nullable = false)
	private Boolean estatus;
	
	@Getter
    @CreationTimestamp
	private Timestamp creacion;
	
	@Getter
    @UpdateTimestamp
	private Timestamp actualizacion;
}
