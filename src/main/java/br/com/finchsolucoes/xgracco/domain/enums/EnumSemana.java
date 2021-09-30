package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.infra.scheduler.cron.CronBuilder;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by renan on 13/12/16.
 */
public enum EnumSemana implements Serializable {

    DOMINGO(1, CronBuilder.DayOfWeekEnum.SUNDAY),
    SEGUNDA(2, CronBuilder.DayOfWeekEnum.MONDAY),
    TERCA(3, CronBuilder.DayOfWeekEnum.TUESDAY),
    QUARTA(4, CronBuilder.DayOfWeekEnum.WEDNESDAY),
    QUINTA(5, CronBuilder.DayOfWeekEnum.THURSDAY),
    SEXTA(6, CronBuilder.DayOfWeekEnum.FRIDAY),
    SABADO(7, CronBuilder.DayOfWeekEnum.SATURDAY);

    private final int id;
    private final CronBuilder.DayOfWeekEnum week;

    EnumSemana(int id, CronBuilder.DayOfWeekEnum week) {
        this.id = id;
        this.week = week;
    }

    public static EnumSemana getById(int id) {
        return Stream.of(EnumSemana.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumSemana.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    public CronBuilder.DayOfWeekEnum getWeek() {
        return week;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}