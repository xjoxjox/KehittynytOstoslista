package KehittynytOstoslista.ServLets;

import KehittynytOstoslista.Models.Kauppa;
import java.io.IOException;
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
public class KaupanlisaysServLet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        String nimi = request.getParameter("nimi");
        String kaupunki = request.getParameter("kaupunki");
        String osoite = request.getParameter("osoite");
        int hakubonus = Integer.parseInt(request.getParameter("bonus"));
        boolean tulos = Kauppa.lisaaKauppa(nimi, kaupunki, osoite, hakubonus);
        String lisaysviesti = "";
        
        if(tulos) {
            lisaysviesti += "Kauppa lisätty onnistuneesti.";
        } else {
            lisaysviesti += "Kaupan lisäys epäonnistui. ";
            if (nimi.length() > 50) {
                lisaysviesti += "Kaupan nimessä saa olla maksimissaan 50 merkkiä. ";
            }
            if (nimi.equals("")) {
                lisaysviesti += "Kaupan nimessä pitää olla vähintään yksi merkki. ";
            }
            if (kaupunki.length() > 50) {
                lisaysviesti += "Kaupunki saa olla maksimissaan 50 merkkiä pitkä. ";
            }
            if (kaupunki.equals("")) {
                lisaysviesti += "Kaupungissa pitää olla vähintään yksi merkki. ";
            }
            if (osoite.length() > 50) {
                lisaysviesti += "Osoite saa olla maksimissaan 50 merkkiä. ";
            }
            if (osoite.equals("")) {
                lisaysviesti += "Osoitteessa pitää olla vähintään yksi merkki. ";
            }
        }
        
        request.setAttribute("lisaysviesti", lisaysviesti);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("kaupat.jsp");
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
            Logger.getLogger(KaupanlisaysServLet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(KaupanlisaysServLet.class.getName()).log(Level.SEVERE, null, ex);
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
