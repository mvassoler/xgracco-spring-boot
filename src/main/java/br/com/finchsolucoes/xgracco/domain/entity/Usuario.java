package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumSistemaExternoUsuario;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumFuncaoConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumSistemaExternoUsuarioConverter;
import br.com.finchsolucoes.xgracco.core.validation.Unique;
import br.com.finchsolucoes.xgracco.core.validation.UsuarioBloqueio;
import br.com.finchsolucoes.xgracco.core.validation.UsuarioFuncao;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Usuário.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Entity
@Audited
@Unique({"login"})
@UsuarioFuncao
@UsuarioBloqueio
@Table(name = "USUARIO")
@Relation(collectionRelation = "usuarios")
@Data
@Builder
@AllArgsConstructor
public class Usuario implements EntidadeAuditada { //, UserDetails {

    //TODO - ACERTAR ESTA CLASSE

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Long id;

    @NotNull
    @MapsId
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuarioSistema")
    @RelatorioInterface(unwrapped = true)
    @JoinColumn(name = "ID", referencedColumnName = "ID")
    private Pessoa pessoa;

    @NotBlank
    @Size(min = 1, max = 60)
    @Column(name = "LOGIN")
    private String login;

    @Size(min = 1, max = 60)
    @Column(name = "SENHA", updatable = false)
    private String senha;

    @Email
    @Size(min = 1, max = 100)
    @Column(name = "EMAIL")
    private String email;

    @NotNull
    @Column(name = "BLOQUEADO")
    private Boolean bloqueado;

    @Min(1)
    @Max(10)
    @Column(name = "JORNADA_TRABALHO")
    private Integer jornadaTrabalho;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID")
    private Perfil perfil;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioEscritorio> escritorios;

    @NotEmpty
    @ElementCollection(targetClass = EnumFuncao.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "USUARIO_FUNCAO", joinColumns = @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID"))
    @Column(name = "ID_FUNCAO")
    @Convert(converter = EnumFuncaoConverter.class)
    private List<EnumFuncao> funcoes;

    @NotAudited
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<ProcessoUsuario> usuariosCompartilhados;

    @NotAudited
    @OneToMany(mappedBy = "usuarioCadastro", fetch = FetchType.LAZY)
    private List<ImportacaoPlanilha> planilhasImportadasPeloUsuario;

    @RelatorioInterface(titulo = "Sistema Externo")
    @Column(name = "ID_SISTEMA_EXTERNO")
    @Convert(converter = EnumSistemaExternoUsuarioConverter.class)
    private EnumSistemaExternoUsuario sistemaExternoUsuario;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private String validacaoEsteira;

    @Transient
    private String validacaoTarefa;

    @Transient
    private String validacaoAgenda;

    @Transient
    private String validacaoSolicitacao;

    @Transient
    private String validacaoCarteiraEvento;

    @Transient
    private String validacaoProcessosOperacional;

    @Transient
    private String validacaoModeloAgendamento;

    @Transient
    private String validacaoTarefaStatusFinalAgendamento;

    @Transient
    private String validacaoFluxoTrabalhoTarefa;

    public Usuario() {
    }

    @Transient
    public Boolean hasFuncao(EnumFuncao... funcao) {
        if (funcao == null) {
            return false;
        }

        return Arrays.asList(funcao)
                .stream()
                .map(Optional::ofNullable)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .anyMatch(f -> this.funcoes.contains(f));
    }


    @QueryProjection
    public Usuario(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Usuario(Long id, Pessoa pessoa) {
        this.id = id;
        this.pessoa = pessoa;
    }

    @QueryProjection
    public Usuario(Long id, String login, Pessoa pessoa) {
        this.id = id;
        this.login = login;
        this.pessoa = pessoa;
    }

    @QueryProjection
    public Usuario(Long id, String cpfCnpj, String nomeRazaoSocial, String apelidoFantasia, String rgInscricaoEstadual) {
        this.id = id;
        this.pessoa = new Pessoa();
        this.pessoa.setId(id);
        this.pessoa.setCpfCnpj(cpfCnpj);
        this.pessoa.setNomeRazaoSocial(nomeRazaoSocial);
        this.pessoa.setApelidoFantasia(apelidoFantasia);
        this.pessoa.setRgInscricaoEstadual(rgInscricaoEstadual);
    }

    @QueryProjection
    public Usuario(Long id, Pessoa pessoa, String login, String senha, Perfil perfil) {
        this.id = id;
        this.pessoa = pessoa;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    @QueryProjection
    public Usuario(Long id, Pessoa pessoa, String login, String senha, String email, Boolean bloqueado, Perfil perfil) {
        this.id = id;
        this.pessoa = pessoa;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.bloqueado = bloqueado;
        this.perfil = perfil;
    }

    @QueryProjection
    public Usuario(Long id, Pessoa pessoa, String login, String senha, String email, Boolean bloqueado, Perfil perfil,
                   EnumSistemaExternoUsuario sistemaExternoUsuario) {
        this.id = id;
        this.pessoa = pessoa;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.bloqueado = bloqueado;
        this.perfil = perfil;
        this.sistemaExternoUsuario = sistemaExternoUsuario;
    }

    @QueryProjection
    public Usuario(Long id, Pessoa pessoa, String login, String senha, String email, Boolean bloqueado, Perfil perfil,
                   List<EnumFuncao> funcoes) {
        this.id = id;
        this.pessoa = pessoa;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.bloqueado = bloqueado;
        this.perfil = perfil;
        this.funcoes = funcoes;
    }

    @QueryProjection
    public Usuario(Long id, String login, String senha, Boolean bloqueado) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.bloqueado = bloqueado;
    }

    public Usuario(Pessoa pessoa, String login, String senha, Integer jornadaTrabalho) {
        this.pessoa = pessoa;
        this.login = login;
        this.senha = senha;
        this.jornadaTrabalho = jornadaTrabalho;
    }

   /* @JsonIgnore
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(getPerfil()).map(Perfil::getPermissoes).orElse(new ArrayList<>());
    }

    @JsonIgnore
    @Transient
    @Override
    public String getPassword() {
        return getSenha();
    }

    @JsonIgnore
    @Transient
    @Override
    public String getUsername() {
        return getLogin();
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return !Optional.ofNullable(getBloqueado()).orElse(false);
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }*/

    @JsonIgnore
    @Transient
    public boolean hasPermissao(String permissao) {
        if (perfil == null || perfil.getPermissoes() == null) {
            return false;
        }

        return perfil.getPermissoes()
                .stream()
                .map(Permissao::getCodigo)
                .filter(permissao::equals)
                .findAny()
                .isPresent();
    }

    @Transient
    public String getNomeRazaoSocial() {
        return pessoa == null ? null : pessoa.getNomeRazaoSocial();
    }

    @JsonIgnore
    public String getSenha() {
        return senha;
    }

    @JsonProperty
    public void setSenha(String senha) {
        this.senha = senha;
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
        Usuario usuario = (Usuario) o;
        return Objects.equals(this.getId(), usuario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
