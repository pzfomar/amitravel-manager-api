package com.amitravel.aficion;

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
@RequestMapping("{lang}/aficion")
public class AficionRestController {

    @Autowired
    private AficionService aficionService;

    @PostMapping(path = "")
    public ResponseEntity<AficionHttp.Response> crear(@PathVariable("lang") String lang,
            @RequestBody AficionHttp.Request request) throws Exception {
        return this.aficionService.crear(lang, request);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<AficionHttp.Response> actualizar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id, @RequestBody AficionHttp.Request request) throws Exception {
        return this.aficionService.actualizar(lang, id, request);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<AficionHttp.Response> eliminar(@PathVariable("lang") String lang, @PathVariable("id") Long id)
            throws Exception {
        return this.aficionService.eliminar(lang, id);
    }

    @GetMapping(path = "")
    public ResponseEntity<AficionHttp.PageResponse> obtener(@PathVariable("lang") String lang,
            @RequestParam(name = "pagina", defaultValue = "0", required = true) Integer pagina,
            @RequestParam(name = "tamanio", defaultValue = "5", required = true) Integer tamanio) throws Exception {
        return this.aficionService.obtener(lang, pagina, tamanio);
    }
}
