## Especificações do Projeto

* API com Spring Boot (Obs.: Utilizado o spring starter Lombok, então necessário a instalação do mesmo na IDE)
* Java 8
* Banco MySQL (username=root | password=123456)

## Rotas

//GET /pautas/listar

//GET /pautas/{id}

//POST /pautas/criar
{
    "nome": "Pauta inicial"
}

//GET /sessoes/listar

//GET /sessoes/{id}

//GET /sessoes/pauta/{id_pauta}

//POST /sessoes/criar
{
    "id_pauta": 1
}
ou
{
    "id_pauta": 1,
    "tempoAbertura": 10
}

//GET /votacoes/listar

//GET /votacoes/{id}

//GET /votacoes/sessao/{id_sessao}

//POST /votacoes/votar
{
    "id_sessao": 1,
    "id_associado": 1,
    "voto": "S"
}
ou
{
    "id_sessao": 1,
    "id_associado": 1,
    "voto": "N"
}