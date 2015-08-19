package KehittynytOstoslista.ServLets;

import KehittynytOstoslista.Models.Bonus;
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
public class KaupanMuokkausNakymaServLet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        Kauppa kauppa = Kauppa.haeKauppa(Integer.parseInt(request.getParameter("id")));
        
        List<Bonus> bonukset = Bonus.haeKaikkiBonukset();
        
        request.setAttribute("muokkauskauppa", kauppa);
        request.setAttribute("bonukset", bonukset);
        
        String hakukaupunki = request.getParameter("hakukaupunki");
        String hakunimi = request.getParameter("hakunimi");
        int hakubonus = Integer.parseInt(request.getParameter("hakubonus"));
        
        List<Kauppa> kaupat = null;
        
        if (hakukaupunki != null && hakukaupunki.length() > 0) {
            kaupat = Kauppa.haeKaupatKaupungilla(hakukaupunki);
        }
        if (hakunimi != null && hakunimi.length() > 0) {
            kaupat = Kauppa.haeKaupatNimella(hakunimi);
        }
        if (hakubonus != 1) {
            kaupat = Kauppa.haeKaupatBonuksella(hakubonus);
        }
        if (hakukaupunki.equals("") && hakunimi.equals("") && hakubonus == 1) {
            kaupat = Kauppa.haeKaikkiKaupat(1);
        }
        
        request.setAttribute("kaupat", kaupat);
        
        if (kaupat.isEmpty()) {
            request.setAttribute("viesti", "Kauppoja ei löytynyt");
        }
        
        request.setAttribute("hakukaupunki", hakukaupunki);
        request.setAttribute("hakunimi", hakunimi);
        request.setAttribute("hakubonus", hakubonus);
        
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
            Logger.getLogger(KaupanMuokkausNakymaServLet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(KaupanMuokkausNakymaServLet.class.getName()).log(Level.SEVERE, null, ex);
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
