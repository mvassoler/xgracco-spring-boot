package br.com.finchsolucoes.xgracco.legacy.beans.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author marceloaguiar
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RelatorioInterface {

    String titulo() default "";

    boolean ignore() default false;

    boolean unwrapped() default false;

    String label() default "";

    String atributo() default "";

    boolean padrao() default false;
}
