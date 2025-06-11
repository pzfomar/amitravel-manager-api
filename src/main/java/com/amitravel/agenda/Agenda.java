package com.amitravel.agenda;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
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
import com.amitravel.usuario.Usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "AGENDAS")
public class Agenda implements Serializable {
	private static final long serialVersionUID = 7090575185584871217L;

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
	@Column(name = "nombre", columnDefinition = "VARCHAR(150)", nullable = false)
	private String nombre;

	@Setter
	@Getter
	@Column(name = "descripcion", columnDefinition = "VARCHAR(200)", nullable = true)
	private String descripcion;

	@Setter
	@Getter
	@Column(name = "fecha", columnDefinition = "DATE", nullable = false)
	private Date fecha;

	@Setter
	@Getter
	@Column(name = "hora", columnDefinition = "TIME", nullable = true)
	private Time hora;

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
