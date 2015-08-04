package KehittynytOstoslista.Models;
import KehittynytOstoslista.ServLets.Yhteys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class Kayttaja {
    private int id;
    private String tunnus;
    private String salasana;
    private boolean admin;

    private Kayttaja(ResultSet tulos) throws SQLException {
        Kayttaja k = new Kayttaja(
            tulos.getInt("account_id"),
            tulos.getString("username"),
            tulos.getString("password"),
            tulos.getBoolean("admin")
        );
    }
        
    public Kayttaja(int id, String tunnus, String salasana, boolean admin) {
        this.id = id;
        this.tunnus = tunnus;
        this.salasana = salasana;
        this.admin = admin;
    }
    
    public static Kayttaja haeKayttajaIDlla(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return new Kayttaja(tulokset);
            } else {
                return null;
            }

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static Kayttaja haeKayttajaTunnuksilla(String kayttaja, String salasana) throws SQLException, NamingException {
        String sql = "SELECT account_id ,username, password, admin from account where username = ? AND password = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, kayttaja);
        kysely.setString(2, salasana);
        ResultSet rs = kysely.executeQuery();
  
        Kayttaja kirjautunut = null;

        if (rs.next()) { 
            kirjautunut = new Kayttaja(rs);
            kirjautunut.setId(rs.getInt("account_id"));
            kirjautunut.setTunnus(rs.getString("username"));
            kirjautunut.setSalasana(rs.getString("password"));
            kirjautunut.setAdmin(rs.getBoolean("admin"));
        }

        try { rs.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return kirjautunut;
    }
    
    public static List<Kayttaja> haeKayttajat(String hakusana) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Kayttaja> kayttajat = new ArrayList<Kayttaja>();

        try {
            String sql = "SELECT * FROM product WHERE username like %?%";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, hakusana);
            kysely.setString(2, hakusana);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    Kayttaja k = new Kayttaja(tulokset);
                    kayttajat.add(k);
                }
            } else {
                return null;
            }
            
            return kayttajat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
  
    public static List<Kayttaja> haeKaikkiKayttajat() throws SQLException, NamingException {
        String sql = "SELECT account_id, username, password, admin from account";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kayttaja> kayttajat = new ArrayList<Kayttaja>();
        while (tulokset.next()) {
            Kayttaja k = new Kayttaja(tulokset.getInt("account_id"), tulokset.getString("username"), 
                    tulokset.getString("password"), tulokset.getBoolean("admin"));
            kayttajat.add(k);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return kayttajat;
    }
    
    public int getId() {
        return this.id;
    }
  
    public String getTunnus() {
        return this.tunnus;
    }
  
    public String getSalasana() {
        return this.salasana;
    }
    
    public boolean getAdmin() {
        return this.admin;
    }
  
    public void setId(int x) {
        this.id = x;
    }
  
    public void setTunnus(String x) {
        this.tunnus = x;
    }
  
    public void setSalasana(String x) {
        this.salasana = x;
    }
    
    public void setAdmin() {
        if (this.admin = false) {
            this.admin = true;
        } else {
            this.admin = false;
        }
    }
    
    public void setAdmin(boolean x) {
        this.admin = x;
    }
}