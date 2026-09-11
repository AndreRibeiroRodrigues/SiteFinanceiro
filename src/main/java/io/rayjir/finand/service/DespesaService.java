package io.rayjir.finand.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Despesa postDespesa(Despesa despesa, Usuario usuario){
        
        despesa.setUsuario(usuario);
        return repository.save(despesa);
    }

    public List<Despesa> getDespesas(Usuario user){
        return repository.findAllByUsuarioId(user.getId());
    }

    public void deleteDespesa(UUID id, Usuario user){
        Despesa despesa = repository.findById(id);
        if(despesa.getUsuario().getId() == user.getId()){
            repository.delete(id);
        }
        
    }
    
}
