package com.github.murilonerdx.skdemoparkapi.controller;

import com.github.murilonerdx.skdemoparkapi.dto.ClienteResponseDTO;
import com.github.murilonerdx.skdemoparkapi.dto.UsuarioCreateDTO;
import com.github.murilonerdx.skdemoparkapi.dto.VagaCreateDTO;
import com.github.murilonerdx.skdemoparkapi.dto.VagaDTO;
import com.github.murilonerdx.skdemoparkapi.entity.Vaga;
import com.github.murilonerdx.skdemoparkapi.entity.enums.StatusVaga;
import com.github.murilonerdx.skdemoparkapi.exception.ErrorMessage;
import com.github.murilonerdx.skdemoparkapi.jwt.JwtUserDetails;
import com.github.murilonerdx.skdemoparkapi.service.VagaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@RestController
@RequestMapping("/api/v1/vagas")
@RequiredArgsConstructor
public class VagaController {
    private final VagaService service;

    @Operation(summary = "Recuperar lista de vagas",
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
    public ResponseEntity<List<VagaDTO>> findall(@Parameter(hidden = true)
                              @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @Operation(summary = "Recuperar lista de vagas em aberto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClienteResponseDTO.class))
                    )
            })
    @GetMapping("/vagas-livres")
    public ResponseEntity<List<VagaDTO>> findallVagaLivre(){
        return ResponseEntity.ok().body(service.findVagaLivre());
    }

    @Operation(summary = "Recuperar uma vaga pelo id", description = "Recuperar uma vaga pelo id",
            responses = {
                    @ApiResponse(responseCode = "403", description = "Erro ao recuperar  vaga pelo id",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vaga.class))),
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vaga.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Vaga> getById(@PathVariable("id") String id){
        return ResponseEntity.ok().body(service.getById(id));
    }

    @Operation(summary = "Atualizar uma vaga pelo id", description = "Atualizar uma vaga pelo id",
            security = @SecurityRequirement(name = "security_auth"),
            responses = {
                    @ApiResponse(responseCode = "403", description = "Erro ao recuperar  vaga pelo id",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vaga.class))),
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vaga.class)))
            })
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Vaga> updateVaga(@PathVariable("id") String id, @RequestParam StatusVaga statusVaga, @AuthenticationPrincipal JwtUserDetails userDetails){
        return ResponseEntity.ok().body(service.updateStatusVaga(id, statusVaga, userDetails.getUsername()));
    }

    @Operation(summary = "Buscar por codigo", description = "Recurso para buscar vaga por codigo",
            security = @SecurityRequirement(name = "security_auth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioCreateDTO.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER')")
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Vaga> getCodigo(@PathVariable("codigo") String codigo){
        return ResponseEntity.ok().body(service.getByCodigo(codigo));
    }

    @Operation(summary = "Criar uma vaga", description = "Recurso para criar um novo vaga",
            security = @SecurityRequirement(name = "security_auth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioCreateDTO.class))),
                    @ApiResponse(responseCode = "409", description = "vaga já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<VagaDTO> create(VagaCreateDTO vaga, @AuthenticationPrincipal JwtUserDetails userDetails) {
        return ResponseEntity.ok().body(service.create(vaga.toModel(userDetails.getUsername())));
    }

}
