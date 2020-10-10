package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.repository.AlunoRepository;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Aluno;
@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class AlunoController {
	
	@Autowired
	private AlunoRepository alunoRep;
	//Incluir
	@PostMapping("alunos")
	public Aluno incluir(@Validated @RequestBody Aluno aluno) {
		
		return this.alunoRep.save(aluno);
		
	}
	//Alterar
	@PutMapping("alunos/{id}")
	public ResponseEntity<Aluno> alterar(@PathVariable(value = "id") Long alunoId,
			@Validated @RequestBody Aluno alunoParametro) throws ResourceNotFoundException {
		Aluno aluno = alunoRep.findById(alunoId).orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado pelo id :: " + alunoId));
		
		aluno.setNome(alunoParametro.getNome());
		aluno.setEndereco(alunoParametro.getEndereco());
		
		return ResponseEntity.ok(this.alunoRep.save(aluno));
		
	}
	//Excluir
	@DeleteMapping("alunos/{id}")
	public Map<String, Boolean> excluir(@PathVariable(value = "id") Long alunoId) throws ResourceNotFoundException {
		Aluno aluno = alunoRep.findById(alunoId).orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado pelo id :: " + alunoId));
		
		
		this.alunoRep.deleteById(alunoId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Excluido", Boolean.TRUE);
		
		return response;
	}
	//Consultar
	@GetMapping("alunos/{id}")
	public ResponseEntity<Aluno> consultar(@PathVariable(value = "id") Long alunoId) throws ResourceNotFoundException {
		Aluno aluno = alunoRep.findById(alunoId).orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado pelo id :: " + alunoId));
		return ResponseEntity.ok().body(aluno);
	}
	//Listar
	@GetMapping("alunos")
	public List<Aluno> listar() {
		return this.alunoRep.findAll();
	}

}
