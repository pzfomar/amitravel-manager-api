package com.amitravel.busqueda;

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
@RequestMapping("{lang}/busqueda")
public class BusquedaRestController {
    @Autowired
    private BusquedaService busquedaService;

    @PostMapping(path = "")
    public ResponseEntity<BusquedaHttp.Response> crear(@PathVariable("lang") String lang,
            @RequestBody BusquedaHttp.Request request) throws Exception {
        return this.busquedaService.crear(request);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<BusquedaHttp.Response> actualizar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id, @RequestBody BusquedaHttp.Request request) throws Exception {
        return this.busquedaService.actualizar(lang, id, request);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<BusquedaHttp.Response> eliminar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id) throws Exception {
        return this.busquedaService.eliminar(lang, id);
    }

    @GetMapping(path = "")
    public ResponseEntity<BusquedaHttp.PageResponse> obtener(@PathVariable("lang") String lang,
            @RequestParam(name = "pagina", defaultValue = "0", required = true) Integer pagina,
            @RequestParam(name = "tamanio", defaultValue = "5", required = true) Integer tamanio) throws Exception {
        return this.busquedaService.obtener(lang, pagina, tamanio);
    }

}
