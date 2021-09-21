package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;

/**
 * @author rianmachado
 */

@Data
@Entity
@Relation(collectionRelation = "praticas")
@Table(name = "PRATICA", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao", "FK_AREA"}, name = "grupoPratica")})
@SequenceGenerator(allocationSize = 1, name = "seqPratica", sequenceName = "SEQ_PRATICA")
@RelatorioInterface(titulo = "Práticas Jurídicas")
@Audited
public class Pratica implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPratica")
    @Column(name = "ID")
    private Long id;


    @Transient
    private LogAuditoria logAuditoria;

    @Override
    public LogAuditoria getLogAuditoria() {
        return this.logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }
}
