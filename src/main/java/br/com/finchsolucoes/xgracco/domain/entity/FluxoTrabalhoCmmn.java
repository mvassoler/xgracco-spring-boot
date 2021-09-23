package br.com.finchsolucoes.xgracco.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * CMMN do fluxo de trabalho.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Data
@Builder
public class FluxoTrabalhoCmmn implements Serializable {

    private FluxoTrabalho fluxoTrabalho;
    private byte[] content;
    private final String contentType = "application/xml";

    public FluxoTrabalhoCmmn() {
    }

    public FluxoTrabalhoCmmn(byte[] content) {
        this.content = content;
    }

    public FluxoTrabalhoCmmn(FluxoTrabalho fluxoTrabalho, byte[] content) {
        this.fluxoTrabalho = fluxoTrabalho;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FluxoTrabalhoCmmn that = (FluxoTrabalhoCmmn) o;
        return Objects.equals(fluxoTrabalho, that.fluxoTrabalho) &&
                Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fluxoTrabalho, content);
    }
}
