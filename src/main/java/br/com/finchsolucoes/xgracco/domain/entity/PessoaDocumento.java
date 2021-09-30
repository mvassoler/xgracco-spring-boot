package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Documento da pessoa
 *
 * @author guilhermecamargo
 */
@Entity
@Relation(collectionRelation = "pessoas-documentos")
@Table(name = "PESSOA_DOCUMENTO")
@SequenceGenerator(allocationSize = 1, name = "seqPessoaDocumento", sequenceName = "SEQ_PESSOA_DOCUMENTO")
@Data
@Builder
@AllArgsConstructor
public class PessoaDocumento extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPessoaDocumento")
    @Column(name = "ID")
    private Long id;

    //@NotBlank
    @Size(max = 255)
    @Column(name = "NOME_ARQUIVO")
    private String nomeArquivo;

    //@NotBlank
    @Column(name = "CAMINHO_DOCUMENTO")
    private String caminhoDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TIPO_DOCUMENTO")
    private TipoDocumento tipoDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOADIRETORIO")
    private PessoaDiretorio diretorio;

    @Column(name = "DATA_CADASTRO")
    private LocalDateTime dataCadastro;

    @Transient
    private LogAuditoria logAuditoria;

    @PrePersist
    void prePersist(){
        this.dataCadastro = LocalDateTime.now();
    }

    public PessoaDocumento() {
    }

    @QueryProjection
    public PessoaDocumento(Long id){
        this.id = id;
    }

    @QueryProjection
    public PessoaDocumento(Long id, String nomeArquivo, String caminhoDocumento, TipoDocumento tipoDocumento, PessoaDiretorio diretorio, LocalDateTime dataCadastro) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.caminhoDocumento = caminhoDocumento;
        this.tipoDocumento = tipoDocumento;
        this.diretorio = diretorio;
        this.dataCadastro = dataCadastro;
    }

    public PessoaDocumento(String nomeArquivo, TipoDocumento tipoDocumento, String caminhoDocumento, PessoaDiretorio diretorio) {
        this.nomeArquivo = nomeArquivo;
        this.tipoDocumento = tipoDocumento;
        this.caminhoDocumento = caminhoDocumento;
        this.diretorio = diretorio;
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
        PessoaDocumento doc = (PessoaDocumento) o;
        return Objects.equals(this.getId(), doc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }


}