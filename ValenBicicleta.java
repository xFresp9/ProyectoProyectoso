package valenbicicleta;

import java.sql.*;
import java.util.Scanner;

public class ValenBicicleta {

    private static final String AWSDNS = "datobasekcd.cvxhzdwznsr1.us-east-1.rds.amazonaws.com";
    private static final String DBNAME = "ValenbiciJDG";
    private static final int PUERTO = 3306;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "pepelodice";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Connection con = DriverManager.getConnection("jdbc:mysql://"
                + AWSDNS + ":" + PUERTO + "/" + DBNAME, USERNAME, PASSWORD);){
            
            
            System.out.println("Elige ver la disponibilidad de las estaciones:");
            System.out.println("Escribe T para ver las estaciones que están disponibles.");
            System.out.println("Escribe F para ver la estaciones que no están disponibles");
            String opcion = scanner.next();
            
            switch (opcion) {
                case "T" -> {
                    PreparedStatement pStmt1 = con.prepareStatement("SELECT * from historico WHERE estado_operativo = 'T';");
                    ResultSet rs1 = pStmt1.executeQuery();
                    while (rs1.next()) {
                        System.out.println(rs1.getInt(1)+" "+rs1.getInt(2)+" "+rs1.getString(3)+" "+rs1.getInt(4)+" "+rs1.getInt(5)+" "+rs1.getString(6)+" "+rs1.getTimestamp(7)+" "+rs1.getString(8));
                    }
                    con.close();
                }
                case "F" -> {
                    PreparedStatement pStmt2 = con.prepareStatement("SELECT * from historico WHERE estado_operativo = 'F';");
                    ResultSet rs2 = pStmt2.executeQuery();
                    while (rs2.next()) {
                        System.out.println(rs2.getInt(1)+" "+rs2.getInt(2)+" "+rs2.getString(3)+" "+rs2.getInt(4)+" "+rs2.getInt(5)+" "+rs2.getString(6)+" "+rs2.getTimestamp(7)+" "+rs2.getString(8));
                    }
                    con.close();
                }
                default -> System.out.println("Esa opción no existe");
            }
            
            
        } catch (SQLException se) {
            System.out.println(se);
        }
        
    }
}