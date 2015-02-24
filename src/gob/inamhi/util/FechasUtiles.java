/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.inamhi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;

/**
 *
 * @author Duke
 */
public class FechasUtiles {

    /**
     * @return the fecha1hora_nterior
     */
    public static String getFecha1hora_anterior() {
        Calendar HoraAnterior = Calendar.getInstance();
        HoraAnterior.add(Calendar.HOUR, -1);
        int dia = HoraAnterior.get(Calendar.DAY_OF_MONTH);   //dia del mes
        int mes = HoraAnterior.get(Calendar.MONTH) + 1;  //mes, de 0 a 11
        int anio = HoraAnterior.get(Calendar.YEAR);  //año

        return anio + "-" + mes + "-" + dia;
    }

    public static String getFecha1dia_anterior() {
        Calendar HoraAnterior = Calendar.getInstance();
        HoraAnterior.add(Calendar.DAY_OF_MONTH, -1);
        int dia = HoraAnterior.get(Calendar.DAY_OF_MONTH);   //dia del mes
        int mes = HoraAnterior.get(Calendar.MONTH) + 1;  //mes, de 0 a 11
        int anio = HoraAnterior.get(Calendar.YEAR);  //año
        return anio + "-" + mes + "-" + dia;
    }

    /**
     * @return the hora1hora_anterior
     */
    public static String getHora1hora_anterior() {
        Calendar HoraAnterior = Calendar.getInstance();
        HoraAnterior.add(Calendar.HOUR, -1);
        int hora = HoraAnterior.get(Calendar.HOUR_OF_DAY);
        int minuto = HoraAnterior.get(Calendar.MINUTE);
        return hora + ":" + minuto;
    }

    /* ------------------------------------------------------------------------- */
    public static String getFechaActual() { //Fecha actual del sistema
        Date fechaHoy = new Date();
        String fecha_actual;
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        fecha_actual = formato.format(fechaHoy);
        return fecha_actual;
    }

    public static String getHoraActual() { //Hora actual del sistema
        Date ahora = new Date();
        String hora_actual;
        SimpleDateFormat formato = new SimpleDateFormat("hh.mm.ss");
        hora_actual = formato.format(ahora);
        return hora_actual;
    }

    public static String getFechaYHoraActual() {
        Calendar cal = Calendar.getInstance();
        int dia = cal.get(Calendar.DAY_OF_MONTH);   //dia del mes
        int mes = cal.get(Calendar.MONTH) + 1;  //mes, de 0 a 11
        int anio = cal.get(Calendar.YEAR);  //año
        int hora = cal.get(Calendar.HOUR_OF_DAY);  //año
        int minuto = cal.get(Calendar.MINUTE);  //año
        String diaString = dia + "";
        String mesString = mes + "";
        String anioString = anio + "";
        String horaString = hora + "";
        String minutoString = minuto + "";
        if (diaString.length() == 1) {
            diaString = "0" + diaString;
        }
        if (mesString.length() == 1) {
            mesString = "0" + mesString;
        }
        if (anioString.length() == 1) {
            anioString = "0" + anioString;
        }
        if (horaString.length() == 1) {
            horaString = "0" + horaString;
        }
        if (minutoString.length() == 1) {
            minutoString = "0" + minutoString;
        }

        if (Integer.parseInt(minutoString.substring(1)) > 0) {
            minutoString = minutoString.substring(0, 1) + "0";
        }

        String strDate = anioString + "-" + mesString + "-" + diaString + " " + horaString + ":" + minutoString + ":00";
        return strDate;
    }

    public static String getFechaYHoraActualSinMod() {
        Calendar cal = Calendar.getInstance();
        int dia = cal.get(Calendar.DAY_OF_MONTH);   //dia del mes
        int mes = cal.get(Calendar.MONTH) + 1;  //mes, de 0 a 11
        int anio = cal.get(Calendar.YEAR);  //año
        int hora = cal.get(Calendar.HOUR_OF_DAY);  //año
        int minuto = cal.get(Calendar.MINUTE);  //año
        String diaString = dia + "";
        String mesString = mes + "";
        String anioString = anio + "";
        String horaString = hora + "";
        String minutoString = minuto + "";
        if (diaString.length() == 1) {
            diaString = "0" + diaString;
        }
        if (mesString.length() == 1) {
            mesString = "0" + mesString;
        }
        if (anioString.length() == 1) {
            anioString = "0" + anioString;
        }
        if (horaString.length() == 1) {
            horaString = "0" + horaString;
        }
        if (minutoString.length() == 1) {
            minutoString = "0" + minutoString;
        }

        String strDate = anioString + "-" + mesString + "-" + diaString + " " + horaString + ":" + minutoString + ":00";
        return strDate;
    }

