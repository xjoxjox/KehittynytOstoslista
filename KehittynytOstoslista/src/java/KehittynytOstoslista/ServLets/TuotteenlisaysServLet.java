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
        
        Tuote tuote = Tuote.lisaaTuote(request.getParameter("nimi"), request.getParameter("valmistaja"), 
                Double.parseDouble(request.getParameter("paino")));
        
        
        
        if(tuote != null) {
            request.setAttribute("lisaysviesti", "Tuote lisätty onnistuneesti.");
            boolean tulos = TuoteHinta.lisaaTuoteHinta(Double.parseDouble(request.getParameter("hinta")), 
                1, tuote.getId(), Integer.parseInt(request.getParameter("kauppa")));
                if (tulos) {
                    request.setAttribute("hintalisaysviesti", "Tuotteen hinta lisätty onnistuneesti.");
                } else {
                    request.setAttribute("hintalisaysviesti", "Tuotteen hintaa ei voitu lisätä.");
                }
        } else {
            request.setAttribute("lisaysviesti", "Tuotteen lisäys epäonnistui.");
        }       
           
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
