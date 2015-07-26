package KehittynytOstoslista.ServLets;


import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Yhteys {
    private static DataSource yhteysVarasto;
    
    static Connection getYhteys() throws NamingException, SQLException {
        InitialContext cxt = new InitialContext();
        yhteysVarasto = (DataSource) cxt.lookup("java:/comp/env/jdbc/tietokanta");
        
        Connection yhteys = yhteysVarasto.getConnection(); 
        
        return yhteys;
    }
    
    void close(Connection yhteys) {
        try { yhteys.close(); } catch (Exception e) {  }
    }
}
