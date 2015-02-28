/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author aaron
 */
@ManagedBean
@SessionScoped
public class ControlComunicaciones {

    /**
     * Creates a new instance of ControlComunicaciones
     */
    private List<DestinatarioBean> listaDestinatarios;
    @ManagedProperty(value = "#{destinatarioBean}")
    DestinatarioBean destinatario;
     @ManagedProperty(value = "#{comunicacionBean}")
     private ComunicacionBean comunicacionBean;
    private String id_destinatario = "";
     Date fecha = new Date();
     private String texto;

    public ControlComunicaciones() {

    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ComunicacionBean getComunicacionBean() {
        return comunicacionBean;
    }

    public void setComunicacionBean(ComunicacionBean comunicacionBean) {
        this.comunicacionBean = comunicacionBean;
    }

    public String getFechaActual() {
       
        return fecha.toLocaleString();
    }

    public String getId_destinatario() {
        return id_destinatario;
    }

    public void setId_destinatario(String id_destinatario) {
        this.id_destinatario = id_destinatario;
    }

    public List<DestinatarioBean> getListaDestinatarios() {
        try {
            listaDestinatarios = new ArrayList<DestinatarioBean>();
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql = "SELECT cedula,nombre FROM funcionario;";

            ResultSet resultado = cone.consulta(sql);
            while (resultado.next()) {
                listaDestinatarios.add(new DestinatarioBean(resultado.getString(1), resultado.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlComunicaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDestinatarios;
    }

    public DestinatarioBean getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(DestinatarioBean destinatario) {
        this.destinatario = destinatario;
    }

    public List<SelectItem> getSelectItemsDestinatario() throws SQLException {

        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        List<DestinatarioBean> listaDestinatario = this.getListaDestinatarios();

        for (DestinatarioBean destinatario : listaDestinatario) {
            SelectItem selectItem = new SelectItem(destinatario.getCedula(), destinatario.getNombre());
            selectItems.add(selectItem);

        }

        return selectItems;
    }

    public void getEnviarMensaje(ActionEvent actionEvent) {
        
        System.out.println("Verificar "+ actionEvent.toString() );
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, this.comunicacionBean.getTexto(),  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        try {
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql;
            ResultSet resultado;
            int idComunicacion = 0;
            sql = "SELECT MAX (id_comunicacion) FROM comunicacion;";

            resultado = cone.consulta(sql);

            while (resultado.next()) {
                idComunicacion = resultado.getInt(1);
            }
            idComunicacion++;

             ControlLogin usuarioB = (ControlLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("controlLogin");
             String cedula="";
             cedula=usuarioB.getUsuarioBean().getCedula();
            sql = "INSERT INTO comunicacion(\n"
                    + "            id_comunicacion, id_remitente, id_destinatario, texto_mensaje, \n"
                    + "            \"fecha-hora\")  VALUES ('" + idComunicacion + "', '"+cedula+"', '"+this.id_destinatario+"','"+this.comunicacionBean.getTexto()+"', '"+this.getFechaActual()+"');";
            
            System.out.println(sql);
           
        } catch (SQLException ex) {
            Logger.getLogger(ControlComunicaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
       //  return "";
    }

    public void idComunicacionRemitente(ValueChangeEvent event) {

        this.id_destinatario = event.getNewValue().toString();
        System.out.println("id_remitente: "+id_destinatario);
//        return id_remitente;
    }
    
    public void buttonAction(ActionEvent actionEvent){
//         FacesContext fc = FacesContext.getCurrentInstance();
//        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
//        params.get("texto");
//         System.out.println("Verificar "+   params.get("texto"));
         
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, this.getTexto(),  null);
//        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
