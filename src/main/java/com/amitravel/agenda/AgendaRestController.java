package com.amitravel.agenda;

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
@RequestMapping("{lang}/agenda")
public class AgendaRestController {
    @Autowired
    private AgendaService agendaService;

    @PostMapping(path = "")
    public ResponseEntity<AgendaHttp.Response> crear(@PathVariable("lang") String lang,
            @RequestBody AgendaHttp.Request request) throws Exception {
        return this.agendaService.crear(request);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<AgendaHttp.Response> actualizar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id, @RequestBody AgendaHttp.Request request) throws Exception {
        return this.agendaService.actualizar(lang, id, request);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<AgendaHttp.Response> eliminar(@PathVariable("lang") String lang, @PathVariable("id") Long id)
            throws Exception {
        return this.agendaService.eliminar(lang, id);
    }

    @GetMapping(path = "")
    public ResponseEntity<AgendaHttp.PageResponse> obtener(@PathVariable("lang") String lang,
            @RequestParam(name = "pagina", defaultValue = "0", required = true) Integer pagina,
            @RequestParam(name = "tamanio", defaultValue = "5", required = true) Integer tamanio) throws Exception {
        return this.agendaService.obtener(lang, pagina, tamanio);
    }
}