    public static String getFechaYHoraActualUTC() {
        Calendar cal = Calendar.getInstance();
        int dia = cal.get(Calendar.DAY_OF_MONTH);   //dia del mes
        int mes = cal.get(Calendar.MONTH) + 1;  //mes, de 0 a 11
        int anio = cal.get(Calendar.YEAR);  //año
        int hora = cal.get(Calendar.HOUR_OF_DAY) + 5;  //hora
        int minuto = cal.get(Calendar.MINUTE);  //minuto
        String diaString = dia + "";
        String mesString = mes + "";
        String anioString = anio + "";
        String horaString = hora + "";
        String minutoString = minuto + "";
        if (diaString.length() == 1) {
            diaString = "0" + diaString;
        }
        if (mesString.length() == 1) {
            mesString = "0" + mesString;
        }
        if (anioString.length() == 1) {
            anioString = "0" + anioString;
        }
        if (horaString.length() == 1) {
            horaString = "0" + horaString;
        }
        if (minutoString.length() == 1) {
            minutoString = "0" + minutoString;
        }

        if (Integer.parseInt(minutoString.substring(1)) > 0) {
            minutoString = minutoString.substring(0, 1) + "0";
        }

        String strDate = anioString + "-" + mesString + "-" + diaString + " " + horaString + ":" + minutoString + ":00";
        return strDate;
    }

    public static String getFechaYHoraActualUTC(Trigger trigger) {
        Calendar cal = Calendar.getInstance();
        Date fireTime = trigger.getNextFireTime();
        cal.setTime(fireTime);
        int dia = cal.get(Calendar.DAY_OF_MONTH);   //dia del mes
        int mes = cal.get(Calendar.MONTH) + 1;  //mes, de 0 a 11
        int anio = cal.get(Calendar.YEAR);  //año
        int hora = cal.get(Calendar.HOUR_OF_DAY) + 5;  //hora
        int minuto = cal.get(Calendar.MINUTE);  //minuto
        String diaString = dia + "";
        String mesString = mes + "";
        String anioString = anio + "";
        String horaString = hora + "";
        String minutoString = minuto + "";
        if (diaString.length() == 1) {
            diaString = "0" + diaString;
        }
        if (mesString.length() == 1) {
            mesString = "0" + mesString;
        }
        if (anioString.length() == 1) {
            anioString = "0" + anioString;
        }
        if (horaString.length() == 1) {
            horaString = "0" + horaString;
        }
        if (minutoString.length() == 1) {
            minutoString = "0" + minutoString;
        }

        if (Integer.parseInt(minutoString.substring(1)) > 0) {
            minutoString = minutoString.substring(0, 1) + "0";
        }

        String strDate = anioString + "-" + mesString + "-" + diaString + " " + horaString + ":" + minutoString + ":00";
        return strDate;
    }

    public static String getFechaYHoraActualUTC(JobExecutionContext jec, Date fecha) {
        Calendar cal = Calendar.getInstance();
        Date fireTime = jec.getTrigger().getFireTimeAfter(fecha);
        cal.setTime(fireTime);
        int dia = cal.get(Calendar.DAY_OF_MONTH);   //dia del mes
        int mes = cal.get(Calendar.MONTH) + 1;  //mes, de 0 a 11
        int anio = cal.get(Calendar.YEAR);  //año
        int hora = cal.get(Calendar.HOUR_OF_DAY) + 5;  //hora
        int minuto = cal.get(Calendar.MINUTE);  //minuto
        String diaString = dia + "";
        String mesString = mes + "";
        String anioString = anio + "";
        String horaString = hora + "";
        String minutoString = minuto + "";
        if (diaString.length() == 1) {
            diaString = "0" + diaString;
        }
        if (mesString.length() == 1) {
            mesString = "0" + mesString;
        }
        if (anioString.length() == 1) {
            anioString = "0" + anioString;
        }
        if (horaString.length() == 1) {
            horaString = "0" + horaString;
        }
        if (minutoString.length() == 1) {
            minutoString = "0" + minutoString;
        }

        if (Integer.parseInt(minutoString.substring(1)) > 0) {
            minutoString = minutoString.substring(0, 1) + "0";
        }

        String strDate = anioString + "-" + mesString + "-" + diaString + " " + horaString + ":" + minutoString + ":00";
        return strDate;
    }

    public static String getFechaYHoraActualUTC(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int dia = cal.get(Calendar.DAY_OF_MONTH);   //dia del mes
        int mes = cal.get(Calendar.MONTH) + 1;  //mes, de 0 a 11
        int anio = cal.get(Calendar.YEAR);  //año
        int hora = cal.get(Calendar.HOUR_OF_DAY) + 5;  //hora
        int minuto = cal.get(Calendar.MINUTE);  //minuto
        String diaString = dia + "";
        String mesString = mes + "";
        String anioString = anio + "";
        String horaString = hora + "";
        String minutoString = minuto + "";
        if (diaString.length() == 1) {
            diaString = "0" + diaString;
        }
        if (mesString.length() == 1) {
            mesString = "0" + mesString;
        }
        if (anioString.length() == 1) {
            anioString = "0" + anioString;
        }
        if (horaString.length() == 1) {
            horaString = "0" + horaString;
        }
        if (minutoString.length() == 1) {
            minutoString = "0" + minutoString;
        }

        if (Integer.parseInt(minutoString.substring(1)) > 0) {
            minutoString = minutoString.substring(0, 1) + "0";
        }

        String strDate = anioString + "-" + mesString + "-" + diaString + " " + horaString + ":" + minutoString + ":00";
        return strDate;
    }

