package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.NovasAcoesProcesso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author laerte.pereira
 */
@Converter(autoApply = true)
public class NovasAcoesProcessoConverter implements AttributeConverter<NovasAcoesProcesso, String> {

    //TODO - ACERTAR ESTA CLASSE

    private static final Logger logger = LoggerFactory.getLogger(NovasAcoesProcessoConverter.class);

    private final String INFORMACOESADICIONAIS = "informacoesAdicionais";
    private final String DADOSDOPROCESSO = "dadosdoprocesso";
    private final String PARTES = "partesdoprocesso";
    private final String CNJ = "cnj";
    private final String INSTANCIA = "instancia";
    // ATRIBUTOS DENTRO DOS DADOS DO PROCESSO
    private final String STATUS = "Status";
    private final String SUMARIO = "Assunto";
    private final String AREA = "Área";
    private final String ORIGEM = "Origem";
    private final String LOCAL = "Local";
    private final String DISTRIBUICAO = "Distribuição";

    @Override
    public String convertToDatabaseColumn(NovasAcoesProcesso processo) {

        //return JsonAdapter.objectToJson(processo);
        return null;
    }

    @Override
    public NovasAcoesProcesso convertToEntityAttribute(String processo) {
        NovasAcoesProcesso processoJSON = null;
        return null;
        /*
        try {
            JSONObject jsonObject = new JSONObject(processo);
            processoJSON = preencheProcessoViewFromJson(jsonObject);
        } catch (JSONException jex) {
        }

        return processoJSON;

         */
    }

    /*
    public NovasAcoesProcesso preencheProcessoViewFromJson(JSONObject jsonObject) {

        if (jsonObject == null) {
            return null;
        }

        NovasAcoesProcesso processoJSON = new NovasAcoesProcesso();

        try {
            // CNJ
            processoJSON.setNumero(jsonObject.getString(CNJ));

            // INSTANCIA
            processoJSON.setInstancia(jsonObject.getString(INSTANCIA));

            // INFORMACOES ADICIONAIS
            JSONObject informacoesAdicionais = jsonObject.getJSONObject(INFORMACOESADICIONAIS);

            JSONArray dadosDoProcesso = null;

            // DADOS DO PROCESSO (DENTRO DE INFORMACOES ADICIONAIS)
            // FAZ UM LOOPING PARA ENCONTRAR OS DADOS DO PROCESSO
            for (Iterator iterator = informacoesAdicionais.keys(); iterator.hasNext(); ) {
                String obj = String.valueOf(iterator.next());
                if (obj.replace(" ", "").toLowerCase().equals(DADOSDOPROCESSO)) {
                    dadosDoProcesso = informacoesAdicionais.getJSONArray(obj);
                    break;
                }
            }

            // CASO NAO TENHA ENCONTRADO DADOS DO PROCESSO, LE O PROXIMO
            if (dadosDoProcesso == null) {
                return null;
            }

            // FAZ UM LOOPING PARA ENCONTRAR AS INFORMACOES
            for (int indexDados = 0; indexDados < dadosDoProcesso.length(); indexDados++) {

                String id = dadosDoProcesso.getJSONObject(indexDados).getString("ID");
                String valor = dadosDoProcesso.getJSONObject(indexDados).getString("Valor");

                switch (id) {

                    case STATUS:
                        processoJSON.setStatus(valor);
                        break;

                    case SUMARIO:
                        processoJSON.setSumario(valor);
                        break;

                    case AREA:
                        processoJSON.setArea(valor);
                        break;

                    case ORIGEM:
                        processoJSON.setOrigem(valor);
                        break;

                    case LOCAL:
                        processoJSON.setOrigem(valor);
                        break;

                    case DISTRIBUICAO:
                        processoJSON.setDistribuicao(valor);
                        break;

                    default:
                }
            }

            JSONArray partes = null;
            // FAZ UM LOOPING PARA ENCONTRAR AS PARTES
            for (Iterator iterator = informacoesAdicionais.keys(); iterator.hasNext(); ) {
                String obj = String.valueOf(iterator.next());
                if (obj.replace(" ", "").toLowerCase().equals(PARTES)) {
                    partes = informacoesAdicionais.getJSONArray(obj);
                    break;
                }
            }

            if (partes == null) {
                return null;
            }

            // QUANDO HOUVER UMA TRATATIVA, DESCONSIDERAR O LOOPING SOMENTE DOS DOIS PRIMEIROS
            for (int indexParte = 0; indexParte < partes.length() && indexParte < 2; indexParte++) {

                String id = partes.getJSONObject(indexParte).getString("ID");
                String valor = partes.getJSONObject(indexParte).getString("Valor");

                // CONSIDERA A PRIMEIRA PARTE ENCONTRADA COMO SENDO A PARTE INTERESSADA
                if (indexParte == 0) {
                    processoJSON.setParteInteressada(retornaParte(valor));

                } else {
                    processoJSON.setParteContraria(retornaParte(valor));
                }
            }

        } catch (JSONException ex) {
            // FALHA AO PREENCHER OBJETO JSON
            // REGISTAR LOG E RETORNA LISTA VAZIA
            logger.error("Erro ao converter JSON", ex);
            return null;
        }

        return processoJSON;
    }

     */

    private String retornaParte(String valor) {

        if (valor == null) {
            return null;
        }

        // CONSIDERA A PRIMEIRA QUEBRA
        if (valor.contains("<br>")) {
            return valor.split("<br>")[0].trim();
        }

        return valor.trim();
    }
}
