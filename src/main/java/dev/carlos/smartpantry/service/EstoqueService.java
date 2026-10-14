package dev.carlos.smartpantry.service;

import dev.carlos.smartpantry.entities.Ingrediente;
import dev.carlos.smartpantry.entities.ItemEstoque;
import dev.carlos.smartpantry.entities.User;
import dev.carlos.smartpantry.entities.dto.EstoqueRegister;
import dev.carlos.smartpantry.entities.enums.StatusIngrediente;
import dev.carlos.smartpantry.repository.EstoqueRepository;
import dev.carlos.smartpantry.repository.IngredienteRepository;
import dev.carlos.smartpantry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EstoqueService {
    @Autowired
    EstoqueRepository estoqueRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IngredienteRepository ingredienteRepository;


    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void estoqueInsert(String email, EstoqueRegister estoqueRegister){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não cadastrado"));
        Ingrediente ingrediente = ingredienteRepository.findByNome(estoqueRegister.nomeIngrediente()).orElseThrow(() -> new IllegalArgumentException("Ingrediente não cadastrado"));
        if (user != null && ingrediente != null){
            ItemEstoque estoque = new ItemEstoque();
            estoque.setUser_id(user);
            estoque.setIngrediente_id(ingrediente);
            estoque.setStatus(StatusIngrediente.valueOf(estoqueRegister.status()));
            estoque.setQuantidade(estoqueRegister.quantidade());
            estoque.setDataValidade(LocalDate.parse(estoqueRegister.dataValidade(), dateTimeFormatter));
            estoqueRepository.save(estoque);
        }

    }

    public void estoqueInsertAll(String email, List<EstoqueRegister> listEstoqueRegister){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não cadastrado"));

        List<Ingrediente> ingredientes = converterIngredientes(listEstoqueRegister);
        if (ingredientes != null){
            for (int i = 0; i < ingredientes.size(); i++) {
                ItemEstoque estoque = new ItemEstoque();
                if (estoqueRepository.findByUser_idAndIngrediente_id(user, ingredientes.get(i)).isEmpty()) {
                    estoque.setUser_id(user);
                    estoque.setIngrediente_id(ingredientes.get(i));
                    estoque.setStatus(StatusIngrediente.valueOf(listEstoqueRegister.get(i).status()));
                    estoque.setQuantidade(listEstoqueRegister.get(i).quantidade());
                    estoque.setDataValidade(LocalDate.parse(listEstoqueRegister.get(i).dataValidade(), dateTimeFormatter));
                    estoqueRepository.save(estoque);
                }
                else {
                    estoque = estoqueRepository.findByUser_idAndIngrediente_id(user, ingredientes.get(i)).orElseThrow();
                    estoque.setQuantidade(estoque.getQuantidade().add(listEstoqueRegister.get(i).quantidade()));
                    estoque.setDataValidade(LocalDate.parse(listEstoqueRegister.get(i).dataValidade(), dateTimeFormatter));
                    estoqueRepository.save(estoque);
                }
            }
        }

    }

    public List<String> findAllByUser(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("USUÁRIO NÃO CADASTRADO"));
       estoqueRepository.findAllByUser_id(user);
        return estoqueRepository.findAllByUser_id(user);
    }

    public List<Ingrediente> converterIngredientes(List<EstoqueRegister> listEstoqueRegister){
        return listEstoqueRegister.stream().map(ingrediente -> ingredienteRepository.findByNome(ingrediente.nomeIngrediente()).orElseThrow(()-> new IllegalArgumentException("Ingrediente não cadastrado")))
                .toList();
    }

}
