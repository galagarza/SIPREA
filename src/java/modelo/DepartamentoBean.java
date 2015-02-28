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
public class DepartamentoBean {

    /**
     * Creates a new instance of DepartamentoBean
     */
    private String codigo;
    private String nombre;
    private String encargado;

    public DepartamentoBean() {
    }

    public DepartamentoBean(String codigo, String nombre, String encargado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.encargado = encargado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }
    
    

}
