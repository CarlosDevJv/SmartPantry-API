package dev.carlos.smartpantry.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.carlos.smartpantry.entities.enums.StatusIngrediente;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "itemEstoque")
public class ItemEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;
    @ManyToOne
    @JoinColumn(name = "ingrediente_id")
    private Ingrediente ingrediente_id;
    private BigDecimal quantidade;
    @Column(name = "datavalidade")
    private LocalDate dataValidade;
    @Enumerated(EnumType.STRING)
    private StatusIngrediente status;


    public String toStringEmail(){
        return ingrediente_id.getNome() + " Validade: " + dataValidade;
    }

    public ItemEstoque(String ingrediente, LocalDate dataValidade){
        this.dataValidade = dataValidade;
    }

}
