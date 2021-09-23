package br.com.finchsolucoes.xgracco.domain.entity;


import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Diretório de documento da pessoa
 *
 * @author guilhermecamargo
 */
@Entity
@Relation(collectionRelation = "pessoas-diretorios")
@Table(name = "PESSOA_DIRETORIO")
@Audited
@SequenceGenerator(allocationSize = 1, name = "seqPessoaDiretorio", sequenceName = "SEQ_PESSOA_DIRETORIO")
@Data
@Builder
@AllArgsConstructor
public class PessoaDiretorio extends Entidade implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPessoaDiretorio")
    @Column(name = "ID")
    private Long id;

    //@NotBlank
    @Size(max = 255)
    @Column(name = "DESCRICAO")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOADIRETORIO_PAI")
    private PessoaDiretorio diretorioPai;

    @Column(name = "DATA_CADASTRO")
    private LocalDateTime dataCadastro;

    @Transient
    private List<PessoaDiretorio> diretorios;

    @Transient
    private List<PessoaDocumento> documentos;

    @Transient
    private LogAuditoria logAuditoria;

    @PrePersist
    void prePersist(){
        this.dataCadastro = LocalDateTime.now();
    }

    public PessoaDiretorio() {
    }

    @QueryProjection
    public PessoaDiretorio(Long id) {
        this.id = id;
    }

    public PessoaDiretorio(String nome, Pessoa pessoa) {
        this.nome = nome;
        this.pessoa = pessoa;
    }

    @QueryProjection
    public PessoaDiretorio(Long id,  Pessoa pessoa) {
        this.id = id;
        this.pessoa = pessoa;
    }

    @QueryProjection
    public PessoaDiretorio(Long id, String nome, Pessoa pessoa){
        this.id = id;
        this.nome = nome;
        this.pessoa = pessoa;
    }


    @QueryProjection
    public PessoaDiretorio(Long id, String nome, Pessoa pessoa, PessoaDiretorio diretorioPai, LocalDateTime dataCadastro){
        this.id = id;
        this.nome = nome;
        this.pessoa = pessoa;
        this.diretorioPai = diretorioPai;
        this.dataCadastro = dataCadastro;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return this.logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PessoaDiretorio dir = (PessoaDiretorio) o;
        return Objects.equals(this.getId(), dir.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}