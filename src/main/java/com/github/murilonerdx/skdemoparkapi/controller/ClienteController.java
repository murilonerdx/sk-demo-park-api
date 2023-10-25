package com.github.murilonerdx.skdemoparkapi.controller;

import com.github.murilonerdx.skdemoparkapi.dto.*;
import com.github.murilonerdx.skdemoparkapi.entity.Cliente;
import com.github.murilonerdx.skdemoparkapi.exception.ErrorMessage;
import com.github.murilonerdx.skdemoparkapi.jwt.JwtUserDetails;
import com.github.murilonerdx.skdemoparkapi.service.ClienteService;
import com.github.murilonerdx.skdemoparkapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @PreAuthorize("hasRole('CUSTOMER') OR hasRole('ADMIN')")
    @Operation(summary = "Criar um novo cliente", description = "Recurso para criar um novo cliente",
            security = @SecurityRequirement(name = "security_auth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
                    @ApiResponse(responseCode = "409", description = "Cliente e-mail já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> create(@RequestBody @Valid ClienteCreateDTO cliente, @AuthenticationPrincipal JwtUserDetails userDetails){
        Cliente clienteCreateDTO = cliente.toModel();
        clienteCreateDTO.setUsuario(usuarioService.getById(userDetails.getId()).toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
    }

    @Operation(summary = "Recuperar lista de clientes",
            description = "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN' ",
            security = @SecurityRequirement(name = "security_auth"),
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Representa a página retornada"
                    ),
                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5")),
                            description = "Representa o total de elementos por página"
                    ),
                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "nome,asc")),
                            description = "Representa a ordenação dos resultados. Aceita multiplos critérios de ordenação são suportados.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClienteResponseDTO.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Cliente>> getAll(@Parameter(hidden = true)
                                              @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
        Page<Cliente> clientes = clienteService.getAll(pageable);
        return ResponseEntity.ok(clientes);
    }

    @PreAuthorize(
            "hasHole('ADMIN') OR (hasHole('CUSTOMER') AND #id == authentication.principal.id)"
    )
    @Operation(summary = "Recuperar um cliente pelo id", description = "Recuperar um cliente pelo id",
            security = @SecurityRequirement(name = "security_auth"),
            responses = {
                    @ApiResponse(responseCode = "403", description = "Erro ao recuperar cliente pelo id",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getById(@PathVariable("id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getById(id));
    }

    @Operation(summary = "Recuperar dados do cliente autenticado",
            description = "Requisição exige uso de um bearer token. Acesso restrito a Role='CLIENTE'",
            security = @SecurityRequirement(name = "security_auth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClienteResponseDTO.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de ADMIN",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping("/detalhes")
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('ADMIN')")
    public ResponseEntity<?> getDetalhes(@AuthenticationPrincipal JwtUserDetails userDetails) {
        if(userDetails.getId() == null){
            return ResponseEntity.ok(ErrorMessage.builder().message("Cliente id não encontrado").build());
        }

        ClienteResponseDTO cliente = clienteService.buscarPorUsuarioId(userDetails.getId());
        return ResponseEntity.ok(cliente);
    }
}
