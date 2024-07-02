/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.dal.implement.CategoryDAO;
import dal.dal.implement.ProductDAO;
import entity.Category;
import entity.Product;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class HomeController extends HttpServlet {

    ProductDAO productDAO = null;
    CategoryDAO categoryDAO = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> listProduct = findProductDoGet(request);
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        //get ve list

        List<Category> listCategory = categoryDAO.findAll();
        //set vao session
        HttpSession session = request.getSession();
        session.setAttribute("listProduct", listProduct);
        session.setAttribute("listCategory", listCategory);
        //chuyen sang trang home
        request.getRequestDispatcher("view/homepage/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private List<Product> findProductDoGet(HttpServletRequest request) {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        //get ve keywword hay search
        String actionSearch =request.getParameter("search")==null
                    ?"default"
                    :request.getParameter("search");
        List<Product> listProduct;
        switch (actionSearch) {
            case "category":
                String categoryId=request.getParameter("categoryId");
                listProduct= productDAO.findByCategory(categoryId);
                break;
            default:
                listProduct = productDAO.findAll();
        }
     return listProduct;  
    }

}
