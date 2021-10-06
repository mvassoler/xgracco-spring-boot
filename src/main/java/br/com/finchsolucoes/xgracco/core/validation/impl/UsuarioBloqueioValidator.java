package br.com.finchsolucoes.xgracco.core.validation.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.core.validation.UsuarioBloqueio;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Objects;

/**
 * Implementação validação de preenchimento dos Usuarios com funções que exige escritório.
 *
 * @author Jordano
 * @since 2.1
 */
public class UsuarioBloqueioValidator implements ConstraintValidator<UsuarioBloqueio, Usuario> {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private UsuarioService usuarioService;

    /**
     * Implementa a validação.
     *
     * @param usuario
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(Usuario usuario, ConstraintValidatorContext context) {

        if (Objects.isNull(usuario)) return false;
        if (Objects.isNull(usuario.getId()) || !usuario.getBloqueado()) return true;

        HashMap<String, String> pendencias =  null; //usuarioService.retornarPendenciasUsuario(usuario);

        boolean isValid = pendencias.isEmpty();

        if (!isValid){

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(UsuarioBloqueio.MESSAGE)
                    .addPropertyNode("bloqueado")
                    .addConstraintViolation();

            pendencias.forEach((pendencia, mensagem)->{
                context.buildConstraintViolationWithTemplate(mensagem)
                        .addPropertyNode(pendencia)
                        .addConstraintViolation();
            });
        }

        return isValid;
    }


}
