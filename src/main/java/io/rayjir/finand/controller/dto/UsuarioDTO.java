package io.rayjir.finand.controller.dto;

import java.util.List;
import java.util.UUID;

public record UsuarioDTO(UUID id, String username, String password, List<String> roles) {
    
}
