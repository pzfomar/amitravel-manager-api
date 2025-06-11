package com.amitravel.usuario;

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
@RequestMapping("{lang}/usuario")
public class UsuarioRestController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(path = "")
    public ResponseEntity<UsuarioHttp.Response> crear(@PathVariable("lang") String lang,
            @RequestBody UsuarioHttp.Request request) throws Exception {
        return this.usuarioService.crear(lang, request);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<UsuarioHttp.Response> actualizar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id, @RequestBody UsuarioHttp.Request request) throws Exception {
        return this.usuarioService.actualizar(lang, id, request);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<UsuarioHttp.Response> eliminar(@PathVariable("lang") String lang, @PathVariable("id") Long id)
            throws Exception {
        return this.usuarioService.eliminar(lang, id);
    }

    @GetMapping(path = "")
    public ResponseEntity<UsuarioHttp.PageResponse> obtener(@PathVariable("lang") String lang,
            @RequestParam(name = "pagina", defaultValue = "0", required = true) Integer pagina,
            @RequestParam(name = "tamanio", defaultValue = "5", required = true) Integer tamanio,
            @RequestParam(name = "busqueda", required = false) String busqueda,
            @RequestParam(name = "id", required = false) Long id) throws Exception {
        return this.usuarioService.obtener(lang, pagina, tamanio, busqueda, id);
    }

    @GetMapping(path = "lista")
    public ResponseEntity<List<UsuarioHttp.Response>> obtener(@PathVariable("lang") String lang) throws Exception {
        return this.usuarioService.obtener(lang);
    }
}
