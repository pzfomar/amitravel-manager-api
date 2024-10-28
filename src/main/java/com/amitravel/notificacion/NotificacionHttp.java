package com.amitravel.notificacion;

import java.time.LocalDateTime;
import java.util.List;

import com.amitravel.negocio.NegocioHttp;
import com.amitravel.usuario.UsuarioHttp;

import lombok.Builder;
import lombok.Data;

public class NotificacionHttp {
	@Data
	public static class Request {
		private Long negocioId;
		private List<Long> usuarioIds;
		private String nombre;
		private String descripcion;
		private TipoNotificacion tipo;
		private String imagen;
		private Boolean estatus;
	}

	@Data
	@Builder
	public static class Response {
		private Long id;
		private NegocioHttp.Response negocio;
		private List<UsuarioHttp.Response> usuarios;
		private String nombre;
		private String descripcion;
		private TipoNotificacion tipo;
		private String imagen;
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
