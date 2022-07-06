package br.com.cronos.employees.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cronos.employees.entity.Post;
import br.com.cronos.employees.repository.PostRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

	@Autowired
	private PostRepository postRepository;

	/**
	 * 
	 * @return busca uma lista de posts no banco
	 */
	public List<Post> buscarTodosPosts() {
		return postRepository.findAll();
	}

	/**
	 * 
	 * @param id
	 * @return retorna um único post pelo id do banco
	 * @throws ObjectNotFoundException
	 */
	public Post buscarUnicoPost(Long id) throws ObjectNotFoundException{
		Optional<Post> post = postRepository.findById(id);
		return post.orElseThrow(()-> new ObjectNotFoundException(null, "Post não encontrado!"));
	}

	/**
	 * 
	 * @param objPost
	 * @return faz a inserção de um post no banco 
	 */
	public Post inserirPost(Post objPost) {
		return postRepository.save(objPost);
	}

	/**
	 * 
	 * @param id deleta um post pelo id do banco
	 */
	public void deletaUmPost(Long id) {
		postRepository.deleteById(id);
	}

	public Post atualizaUmPost(Post objPost) {
		Post post = buscarUnicoPost(objPost.getId());
		post.setDescricao(objPost.getDescricao());
		post.setTitulo(objPost.getTitulo());
		post.setFuncionario(objPost.getFuncionario());
		return inserirPost(post);
		
	}
	
	
}
