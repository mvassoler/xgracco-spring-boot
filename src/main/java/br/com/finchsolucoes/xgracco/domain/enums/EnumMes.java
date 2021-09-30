package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.infra.scheduler.cron.CronBuilder;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by renan on 13/12/16.
 */
public enum EnumMes implements Serializable {

    JANEIRO(1, CronBuilder.MonthEnum.JANUARY),
    FEVEREIRO(2, CronBuilder.MonthEnum.FEBRUARY),
    MARCO(3, CronBuilder.MonthEnum.MARCH),
    ABRIL(4, CronBuilder.MonthEnum.APRIL),
    MAIO(5, CronBuilder.MonthEnum.MAY),
    JUNHO(6, CronBuilder.MonthEnum.JUNE),
    JULHO(7, CronBuilder.MonthEnum.JULY),
    AGOSTO(8, CronBuilder.MonthEnum.AUGUST),
    SETEMBRO(9, CronBuilder.MonthEnum.SEPTEMBER),
    OUTUBRO(10, CronBuilder.MonthEnum.OCTOBER),
    NOVEMBRO(11, CronBuilder.MonthEnum.NOVEMBER),
    DEZEMBRO(12, CronBuilder.MonthEnum.DECEMBER);

    private final int id;
    private final CronBuilder.MonthEnum month;

    EnumMes(int id, CronBuilder.MonthEnum month) {
        this.id = id;
        this.month = month;
    }

    public static EnumMes getById(int id) {
        return Stream.of(EnumMes.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumMes.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    public CronBuilder.MonthEnum getMonth() {
        return month;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}