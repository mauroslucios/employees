package br.com.cronos.employees.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import br.com.cronos.employees.entity.Departamento;
import br.com.cronos.employees.service.DepartamentoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/api/v1")
@CrossOrigin(origins="*")
public class DepartamentoController {

	@Autowired
	private DepartamentoService departamentoService;
	/**
	 * 
	 * @return uma lista de departamentos
	 */
	@GetMapping(value="/departamentos")
	@ApiOperation(value = "Retorna uma lista de departamentos")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Departamentos encontrados com sucesso!"),
			@ApiResponse(code = 400, message = "Verifique sua requisição algo errado."),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
			@ApiResponse(code = 404, message = "Recurso não encontrado ou movido."),
			@ApiResponse(code = 500, message = "Aconteceu uma exceção")
	})
	public ResponseEntity<List<Departamento>> buscarTodosDepartamentos(){
		List<Departamento> listDepartamentos = departamentoService.buscarTodosDepartamentos();
		ArrayList<Departamento> departamentos = new ArrayList<Departamento>();
		for(Departamento departamento : listDepartamentos){
			Long id = departamento.getId();
			departamento.add(linkTo(methodOn(DepartamentoController.class).buscarUnicoDepartamento(id)).withRel("Listar departamento pelo id"));
			departamentos.add(departamento);
		}
		return ResponseEntity.ok().body(departamentos);
	}
	
	/**
	 * 
	 * @param id
	 * @return retorna um recurso pelo id
	 */
	@GetMapping(value="/departamentos/{id}")
	@ApiOperation(value = "Retorna um único departamento pelo id")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Departamento encontrado com sucesso!"),
			@ApiResponse(code = 400, message = "Verifique sua requisição algo errado."),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
			@ApiResponse(code = 404, message = "Recurso não encontrado ou movido."),
			@ApiResponse(code = 500, message = "Aconteceu uma exceção.")
	})
	public ResponseEntity<Departamento> buscarUnicoDepartamento(@PathVariable Long id){
		Departamento departamento = departamentoService.buscarUnicoDepartamento(id);
		departamento.add(linkTo(methodOn(DepartamentoController.class).buscarTodosDepartamentos()).withRel("Listar todos departamentos"));
		return ResponseEntity.ok().body(departamento);
	}
	
	/**
	 * 
	 * @param objDepartamento
	 * @return cadastra um departamento no banco
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/departamentos")
	@ApiResponses(value= {
			@ApiResponse(code = 201, message = "Departamento cadastrado com sucesso!"),
			@ApiResponse(code = 400, message = "Verifique sua requisição algo errado."),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
			@ApiResponse(code = 500, message = "Aconteceu uma exceção.")
	})
	public ResponseEntity<Void> inserirDepartamento(@RequestBody Departamento objDepartamento){
		Departamento departamento = departamentoService.inserirDepartamento(objDepartamento);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(departamento.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * 
	 * @param id
	 * @return deleta um departamento do banco pelo id
	 */
	@DeleteMapping(value="/departamentos/{id}")
	@ApiOperation(value="Deleta um departamento pelo id")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Departamento deletado com sucesso!"),
			@ApiResponse(code = 400, message = "Verifique sua requisição algo errado."),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
			@ApiResponse(code = 404, message = "Recurso não encontrado ou movido."),
			@ApiResponse(code = 500, message = "Aconteceu uma exceção.")
	})
	public ResponseEntity<Void> deleteUmDepartamento(@PathVariable Long id){
		departamentoService.deleteUmDepartamento(id);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * 
	 * @param objDepartamento
	 * @param id
	 * @return atualiza um departamento no banco pelo id
	 */
	@PutMapping(value="/departamentos/{id}")
	@ApiOperation(value="Atualiza um departamento pelo id")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Departamento atualizado com sucesso!"),
			@ApiResponse(code = 400, message = "Verifique sua requisição algo errado."),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
			@ApiResponse(code = 404, message = "Recurso não encontrado ou movido."),
			@ApiResponse(code = 500, message = "Aconteceu uma exceção.")
	})
	public ResponseEntity<Void> atualizaUmDepartamento(@RequestBody Departamento objDepartamento, @PathVariable Long id){
		objDepartamento.setId(id);
		departamentoService.atualizaUmDepartamento(objDepartamento);
		return ResponseEntity.noContent().build();
	}
	
	
	
}
