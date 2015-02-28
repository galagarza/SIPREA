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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author aaron
 */
@ManagedBean
@SessionScoped

public class ControlOficina {

    /**
     * Creates a new instance of ControlOficina
     */
    @ManagedProperty(value = "#{oficinaBean}")
    private OficinaBean oficinaBean;
    private String idOficina;

    public ControlOficina() {
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

}
