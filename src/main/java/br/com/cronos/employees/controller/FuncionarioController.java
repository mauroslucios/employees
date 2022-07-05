package br.com.cronos.employees.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.cronos.employees.entity.Funcionario;
import br.com.cronos.employees.service.FuncionarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value="/api/v1")
@CrossOrigin(origins="*")
@AllArgsConstructor
public class FuncionarioController {

	private FuncionarioService funcionarioService;
	private final PasswordEncoder encoder;
	
	/**
	 * 
	 * @return retorna uma lista de funcionários
	 */
	@GetMapping(value="/funcionarios", produces="application/json")
	@ApiOperation(value="Retorna uma lista de funcionários")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Funcionários encontrados com sucesso!"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
			@ApiResponse(code = 500, message = "Aconteceu uma exceção.")
	})
	public ResponseEntity<List<Funcionario>> buscarTodosFuncionarios(){
		List<Funcionario> listFuncionarios = funcionarioService.buscarTodosFuncionarios();
		ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
		for(Funcionario funcionario : listFuncionarios) {
			Long id = funcionario.getId();
			funcionario.add(linkTo(methodOn(FuncionarioController.class).buscarUnicoFuncionario(id)).withRel("Listar funcionário pelo id"));
			funcionarios.add(funcionario);
		}
		return ResponseEntity.ok().body(funcionarios);
	}
	
	/**
	 * 
	 * @param id
	 * @return retorna um único funcionário
	 */
	@GetMapping(value="/{id}", produces="application/json")
	@ApiOperation(value="Busca um único funcionário")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Funcionário encontrado com sucesso!"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
			@ApiResponse(code = 500, message = "Aconteceu uma exceção.")
	})
	public ResponseEntity<Funcionario> buscarUnicoFuncionario(@PathVariable Long id){
		Funcionario funcionario = funcionarioService.buscarUnicoFuncionario(id);
		funcionario.add(linkTo(methodOn(FuncionarioController.class).buscarTodosFuncionarios()).withRel("Listar todos funcionários"));
		return ResponseEntity.ok().body(funcionario);
	}
	
	/**
	 * 
	 * @param objFuncionario
	 * @return faz a inserção de um funcionário no banco
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value="Cadastra um funcionário no banco")
	@PostMapping(value="/funcionarios", produces="application/json")
	@ApiResponses(value= {
			@ApiResponse(code = 201, message = "Funcionario cadastrado com sucesso!"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Aconteceu gerada uma exceção")
	})
	public ResponseEntity<Void> inserirFuncionario(@RequestBody Funcionario objFuncionario){
		objFuncionario.setSenha(encoder.encode(objFuncionario.getSenha()));
		Funcionario funcionario = funcionarioService.inserirFuncionario(objFuncionario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(funcionario.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * 
	 * @param id
	 * @return deleta um funcionário do banco
	 */
	@DeleteMapping(value="/funcionario/{id}", produces="application/json")
	@ApiOperation(value="Deleta um funcionário do banco")
	@ApiResponses(value= {
			@ApiResponse(code = 204, message = "Funcionário deletado com sucesso!"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
			@ApiResponse(code = 500, message = "Aconteceu uma excessão.")
	})
	public ResponseEntity<Void> deletaUmFuncionario(@PathVariable Long id){
		funcionarioService.deletaUmFuncionario(id);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * 
	 * @param objFuncionario
	 * @param id
	 * @return atualiza um funcionário no banco
	 */
	@ApiOperation(value = "Atualizar as informações de um funcionário")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Informações do funcionário alteradas com sucesso"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Ocorreu uma exceção")
	})
	@PutMapping(value = "/funcionarios/{id}", produces = "application/json")
	public ResponseEntity<Void> atualizarUmFuncionario(@RequestBody Funcionario objFuncionario, @PathVariable Long id){
		objFuncionario.setId(id);
		funcionarioService.atualizarUmFuncionario(objFuncionario);
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
}
