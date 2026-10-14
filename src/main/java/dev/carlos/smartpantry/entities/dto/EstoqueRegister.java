package dev.carlos.smartpantry.entities.dto;

import dev.carlos.smartpantry.entities.Ingrediente;
import dev.carlos.smartpantry.entities.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EstoqueRegister(String nomeIngrediente, BigDecimal quantidade, String dataValidade, String status) {
}
