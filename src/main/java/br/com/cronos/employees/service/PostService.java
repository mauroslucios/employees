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

	public List<Post> buscarTodosPosts() {
		return postRepository.findAll();
	}

	public Post buscarUnicoPost(Long id) throws ObjectNotFoundException{
		Optional<Post> post = postRepository.findById(id);
		return post.orElseThrow(()-> new ObjectNotFoundException(null, "Post não encontrado!"));
	}

	public Post inserirPost(Post objPost) {
		return postRepository.save(objPost);
	}

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
