package KehittynytOstoslista.ServLets;

import KehittynytOstoslista.Models.Kayttaja;
import KehittynytOstoslista.Models.OstoslistaTallennettu;
import KehittynytOstoslista.Models.TuoteLista;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class OstoslistaTallennettuPoistoServLet extends HttpServlet {

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
        
        TuoteLista.poistaLista(Integer.parseInt(request.getParameter("id")));
        boolean poisto = OstoslistaTallennettu.poistaLista(Integer.parseInt(request.getParameter("id")));
        
        if (!poisto) {
            request.setAttribute("listanpoisto", "Listan poisto onnistui.");
        } else {
            request.setAttribute("listanpoisto", "Listan poisto epäonnistui.");
        }
        
        HttpSession session = request.getSession();
        String tunnus = (String)session.getAttribute("kirjautunut");
        Kayttaja kayttaja = Kayttaja.haeKayttajaTunnuksella(tunnus);
        List<OstoslistaTallennettu> listat = null;
        
        listat = OstoslistaTallennettu.haeKaikkiOstoslistaTallennettuJoitaEiKuitattu(kayttaja.getId());
    
        request.setAttribute("listat", listat);
        
        if (listat.isEmpty()) {
            request.setAttribute("eiOstoslistoja", "Ei tallennettuja listoja.");
        }
        
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
        } catch (Exception ex) {
            Logger.getLogger(OstoslistaTallennettuPoistoServLet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(OstoslistaTallennettuPoistoServLet.class.getName()).log(Level.SEVERE, null, ex);
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
