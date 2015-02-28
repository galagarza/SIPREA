/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author aaron
 */
@ManagedBean
public class SolicitudPendiente {

    private String cedula, nombre, valor;
    private int id_solicitud;
    private Date fecha_solicitud;

    public SolicitudPendiente(String cedula, String nombre, String valor, int id_solicitud, Date fecha_solicitud) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.valor = valor;
        this.id_solicitud = id_solicitud;
        this.fecha_solicitud = fecha_solicitud;
    }

    public SolicitudPendiente() {
    }
    

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public Date getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(Date fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }
    
    
}
