package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.ViewInterface;

import java.io.Serializable;

public class ItemFilaEsteiraView implements Serializable {

    private static final long serialVersionUID = 1969275769898517573L;

    /* ID da FILA*/
    @ViewInterface(campo = "f.id", ordem = 0)
    private Long id;

    /* DESCRICAO da FILA*/
    @ViewInterface(campo = "f.descricao", ordem = 1)
    private String descricao;

    @ViewInterface(campo = "e.descricao", ordem = 3)
    private String esteira;

    @ViewInterface(campo = "e.id", ordem = 2)
    private Long idEsteira;

    private String cliente;

    private Long idCliente;

    private Long recurso;

    private Long recursosAtivos;

    private Boolean ativo;

    private String ativoTextoExportar;

    private Long percentual;

    private String totalSolicitacoes;

    private Long totalSolicitacoesLong;

    private Long totalSolicitacoesConcluidasLong;

    @ViewInterface(campo = "f.filaDevolucao", ordem = 4)
    private Boolean filaDevolucao;

    public ItemFilaEsteiraView(Long id, String descricao, Long idEsteira, String esteira, Boolean filaDevolucao) {
        this.id = id;
        this.descricao = descricao;
        this.esteira = esteira;
        this.idEsteira = idEsteira;
        this.filaDevolucao = filaDevolucao;
    }

    public ItemFilaEsteiraView() {
    }

    private ItemFilaEsteiraView(Long id, String descricao, String esteira, Long idEsteira, String cliente, Long idCliente, Long recurso, Long recursosAtivos, Boolean ativo, String ativoTextoExportar, Long percentual, String totalSolicitacoes, Long totalSolicitacoesLong, Long totalSolicitacoesConcluidasLong, Boolean filaDevolucao) {
        this.id = id;
        this.descricao = descricao;
        this.esteira = esteira;
        this.idEsteira = idEsteira;
        this.cliente = cliente;
        this.idCliente = idCliente;
        this.recurso = recurso;
        this.recursosAtivos = recursosAtivos;
        this.ativo = ativo;
        this.ativoTextoExportar = ativoTextoExportar;
        this.percentual = percentual;
        this.totalSolicitacoes = totalSolicitacoes;
        this.totalSolicitacoesLong = totalSolicitacoesLong;
        this.totalSolicitacoesConcluidasLong = totalSolicitacoesConcluidasLong;
        this.filaDevolucao = filaDevolucao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEsteira() {
        return esteira;
    }

    public void setEsteira(String esteira) {
        this.esteira = esteira;
    }

    public Long getIdEsteira() {
        return idEsteira;
    }

    public void setIdEsteira(Long idEsteira) {
        this.idEsteira = idEsteira;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getTotalSolicitacoes() {
        return totalSolicitacoes;
    }

    public void setTotalSolicitacoes(String totalSolicitacoes) {
        this.totalSolicitacoes = totalSolicitacoes;
    }

    public Long getRecurso() {
        return recurso;
    }

    public void setRecurso(Long recurso) {
        this.recurso = recurso;
    }

    public Long getRecursosAtivos() {
        return recursosAtivos;
    }

    public void setRecursosAtivos(Long recursosAtivos) {
        this.recursosAtivos = recursosAtivos;
    }

    public Long getPercentual() {
        return percentual;
    }

    public void setPercentual(Long percentual) {
        this.percentual = percentual;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public String getAtivoTextoExportar() {
        if (ativo != null && ativo) {
            return "Sim";
        }
        return "Não";
    }

    public Long getTotalSolicitacoesLong() {
        return totalSolicitacoesLong;
    }

    public void setTotalSolicitacoesLong(Long totalSolicitacoesLong) {
        this.totalSolicitacoesLong = totalSolicitacoesLong;
    }

    public Long getTotalSolicitacoesConcluidasLong() {
        return totalSolicitacoesConcluidasLong;
    }

    public void setTotalSolicitacoesConcluidasLong(Long totalSolicitacoesConcluidasLong) {
        this.totalSolicitacoesConcluidasLong = totalSolicitacoesConcluidasLong;
    }

    public Boolean getFilaDevolucao() {
        return filaDevolucao;
    }

    public void setFilaDevolucao(Boolean filaDevolucao) {
        this.filaDevolucao = filaDevolucao;
    }

    public ItemFilaEsteiraViewBuilder builder() {
        return new ItemFilaEsteiraViewBuilder();
    }

    public class ItemFilaEsteiraViewBuilder {

        private Long id;
        private String descricao;
        private String esteira;
        private Long idEsteira;
        private String cliente;
        private Long idCliente;
        private Long recurso;
        private Long recursosAtivos;
        private Boolean ativo;
        private String ativoTextoExportar;
        private Long percentual;
        private String totalSolicitacoes;
        private Long totalSolicitacoesLong;
        private Long totalSolicitacoesConcluidasLong;
        private Boolean filaDevolucao;

        public ItemFilaEsteiraViewBuilder() {
        }

        public ItemFilaEsteiraViewBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setDescricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setEsteira(String esteira) {
            this.esteira = esteira;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setIdEsteira(Long idEsteira) {
            this.idEsteira = idEsteira;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setCliente(String cliente) {
            this.cliente = cliente;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setIdCliente(Long idCliente) {
            this.idCliente = idCliente;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setRecurso(Long recurso) {
            this.recurso = recurso;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setRecursosAtivos(Long recursosAtivos) {
            this.recursosAtivos = recursosAtivos;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setAtivo(Boolean ativo) {
            this.ativo = ativo;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setAtivoTextoExportar(String ativoTextoExportar) {
            this.ativoTextoExportar = ativoTextoExportar;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setPercentual(Long percentual) {
            this.percentual = percentual;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setTotalSolicitacoes(String totalSolicitacoes) {
            this.totalSolicitacoes = totalSolicitacoes;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setTotalSolicitacoesLong(Long totalSolicitacoesLong) {
            this.totalSolicitacoesLong = totalSolicitacoesLong;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setTotalSolicitacoesConcluidasLong(Long totalSolicitacoesConcluidasLong) {
            this.totalSolicitacoesConcluidasLong = totalSolicitacoesConcluidasLong;
            return this;
        }

        public ItemFilaEsteiraViewBuilder setFilaDevolucao(Boolean filaDevolucao) {
            this.filaDevolucao = filaDevolucao;
            return this;
        }

        public ItemFilaEsteiraView build() {
            return new ItemFilaEsteiraView(this.id, this.descricao, this.esteira, this.idEsteira, this.cliente, this.idCliente, this.recurso, this.recursosAtivos, this.ativo, this.ativoTextoExportar, this.percentual, this.totalSolicitacoes, this.totalSolicitacoesLong, this.totalSolicitacoesConcluidasLong, this.filaDevolucao);
        }
    }

}
