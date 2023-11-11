package com.github.murilonerdx.skdemoparkapi.service;

import com.github.murilonerdx.skdemoparkapi.dto.utils.EstacionamentoUtils;
import com.github.murilonerdx.skdemoparkapi.entity.Cliente;
import com.github.murilonerdx.skdemoparkapi.entity.ClienteVaga;
import com.github.murilonerdx.skdemoparkapi.entity.Vaga;
import com.github.murilonerdx.skdemoparkapi.entity.enums.StatusVaga;
import com.github.murilonerdx.skdemoparkapi.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService vagaService;

    @Transactional
    public ClienteVaga checkIn(ClienteVaga clienteVaga) {
        Cliente cliente = clienteService.findByCpfModel(clienteVaga.getCliente().getCpf());
        clienteVaga.setCliente(cliente);

        Vaga vaga = vagaService.findVagaLivreModel().stream().findFirst().orElseThrow(() -> new NotFoundException("Vaga não existe"));
        vaga.setStatus(StatusVaga.OCUPADA);
        clienteVaga.setVaga(vaga);

        clienteVaga.setDataEntrada(LocalDateTime.now());

        clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

        return clienteVagaService.salvar(clienteVaga);
    }

    @Transactional
    public ClienteVaga checkOut(String recibo) {
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);

        LocalDateTime dataSaida = LocalDateTime.now();

        BigDecimal valor = EstacionamentoUtils.calcularCusto(clienteVaga.getDataEntrada(), dataSaida);
        clienteVaga.setValor(valor);

        long totalDeVezes = clienteVagaService.getTotalDeVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf());

        BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor, totalDeVezes);
        clienteVaga.setDesconto(desconto);

        clienteVaga.setDataSaida(dataSaida);
        clienteVaga.getVaga().setStatus(StatusVaga.LIVRE);

        return clienteVagaService.salvar(clienteVaga);
    }
}
