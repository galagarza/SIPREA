/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

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
public class ControlPrestamo {

    /**
     * Creates a new instance of ControlPrestamo
     */
    @ManagedProperty(value = "#{prestamoBean}")
    private PrestamoBean prestamoBean;
    private ArrayList<SolicitudPendiente> listaSolicitudesPendientes;
    private ArrayList<ActivoBean> listaActivos;
    private ArrayList<ActivoBean> noPrestar;
    private ConexionBD cone = new ConexionBD();
    private int id_solicitud;
    private String placa;
    private int idPrestamo=0;

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getPlaca() {
        return placa;
    }

    public ArrayList<ActivoBean> getNoPrestar() {
        return noPrestar;
    }

    public void setNoPrestar(ArrayList<ActivoBean> noPrestar) {
        this.noPrestar = noPrestar;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public ControlPrestamo() {

        try {
            listaSolicitudesPendientes = new ArrayList<SolicitudPendiente>();
            listaActivos = new ArrayList<ActivoBean>();
            noPrestar = new ArrayList<ActivoBean>();
            cone.conexion();
            this.cargarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(ControlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public PrestamoBean getPrestamoBean() {
        return prestamoBean;
    }

    public ArrayList<SolicitudPendiente> getListaSolicitudesPendientes() {
        return listaSolicitudesPendientes;
    }

    public void setListaSolicitudesPendientes(ArrayList<SolicitudPendiente> listaSolicitudesPendientes) {
        this.listaSolicitudesPendientes = listaSolicitudesPendientes;
    }

    public void setPrestamoBean(PrestamoBean prestamoBean) {
        this.prestamoBean = prestamoBean;
    }

    public ArrayList<ActivoBean> getListaActivos() {
        return listaActivos;
    }

    public void setListaActivos(ArrayList<ActivoBean> listaActivos) {
        this.listaActivos = listaActivos;
    }

    public void cargarTabla() {
        try {
            String sql = "SELECT DISTINCT solicitud.id_solicitud,fecha_solicitud,cedula,nombre,valor FROM solicitud,solicitud_estado_solicitud,funcionario WHERE  solicitud_estado_solicitud.id_solicitud=solicitud.id_solicitud AND solicitud_estado_solicitud.valor='Pendiente' AND funcionario.cedula=solicitud.id_solicitante;";
            ResultSet resultado = cone.consulta(sql);
            ResultSet resultadoActivo;
            int index = 0;
            while (resultado.next()) {
                listaSolicitudesPendientes.add(new SolicitudPendiente(resultado.getString("cedula"), resultado.getString("nombre"), resultado.getString("valor"), resultado.getInt("id_solicitud"), resultado.getDate("fecha_solicitud")));
//                System.err.println(resultado.getString("cedula"));
                String sqlActivos = "SELECT DISTINCT\n"
                        + " activo.placa,\n"
                        + " activo.nombre,\n"
                        + " activo.marca,\n"
                        + " activo.modelo\n"
                        + "FROM\n"
                        + " public.solicitud,\n"
                        + " public.solicitud_estado_solicitud,\n"
                        + " public.activos_solicitados,\n"
                        + " public.activo,\n"
                        + " public.funcionario\n"
                        + "WHERE\n"
                        + " '" + listaSolicitudesPendientes.get(index).getId_solicitud() + "' = solicitud_estado_solicitud.id_solicitud AND\n"
                        + " solicitud.id_solicitante = funcionario.cedula AND\n"
                        + " solicitud_estado_solicitud.id_solicitud = activos_solicitados.id_solicitud AND\n"
                        + " activo.placa = activos_solicitados.placa AND\n"
                        + " solicitud_estado_solicitud.valor = 'Pendiente';";
                resultadoActivo = cone.consulta(sqlActivos);
                int indx = 0;
                // ActivoBean[] activosSolicitados=new ActivoBean[listaSolicitudesPendientes.size()];
                while (resultadoActivo.next()) {

                    ActivoBean activo = new ActivoBean(resultadoActivo.getString("nombre"), resultadoActivo.getString("placa"), resultadoActivo.getString("modelo"), resultadoActivo.getString("marca"));
                    listaActivos.add(activo);

                }

                index++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ControlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String finalizarSolicitud() {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.id_solicitud = Integer.parseInt(params.get("finalizarPrestamo"));

        this.eliminarActivosSolicitud();
        this.cambiarEstadoSolicitud();
        
        this.crearPrestamo(id_solicitud);
        this.addActivosPrestados(id_solicitud); 

        for (int i = 0; i < listaSolicitudesPendientes.size(); i++) {
            if (listaSolicitudesPendientes.get(i).getId_solicitud() == this.id_solicitud) {

                this.listaSolicitudesPendientes.remove(i);
            }
        }

        System.out.println("Finalizar solicitud: " + this.id_solicitud);
        FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
        return "idPrestamo";
    }

    public String prestarActivo() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.placa = params.get("prestarActivo");

        return "";

    }

    public String noPrestarActivo() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.placa = params.get("noPrestarActivo");
        for (int i = 0; i < listaActivos.size(); i++) {
            if (listaActivos.get(i).getPlaca().equalsIgnoreCase(this.placa)) {
                this.noPrestar.add(this.listaActivos.get(i));
                this.listaActivos.remove(i);
            }

        }
        System.out.println("No prestar activo: " + this.placa);

        return "";
    }

    public void crearPrestamo(int id_solicitud) {

        try {
            String sqlPrestamo = "";

            String idCustodio = "";
            ResultSet resultadoPrestamo = null;
            sqlPrestamo = "SELECT MAX (id_prestamo) FROM prestamo;";

            resultadoPrestamo = cone.consulta(sqlPrestamo);

            while (resultadoPrestamo.next()) {
                this.idPrestamo = resultadoPrestamo.getInt(1);
            }
            this.idPrestamo++;
            System.out.println("id_prestamo: "+idPrestamo);

            sqlPrestamo = "SELECT DISTINCT cedula_custodio FROM activos_solicitados,activo_funcionario WHERE activos_solicitados.id_solicitud='" + id_solicitud + "' AND activo_funcionario.placa_activo=activos_solicitados.placa;";
            resultadoPrestamo = cone.consulta(sqlPrestamo);
            while (resultadoPrestamo.next()) {
                idCustodio = resultadoPrestamo.getString("cedula_custodio");
            }

            sqlPrestamo = "SELECT insertar_prestamo('" + idPrestamo + "','" + idCustodio + "','1','" + id_solicitud + "');";
            cone.procedimientos(sqlPrestamo);

        } catch (SQLException ex) {
            Logger.getLogger(ControlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void eliminarActivosSolicitud() {
        if (noPrestar.size() != 0) {
            for (int i = 0; i < noPrestar.size(); i++) {
                String sqlRm = "DELETE FROM activos_solicitados WHERE activos_solicitados.id_solicitud='" + this.id_solicitud + "' AND activos_solicitados.placa='" + this.noPrestar.get(i).getPlaca() + "';";
                try {
                    cone.procedimientos(sqlRm);
                } catch (SQLException ex) {
                    Logger.getLogger(ControlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//FIN DEL METODO 

    public void cambiarEstadoSolicitud() {
        try {
            String sqlEstado = "SELECT modificar_solicitud_estado_solicitud('" + this.id_solicitud + "','Aprobado');";

            cone.procedimientos(sqlEstado);
        } catch (SQLException ex) {
            Logger.getLogger(ControlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addActivosPrestados(int idSolicitud) {
        String sqlLA = "";
        ResultSet resultado;
        Date fechaPrestamo = null, fechaDevolucion = null;
        try {

            sqlLA = "SELECT \"fecha-hora-inicio\",\"fecha-hora-fin\" FROM activos_solicitados WHERE activos_solicitados.id_solicitud='"+idSolicitud+"';";
            resultado = cone.consulta(sqlLA);
            while (resultado.next()) {
                fechaPrestamo = resultado.getDate(1);
                fechaDevolucion = resultado.getDate(2);
            }
            System.out.println(fechaPrestamo+"---"+fechaDevolucion);

            for (int i = 0; i < listaActivos.size(); i++) {
                sqlLA = "SELECT insertar_activos_prestados('" + this.getIdPrestamo() + "','" + listaActivos.get(i).getPlaca() + "','" + fechaPrestamo + "','" + fechaDevolucion + "'); ";

                try {
                    cone.procedimientos(sqlLA);
                } catch (SQLException ex) {
                    Logger.getLogger(ControlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public static void main(String args[]){
//       ControlPrestamo c= new ControlPrestamo();
//       c.addActivosPrestados(1);
//    }
}
