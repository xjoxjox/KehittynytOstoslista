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
* Jokainen Tuote - luokan olio kuuluu johonkin alakategoriaan, joka määritellään tässä luokassa.
*
* @see KehittynytOstoslista.Models.TuoteKategoria
* @author Johanna
*/

public class Alakategoria {
    private int id;
    private String kuvaus;
    
    private Alakategoria(ResultSet tulos) throws SQLException {
        Alakategoria a = new Alakategoria(
            tulos.getInt("subcategory_id"),
            tulos.getString("description")
        );
    }

    public Alakategoria(int id, String kuvaus) {
        this.id = id;
        this.kuvaus = kuvaus;
    }
    /**
    * Metodilla haetaan alakategoria sen id:llä.
    *
    * @param id alakategorian id tietokannassa.
    * @throws Exception
    * @return palauttaa id:tä vastaavan alakategorian.
    */
    public static Alakategoria haeAlakategoria(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM subcategory WHERE subcategory_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return new Alakategoria(tulokset);
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
    * Metodilla haetaan alakategorioita hakusanalla.
    *
    * @param hakusana hakusana jolla alakategoriaa haetaan tietokannasta.
    * @throws Exception
    * @return palauttaa listan alakategorioita, jotka löytyvät hakusanalla.
    */
    public static List<Alakategoria> haeAlakategoriat(String hakusana) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Alakategoria> alakategoriat = new ArrayList<Alakategoria>();

        try {
            String sql = "SELECT * FROM subcategory WHERE description like ? ORDER BY description";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, "%" + hakusana + "%");
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                Alakategoria a = new Alakategoria(tulokset);
                alakategoriat.add(a);
            }

            
            return alakategoriat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan kaikki alakategoriat.
    *
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa listana kaikki alakategoriat.
    */
    public static List<Alakategoria> haeKaikkiAlakategoriat() throws SQLException, NamingException {
        String sql = "SELECT subcategory_id, description, category_id FROM subcategory ORDER BY description";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Alakategoria> alakategoriat = new ArrayList<Alakategoria>();
        
        while (tulokset.next()) {
            Alakategoria a = new Alakategoria(tulokset.getInt("subcategory_id"), tulokset.getString("description"));
            alakategoriat.add(a);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return alakategoriat;
    }
    /**
    * Metodilla muokataan alakategorian kuvausta.
    *
    * @param x uusi kuvaus.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public boolean muokkaaKuvaus(String x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE subcategory SET description = ? WHERE subcategory_id = ? RETURNING description";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.kuvaus = tulokset.getString("description");
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
    * Metodilla tallennetaan Alakategoria - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos tallennus onnistui.
    */
    public boolean tallenna() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO subcategory(description) VALUES(?) RETURNING subcategory_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, kuvaus);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                id = tulokset.getInt("subcategory_id");
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
    * Metodilla poistetaan Alakategoria - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
    public boolean poista() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM subcategory where subcategory_id = ?";
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
  
    public String getKuvaus() {
        return this.kuvaus;
    }

    public void setId(int x) {
        this.id = x;
    }
  
    public void setKuvaus(String x) {
        this.kuvaus = x;
    }
}
