package dev.carlos.smartpantry.service;

import dev.carlos.smartpantry.entities.Ingrediente;
import dev.carlos.smartpantry.entities.dto.IngredienteRegister;
import dev.carlos.smartpantry.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredienteService {
    @Autowired
    IngredienteRepository ingredienteRepository;

    public void insert(IngredienteRegister ingredienteRegister){
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setCategoria(ingredienteRegister.categoria());
        ingrediente.setNome(ingredienteRegister.nome());
        ingrediente.setUnidademedida(ingredienteRegister.unidadeMedida());
        ingredienteRepository.save(ingrediente);
    }

    public void insertAll(List<IngredienteRegister> list){
        List<Ingrediente> ingredientes = list.stream()
                .map(ingredienteRegister -> converterDto(ingredienteRegister))
                .toList();
        ingredienteRepository.saveAll(ingredientes);
    }

    public Ingrediente converterDto(IngredienteRegister ingredienteRegister){
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setUnidademedida(ingredienteRegister.unidadeMedida());
        ingrediente.setNome(ingredienteRegister.nome());
        ingrediente.setCategoria(ingredienteRegister.categoria());
        return ingrediente;
    }
}
