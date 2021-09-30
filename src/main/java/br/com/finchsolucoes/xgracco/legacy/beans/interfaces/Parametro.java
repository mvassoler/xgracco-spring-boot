package br.com.finchsolucoes.xgracco.legacy.beans.interfaces;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;

public interface Parametro {

	EnumParametro getChave();

	String getValor();

	String getClasse();

	String getDescricao();

	Long getIdPessoa();

	void setValor(String valor);

	void setChave(EnumParametro chave);

	void setClasse(String classe);

	void setDescricao(String descricao);

	void setIdPessoa(Long idPessoa);

}
