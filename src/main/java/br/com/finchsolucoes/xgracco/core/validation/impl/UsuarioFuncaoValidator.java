package br.com.finchsolucoes.xgracco.core.validation.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.core.validation.UsuarioFuncao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementação validação de preenchimento dos Usuarios com funções que exige escritório.
 *
 * @author Jordano
 * @since 2.1
 */
public class UsuarioFuncaoValidator implements ConstraintValidator<UsuarioFuncao, Usuario> {


    @Override
    public void initialize(UsuarioFuncao constraintAnnotation) {

    }

    /**
     * Implementa a validação.
     *
     * @param usuario
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(Usuario usuario, ConstraintValidatorContext context) {
        if (usuario == null) {
            return false;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(UsuarioFuncao.MESSAGE)
                .addPropertyNode("funcoes")
                .addConstraintViolation();

        return !(usuario.getFuncoes().contains(EnumFuncao.COORDENADOR_AUDIENCISTA) && usuario.getFuncoes().contains(EnumFuncao.AUDIENCISTA_EXTERNO));
    }
}
