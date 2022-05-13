/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Alumno;
import modelo.Conexion;
import modelo.Materia;

/**
 *
 * @author Caro_Z
 */
public class MateriaData {

    private Connection con = null;
    private String sql;

    public MateriaData(Conexion conexion) {

        con = conexion.getConexion();
    }

    public void agregarMateria(Materia materia) {
        try {
            sql = "INSERT INTO materia(nombre, año, activo) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnio());
            ps.setBoolean(3, true);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                materia.setId_materia(rs.getInt(1));

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo ingresar la materia.");

            }
            ps.close();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, " Error");
        }

    }

    public void eliminarMateria(int id) {

        sql = "UPDATE materia SET activo = 0 WHERE id_materia = ? ";
            
         try{ PreparedStatement ps= con.prepareStatement(sql);
             
             ps.setInt(1, id);
             ps.executeUpdate();
             JOptionPane.showMessageDialog(null, " Se elimino la materia");
             ps.close(); 

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, " No se pudo eliminar la materia");
        }
    }

    public Materia modificarMateria(int id) {
        Materia materia = null;
        sql = "UPDATE materia SET nombre=?, año=?, activo=? WHERE id_materia=?";
            
         try{ PreparedStatement ps= con.prepareStatement(sql);
             materia = new Materia();
             ps.setString(1, materia.getNombre());
             ps.setInt(2, materia.getAnio());
             ps.setBoolean(3, materia.isActivo());
             ps.setInt(4, materia.getId_materia());
             ps.executeUpdate();
             ps.close(); 

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, " No se pudo modificar la materia");
        }
       return materia;
    }

    public Materia buscarMateria(int id) {
        Materia materia = null;
        try {

            sql = "SELECT * FROM materia WHERE id_materia=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id); // seteo Id para buscar en DB

            ResultSet rs = ps.executeQuery(); // trae los datos del alumno
            if (rs.next()) {
                materia = new Materia();
                materia.setId_materia(rs.getInt(1));
                materia.setNombre(rs.getString(2));
                materia.setAnio(rs.getInt(3)); // pasar de Date a LocalDate
                materia.setActivo(rs.getBoolean(4));

            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " Error en la busqueda de la materia.");
        }
        return materia;

    }

    public List <Materia> listarMaterias() {
// declarar Array para guardar los alumnos
        List<Materia> materias = new ArrayList<>();
        try {
            sql = "SELECT * FROM materia";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Materia materia = new Materia();

                materia.setId_materia(rs.getInt(1));
                materia.setNombre(rs.getString(2));
                materia.setAnio(rs.getInt(3));
                materia.setActivo(rs.getBoolean(4));
                materias.add(materia);

            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " Error en la busqueda. ");
        }
        return materias;
    }
}
