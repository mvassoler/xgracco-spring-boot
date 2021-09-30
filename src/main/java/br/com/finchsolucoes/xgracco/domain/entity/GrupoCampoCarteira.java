package br.com.finchsolucoes.xgracco.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;

/**
 * @author jordano
 */
@Entity
@IdClass(GrupoCampoCarteiraPK.class)
@Relation(collectionRelation = "grupos-campos-carteiras")
@Table(name = "RELCARTEIRAGRUPOCAMPO")
@Data
@Builder
public class GrupoCampoCarteira {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CARTEIRA", referencedColumnName = "ID")
    private Carteira carteira;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPOCAMPO", referencedColumnName = "ID")
    private GrupoCampo grupoCampo;

    public GrupoCampoCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    public GrupoCampoCarteira(GrupoCampo grupoCampo) {
        this.grupoCampo = grupoCampo;
    }

    public GrupoCampoCarteira() {
    }

    public GrupoCampoCarteira(Carteira carteira, GrupoCampo grupoCampo) {
        this.carteira = carteira;
        this.grupoCampo = grupoCampo;
    }

}
