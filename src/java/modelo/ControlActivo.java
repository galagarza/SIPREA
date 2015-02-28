/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author aaron
 */
@ManagedBean
@SessionScoped
public class ControlActivo {

    /**
     * Creates a new instance of ControlActivo
     */
    @ManagedProperty(value = "#{activoBean}")
    private ActivoBean activo;

    private String codigoOficina = "";
    private ArrayList<ActivoBean> listaActivos;
    private ActivoBean[] selectedActivos;
    private ActivoBean selectedActivo;

    public ActivoBean[] getSelectedActivos() {
        return selectedActivos;
    }

    public void setSelectedActivos(ActivoBean[] selectedActivos) {
        this.selectedActivos = selectedActivos;
    }

    public ActivoBean getSelectedActivo() {
        return selectedActivo;
    }

    public void setSelectedActivo(ActivoBean selectedActivo) {
        this.selectedActivo = selectedActivo;
    }

    public ControlActivo() {
        this.listaActivos = new ArrayList<ActivoBean>();
       // listaActivos.add(new ActivoBean("Laptop XPS", "A457", "P6", "Dell"));
    }

    public ActivoBean getActivo() {
        return activo;
    }

    public void setActivo(ActivoBean activo) {
        this.activo = activo;
    }

    public String getCodigoOficina() {
        return codigoOficina;
    }

    public void setCodigoOficina(String codigoOficina) {
        this.codigoOficina = codigoOficina;
    }

    public ArrayList<ActivoBean> getListaActivos() {
        return listaActivos;
    }

    public void setListaActivos(ArrayList<ActivoBean> listaActivos) {
        this.listaActivos = listaActivos;
    }

    public ArrayList<ActivoBean> obtenerListaActivos() throws SQLException {
        this.listaActivos = new ArrayList<ActivoBean>();

        ConexionBD cone = new ConexionBD();
        cone.conexion();
        String consulta = "SELECT activo.placa,activo.nombre,activo.marca,activo.modelo FROM activo,oficina,activo_funcionario WHERE oficina.id_oficina= '" + this.getCodigoOficina() + "' AND oficina.custodio=activo_funcionario.cedula_custodio AND activo_funcionario.placa_activo=activo.placa";
        ResultSet result = cone.consulta(consulta);
        while (result.next()) {
            ActivoBean activoNew = new ActivoBean(result.getString("nombre").trim().toString(), result.getString("placa").trim().toString(), result.getString("modelo").trim().toString(), result.getString("marca").trim().toString());
            this.listaActivos.add(activoNew);
        }
        return this.listaActivos;
    }

    public void codigoOficinaListener(ValueChangeEvent vHE) throws SQLException {

        FacesContext faceContext = FacesContext.getCurrentInstance();
        UIViewRoot uIViewRoot = faceContext.getViewRoot();
        String nuevoCodigoOficina = (String) vHE.getNewValue();
        System.out.println("Codigo oficina: "+nuevoCodigoOficina);
        this.setCodigoOficina(nuevoCodigoOficina);

        // ControlActivo activoH = (ControlActivo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("controlActivo");
        ControlActivo activoH = (ControlActivo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("controlActivo");
        activoH.setListaActivos(this.obtenerListaActivos());
    }
}
