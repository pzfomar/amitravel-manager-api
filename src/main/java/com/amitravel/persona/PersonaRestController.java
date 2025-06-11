package com.amitravel.persona;

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
@RequestMapping("{lang}/persona")
public class PersonaRestController {
    @Autowired
    private PersonaService personaService;

    @PostMapping(path = "")
    public ResponseEntity<PersonaHttp.Response> crear(@PathVariable("lang") String lang,
            @RequestBody PersonaHttp.Request request) throws Exception {
        return this.personaService.crear(lang, request);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<PersonaHttp.Response> actualizar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id, @RequestBody PersonaHttp.Request request) throws Exception {
        return this.personaService.actualizar(lang, id, request);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<PersonaHttp.Response> eliminar(@PathVariable("lang") String lang, @PathVariable("id") Long id)
            throws Exception {
        return this.personaService.eliminar(lang, id);
    }

    @GetMapping(path = "")
    public ResponseEntity<PersonaHttp.PageResponse> obtener(@PathVariable("lang") String lang,
            @RequestParam(name = "pagina", defaultValue = "0", required = true) Integer pagina,
            @RequestParam(name = "tamanio", defaultValue = "5", required = true) Integer tamanio,
            @RequestParam(name = "busqueda", required = false) String busqueda,
            @RequestParam(name = "usuarioId", required = false) Long usuarioId) throws Exception {
        return this.personaService.obtener(lang, pagina, tamanio, busqueda, usuarioId);
    }

}
