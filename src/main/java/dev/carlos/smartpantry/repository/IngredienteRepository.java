package dev.carlos.smartpantry.repository;

import dev.carlos.smartpantry.entities.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    Optional<Ingrediente> findByNome(String nome);
}
