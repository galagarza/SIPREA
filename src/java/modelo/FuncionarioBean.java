/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author marcos
 */
@ManagedBean
@SessionScoped
public class FuncionarioBean implements Serializable{

    /**
     * Creates a new instance of Funcionario
     */
    private String cedula, nombre, departamento, direccion, puesto;
    public FuncionarioBean() {
        
    }

    public FuncionarioBean(String cedula, String nombre, String departamento, String direccion, String puesto) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.departamento = departamento;
        this.direccion = direccion;
        this.puesto = puesto;
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    
    
}
