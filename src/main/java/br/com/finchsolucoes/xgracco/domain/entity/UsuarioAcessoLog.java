package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumAcessoLogAcaoLogoff;
import br.com.finchsolucoes.xgracco.domain.enums.EnumAcessoLogAcaoOrigem;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumAcessoLogAcaoLogoffConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumAcessoLogAcaoOrigemConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade responsável por mapear o login e o logout de usuários.
 *
 * @autor Eloi Correia
 * @since 5.46.0
 */
@Entity
@Table(name = "USUARIO_ACESSO_LOG")
@SequenceGenerator(allocationSize = 1, name = "seqUsuarioAcessoLog", sequenceName = "SEQ_USUARIO_ACESSO_LOG")
@Data
@Builder
@AllArgsConstructor
public class UsuarioAcessoLog extends Entidade implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqUsuarioAcessoLog")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USUARIO", referencedColumnName = "ID")
    private Usuario usuario;

    @NotNull
    @Column(name = "DATA_LOGIN")
    private LocalDateTime dataLogin;

    @Column(name = "DATA_LOGOFF")
    private LocalDateTime dataLogoff;

    @Column(name = "EXPIRACAO")
    private LocalDateTime expiracao;

    @Column(name = "SESSAO")
    private String sessao;

    @Column(name = "TOKENAUTORIZACAO")
    private String token;

    @Column(name = "IP")
    private String ip;

    @Column(name = "ESTACAO")
    private String estacao;

    @NotNull
    @Column(name = "FK_USUARIO_ACESSO_LOG_ORIGEM")
    @Convert(converter = EnumAcessoLogAcaoOrigemConverter.class)
    private EnumAcessoLogAcaoOrigem acessoLogAcaoOrigem;

    @Column(name = "FK_USUARIO_ACESSO_LOG_LOGOFF")
    @Convert(converter = EnumAcessoLogAcaoLogoffConverter.class)
    private EnumAcessoLogAcaoLogoff acessoLogAcaoLogoff;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USUARIO_DESCONECTOU", referencedColumnName = "ID")
    private Usuario desconectadoPorUsuario;

    @Column(name = "ULTIMA_COMUNICACAO")
    private LocalDateTime ultimaComunicacao;

    @Transient
    private LogAuditoria logAuditoria;

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

    public UsuarioAcessoLog() {
    }

    @QueryProjection
    public UsuarioAcessoLog(EnumAcessoLogAcaoOrigem acessoLogAcaoOrigem) {
        this.acessoLogAcaoOrigem = acessoLogAcaoOrigem;
    }

    @QueryProjection
    public UsuarioAcessoLog(String estacao) {
        this.estacao = estacao;
    }

    @QueryProjection
    public UsuarioAcessoLog(Usuario usuario) {
        this.usuario = usuario;
    }

    @QueryProjection
    public UsuarioAcessoLog(Long id, Usuario usuario, LocalDateTime dataLogin, LocalDateTime dataLogoff,
                            LocalDateTime expiracao, String sessao, String token, String ip, String estacao,
                            EnumAcessoLogAcaoOrigem acessoLogAcaoOrigem, EnumAcessoLogAcaoLogoff acessoLogAcaoLogoff,
                            Usuario desconectadoPorUsuario, LocalDateTime ultimaComunicacao) {
        this.id = id;
        this.usuario = usuario;
        this.dataLogin = dataLogin;
        this.dataLogoff = dataLogoff;
        this.expiracao = expiracao;
        this.sessao = sessao;
        this.token = token;
        this.ip = ip;
        this.estacao = estacao;
        this.acessoLogAcaoOrigem = acessoLogAcaoOrigem;
        this.acessoLogAcaoLogoff = acessoLogAcaoLogoff;
        this.desconectadoPorUsuario = desconectadoPorUsuario;
        this.ultimaComunicacao = ultimaComunicacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioAcessoLog that = (UsuarioAcessoLog) o;
        return Objects.equals(id, that.id) && Objects.equals(usuario, that.usuario) && Objects.equals(dataLogin, that.dataLogin) && Objects.equals(dataLogoff, that.dataLogoff) && Objects.equals(expiracao, that.expiracao) && Objects.equals(sessao, that.sessao) && Objects.equals(token, that.token) && Objects.equals(ip, that.ip) && Objects.equals(estacao, that.estacao) && acessoLogAcaoOrigem == that.acessoLogAcaoOrigem && acessoLogAcaoLogoff == that.acessoLogAcaoLogoff && Objects.equals(desconectadoPorUsuario, that.desconectadoPorUsuario) && Objects.equals(logAuditoria, that.logAuditoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, dataLogin, dataLogoff, expiracao, sessao, token, ip, estacao, acessoLogAcaoOrigem, acessoLogAcaoLogoff, desconectadoPorUsuario, logAuditoria);
    }

    @Override
    public String toString() {
        return "UsuarioAcessoLog{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", dataLogin=" + dataLogin +
                ", dataLogoff=" + dataLogoff +
                ", expiracao=" + expiracao +
                ", sessao='" + sessao + '\'' +
                ", token='" + token + '\'' +
                ", ip='" + ip + '\'' +
                ", estacao='" + estacao + '\'' +
                ", acessoLogAcaoOrigem=" + acessoLogAcaoOrigem +
                ", acessoLogAcaoLogoff=" + acessoLogAcaoLogoff +
                ", desconectadoPorUsuario=" + desconectadoPorUsuario +
                ", logAuditoria=" + logAuditoria +
                '}';
    }
}
