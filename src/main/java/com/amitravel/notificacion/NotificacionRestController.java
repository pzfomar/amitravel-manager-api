package com.amitravel.notificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("{lang}/notificacion")
public class NotificacionRestController {
    @Autowired
    private NotificacionService notificacionService;

    @PostMapping(path = "")
    public ResponseEntity<NotificacionHttp.Response> crear(@PathVariable("lang") String lang,
            @RequestBody NotificacionHttp.Request request) throws Exception {
        return this.notificacionService.crear(request);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<NotificacionHttp.Response> actualizar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id, @RequestBody NotificacionHttp.Request request) throws Exception {
        return this.notificacionService.actualizar(lang, id, request);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<NotificacionHttp.Response> eliminar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id) throws Exception {
        return this.notificacionService.eliminar(lang, id);
    }

    @GetMapping(path = "")
    public ResponseEntity<NotificacionHttp.PageResponse> obtener(@PathVariable("lang") String lang,
            @RequestParam(name = "pagina", defaultValue = "0", required = true) Integer pagina,
            @RequestParam(name = "tamanio", defaultValue = "5", required = true) Integer tamanio,
            @RequestParam(name = "busqueda", required = false) String busqueda,
            @RequestParam(name = "negocioId", required = false) Long negocioId) throws Exception {
        return this.notificacionService.obtener(lang, pagina, tamanio, busqueda, negocioId);
    }
}
