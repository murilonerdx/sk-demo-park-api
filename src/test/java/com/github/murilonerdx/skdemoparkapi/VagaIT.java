package com.github.murilonerdx.skdemoparkapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.github.murilonerdx.skdemoparkapi.dto.VagaCreateDTO;
import com.github.murilonerdx.skdemoparkapi.entity.Cliente;
import com.github.murilonerdx.skdemoparkapi.entity.ClienteVaga;
import com.github.murilonerdx.skdemoparkapi.entity.Usuario;
import com.github.murilonerdx.skdemoparkapi.entity.Vaga;
import com.github.murilonerdx.skdemoparkapi.entity.enums.StatusVagaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class VagaIT {

    @Autowired
    WebTestClient testClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() throws IOException {
        mongoTemplate.dropCollection(ClienteVaga.class);
        mongoTemplate.dropCollection(Usuario.class);
        mongoTemplate.dropCollection(Vaga.class);
        mongoTemplate.dropCollection(Cliente.class);

        InputStream etacionamentoStream = new ClassPathResource("/json/estacionamentos/estacionamentos-insert.json").getInputStream();
        String jsonEstacionamento = new BufferedReader(new InputStreamReader(etacionamentoStream))
                .lines().collect(Collectors.joining("\n"));

        InputStream usuarioStream = new ClassPathResource("/json/usuarios/usuarios-insert.json").getInputStream();
        String jsonUsuario = new BufferedReader(new InputStreamReader(usuarioStream))
                .lines().collect(Collectors.joining("\n"));


        InputStream clientesStream = new ClassPathResource("/json/clientes/clientes-insert.json").getInputStream();
        String jsonClientes = new BufferedReader(new InputStreamReader(clientesStream))
                .lines().collect(Collectors.joining("\n"));

        InputStream vagasStream = new ClassPathResource("/json/vagas/vagas-insert.json").getInputStream();
        String jsonVagas = new BufferedReader(new InputStreamReader(vagasStream))
                .lines().collect(Collectors.joining("\n"));

        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.registerModule(module);
        ClienteVaga[] estacionamentos = mapper.readValue(jsonEstacionamento, ClienteVaga[].class);
        Usuario[] usuarios = mapper.readValue(jsonUsuario, Usuario[].class);
        Vaga[] vagas = mapper.readValue(jsonVagas, Vaga[].class);
        Cliente[] clientes = mapper.readValue(jsonClientes, Cliente[].class);
        Arrays.stream(usuarios).forEach(mongoTemplate::save);
        Arrays.stream(clientes).forEach(mongoTemplate::save);
        Arrays.stream(vagas).forEach(mongoTemplate::save);
        Arrays.stream(estacionamentos).forEach(mongoTemplate::save);
    }

    @Test
    public void criarVaga_ComDadosValidos_RetornarLocationStatus201() {
        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .bodyValue(new VagaCreateDTO("A-05", StatusVagaDTO.LIVRE))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }


    @Test
    public void buscarVaga_ComCodigoExistente_RetornarVagaComStatus200() {
        testClient
                .get()
                .uri("/api/v1/vagas/codigo/{codigo}", "A-01")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(10)
                .jsonPath("codigo").isEqualTo("A-01")
                .jsonPath("status").isEqualTo("LIVRE");

    }

    @Test
    public void buscarVaga_ComCodigoInexistente_RetornarErrorMessageComStatus404() {
        testClient
                .get()
                .uri("/api/v1/vagas/{codigo}", "A-10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/vagas/A-10");
    }

    @Test
    public void buscarVaga_ComUsuarioSemPermissaoDeAcesso_RetornarErrorMessageComStatus403() {
        testClient
                .get()
                .uri("/api/v1/vagas/{codigo}", "A-01")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/vagas/A-01");
    }

    @Test
    public void criarVaga_ComUsuarioSemPermissaoDeAcesso_RetornarErrorMessageComStatus403() {
        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .bodyValue(new VagaCreateDTO("A-05", StatusVagaDTO.OCUPADA))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vagas");
    }

}
