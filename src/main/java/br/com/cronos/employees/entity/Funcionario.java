package br.com.cronos.employees.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.cronos.employees.enums.TipoFuncionario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tb_funcionario")
@Getter
@Setter
@AllArgsConstructor
public class Funcionario {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 3, message = "Nome deve conter no mínimo 3 caracteres")
	@Size(max = 50, message = "Nome deve conter n máximo 50 caracteres")
	private String nome;
	
	@Column(unique = true)
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Column(unique=true)
	private String senha;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	TipoFuncionario tipo;
	
	
	public Funcionario() {
		
	}

	
}
