package com.desafio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Votacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long id_sessao;
	
	@Column(nullable = false)
	private Long id_associado;
	
	@Column(nullable = false)
	private String voto; //-- (S)im ou (N)ão
	
}
