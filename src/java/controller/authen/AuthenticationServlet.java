/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.authen;

import constant.CommonConst;
import dal.dal.implement.AccountDAO;
import entity.Account;
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
public class AuthenticationServlet extends HttpServlet {
   AccountDAO accountDAO=new AccountDAO();
    
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
            case "log-out":
                url=logOut(request,response);
                break;
            case "sign-up"    :
                url="view/authen/register.jsp";
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
            case "sign-up":
                url=signUp(request,response);
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
        String url=null;
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        
        //kiem tra ton tai trong db khong
        Account account=Account.builder()
                        .username(username)
                        .password(password)
                        .build();
        Account accFoundByUsernamePass =accountDAO.findByUsernameAndPass(account);
        //true=>home(set account vao session)
        if(accFoundByUsernamePass!=null){
           request.getSession().setAttribute(CommonConst.SESSION_Account, accFoundByUsernamePass);
           url="home";
        }else{
            request.setAttribute("error", "User name or password incorect");
            url="view/authen/login.jsp";
        }
        //falsse=>quay login
        return url;
    }

    private String logOut(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(CommonConst.SESSION_Account);
        return "home";
    }

    private String signUp(HttpServletRequest request, HttpServletResponse response) {
        String url;
        //get ve thong tin
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        
        //kiem tra da bi trung 
        Account account=Account.builder()
                        .username(username)
                .password(password)
                        .build();
        boolean isExistUsername=accountDAO.checkUsernameExist(account);
        
        //true
        if(isExistUsername){
            request.setAttribute("error", "Username exist");
            url="view/authen/register.jsp";
        }else{
            accountDAO.insert(account);
            url="home";
        }
        return url;
    }

}
