package io.rayjir.finand.service;

import io.rayjir.finand.entity.Usuario;

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