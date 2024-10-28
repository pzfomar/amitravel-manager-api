package com.amitravel.producto;

import java.time.LocalDateTime;
import java.util.List;

import com.amitravel.negocio.NegocioHttp;

import lombok.Builder;
import lombok.Data;

public class ProductoHttp {
	@Data
	public static class Request {
		private Long negocioId;
		private String nombre;
		private TipoProducto tipo;
		private String descripcion;
		private String imagen;
		private LocalDateTime caducidad;
		private Integer cantidad;
		private Boolean estatus;
	}

	@Data
	@Builder
	public static class Response {
		private Long id;
		private NegocioHttp.Response negocio;
		private String nombre;
		private TipoProducto tipo;
		private String descripcion;
		private String imagen;
		private LocalDateTime caducidad;
		private Integer cantidad;
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
