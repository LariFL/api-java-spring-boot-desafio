package com.desafio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Sessao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long id_pauta;
	
	@Column(nullable = true)
	private int tempoAbertura;
	
	@Column(nullable = true, length = 20)
	private String dataHoraFinal;
	
	@Column(nullable = true)
	private int votosSim;
	
	@Column(nullable = true)
	private int votosNao;
	
	public void somaVotosSim() {
		this.votosSim++;
	}
	
	public void somaVotosNao() {
		this.votosNao++;
	}
	
}
