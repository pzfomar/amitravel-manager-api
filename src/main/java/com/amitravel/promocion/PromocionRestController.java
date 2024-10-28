package com.amitravel.promocion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
@RequestMapping("{lang}/promocion")
public class PromocionRestController {
    @Autowired
    private PromocionService promocionService;

    @PostMapping(path = "")
    public ResponseEntity<PromocionHttp.Response> crear(@PathVariable("lang") String lang,
            @RequestBody PromocionHttp.Request request) throws Exception {
        return this.promocionService.crear(lang, request);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<PromocionHttp.Response> actualizar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id, @RequestBody PromocionHttp.Request request) throws Exception {
        return this.promocionService.actualizar(lang, id, request);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<PromocionHttp.Response> eliminar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id) throws Exception {
        return this.promocionService.eliminar(lang, id);
    }

    @GetMapping(path = "")
    public ResponseEntity<PromocionHttp.PageResponse> obtener(@PathVariable("lang") String lang,
            @RequestParam(name = "pagina", defaultValue = "0", required = true) Integer pagina,
            @RequestParam(name = "tamanio", defaultValue = "5", required = true) Integer tamanio,
            @RequestParam(name = "busqueda", required = false) String busqueda,
            @RequestParam(name = "negocioId", required = false) Long negocioId) throws Exception {
        return this.promocionService.obtener(lang, pagina, tamanio, busqueda, negocioId);
    }

    @GetMapping(path = "lista")
    public ResponseEntity<List<PromocionHttp.Response>> obtener(@PathVariable("lang") String lang) throws Exception {
        return this.promocionService.obtener(lang);
    }
}
