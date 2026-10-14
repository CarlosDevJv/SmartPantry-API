package dev.carlos.smartpantry.controller;

import dev.carlos.smartpantry.entities.Ingrediente;
import dev.carlos.smartpantry.entities.dto.IngredienteRegister;
import dev.carlos.smartpantry.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingrediente")
public class IngredienteController {

    @Autowired
    IngredienteService ingredienteService;

    @PostMapping("/createIngrediente")
    public ResponseEntity insertIn(@RequestBody List<IngredienteRegister> ingrediente){
        ingredienteService.insertAll(ingrediente);
        return ResponseEntity.ok().build();
    }
}
