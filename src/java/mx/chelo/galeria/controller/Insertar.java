package mx.chelo.galeria.controller;

import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;

/**
 *
 * @author chelo
 */
@MultipartConfig(maxFileSize = 16177215)
public class Insertar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    private static final int BUFFER_SIZE = 4096;
    // database connection settings
    private String dbURL = "jdbc:mysql://localhost:3306/itz";
    private String dbUser = "root";
    private String dbPass = "";

    //naive way to obtain a connection to database
    //this MUST be improved, shown for 
    private Connection getConnection() {
        Connection conn = null;
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
        } catch (Exception e) {
            throw new RuntimeException("Failed to obtain database connection.", e);
        }
        return conn;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        //get values of text fields
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        InputStream inputStream = null;
        
        Part filePart = request.getPart("img");
        if (filePart != null) {
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());

            inputStream = filePart.getInputStream();
        }

        Connection conn = null; 
        String message = null; 
        try {
            conn = getConnection();
            String sql = "INSERT INTO galeria (id, nombre, descripcion, img) values (?, ?, ?, ?)";
            PreparedStatement pstmtSave = conn.prepareStatement(sql);
            pstmtSave.setInt(1, id);
            pstmtSave.setString(2, nombre);
            pstmtSave.setString(3, descripcion);
            
            if (inputStream != null) {
                pstmtSave.setBlob(4, inputStream);
            }
            int row = pstmtSave.executeUpdate();
            if (row > 0) {
                message = "File uploaded and saved into database";
            }
            Random i = new Random();
            String filepath = "D:\\Documentos\\NetBeansProjects\\Galeria\\web\\imagenes\\foto.jpg";
            
            String sql1 = "SELECT img FROM galeria WHERE id=?";
            PreparedStatement pstmtSelect = conn.prepareStatement(sql1);
            pstmtSelect.setInt(1, id);
            ResultSet result = pstmtSelect.executeQuery();
            if (result.next()) {
                Blob blob = result.getBlob("img");
                InputStream inputStream1 = blob.getBinaryStream();
                OutputStream outputStream = new FileOutputStream(filepath);
                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream1.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream1.close();
                outputStream.close();
                System.out.println("File saved");
            }
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
            request.setAttribute("message", message);
            getServletContext().getRequestDispatcher("/galeria.jsp")
                    .include(request, response);
        }
    }

}




