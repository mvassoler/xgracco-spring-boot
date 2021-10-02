package br.com.finchsolucoes.xgracco.core.validation;

import br.com.finchsolucoes.xgracco.core.validation.impl.UsuarioFuncaoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Restrição que valida preenchimento do usuário.
 *
 * @author Jordano
 * @since 2.1
 */
@Documented
@Constraint(validatedBy = UsuarioFuncaoValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsuarioFuncao {

    String MESSAGE = "{br.com.finchsolucoes.xgracco.core.validation.UsuarioEscritorioFuncao.message}";

    String message() default MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
