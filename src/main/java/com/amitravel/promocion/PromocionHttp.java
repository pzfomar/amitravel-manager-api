package com.amitravel.promocion;

import java.time.LocalDateTime;
import java.util.List;

import com.amitravel.negocio.NegocioHttp;

import lombok.Builder;
import lombok.Data;

public class PromocionHttp {
	@Data
	public static class Request {
		private Long negocioId;
		private String nombre;
		private String descripcion;
		private String imagen;
		private String url;
		private Boolean estatus;
	}

	@Data
	@Builder
	public static class Response {
		private Long id;
		private NegocioHttp.Response negocio;
		private String nombre;
		private String descripcion;
		private String imagen;
		private String url;
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
