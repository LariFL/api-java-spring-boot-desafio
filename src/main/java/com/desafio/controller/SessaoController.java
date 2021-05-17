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
import com.desafio.repository.PautaRepository;
import com.desafio.repository.SessaoRepository;


@RestController
@RequestMapping("/sessoes")
public class SessaoController {
	
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;

	@GetMapping(path = "/listar")
	public List<Sessao> listar() {
		return sessaoRepository.findAll();
	}
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<Sessao> consultar(@PathVariable("id") Long id) {
        return sessaoRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }
	
    @GetMapping(path = "/pauta/{id_pauta}")
    public ResponseEntity<List<Sessao>> consultarPorPauta(@PathVariable("id_pauta") Long id_pauta) {
    	List<Sessao> sessoes = sessaoRepository.findBySessaoPauta(id_pauta);
    	
    	if(sessoes.size() == 0)
    		return ResponseEntity.notFound().build();
    	
        return ResponseEntity.ok(sessoes);
    }
	
	@PostMapping(path = "/criar")
	@ResponseStatus(HttpStatus.CREATED)
	public Sessao criar(@RequestBody Sessao sessao) throws Exception {
		
		if(!pautaRepository.findById(sessao.getId_pauta()).isPresent())
			throw new Exception("Pauta não encontrada");
		
		if(sessaoRepository.findBySessaoPautaAberta(sessao.getId_pauta()).size() > 0)
			throw new Exception("Já existe uma sessão em aberto para a pauta informada.");
		
		if(sessao.getTempoAbertura() <= 0)
			sessao.setTempoAbertura(1);
		
		LocalDateTime dataHoraFinal = LocalDateTime.now().plusMinutes(sessao.getTempoAbertura());
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formattedDate = dataHoraFinal.format(myFormatObj);	    
	    sessao.setDataHoraFinal(formattedDate);
		
		return sessaoRepository.save(sessao);
	}

}