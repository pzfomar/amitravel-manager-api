package com.amitravel.anuncio;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.amitravel.negocio.Negocio;
import com.amitravel.usuario.Usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "ANUNCIOS")
public class Anuncio implements Serializable {
	private static final long serialVersionUID = 1428397998291624013L;

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
	@ManyToMany
	@JoinTable(name = "ANUNCIOS_USUARIOS")
	private List<Usuario> usuarios;

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
	@Column(name = "imagen", columnDefinition = "VARCHAR(350)", nullable = false)
	private String imagen;

	@Setter
	@Getter
	@Column(name = "url", columnDefinition = "VARCHAR(150)", nullable = false)
	private String url;

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
