/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author aaron
 */
@ManagedBean
@SessionScoped
public class ControlSolicitud {
    
    @ManagedProperty(value = "#{solicitudActivosBean}")
    private SolicitudActivosBean solicitudActivosBean;
    
     

    public SolicitudActivosBean getSolicitudActivosBean() {
        return solicitudActivosBean;
    }

    public void setSolicitudActivosBean(SolicitudActivosBean solicitudActivosBean) {
        this.solicitudActivosBean = solicitudActivosBean;
    }
    @ManagedProperty(value = "#{activoBean}")
    private ActivoBean activos;
    @ManagedProperty(value = "#{controlOficina}")
    private ControlOficina controlOficina;
    @ManagedProperty(value = "#{controlDepartamento}")
    private ControlDepartamento controlDepartamento;
    @ManagedProperty(value = "#{departamentoBean}")
    private DepartamentoBean departamentoBean;
    @ManagedProperty(value = "#{controActivo}")
    private ControlActivo controActivo;

    private String nuevoCodigoDepa = "";
     private boolean datosEnviados = false;
    private List<SelectItem> selectItemsOficinas;

    

    public boolean isDatosEnviados() {
        return datosEnviados;
    }

    public void setDatosEnviados(boolean datosEnviados) {
        this.datosEnviados = datosEnviados;
    }

   

    public ActivoBean getActivos() {
        return activos;
    }

    public void setActivos(ActivoBean activos) {
        this.activos = activos;
    }

    public ControlOficina getControlOficina() {
        return controlOficina;
    }

    public void setControlOficina(ControlOficina controlOficina) {
        this.controlOficina = controlOficina;
    }

    public ControlDepartamento getControlDepartamento() {
        return controlDepartamento;
    }

    public void setControlDepartamento(ControlDepartamento controlDepartamento) {
        this.controlDepartamento = controlDepartamento;
    }

    public DepartamentoBean getDepartamentoBean() {
        return departamentoBean;
    }

    public void setDepartamentoBean(DepartamentoBean departamentoBean) {
        this.departamentoBean = departamentoBean;
    }

    public ControlActivo getControActivo() {
        return controActivo;
    }

    public void setControActivo(ControlActivo controActivo) {
        this.controActivo = controActivo;
    }

    public String getNuevoCodigoDepa() {
        return nuevoCodigoDepa;
    }

    public void setNuevoCodigoDepa(String nuevoCodigoDepa) {
        this.nuevoCodigoDepa = nuevoCodigoDepa;
    }

    public List<SelectItem> getSelectItemsOficinas() {
        return selectItemsOficinas;
    }

    public void setSelectItemsOficinas(List<SelectItem> selectItemsOficinas) {
        this.selectItemsOficinas = selectItemsOficinas;
    }
    
     public void codigoDepartamentoListener(ValueChangeEvent valueChangeEvent) throws SQLException {

        FacesContext faceContext = FacesContext.getCurrentInstance();
        UIViewRoot uIViewRoot = faceContext.getViewRoot();
        this.nuevoCodigoDepa = (String) valueChangeEvent.getNewValue();
        // System.out.println(nuevoCodigoOfi);
        UIInput oficinaInputText = (UIInput) uIViewRoot.findComponent("solicitud:oficinaID:oficinaId");
        this.getControlOficina().setIdOficina(nuevoCodigoDepa);

        if (!this.getControlOficina().getIdOficina().trim().equals("0")) {
            this.getControlOficina().setIdOficina(nuevoCodigoDepa);
            this.setSelectItemsOficinas(this.getControlOficina().getSelectItemsOfi());

            oficinaInputText.setValue(this.getSelectItemsOficinas());
            oficinaInputText.setSubmittedValue(this.getSelectItemsOficinas());
            faceContext.renderResponse();

        } else {
            this.getControlOficina().setIdOficina("0");
            SelectItem itemVacio = new SelectItem("0", "Seleccione una opcion");
            List<SelectItem> itemListVacio = new ArrayList<SelectItem>();
            itemListVacio.add(itemVacio);

            oficinaInputText.setValue(itemListVacio);
            oficinaInputText.setSubmittedValue(itemListVacio);
            faceContext.renderResponse();
        }

    }
     
    
}
