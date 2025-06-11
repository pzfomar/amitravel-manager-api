package com.amitravel.usuario;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import com.amitravel.aficion.Aficion;
import com.amitravel.agenda.Agenda;
import com.amitravel.anuncio.Anuncio;
import com.amitravel.busqueda.Busqueda;
import com.amitravel.calificacion.Calificacion;
import com.amitravel.negocio.Negocio;
import com.amitravel.notificacion.Notificacion;
import com.amitravel.persona.Persona;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "USUARIOS")
public class Usuario implements Serializable {
	private static final long serialVersionUID = -5529019334801486592L;

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(11)", nullable = false)
	private Long id;

	@Setter
	@Getter
	@ManyToOne
	@JoinColumn(name = "negocio_id", nullable = true)
	private Negocio negocio;

	@Setter
	@Getter
	@Column(name = "apodo", columnDefinition = "VARCHAR(150)", nullable = false, unique = true)
	private String apodo;

	@Setter
	@Getter
	@Column(name = "contrasenia", columnDefinition = "VARCHAR(250)", nullable = false)
	private String contrasenia;

	@Setter
	@Getter
	@Enumerated(EnumType.STRING)
	@Column(name = "rol", columnDefinition = "VARCHAR(150)", nullable = false)
	private TipoRol rol;

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
	@OneToOne(mappedBy = "usuario")
	@PrimaryKeyJoinColumn
	private Persona persona;

	@Getter
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Agenda> agendas;

	@Getter
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Calificacion> calificaciones;

	@Getter
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Busqueda> busquedas;

	@Getter
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Aficion> aficiones;

	@Getter
	@ManyToMany(mappedBy = "usuarios", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Notificacion> notificaciones;

	@Getter
	@ManyToMany(mappedBy = "usuarios", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Anuncio> anuncios;
}
