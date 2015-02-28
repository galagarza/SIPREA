/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author aaron
 */
@ManagedBean
@SessionScoped
public class OficinaBean {

    /**
     * Creates a new instance of OficinaBean
     */
    private String idOficina;
    private String nombreOficina;
    private String custodio;
    private String departamento;

    public OficinaBean() {
    }

    public OficinaBean(String idOficina, String nombreOficina, String custodio, String departamento) {
        this.idOficina = idOficina;
        this.nombreOficina = nombreOficina;
        this.custodio = custodio;
        this.departamento = departamento;
    }
    
    

    public String getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(String idOficina) {
        this.idOficina = idOficina;
    }

    public String getNombreOficina() {
        return nombreOficina;
    }

    public void setNombreOficina(String nombreOficina) {
        this.nombreOficina = nombreOficina;
    }

    public String getCustodio() {
        return custodio;
    }

    public void setCustodio(String custodio) {
        this.custodio = custodio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    

}
