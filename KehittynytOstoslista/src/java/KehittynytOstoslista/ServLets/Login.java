package KehittynytOstoslista.ServLets;

import KehittynytOstoslista.Models.Kayttaja;
import java.io.IOException;
import java.sql.SQLException;
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
public class Login extends HttpServlet {

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
            throws ServletException, IOException, SQLException, NamingException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        String kayttaja = request.getParameter("username");
        String salasana = request.getParameter("password");

        if (kayttaja == null || salasana == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (kayttaja == null || kayttaja.equals("")) {
            request.setAttribute("virhe", "Kirjautuminen epäonnistui! Et antanut käyttäjätunnusta.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        request.setAttribute("kayttaja", kayttaja);

        if (salasana == null || salasana.equals("")) {
            request.setAttribute("virhe", "Kirjautuminen epäonnistui! Et antanut salasanaa.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        HttpSession session = request.getSession();
        Kayttaja k = Kayttaja.haeKayttajaTunnuksilla(kayttaja, salasana);
        
        if (k != null) {
            session.setAttribute("kirjautunut", kayttaja);
            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("virhe", "Kirjautuminen epäonnistui! Antamasi tunnus tai salasana on väärä.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
