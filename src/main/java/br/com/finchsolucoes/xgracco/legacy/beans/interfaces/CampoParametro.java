package br.com.finchsolucoes.xgracco.legacy.beans.interfaces;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface  CampoParametro{
	
    EnumParametro campo();
    
}
