package KehittynytOstoslista.ServLets;

import KehittynytOstoslista.Models.Kayttaja;
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
public class KayttajaServLet extends HttpServlet {

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
        
        String hakusana = request.getParameter("haku");
        List<Kayttaja> kayttajat = null;
        
        if (hakusana != null && hakusana.length() > 0) {
            kayttajat = Kayttaja.haeKayttajat(hakusana);
        } else {
            kayttajat = Kayttaja.haeKaikkiKayttajat();
        }
    
        request.setAttribute("kayttajat", kayttajat);
        
        if (kayttajat.isEmpty()) {
            request.setAttribute("viesti", "Käyttäjiä ei löytynyt");
        }
        
        request.setAttribute("pageError", "Ei yhtään käyttäjiä!"); 
        
        /* Luodaan RequestDispatcher-olio, joka osaa näyttää näkymätiedoston lista.jsp */
        RequestDispatcher dispatcher = request.getRequestDispatcher("lista.jsp");
        /* Pyydetään dispatcher-oliota näyttämään JSP-sivunsa */
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
            Logger.getLogger(KayttajaServLet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(KayttajaServLet.class.getName()).log(Level.SEVERE, null, ex);
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
