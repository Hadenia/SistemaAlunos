package br.com.feltex.academicnet.beans;

import br.com.feltex.academicnet.model.Aluno;
import br.com.feltex.academicnet.repository.AlunoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@Named(value = "alunoMB")
@ViewScoped
public class AlunoMB {

    @Getter
    @Setter
    private List<Aluno> alunos = new ArrayList<>();

    @Getter
    @Setter
    private Aluno aluno;

    @Autowired
    private AlunoRepository alunoRepository;

    @PostConstruct
    public List<Aluno> listarTodos() {
        alunos = alunoRepository.findAll();
        return alunos;
    }

 /**   @DeleteMapping()
    public void deletar(Aluno aluno){
        alunoRepository.delete(aluno);
    }

    //Tentativa fazer o Put
    @PutMapping
    public void alterar(@RequestBody Aluno aluno){
        alunoRepository.save(aluno);
    }
  **/

    public Integer getTamanhoDaLista() {
        return alunos.size();
    }

    public void setTamanhoDaLista(Integer size) {
        // Método criado para ser utilizado pelo primefaces
    }
}
