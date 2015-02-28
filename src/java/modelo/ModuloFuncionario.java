/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import modelo.FuncionarioBean;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author marcos
 */
@ManagedBean
@SessionScoped
public class ModuloFuncionario implements Serializable {

    /**
     * Creates a new instance of ControlFuncionario
     */
    @ManagedProperty(value = "#{funcionarioBean}")
    private FuncionarioBean funcionarioBean;

    public ModuloFuncionario() {
    }

    public FuncionarioBean getFuncionarioBean() {
        return funcionarioBean;
    }

    public void setFuncionarioBean(FuncionarioBean funcionarioBean) {
        this.funcionarioBean = funcionarioBean;
    }

    public String insertarFuncionario() {
        try {
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql = "SELECT insertar_funcionario('" + this.funcionarioBean.getCedula() + "','" + this.funcionarioBean.getNombre() + "','" + this.funcionarioBean.getDepartamento() + "','" + this.funcionarioBean.getDireccion() + "','" + this.funcionarioBean.getPuesto() + "');";
//             String sql = "SELECT insertar_funcionario('111','juan','1','aqui','peon');";
            System.err.println(sql);
//            cone.getStatement(sql);
            cone.procedimientos(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ModuloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }//Fin del método insertar funcionario

    public void modificarFuncionario() {
        try {
            System.out.print("Ingreso a modificar Funcionario");
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql = "SELECT modificar_funcionario('" + this.funcionarioBean.getCedula() + "','" + this.funcionarioBean.getNombre() + "','" + this.funcionarioBean.getDepartamento() + "','" + this.funcionarioBean.getDireccion() + "','" + this.funcionarioBean.getPuesto() + "');";

            System.err.println(sql);
            cone.procedimientos(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ModuloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//Fin del método modificar funcionario

    public void eliminarFuncionario() {
        try {
            System.out.print("Ingreso a eliminar Funcionario");
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql = "SELECT eliminar_funcionario('" + this.funcionarioBean.getCedula() + "');";

            System.err.println(sql);
            cone.procedimientos(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ModuloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//Fin del método eliminar funcionario

    public void consultarFuncionario() {
        System.out.print("Ingreso a consultarFuncionario");
        try {
            String nombre = "", cedula = "", departamento = "", direccion = "", puesto = "";
            cedula = this.funcionarioBean.getCedula();
            System.out.println("cedula: " + cedula);
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String consultaCedula = "SELECT * FROM public.funcionario WHERE rtrim(funcionario.cedula)='" + this.funcionarioBean.getCedula().trim() + "';";
            ResultSet result = cone.consulta(consultaCedula);
            while (result.next()) {
                nombre = result.getString("nombre");
                System.err.println("En consultar funcionario, nombre: "+nombre);
                departamento = result.getString("departamento");
                direccion = result.getString("direccion");
                puesto = result.getString("puesto");
            }
            ModuloFuncionario user = (ModuloFuncionario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("controlFuncionario");
//           user.setUsuarioBean(new FuncionarioBean(cedula, nombre, departamento, direccion, puesto) );
            user.setFuncionarioBean(new FuncionarioBean(cedula, nombre, departamento, direccion, puesto));
        } catch (SQLException ex) {
            Logger.getLogger(ModuloFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void probar() {
        System.out.print("Ingreso a probar");

//       this.funcionarioBean.setNombre("acá va el nombre");
    }//Fin del método probar funcionario

//public static void main (String a[])
//{
//    ControlFuncionario c=new ControlFuncionario();
//    c.insertarFuncionario();
//         
//}
}//fin de la clase
