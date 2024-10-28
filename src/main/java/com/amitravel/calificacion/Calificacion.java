package com.amitravel.calificacion;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.amitravel.evento.Evento;
import com.amitravel.servicio.Servicio;
import com.amitravel.usuario.Usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "CALIFICACIONES")
public class Calificacion implements Serializable {
	private static final long serialVersionUID = -8162998067929082051L;

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(11)", nullable = false)
	private Long id;

	@Setter
	@Getter
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	@Setter
	@Getter
	@ManyToOne
	@JoinColumn(name = "evento_id", nullable = true)
	private Evento evento;

	@Setter
	@Getter
	@ManyToOne
	@JoinColumn(name = "servicio_id", nullable = true)
	private Servicio servicio;

	@Setter
	@Getter
	@Column(name = "evaluacion", columnDefinition = "INT(1)", nullable = false)
	private Integer evaluacion;

	@Setter
	@Getter
	@Column(name = "comentario", columnDefinition = "VARCHAR(300)", nullable = true)
	private String comentario;

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
