package br.com.finchsolucoes.xgracco.legacy.beans.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author marceloaguiar
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface  ViewInterface{
    String campo();
    int ordem();
    boolean groupBy() default false;
}
