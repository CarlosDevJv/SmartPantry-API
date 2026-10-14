package dev.carlos.smartpantry.entities;

import dev.carlos.smartpantry.entities.dto.ReceitaRegister;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "receita")
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;
    private String titulo;
    @Column(name = "preparo")
    private String modoPreparo;
    @Column(name = "datacriacao")
    private LocalDate dataCriacao;
    private String ingredientes;



    public Receita(ReceitaRegister receitaRegister){
        setIngredientes(receitaRegister.ingredientes());
        setTitulo(receitaRegister.titulo());
        setDataCriacao(LocalDate.now());
        setModoPreparo(receitaRegister.preparo());
    }
}
