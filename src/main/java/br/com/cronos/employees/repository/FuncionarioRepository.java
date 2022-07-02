package br.com.cronos.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cronos.employees.entity.Funcionario;
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}
