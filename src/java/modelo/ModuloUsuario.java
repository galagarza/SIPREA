/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author aaron
 */
@ManagedBean
@SessionScoped
public class ModuloUsuario {

    /**
     * Creates a new instance of ModuloUsuario
     */
    @ManagedProperty(value = "#{usuarioBean}")
    private UsuarioBean usuarioBean;

    public ModuloUsuario() {

    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }
    
     public String insertarUsuario() {
        try {
            String nombre = "";
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql="INSERT INTO usuario(id_usuario, nombre, clave, cedula, perfil) VALUES ('" + this.usuarioBean.getId() + "','" + this.usuarioBean.getNombre() + "','" + this.usuarioBean.getClave() + "', '" + this.usuarioBean.getCedula() +"', '" + this.usuarioBean.getPerfil() +"');";
           // String sql = "SELECT public.insertar_usuario('" + this.usuario.getId() + "','" + this.usuario.getNombre() + "','" + this.usuario.getClave() + "', '" + this.usuario.getCedula() + "', '" + this.usuario.getPerfil() + "'); ";
            System.err.println(sql);
            cone.getStatement(sql);
          //  cone.insertarUsuario(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ModuloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public void buscarCedula(ActionEvent e) throws SQLException {

        String nombre = "",cedula="",clave="",perfil="",id="";
        ConexionBD cone = new ConexionBD();
        cone.conexion();
        String consultaCedula = "SELECT usuario.nombre,cedula,clave,perfil,id_usuario FROM public.usuario WHERE usuario.cedula='" + this.usuarioBean.getCedula() + "';";
        ResultSet result = cone.consulta(consultaCedula);
        while(result.next()){
            nombre = result.getString("nombre");
            cedula=result.getString("cedula");
            clave=result.getString("clave");
            perfil=result.getString("perfil");
            id=result.getString("id_usuario");
        }
        
        ModuloUsuario user = (ModuloUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("moduloUsuario");
        user.setUsuarioBean(new UsuarioBean(cedula, perfil, clave, nombre, id));

    }

}