    public static String setFechaYHoraActual(Date fecha) {
        String strDate = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTime(fecha);
        strDate = sdf.format(cal.getTime());
        return strDate;
    }

    public static Date setFechaYHoraActual(String fecha) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(fecha);
        return date;
    }

    public static String setFechaYHoraActualUTC(Date fecha) {
        String strDate = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTime(fecha);
        Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        strDate = sdf.format(cal.getTime());
        return strDate;
    }

    public static String getFechaYHoraMenosUnaHoraAnterior(String fechaActual, String hora) {
        String[] aux = hora.split("_");
        String strDate = "";
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(fechaActual));
            int diferencia = Integer.parseInt(aux[0]);
            switch (aux[1]) {
                case "m":
                    cal.add(Calendar.MINUTE, -diferencia);
                    break;

                case "h":
                    cal.add(Calendar.HOUR, -diferencia);
                    break;

                case "d":
                    cal.add(Calendar.DAY_OF_MONTH, -diferencia);
                    break;

                default:
                    cal.add(Calendar.HOUR, -diferencia);
                    break;
            }
            strDate = sdf.format(cal.getTime());
//            System.out.println(strDate);
            return strDate;
        } catch (ParseException ex) {
            Logger.getLogger(FechasUtiles.class.getName()).log(Level.SEVERE, null, ex);
            return strDate;
        }
    }

    public static String getFechaYHoraMenosUnaHoraAnteriorUTC(String fechaActual, String hora) {
        String[] aux = hora.split("_");
        String strDate = "";
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(fechaActual));
            int diferencia = Integer.parseInt(aux[0]);
            switch (aux[1]) {
                case "m":
                    cal.add(Calendar.MINUTE, -diferencia);
                    break;

                case "h":
                    cal.add(Calendar.HOUR, -diferencia);
                    cal.add(Calendar.HOUR, 5);
                    break;

                case "d":
                    cal.add(Calendar.DAY_OF_MONTH, -diferencia);
                    break;

                default:
                    cal.add(Calendar.HOUR, -diferencia);
                    break;
            }
            strDate = sdf.format(cal.getTime());
//            System.out.println(strDate);
            return strDate;
        } catch (ParseException ex) {
            Logger.getLogger(FechasUtiles.class.getName()).log(Level.SEVERE, null, ex);
            return strDate;
        }
    }

    /*-------------------------------------------------------------------------*/
    public static String getFechaYHoraMasUnaHoraPosterior(String fecha, String hora) {
        String strDate = "";
        try {
            String[] aux = hora.split("_");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(fecha));
            int sumando = Integer.parseInt(aux[0]);
            switch (aux[1]) {
                case "m":
                    cal.add(Calendar.MINUTE, sumando);
                    break;

                case "h":
                    cal.add(Calendar.HOUR, sumando);
                    break;

                case "d":
                    cal.add(Calendar.DAY_OF_MONTH, sumando);
                    break;

                default:
                    cal.add(Calendar.HOUR, sumando);
                    break;
            }
            strDate = sdf.format(cal.getTime());
            return strDate;
        } catch (ParseException ex) {
            Logger.getLogger(FechasUtiles.class.getName()).log(Level.SEVERE, null, ex);
            return strDate;
        }
    }

    public static Date fechaStringADate(String fecha) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(fecha));
            return cal.getTime();
        } catch (ParseException ex) {
            Logger.getLogger(FechasUtiles.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String getFechaYHoraMasUnaHoraPosteriorUTC(String fecha, String hora) {
        String strDate = "";
        try {
            String[] aux = hora.split("_");

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(fecha));
            int sumando = Integer.parseInt(aux[0]);
            switch (aux[1]) {
                case "m":
                    cal.add(Calendar.MINUTE, sumando);
                    break;

                case "h":
                    cal.add(Calendar.HOUR, sumando);
                    cal.add(Calendar.HOUR, 5);
                    break;

                case "d":
                    cal.add(Calendar.DAY_OF_MONTH, sumando);
                    break;

                default:
                    cal.add(Calendar.HOUR, sumando);
                    break;
            }
            strDate = sdf.format(cal.getTime());
            return strDate;
        } catch (ParseException ex) {
            Logger.getLogger(FechasUtiles.class.getName()).log(Level.SEVERE, null, ex);
            return strDate;
        }
    }
}
