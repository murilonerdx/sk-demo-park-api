package com.github.murilonerdx.skdemoparkapi.jwt;

import com.github.murilonerdx.skdemoparkapi.entity.Usuario;
import com.github.murilonerdx.skdemoparkapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new JwtUserDetails(usuarioService.findByUsername(username));
    }

    public JwtToken getTokenAuthenticated(String username){
        Usuario.Role roleByUsername = usuarioService.findByUsername(username).getRole();
        return JwtUtils.createToken(username, roleByUsername.name().substring("ROLE_".length()));
    }
}
