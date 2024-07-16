/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user;

import entity.Order;
import entity.OrderDetails;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class DashboardUserServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      request.getRequestDispatcher("view/user/dashboard.jsp");
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
        String action =request.getParameter("action") ==null
                    ?""
                :request.getParameter("action");
        switch (action) {
            case "add-product":
                addProduct(request,response);
                break;
            default:
                throw new AssertionError();
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

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get ve session
        HttpSession session=request.getSession();
        //get ve product id
        int id=Integer.parseInt(request.getParameter("id"));
        //get ve quantity
        int quantity=Integer.parseInt(request.getParameter("quantity"));
        //lay cart o session vao trong cart
        Order cart=(Order) session.getAttribute("cart");
        if(cart==null){
            cart=new Order();
        }
        OrderDetails od=new OrderDetails();
        od.setProductId(id);
        od.setQuantity(quantity);
        //set cart moi len session
        addOrderDetailstoOrder(od,cart);
        session.setAttribute("cart", cart);
        response.sendRedirect("payment");
    }

    private void addOrderDetailstoOrder(OrderDetails od, Order cart) {
        boolean isAdd=false;
        for(OrderDetails obj: cart.getListOrderDetails()){
            if(obj.getProductId()==od.getProductId()){
                obj.setQuantity(obj.getQuantity()+od.getQuantity());
                isAdd=true;
            }
        }
        if(isAdd==false){
            cart.getListOrderDetails().add(od);
        }
    }

}
