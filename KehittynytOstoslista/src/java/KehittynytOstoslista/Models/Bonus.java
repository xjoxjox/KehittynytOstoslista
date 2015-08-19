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
* Kauppa - olioon voi kuulua jokin Bonus - olio, joka tässä luokassa määritellään.
*
* @see KehittynytOstoslista.Models.Kauppa
* @author Johanna
*/

public class Bonus {
    private int id;
    private String nimi;
    
    private Bonus(ResultSet tulos) throws SQLException {
        Bonus b = new Bonus(
            tulos.getInt("bonus_id"),
            tulos.getString("name")
        );
    }

    public Bonus(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    } 
    /**
    * Metodilla haetaan bonus sen id:llä.
    *
    * @param id bonuksen id tietokannassa.
    * @throws Exception
    * @return palauttaa id:tä vastaavan bonuksen.
    */
    public static Bonus haeBonus(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM bonus WHERE bonus_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                Bonus b = new Bonus(tulokset.getInt("bonus_id"), tulokset.getString("name"));
                return b;
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
    * Metodilla haetaan bonuksia tietokannasta hakusanalla.
    *
    * @param hakusana hakusana, jolla bonuksia haetaan tietokannasta.
    * @throws Exception
    * @return palauttaa listana bonukset, jotka vastaavat hakusanaa.
    */
    public static List<Bonus> haeBonukset(String hakusana) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Bonus> bonukset = new ArrayList<Bonus>();

        try {
            String sql = "SELECT * FROM bonus WHERE name like ? ORDER BY name";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, "%" + hakusana + "%");
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                Bonus b = new Bonus(tulokset.getInt("bonus_id"), tulokset.getString("name"));
                bonukset.add(b);
            }

            
            return bonukset;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan kaikki bonukset.
    *
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa listana kaikki bonukset.
    */
    public static List<Bonus> haeKaikkiBonukset() throws SQLException, NamingException {
        String sql = "SELECT bonus_id, name FROM bonus ORDER BY name";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Bonus> bonukset = new ArrayList<Bonus>();
        
        while (tulokset.next()) {
            Bonus b = new Bonus(tulokset.getInt("bonus_id"), tulokset.getString("name"));
            bonukset.add(b);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return bonukset;
    }
    /**
    * Metodilla muokataan bonuksen nimi.
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
            String sql = "UPDATE bonus SET name = ? WHERE bonus_id = ? RETURNING name";
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
    * Metodilla tallennetaan Bonus - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos tallennus onnistui.
    */
    public boolean tallenna() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO bonus(name) VALUES(?) RETURNING bonus_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, nimi);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                id = tulokset.getInt("bonus_id");
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
    * Metodilla poistetaan Bonus - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
    public boolean poista() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM bonus where bonus_id = ?";
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