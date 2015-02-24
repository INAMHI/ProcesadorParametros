/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.inamhi.md;

import gob.inamhi.dp.ProcesadorMain;
import gob.inamhi.util.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class ProcessedDataMD {

    private final String base = "bandahm";
    private final String usuario = "postgres";
    private final String password = "inamhidb";
    private final String driver = "org.postgresql.Driver";
    private final String host = "192.168.1.226";
    private final String puerto = "5432";

    public int buscarRepetido(String tabla, int esta, int copa, int cfges, String trco, String fecha) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        int numeroValores = -1;
        try {
            // TODO code application logic here
            con.conectar();
            String ncolumna = tabla.split("\\.")[1];
            String query = "select " + ncolumna + "nval" + " from " + tabla + " where esta__id=" + esta + " and copa__id=" + copa + " and cfges__id=" + cfges + " and trco__id='" + trco + "' and " + ncolumna + "fetd='" + fecha + "'";
            System.out.println("buscando repetido: " + query);
            ResultSet rs = con.getSentencia().executeQuery(query);
            while (rs.next()) {
                numeroValores = rs.getInt(1);
            }
            return numeroValores;
        } catch (Exception ex) {
            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
            return numeroValores;
        } finally {
            con.desconectar();
        }
    }

    public void insertarDato(String tabla, String id, int esta, int copa, int cali, int cfges, String trco, String fecha, double valor, int nval, boolean estado, String uing) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        try {
            // TODO code application logic here
            con.conectar();
            String ncolumna = tabla.split("\\.")[1];
            String query = "insert into " + tabla + " (" + ncolumna + "__id,esta__id,copa__id,cali__id,cfges__id,trco__id," + ncolumna + "fetd," + ncolumna + "valo," + ncolumna + "nval," + ncolumna + "esta," + ncolumna + "uing) values ('" + id + "'," + esta + "," + copa + "," + cali + "," + cfges + ",'" + trco + "','" + fecha + "'," + valor + "," + nval + "," + estado + ",'" + uing + "')";
//            System.out.println("insertando registro: " + query);
            con.getSentencia().execute(query);
            con.commit();

        } catch (Exception ex) {
            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
            con.rollback();

        } finally {
            con.desconectar();
        }
    }

    public void actualizarDato(String tabla, int esta, int copa, int cali, int cfges, String trco, String fecha, double valor, int nval, String umod, String fmod) {
        Conexion con = new Conexion(usuario, password, driver, host, puerto, base);
        try {
            // TODO code application logic here
            con.conectar();
            String ncolumna = tabla.split("\\.")[1];
            String query = "update " + tabla + " set cali__id=" + cali + "," + ncolumna + "valo=" + valor + "," + ncolumna + "nval=" + nval + "," + ncolumna + "umod='" + umod + "'," + ncolumna + "fmod='" + fmod + "' where esta__id=" + esta + " and copa__id=" + copa + " and " + ncolumna + "fetd='" + fecha + "' and cfges__id=" + cfges + " and trco__id='" + trco + "'";
//            System.out.println("actualizando registro: " + query);
            con.getSentencia().executeUpdate(query);
            con.commit();

        } catch (Exception ex) {
            Logger.getLogger(ProcesadorMain.class.getName()).log(Level.SEVERE, null, ex);
            con.rollback();


        } finally {
            con.desconectar();

        }
    }
}
