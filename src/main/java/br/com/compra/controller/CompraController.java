package br.com.compra.controller;

import br.com.compra.model.Compra;
import br.com.compra.service.CompraService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping("/compra")
    private ResponseEntity<?> salvarCompra(@RequestBody Compra compra) {
        try {
            return new ResponseEntity<>(compraService.salvarCompra(compra), HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/health")
    private ResponseEntity<?> health() {
        return new ResponseEntity<>("Estou funcionando!", HttpStatus.OK);
    }
}
