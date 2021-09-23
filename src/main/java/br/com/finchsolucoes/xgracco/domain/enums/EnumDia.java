package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.infra.scheduler.cron.CronBuilder;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by renan on 13/12/16.
 */
public enum EnumDia implements Serializable {

    DIA1(1, CronBuilder.DayOfMonthEnum.DAY_1),
    DIA2(2, CronBuilder.DayOfMonthEnum.DAY_2),
    DIA3(3, CronBuilder.DayOfMonthEnum.DAY_3),
    DIA4(4, CronBuilder.DayOfMonthEnum.DAY_4),
    DIA5(5, CronBuilder.DayOfMonthEnum.DAY_5),
    DIA6(6, CronBuilder.DayOfMonthEnum.DAY_6),
    DIA7(7, CronBuilder.DayOfMonthEnum.DAY_7),
    DIA8(8, CronBuilder.DayOfMonthEnum.DAY_8),
    DIA9(9, CronBuilder.DayOfMonthEnum.DAY_9),
    DIA10(10, CronBuilder.DayOfMonthEnum.DAY_10),
    DIA11(11, CronBuilder.DayOfMonthEnum.DAY_11),
    DIA12(12, CronBuilder.DayOfMonthEnum.DAY_12),
    DIA13(13, CronBuilder.DayOfMonthEnum.DAY_13),
    DIA14(14, CronBuilder.DayOfMonthEnum.DAY_14),
    DIA15(15, CronBuilder.DayOfMonthEnum.DAY_15),
    DIA16(16, CronBuilder.DayOfMonthEnum.DAY_16),
    DIA17(17, CronBuilder.DayOfMonthEnum.DAY_17),
    DIA18(18, CronBuilder.DayOfMonthEnum.DAY_18),
    DIA19(19, CronBuilder.DayOfMonthEnum.DAY_19),
    DIA20(20, CronBuilder.DayOfMonthEnum.DAY_20),
    DIA21(21, CronBuilder.DayOfMonthEnum.DAY_21),
    DIA22(22, CronBuilder.DayOfMonthEnum.DAY_22),
    DIA23(23, CronBuilder.DayOfMonthEnum.DAY_23),
    DIA24(24, CronBuilder.DayOfMonthEnum.DAY_24),
    DIA25(25, CronBuilder.DayOfMonthEnum.DAY_25),
    DIA26(26, CronBuilder.DayOfMonthEnum.DAY_26),
    DIA27(27, CronBuilder.DayOfMonthEnum.DAY_27),
    DIA28(28, CronBuilder.DayOfMonthEnum.DAY_28),
    DIA29(29, CronBuilder.DayOfMonthEnum.DAY_29),
    DIA30(30, CronBuilder.DayOfMonthEnum.DAY_30),
    DIA31(31, CronBuilder.DayOfMonthEnum.DAY_31);

    private final int id;
    private final CronBuilder.DayOfMonthEnum day;

    EnumDia(int id, CronBuilder.DayOfMonthEnum day) {
        this.id = id;
        this.day = day;
    }

    public static EnumDia getById(int id) {
        return Stream.of(EnumDia.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumDia.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    public CronBuilder.DayOfMonthEnum getDay() {
        return day;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}