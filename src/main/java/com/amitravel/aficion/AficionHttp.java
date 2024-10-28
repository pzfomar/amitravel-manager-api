package com.amitravel.aficion;

import java.time.LocalDateTime;
import java.util.List;

import com.amitravel.usuario.UsuarioHttp;

import lombok.Builder;
import lombok.Data;

public class AficionHttp {
	@Data
	public static class Request {
		private Long usuarioId;
		private TipoMovilidad movilidad;
		private TipoSitio sitio;
		private Double presupuesto;
		private TipoHorarioActividad horarioActividad;
		private Boolean estatus;
	}

	@Data
	@Builder
	public static class Response {
		private Long id;
		private UsuarioHttp.Response usuario;
		private TipoMovilidad movilidad;
		private TipoSitio sitio;
		private Double presupuesto;
		private TipoHorarioActividad horarioActividad;
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
