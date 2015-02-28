/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author aaron
 */
@ManagedBean
@SessionScoped
public class ComunicacionBean {

    /**
     * Creates a new instance of ComunicacionBean
     */
    
     private String texto;
     
    public ComunicacionBean() {
    }
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
