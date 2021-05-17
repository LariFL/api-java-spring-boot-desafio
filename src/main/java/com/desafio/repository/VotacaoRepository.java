package com.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafio.model.Votacao;

public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
	
	@Query("SELECT v FROM Votacao v WHERE v.id_sessao = ?1 AND v.id_associado = ?2 ")
	public List<Votacao> findBySessaoAssociado(Long id_sessao, Long id_associado);
	
	@Query("SELECT v FROM Votacao v WHERE v.id_sessao = ?1 ORDER BY v.id_associado ")
	public List<Votacao> findByVotacaoSessao(Long id_sessao);
}
