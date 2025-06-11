package com.amitravel.servicio;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import com.amitravel.calificacion.Calificacion;
import com.amitravel.negocio.Negocio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "SERVICIOS")
public class Servicio implements Serializable {
	private static final long serialVersionUID = -3076717403471946818L;

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(11)", nullable = false)
	private Long id;

	@Setter
	@Getter
	@ManyToOne
	@JoinColumn(name = "negocio_id", nullable = false)
	private Negocio negocio;

	@Setter
	@Getter
	@Column(name = "nombre", length = 150, columnDefinition = "VARCHAR(150)", nullable = false)
	private String nombre;

	@Setter
	@Getter
	@Column(name = "descripcion", columnDefinition = "TEXT", nullable = true)
	private String descripcion;

	// private String horario;

	@Setter
	@Getter
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", columnDefinition = "VARCHAR(150)", nullable = false)
	private TipoServicio tipo;

	@Setter
	@Getter
	@Column(name = "lugar", columnDefinition = "TEXT", nullable = false)
	private String lugar;

	@Setter
	@Getter
	@Column(name = "mapa", columnDefinition = "TEXT", nullable = false)
	private String mapa;

	@Setter
	@Getter
	@Column(name = "imagen", columnDefinition = "VARCHAR(350)", nullable = false)
	private String imagen;

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

	@Getter
	@OneToMany(mappedBy = "servicio", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Calificacion> calificaciones;
}
