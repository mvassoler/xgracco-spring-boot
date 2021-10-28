package br.com.finchsolucoes.xgracco.resource;

import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.resource.openapi.PraticaResourceOpenApi;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/praticas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PraticaResource implements PraticaResourceOpenApi {

    //TODO - ACERTAR ESTA CLASSE - IMPLEMENTADA SOMENTE PARA TESTE DE HATEOAS DA ACAO

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<Pratica>> findById(@PathVariable final Long id) {
        ResponseDTO<Pratica> pratica = new ResponseDTO<>();
        return ResponseEntity.ok().body(pratica);
    }
}
