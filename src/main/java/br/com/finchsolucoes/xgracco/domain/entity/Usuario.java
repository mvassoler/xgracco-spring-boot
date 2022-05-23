package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumSistemaExternoUsuario;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPermissao;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumFuncaoConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumSistemaExternoUsuarioConverter;
import br.com.finchsolucoes.xgracco.core.validation.Unique;
import br.com.finchsolucoes.xgracco.core.validation.UsuarioBloqueio;
import br.com.finchsolucoes.xgracco.core.validation.UsuarioFuncao;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.querydsl.core.annotations.QueryProjection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class Usuario implements EntidadeAuditada, UserDetails {

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

    @NotAudited
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USUARIO_PERFIL", joinColumns = @JoinColumn(name = "FK_USUARIO"), inverseJoinColumns = @JoinColumn(name = "FK_PERFIL"))
    @JsonManagedReference
    private List<UsuarioPerfil> perfis;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.DETACH)
    @JsonManagedReference
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

    @Column(name = "ABAS")
    @NotAudited
    private String views;


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

    @Transient
    private List<String> perfisList;

    public Usuario() {}

    public List<String> getPerfisList() {
        return perfisList;
    }

    public void setPerfisList(List<String> perfisList) {
        this.perfisList = perfisList;
    }

    @Transient
    public Boolean hasFuncao(EnumFuncao... funcao) {
        if (funcao == null) {
            return false;
        }

        return Arrays.stream(funcao)
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
    public Usuario(Long id, Pessoa pessoa, String login, String senha) {
        this.id = id;
        this.pessoa = pessoa;
        this.login = login;
        this.senha = senha;
    }

    @QueryProjection
    public Usuario(Long id, Pessoa pessoa, String login, String senha, String email, Boolean bloqueado) {
        this.id = id;
        this.pessoa = pessoa;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.bloqueado = bloqueado;
    }

    @QueryProjection
    public Usuario(Pessoa pessoa, Long id, String login, String senha, String email, Boolean bloqueado, EnumSistemaExternoUsuario sistemaExternoUsuario) {
        this.id = id;
        this.pessoa = pessoa;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.bloqueado = bloqueado;
        this.sistemaExternoUsuario = sistemaExternoUsuario;
    }

    @QueryProjection
    public Usuario(Long id, Pessoa pessoa, String login, String senha, String email, Boolean bloqueado,
                   List<EnumFuncao> funcoes) {
        this.id = id;
        this.pessoa = pessoa;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.bloqueado = bloqueado;
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

    @JsonIgnore
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (getPerfis() == null) {
            return new ArrayList<>();
        }

        final List<Permissao> permissaoCodigos = getPerfis()
                .stream()
                .flatMap(up -> up.getPerfil().getPermissoes().stream())
                .collect(Collectors.toList());

        final List<Permissao> permissoesUrl = new ArrayList<>();

        for (final Permissao permissao : permissaoCodigos) {

            if (StringUtils.isBlank(permissao.getUrl())) {
                continue;
            }

            if (permissao.getTipo().equals(EnumTipoPermissao.MENU) || permissao.getTipo().equals(EnumTipoPermissao.ABA)) {
                final String codigo = permissao.getCodigo();
                final String url = "/".concat(codigo.replace(":", "/"));
                permissoesUrl.add(new Permissao(permissao.getId(), url));
            }

            permissoesUrl.add(new Permissao(permissao.getId(), permissao.getUrl()));
        }

        permissaoCodigos.addAll(permissoesUrl);

        return permissaoCodigos;
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
    public List<Long> getEscritorioIds() {
        return this.getEscritorios().stream().map(usuarioEscritorio -> usuarioEscritorio.getEscritorio().getId()).collect(Collectors.toList());
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
    }

    @JsonIgnore
    @Transient
    public boolean hasPermissao(String permissao) {
        if (perfis.size() == 0) {
            return false;
        }

        return perfis.stream().anyMatch(p -> p.getPerfil().getPermissoes().stream().map(Permissao::getCodigo).anyMatch(permissao::equals));

    }

    @JsonIgnore
    public List<UsuarioPerfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<UsuarioPerfil> perfis) {
        this.perfis = perfis;
    }

    @Transient
    public String getNomeRazaoSocial() {
        return pessoa == null ? null : pessoa.getNomeRazaoSocial();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @JsonIgnore
    public String getSenha() {
        return senha;
    }

    @JsonProperty
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Integer getJornadaTrabalho() {
        return jornadaTrabalho;
    }

    public void setJornadaTrabalho(Integer jornadaTrabalho) {
        this.jornadaTrabalho = jornadaTrabalho;
    }

    public List<UsuarioEscritorio> getEscritorios() {
        return escritorios;
    }

    public void setEscritorios(List<UsuarioEscritorio> escritorios) {
        this.escritorios = escritorios;
    }

    public List<EnumFuncao> getFuncoes() {
        return funcoes;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
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