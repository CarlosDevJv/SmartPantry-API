package dev.carlos.smartpantry.controller;

import dev.carlos.smartpantry.entities.dto.EstoqueRegister;
import dev.carlos.smartpantry.entities.dto.IngredienteRegister;
import dev.carlos.smartpantry.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    EstoqueService estoqueService;

    @PostMapping("/insertEstoque/{email}")
    public void insertIn(@PathVariable String email, @RequestBody EstoqueRegister estoqueRegister){
        estoqueService.estoqueInsert(email, estoqueRegister);
    }

    @PostMapping("/insertAllEstoque/{email}")
    public void insertAll(@PathVariable String email, @RequestBody List<EstoqueRegister> estoqueRegister){
        estoqueService.estoqueInsertAll(email, estoqueRegister);
    }

//    @GetMapping("/listaIngredientes/{email}")
//    public ResponseEntity getList(@PathVariable String email){
//        System.out.println(estoqueService.findAllByUser(email));
//        return ResponseEntity.ok().body(estoqueService.findAllByUser(email));
//    }

//    @GetMapping("/validadeIngredientes/{email}")
//    public ResponseEntity validade(@PathVariable String email){
//        System.out.println(estoqueService.vencimentoAutomatico(email));
//        return ResponseEntity.ok().body(estoqueService.vencimentoAutomatico(email));
//    }
}
