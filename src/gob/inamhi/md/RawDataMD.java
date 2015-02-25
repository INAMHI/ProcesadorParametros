/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.inamhi.md;

import gob.inamhi.dp.ProcesadorMain;
import gob.inamhi.util.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class RawDataMD {

    private ArrayList<String[]> parametros;
    private Double[] datos;
    private int cfges__id = 0;
    private String trco__id;
    private final String base = "bandahm";
    private final String usuario = "postgres";
    private final String password = "inamhidb";
    private final String driver = "org.postgresql.Driver";
    private final String host = "192.168.1.226";
    private final String puerto = "5432";

    public ArrayList<String[]> getParametrosAProcesar() {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        try {
            // TODO code application logic here
            parametros = new ArrayList<String[]>();
            con.conectar();

            ResultSet rs = con.getSentencia().executeQuery("select prpm__id,copa__id,coparesu,prpmoper,prpmdeci,prpminte,prpmhorc,prpmpaso,prpmfnte,prpmdest,prpmesta,esta__id,prpmporc,cfges__id,trco__id,prpmfind from administrative.prpm ");
            while (rs.next()) {
                String[] prpm = new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16)};
                parametros.add(prpm);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.desconectar();
        }
        return parametros;
    }

    public boolean getEstadoDeProceso(int idProceso) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        boolean resultado = false;
        try {
            // TODO code application logic here
            con.conectar();
            ResultSet rs = con.getSentencia().executeQuery("select prpmesta from administrative.prpm WHERE prpm__id=" + idProceso + " ");
            while (rs.next()) {
                resultado = rs.getBoolean(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.desconectar();
        }
        return resultado;
    }

    public Double[] getDatos(String tablaFuente, int copa, int estacion, int cfges, String trco, String fechaInicio, String fechaFin) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        try {
            // TODO code application logic here
            con.conectar();
            ArrayList<Double> aux = new ArrayList<Double>();
            String query = "";
            if (cfges != -1) {
                if (trco != null) {
                    query = "select datavalo,cfges__id,trco__id from " + tablaFuente + " where esta__id=" + estacion + " and cfges__id=" + cfges + " and datafetd >= '" + fechaInicio + "' and datafetd < '" + fechaFin + "' and copa__id=" + copa + " and trco__id='" + trco + "'"; //and cali1__id=8";
                } else {
                    query = "select datavalo,cfges__id,trco__id from " + tablaFuente + " where esta__id=" + estacion + " and cfges__id=" + cfges + " and datafetd >= '" + fechaInicio + "' and datafetd < '" + fechaFin + "' and copa__id=" + copa + ""; //and cali1__id=8";
                }
            } else {
                if (trco != null) {
                    query = "select datavalo,cfges__id,trco__id from " + tablaFuente + " where esta__id=" + estacion + " and datafetd >= '" + fechaInicio + "' and datafetd < '" + fechaFin + "' and copa__id=" + copa + " and trco__id='" + trco + "'"; //and cali1__id=8";
                } else {
                    query = "select datavalo,cfges__id,trco__id from " + tablaFuente + " where esta__id=" + estacion + " and datafetd >= '" + fechaInicio + "' and datafetd < '" + fechaFin + "' and copa__id=" + copa + ""; //and cali1__id=8";
                }
            }

            System.out.println("buscando datos de Estacion: " + estacion + " Parametro: " + copa + " Query:" + query);
            ResultSet rs = con.getSentencia().executeQuery(query);

            while (rs.next()) {
                aux.add(rs.getDouble(1));
                if (rs.wasNull()) {
                    aux.add(null);
                }
                setCfges__id(rs.getInt(2));
                setTrco__id(rs.getString(3));
            }

            datos = new Double[aux.size()];
            aux.toArray(datos);
        } catch (Exception ex) {
            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.desconectar();
        }
        return datos;
    }

    public void setCfges__id(int cfges__id) {
        this.cfges__id = cfges__id;
    }

    public int getCfges__id() {
        return cfges__id;
    }

    public void setTrco__id(String trco__id) {
        this.trco__id = trco__id;
    }

    public String getTrco__id() {
        return trco__id;
    }
}
