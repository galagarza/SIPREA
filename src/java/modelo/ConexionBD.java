/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aaron
 */
public class ConexionBD {
    
    private String driver;
    private String connectString; // ACA PONE DONDE ESTA TU BASE DE DATOS
    private String user;
    private String password;
    private Connection cone;

    public ConexionBD() {

        this.driver = "org.postgresql.Driver";
        this.connectString = "jdbc:postgresql://127.0.0.1:5432/siprea"; // ACA PONE DONDE ESTA TU BASE DE DATOS
        this.user = "postgres";
        this.password = "123";

    }

    public ConexionBD(String driver, String connectString, String user, String password) {
        this.driver = driver;
        this.connectString = connectString;
        this.user = user;
        this.password = password;
    }

    public void conexion() throws SQLException {
        try {
            Class.forName(this.driver);
            this.cone = DriverManager.getConnection(this.connectString, this.user, this.password);
            System.out.println("Se realizó la conexión correctamente");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PreparedStatement getStatement(String query) {
        PreparedStatement stmt = null;

        try {
            stmt = this.cone.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stmt;
    }

    public ResultSet consulta(String consultaSQL) throws SQLException {
        Statement comando = cone.createStatement();
        ResultSet resultado = comando.executeQuery(consultaSQL);
        return resultado;
    }

    public void procedimientos(String procedimientoSQL) throws SQLException {
        Statement estado = cone.createStatement();
        estado.execute(procedimientoSQL);
        estado.close();
    }

    public void cerrarConexion() {
        try {
            cone.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void insertarUsuario(String sql) {
//        String res = "";
//        try {
//
//            Statement comando = cone.createStatement();
//            ResultSet resultado = comando.executeQuery(sql);
//
//          //  statement = conn.createStatement();
//            //resultSet = statement.executeQuery("SELECT registrar('"+Cedula_Identidad+"'); ");
//            while (resultado.next()) {
//                res = resultado.getString(1);
//            }
//        } catch (SQLException ex) {
//            System.err.println(ex.getMessage());
//        }
//
//    }
//    public static void main(String args[]){
//        try {
//            ConexionBD conex=new ConexionBD();
//            conex.conexion();
//           ResultSet resultado= conex.consulta("SELECT * FROM public.usuario");
//           while(resultado.next()){
//               System.err.println(resultado.getString("cedula"));
//           }
//        } catch (SQLException ex) {
//            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
}
