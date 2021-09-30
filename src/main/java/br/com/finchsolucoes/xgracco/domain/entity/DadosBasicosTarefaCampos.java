package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DADOSBASICOSTAREFACAMPOS")
@SequenceGenerator(allocationSize = 1, name = "seqDadosBasicosTarefaCampos", sequenceName = "SEQ_DADOSBASICOSTAREFACAMPOS")
@Data
@Builder
@AllArgsConstructor
public class DadosBasicosTarefaCampos extends Entidade implements Identificavel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqDadosBasicosTarefaCampos")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DADOSBASICOSTAREFA")
    private DadosBasicosTarefa dadosBasicosTarefa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_CAMPO")
    private Campo campo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CAMPOLISTA")
    private CampoLista campoLista;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "TEXTO_PUBLICACAO")
    private byte[] textoPublicacao;

    public DadosBasicosTarefaCampos() {

    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DadosBasicosTarefaCampos that = (DadosBasicosTarefaCampos) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
