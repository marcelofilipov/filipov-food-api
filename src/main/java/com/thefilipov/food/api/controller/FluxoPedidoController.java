package com.thefilipov.food.api.controller;

import com.thefilipov.food.api.openapi.controller.FluxoPedidoControllerDocumentation;
import com.thefilipov.food.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = FluxoPedidoController.URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoPedidoControllerDocumentation {

    public static final String URI = "/pedidos/{codigoPedido}";

    @Autowired
    private FluxoPedidoService fluxoPedido;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
        fluxoPedido.confirmar(codigoPedido);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
        fluxoPedido.cancelar(codigoPedido);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
        fluxoPedido.entregar(codigoPedido);

        return ResponseEntity.noContent().build();
    }

}
