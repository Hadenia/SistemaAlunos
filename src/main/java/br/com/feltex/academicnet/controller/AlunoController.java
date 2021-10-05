package br.com.feltex.academicnet.controller;

import br.com.feltex.academicnet.model.Aluno;
import br.com.feltex.academicnet.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

  /*  @GetMapping
    public Aluno getInfor(){
       Aluno aluno = new Aluno(1L, "Hadenia Rodrigues", "3333-4444", "hadenia@feltex.com.br", Instant.now());
        return aluno;
    }
  */
    @GetMapping
    public List<Aluno> listar(){
        return alunoRepository.findAll();
    }

    @PostMapping
    public void incluir(@RequestBody Aluno aluno){
        alunoRepository.save(aluno);
    }

    @PutMapping
    public void alterar(@RequestBody Aluno aluno){
        alunoRepository.save(aluno);
    }

    @DeleteMapping("/{matricula}")
    public void deletar(@PathVariable Long matricula){
        alunoRepository.deleteById(matricula);
    }

    @GetMapping("/{matricula}")
    public Optional<Aluno> ler(@PathVariable Long matricula){
        return alunoRepository.findById(matricula);
    }

}
