/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author aaron
 */
@ManagedBean
@RequestScoped
public class ModuloDepartamento {

    /**
     * Creates a new instance of ControlDepartamento
     */
    @ManagedProperty(value = "#{departamentoBean}")
    private DepartamentoBean departamentoBean;

    public ModuloDepartamento() {
    }

    public DepartamentoBean getDepartamentoBean() {
        return departamentoBean;
    }

    public void setDepartamentoBean(DepartamentoBean departamentoBean) {
        this.departamentoBean = departamentoBean;
    }

    public List<DepartamentoBean> getDepartamentos() {
        List<DepartamentoBean> listaDepartamento = new ArrayList<DepartamentoBean>();
        try {

            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql = "SELECT * FROM departamento";
            ResultSet resultado = cone.consulta(sql);

            while (resultado.next()) {
                DepartamentoBean departamento = new DepartamentoBean(resultado.getString("codigo").trim(), resultado.getString("nombre").trim(), resultado.getString("encargado").trim());
                listaDepartamento.add(departamento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModuloDepartamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDepartamento;
    }

    public List<SelectItem> getSelectItemsDepartamento() {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        List<DepartamentoBean> listaDepartamento = this.getDepartamentos();

        for (DepartamentoBean depa : listaDepartamento) {
            SelectItem selectItem = new SelectItem(depa.getCodigo(), depa.getNombre());
            selectItems.add(selectItem);

        }

        return selectItems;
    }

    public String insertarDepartamento() {
        try {
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql = "SELECT insertar_departamento('" + this.departamentoBean.getCodigo().trim() + "','" + this.departamentoBean.getNombre() + "','" + this.departamentoBean.getEncargado() + "');";
//             String sql = "SELECT insertar_funcionario('111','juan','1','aqui','peon');";
            System.err.println(sql);
//            cone.getStatement(sql);
            cone.procedimientos(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ModuloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }//Fin del método insertar departamento
    
     public void modificarDepartamento() {
        try {
            
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql = "SELECT modificar_departamento('" + this.departamentoBean.getCodigo() + "','" + this.departamentoBean.getNombre() + "','" + this.departamentoBean.getEncargado() + "');";

            System.err.println(sql);
            cone.procedimientos(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ModuloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//Fin del método modificar departamento
    
     public void eliminarDepartamento() {
        try {            
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String sql = "SELECT eliminar_departamento('" + this.departamentoBean.getCodigo() + "');";

            System.err.println(sql);
            cone.procedimientos(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ModuloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//Fin del método eliminar departamento
     
     public void consultarDepartamento() {
        try {
            String codigo = "",nombre = "", encargado = "";
            codigo = this.departamentoBean.getCodigo();          
            ConexionBD cone = new ConexionBD();
            cone.conexion();
            String consultaCedula = "SELECT * FROM public.departamento WHERE rtrim(departamento.codigo)='" + this.departamentoBean.getCodigo().trim() + "';";
            ResultSet result = cone.consulta(consultaCedula);
            while (result.next()) {
                codigo = result.getString("codigo");
                nombre = result.getString("nombre");
                encargado = result.getString("encargado");               
            }
            ModuloDepartamento depa = (ModuloDepartamento) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("controlDepartamento");
//          user.setUsuarioBean(new FuncionarioBean(cedula, nombre, departamento, direccion, puesto) );
            depa.setDepartamentoBean(new DepartamentoBean(codigo, nombre, encargado));
        } catch (SQLException ex) {
            Logger.getLogger(ModuloFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}//Fin de la clase
