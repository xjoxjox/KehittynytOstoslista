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


public class TuoteServLet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NamingException, Exception {
        
        response.setContentType("text/html;charset=UTF-8");
        
        String hakusana = request.getParameter("haku");
        List<Tuote> tuotteet = null;
        
        if (hakusana != null && hakusana.length() > 0) {
            tuotteet = Tuote.haeTuotteet(hakusana);
        } else {
            tuotteet = Tuote.getTuotteet();
        }
    
        request.setAttribute("tuotteet", tuotteet);
        if (tuotteet.isEmpty()) {
            request.setAttribute("viesti", "Tuotteita ei löytynyt");
        }
    
        /* Luodaan RequestDispatcher-olio, joka osaa näyttää näkymätiedoston lista.jsp */
        RequestDispatcher dispatcher = request.getRequestDispatcher("lista.jsp");
        /* Pyydetään dispatcher-oliota näyttämään JSP-sivunsa */
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(TuoteServLet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TuoteServLet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(TuoteServLet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TuoteServLet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
