package br.com.finchsolucoes.xgracco.core.validation;

import br.com.finchsolucoes.xgracco.core.validation.impl.ExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Restrição que valida se a entidade existe.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Documented
@Constraint(validatedBy = ExistsValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {

    String message() default "{br.com.finchsolucoes.xgracco.core.validation.Exists.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
