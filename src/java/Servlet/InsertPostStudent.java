/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vaidehi Soni
 */
public class InsertPostStudent extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InsertPostStudent</title>");            
            out.println("</head>");
            out.println("<body>");
            ServletContext sc = getServletContext();
            
            String dbUrl = sc.getInitParameter("dbUrl");
            String dbpass = sc.getInitParameter("dbPass");
            String dbUser = sc.getInitParameter("dbUser");
           
            HttpSession session = request.getSession();
            //if(session.getAttribute("rn") != ""){
                //urn = (String)session.getAttribute("rn");
            //}
            Object urn = session.getAttribute("rn");
            //out.print(urn);
            //fetch values
            String sname = request.getParameter("txtname");
            int sage = Integer.parseInt(request.getParameter("txtage"));
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(dbUrl, dbUser, dbpass);
                String query = "";
                if(session.getAttribute("rn")==null || session.getAttribute("rn").equals("")){
                     query = "insert into stud values(0,'" + sname + "',"+sage +")";
                     
                }else{
                        query = "update stud set studname='"+sname +"',age="+ sage +" where rollno="+urn;
                        session.removeAttribute("rn");
                        
                }
                //query = "insert into stud values(0," + sname + ","+sage +")";
                //out.print(query);
                
                PreparedStatement stmt = con.prepareStatement(query);
                
                
                int affectedRow = stmt.executeUpdate();
                if(affectedRow > 0){
                    //out.println("1 row affected...");
                    response.sendRedirect("InsertStudent");
//                    RequestDispatcher rd = request.getRequestDispatcher("InsertStudent");
//                    rd.forward(request, response);
                }
                stmt.close();
                con.close();
                
            }catch(Exception ex){
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
