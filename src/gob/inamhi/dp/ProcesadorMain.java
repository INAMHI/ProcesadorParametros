/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.inamhi.dp;

import gob.inamhi.md.RawDataMD;
import gob.inamhi.util.FechasUtiles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.quartz.CronScheduleBuilder;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Diego
 */
public class ProcesadorMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SchedulerException {
        RawDataMD est = new RawDataMD();
        ArrayList<String[]> parametros = est.getParametrosAProcesar();
        Iterator it = parametros.iterator();
        Properties props = new Properties();
        Integer numeroHilos = 600;
        props.put("org.quartz.threadPool.threadCount", numeroHilos.toString());
        props.put("org.quartz.scheduler.instanceName", "RMIScheduler");
        props.put("org.quartz.scheduler.rmi.export", "true");
        props.put("org.quartz.scheduler.rmi.createRegistry", "true");
        props.put("org.quartz.scheduler.rmi.registryHost", "localhost");
        props.put("org.quartz.scheduler.rmi.registryPort", "1099");
        props.put("org.quartz.scheduler.rmi.serverPort", "1100");
        SchedulerFactory sf = new StdSchedulerFactory(props);
        Scheduler sched = sf.getScheduler();

        while (it.hasNext()) {
            String[] prpm = (String[]) it.next();
            int identificadorProceso = Integer.parseInt(prpm[0]);
            JobDetail demoJob = newJob(HiloTarea.class).build();
            demoJob.getJobDataMap().put("copa__id", prpm[1]);  //Código del parámetro a procesar
            demoJob.getJobDataMap().put("coparesu", prpm[2]);  //Código del parámetro resultante
            demoJob.getJobDataMap().put("prpmoper", prpm[3]);  //Tipo de operación matemática a realizarse sobre los datos
            demoJob.getJobDataMap().put("prpmdeci", prpm[4]);  //Intervalo de calculo del dato
            demoJob.getJobDataMap().put("prpmhorc", prpm[6]);  //Intervalo de calculo del dato
            demoJob.getJobDataMap().put("prpmpaso", prpm[7]);  //Intervalo de calculo del dato
            demoJob.getJobDataMap().put("prpmfnte", prpm[8]);  //Intervalo de calculo del dato
            demoJob.getJobDataMap().put("prpmdest", prpm[9]);  //Control de estado de la tarea
            demoJob.getJobDataMap().put("prpmesta", prpm[10]);  //Control de estado de la tarea
            demoJob.getJobDataMap().put("esta__id", prpm[11]);  //Control de estado de la tarea
            demoJob.getJobDataMap().put("prpmporc", prpm[12]);  //Control de estado de la tarea
            demoJob.getJobDataMap().put("cfges__id", prpm[13]);  //Control de estado de la tarea
            demoJob.getJobDataMap().put("trco__id", prpm[14]);  //Control de estado de la tarea
            demoJob.getJobDataMap().put("prpmfind", prpm[15]);  //Control de estado de la tarea
            demoJob.getJobDataMap().put("prpm__id", identificadorProceso);  //Control de estado de la tarea
            demoJob.getJobDataMap().put("fechaHoraActual", FechasUtiles.getFechaYHoraActual());  //Control de estado de la tarea
            demoJob.getJobDataMap().put("bandera", 1);  //Control de estado de la tarea


            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("Proceso" + identificadorProceso, "group")
                    .withSchedule(
                    CronScheduleBuilder.cronSchedule(prpm[5]).withMisfireHandlingInstructionFireAndProceed()) //Configuración formato cron para determinar el tiempo de ejecución de la tarea
                    .build();

            try {
                sched.scheduleJob(demoJob, trigger);


            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        sched.start();
    }
}
