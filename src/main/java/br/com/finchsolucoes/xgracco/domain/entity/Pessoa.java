package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumEstadoCivil;
import br.com.finchsolucoes.xgracco.domain.enums.EnumSexo;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPessoa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTratamentoPessoa;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumEstadoCivilConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumSexoConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoPessoaConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTratamentoPessoaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author Marcelo Aguiar
 */
@Entity
@Table(name = "PESSOA")
@Relation(collectionRelation = "pessoas")
@SequenceGenerator(allocationSize = 1, name = "seqPessoa", sequenceName = "SEQ_PESSOA")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Pessoa extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPessoa")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "TIPO")
    @Convert(converter = EnumTipoPessoaConverter.class)
    private EnumTipoPessoa tipo;

    @Column(name = "ID_TRATAMENTO")
    @Convert(converter = EnumTratamentoPessoaConverter.class)
    private EnumTratamentoPessoa tratamento;

    @NotBlank
    @Size(max = 200)
    @RelatorioInterface(titulo = "Nome", padrao = true)
    @Column(name = "NOME_RAZAOSOCIAL")
    private String nomeRazaoSocial;

    @NotBlank
    @Size(max = 200)
    @RelatorioInterface(titulo = "Apelido")
    @Column(name = "APELIDO_FANTASIA")
    private String apelidoFantasia;

    @Size(max = 30)
    @Column(name = "RG_INSCRICAOESTADUAL")
    private String rgInscricaoEstadual;

    @Column(name = "ID_ESTADO_CIVIL")
    @Convert(converter = EnumEstadoCivilConverter.class)
    private EnumEstadoCivil estadoCivil;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "SEXO")
    @Convert(converter = EnumSexoConverter.class)
    private EnumSexo sexo;

    @Size(max = 15)
    @Column(name = "INSCRICAO_MUNICIPAL")
    private String inscricaoMunicipal;

    @Size(max = 30)
    @Column(name = "CPF_CNPJ")
    private String cpfCnpj;

    @Column(name = "MICROEMPRESA")
    private Boolean microEmpresa;

    @Column(name = "ANOTACAO")
    private String anotacao;

    @Column(name = "ATIVO")
    private boolean ativo;

    @Column(name = "IMAGEM")
    private String imagem;

    @Transient
    private LogAuditoria logAuditoria;

    @JsonIgnore
    @NotAudited
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PessoaEndereco> enderecos;

    @JsonIgnore
    @NotAudited
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PessoaTelefone> telefone;

    @JsonIgnore
    @NotAudited
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PessoaEnderecoEletronico> enderecosEletronicos;

    @NotEmpty
    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RELPESSOAPAPEL", joinColumns = @JoinColumn(name = "ID_PESSOA"), inverseJoinColumns = @JoinColumn(name = "ID_PAPEL"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Papel> papeis;

    @JsonIgnore
    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "responsavel")
    private List<Honorario> honorario;

    @JsonIgnore
    @NotAudited
    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PessoaEmpresaColigada> empresas;

    @JsonIgnore
    @NotAudited
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PessoaDadoBancario> dadosBancarios;

    @JsonIgnore
    @NotAudited
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PessoaEmpresaColaborador> pessoas;

    @JsonIgnore
    @NotAudited
    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PessoaEmpresaColaborador> empresaPessoa;

    @JsonIgnore
    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "pessoas")
    private List<Carteira> carteiras;

    @JsonIgnore
    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<ProcessoHistorico> historicoAlteracoes;

    @JsonIgnore
    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ESTEIRA_PESSOA", joinColumns = @JoinColumn(name = "ID_PESSOA"), inverseJoinColumns = @JoinColumn(name = "ID_ESTEIRA"))
    private List<Esteira> esteiras;

    @JsonIgnore
    @OneToMany(mappedBy = "solicitante")
    private List<SolicitacaoLog> solicitacaoLog;

    @JsonIgnore
    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa", cascade = CascadeType.REMOVE)
    private List<PessoaOab> oab;

    @JsonIgnore
    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "pessoas")
    private List<Comarca> comarcas;

    @JsonIgnore
    @NotAudited
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Usuario usuarioSistema;

    @JsonIgnore
    @NotAudited
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Escritorio escritorio;

    @JsonIgnore
    @NotAudited
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<InformacoesAdicionais> informacoesAdicionais;

    @JsonIgnore
    @NotAudited
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PessoaDivisao> pessoaDivisoes;

    public Pessoa() {
    }

    @QueryProjection
    public Pessoa(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Pessoa(Long id, String nomeRazaoSocial, String apelidoFantasia, String rgInscricaoEstadual, String cpfCnpj) {
        this.id = id;
        this.nomeRazaoSocial = nomeRazaoSocial;
        this.apelidoFantasia = apelidoFantasia;
        this.rgInscricaoEstadual = rgInscricaoEstadual;
        this.cpfCnpj = cpfCnpj;
    }

    @QueryProjection
    public Pessoa(Long id, String nomeRazaoSocial, String apelidoFantasia, String rgInscricaoEstadual, String cpfCnpj, EnumTipoPessoa tipo) {
        this.id = id;
        this.nomeRazaoSocial = nomeRazaoSocial;
        this.apelidoFantasia = apelidoFantasia;
        this.rgInscricaoEstadual = rgInscricaoEstadual;
        this.cpfCnpj = cpfCnpj;
        this.tipo = tipo;
    }

    @QueryProjection
    public Pessoa(Long id, String nomeRazaoSocial, String apelidoFantasia, String rgInscricaoEstadual, String cpfCnpj, EnumTipoPessoa tipo, boolean ativo) {
        this.id = id;
        this.nomeRazaoSocial = nomeRazaoSocial;
        this.apelidoFantasia = apelidoFantasia;
        this.rgInscricaoEstadual = rgInscricaoEstadual;
        this.cpfCnpj = cpfCnpj;
        this.tipo = tipo;
        this.ativo = ativo;
    }

    @QueryProjection
    public Pessoa(Long id, String nomeRazaoSocial, String apelidoFantasia, String rgInscricaoEstadual, String cpfCnpj, EnumTipoPessoa tipo, boolean ativo, Usuario usuarioSistema) {
        this.id = id;
        this.nomeRazaoSocial = nomeRazaoSocial;
        this.apelidoFantasia = apelidoFantasia;
        this.rgInscricaoEstadual = rgInscricaoEstadual;
        this.cpfCnpj = cpfCnpj;
        this.tipo = tipo;
        this.ativo = ativo;
        this.usuarioSistema = usuarioSistema;
    }

    @QueryProjection
    public Pessoa(Long id, String nomeRazaoSocial) {
        this.id = id;
        this.nomeRazaoSocial = nomeRazaoSocial;
    }

    @QueryProjection
    public Pessoa(Long id, String nomeRazaoSocial, String apelidoFantasia, String cpfCnpj) {
        this.id = id;
        this.nomeRazaoSocial = nomeRazaoSocial;
        this.apelidoFantasia = apelidoFantasia;
        this.cpfCnpj = cpfCnpj;
    }

    @QueryProjection
    public Pessoa(Long id, EnumTipoPessoa tipo, EnumTratamentoPessoa tratamento, String nomeRazaoSocial, String apelidoFantasia,
                  String rgInscricaoEstadual, EnumEstadoCivil estadoCivil, LocalDate dataNascimento, EnumSexo sexo, String inscricaoMunicipal,
                  String cpfCnpj, Boolean microEmpresa, String anotacao, Boolean ativo, String imagem, Usuario usuarioSistema, List<Papel> papeis) {

        this.id = id;
        this.tipo = tipo;
        this.tratamento = tratamento;
        this.nomeRazaoSocial = nomeRazaoSocial;
        this.apelidoFantasia = apelidoFantasia;
        this.rgInscricaoEstadual = rgInscricaoEstadual;
        this.estadoCivil = estadoCivil;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.inscricaoMunicipal = inscricaoMunicipal;
        this.cpfCnpj = cpfCnpj;
        this.microEmpresa = microEmpresa;
        this.anotacao = anotacao;
        this.ativo = ativo;
        this.imagem = imagem;
        this.usuarioSistema = usuarioSistema;
        this.papeis = papeis;
    }

    public Pessoa(EnumTipoPessoa tipo) {
        this.tipo = tipo;
    }

    @JsonIgnore
    public String getNomeUsuario() {
        // CONSIDERA SOMENTE O PRIMEIRO E O ULTIMO NOME
        if (nomeRazaoSocial == null) {
            return "";
        }
        String[] array = nomeRazaoSocial.split(" ");
        if (array.length == 1) {
            return array[0];
        }
        return array[0] + " " + array[array.length - 1];
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Pessoa that = (Pessoa) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
