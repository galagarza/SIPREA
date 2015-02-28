/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author aaron
 */
@ManagedBean
@SessionScoped
public class ControlLogin {

    /**
     * Creates a new instance of controlLogin
     */
    @ManagedProperty(value = "#{usuarioBean}")
    private UsuarioBean usuarioBean;

    public ControlLogin() {
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

   

    public String verificarUsuario() {
        try {
            ConexionBD conexion = new ConexionBD();
            conexion.conexion();

            String sql = "SELECT * FROM public.usuario";
            ResultSet result = conexion.consulta(sql);
            

            while(result.next()) {
                if (result.getString("clave").trim().equals(this.usuarioBean.getClave().trim()) && result.getString("nombre").trim().equals(this.usuarioBean.getNombre().toString().trim()) && result.getString("perfil").toString().trim().equals(this.usuarioBean.getPerfil().trim())) {
                    this.usuarioBean.setCedula(result.getString("cedula"));
                    this.usuarioBean.setId(result.getString("id_usuario"));
                    if (this.usuarioBean.getPerfil().trim().equals("administrador")) {
                        return "administrador";
                    } else if (this.usuarioBean.getPerfil().trim().equals("consulta")) {
                        return "consulta";
                    } else if (this.usuarioBean.getPerfil().trim().equals("docente")) {
                        return "docente";
                    }
                }
            }//fin del while

        } catch (SQLException ex) {
            Logger.getLogger(ControlLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error";
    }

}
