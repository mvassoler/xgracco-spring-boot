package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.views.RetornoMetodo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author Marcelo Aguiar | Maicon Carraro
 *         <p>
 *         Classe que todas as Entitys deverão estender
 */
public abstract class Entidade {

    public abstract Long getId();

    @JsonIgnore
    public boolean isEmpty() {
        return this == null || getId() == null;
    }

    public <E> RetornoMetodo validar() {
        E obj = (E) this;
        RetornoMetodo retorno = new RetornoMetodo();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<E>> constraintViolations = validator.validate(obj);
        for (ConstraintViolation<?> object : constraintViolations) {
            retorno.setSucesso(false);
            retorno.setMensagem(object.getMessage());
            return retorno;
        }
        retorno.setSucesso(true);

        return retorno;
    }
}
