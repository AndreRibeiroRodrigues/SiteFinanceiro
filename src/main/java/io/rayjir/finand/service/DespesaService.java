package io.rayjir.finand.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.rayjir.finand.entity.Despesa;
import io.rayjir.finand.entity.Usuario;
import io.rayjir.finand.repository.FinanceiroRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class DespesaService {
    
    private final FinanceiroRepository repository;
    private final UsuarioService service;

    public void postDespesa(Despesa despesa, String username){
        Usuario user = service.getUsuario(username);
        despesa.setUsuarioId(user.getId());
        repository.save(despesa);
    }

    public List<Despesa> getDespesas(String username){
        Usuario user = service.getUsuario(username);
        return repository.findAllByUsuarioId(user.getId());
    }
}
