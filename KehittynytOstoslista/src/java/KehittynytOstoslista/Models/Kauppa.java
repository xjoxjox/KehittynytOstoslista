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
* @author Johanna
*/

public class Kauppa {
    private int id;
    private String nimi;
    private String kaupunki;
    private String osoite;
    private Bonus bonus;
    
    private Kauppa(ResultSet tulos) throws SQLException, Exception {
        Kauppa k = new Kauppa(
            tulos.getInt("shop_id"),
            tulos.getString("name"),
            tulos.getString("city"),
            tulos.getString("address"),
            Bonus.haeBonus(tulos.getInt("bonus_id"))
        );
    }

    public Kauppa(int id, String nimi, String kaupunki, String osoite, Bonus bonus) {
        this.id = id;
        this.nimi = nimi;
        this.kaupunki = kaupunki;
        this.osoite = osoite;
        this.bonus = bonus;
    }
    /**
    * Metodilla haetaan kauppa sen id:llä.
    *
    * @param id kaupan id tietokannassa.
    * @throws Exception
    * @return palauttaa id:tä vastaavan kaupan.
    */
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
                Kauppa k = new Kauppa(tulokset.getInt("shop_id"), tulokset.getString("name"), tulokset.getString("city"),
                tulokset.getString("address"), Bonus.haeBonus(tulokset.getInt("bonus_id")));
                k.setId(tulokset.getInt("shop_id"));
                k.setNimi(tulokset.getString("name"));
                k.setKaupunki(tulokset.getString("city"));
                k.setOsoite(tulokset.getString("address"));
                k.setBonus(Bonus.haeBonus(tulokset.getInt("bonus_id")));
                return k;
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
    * Metodilla haetaan kauppoja hakusanalla.
    *
    * @param hakusana hakusana jolla kauppoja haetaan tietokannasta.
    * @throws Exception
    * @return palauttaa listan kaupoista, jotka löytyvät hakusanalla.
    */
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
                    Kauppa k = new Kauppa(tulokset.getInt("shop_id"), tulokset.getString("name"), tulokset.getString("city"),
                        tulokset.getString("address"), Bonus.haeBonus(tulokset.getInt("bonus_id")));
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
    /**
    * Metodilla haetaan kauppoja kaupungin perusteella.
    *
    * @param hakusana kaupunki jolla kauppoja haetaan tietokannasta.
    * @throws Exception
    * @return palauttaa listan kaupoista, jotka löytyvät kaupungilla.
    */
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
                    Kauppa k = new Kauppa(tulokset.getInt("shop_id"), tulokset.getString("name"), tulokset.getString("city"),
                        tulokset.getString("address"), Bonus.haeBonus(tulokset.getInt("bonus_id")));
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
    /**
    * Metodilla haetaan kauppoja bonuksen perusteella.
    *
    * @param bonusId bonuksen id tietokannassa, jolla kauppoja haetaan.
    * @throws Exception
    * @see KehittynytOstoslista.Models.Bonus
    * @return palauttaa listan kaupoista, jotka löytyvät bonuksella.
    */
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
                    Kauppa k = new Kauppa(tulokset.getInt("shop_id"), tulokset.getString("name"), tulokset.getString("city"),
                        tulokset.getString("address"), Bonus.haeBonus(tulokset.getInt("bonus_id")));
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
    /**
    * Metodilla haetaan kaikki kaupat.
    *
    * @param sivu
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa listana kaikki kaupat.
    */
    public static List<Kauppa> haeKaikkiKaupat(int sivu) throws SQLException, NamingException, Exception {
        String sql = "SELECT shop_id, name, city, address, bonus_id FROM shop ORDER BY name LIMIT 50 OFFSET ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, (sivu-1)*50);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kauppa> kaupat = new ArrayList<Kauppa>();
        
        while (tulokset.next()) {
            Kauppa k = new Kauppa(tulokset.getInt("shop_id"), tulokset.getString("name"), tulokset.getString("city"),
                tulokset.getString("address"), Bonus.haeBonus(tulokset.getInt("bonus_id")));
            k.setId(tulokset.getInt("shop_id"));
            k.setNimi(tulokset.getString("name"));
            k.setKaupunki(tulokset.getString("city"));
            k.setOsoite(tulokset.getString("address"));
            k.setBonus(Bonus.haeBonus(tulokset.getInt("bonus_id")));
            kaupat.add(k);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return kaupat;
    }
    /**
    * Metodilla lasketaan kaikkien kauppojen lukumäärä.
    *
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa kauppojen lukumäärän.
    */
    public static int kauppojenLukumaara() throws SQLException, NamingException {
        String sql = "SELECT count(*) AS lkm FROM shop";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();
        
        tulokset.next(); 
        int lkm = tulokset.getInt("lkm");

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return lkm;
    }
    /**
    * Metodilla muokataan kaupan nimi.
    *
    * @param x uusi nimi.
    * @param kauppa muokattavan kaupan id tietokannassa.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public static boolean muokkaaNimi(String x, int kauppa) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        if(x.length() > 50 || x.equals("")) {
            return false;
        }

        try {
            String sql = "UPDATE shop SET name = ? WHERE shop_id = ? RETURNING name";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, x);
            kysely.setInt(2, kauppa);
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
    /**
    * Metodilla muokataan kaupunki, jossa kauppa sijaitsee.
    *
    * @param x uusi kaupunki.
    * @param kauppa muokattavan kaupan id tietokannassa.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public static boolean muokkaaKaupunki(String x, int kauppa) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        if(x.length() > 50 || x.equals("")) {
            return false;
        }

        try {
            String sql = "UPDATE shop SET city = ? WHERE shop_id = ? RETURNING city";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, x);
            kysely.setInt(2, kauppa);
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
    /**
    * Metodilla muokataan kaupan osoite.
    *
    * @param x uusi osoite.
    * @param kauppa muokattavan kaupan id tietokannassa.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public static boolean muokkaaOsoite(String x, int kauppa) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        if(x.length() > 50) {
            return false;
        }

        try {
            String sql = "UPDATE shop SET address = ? WHERE shop_id = ? RETURNING address";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, x);
            kysely.setInt(2, kauppa);
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
    /**
    * Metodilla muokataan bonus, johon kauppa kuuluu.
    *
    * @param x uusi bonus.
    * @param kauppa muokattavan kaupan id tietokannassa.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public static boolean muokkaaBonus(int x, int kauppa) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shop SET bonus_id = ? WHERE shop_id = ? RETURNING bonus_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
            kysely.setInt(2, kauppa);
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
    /**
    * Metodilla haetaan sen bonuksen id, johon kauppa kuuluu.
    *
    * @param kauppa kaupan id tietokannassa.
    * @throws Exception
    * @return palauttaa bonuksen id:n.
    */
    public static int haeKaupalleBonus(int kauppa) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT bonus_id FROM shop WHERE shop_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, kauppa);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return tulokset.getInt("bonus_id");
            } else {
                return 0;
            }

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla poistetaan kauppa tietokannasta.
    *
    * @param poistoId poistettavan kaupan id tietokannassa.
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
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
    /**
    * Metodilla lisätään kauppa tietokantaan.
    *
    * @param lisaysnimi lisättävän kaupan nimi.
    * @param lisayskaupunki kaupunki, jossa lisättävä kauppa sijaitsee.
    * @param lisaysosoite lisättävän kaupan osoite.
    * @param lisaysbonus lisättävän kaupan bonus.
    * @throws Exception
    * @return palauttaa true, jos lisäys onnistui.
    */
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
    /**
    * Metodilla tallennetaan Kauppa - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos tallennus onnistui.
    */
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
            kysely.setInt(4, bonus.getId());
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
    /**
    * Metodilla poistetaan Kauppa - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
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
  
    public Bonus getBonus() {
        return this.bonus;
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
  
    public void setBonus(Bonus x) {
        this.bonus = x;
    }
}