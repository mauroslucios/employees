package br.com.cronos.employees.service;

import org.springframework.stereotype.Service;

import br.com.cronos.employees.repository.PostRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

	private PostRepository postRepository;
	
	
}
