package br.com.cronos.employees.service;

import org.springframework.stereotype.Service;

import br.com.cronos.employees.repository.FuncionarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FuncionarioService {

	private FuncionarioRepository funcionarioRepository;
}
