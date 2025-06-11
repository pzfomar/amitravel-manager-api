package com.amitravel.evento;

import java.util.List;

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
@RequestMapping("{lang}/evento")
public class EventoRestController {
    @Autowired
    private EventoService eventoService;

    @PostMapping(path = "")
    public ResponseEntity<EventoHttp.Response> crear(@PathVariable("lang") String lang,
            @RequestBody EventoHttp.Request request) throws Exception {
        return this.eventoService.crear(lang, request);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<EventoHttp.Response> actualizar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id, @RequestBody EventoHttp.Request request) throws Exception {
        return this.eventoService.actualizar(lang, id, request);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<EventoHttp.Response> eliminar(@PathVariable("lang") String lang, @PathVariable("id") Long id)
            throws Exception {
        return this.eventoService.eliminar(lang, id);
    }

    @GetMapping(path = "")
    public ResponseEntity<EventoHttp.PageResponse> obtener(@PathVariable("lang") String lang,
            @RequestParam(name = "pagina", defaultValue = "0", required = true) Integer pagina,
            @RequestParam(name = "tamanio", defaultValue = "5", required = true) Integer tamanio,
            @RequestParam(name = "busqueda", required = false) String busqueda,
            @RequestParam(name = "negocioId", required = false) Long negocioId) throws Exception {
        return this.eventoService.obtener(lang, pagina, tamanio, busqueda, negocioId);
    }

    @GetMapping(path = "lista")
    public ResponseEntity<List<EventoHttp.Response>> obtener(@PathVariable("lang") String lang) throws Exception {
        return this.eventoService.obtener(lang);
    }
}
