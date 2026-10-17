package dev.carlos.smartpantry.service;

import dev.carlos.smartpantry.entities.Ingrediente;
import dev.carlos.smartpantry.entities.ItemEstoque;
import dev.carlos.smartpantry.entities.User;
import dev.carlos.smartpantry.entities.dto.EstoqueRegister;
import dev.carlos.smartpantry.entities.dto.IngredienteVencimento;
import dev.carlos.smartpantry.entities.enums.StatusIngrediente;
import dev.carlos.smartpantry.repository.EstoqueRepository;
import dev.carlos.smartpantry.repository.IngredienteRepository;
import dev.carlos.smartpantry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
public class EstoqueService {
    @Autowired
    EstoqueRepository estoqueRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IngredienteRepository ingredienteRepository;
    @Autowired
    JavaMailSender mailSender;


    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void estoqueInsert(String email, EstoqueRegister estoqueRegister){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("USUÁRIO INCORRETO"));
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
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("USUÁRIO INCORRETO"));

        List<Ingrediente> ingredientes = converterIngredientes(listEstoqueRegister);
        if (ingredientes != null){
            for (int i = 0; i < ingredientes.size(); i++) {
                ItemEstoque estoque = new ItemEstoque();
                if (estoqueRepository.findByUser_idAndIngrediente_id(ingredientes.get(i)).isEmpty()) {
                    estoque.setUser_id(user);
                    estoque.setIngrediente_id(ingredientes.get(i));
                    estoque.setStatus(StatusIngrediente.valueOf(listEstoqueRegister.get(i).status()));
                    estoque.setQuantidade(listEstoqueRegister.get(i).quantidade());
                    estoque.setDataValidade(LocalDate.parse(listEstoqueRegister.get(i).dataValidade(), dateTimeFormatter));
                    estoqueRepository.save(estoque);
                }
                else {
                    estoque = estoqueRepository.findByUser_idAndIngrediente_id(ingredientes.get(i)).orElseThrow();
                    estoque.setQuantidade(estoque.getQuantidade().add(listEstoqueRegister.get(i).quantidade()));
                    estoque.setDataValidade(LocalDate.parse(listEstoqueRegister.get(i).dataValidade(), dateTimeFormatter));
                    estoqueRepository.save(estoque);
                }
            }
        }

    }


    public List<Ingrediente> converterIngredientes(List<EstoqueRegister> listEstoqueRegister){
        return listEstoqueRegister.stream().map(ingrediente -> ingredienteRepository.findByNome(ingrediente.nomeIngrediente()).orElseThrow(()-> new IllegalArgumentException("Ingrediente não cadastrado")))
                .toList();
    }


    @Scheduled(cron = "${api.cron.expression}")
    public List<ItemEstoque> vencimentoAutomatico(){
        List<ItemEstoque> lista = estoqueRepository.prazoVencimento(LocalDate.now());
        lista.stream()
                .forEach(itemEstoque -> itemEstoque.setStatus(StatusIngrediente.VENCIDO));
        estoqueRepository.saveAll(lista);
        return lista;

    }

    @Scheduled(cron = "${api.cron.expression}")
    public void proximoVencimento(){
        List<IngredienteVencimento> corpoEmail = estoqueRepository.findAllProximoVencimento(LocalDate.now(), LocalDate.now().plusDays(15));
        if (corpoEmail != null){
            var message = new SimpleMailMessage();
            message.setFrom("noreplay@email.com");
            message.setTo("teste@gmail.com");
            message.setSubject("Ingredientes próximos ao prazo de validade");
            message.setText("CUIDADO\n PRODUTOS PRÓXIMO DA DATA DE VALIDADE:\n"  + listaVencimento(corpoEmail));
            mailSender.send(message);
        }
    }

    public String listaVencimento(List<IngredienteVencimento> ingredienteVencimentos) {
        StringBuilder sb = new StringBuilder();

        for (IngredienteVencimento ingrediente : ingredienteVencimentos) {
            sb.append("- ")
                    .append(ingrediente.ingrediente())
                    .append(" (Vence em: ")
                    .append(ingrediente.dataValidade())
                    .append(")\n");
        }

        return sb.toString();
    }



}
