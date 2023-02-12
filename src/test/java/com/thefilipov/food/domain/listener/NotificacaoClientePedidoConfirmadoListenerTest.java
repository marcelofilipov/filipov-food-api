package com.thefilipov.food.domain.listener;

import com.thefilipov.food.domain.event.PedidoConfirmadoEvent;
import com.thefilipov.food.domain.model.Endereco;
import com.thefilipov.food.domain.model.Pedido;
import com.thefilipov.food.domain.model.Restaurante;
import com.thefilipov.food.domain.model.Usuario;
import com.thefilipov.food.domain.service.EnvioEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificacaoClientePedidoConfirmadoListenerTest {

    @Mock
    private EnvioEmailService mockEnvioEmail;

    @InjectMocks
    private NotificacaoClientePedidoConfirmadoListener notificacaoClientePedidoConfirmadoListenerUnderTest;

    @Test
    void testAoConfirmarPedido() {
        // Setup
        final Pedido pedido = new Pedido();
        pedido.setId(0L);
        pedido.setCodigo("codigo");
        pedido.setSubTotal(new BigDecimal("0.00"));
        pedido.setTaxaFrete(new BigDecimal("0.00"));
        pedido.setValorTotal(new BigDecimal("0.00"));
        final Endereco enderecoEntrega = new Endereco();
        enderecoEntrega.setCep("cep");
        enderecoEntrega.setLogradouro("logradouro");
        enderecoEntrega.setNumero("numero");
        enderecoEntrega.setComplemento("complemento");
        enderecoEntrega.setBairro("bairro");
        pedido.setEnderecoEntrega(enderecoEntrega);
        final Restaurante restaurante = new Restaurante();
        restaurante.setNome("nome");
        pedido.setRestaurante(restaurante);
        final Usuario cliente = new Usuario();
        cliente.setEmail("email");
        pedido.setCliente(cliente);
        final PedidoConfirmadoEvent event = new PedidoConfirmadoEvent(pedido);

        // Run the test
        notificacaoClientePedidoConfirmadoListenerUnderTest.aoConfirmarPedido(event);

        // Verify the results
        verify(mockEnvioEmail).enviar(any(EnvioEmailService.Mensagem.class));
    }
}
