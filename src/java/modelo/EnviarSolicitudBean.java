/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.sun.xml.wss.util.DateUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author aaron
 */
@ManagedBean
@SessionScoped
public class EnviarSolicitudBean {

    /**
     * Creates a new instance of EnviarSolicitudBean
     */
    private String usuario, cedula, idOficina, idDepartamento, nombreOficina, nombreDepartamento, fechaP, fechaD;
    private Date fechaPrestamo, fechaDevolucion;
    private ArrayList<ActivoBean> listaActivos;
    SolicitudActivosBean soliActivo;
    

    public EnviarSolicitudBean() {
    }

   public String cancelarSolicitud(){
       
       return "inicio";
   }

    public String mostrarSolicitud() {
        try {

          

            UsuarioBean usuarioB = (UsuarioBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioBean");
            ControlActivo controlActivo = (ControlActivo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("controlActivo");
            ControlSolicitud controlSolicitud = (ControlSolicitud) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("controlSolicitud");
            soliActivo = (SolicitudActivosBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("solicitudActivosBean");
            DateBean dateB = (DateBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateBean");
            this.usuario = usuarioB.getNombre();
            this.cedula = usuarioB.getCedula();
            this.idDepartamento = controlSolicitud.getNuevoCodigoDepa();
            this.idOficina = controlActivo.getCodigoOficina();

            String sql = "SELECT DISTINCT oficina.nombre_oficina,oficina.id_oficina,oficina.custodio,oficina.departamento FROM departamento,oficina WHERE '" + idOficina + "'=oficina.id_oficina";
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            ResultSet resultado = cone.consulta(sql);
            while (resultado.next()) {
                OficinaBean ofi = new OficinaBean(resultado.getString("id_oficina").trim(), resultado.getString("nombre_oficina").trim(), resultado.getString("custodio").trim(), resultado.getString("departamento").trim());
                this.nombreOficina = ofi.getNombreOficina();
            }
            sql = "SELECT DISTINCT codigo, nombre, encargado FROM departamento WHERE codigo='" + this.idDepartamento + "';";
            resultado = cone.consulta(sql);
            while (resultado.next()) {
                DepartamentoBean depa = new DepartamentoBean(resultado.getString("codigo").trim(), resultado.getString("nombre").trim(), resultado.getString("encargado").trim());
                this.nombreDepartamento = depa.getNombre();
            }
            this.listaActivos = controlActivo.getListaActivos();
            this.fechaPrestamo = dateB.getFechaPrestamo();
            if (this.fechaPrestamo == null) {
                this.fechaP = "No carga la fecha";
            }

        } catch (SQLException ex) {
            Logger.getLogger(EnviarSolicitudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "enviarSolicitud";
    }

    public String getFechaP() {

        return "27/02/2015";
    }

    public void setFechaP(String fechaP) {
        this.fechaP = fechaP;
    }

    public ArrayList<ActivoBean> getListaActivos() {
        return listaActivos;
    }

    public void setListaActivos(ArrayList<ActivoBean> listaActivos) {
        this.listaActivos = listaActivos;
    }

    public String getNombreOficina() {
        return nombreOficina;
    }

    public void setNombreOficina(String nombreOficina) {
        this.nombreOficina = nombreOficina;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(String idOficina) {
        this.idOficina = idOficina;
    }

    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
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

    public SolicitudActivosBean getSoliActivo() {
        return soliActivo;
    }

    public void setSoliActivo(SolicitudActivosBean soliActivo) {
        this.soliActivo = soliActivo;
    }

    public String getFechaD() {
        return "05/03/2015";
    }

    public void setFechaD(String fechaD) {
        this.fechaD = fechaD;
    }

    public String guardarSolicitud() {

        try {
            this.fechaPrestamo = new Date(2015, 03, 25);
            this.fechaDevolucion = new Date(2015, 03, 28);
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sqlCodigo = "SELECT MAX (id_solicitud) FROM solicitud;";
            ResultSet resltadoCodigo = cone.consulta(sqlCodigo);
            int codigo = 001;
            while (resltadoCodigo.next()) {
                codigo = (resltadoCodigo.getInt(1));
            }
            codigo++;
            String sql = "SELECT insertar_solicitud('" + codigo + "','" + this.cedula + "','" + this.fechaPrestamo + "');";
            cone.procedimientos(sql);
            String sqlEstado = "SELECT insertar_solicitud_estado_solicitud('" + codigo + "','01','Pendiente')";
            cone.procedimientos(sqlEstado);
            String sqlSolActivos = "";
            for (int i = 0; i < listaActivos.size(); i++) {
                sqlSolActivos = "SELECT insertar_activos_solicitado('" + codigo + "','" + listaActivos.get(i).getPlaca() + "','" + this.fechaPrestamo + "','" + this.fechaDevolucion + "');";
                cone.procedimientos(sqlSolActivos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EnviarSolicitudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "administrador";

    }

}
