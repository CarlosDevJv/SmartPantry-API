package dev.carlos.smartpantry.entities.dto;

import java.time.LocalDate;

public record IngredienteVencimento(String ingrediente, LocalDate dataValidade) {
}
