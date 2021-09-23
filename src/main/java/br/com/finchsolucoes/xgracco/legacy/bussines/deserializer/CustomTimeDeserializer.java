package br.com.finchsolucoes.xgracco.legacy.bussines.deserializer;

import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Deserializer para campos tipo Date com formato de HORA .
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
public class CustomTimeDeserializer extends StdDeserializer<Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat(Util.PATTERN_HORA);

    public CustomTimeDeserializer() {
        this(null);
    }

    public CustomTimeDeserializer(Class<?> pd) {
        super(pd);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            if (jsonParser.getValueAsString() == null || jsonParser.getValueAsString().isEmpty()) {
                return null;
            }
            return dateFormat.parse(jsonParser.getValueAsString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
