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
    private Conexion conexion;

    public InscripcionData(Conexion conexion) {

        con = conexion.getConexion();
    }
    
      
    public Alumno buscarAlumno(int id){
    
        AlumnoData ad=new AlumnoData(conexion);
        
        return ad.buscarAlumno(id);
        
    }
    
    public Materia buscarMateria(int id){
    
        MateriaData md=new MateriaData(conexion);
        return md.buscarMateria(id);
    
    }


    public List <Materia> ObtenerMateriasInscriptas(int id_alumno){
        List <Materia> materias=new ArrayList<>();
        try {
            String sql = "SELECT id_materia, nombre FROM inscripcion, materia WHERE inscripcion.id_materia = materia.id_materia AND inscripcion.id_alumno = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_alumno);
            ResultSet rs = ps.executeQuery();
            Materia materia;
            while(rs.next()){
                materia = new Materia();
                materia.setId_materia(rs.getInt("id_materia"));
                materia.setNombre(rs.getString("nombre"));
                materias.add(materia);
            }
            JOptionPane.showMessageDialog(null, "Se generó el listado");
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los alumnos: " + ex.getMessage());
        }
        return materias;
    }
    

    public List<Materia> obtenerMateriasNOInscriptas(int id_alumno){
        List<Materia> materias = new ArrayList<Materia>();


        try {
            String sql = "SELECT * FROM materia WHERE id_materia NOT IN "
                    + "(SELECT id_materia FROM inscripcion WHERE id_alumno = ? );";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_alumno);
            ResultSet resultSet = ps.executeQuery();
            Materia materia;
            while(resultSet.next()){
                materia = new Materia();
                materia.setId_materia(resultSet.getInt("id_materia"));
                materia.setNombre(resultSet.getString("nombre"));
                materias.add(materia);
            }
            JOptionPane.showMessageDialog(null, "Se generó el listado");
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los alumnos: " + ex.getMessage());
        }
        return materias;
    }

    public List<Materia>listarMateriasPorAlumno(int id_alumno) {

            List<Materia> materias = new ArrayList<>();
            try {
            sql="SELECT materia.id_materia,nombre FROM inscripcion,materia WHERE inscripcion.id_materia = materia.id_materia AND inscripcion.id_alumno = ? ";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,id_alumno);

            ResultSet rs = ps.executeQuery();
           while (rs.next()){

                Materia  mat = new Materia();
               mat.setId_materia(rs.getInt("id_materia"));
               mat.setNombre(rs.getString("nombre"));
//               mat.setAnio(rs.getInt("anio"));
//               mat.setActivo(rs.getBoolean("activo"));
               materias.add(mat);

            }
            JOptionPane.showMessageDialog(null, "Se generó el listado");
            ps.close();

        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "No se pudo generar la consulta.");
        } return materias;
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

    public List<Inscripcion> obtenerInscripcionesPorAlumno(int id){
        List<Inscripcion> inscripciones= new ArrayList<Inscripcion>();            
        try {
            String sql = "SELECT * FROM inscripcion WHERE id_alumno = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            Inscripcion ins;
            while(rs.next()){
                ins = new Inscripcion();
                ins.setId_inscripcion(rs.getInt("id"));
                
                Alumno a = buscarAlumno(rs.getInt("id_alumno"));
                ins.setAlumno(a);
                
                Materia m = buscarMateria(rs.getInt("id_materia"));
                ins.setMateria(m);
                ins.setNota(rs.getInt("nota"));
                inscripciones.add(ins);
            }      
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los alumnos: " + ex.getMessage());
        }                
        return inscripciones;
    }
    
    public List<Alumno> listarAlumnoNoInscr(int materia){
        List<Alumno> alumnos = new ArrayList<>();
        return alumnos;
    }
    
   public void registrarNota(double nota, int id_alumno, int id_materia) {

       try {
            
            String sql = "UPDATE inscripcion SET nota = ? WHERE id_alumno =? and id_materia =?;";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1,nota);
            ps.setInt(2, id_alumno);
            ps.setInt(3, id_materia);
           
            
            ps.executeUpdate();
            
            
            ps.close();
    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar un alumno: " + ex.getMessage());
        }                                  
    }
 
   
    public void borrarInscripcion(int id_alumno,int id_materia){
    
        try {
            
            String sql = "DELETE FROM inscripcion WHERE id_alumno =? and id_materia =?;";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id_alumno);
            ps.setInt(2, id_materia);
           
            
            ps.executeUpdate();
            
            
            ps.close();
    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar un alumno: " + ex.getMessage());
        }            
    }

}
