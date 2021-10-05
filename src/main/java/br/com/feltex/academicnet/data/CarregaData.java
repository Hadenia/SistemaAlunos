package br.com.feltex.academicnet.data;

import br.com.feltex.academicnet.model.Aluno;
import br.com.feltex.academicnet.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;


//Carga de Dados

@Component
public class CarregaData {

    @Value("${habilitar.carregar.massa.dados}")
    private boolean podeCarregarDados;

    @Autowired
    private AlunoRepository alunoRepository;

    @PostConstruct
    public void loadData(){
//Carregar os registros
        if(podeCarregarDados){
            for (int x = 0; x < 25; x++){
                alunoRepository.save(new Aluno((long) x, "Jose da Silva" + x,"99662-554" + x, "jose@feltex.com.br", Instant.now() ));
            }
        }
    }

}
