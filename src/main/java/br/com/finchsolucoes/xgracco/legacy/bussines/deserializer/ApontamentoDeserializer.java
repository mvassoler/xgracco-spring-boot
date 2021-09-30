package br.com.finchsolucoes.xgracco.legacy.bussines.deserializer;

import br.com.finchsolucoes.xgracco.domain.entity.Apontamento;
import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by felipiberdun on 10/10/2016.
 */
public class ApontamentoDeserializer extends StdDeserializer<Apontamento> {

    public ApontamentoDeserializer() {
        this(null);
    }

    public ApontamentoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Apontamento deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Apontamento apontamento = new Apontamento();

        if (node.get("responsavel") != null && !node.get("responsavel").isNull()) {
            Pessoa pessoa = new Pessoa(node.get("responsavel").get("id").asLong());
            apontamento.setResponsavel(pessoa);
        }
        if (node.get("id") != null && !node.get("id").isNull()) {
            apontamento.setId(node.get("id").asLong());
        }
        if (node.get("descricao") != null) {
            apontamento.setDescricao(node.get("descricao").asText());
        }
        if (node.get("dataLancamento") != null) {
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(node.get("dataLancamento").asText()));
            } catch (ParseException e) {
            }
            apontamento.setDataLancamento(calendar);
        }
        if (node.get("quantidadeHoras") != null) {
            apontamento.setQuantidadeHoras(new BigDecimal(node.get("quantidadeHoras").asDouble()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        if (node.get("processo") != null && !node.get("processo").isNull()) {
            Processo processo = new Processo(node.get("processo").get("id").asLong());
            apontamento.setProcesso(processo);
        }
        if (node.get("faturavel") != null) {
            apontamento.setFaturavel(node.get("faturavel").asBoolean());
        }

        return apontamento;
    }

}
