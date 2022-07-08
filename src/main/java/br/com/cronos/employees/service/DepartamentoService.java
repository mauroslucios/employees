package br.com.cronos.employees.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cronos.employees.entity.Departamento;
import br.com.cronos.employees.exceptions.EntityNotFoundException;
import br.com.cronos.employees.repository.DepartamentoRepository;

@Service
public class DepartamentoService {

	@Autowired
	private DepartamentoRepository departamentoRepository;

	/**
	 * 
	 * @return Faz uma busca por uma lista de departamentos no banco
	 */
	public List<Departamento> buscarTodosDepartamentos(){
		return departamentoRepository.findAll();
	}
	
	
	/**
	 * 
	 * @param id
	 * @return Faz uma busca por uma único departamento no banco
	 * @throws ObjectNotFoundException lança uma exceção se não encontrado
	 */
	public Departamento buscarUnicoDepartamento(Long id){
		Optional<Departamento> departamento = departamentoRepository.findById(id);
		return departamento.orElseThrow(()-> new EntityNotFoundException("Departamento não encontrado id = " + id));
	}
	
	
	/**
	 * 
	 * @param departamento
	 * @return faz a inserção de um departamento no banco
	 */
	public Departamento inserirDepartamento(Departamento departamento) {
		return departamentoRepository.save(departamento);
	}
	
	/**
	 * 
	 * @param id deleta um departamento do banco de dados
	 */
	public void deleteUmDepartamento(Long id) {
		departamentoRepository.deleteById(id);
	}
	
	/**
	 * 
	 * @param objDepartamento
	 * @return atualiza um único departamento no banco
	 */
	public Departamento atualizaUmDepartamento(Departamento objDepartamento) {
		Departamento departamento = buscarUnicoDepartamento(objDepartamento.getId());
		departamento.setNome(objDepartamento.getNome());
		return inserirDepartamento(departamento);
	}
	
	
	
}
