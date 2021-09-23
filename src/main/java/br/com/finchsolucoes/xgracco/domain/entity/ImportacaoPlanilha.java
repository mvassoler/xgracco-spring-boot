package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumImportacao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusImportacaoPlanilha;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumImportacaoConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumStatusImportacaoPlanilhaConverter;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Implementação da entidade Rotina.
 *
 * @author Alessandro Ramos, 2021
 */
@Entity
@Table(name = "IMPORTACAO_PLANILHA")
@SequenceGenerator(allocationSize = 1, name = "seqImportacaoPlanilha", 
        sequenceName = "SEQ_IMPORTACAO_PLANILHA")
@Data
@Builder
@AllArgsConstructor
public class ImportacaoPlanilha {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqImportacaoPlanilha")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USUARIO")
    private Usuario usuarioCadastro;    
    
    @Column(name = "DATA_ENCAMINHAMENTO")
    private LocalDateTime dataEncaminhamento;
    
    @Column(name = "MODELO")
    @Convert(converter = EnumImportacaoConverter.class)
    private EnumImportacao modelo;
    
    @Column(name = "ARQUIVO_IMPORTACAO")
    @Size(min=1, max=255)
    private String arquivoImportacao;

    @Column(name = "NOME_ORIGINAL")
    @Size(min=1, max=255)
    private String nomeOriginal;
    
    @Column(name = "STATUS")
    @Convert(converter = EnumStatusImportacaoPlanilhaConverter.class)
    private EnumStatusImportacaoPlanilha status;
    
    @Column(name = "DATA_INICIO")
    private LocalDateTime dataInicio;
    
    @Column(name = "DATA_FINALIZACAO")
    private LocalDateTime dataFinalizacao;
    
    @Column(name = "OBSERVACAO", length = 512)
    @Size(max=512)
    private String observacao;
    
    public ImportacaoPlanilha() {
    }

    @QueryProjection
    public ImportacaoPlanilha(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ImportacaoPlanilha rotina = (ImportacaoPlanilha) o;
        return Objects.equals(this.getId(), rotina.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
