package io.rayjir.finand.service;

import io.rayjir.finand.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService{
    private final UsuarioService service;

    public Usuario getLogedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails  = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return service.getUsuario(username);
    }
}