package br.com.finchsolucoes.xgracco.infra.scheduler.cron;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Classe útil para construir expressões cron.
 *
 * @author renan.gigliotti
 */
public class CronBuilder implements Serializable {

    private String seconds = "";
    private String minutes = "";
    private String hours = "";
    private String interval = "";
    private final Set<DayOfMonthEnum> daysOfMonth = new LinkedHashSet<DayOfMonthEnum>();
    private final Set<MonthEnum> months = new LinkedHashSet<MonthEnum>();
    private final Set<DayOfWeekEnum> daysOfWeek = new LinkedHashSet<DayOfWeekEnum>();

    public CronBuilder setSeconds(String seconds) {
        this.seconds = seconds == null ? "" : seconds;
        return this;
    }

    public CronBuilder setMinutes(String minutes) {
        this.minutes = minutes == null ? "" : minutes;
        return this;
    }

    public CronBuilder setHours(String hours) {
        this.hours = hours == null ? "" : hours;
        return this;
    }

    public CronBuilder setInterval(String interval) {
        this.interval = interval;
        return this;
    }

    public CronBuilder addDayOfMonth(DayOfMonthEnum dayOfMonth) {
        this.daysOfMonth.add(dayOfMonth);
        return this;
    }

    public CronBuilder addMonth(MonthEnum month) {
        this.months.add(month);
        return this;
    }

    public CronBuilder addDayOfWeek(DayOfWeekEnum dayOfWeek) {
        this.daysOfWeek.add(dayOfWeek);
        return this;
    }

    public String build() {
        /**
         * Tratando segundos
         */
        if (seconds.isEmpty()) {
            seconds = "*";
        }

        /**
         * Tratando minutos
         */
        if (minutes.isEmpty()) {
            minutes = "*";
        }

        /**
         * Tratando horas
         */
        if (hours.isEmpty()) {
            hours = "*";
        }

        /**
         * Tratando dias do mês
         */
        String dayMonth = "";
        if (interval == null || interval.isEmpty()) {
            int iDayMonth = 0;
            for (DayOfMonthEnum d : daysOfMonth) {
                if (iDayMonth > 0) {
                    dayMonth += ",";
                }
                dayMonth += d.getValue();
                iDayMonth++;
            }
        } else {
            dayMonth = interval;
        }
        if (dayMonth.isEmpty()) {
            dayMonth = "*";
        }

        /**
         * Tratando meses
         */
        String month = "";
        int iMonth = 0;
        for (MonthEnum m : months) {
            if (iMonth > 0) {
                month += ",";
            }
            month += m.getValue();
            iMonth++;
        }
        if (month.isEmpty()) {
            month = "*";
        }

        /**
         * Tratando dias da semana
         */
        String dayWeek = "";
        int iDayWeek = 0;
        for (DayOfWeekEnum d : daysOfWeek) {
            if (iDayWeek > 0) {
                dayWeek += ",";
            }
            dayWeek += d.getValue();
            iDayWeek++;
        }
        if (dayWeek.isEmpty()) {
            dayWeek = "?";
        } else {
            dayMonth = "?";
        }

        /**
         * Construindo expressão cron
         */
        return String.format("%s %s %s %s %s %s",
                seconds, minutes, hours, dayMonth, month, dayWeek);
    }

    public enum DayOfMonthEnum {

        DAY_1("1"),
        DAY_2("2"),
        DAY_3("3"),
        DAY_4("4"),
        DAY_5("5"),
        DAY_6("6"),
        DAY_7("7"),
        DAY_8("8"),
        DAY_9("9"),
        DAY_10("10"),
        DAY_11("11"),
        DAY_12("12"),
        DAY_13("13"),
        DAY_14("14"),
        DAY_15("15"),
        DAY_16("16"),
        DAY_17("17"),
        DAY_18("18"),
        DAY_19("19"),
        DAY_20("20"),
        DAY_21("21"),
        DAY_22("22"),
        DAY_23("23"),
        DAY_24("24"),
        DAY_25("25"),
        DAY_26("26"),
        DAY_27("27"),
        DAY_28("28"),
        DAY_29("29"),
        DAY_30("30"),
        DAY_31("31");

        private final String value;

        private DayOfMonthEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum MonthEnum {

        JANUARY("JAN"),
        FEBRUARY("FEB"),
        MARCH("MAR"),
        APRIL("APR"),
        MAY("MAY"),
        JUNE("JUN"),
        JULY("JUL"),
        AUGUST("AUG"),
        SEPTEMBER("SEP"),
        OCTOBER("OCT"),
        NOVEMBER("NOV"),
        DECEMBER("DEC");

        private final String value;

        private MonthEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum DayOfWeekEnum {

        SUNDAY("SUN"),
        MONDAY("MON"),
        TUESDAY("TUE"),
        WEDNESDAY("WED"),
        THURSDAY("THU"),
        FRIDAY("FRI"),
        SATURDAY("SAT");

        private final String value;

        private DayOfWeekEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
