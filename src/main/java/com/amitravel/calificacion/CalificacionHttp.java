package com.amitravel.calificacion;

import java.time.LocalDateTime;
import java.util.List;

import com.amitravel.evento.EventoHttp;
import com.amitravel.usuario.UsuarioHttp;

import lombok.Builder;
import lombok.Data;

public class CalificacionHttp {
	@Data
	public static class Request {
		private Long usuarioId;
		private Long eventoId;
		private Integer evaluacion;
		private String comentario;
		private Boolean estatus;
	}

	@Data
	@Builder
	public static class Response {
		private Long id;
		private UsuarioHttp.Response usuario;
		private EventoHttp.Response evento;
		private Integer evaluacion;
		private String comentario;
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
