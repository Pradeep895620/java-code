import java.sql.*;
import java.util.Scanner;

public class JDBCMenuProgram {
    // Database credentials
    static final String URL = "jdbc:mysql://localhost:3306/testdb";
    static final String USER = "root";
    static final String PASS = "password"; // Apna password yahan likhein

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            while (true) {
                System.out.println("\n--- STUDENT DATABASE MENU ---");
                System.out.println("1. Insert Student");
                System.out.println("2. Display All Students");
                System.out.println("3. Update Student Name");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                
                int choice = sc.nextInt();
                
                switch (choice) {
                    case 1: // INSERT
                        System.out.print("Enter ID: "); int id = sc.nextInt();
                        System.out.print("Enter Name: "); String name = sc.next();
                        System.out.print("Enter Age: "); int age = sc.nextInt();
                        
                        String sqlInsert = "INSERT INTO students VALUES (?, ?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
                        pstmt.setInt(1, id);
                        pstmt.setString(2, name);
                        pstmt.setInt(3, age);
                        pstmt.executeUpdate();
                        System.out.println("Data Inserted Successfully!");
                        break;

                    case 2: // SELECT (Read)
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM students");
                        System.out.println("\nID\tNAME\tAGE");
                        while (rs.next()) {
                            System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3));
                        }
                        break;

                    case 3: // UPDATE
                        System.out.print("Enter Student ID to update name: ");
                        int uid = sc.nextInt();
                        System.out.print("Enter New Name: ");
                        String newName = sc.next();
                        
                        String sqlUpdate = "UPDATE students SET name = ? WHERE id = ?";
                        PreparedStatement upstmt = conn.prepareStatement(sqlUpdate);
                        upstmt.setString(1, newName);
                        upstmt.setInt(2, uid);
                        int rowsUpdated = upstmt.executeUpdate();
                        if (rowsUpdated > 0) System.out.println("Update Successful!");
                        else System.out.println("Student not found.");
                        break;

                    case 4: // DELETE
                        System.out.print("Enter ID to delete: ");
                        int did = sc.nextInt();
                        String sqlDelete = "DELETE FROM students WHERE id = ?";
                        PreparedStatement dstmt = conn.prepareStatement(sqlDelete);
                        dstmt.setInt(1, did);
                        dstmt.executeUpdate();
                        System.out.println("Record Deleted!");
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}