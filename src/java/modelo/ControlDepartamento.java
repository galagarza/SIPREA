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
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author aaron
 */
@ManagedBean
@SessionScoped
public class ControlDepartamento {

    /**
     * Creates a new instance of ControlDepartamento
     */
    @ManagedProperty(value = "#{departamentoBean}")
    private DepartamentoBean departamentoBean;

    public ControlDepartamento() {
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
            Logger.getLogger(ControlDepartamento.class.getName()).log(Level.SEVERE, null, ex);
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

}
