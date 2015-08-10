package KehittynytOstoslista.ServLets;

import KehittynytOstoslista.Models.Kauppa;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Johanna
 */
public class KauppaServLet extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        
        String hakukaupunki = request.getParameter("hakukaupunki");
        String hakunimi = request.getParameter("hakunimi");
        String hakubonus = request.getParameter("hakubonus");
        int hakubonusInt = 0;
        if (hakubonus.length() > 0) {
            hakubonusInt = Integer.parseInt(hakubonus);
        }
        
        List<Kauppa> kaupat = null;
        
        if (hakukaupunki != null && hakukaupunki.length() > 0) {
            kaupat = Kauppa.haeKaupatKaupungilla(hakukaupunki);
        }
        if (hakunimi != null && hakunimi.length() > 0) {
            kaupat = Kauppa.haeKaupatNimella(hakunimi);
        }
        if (hakubonusInt != 0) {
            kaupat = Kauppa.haeKaupatBonuksella(hakubonusInt);
        }
        if (hakukaupunki.equals("") && hakunimi.equals("") && hakubonusInt == 0) {
            kaupat = Kauppa.haeKaikkiKaupat();
        }
        
        request.setAttribute("kaupat", kaupat);
        
        if (kaupat.isEmpty()) {
            request.setAttribute("viesti", "Kauppoja ei löytynyt");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("kauppa.jsp");
        dispatcher.forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(KauppaServLet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(KauppaServLet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
