package KehittynytOstoslista.ServLets;

import KehittynytOstoslista.Models.Kauppa;
import KehittynytOstoslista.Models.Kayttaja;
import KehittynytOstoslista.Models.OstoslistaTallennettu;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Johanna
 */
public class OstoslistanlisaysServLet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NamingException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        
        String listanimi = request.getParameter("listanimi");
        int listakauppa = Integer.parseInt(request.getParameter("kauppa"));
        String kayttaja = (String)session.getAttribute("kirjautunut");
        
        Kayttaja k = Kayttaja.haeKayttajaTunnuksella(kayttaja);
        
        boolean lista = OstoslistaTallennettu.luoUusiLista(listanimi, listakauppa, k.getId());
        
        String listanluonti = "";
        
        if (lista) {
            listanluonti += "Uusi lista tallennettu. ";
        } else {
            listanluonti += "Uuden listan tallentaminen epäonnistui. ";
            if (listanimi.length() > 50) {
                listanluonti += "Listan nimessä saa olla maksimissaan 50 merkkiä.";
            }
            if (listanimi.equals("")) {
                listanluonti += "Listan nimessä pitää olla vähintään yksi merkki.";
            }
        }
        
        request.setAttribute("listanluonti", listanluonti);
        
        List<OstoslistaTallennettu> listat = OstoslistaTallennettu.haeKaikkiOstoslistaTallennettuJoitaEiKuitattu(k.getId());
        
        request.setAttribute("listat", listat);
        
        if(listat.isEmpty() || listat == null) {
            request.setAttribute("eiOstoslistoja", "Ei tallennettuja ostoslistoja.");
        }
        
        List<Kauppa> kaupat = null;
        
        kaupat = Kauppa.haeKaikkiKaupat(1);
        
        request.setAttribute("kaupat", kaupat);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("ostoslistat.jsp");
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
        } catch (NamingException ex) {
            Logger.getLogger(OstoslistanlisaysServLet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(OstoslistanlisaysServLet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (NamingException ex) {
            Logger.getLogger(OstoslistanlisaysServLet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(OstoslistanlisaysServLet.class.getName()).log(Level.SEVERE, null, ex);
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
