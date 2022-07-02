package br.com.cronos.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cronos.employees.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
