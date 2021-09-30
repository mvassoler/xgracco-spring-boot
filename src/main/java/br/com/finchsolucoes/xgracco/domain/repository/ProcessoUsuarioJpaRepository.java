package br.com.finchsolucoes.xgracco.domain.repository;

public interface ProcessoUsuarioJpaRepository {

    void removeByIdProcessoAndIdUsuario(Long idProcesso, Long idUsuario);
}
