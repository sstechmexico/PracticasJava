/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sunglasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


/**
 *
 * @author 
 */
public class SunGlasses {
    
       //VARIABLES GLOBALES
       static public  Connection conectar = null;
       static Statement stmt;
       static PreparedStatement insertarNuevoRegistro = null;
       static Scanner entradaDatos = new Scanner(System.in);


    //METODO PRINCIPAL
    public static void main(String[] args) throws SQLException{
      
        int opcion;
        //PRESENTACION
        System.out.print("===================================================\n");
        System.out.print("==  S U N  G L A S S E S   N E W   A G E         ==\n");
        System.out.print("===================================================\n");
        System.out.print("      OFERTAS EXCLUSIVAS Y PROMOCIONES   \n\n");

        menu();// LLAMADA AL METODO MENU
        
        //BUCLE PARA TRABAJAR CON EL MENU DE OPCIONES
        do{
            System.out.print("INGRESA LA OPCION: ");
            opcion = entradaDatos.nextInt();

            switch(opcion){
                case 1: insertar();break;
                case 2: consultar(); break;
                case 3: System.out.println("*** S A L I E N D O   D E L   S I S T E M A ***");break;
                default: System.out.println("*** O P C I O N   I N V A L I D A ***");break;
            }

        }while(opcion != 3);
        
    }
    
    
     public static void menu(){

        System.out.print("1.- NUEVA CAPTURA\t2.- REVISAR EXISTENCIA\n");
        System.out.print("3.- SALIR\n");
        System.out.print("-------------------------------------------------\n");
    }
    
    /*****************************************************
     *   METODO PARA CREAR LA CONEXION AL SERVIDOR MYSQL *
     ****************************************************/
    
    public static void conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sunglasses?useSSL=false", "root", "Defcon18");
        } catch (ClassNotFoundException | SQLException error) {
            System.out.println("*** E R O R   D E   C O N E X I O N ***");
        }
    }
    /*****************************************************
     *   METODO PARA CONSULTAR LA BASE DE DATOS          *
     ****************************************************/
    
    public static void consultar() throws SQLException {
        
    conexion(); // LLAMADA AL METODO PARA LA CONEXION
    
    // BLOQUE PARA PREPARAR Y EJECUTAR LA CUNSULTA 
           try {
        stmt = conectar.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM sunglassesdb"); // QUERY
        //ENCABEZADO
        
        System.out.print("\n\n");
        System.out.println("LISTADO DE MERCANCIA EN BODEGA </ OPCION CONSULTA >\n");
        System.out.println("ITEM\tMARCA\t\tMODELO\t\t\t\tUPC\t\t\tCOLOR\t\tPrecio\n"
                + "-------------------------------------------------------------------------------------------------------");
    // BUCLE PARA PRESENTAR LOS DATOS  
    while (rs.next()) {

        int idProducto = rs.getInt("idProducto");
        String marca = rs.getString("marca");
        String modelo = rs.getString("modelo");
        String upc = rs.getString("upc");
        String color = rs.getString("color");
        double precio = rs.getDouble("precio");
      
        System.out.println(String.format("[%d]\t%s\t\t%s\t\t\t%s\t\t%s\t\t$%.2f", idProducto, marca, modelo,upc,color,precio));
        System.out.print("+------------------------------------------------------------------------------------------------------+\n");

    }
        
           } catch (SQLException ex) {
               System.out.println(ex);
           }
    
    }
    
    /*****************************************************
     *   METODO PARA INSERTAR DATOS A MYSQL              *
     ****************************************************/
    public static void insertar() throws SQLException{
        
        String modelo,marca,upc,color;
        int idProducto;
        double precio;
        
        // CAPTURA Y ALMACENA EN VARIABLES LA INFORMACION QUE SERA INSERTADA EN MYSQL
        entradaDatos.nextLine();
        System.out.println();
        System.out.println("INGRESA EL MODELO >>");
        modelo = entradaDatos.nextLine();
        
        System.out.print("INGRESA EL MARCA >>");
        marca = entradaDatos.nextLine();
        
        System.out.print("INGRESA EL UPC >>");
        upc = entradaDatos.nextLine();
           
        System.out.print("INGRESA EL COLOR >>");
        color = entradaDatos.nextLine();
        
        System.out.print("INGRESA EL PRECIO >>");
        precio = entradaDatos.nextDouble();

        conexion(); // LLAMAMOS A LA CONEXION PARA EJECUTAR EL QUERY
        int validacion = 0;
                 
        try{
        insertarNuevoRegistro = conectar.prepareStatement("INSERT INTO sunglassesdb(modelo,marca,upc,color,precio) VALUES(?,?,?,?,?)");
        insertarNuevoRegistro.setString(1,modelo);
        insertarNuevoRegistro.setString(2,marca);
        insertarNuevoRegistro.setString(3,upc);
        insertarNuevoRegistro.setString(4,color);
        insertarNuevoRegistro.setDouble(5, precio);
        
        validacion = insertarNuevoRegistro.executeUpdate();
        }catch(SQLException excepcion){
            excepcion.printStackTrace();
        
        }
        
    }

}

        
    
    
