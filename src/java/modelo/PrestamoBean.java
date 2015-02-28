/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author aaron
 */
@ManagedBean
@SessionScoped
public class PrestamoBean {

    /**
     * Creates a new instance of PrestamoBean
     */
    private String idPrestamo,idSolicitud,departamento,oficina;
    private Date fechaPrestamo,fechaDevolucion;
    private ActivoBean listaActivos[];
    
    public PrestamoBean() {
    }

    public PrestamoBean(String idSolicitud, ActivoBean[] listaActivos) {
        this.idSolicitud = idSolicitud;
        this.listaActivos = listaActivos;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public ActivoBean[] getListaActivos() {
        return listaActivos;
    }

    public void setListaActivos(ActivoBean[] listaActivos) {
        this.listaActivos = listaActivos;
    }
    
    
}
