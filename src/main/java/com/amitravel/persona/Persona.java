package com.amitravel.persona;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.amitravel.usuario.Usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "PERSONAS")
public class Persona implements Serializable {
	private static final long serialVersionUID = -1388047564155213625L;

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(11)", nullable = false)
	private Long id;

	@Setter
	@Getter
	@OneToOne
	@JoinColumn(name = "usuario_id", nullable = false, unique = true)
	private Usuario usuario;

	@Setter
	@Getter
	@Column(name = "correo", columnDefinition = "VARCHAR(200)", nullable = false)
	private String correo;

	@Setter
	@Getter
	@Column(name = "nombre", columnDefinition = "VARCHAR(150)", nullable = false)
	private String nombre;

	@Setter
	@Getter
	@Column(name = "apellido_paterno", columnDefinition = "VARCHAR(150)", nullable = false)
	private String apellidoPaterno;

	@Setter
	@Getter
	@Column(name = "apellido_materno", columnDefinition = "VARCHAR(150)", nullable = false)
	private String apellidoMaterno;

	@Setter
	@Getter
	@Column(name = "edad", columnDefinition = "INT(2)", nullable = false)
	private Integer edad;

	@Setter
	@Getter
	@Column(name = "telefono", columnDefinition = "VARCHAR(20)", nullable = false)
	private String telefono;

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
