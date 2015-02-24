/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.inamhi.util;

import org.apache.commons.math3.stat.StatUtils;

/**
 *
 * @author Diego
 */
public class Operaciones {

    public Double operacion(double[] valores, String operacion) {
        Double resultado = null;
        try {
            switch (operacion) {
                case "SUMA":

                    resultado = StatUtils.sum(valores);
                    break;
                case "PROMEDIO":
                    resultado = StatUtils.mean(valores);
                    break;
                case "MAXIMA":
                    resultado = StatUtils.max(valores);
                    break;
                case "MINIMA":
                    resultado = StatUtils.min(valores);
                    break;

                default:
                    resultado = StatUtils.mean(valores);
                    break;
            }
        } catch (Exception e) {
        }

        return resultado;

    }
}
