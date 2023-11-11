package com.github.murilonerdx.skdemoparkapi;

import static com.github.murilonerdx.skdemoparkapi.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Collections;

import com.github.murilonerdx.skdemoparkapi.controller.UsuarioController;
import com.github.murilonerdx.skdemoparkapi.dto.UsuarioCreateDTO;
import com.github.murilonerdx.skdemoparkapi.dto.UsuarioDTO;
import com.github.murilonerdx.skdemoparkapi.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ExtendWith(MockitoExtension.class)
class AutenticacaoIT {
    static String USER_API = "/api/v1/users";


    @Mock
    UsuarioService userService;


    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc =
                MockMvcBuilders.standaloneSetup(usuarioController)
                        .setCustomArgumentResolvers(
                                new PageableHandlerMethodArgumentResolver())
                        .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                        .build();
    }

    @Test
    @DisplayName("Deve criar um usuario com sucesso")
    void whenPOSTIsCalledThenStatusCreatedShouldBeInformed() throws Exception {
        UsuarioDTO expectedCreated = buildResponse();

        when(userService.save(buildCreate()))
                .thenReturn(expectedCreated);

        mockMvc
                .perform(post(USER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(buildCreate())))
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath("$.id", is("teste-123")))
                .andExpect(jsonPath("$.username", is("murilonerdx")));

    }

    public String passwordEncoder(String senha){
        return new BCryptPasswordEncoder().encode(senha);
    }

    private UsuarioCreateDTO buildCreate() {
        return UsuarioCreateDTO.builder().username("murilonerdx").password(passwordEncoder("123")).build();
    }

    private UsuarioDTO buildResponse() {
        return UsuarioDTO.builder().id("teste-123").username("murilonerdx").password(passwordEncoder("123")).build();
    }

}
