/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recurso;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author carlos
 */
public class Pool {

    private DataSource dataSource;
    private String driver = "org.gjt.mm.mysql.Driver";//es el driver varia segun la base de datos que usemos
 private  String url = "jdbc:mysql://192.168.1.104/sigloxxi";
 private  String contrasena = "12345";
    private  String usuario = "usuario1";

//    private String url = "jdbc:mysql://sandbox2.ufps.edu.co/ufps_76";
//    private String contrasena = "ufps_29";
//    private String usuario = "ufps_76";
//    

    public Pool() {
        this.inicializarDataSource();;
    }

    private void inicializarDataSource() {
        BasicDataSource basic = new BasicDataSource();
        basic.setDriverClassName(driver);
        basic.setUsername(usuario);
        basic.setPassword(contrasena);
        basic.setUrl(url);
        basic.setMaxActive(5);

        this.dataSource = basic;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

}
