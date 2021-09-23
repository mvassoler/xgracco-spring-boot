/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author saraveronez
 */
@Entity
@Table(name = "TEMPLATEDIRETORIO")
@SequenceGenerator(allocationSize = 1, name = "seqTemplateDiretorio", sequenceName = "SEQ_TEMPLATEDIRETORIO")
@Data
@Builder
@AllArgsConstructor
public class TemplateDiretorio implements Identificavel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqTemplateDiretorio")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TEMPLATE", nullable = true)
    private TemplateDiretorio templateDiretorio;

    @OneToMany(mappedBy = "templateDiretorio", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TemplateDiretorio> templates;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_MODELOTEMPLATE", nullable = false)
    private ModeloTemplate modeloTemplate;

    @Column(name = "ISFINCH")
    private Boolean isFinch;

    @Transient
    private Long idPai;

    public TemplateDiretorio() {
    }

    public TemplateDiretorio(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public String getTextoLog() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        TemplateDiretorio that = (TemplateDiretorio) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
