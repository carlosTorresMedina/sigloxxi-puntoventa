/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloChance;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos esta clase implementa el patron singleton
 */
public class GestorPremio {

   
    private static GestorPremio instancia;

    private GestorPremio() {
    }

    public static GestorPremio getInstance() {
        if (instancia == null) {
            instancia = new GestorPremio();
        }
        return instancia;
    }

    /**
     * consulta la cantidad de chances que ha vendido cada persona en el sistema
     *
     * @param fecha
     * @return
     */
    public ArrayList<TicketDto_1> consultarVentasChance(int codigo, String moneda) {
        ArrayList<TicketDto_1> lista = new ArrayList();
        Connection con = null;
        try {
            con = Recurso.Conexion.getPool().getDataSource().getConnection();
            String sql = "SELECT codigo,sum(vrl_apuesta) "
                    + "FROM ticket"
                    + " where "
                    + " fecha = curdate() and codigo=" + codigo + " and moneda='" + moneda + "' group by codigo";

            PreparedStatement str;
            str = con.prepareStatement(sql);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {
                TicketDto_1 dto = new TicketDto_1();
                dto.setCodigo(rs.getString(1));
                dto.setValor(rs.getInt(2));
                dto.setMoneda(moneda);
                lista.add(dto);
            }
            str.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GestorPremio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return lista;
        }
    }

    
}
