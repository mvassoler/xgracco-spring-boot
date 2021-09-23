package br.com.finchsolucoes.xgracco.domain.validation;

import br.com.finchsolucoes.xgracco.domain.validation.impl.UsuarioBloqueioValidator;

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
@Constraint(validatedBy = UsuarioBloqueioValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsuarioBloqueio {

    String MESSAGE = "{br.com.finchsolucoes.xgracco.domain.validation.UsuarioBloqueio.message}";

    String message() default MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
