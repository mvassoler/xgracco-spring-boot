package br.com.finchsolucoes.xgracco.legacy.bussines.serializer;

import br.com.finchsolucoes.xgracco.domain.entity.CarteiraEventoTarefaTag;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Created by renan on 21/10/16.
 */
public class CarteiraEventoTarefaTagSerializer extends StdSerializer<CarteiraEventoTarefaTag> {

    public CarteiraEventoTarefaTagSerializer() {
        this(null);
    }

    public CarteiraEventoTarefaTagSerializer(Class<CarteiraEventoTarefaTag> t) {
        super(t);
    }

    @Override
    public void serialize(CarteiraEventoTarefaTag carteiraEventoTarefaTag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        if (carteiraEventoTarefaTag.getCondicao() != null) {
            jsonGenerator.writeStringField("condition", carteiraEventoTarefaTag.getCondicao().toString());
        }

        if (carteiraEventoTarefaTag.getRules() != null && carteiraEventoTarefaTag.getRules().size() > 0) {
            jsonGenerator.writeObjectField("rules", carteiraEventoTarefaTag.getRules());
        }
        if (carteiraEventoTarefaTag.getValor() != null) {
            jsonGenerator.writeObjectField("value", carteiraEventoTarefaTag.getValor());
        }

        jsonGenerator.writeEndObject();
    }
}
