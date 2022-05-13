/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
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
import modelo.Inscripcion;
import modelo.Materia;

/**
 *
 * @author Caro_Z
 */
public class InscripcionData {
    
    private Connection con = null;
    private String sql;
    

    public InscripcionData(Conexion conexion) {

        con = conexion.getConexion();
    }
    
    public List listarMaterias(int id){
        List <Materia> materias=new ArrayList<>();
        Materia materia = null;
        String sql =("");
      return materias ;  
    }
    public List listarAlumnos(Materia materia){
        List <Alumno> alumnos=new ArrayList<>();
        
        return alumnos;
    }
    

    
    public void inscribir(Inscripcion insc){
        
        
     try {   
        String sql = "INSERT INTO inscripcion ( id_alumno , id_materia , nota ) VALUES ( ? ,? ,?)";
        
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        ps.setInt(2, insc.getMateria().getId_materia());
        ps.setInt(1, insc.getAlumno().getId_alumno());
        ps.setDouble(3, insc.getNota());
        
        ps.executeUpdate();
        
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()){
            insc.setId_inscripcion(rs.getInt(1));
            JOptionPane.showMessageDialog(null, "Alumno Inscripto con éxito!");
        }
        
     } catch (SQLException ex){
         JOptionPane.showMessageDialog(null, "Error en la inscripcion ");
     }
    }
    
     public void borrarInscripcion(Alumno alumno, Materia materia){
        
    }
    
    
    public void registrarNota(double nota,Alumno alumno, Materia materia){
        
    }
    
    public List<Alumno> listarAlumnoNoInscr(int materia){
        List<Alumno> alumnos = new ArrayList<>(); 
        return alumnos;
    }
    
}
