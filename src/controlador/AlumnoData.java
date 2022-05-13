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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Alumno;
import modelo.Conexion;

/**
 *
 * @author Caro_Z
 */
public class AlumnoData {

    private Connection con = null;
    private String sql;

    public AlumnoData(Conexion conexion) {

        con = conexion.getConexion();
    }

    public void agregarAlumno(Alumno alumno) {
        try {
            sql = "INSERT INTO alumno(apellido, nombre, fechaNac, activo) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, alumno.getApellido());
            ps.setString(2, alumno.getNombre());
            ps.setDate(3, Date.valueOf(alumno.getFechaNac()));
            ps.setBoolean(4, alumno.isActivo());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                alumno.setId_alumno(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Se agregó el alumno");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo ingresar el alumno");

            }
            ps.close();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, " Error");
        }

    }

    public void eliminarAlumno(int id) {
        Alumno alumno = null;
        sql = "UPDATE alumno SET activo = 0 WHERE id_alumno = ? ";
        
        try{ PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Alumno eliminado");
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " No se pudo eliminar el alumno");
        }
    }

    public void modificarAlumno(int id) {
        Alumno alumno = null;
        sql = "UPDATE alumno SET apellido=?, nombre=?, fechaNac=?, activo=? WHERE id_alumno=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            alumno = new Alumno();
            ps.setString(1, alumno.getApellido());
            ps.setString(2, alumno.getNombre());
            ps.setDate(3, Date.valueOf(alumno.getFechaNac()));
            ps.setBoolean(4, alumno.isActivo());
            ps.setInt(4, alumno.getId_alumno());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, " Se modifico el alumno.");
            ps.close();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, " No se pudo modificar el alumno");
        }
        
    }

    public Alumno buscarAlumno(int id) {
        Alumno alumno = null;
        try {

            sql = "SELECT * FROM alumno WHERE id_alumno = ? AND activo = 1 ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id); // seteo Id para buscar en DB

            ResultSet rs = ps.executeQuery(); // trae los datos del alumno
            if (rs.next()) {
                alumno = new Alumno();
                alumno.setId_alumno(id);
                alumno.setApellido(rs.getString(2));
                alumno.setNombre(rs.getString(3));
                alumno.setFechaNac(rs.getDate(4).toLocalDate()); // pasar de Date a LocalDate
                alumno.setActivo(rs.getBoolean(5));
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " Error en la busqueda del alumno.");
        }
        return alumno;
    }

    public List <Alumno> listarAlumnos() {
// declarar Array para guardar los alumnos
        List<Alumno> alumnos = new ArrayList<>();
        try {
            sql = "SELECT * FROM alumno WHERE activo = 1 ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alumno alumno = new Alumno();

                alumno.setId_alumno(rs.getInt(1));
                alumno.setApellido(rs.getString(2));
                alumno.setNombre(rs.getString(3));
                alumno.setFechaNac(rs.getDate(4).toLocalDate());
                alumno.setActivo(rs.getBoolean(5));
                alumnos.add(alumno);

            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " Error en la busqueda. ");
        }
        return alumnos;
    }

    public void activarAlumno(int id){
         sql = "UPDATE alumno SET activo =1 WHERE id_alumno=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

           
            ps.setInt(1, id);
            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, " No se puedo activar el alumno");
        }
    }

}
