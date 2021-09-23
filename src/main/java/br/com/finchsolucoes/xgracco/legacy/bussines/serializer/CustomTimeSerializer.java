package br.com.finchsolucoes.xgracco.legacy.bussines.serializer;

import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Serializer para campos tipo Date com formato de HORA .
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
public class CustomTimeSerializer extends JsonSerializer<Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat(Util.PATTERN_HORA);

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(dateFormat.format(date));
    }
}
