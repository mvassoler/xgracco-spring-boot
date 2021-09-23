package br.com.finchsolucoes.xgracco.infra.scheduler;

import br.com.finchsolucoes.xgracco.domain.entity.Rotina;
import br.com.finchsolucoes.xgracco.infra.scheduler.cron.CronBuilder;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Scheduler das rotinas agendadas
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
@Component
public class JobQuartzScheduler implements Serializable {

    //TODO - ACERTAR ESTA CLASSE

    private static final Logger logger = LoggerFactory.getLogger(JobQuartzScheduler.class);

    private final String ZERO = "00";

    @Autowired
    private Util util;

    //@Autowired
    private Scheduler scheduler;

    /**
     * Retorna um Date com a próxima execução do Job
     *
     * @param rotina
     */
    public Calendar proximaExecucao(Rotina rotina) {
        try {
            if (!rotina.getAtivo()) {
                return null;
            }

            Trigger trigger = scheduler.getTrigger(getTriggerKey(rotina));
            if (trigger != null && trigger.mayFireAgain()) {
                return DateUtils.toCalendar(trigger.getNextFireTime());
            }
            return null;
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifica existencia do scheduler
     *
     * @param rotina
     * @return
     */
    public boolean verificaExistenciaJob(Rotina rotina) {
        try {
            return scheduler.checkExists(getJobKey(rotina));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Insere um novo job
     *
     * @param rotina
     */
    public void create(Rotina rotina) {
        try {
            if (rotina.getAtivo()) {
                /**
                 * Definindo Job
                 */
                JobKey jobKey = getJobKey(rotina);

                JobDetail jobDetail = null;

                /*JobBuilder.newJob(rotina.getJob().getClazz())
                        .storeDurably()
                        .withIdentity(jobKey)
                        .usingJobData("rotina", rotina.getId())
                        .build();*/

                /**
                 * Definindo a Trigger
                 */
                Trigger trigger = createTrigger(rotina);

                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Altera um Job
     *
     * @param rotina
     */
    public void update(Rotina rotina) {
        try {
            /**
             * Remove o Job Antigo
             */
            if (scheduler.checkExists(getJobKey(rotina))) {
                this.remove(rotina);
            }
            /**
             * Cria um job novo
             */
            if (rotina.getAtivo()) {
                this.create(rotina);
            }
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove um Job
     *
     * @param rotina
     */
    public void remove(Rotina rotina) {
        try {
            scheduler.deleteJob(getJobKey(rotina));
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Executa um Job
     *
     * @param rotina
     */
    public void executarRotina(Rotina rotina) {
        try {
            if (scheduler.checkExists(getJobKey(rotina))) {
                scheduler.triggerJob(getJobKey(rotina));
            }
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter que gera o JobKey
     *
     * @param rotina
     * @return
     */
    private JobKey getJobKey(Rotina rotina) {
        return new JobKey("job" + rotina.getId(), "jobGroup");
    }

    private TriggerKey getTriggerKey(Rotina rotina) {
        return new TriggerKey("trigger" + rotina.getId(), rotina.getJob().toString());
    }

    /**
     * Constrói uma trigger para agendamento do Job
     *
     * @param rotina
     * @return
     */
    private Trigger createTrigger(Rotina rotina) {
        /**
         * Definindo expressão cron
         */
        String cronExpression = buildCronExpression(rotina);

        /**
         * Definindo trigger
         */
        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder
                .newTrigger()
                .withIdentity(getTriggerKey(rotina))
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression));

        /**
         * Definindo data/hora inicial e final
         */
        Calendar dataHoraInicial = rotina.getDataVigenciaInicio();

        if (dataHoraInicial != null) {
            util.correcaoInicioDia(dataHoraInicial);
            triggerBuilder.startAt(dataHoraInicial.getTime());
        }

        Calendar dataHoraFinal = rotina.getDataVigenciaFim();

        if (dataHoraFinal != null) {
            util.correcaoFimDia(dataHoraFinal);
            triggerBuilder.endAt(dataHoraFinal.getTime());
        }

        return triggerBuilder.build();
    }

    /**
     * Constrói a expressão cron com base na execução passada por parâmetro.
     *
     * @param rotina
     * @return
     */
    private String buildCronExpression(Rotina rotina) {
        CronBuilder cronBuilder = new CronBuilder();

        Calendar dataHoraInicial = Calendar.getInstance();
        if (rotina.getHoraInicio() != null) {
            dataHoraInicial = DateUtils.toCalendar(rotina.getHoraInicio());
        } else if (rotina.getDataVigenciaInicio() != null) {
            dataHoraInicial = rotina.getDataVigenciaInicio();
        }

        if (rotina.getTipoRepeticao() != null && rotina.getRepeticao() != null) {
            cronBuilder.setSeconds(ZERO);
            /**
             * Repetição
             */
            switch (rotina.getTipoRepeticao()) {
                case SEGUNDOS:
                    /**
                     * Repetição em segundos
                     */
                    cronBuilder.setSeconds("0/" + rotina.getRepeticao());
                    break;
                case MINUTOS:
                    /**
                     * Repetição em minutos
                     */
                    cronBuilder.setMinutes("0/" + rotina.getRepeticao());
                    break;
                case HORAS:
                    /**
                     * Repetição em horas
                     */
                    cronBuilder.setMinutes(String.valueOf(dataHoraInicial.get(Calendar.MINUTE)))
                            .setHours("0/" + rotina.getRepeticao());
                    break;
            }
        } else {
            cronBuilder.setSeconds(String.valueOf(dataHoraInicial.get(Calendar.SECOND)))
                    .setMinutes(String.valueOf(dataHoraInicial.get(Calendar.MINUTE)))
                    .setHours(String.valueOf(dataHoraInicial.get(Calendar.HOUR_OF_DAY)));
        }

        /**
         * Dias da semana
         */
        rotina.getSemanas().stream().forEach(semana -> cronBuilder.addDayOfWeek(semana.getWeek()));

        /**
         * Dias do mês
         */
        rotina.getDias().stream().forEach(dia -> cronBuilder.addDayOfMonth(dia.getDay()));

        /**
         * Meses
         */
        rotina.getMeses().stream().forEach(mes -> cronBuilder.addMonth(mes.getMonth()));

        return cronBuilder.build();
    }
}
