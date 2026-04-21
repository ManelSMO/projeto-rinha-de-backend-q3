package Service;

import Controller.Request.PessoaRequest;
import Controller.Response.PessoaResponse;
import Entity.Pessoa;
import Repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    //Metodos auxiliares para validacao de create pessoa
    //metodo de converter a data de nascimento de String para LocalDate, deixar no formato valido e com as informacoes validas
    private LocalDate converterNascimento(String nascimento) {
        try{
            return LocalDate.parse(nascimento);
        }catch(DateTimeParseException e){
            throw new RuntimeException("Data de nascimento invalida");
        }
    }

    //Metodo auxiliar que converte a Stack para lista de String
    private String converterStackString(List<String> stack){
        if(stack == null || stack.isEmpty()){
            return null;
        }
        return String.join(";", stack);
    }

    //Metodo converter lista de string para stack
    private List<String> converterStringStack(String stack){
        if(stack == null || stack.isBlank()){
            return null;
        }
        return Arrays.asList(stack.split(";"));
    }

    //Metodo create pessoa com as validacoes dos metodos auxiliares
    public PessoaResponse create(PessoaRequest pessoaRequest) {
        //Utilizo o metodo criado na repository para validar se o apelido existe
        if(pessoaRepository.existsByApelido(pessoaRequest.apelido())){
            throw new RuntimeException("Apelido ja existe");
        }

        //Metodos auxiliares para converter os dados para salvar no banco
        LocalDate nascimentoConverter = converterNascimento(pessoaRequest.nascimento());
        String stackConverter = converterStackString(pessoaRequest.stack());

        //Salvo as informacoes convertidas
        Pessoa pessoa = new Pessoa();
        pessoa.setApelido(pessoaRequest.apelido());
        pessoa.setNome(pessoaRequest.nome());
        pessoa.setNascimento(nascimentoConverter);
        pessoa.setStack(stackConverter);
        Pessoa pessoaSave = pessoaRepository.save(pessoa);

        //Retorno a response para o usuario
        return new PessoaResponse(
                pessoaSave.getId(),
                pessoaSave.getApelido(),
                pessoaSave.getNome(),
                pessoaSave.getNascimento(),
                converterStringStack(pessoaSave.getStack())
        );

    }

    //Buscar por ID a pessoa cadastrada, e validar se existe, retorna a Response
    public PessoaResponse findById(UUID id){
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Pessoa nao encontrada"));

        return new PessoaResponse(
                pessoa.getId(),
                pessoa.getApelido(),
                pessoa.getNome(),
                pessoa.getNascimento(),
                converterStringStack(pessoa.getStack())
        );
    }

}
