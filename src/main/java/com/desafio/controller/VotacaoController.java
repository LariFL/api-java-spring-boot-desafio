package com.desafio.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.model.Sessao;
import com.desafio.model.Votacao;
import com.desafio.repository.SessaoRepository;
import com.desafio.repository.VotacaoRepository;

@RestController
@RequestMapping("/votacoes")
public class VotacaoController {

	@Autowired
	private VotacaoRepository votacaoRepository;
	
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@GetMapping(path = "/listar")
	public List<Votacao> listar() {
		return votacaoRepository.findAll();
	}
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<Votacao> consultar(@PathVariable("id") Long id) {
        return votacaoRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }
	
	@GetMapping(path = "/sessao/{id_sessao}")
    public ResponseEntity<List<Votacao>> consultarPorSessao(@PathVariable("id_sessao") Long id_sessao) {
    	List<Votacao> votacoes = votacaoRepository.findByVotacaoSessao(id_sessao);
    	
    	if(votacoes.size() == 0)
    		return ResponseEntity.notFound().build();
    	
        return ResponseEntity.ok(votacoes);
    }
	
	@PostMapping(path = "/votar")
	@ResponseStatus(HttpStatus.OK)
	public Votacao votar(@RequestBody Votacao votacao) throws Exception {
		
		Sessao sessao;
		try {
			sessao = sessaoRepository.findById(votacao.getId_sessao()).get();
		} catch (Exception e) {
			throw new Exception("Sessao de votação não encontrada");
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
		LocalDateTime dataHoraFinal = LocalDateTime.parse(sessao.getDataHoraFinal(), formatter);
		LocalDateTime dataHoraAtual = LocalDateTime.now();
		 
		if(dataHoraAtual.isAfter(dataHoraFinal))
			throw new Exception("Sessao de votação já encerrada.");
		
		if(votacaoRepository.findBySessaoAssociado(votacao.getId_sessao(), votacao.getId_associado()).size() > 0)
			throw new Exception("Voto do associado já contabilizado.");
		
		votacao.setVoto(votacao.getVoto().toUpperCase().trim());
		if(!votacao.getVoto().equals("S") && !votacao.getVoto().equals("N"))
			throw new Exception("Voto inválido! Utilize 'S' para sim ou 'N' para não.");
		
		if(votacao.getVoto().equals("S"))
			sessao.somaVotosSim();
		else
			sessao.somaVotosNao();
		
		sessaoRepository.save(sessao);
		
		return votacaoRepository.save(votacao);
	}
	
}
