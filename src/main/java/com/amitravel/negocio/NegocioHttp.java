package com.amitravel.negocio;

import java.time.LocalDateTime;
import java.util.List;

import com.amitravel.agenda.AgendaHttp;
import com.amitravel.anuncio.AnuncioHttp;
import com.amitravel.calificacion.CalificacionHttp;
import com.amitravel.evento.EventoHttp;
import com.amitravel.horario.HorarioDto;
import com.amitravel.notificacion.NotificacionHttp;
import com.amitravel.producto.ProductoHttp;
import com.amitravel.promocion.PromocionHttp;

import lombok.Builder;
import lombok.Data;

public class NegocioHttp {
	@Data
	public static class Request {
		private String nombre;
		private String descripcion;
		private String lugar;
		private String mapa;
		private String imagen;
		private List<HorarioDto> horarios;
		private Boolean estatus;
	}

	@Data
	@Builder
	public static class Response {
		private Long id;
		private String nombre;
		private String descripcion;
		private String lugar;
		private String mapa;
		private String imagen;
		private List<HorarioDto> horarios;
		private Boolean estatus;
		private LocalDateTime creacion;
		private LocalDateTime actualizacion;
		private List<NotificacionHttp.Response> notificaciones;
		private List<PromocionHttp.Response> promociones;
		private List<ProductoHttp.Response> productos;
		private List<CalificacionHttp.Response> calificaciones;
		private List<EventoHttp.Response> eventos;
		private List<AnuncioHttp.Response> anuncios;
		private List<AgendaHttp.Response> agendas;
	}

	@Data
	@Builder
	public static class PageResponse {
		private Integer paginas;
		private Integer paginaSeleccionada;
		private List<Response> contenido;
	}
}
