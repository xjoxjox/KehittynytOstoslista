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
* Jokainen Alakategoria - luokan olio kuuluu johonkin (ylä)Kategoriaan, joka määritellään tässä luokassa.
*
* @see KehittynytOstoslista.Models.Alakategoria
* @author Johanna
*/

public class Kategoria {
    private int id;
    private String kuvaus;
    
    private Kategoria(ResultSet tulos) throws SQLException {
        Kategoria k = new Kategoria(
            tulos.getInt("category_id"),
            tulos.getString("description")
        );
    }

    public Kategoria(int id, String kuvaus) {
        this.id = id;
        this.kuvaus = kuvaus;
    }
    /**
    * Metodilla haetaan kategoria sen id:llä.
    *
    * @param id kategorian id tietokannassa.
    * @throws Exception
    * @return palauttaa id:tä vastaavan kategorian.
    */
    public static Kategoria haeKategoria(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM category WHERE category_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return new Kategoria(tulokset);
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
    * Metodilla haetaan kategorioita hakusanalla.
    *
    * @param hakusana hakusana jolla kategoriaa haetaan tietokannasta.
    * @throws Exception
    * @return palauttaa listan kategorioita, jotka löytyvät hakusanalla.
    */
    public static List<Kategoria> haeKategoriat(String hakusana) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Kategoria> kategoriat = new ArrayList<Kategoria>();

        try {
            String sql = "SELECT * FROM category WHERE description like ? ORDER BY description";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, "%" + hakusana + "%");
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                Kategoria k = new Kategoria(tulokset);
                kategoriat.add(k);
            }

            
            return kategoriat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan kaikki kategoriat.
    *
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa listana kaikki kategoriat.
    */
    public static List<Kategoria> haeKaikkiKategoriat() throws SQLException, NamingException {
        String sql = "SELECT category_id, description FROM category ORDER BY description";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kategoria> kategoriat = new ArrayList<Kategoria>();
        
        while (tulokset.next()) {
            Kategoria k = new Kategoria(tulokset.getInt("category_id"), tulokset.getString("description"));
            kategoriat.add(k);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return kategoriat;
    }
    /**
    * Metodilla muokataan kategorian kuvausta.
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
            String sql = "UPDATE category SET description = ? WHERE category_id = ? RETURNING description";
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
    * Metodilla tallennetaan Kategoria - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos tallennus onnistui.
    */ 
    public boolean tallenna() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO category(description) VALUES(?) RETURNING category_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, kuvaus);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                id = tulokset.getInt("category_id");
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
    * Metodilla poistetaan Kategoria - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
    public boolean poista() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM category where category_id = ?";
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