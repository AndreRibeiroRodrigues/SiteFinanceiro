package io.rayjir.finand.controller;

import java.util.List;
import java.util.UUID;

import io.rayjir.finand.controller.dto.DespesaDTO;
import io.rayjir.finand.controller.mappers.DespesaMapper;
import io.rayjir.finand.repository.FinanceiroRepository;
import io.rayjir.finand.service.DespesaService;
import io.rayjir.finand.service.SecurityService;
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

    private final DespesaService despesaService;
    private final DespesaMapper mapper;
    private final SecurityService securityService;

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Despesa>> getDespesas() {
        return ResponseEntity.ok(despesaService.getDespesas(securityService.getLogedUser()));
    }

    @PostMapping("/postDespesa")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Despesa> postDespesa(
        @RequestBody DespesaDTO dto) {
        Despesa salva = despesaService.postDespesa(
            mapper.toEnity(dto), 
            securityService.getLogedUser()
    );
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
    public ResponseEntity<Void> deleteDespesa(@PathVariable("id") UUID id ) {
        try {
            if (!financeiroRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }else{
                despesaService.deleteDespesa(id, securityService.getLogedUser());
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
