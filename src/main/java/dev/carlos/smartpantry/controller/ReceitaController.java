package dev.carlos.smartpantry.controller;

import dev.carlos.smartpantry.entities.dto.ReceitaRegister;
import dev.carlos.smartpantry.service.ReceitaService;
import org.springframework.ai.chat.model.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {
    @Autowired
    ReceitaService receitaService;


    @GetMapping("/TestReceita/{email}")
public ResponseEntity<ReceitaRegister> insertIn(@PathVariable String email){
        receitaService.registrarReceita(email);
        return ResponseEntity.ok().build();
    }
}
