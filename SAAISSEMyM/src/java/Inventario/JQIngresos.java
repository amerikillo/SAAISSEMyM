/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventario;

import conn.ConectionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Americo
 */
public class JQIngresos extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        ConectionDB con = new ConectionDB();
        try {
            if (request.getParameter("accion").equals("buscaClave")) {
                con.conectar();
                JSONObject json = new JSONObject();
                JSONArray jsona = new JSONArray();
                ResultSet rset = con.consulta("select F_Clave from tb_pedidoisem where F_Clave like '%" + request.getParameter("clave") + "%' limit 0,10");
                while (rset.next()) {
                    json.put("F_ClaPro", rset.getString(1).trim().replaceAll("\\n", ""));
                    jsona.add(json);
                    json = new JSONObject();
                }
                con.cierraConexion();
                out.println(jsona);
                System.out.println(jsona);
            }
            
            if (request.getParameter("accion").equals("buscaClaveTodas")) {
                con.conectar();
                JSONObject json = new JSONObject();
                JSONArray jsona = new JSONArray();
                ResultSet rset = con.consulta("select F_ClaPro from tb_medica where F_ClaPro like '%" + request.getParameter("clave") + "%' limit 0,10");
                while (rset.next()) {
                    json.put("F_ClaPro", rset.getString(1).trim().replaceAll("\\n", ""));
                    jsona.add(json);
                    json = new JSONObject();
                }
                con.cierraConexion();
                out.println(jsona);
                System.out.println(jsona);
            }
        } catch (Exception e) {
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
