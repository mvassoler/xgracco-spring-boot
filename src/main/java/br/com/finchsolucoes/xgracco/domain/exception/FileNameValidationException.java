package br.com.finchsolucoes.xgracco.domain.exception;

public class FileNameValidationException extends ValidationException {

    private final Object[] objects;

    public FileNameValidationException(Object... objects) {
        super(returnMensageValidation(objects));
        this.objects = objects;
    }

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }

    private static String returnMensageValidation(Object... objects){
        StringBuilder msg = new StringBuilder();
        for (Object o : objects) {
            msg.append(o);
            msg.append(" ");
        }
        return msg.toString();
    }

}
