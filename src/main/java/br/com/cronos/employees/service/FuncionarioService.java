package br.com.cronos.employees.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import br.com.cronos.employees.entity.Funcionario;
import br.com.cronos.employees.repository.FuncionarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FuncionarioService {

	private FuncionarioRepository funcionarioRepository;
	
	/**
	 * 
	 * @return retorna uma lista de funcionários
	 */
	public List<Funcionario> buscarTodosFuncionarios(){
		return funcionarioRepository.findAll();
	}
	
	public Funcionario buscarUnicoFuncionario(Long id) throws ObjectNotFoundException{
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		return funcionario.orElseThrow(()-> new ObjectNotFoundException(null, "Funcionário não encontrado!"));
	}
	
	public Funcionario inserirFuncionario(Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);
	}
	
	public void deletaUmFuncionario(Long id) {
		funcionarioRepository.deleteById(id);
	}
	
	public Funcionario atualizarUmFuncionario(Funcionario objFuncionario) {
		Funcionario funcionario = buscarUnicoFuncionario(objFuncionario.getId());
		funcionario.setNome(objFuncionario.getNome());
		funcionario.setEmail(objFuncionario.getEmail());
		funcionario.setSenha(objFuncionario.getSenha());
		funcionario.setTipo(objFuncionario.getTipo());
		return inserirFuncionario(funcionario);		
	}
}
