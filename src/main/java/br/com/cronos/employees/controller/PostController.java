package br.com.cronos.employees.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

import br.com.cronos.employees.entity.Post;
import br.com.cronos.employees.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/api/v1")
@CrossOrigin(origins="*")
public class PostController {

	@Autowired
	private PostService postService;
	
	/**
	 * 
	 * @return retorna uma lista de posts
	 */
	@GetMapping(value="/posts", produces="application/json")
	@ApiOperation(value = "Retorna uma lista de posts")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Posts encontrados com sucesso!"),
			@ApiResponse(code = 400, message = "Verifique sua requisição algo deu errado."),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
			@ApiResponse(code = 404, message = "Recurso não encontrado ou movido."),
			@ApiResponse(code = 500, message = "Aconteceu uma exceção.")
	})
	public ResponseEntity<List<Post>> buscarTodosPosts(){
		List<Post> listPosts = postService.buscarTodosPosts();
		ArrayList<Post> posts = new ArrayList<Post>();
		for(Post post: listPosts){
			Long id = post.getId();
			post.add(linkTo(methodOn(PostController.class).buscarUnicoPost(id)).withRel("Listar post pelo id"));
			posts.add(post);
		}
		return ResponseEntity.ok().body(posts);
	}
	
	/**
	 * 
	 * @param id
	 * @return retorna um único post pelo id
	 */
	@GetMapping(value="/posts/{id}",produces="application/json")
	@ApiOperation(value = "Retorna um único post pelo id")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Post encontrado com sucesso!"),
		@ApiResponse(code = 400, message = "Verifique sua requisição algo deu errado."),
		@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
		@ApiResponse(code = 404, message = "Recurso não encontrado ou movido."),
		@ApiResponse(code = 500, message = "Aconteceu uma excessão.")
	})
	public ResponseEntity<Post> buscarUnicoPost(@PathVariable(value="id") Long id){
		Post post = postService.buscarUnicoPost(id);
		post.add(linkTo(methodOn(PostController.class).buscarTodosPosts()).withRel("Listar todos os posts"));
		return ResponseEntity.ok().body(post);
	}
	
	/**
	 * 
	 * @param objPost
	 * @return faz a inserção de um post no banco
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/posts",produces="application/json")
	@ApiResponses(value= {
		@ApiResponse(code = 201, message = "Post cadstrado com sucesso!"),
		@ApiResponse(code = 400, message = "Verifique sua requisição algo deu errado."),
		@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
		@ApiResponse(code = 500, message = "Aconteceu uma exceção.")
	})
	public ResponseEntity<Void> inserirPost(@RequestBody Post objPost){
		Post post = postService.inserirPost(objPost);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	
	/**
	 * 
	 * @param id
	 * @return deleta um post do banco pelo id
	 */
	@DeleteMapping(value="/posts/{id}",produces="application/json")
	@ApiOperation(value="Deleta um post do banco pelo id")
	@ApiResponses(value= {
			@ApiResponse(code = 204, message = "Post deletado com sucesso!"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
			@ApiResponse(code = 500, message = "Aocnteceu uma exceção.")
	})
	public ResponseEntity<Void> deletaUmPost(@PathVariable Long id){
		postService.deletaUmPost(id);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * 
	 * @param objPost
	 * @param id
	 * @return atualiza um post no banco pelo id
	 */
	@PutMapping(value="/posts/{id}", produces="application/json")
	@ApiOperation(value="Atualiza um post pelo id")
	@ApiResponses(value= {
			@ApiResponse(code = 204, message = "Post alterado com sucesso!"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Aconteceu gerada uma exceção")
	})
	public ResponseEntity<Void> atualizaUmPost(@RequestBody Post objPost, @PathVariable Long id){
		objPost.setId(id);
		postService.atualizaUmPost(objPost);
		return ResponseEntity.noContent().build();
	}
	

}
