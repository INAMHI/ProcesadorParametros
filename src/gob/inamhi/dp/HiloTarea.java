/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.inamhi.dp;

import gob.inamhi.md.ProcessedDataMD;
import gob.inamhi.md.RawDataMD;
import gob.inamhi.util.FechasUtiles;
import gob.inamhi.util.Operaciones;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.util.Precision;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

/**
 *
 * @author Diego
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class HiloTarea implements Job {

    public HiloTarea() {
    }

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        JobDataMap jdMap = jec.getJobDetail().getJobDataMap(); //Obtiene el detalle de la tarea y los parámetros depositados en un Mapa.
        String estado = jdMap.get("prpmesta").toString();      //Controla el estado de la tarea.
        int bandera = Integer.parseInt(jdMap.get("bandera").toString());
        int identificadorProceso = Integer.parseInt(jdMap.get("prpm__id").toString());
        RawDataMD est = new RawDataMD();
        if (bandera != 1) {
            if (est.getEstadoDeProceso(identificadorProceso)) {
                estado = "t";
            } else {
                estado = "f";
            }
        }
        if (estado.compareToIgnoreCase("t") == 0) {            //Relaciona el estado actual de la tarea, si es "t" se ejecuta, si es "f" no lo hace.
            Integer copaIn = Integer.parseInt(jdMap.get("copa__id").toString()); //Valor del parámetro a procesar.
            Integer copaOut = Integer.parseInt(jdMap.get("coparesu").toString()); //Valor del parámetro resultante.
            String pasoUnidad = jdMap.get("prpmpaso").toString();
            String operacion = jdMap.get("prpmoper").toString();               //Tipo de operación a aplicar a la lista de datos.
            String hora = jdMap.get("prpmhorc").toString();                    //Hora que se resta al inicio de la ejecución de la tarea desde donde se procesarán los datos.
            String tablaFuente = jdMap.get("prpmfnte").toString();
            String usarFechaInicioComoFechaDelDato = jdMap.get("prpmfind").toString();

            Integer numeroDecimales = Integer.parseInt(jdMap.get("prpmdeci").toString());
            int estacion = Integer.parseInt(jdMap.get("esta__id").toString());
            int numeroDatosMin = Integer.parseInt(jdMap.get("prpmporc").toString());

            int id_configuracion = -1;
            try {
                id_configuracion = Integer.parseInt(jdMap.get("cfges__id").toString());
            } catch (Exception e) {
            }
            String medioTransmision = null;
            try {
                medioTransmision = jdMap.get("trco__id").toString();
            } catch (Exception e) {
            }
            String tablaDestino = jdMap.get("prpmdest").toString();
            System.out.println("Ejecutando... Estacion: " + estacion + " Parametro: " + copaOut);
            String fechaHoraActual = jdMap.get("fechaHoraActual").toString();
            if (bandera == 1) {
                fechaHoraActual = FechasUtiles.getFechaYHoraActualUTC(jec, FechasUtiles.fechaStringADate(fechaHoraActual));
            }

            String fechaFin = fechaHoraActual;                       //Fecha Actual de ejecución de la tarea
            String fechaInicio = FechasUtiles.getFechaYHoraMenosUnaHoraAnterior(fechaHoraActual, hora);   //Fecha Actual de ejecución de la tarea menos el tiempo de diferencia (prpmhorc)
            String fechaFinPaso = FechasUtiles.getFechaYHoraMasUnaHoraPosterior(fechaInicio, pasoUnidad);
            String fechaInicioAux = fechaInicio;
            String fechaFinPasoAux = fechaFinPaso;
            //Clase para manejo de datos (Base de datos y consultas)
            ProcessedDataMD proc = new ProcessedDataMD();

            do {
                double[] datos = ArrayUtils.toPrimitive(est.getDatos(tablaFuente, copaIn, estacion, id_configuracion, medioTransmision, fechaInicioAux, fechaFinPasoAux));     //Se obtiene la lista de datos desde la fecha de inicio hasta la fecha de fin de dicha estación 
                //con dicho copa(parámetro). Se utliza la libreria APACHE COMMONS UTIL para convertir la lista resultante en un arreglo. 
                if (id_configuracion == -1) {
                    id_configuracion = est.getCfges__id();
                }
                if (medioTransmision == null) {
                    medioTransmision = est.getTrco__id();
                }
                String fechaFinPasoAuxHoraLocal = FechasUtiles.getFechaYHoraMenosUnaHoraAnterior(fechaFinPasoAux, "5_h");

                if (usarFechaInicioComoFechaDelDato.compareToIgnoreCase("t") == 0) {
                    fechaFinPasoAuxHoraLocal = FechasUtiles.getFechaYHoraMenosUnaHoraAnterior(fechaInicioAux, "5_h");
                }

                int numeroDeDatos = datos.length;
                String id_dato = estacion + "_" + copaOut + "_" + id_configuracion + "_" + medioTransmision + "_" + fechaFinPasoAuxHoraLocal.replaceAll(" ", "_");
                System.out.println("Estacion: " + estacion + " Parametro: " + copaOut + " Numero de datos: " + numeroDeDatos);
                if (numeroDeDatos >= 1) {                                       //Si existen datos en el arreglo se ejecuta el siguiente bloque.
                    Operaciones op = new Operaciones();                        //Clase de utilidades para realizar operaciones aritméticas. 
                    Double resultado = op.operacion(datos, operacion);         //Se realiza la operación sobre los "datos" definida por la variable "operación".
                    resultado = Precision.round(resultado, numeroDecimales);                 //Se redondea el resultado al decimal deseado.
                    int nval = proc.buscarRepetido(tablaDestino, estacion, copaOut, id_configuracion, medioTransmision, fechaFinPasoAuxHoraLocal);
                    if (nval == -1) {
                        if (numeroDeDatos >= numeroDatosMin) {
                            System.out.println("Insertando Dato Hora: " + fechaFinPasoAuxHoraLocal + " estacion: " + estacion + " copa: " + copaOut + " Operación: " + operacion + ": " + resultado + " #Datos: " + numeroDeDatos);
                            proc.insertarDato(tablaDestino, id_dato, estacion, copaOut, 1, id_configuracion, medioTransmision, fechaFinPasoAuxHoraLocal, resultado, numeroDeDatos, true, "ADMIN");
                        } else {
                            System.out.println("Insertando Dato Hora: " + fechaFinPasoAuxHoraLocal + " estacion: " + estacion + " copa: " + copaOut + " Operación: " + operacion + ": " + resultado + " #Datos: " + numeroDeDatos);
                            proc.insertarDato(tablaDestino, id_dato, estacion, copaOut, 2, id_configuracion, medioTransmision, fechaFinPasoAuxHoraLocal, resultado, numeroDeDatos, true, "ADMIN");
                        }
                        //String tabla, int esta, int copa,int cali, String fecha, double valor, double nval,boolean estado,String uing
                    } else {
                        if (numeroDeDatos > nval) {
                            if (numeroDeDatos >= numeroDatosMin) {
                                String fechaActualLocal = FechasUtiles.getFechaYHoraMenosUnaHoraAnterior(fechaHoraActual, "5_h");
                                System.out.println("Actualizando Dato Hora: " + fechaFinPasoAuxHoraLocal + " estacion: " + estacion + " copa: " + copaOut + " Operación: " + operacion + ": " + resultado + " #Datos: " + numeroDeDatos);
                                proc.actualizarDato(tablaDestino, estacion, copaOut, 1, id_configuracion, medioTransmision, fechaFinPasoAuxHoraLocal, resultado, numeroDeDatos, "ADMIN", fechaActualLocal);
                            }
                        } else {
                            System.out.println("Dato ya Actualizando Hora: " + fechaFinPasoAuxHoraLocal + " estacion: " + estacion + " copa: " + copaOut + " Operación: " + operacion + ": " + resultado + " #Datos: " + numeroDeDatos);
                        }
                    }
                } else {
                    System.out.println("No existen datos para la Estacion: " + estacion + " ParametroIn:" + copaIn + " ParametroOut: " + copaOut + " Operacion: " + operacion + " Numero de datos: " + numeroDeDatos);
                }
                fechaInicioAux = fechaFinPasoAux;
                fechaFinPasoAux = FechasUtiles.getFechaYHoraMasUnaHoraPosterior(fechaInicioAux, pasoUnidad);

            } while (fechaFin.compareToIgnoreCase(fechaFinPasoAux) > 0);

            jdMap.put("fechaHoraActual", FechasUtiles.getFechaYHoraActualUTC(jec.getNextFireTime()));
            jdMap.put("bandera", 2);
            System.out.println("Siguiente ejecucion: " + jdMap.get("fechaHoraActual").toString() + " Estacion: " + estacion + " ParametroIn:" + copaIn + " ParametroOut: " + copaOut + " Operacion: " + operacion);
        }
    }
}