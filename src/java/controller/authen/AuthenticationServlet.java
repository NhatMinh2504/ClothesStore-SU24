/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.authen;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class AuthenticationServlet extends HttpServlet {
   
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //get ve action
        String action=request.getParameter("action")!=null
                        ?request.getParameter("action")
                :       "";
        
        //dua theo action set url can chuyen den
        String url;
        switch (action) {
            case "login":
                url="view/authen/login.jsp";
                break;
            default:
                url="home";
        }
        //chuyen trang
        request.getRequestDispatcher(url).forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action=request.getParameter("action")!=null
                        ?request.getParameter("action")
                :       "";
        
        //dua theo action set url can chuyen den
        String url;
        switch (action) {
            case "login":
               url= loginDoPost(request,response);
                break;
            default:
                url="home";
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String loginDoPost(HttpServletRequest request, HttpServletResponse response) {
        //get ve thong tin nguoi dung
        
        //kiem tra ton tai trong db khong
        
        //true=>home(set account vao session)
        
        //falsse=>quay login
    }

}
