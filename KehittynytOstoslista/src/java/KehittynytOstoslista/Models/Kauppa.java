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
            String sql = "SELECT * FROM shop WHERE name like ? ORDER BY name";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, "%" + hakusana + "%");
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
            String sql = "SELECT * FROM shop WHERE city like ? ORDER BY name";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, "%" + hakusana + "%");
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
            String sql = "SELECT * FROM shop WHERE bonus_id = ? ORDER BY name";
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
        String sql = "SELECT shop_id, name, city, address, bonus_id FROM shop ORDER BY name";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kauppa> kaupat = new ArrayList<Kauppa>();
        
        while (tulokset.next()) {
            System.out.println(tulokset.getString("name"));
            Kauppa k = new Kauppa(tulokset);
            k.setId(tulokset.getInt("shop_id"));
            k.setNimi(tulokset.getString("name"));
            k.setKaupunki(tulokset.getString("city"));
            k.setOsoite(tulokset.getString("address"));
            k.setBonusId(tulokset.getInt("bonus_id"));
            kaupat.add(k);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return kaupat;
    }
    
    public boolean muokkaaNimi(String x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shop SET name = ? WHERE shop_id = ? RETURNING name";
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
    
    public boolean muokkaaKaupunki(String x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shop SET city = ? WHERE shop_id = ? RETURNING city";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.kaupunki = tulokset.getString("city");
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
    
    public boolean muokkaaOsoite(String x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shop SET address = ? WHERE shop_id = ? RETURNING address";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.osoite = tulokset.getString("address");
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
    
    public boolean muokkaaBonus(int x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shop SET bonus_id = ? WHERE shop_id = ? RETURNING bonus_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.bonusId = tulokset.getInt("onus_id");
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
    
    public static boolean poistaKauppa(int poistoId) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM shop where shop_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, poistoId);
            return kysely.execute();
        } finally {
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static boolean lisaaKauppa(String lisaysnimi, String lisayskaupunki, String lisaysosoite, int lisaysbonus) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        if(lisaysnimi.length() > 50 || lisaysnimi.length() < 0 || lisaysnimi.equals("")) {
            return false;
        }       
        if(lisayskaupunki.length() > 50 || lisayskaupunki.length() < 0 || lisayskaupunki.equals("")) {
            return false;
        }
        if(lisaysosoite.length() > 50 || lisaysosoite.length() < 0 || lisaysosoite.equals("")) {
            return false;
        }
        if(lisaysbonus == 0) {
            return false;
        }

        try {
            String sql = "INSERT INTO shop(name, city, address, bonus_id) VALUES(?,?,?,?) RETURNING shop_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, lisaysnimi);
            kysely.setString(2, lisayskaupunki);
            kysely.setString(3, lisaysosoite);
            kysely.setInt(4, lisaysbonus);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
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