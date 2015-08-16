package KehittynytOstoslista.ServLets;

import KehittynytOstoslista.Models.Tuote;
import KehittynytOstoslista.Models.TuoteHinta;
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
public class TuotteenlisaysServLet extends HttpServlet {

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
        
        String nimi = request.getParameter("nimi");
        String valmistaja = request.getParameter("valmistaja");
        double hinta = -1;
        if (!request.getParameter("hinta").matches("[0-9.]*")) {
            hinta = Double.parseDouble(request.getParameter("hinta"));
        }
        double paino = -1;
        if (!request.getParameter("paino").matches("[0-9.]*")) {
            paino = Double.parseDouble(request.getParameter("paino"));
        }
        String lisaysviesti = "";
        String hintalisaysviesti = "";
        
        Tuote tuote = Tuote.lisaaTuote(nimi, valmistaja, paino);

        if(tuote != null) {
            lisaysviesti += "Tuote lisätty onnistuneesti.";
            boolean tulos = TuoteHinta.lisaaTuoteHinta(hinta, 
                1, tuote.getId(), Integer.parseInt(request.getParameter("kauppa")));
                if (tulos) {
                    hintalisaysviesti += "Tuotteen hinta lisätty onnistuneesti.";
                } else {
                    hintalisaysviesti += "Tuotteen hintaa ei voitu lisätä. ";
                    if (hinta < 0) {
                        hintalisaysviesti += "Hinta ei voi olla negatiivinen tai tyhjä. "; 
                    }
                    if (request.getParameter("hinta").contains(",")) {
                        hintalisaysviesti += "Käytä desimaalierottimena pistettä. "; 
                    } else {
                        hintalisaysviesti += "Hinta saa sisältää vain numeroita ja desimaalierottimena pisteen. "; 
                    }
                }
        } else {
            lisaysviesti += "Tuotteen lisäys epäonnistui. ";
            if (nimi.length() > 50) {
                lisaysviesti += "Tuotteen nimessä voi olla maksimissaan 50 merkkiä. ";
            }
            if (nimi.equals("")) {
                lisaysviesti += "Tuotteen nimessä täytyy olla vähintään yksi merkki. ";
            }
            if (valmistaja.length() > 50) {
                lisaysviesti += "Valmistaja voi olla maksimissaan 50 merkkiä. ";
            }
            if (valmistaja.equals("")) {
                lisaysviesti += "Valmistajassa täytyy olla vähintään yksi merkki. ";
            }
            if (paino < 0) {
                lisaysviesti += "Tuotteen paino ei voi olla negatiivinen tai tyhjä. ";
            }
            if (request.getParameter("paino").contains(",")) {
                lisaysviesti += "Käytä painon desimaalierottimena pistettä. ";
            }
            if (!request.getParameter("paino").matches("[0-9.]*")) {
                lisaysviesti += "Paino saa sisältää vain numeroita ja desimaalierottimena pisteen. ";
            }
        }    
        
        request.setAttribute("lisaysviesti", lisaysviesti);
        request.setAttribute("hintalisaysviesti", hintalisaysviesti);
           
        RequestDispatcher dispatcher = request.getRequestDispatcher("tuotteet.jsp");
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
            Logger.getLogger(TuotteenlisaysServLet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TuotteenlisaysServLet.class.getName()).log(Level.SEVERE, null, ex);
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
