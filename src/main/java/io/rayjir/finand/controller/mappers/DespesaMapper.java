package io.rayjir.finand.controller.mappers;

import io.rayjir.finand.controller.dto.DespesaDTO;
import io.rayjir.finand.entity.Despesa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DespesaMapper {

    Despesa toEnity(DespesaDTO dto);

    DespesaDTO toDTO(Despesa despesa);
}
