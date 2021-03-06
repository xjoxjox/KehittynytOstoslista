package KehittynytOstoslista.ServLets;

import KehittynytOstoslista.Models.Tuote;
import KehittynytOstoslista.Models.TuoteHinta;
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

/**
 *
 * @author Johanna
 */
public class TuotteenpoistoServLet extends HttpServlet {

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
        
        boolean listalla = TuoteLista.onkoTuoteListalla(Integer.parseInt(request.getParameter("id")));
        
        if (!listalla) {
            TuoteHinta.poistaTuoteHinta(Integer.parseInt(request.getParameter("id")));
        
            boolean tulos = Tuote.poistaTuote(Integer.parseInt(request.getParameter("id")));
        
            if(!tulos) {
                request.setAttribute("poistoviesti", "Tuote poistettu onnistuneesti.");
            } else {
                request.setAttribute("poistoviesti", "Tuotteen poisto epäonnistui.");
            }
        } else {
            request.setAttribute("poistoviesti", "Tuotetta ei voida poistaa, koska se on jollakin ostoslistalla.");
        }
        
        String hakunimi = request.getParameter("hakunimi");
        
        List<Tuote> tuotteet = null;
        
        if (hakunimi != null && hakunimi.length() > 0) {
            tuotteet = Tuote.haeTuotteet(hakunimi);
        } else {
            tuotteet = Tuote.haeKaikkiTuotteet(1);
        }
        
        int tuoteLkm = Tuote.tuotteidenLukumaara();
        request.setAttribute("tuoteLkm", tuoteLkm);
        
        request.setAttribute("hakunimi", hakunimi);
        request.setAttribute("tuotteet", tuotteet);
        
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
        } catch (Exception ex) {
            Logger.getLogger(TuotteenpoistoServLet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TuotteenpoistoServLet.class.getName()).log(Level.SEVERE, null, ex);
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
