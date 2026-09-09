package io.rayjir.finand.controller.dto;

import io.rayjir.finand.entity.FormaPagamento;
import io.rayjir.finand.entity.Status;

import java.util.Date;
import java.util.UUID;

public record DespesaDTO(UUID id, UUID usuarioId, Date date, String category, String subcategory, String description, double value, Status status, FormaPagamento paymentMethod, String observation) {
}
