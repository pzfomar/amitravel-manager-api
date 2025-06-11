package com.amitravel.servicio;

import java.time.LocalDateTime;
import java.util.List;

import com.amitravel.agenda.AgendaHttp;
import com.amitravel.calificacion.CalificacionHttp;
import com.amitravel.negocio.NegocioHttp;

import lombok.Builder;
import lombok.Data;

public class ServicioHttp {
	@Data
	public static class Request {
		private Long negocioId;
		private String nombre;
		private String descripcion;
		// private String horario;
		private TipoServicio tipo;
		private String lugar;
		private String mapa;
		private String imagen;
		private Boolean estatus;
	}

	@Data
	@Builder
	public static class Response {
		private Long id;
		private NegocioHttp.Response negocio;
		private String nombre;
		private String descripcion;
		// private String horario;
		private TipoServicio tipo;
		private String lugar;
		private String mapa;
		private String imagen;
		private Boolean estatus;
		private LocalDateTime creacion;
		private LocalDateTime actualizacion;
		private List<AgendaHttp.Response> agendas;
		private List<CalificacionHttp.Response> calificaciones;
	}

	@Data
	@Builder
	public static class PageResponse {
		private Integer paginas;
		private Integer paginaSeleccionada;
		private List<Response> contenido;
	}
}
