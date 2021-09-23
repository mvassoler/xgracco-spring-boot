package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumPublicacaoNaoColadaAcao;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade utilizada para salvar as ações feitas na entidade PublicacaoNaoColada
 * pela equipe de BPO para futuro faturamento.
 * @author guilhermecamargo
 */
@Entity
@Table(name = "PUBLICACAO_NAO_COLADA_ACAO")
@Relation(collectionRelation = "publicacoes-nao-coladas-acoes")
@SequenceGenerator(allocationSize = 1, name = "seqPubNaoColadaAcao", sequenceName = "SEQ_PUBNCOLADA_ACAO")
@Data
@Builder
@AllArgsConstructor
public class PublicacaoNaoColadaAcao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPubNaoColadaAcao")
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PUBLICACAO_NAO_COLADA")
    private PublicacaoNaoColada publicacaoNaoColada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @Column(name = "DATA_ACAO")
    private LocalDateTime dataAcao;

    @Column(name = "ACAO")
    private EnumPublicacaoNaoColadaAcao acao;

    public PublicacaoNaoColadaAcao(){

    }

    @QueryProjection
    public PublicacaoNaoColadaAcao(Long id){
        this.id = id;
    }

    @QueryProjection
    public PublicacaoNaoColadaAcao(Long id,Pessoa pessoa, Processo processo, LocalDateTime dataAcao, EnumPublicacaoNaoColadaAcao acao, PublicacaoNaoColada publicacaoNaoColada ) {
        this.id = id;
        this.pessoa = pessoa;
        this.processo = processo;
        this.dataAcao = dataAcao;
        this.acao = acao;
        this.publicacaoNaoColada = publicacaoNaoColada;
    }

    @PrePersist
    void prePersist(){
        this.dataAcao = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate(){
        this.dataAcao = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicacaoNaoColadaAcao that = (PublicacaoNaoColadaAcao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}