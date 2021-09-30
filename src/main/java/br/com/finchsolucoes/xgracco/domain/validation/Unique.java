package br.com.finchsolucoes.xgracco.domain.validation;

import br.com.finchsolucoes.xgracco.domain.validation.impl.UniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Restrição que valida se já existe uma entidade com o(s) mesmo(s) campo(s).
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Documented
@Constraint(validatedBy = UniqueValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {

    String MESSAGE = "{br.com.finchsolucoes.xgracco.domain.validation.Unique.message}";

    String[] value();

    String message() default MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
