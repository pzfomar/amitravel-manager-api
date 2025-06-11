package com.amitravel.negocio;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import com.amitravel.anuncio.Anuncio;
import com.amitravel.evento.Evento;
import com.amitravel.horario.Horario;
import com.amitravel.notificacion.Notificacion;
import com.amitravel.producto.Producto;
import com.amitravel.promocion.Promocion;
import com.amitravel.usuario.Usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "NEGOCIOS")
public class Negocio implements Serializable {
	private static final long serialVersionUID = -7560085881398500957L;

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(11)", nullable = false)
	private Long id;

	@Setter
	@Getter
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", columnDefinition = "VARCHAR(150)", nullable = true)
	private TipoNegocio tipo;

	@Setter
	@Getter
	@Column(name = "nombre", columnDefinition = "VARCHAR(150)", nullable = false)
	private String nombre;

	@Setter
	@Getter
	@Column(name = "descripcion", columnDefinition = "TEXT", nullable = true)
	private String descripcion;

	@Setter
	@Getter
	@Column(name = "lugar", columnDefinition = "VARCHAR(250)", nullable = false)
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
	@OneToMany(mappedBy = "negocio", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Notificacion> notificaciones;

	@Getter
	@OneToMany(mappedBy = "negocio", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Promocion> promociones;

	@Getter
	@OneToMany(mappedBy = "negocio", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Producto> productos;

	@Getter
	@OneToMany(mappedBy = "negocio", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Evento> eventos;

	@Getter
	@OneToMany(mappedBy = "negocio", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Anuncio> anuncios;

	@Getter
	@OneToMany(mappedBy = "negocio", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Horario> horarios;

	@Getter
	@OneToMany(mappedBy = "negocio", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Usuario> usuarios;
}
