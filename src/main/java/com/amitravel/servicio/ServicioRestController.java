package com.amitravel.servicio;

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
@RequestMapping("{lang}/servicio")
public class ServicioRestController {

    @Autowired
    private ServicioService servicioService;

    @PostMapping(path = "")
    public ResponseEntity<ServicioHttp.Response> crear(@PathVariable("lang") String lang,
            @RequestBody ServicioHttp.Request request) throws Exception {
        return this.servicioService.crear(lang, request);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ServicioHttp.Response> actualizar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id, @RequestBody ServicioHttp.Request request) throws Exception {
        return this.servicioService.actualizar(lang, id, request);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ServicioHttp.Response> eliminar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id)
            throws Exception {
        return this.servicioService.eliminar(lang, id);
    }

    @GetMapping(path = "")
    public ResponseEntity<ServicioHttp.PageResponse> obtener(@PathVariable("lang") String lang,
            @RequestParam(name = "pagina", defaultValue = "0", required = true) Integer pagina,
            @RequestParam(name = "tamanio", defaultValue = "5", required = true) Integer tamanio,
            @RequestParam(name = "busqueda", required = false) String busqueda) throws Exception {
        return this.servicioService.obtener(lang, pagina, tamanio, busqueda);
    }

    @GetMapping(path = "lista")
    public ResponseEntity<List<ServicioHttp.Response>> obtener(@PathVariable("lang") String lang) throws Exception {
        return this.servicioService.obtener(lang);
    }

}
