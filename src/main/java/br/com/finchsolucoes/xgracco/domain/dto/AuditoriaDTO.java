package br.com.finchsolucoes.xgracco.domain.dto;

import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by paulomarcon
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditoriaDTO{

    private Usuario usuario;
    private Calendar dataInicio;
    private Calendar dataFim;
    private Class classe;
    private String nomeClasse;

}