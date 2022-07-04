package br.com.cronos.employees.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tb_post")
@Getter
@Setter
@AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(unique=true)
	private String descricao;
	
	@NotBlank
	@Column(unique=true)
	private String titulo;
	
	@ManyToOne
	@JoinColumn(name="funcionario_id")
	private Funcionario funcionario;
	
	public Post() {
		
	}
	
}
