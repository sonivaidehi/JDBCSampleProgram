/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vaidehi Soni
 */
public class InsertStudent extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public int updatedUser = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InsertStudent</title>");
            out.println("</head>");
            out.println("<body>");
            int udage = 0;
            String udstudname = "";
            ServletContext sc = getServletContext();
            String dbUrl = sc.getInitParameter("dbUrl");
            String dbpass = sc.getInitParameter("dbPass");
            String dbUser = sc.getInitParameter("dbUser");

//            String currentUrl = request.getRequestURI();
//            String qs = request.getQueryString();
//            out.println(currentUrl.concat("?" + qs));
            if(request.getQueryString() != null){
                //if (!request.getParameter("rn").equals("")) {
                updatedUser = Integer.parseInt(request.getParameter("rn"));
                //out.println(updatedUser+"hi");

                if (updatedUser != 0) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection(dbUrl, dbUser, dbpass);
                        String query = "select * from stud where rollno="+updatedUser;
                        PreparedStatement stmt = con.prepareStatement(query);
                        //stmt.setInt(1, updatedUser);
                        //out.println(updatedUser + "hi");
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()){
                            udage = rs.getInt("age");
                            udstudname = rs.getString("studname");
                            //out.println(udage + "" + udstudname);
                            HttpSession session = request.getSession();
                            session.setAttribute("rn", rs.getInt("rollno"));
                            
                        }

                        rs.close();
                        stmt.close();
                        con.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            //}

            }
            
            out.println("<form method=\"POST\" action=\"InsertPostStudent\" >");
            out.println("<lable>Student Name: </label>");
            out.println("<input type=\"text\" name=\"txtname\" value='" + udstudname + "'><br>");
            out.println("<label>Age: </label>");
            out.println("<input type=\"number\" name=\"txtage\" value=" + udage + "><br>");
            if(request.getQueryString() != null){
                out.println("<input type=\"submit\" name=\"btnsubmit\" value=\"Update\">");
            }else{
                out.println("<input type=\"submit\" name=\"btnsubmit\" value=\"Insert\">");
            }
            out.println("<br><br>");
            out.println("<table border=1><tr><th>RollNo</th><th>Student Name</th><th>Age</th></tr>");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(dbUrl, dbUser, dbpass);
                String query = "select * from stud";
                PreparedStatement stmt = con.prepareStatement(query);

                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    int rollno = rs.getInt("rollno");
                    String studname = rs.getString("studname");
                    int studage = rs.getInt("age");
                    out.println("<tr>");
                    out.println("<td>" + rollno + "</td>");
                    out.println("<td>" + studname + "</td>");
                    out.println("<td>" + studage + "</td>");
                    out.println("<td><a href='InsertStudent?rn=" + rollno + "'>Edit</a></td>");
                    out.println("<td><a href='DeleteStudent?rn=" + rollno + "'>Delete</a></td>");
                    out.println("<tr>");

                }
                rs.close();
                stmt.close();
                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
