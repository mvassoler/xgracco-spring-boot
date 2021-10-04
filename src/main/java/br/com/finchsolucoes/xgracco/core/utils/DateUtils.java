package br.com.finchsolucoes.xgracco.core.utils;

import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class DateUtils {

    public static final String PATTERN_HORA_MIN_SEG = "hh:mm:ss";
    public static final String PATTERN_MES_ANO = "MM/yyyy";
    public static final String PATTERN_MES_DIA_ANO = "MM/dd/yyyy";

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Calendar asCalendar(LocalDateTime dateTime) {
        return GregorianCalendar.from(dateTime.atZone(ZoneId.systemDefault()));
    }

    public static LocalDate asLocalDate(String dataHora, String pattern) {
        return LocalDate.parse(dataHora, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime asLocalDateTime(String dataHora, String pattern) {
        return LocalDateTime.parse(dataHora, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime setFimDeDia(LocalDateTime dateTime) {
        return dateTime.withHour(23).withMinute(59).withSecond(59);
    }

    public static Calendar setFimDeDia(Calendar calendar) {
        Util.correcaoFimDia(calendar);
        return calendar;
    }

    public static Calendar setInicioDeDia(Calendar calendar) {
        Util.correcaoInicioDia(calendar);
        return calendar;
    }

    public static boolean isHojeUltimoDiaDoMes(){
        return LocalDate.now().isEqual(YearMonth.now().atEndOfMonth());
    }

    public static YearMonth asYearMonth(Date data){
        return YearMonth.from(data.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
    }

    public static String getMesAno(Date data){
        return new SimpleDateFormat(PATTERN_MES_ANO).format(data.getTime());
    }


    public static boolean isYearMonthCurrent(Calendar data){
        return YearMonth.from(data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).equals(YearMonth.now());
    }

    public static LocalDate calendarToLocalDate(Calendar data){
        return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static YearMonth calendarToYearMonth(Calendar data){
        return YearMonth.from(calendarToLocalDate(data));
    }

    public static boolean isYearMonthBefore(Calendar data, Long monthSub){
        YearMonth hoje = YearMonth.now().minusMonths(monthSub);

        return DateUtils.calendarToYearMonth(data).isBefore(hoje);
    }

    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date stringToDate(String data, String formato) throws ParseException {
        if (data == null || data.equals("")) {
            return null;
        }
        return new SimpleDateFormat(formato).parse(data);
    }

    public static Calendar stringToCalendar(String data, String formato) throws ParseException {
        return dateToCalendar(stringToDate(data, formato));
    }

    public static LocalDateTime calendarToLocalDateTime(Calendar data){
        return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
