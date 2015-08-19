package KehittynytOstoslista.ServLets;

import KehittynytOstoslista.Models.Kauppa;
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
public class KaupanMuokkausServLet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException, SQLException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        
        int kauppaid = Integer.parseInt(request.getParameter("id"));
        String muokkausnimi = request.getParameter("muokkausnimi");
        String muokkauskaupunki = request.getParameter("muokkauskaupunki");
        String muokkausosoite = request.getParameter("muokkausosoite");
        int muokkausbonus = Integer.parseInt(request.getParameter("muokkausbonus"));
        
        String muokkausnimiviesti = "";
        String muokkauskaupunkiviesti = "";
        String muokkausosoiteviesti = "";
        String muokkausbonusviesti = "";
        
        boolean nimi = Kauppa.muokkaaNimi(muokkausnimi, kauppaid);
        boolean kaupunki = Kauppa.muokkaaKaupunki(muokkauskaupunki, kauppaid);
        boolean osoite = Kauppa.muokkaaOsoite(muokkausosoite, kauppaid);
        boolean bonus = Kauppa.muokkaaBonus(muokkausbonus, kauppaid);
        
        if(nimi) {
            muokkausnimiviesti += "Kaupan nimi muokattu onnistuneesti. ";
        } else {
            muokkausnimiviesti += "Kaupan nimeä ei voitu muokata. ";
            if(muokkausnimi.length() > 50) {
                muokkausnimiviesti += "Kaupan nimessä saa olla maksimissaan 50 merkkiä. ";
            }
            if(muokkausnimi.equals("")) {
                muokkausnimiviesti += "Kaupan nimessä pitää olla vähintään yksi merkki. ";
            }
        }
        
        if(kaupunki) {
            muokkauskaupunkiviesti += "Kaupunki muokattu onnistuneesti. ";
        } else {
            muokkauskaupunkiviesti += "Kaupunkia ei voitu muokata. ";
            if(muokkauskaupunki.length() > 50) {
                muokkauskaupunkiviesti += "Kaupungin nimessä saa olla maksimissaan 50 merkkiä. ";
            }
            if(muokkauskaupunki.equals("")) {
                muokkauskaupunkiviesti += "Kaupungin nimessä pitää olla vähintään yksi merkki. ";
            }
        }
        
        if(osoite) {
            muokkausosoiteviesti += "Osoite muokattu onnistuneesti. ";
        } else {
            muokkausosoiteviesti += "Osoite ei voitu muokata. ";
            if(muokkausosoite.length() > 50) {
                muokkausosoiteviesti += "Osoitteessa saa olla maksimissaan 50 merkkiä. ";
            }
        }
        
        if(bonus) {
            muokkausbonusviesti += "Bonus muokattu onnistuneesti. ";
        } else {
            muokkausbonusviesti += "Bonusta ei voitu muokata. ";
        }
        
        request.setAttribute("muokkausnimiviesti", muokkausnimiviesti);
        request.setAttribute("muokkauskaupunkiviesti", muokkauskaupunkiviesti);
        request.setAttribute("muokkausosoiteviesti", muokkausosoiteviesti);
        request.setAttribute("muokkausbonusviesti", muokkausbonusviesti);
        
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
        } catch (SQLException ex) {
            Logger.getLogger(KaupanMuokkausServLet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(KaupanMuokkausServLet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(KaupanMuokkausServLet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(KaupanMuokkausServLet.class.getName()).log(Level.SEVERE, null, ex);
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
