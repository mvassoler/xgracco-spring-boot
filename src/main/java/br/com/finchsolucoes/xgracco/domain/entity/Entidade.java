package br.com.finchsolucoes.xgracco.domain.entity;

/**
 * @author Marcelo Aguiar | Maicon Carraro
 *         <p>
 *         Classe que todas as Entitys deverão estender
 */
public abstract class Entidade {

    /*public abstract Long getId();

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
    }*/
}
