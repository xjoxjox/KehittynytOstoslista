package KehittynytOstoslista.Models;

import KehittynytOstoslista.ServLets.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
* Ostoslistaan voi merkitä tiedon, millä Maksutavalla on maksanut ostoksensa.
* 
* @see KehittynytOstoslista.Models.OstoslistaTallennettu
* @see KehittynytOstoslista.Models.OstoslistaKuitattu
* @author Johanna
*/

public class Maksutapa {
    private int id;
    private String nimi;
    
    private Maksutapa(ResultSet tulos) throws SQLException {
        Maksutapa m = new Maksutapa(
            tulos.getInt("payment_id"),
            tulos.getString("name")
        );
    }

    public Maksutapa(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }
    /**
    * Metodilla haetaan maksutapa sen id:llä.
    *
    * @param id maksutavan id tietokannassa.
    * @throws Exception
    * @return palauttaa id:tä vastaavan maksutavan.
    */
    public static Maksutapa haeMaksutapa(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM payment WHERE payment_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return new Maksutapa(tulokset);
            } else {
                return null;
            }

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan maksutapoja hakusanalla.
    *
    * @param hakusana hakusana jolla maksutapoja haetaan tietokannasta.
    * @throws Exception
    * @return palauttaa listan maksutavoista, jotka löytyvät hakusanalla.
    */
    public static List<Maksutapa> haeMaksutavat(String hakusana) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Maksutapa> maksutavat = new ArrayList<Maksutapa>();

        try {
            String sql = "SELECT * FROM payment WHERE name like ? ORDER BY name";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, "%" + hakusana + "%");
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                Maksutapa m = new Maksutapa(tulokset);
                maksutavat.add(m);
            }

            
            return maksutavat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan kaikki maksutavat.
    *
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa listana kaikki maksutavat.
    */
    public static List<Maksutapa> haeKaikkiMaksutavat() throws SQLException, NamingException {
        String sql = "SELECT payment_id, name FROM payment ORDER BY name";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Maksutapa> maksutavat = new ArrayList<Maksutapa>();
        
        while (tulokset.next()) {
            Maksutapa m = new Maksutapa(tulokset.getInt("payment_id"), tulokset.getString("name"));
            maksutavat.add(m);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return maksutavat;
    }
    /**
    * Metodilla muokataan maksutavan nimi.
    *
    * @param x uusi nimi.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public boolean muokkaaNimi(String x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE payment SET name = ? WHERE payment_id = ? RETURNING name";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.nimi = tulokset.getString("name");
                return true;
            } else {
                return false;
            }

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla tallennetaan Maksutapa - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos tallennus onnistui.
    */
    public boolean tallenna() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO payment(name) VALUES(?) RETURNING payment_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, nimi);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                id = tulokset.getInt("payment_id");
                return true;
            } else {
                return false;
            }

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla poistetaan Maksutapa - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
    public boolean poista() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM payment where payment_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            return kysely.execute();
        } finally {
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }

    public int getId() {
        return this.id;
    }
  
    public String getNimi() {
        return this.nimi;
    }

    public void setId(int x) {
        this.id = x;
    }
  
    public void setNimi(String x) {
        this.nimi = x;
    }
}