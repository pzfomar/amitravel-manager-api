package com.amitravel.usuario;

import java.time.LocalDateTime;
import java.util.List;

import com.amitravel.aficion.AficionHttp;
import com.amitravel.agenda.AgendaHttp;
import com.amitravel.anuncio.AnuncioHttp;
import com.amitravel.busqueda.BusquedaHttp;
import com.amitravel.calificacion.CalificacionHttp;
import com.amitravel.negocio.NegocioHttp;
import com.amitravel.notificacion.NotificacionHttp;
import com.amitravel.persona.PersonaHttp;

import lombok.Builder;
import lombok.Data;

public class UsuarioHttp {
	@Data
	public static class Request {
		private Long negocioId;
		private String apodo;
		private String contrasenia;
		private TipoRol rol;
		private Boolean estatus;
	}

	@Data
	@Builder
	public static class Response {
		private Long id;
		private NegocioHttp.Response negocio;
		private String apodo;
		private String contrasenia;
		private TipoRol rol;
		private Boolean estatus;
		private LocalDateTime creacion;
		private LocalDateTime actualizacion;
		private PersonaHttp.Response persona;
		private List<AgendaHttp.Response> agendas;
		private List<CalificacionHttp.Response> calificaciones;
		private List<BusquedaHttp.Response> busquedas;
		private List<AficionHttp.Response> aficiones;
		private List<NotificacionHttp.Response> notificaciones;
		private List<AnuncioHttp.Response> anuncios;
	}

	@Data
	@Builder
	public static class PageResponse {
		private Integer paginas;
		private Integer paginaSeleccionada;
		private List<Response> contenido;
	}
}
