package io.rayjir.finand.controller;

import java.util.List;
import java.util.UUID;

import io.rayjir.finand.controller.dto.DespesaDTO;
import io.rayjir.finand.controller.mappers.DespesaMapper;
import io.rayjir.finand.repository.FinanceiroRepository;
import io.rayjir.finand.service.DespesaService;
import io.rayjir.finand.entity.Despesa;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/despesas")
@RequiredArgsConstructor
public class DespesasController {

    private final FinanceiroRepository financeiroRepository;

    private final DespesaService despesaservice;
    private final DespesaMapper mapper;

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Despesa>> getDespesas(Authentication authentication) {
        UserDetails usuarioLogado = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(despesaservice.getDespesas(usuarioLogado.getUsername()));
    }

    @PostMapping("/postDespesa")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Despesa> postDespesa(
        @RequestBody DespesaDTO dto,
        Authentication authentication) {
        UserDetails usuarioLogado = (UserDetails) authentication.getPrincipal();
        Despesa salva = despesaservice.postDespesa(mapper.toEnity(dto), usuarioLogado.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Despesa> putDespesa(
            @PathVariable("id") UUID id,
            @RequestBody DespesaDTO dto) {
        Despesa entity = mapper.toEnity(dto);
        return financeiroRepository.findById(id)
                .map(despesa -> {
                    despesa.setDate(entity.getDate());
                    despesa.setCategory(entity.getCategory());
                    despesa.setSubcategory(entity.getSubcategory());
                    despesa.setDescription(entity.getDescription());
                    despesa.setValue(entity.getValue());
                    despesa.setStatus(entity.getStatus());
                    despesa.setPaymentMethod(entity.getPaymentMethod());
                    despesa.setObservation(entity.getObservation());
                    return ResponseEntity.ok(financeiroRepository.save(despesa));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteDespesa(@PathVariable("id") UUID id) {
        if (!financeiroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        financeiroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
