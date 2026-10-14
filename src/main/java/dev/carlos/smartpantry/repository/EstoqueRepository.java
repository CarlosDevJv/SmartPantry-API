package dev.carlos.smartpantry.repository;

import dev.carlos.smartpantry.entities.Ingrediente;
import dev.carlos.smartpantry.entities.ItemEstoque;
import dev.carlos.smartpantry.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<ItemEstoque, Long> {

    @Query("""
                    SELECT estoque
                    FROM ItemEstoque estoque
                    WHERE estoque.user_id = :user
                    AND estoque.ingrediente_id = :ingrediente
                    """)
    Optional<ItemEstoque> findByUser_idAndIngrediente_id(
            @Param("user") User user,
            @Param("ingrediente") Ingrediente ingrediente);


    @Query("""
            SELECT estoque.ingrediente_id.nome
            FROM ItemEstoque as estoque
            WHERE estoque.user_id = :user
            """)
    List<String>findAllByUser_id(
            @Param("user") User user_id);
}
