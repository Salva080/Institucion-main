/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package institucion;

import controlador.*;
import java.time.LocalDate;
import modelo.*;

/**
 *
 * @author Caro_Z
 */
public class Institucion {
    private static Conexion con;
    private static AlumnoData ad;
    private static MateriaData md;
    /**
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       con = new Conexion();
        ad = new AlumnoData(con);
        Alumno a= new Alumno(1 ,"Sosa","Juan",LocalDate.of(1980, 8, 23),true);
        Alumno a2= new Alumno(2 ,"Pepe","Jose",LocalDate.of(1990, 9, 01),true);
        Alumno a3= new Alumno(11 ,"Rodrigo","Jose",LocalDate.of(1997, 12, 01),true);
//        ad.agregarAlumno(a);
//        ad.agregarAlumno(a2);
//        ad.agregarAlumno(a3);
        System.out.println(" "+ ad.buscarAlumno(1));
        System.out.println(" "+ ad.listarAlumnos());
        
        md= new MateriaData(con);
        
        Materia m= new Materia(1, "Matematicas",2,true);
        Materia m1= new Materia(2, "Algoritmos",1,true);
        
//        md.agregarMateria(m);
//        md.agregarMateria(m1);
        
        System.out.println(" "+ m);
        System.out.println(" "+ md.listarMaterias());
        
       Inscripcion in = new Inscripcion(a2, m1, 2);
       InscripcionData id = new InscripcionData(con);
       id.inscribir(in);
    }
    
}
