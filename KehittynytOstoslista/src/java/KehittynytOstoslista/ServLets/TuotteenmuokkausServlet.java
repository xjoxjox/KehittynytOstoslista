package KehittynytOstoslista.ServLets;

import KehittynytOstoslista.Models.Tuote;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Johanna
 */
public class TuotteenmuokkausServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException, NamingException, Exception {
        
        response.setContentType("text/html;charset=UTF-8");
        
        String muokkausnimi = request.getParameter("muokkausnimi");
        String muokkausvalmistaja = request.getParameter("muokkausvalmistaja");
        double muokkauspaino = -1;
        if (!request.getParameter("muokkauspaino").matches("[0-9.]*")) {
            muokkauspaino = Double.parseDouble(request.getParameter("paino"));
        }
        
        int tuoteid = Integer.parseInt(request.getParameter("id"));
        
        boolean nimi = Tuote.muokkaaNimi(muokkausnimi, tuoteid);
        boolean valmistaja = Tuote.muokkaaValmistaja(muokkausvalmistaja, tuoteid);
        boolean paino = Tuote.muokkaaPaino(muokkauspaino, tuoteid);
        
        String nimionnistui = "";
        String valmistajaonnistui = "";
        String painonnistui = "";
        
        if (nimi) {
            nimionnistui += "Tuotteen nimen muokkaus onnistui. ";
        } else {
            nimionnistui += "Tuotteen nimen muokkaus epäonnistui. ";
            if (muokkausnimi.length() > 50) {
                nimionnistui += "Tuotteen nimessä saa olla maksimissaan 50 merkkiä. ";
            }
            if (muokkausnimi.equals("")) {
                nimionnistui += "Tuotteen nimessä pitää olla vähintään yksi merkki. ";
            }
        }
        
        if (valmistaja) {
            valmistajaonnistui += "Tuotteen valmistajan muokkaus onnistui. ";
        } else {
            valmistajaonnistui += "Tuotteen valmistajan muokkaus epäonnistui. ";
            if (muokkausvalmistaja.length() > 50) {
                valmistajaonnistui += "Tuotteen valmistajassa saa olla maksimissaan 50 merkkiä. ";
            }
            if (muokkausvalmistaja.equals("")) {
                valmistajaonnistui += "Tuotteen valmistajassa pitää olla vähintään yksi merkki. ";
            }
        }
        
        if (paino) {
            painonnistui += "Tuotteen painon muokkaus onnistui. ";
        } else {
            painonnistui += "Tuotteen painon muokkaus epäonnistui. ";
            if (muokkauspaino < 0) {
                painonnistui += "Tuotteen paino ei voi olla negatiivinen. ";
            }
            if(request.getParameter("muokkauspaino").contains(",")) {
                painonnistui += "Käytä desimaalierottimena pistettä. ";
            }
            if (!request.getParameter("muokkauspaino").matches("[0-9.]*")) {
                painonnistui += "Paino saa sisältää vain numeroita. \n";
            }
        }
        
        request.setAttribute("nimionnistui", nimionnistui);
        request.setAttribute("valmistajaonnistui", valmistajaonnistui);
        request.setAttribute("painonnistui", painonnistui);
        
        String hakunimi = request.getParameter("hakunimi");
        
        List<Tuote> tuotteet = null;
        
        if (hakunimi != null && hakunimi.length() > 0) {
            tuotteet = Tuote.haeTuotteet(hakunimi);
        } else {
            tuotteet = Tuote.haeKaikkiTuotteet(1);
        }
        
        request.setAttribute("tuotteet", tuotteet);
        
        if (hakunimi != null) {
            request.setAttribute("hakunimi", hakunimi);
        }
        
        int tuoteLkm = Tuote.tuotteidenLukumaara();
        request.setAttribute("tuoteLkm", tuoteLkm);
        
        if (tuotteet.isEmpty()) {
            request.setAttribute("viesti", "Tuotteita ei löytynyt");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("tuote.jsp");
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
        } catch (SQLException ex) {
            Logger.getLogger(TuotteenmuokkausServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(TuotteenmuokkausServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TuotteenmuokkausServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(TuotteenmuokkausServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(TuotteenmuokkausServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TuotteenmuokkausServlet.class.getName()).log(Level.SEVERE, null, ex);
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
