package com.amitravel.negocio;

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
@RequestMapping("{lang}/negocio")
public class NegocioRestController {
    @Autowired
    private NegocioService negocioService;

    @PostMapping(path = "")
    public ResponseEntity<NegocioHttp.Response> crear(@PathVariable("lang") String lang,
            @RequestBody NegocioHttp.Request request) {
        return this.negocioService.crear(lang, request);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<NegocioHttp.Response> actualizar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id, @RequestBody NegocioHttp.Request request) throws Exception {
        return this.negocioService.actualizar(lang, id, request);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<NegocioHttp.Response> eliminar(@PathVariable("lang") String lang, @PathVariable("id") Long id)
            throws Exception {
        return this.negocioService.eliminar(lang, id);
    }

    @GetMapping(path = "")
    public ResponseEntity<NegocioHttp.PageResponse> obtener(@PathVariable("lang") String lang,
            @RequestParam(name = "pagina", defaultValue = "0", required = true) Integer pagina,
            @RequestParam(name = "tamanio", defaultValue = "5", required = true) Integer tamanio,
            @RequestParam(name = "busqueda", required = false) String busqueda,
            @RequestParam(name = "id", required = false) Long id) throws Exception {
        return this.negocioService.obtener(lang, pagina, tamanio, busqueda, id);
    }

    @GetMapping(path = "lista")
    public ResponseEntity<List<NegocioHttp.Response>> obtener(@PathVariable("lang") String lang) throws Exception {
        return this.negocioService.obtener(lang);
    }
}
