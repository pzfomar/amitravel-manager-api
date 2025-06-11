package com.amitravel.persona;

import java.time.LocalDateTime;
import java.util.List;

import com.amitravel.usuario.UsuarioHttp;

import lombok.Builder;
import lombok.Data;

public class PersonaHttp {
	@Data
	public static class Request {
		private Long usuarioId;
		private String correo;
		private String nombre;
		private String apellidoPaterno;
		private String apellidoMaterno;
		private Integer edad;
		private String telefono;
		private Boolean estatus;
	}

	@Data
	@Builder
	public static class Response {
		private Long id;
		private UsuarioHttp.Response usuario;
		private String correo;
		private String nombre;
		private String apellidoPaterno;
		private String apellidoMaterno;
		private Integer edad;
		private String telefono;
		private Boolean estatus;
		private LocalDateTime creacion;
		private LocalDateTime actualizacion;
	}

	@Data
	@Builder
	public static class PageResponse {
		private Integer paginas;
		private Integer paginaSeleccionada;
		private List<Response> contenido;
	}
}
