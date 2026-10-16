package dev.carlos.smartpantry.repository;

import dev.carlos.smartpantry.entities.Ingrediente;
import dev.carlos.smartpantry.entities.ItemEstoque;
import dev.carlos.smartpantry.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<ItemEstoque, Long> {

    @Query("""
            SELECT estoque
            FROM ItemEstoque estoque
            WHERE estoque.dataValidade < :agora
            AND estoque.status in ('DISPONIVEL', 'disponivel')
            """)
    List<ItemEstoque> prazoVencimento(
            @Param("agora") LocalDate agora);

    @Query("""
                    SELECT estoque
                    FROM ItemEstoque estoque
                    WHERE estoque.ingrediente_id = :ingrediente
                    """)
    Optional<ItemEstoque> findByUser_idAndIngrediente_id(
            @Param("ingrediente") Ingrediente ingrediente);


    @Query("""
            SELECT estoque.ingrediente_id.nome
            FROM ItemEstoque as estoque
            """)
    List<String>findAllIngridientes();
}
