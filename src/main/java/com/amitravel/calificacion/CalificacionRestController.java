package com.amitravel.calificacion;

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
@RequestMapping("{lang}/calificacion")
public class CalificacionRestController {
    @Autowired
    private CalificacionService calificacionService;

    @PostMapping(path = "")
    public ResponseEntity<CalificacionHttp.Response> crear(@PathVariable("lang") String lang,
            @RequestBody CalificacionHttp.Request request) throws Exception {
        return this.calificacionService.crear(request);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<CalificacionHttp.Response> actualizar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id, @RequestBody CalificacionHttp.Request request) throws Exception {
        return this.calificacionService.actualizar(lang, id, request);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<CalificacionHttp.Response> eliminar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id) throws Exception {
        return this.calificacionService.eliminar(lang, id);
    }

    @GetMapping(path = "")
    public ResponseEntity<CalificacionHttp.PageResponse> obtener(@PathVariable("lang") String lang,
            @RequestParam(name = "pagina", defaultValue = "0", required = true) Integer pagina,
            @RequestParam(name = "tamanio", defaultValue = "5", required = true) Integer tamanio,
            @RequestParam(name = "busqueda", required = false) String busqueda,
            @RequestParam(name = "negocioId", required = false) Long negocioId) throws Exception {
        return this.calificacionService.obtener(lang, pagina, tamanio, busqueda, negocioId);
    }

    @GetMapping(path = "lista")
    public ResponseEntity<List<CalificacionHttp.Response>> obtener(@PathVariable("lang") String lang) throws Exception {
        return this.calificacionService.obtener(lang);
    }

}
