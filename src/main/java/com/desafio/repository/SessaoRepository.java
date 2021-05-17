package com.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafio.model.Sessao;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

	@Query("SELECT s FROM Sessao s WHERE s.id_pauta = ?1 ORDER BY s.dataHoraFinal DESC ")
	public List<Sessao> findBySessaoPauta(Long id_pauta);
	
	@Query("SELECT s FROM Sessao s WHERE s.id_pauta = ?1 AND NOW() <= s.dataHoraFinal ")
	public List<Sessao> findBySessaoPautaAberta(Long id_pauta);
	
}
