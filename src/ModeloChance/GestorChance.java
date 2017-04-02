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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class GestorChance {

    public String fechaSistema(){
      String lista = "";
        Connection con = null;
        try {
            con = Recurso.Conexion.getPool().getDataSource().getConnection();
            String sql = "select NOW() from dual";
            PreparedStatement str;
            str = con.prepareStatement(sql);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {
               lista=rs.getString(1);

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
                    Logger.getLogger(GestorChance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return lista;
        }

    }
    
    private ArrayList<RecaudoDto> consultarListaLoterias( Connection con){
     ArrayList<RecaudoDto> lista = new ArrayList();
        
        try {
           
            String sql = "select lo.nombre from loteria lo ";
            
            System.out.println(sql);
            PreparedStatement str;
            str = con.prepareStatement(sql);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {
                RecaudoDto dto=new RecaudoDto();
                dto.setNombreLoteria(rs.getString(1));
                dto.setValor(0);
                lista.add(dto);
               

            }
            str.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            return lista;
        }
    }
    
    /**
     * Este metodo retorna una lista por persona de los tickets vendidos por loteria en la fecha del sistema.
     * @param moneda
     * @param codigoPersona
     * @return 
     */
    public  ArrayList<RecaudoDto> listarRecaudo(String moneda,int codigoPersona){
    ArrayList<RecaudoDto> lista =null;
        Connection con = null;
        try {
            con = Recurso.Conexion.getPool().getDataSource().getConnection();
            lista=this.consultarListaLoterias(con);
            String sql = "select lo.nombre, SUM(ti.vrl_apuesta) from loteria lo LEFT JOIN lotxticket loti on lo.codigo=loti.cod_lot LEFT JOIN ticket ti on loti.serial = ti.serial"
                    + " where ti.moneda = '"+moneda+"' and ti.fecha=curdate() and ti.codigo="+codigoPersona+"  GROUP BY lo.nombre";
            
            System.out.println(sql);
            PreparedStatement str;
            str = con.prepareStatement(sql);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {
                RecaudoDto dto=buscarLoteria(rs.getString(1),lista);     
                dto.setValor(rs.getInt(2));
                
               

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
                    Logger.getLogger(GestorChance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
              
            return lista;
        }

    }
      
     private RecaudoDto buscarLoteria(String string, ArrayList<RecaudoDto> lista) {
       for(RecaudoDto dto:lista){
       if(string.equals(dto.getNombreLoteria()))
           return dto;
       }
       return null;
    }
    
    /**
     * registra la impresion
     *
     * @param ticket
     * @return
     */
    public String Imprimir(TicketDto ticket) {
        Connection con = null;

        boolean resu = false;
        String codigo="";
        try {
            con = Recurso.Conexion.getPool().getDataSource().getConnection();
            con.setAutoCommit(false);
            codigo=this.generarCodigo(con);
            ticket.setSerial(codigo);
            boolean x = this.registrarTicketEncabezado(ticket, con);
            boolean y = this.registrarTicketLoterias(ticket, con);
            boolean z = this.registrarTicketItems(ticket, con);
            if (y & x & z) {
                resu = true;
            }
            con.commit();
        } catch (SQLException ex) {
            con.rollback();
            ex.printStackTrace();
            throw ex;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GestorChance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             return codigo;
        }
        

    }

    private boolean registrarTicketEncabezado(TicketDto ticket, Connection con) throws SQLException {
        //aqui registro el encabaezado
        PreparedStatement stmt = null;
        boolean exito = false;

        stmt = con.prepareStatement("INSERT INTO ticket ( fecha,codigo,serial,vrl_apuesta,moneda) values (NOW(), ?, ?,?,?)");

        
        stmt.setInt(1, ticket.getPersona().getCodigo());
        stmt.setString(2, ticket.getSerial());
        stmt.setInt(3, ticket.getVlr_apuesta());
        stmt.setString(4, ticket.getMoneda());
        int total = stmt.executeUpdate();

        if (total > 0) {

            exito = true;
        }
        stmt.close();
        return exito;
    }

    private boolean registrarTicketLoterias(TicketDto ticket, Connection con) throws SQLException {
        //aqui registro las loterias
        PreparedStatement stmt = null;
        boolean exito = false;

        for (LotxTicketDto dto : ticket.getLoterias()) {

            stmt = con.prepareStatement("INSERT INTO lotxticket ( fecha,codigo,serial,cod_lot) values (?, ?, ?,?)");
            stmt.setString(1, ticket.getFecha());
            stmt.setInt(2, ticket.getPersona().getCodigo());
            stmt.setString(3, ticket.getSerial());
            stmt.setInt(4, dto.getCodigoLoteria());

            int total = stmt.executeUpdate();

            if (total > 0) {

                exito = true;
            }

        }
        if (stmt != null) {
            stmt.close();
        }
        return exito;
    }

    private boolean registrarTicketItems(TicketDto ticket, Connection con) throws SQLException {
        //aqui registro  los ticket
        boolean res = false;
        PreparedStatement stmt = null;
        boolean exito = false;
        int ordinal = 1;

        stmt = con.prepareStatement("INSERT INTO item (fecha,codigo,serial,ordinal,numero,pleno,combinado,pata,uña) values (?, ?, ?,?,?,?,?,?,?)");
        for (ItemDto dto : ticket.getItems()) {
            stmt.setString(1, ticket.getFecha());
            stmt.setInt(2, ticket.getPersona().getCodigo());
            stmt.setString(3, ticket.getSerial());
            stmt.setInt(4, ordinal);
            stmt.setString(5, dto.getNumero());
            stmt.setInt(6, dto.getPleno());
            stmt.setInt(7, dto.getCombinado());
            stmt.setInt(8, dto.getPata());
            stmt.setInt(9, dto.getUña());
            int total = stmt.executeUpdate();
            if (total > 0) {
                exito = true;
            }
            ordinal++;
        }
        stmt.close();
        return exito;

    }

    /**
     * lista los planes loterias por fechas actual.
     *
     * @return
     */
    public ArrayList<LoteriaDto> listarPlanLoteriaXFecha() {
        Date hoy = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String fechaHoy = formato.format(hoy);
        hoy.setHours(23);
        hoy.setMinutes(59);
        String fechaHoyF = formato.format(hoy);
        System.out.println(fechaHoy);
        System.out.println(fechaHoyF);

        ArrayList<LoteriaDto> listaLoterias = new ArrayList();
        ArrayList<PlanLoteriaDto> lista = null;
        Connection con = null;
        try {
            con = Recurso.Conexion.getPool().getDataSource().getConnection();
            String sql = "SELECT p.fecha,Date_format(p.cierre,'%Y-%m-%d %H:%i'),p.numero,p.serie,p.escrutado,p.cerrado,l.nombre,l.codigo FROM planloteria p,loteria l where p.cod_lot=l.codigo and p.cierre > NOW()  and p.fecha = CURDATE()";
            PreparedStatement str;
            str = con.prepareStatement(sql);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {
                lista = new ArrayList();
                PlanLoteriaDto plan = new PlanLoteriaDto();
                plan.setFecha(rs.getString(1));
                plan.setCierreBaseDatos(rs.getString(2));
                plan.setNumero(rs.getString(3));
                plan.setSerie(rs.getString(4));
                plan.setEscrutado(rs.getString(5));
                plan.setCerrado(rs.getString(6));

                lista.add(plan);
                LoteriaDto dto = new LoteriaDto();
                dto.setNombre(rs.getString(7));
                dto.setCodigo(rs.getInt(8));

                dto.setPlanLoterias(lista);
                listaLoterias.add(dto);
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
                    Logger.getLogger(GestorChance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return listaLoterias;
        }

    }

    public ArrayList<PersonaDto> listarPersonaXCedula(long codigo)  {
        ArrayList<PersonaDto> lista = new ArrayList();
        Connection con = null;
        try {
            con = Recurso.Conexion.getPool().getDataSource().getConnection();
            String sql = "SELECT nuip,codigo,nombre,apellido,direccion,telefono,tipo,activo,porcentaje FROM persona where codigo=" + codigo + " and tipo='V' limit 1";
            PreparedStatement str;
            str = con.prepareStatement(sql);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {
                PersonaDto dto = new PersonaDto();
                dto.setNuip(rs.getInt(1));
                dto.setCodigo(rs.getInt(2));
                dto.setNombre(rs.getString(3));
                dto.setApellido(rs.getString(4));
                dto.setDireccion(rs.getString(5));
                dto.setTelefono(rs.getString(6));
                dto.setTipo(rs.getString(7));
                dto.setActivo(rs.getString(8));
                dto.setPorcentaje(rs.getInt(9));
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
                    Logger.getLogger(GestorChance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return lista;
        }

    }

    /**
     * genera el codigo del serial
     *
     * @return
     */
    public String generarCodigo(Connection con) {

        
        String serial = "";
        try {
            con=Recurso.Conexion.getPool().getDataSource().getConnection();
            String sql = "SELECT serial FROM ticket order by serial DESC limit 1";
            PreparedStatement str;
            str = con.prepareStatement(sql);
            ResultSet rs = str.executeQuery();

            if (rs.next()) {
                //lo aumento
                serial = rs.getString(1);
                serial = aumentarSerial(serial);
            } else {
                //lo creo
                serial = crearSerial();
            }

            str.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
finally{
        if(con!=null){
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestorChance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
        return serial;

    }

    private String crearSerial() {
        return "AA000001";
    }

    private String aumentarSerial(String serial) {
        String letras = serial.substring(0, 2);
        String numero = serial.substring(2, 8);
        int valor = Integer.parseInt(numero);

        valor = valor + 1;
        numero = valor + "";
        if (numero.length() == 1) {
            numero = "00000" + valor;
        } else if (numero.length() == 2) {
            numero = "0000" + valor;
        } else if (numero.length() == 3) {
            numero = "000" + valor;
        } else if (numero.length() == 4) {
            numero = "00" + valor;
        } else if (numero.length() == 5) {
            numero = "0" + valor;

        } else if (numero.length() >= 6) {

            if (valor <= 999999) {
                numero = valor + "";

            } else {

                numero = "000000";
                char letra[] = letras.toCharArray();

                if (letra[0] == 'Z' && letra[1] == 'Z') {
                    return "error";//ya se acaban las posibilidades de generar seriales
                }

                if (letra[1] == 'Z') {
                    letra[1] = 'A';
                    letra[0] = this.aumentarLetra(letra[0]);
                } else if (letra[0] == 'Z') {

                    letra[1] = aumentarLetra(letra[1]);

                } else {

                    letra[1] = aumentarLetra(letra[0]);
                }

                letras = letra[0] + "" + letra[1];

            }
        }

        return letras + numero;

    }

  

    private char aumentarLetra(char letra) {
        if (letra == 'A') {

            return 'B';
        }
        if (letra == 'B') {
            return 'C';
        }
        if (letra == 'C') {
            return 'D';
        }
        if (letra == 'D') {
            return 'E';
        }
        if (letra == 'E') {
            return 'F';
        }
        if (letra == 'F') {
            return 'G';
        }
        if (letra == 'G') {
            return 'H';
        }
        if (letra == 'H') {
            return 'I';
        }
        if (letra == 'I') {
            return 'J';
        }
        if (letra == 'J') {
            return 'K';
        }
        if (letra == 'K') {
            return 'L';
        }
        if (letra == 'L') {
            return 'M';
        }
        if (letra == 'M') {
            return 'N';
        }
        if (letra == 'N') {
            return 'Ñ';
        }
        if (letra == 'Ñ') {
            return 'O';
        }
        if (letra == 'O') {
            return 'P';
        }
        if (letra == 'P') {
            return 'Q';
        }
        if (letra == 'Q') {
            return 'R';
        }
        if (letra == 'R') {
            return 'S';
        }
        if (letra == 'S') {
            return 'T';
        }
        if (letra == 'T') {
            return 'V';
        }
        if (letra == 'V') {
            return 'W';
        }
        if (letra == 'W') {
            return 'X';
        }
        if (letra == 'X') {
            return 'Y';
        }
        if (letra == 'Y') {
            return 'Z';
        }
        return '@';
    }

   

   

}
