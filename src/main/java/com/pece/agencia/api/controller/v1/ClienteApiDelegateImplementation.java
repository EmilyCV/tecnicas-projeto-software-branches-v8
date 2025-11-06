package com.pece.agencia.api.controller.v1;

import com.pece.agencia.api.controller.v1.dto.Cliente;
import com.pece.agencia.api.controller.v1.dto.Compra;
import com.pece.agencia.api.controller.v1.mapper.ClienteMapper;
import com.pece.agencia.api.controller.v1.mapper.CompraMapper;
import com.pece.agencia.api.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClienteApiDelegateImplementation implements ClienteApiDelegate {
    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;
    private final CompraMapper compraMapper;

    @Override
    public ResponseEntity<Cliente> buscarClientePorId(UUID id) {
        return clienteService.findById(id)
                             .map(clienteMapper::toDTO)
                             .map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Compra> buscarCompraDeClientePorId(UUID idCliente, UUID idCompra) {
        return clienteService.findCompraById(idCliente, idCompra)
                             .map(compraMapper::toDTO)
                             .map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Compra>> buscarComprasDeCliente(UUID id, Pageable pageable) {
        Page<Compra> page = clienteService.findAllCompras(id, pageable).map(compraMapper::toDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Override
    public ResponseEntity<List<Cliente>> listarTodosClientes(Pageable pageable) {
        Page<Cliente> page = clienteService.findAll(pageable)
                                            .map(clienteMapper::toDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
