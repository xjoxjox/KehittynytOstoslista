package KehittynytOstoslista.Models;

import KehittynytOstoslista.ServLets.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class Kauppa {
    private int id;
    private String nimi;
    private String kaupunki;
    private String osoite;
    private int bonusId;
    
    private Kauppa(ResultSet tulos) throws SQLException {
        Kauppa k = new Kauppa(
            tulos.getInt("shop_id"),
            tulos.getString("name"),
            tulos.getString("city"),
            tulos.getString("address"),
            tulos.getInt("bonus_id")
        );
    }

    public Kauppa(int id, String nimi, String kaupunki, String osoite, int bonusId) {
        this.id = id;
        this.nimi = nimi;
        this.kaupunki = kaupunki;
        this.osoite = osoite;
        this.bonusId = bonusId;
    }
    
    public static Kauppa haeKauppa(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM shop WHERE shop_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return new Kauppa(tulokset);
            } else {
                return null;
            }

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<Kauppa> haeKaupatNimella(String hakusana) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Kauppa> kaupat = new ArrayList<Kauppa>();

        try {
            String sql = "SELECT * FROM shop WHERE name like %?%";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, hakusana);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    Kauppa k = new Kauppa(tulokset);
                    kaupat.add(k);
                }
            } else {
                return null;
            }
            
            return kaupat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<Kauppa> haeKaupatKaupungilla(String hakusana) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Kauppa> kaupat = new ArrayList<Kauppa>();

        try {
            String sql = "SELECT * FROM shop WHERE city like %?%";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, hakusana);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    Kauppa k = new Kauppa(tulokset);
                    kaupat.add(k);
                }
            } else {
                return null;
            }
            
            return kaupat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<Kauppa> haeKaupatBonuksella(int bonusId) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Kauppa> kaupat = new ArrayList<Kauppa>();

        try {
            String sql = "SELECT * FROM shop WHERE name bonus_id %?%";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, bonusId);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    Kauppa k = new Kauppa(tulokset);
                    kaupat.add(k);
                }
            } else {
                return null;
            }
            
            return kaupat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<Kauppa> haeKaikkiKaupat() throws SQLException, NamingException {
        String sql = "SELECT shop_id, name, city, address, bonus_id from shop";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kauppa> kaupat = new ArrayList<Kauppa>();
        while (tulokset.next()) {
            Kauppa k = new Kauppa(tulokset.getInt("shop_id"), tulokset.getString("name"), tulokset.getString("city"),
                tulokset.getString("address"), tulokset.getInt("bonus_id"));
            kaupat.add(k);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return kaupat;
    }
    
    public boolean tallenna() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO shop(name, city, address, bonus_id) VALUES(?,?,?,?) RETURNING shop_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, nimi);
            kysely.setString(2, kaupunki);
            kysely.setString(3, osoite);
            kysely.setInt(4, bonusId);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                id = tulokset.getInt("shop_id");
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
    
    public boolean poista() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM shop where shop_id = ?";
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
  
    public String getKaupunki() {
        return this.kaupunki;
    }
  
    public String getOsoite() {
        return this.osoite;
    }
  
    public int getBonusId() {
        return this.bonusId;
    }
  
    public void setId(int x) {
        this.id = x;
    }
  
    public void setNimi(String x) {
        this.nimi = x;
    }
  
    public void setKaupunki(String x) {
        this.kaupunki = x;
    }
  
    public void setOsoite(String x) {
        this.osoite = x;
    }
  
    public void setBonusId(int x) {
        this.bonusId = x;
    }
}