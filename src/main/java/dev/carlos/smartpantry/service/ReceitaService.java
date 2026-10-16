package dev.carlos.smartpantry.service;

import dev.carlos.smartpantry.entities.Receita;
import dev.carlos.smartpantry.entities.User;
import dev.carlos.smartpantry.entities.dto.ReceitaRegister;
import dev.carlos.smartpantry.repository.EstoqueRepository;
import dev.carlos.smartpantry.repository.IngredienteRepository;
import dev.carlos.smartpantry.repository.ReceitaRepository;
import dev.carlos.smartpantry.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class ReceitaService {
    @Autowired
    ReceitaRepository receitaRepository;
    @Autowired
    GoogleGenAiChatModel chatModel;
    @Autowired
    EstoqueRepository estoqueRepository;
    @Autowired
    IngredienteRepository ingredienteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EstoqueService estoqueService;

    private final ChatClient chatClient;

    public ReceitaService(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }

        public void registrarReceita(String email){
            Receita receita = new Receita(gerarReceita(email));
            receita.setUser_id(userRepository.findByEmail(email).orElseThrow());
            receitaRepository.save(receita);
        }


    public ReceitaRegister gerarReceita(String email){

        PromptTemplate promptTemplate = new PromptTemplate("""
            Pesquise receitas que possam ser feitas com {lista}.
            Lembre-se, você deve retornar um título para a receita, os ingredientes e o modo de preparo em até 1500 caracteres.
            Busque apenas uma receita.
    """);

        SystemMessage systemMessage = new SystemMessage("""
                Você não deve ultrapassar os 1500 caracteres para o modo de preparo.
                A descrição sobre o que é a receita é desnecessária dentro do modo de preparo.""");
        promptTemplate.add("lista", estoqueRepository.findAllIngridientes().stream().collect(Collectors.joining("\n")));
        return chatClient.prompt()
                .system(systemMessage.toString())
                .user(promptTemplate.render())
                .call()
                .entity(ReceitaRegister.class);
    }

}
