/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author aaron
 */
@ManagedBean
@RequestScoped
public class ModuloOficina {

    /**
     * Creates a new instance of ControlOficina
     */
    @ManagedProperty(value = "#{oficinaBean}")
    private OficinaBean oficinaBean;
    private String idOficina;

    public ModuloOficina() {
    }

    public OficinaBean getOficinaBean() {
        return oficinaBean;
    }

    public void setOficinaBean(OficinaBean oficinaBean) {
        this.oficinaBean = oficinaBean;
    }

    public String getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(String idOficina) {
        this.idOficina = idOficina;
    }

    public List<OficinaBean> getOficinas(String idDepartamento) throws SQLException {

        List<OficinaBean> oficinas = null;
        oficinas = new ArrayList<OficinaBean>();

        if (idDepartamento.equals("0")) {

            oficinas = null;

        } else {
            String sql = "SELECT DISTINCT oficina.nombre_oficina,oficina.id_oficina,oficina.custodio,oficina.departamento FROM departamento,oficina WHERE '" + idDepartamento + "'=oficina.departamento";
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            ResultSet resultado = cone.consulta(sql);
            while (resultado.next()) {
                OficinaBean ofi = new OficinaBean(resultado.getString("id_oficina").trim(), resultado.getString("nombre_oficina").trim(), resultado.getString("custodio").trim(), resultado.getString("departamento").trim());
                oficinas.add(ofi);
            }
        }
        return oficinas;
    }

    public List<SelectItem> getSelectItemsOfi() throws SQLException {
        List<SelectItem> selectItems = null;
        List<OficinaBean> listaOficinas;
        selectItems = new ArrayList<SelectItem>();

        if (this.getIdOficina() == null) {
            SelectItem itemVacio = new SelectItem("0", "Seleccione una opcion");
            selectItems.add(itemVacio);
        } else {

            listaOficinas = this.getOficinas(this.getIdOficina());

            for (OficinaBean oficina : listaOficinas) {
                SelectItem selectItem = new SelectItem(oficina.getIdOficina(), oficina.getNombreOficina());
                selectItems.add(selectItem);
            }
        }
        return selectItems;
    }

    public String insertarOficina() {
        try {
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql = "SELECT insertar_oficina('" + this.oficinaBean.getIdOficina() + "','" + this.oficinaBean.getNombreOficina() + "','" + this.oficinaBean.getCustodio() + "','" + this.oficinaBean.getDepartamento() + "');";
            System.err.println(sql);
            cone.procedimientos(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ModuloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }//Fin del método insertar oficina

    public void modificarOficina() {
        try {

            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql = "SELECT modificar_oficina('" + this.oficinaBean.getIdOficina() + "','" + this.oficinaBean.getNombreOficina() + "','" + this.oficinaBean.getCustodio() + "','" + this.oficinaBean.getDepartamento() + "');";

            System.err.println(sql);
            cone.procedimientos(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ModuloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//Fin del método modificar oficina

    public void eliminarOficina() {
        try {
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql = "SELECT eliminar_oficina('" + this.oficinaBean.getIdOficina() + "');";

            System.err.println(sql);
            cone.procedimientos(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ModuloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//Fin del método eliminar oficina

    public void consultarOficina() {
        try {

            String idOficina2=" ";
            String nombre_oficina=" ";
            String custodio="";
            String departamento="";
            idOficina2 = this.oficinaBean.getIdOficina();
            ConexionBD cone = new ConexionBD();

            cone.conexion();
            String consultaCedula = "SELECT  id_oficina, nombre_oficina, custodio, departamento FROM public.oficina WHERE oficina.id_oficina='" + this.oficinaBean.getIdOficina().trim() + "';";
            ResultSet result = cone.consulta(consultaCedula);

            while (result.next()) {
                idOficina2 = result.getString(1);
                nombre_oficina = result.getString(2);
                custodio = result.getString(3);
                departamento = result.getString(4);
            }

            //ControlOficina ofi = (ControlOficina) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("controlOficina");
            //ofi.setOficinaBean(new OficinaBean(idOficina2, nombre_oficina, custodio, departamento));
            
        } catch (SQLException ex) {
            Logger.getLogger(ModuloFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
           
}