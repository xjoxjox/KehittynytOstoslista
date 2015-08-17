package KehittynytOstoslista.Models;

import KehittynytOstoslista.ServLets.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.NamingException;

public class OstoslistaKuitattu {
    private static int id;
    private static String nimi;
    private static HashMap<Tuote, Integer> tuotteet;
    private static double summa;
    private static double paino;
    private static Timestamp paivays;
    private static int kauppaId;
    private static int kayttajaId;
    private static int maksutapaId;
    private static int bonusId;
    
    private OstoslistaKuitattu(ResultSet tulos) throws SQLException, Exception {
        OstoslistaKuitattu o = new OstoslistaKuitattu(
            tulos.getInt("shoppinglist_id"),
            tulos.getString("name"),
            tulos.getDouble("sum"),
            tulos.getDouble("weight"),
            tulos.getTimestamp("time_checked"),
            tulos.getInt("shop_id"),
            tulos.getInt("account_id"),
            tulos.getInt("payment_id"),
            tulos.getInt("bonus_id")
        );
    }

    public OstoslistaKuitattu(int id, String nimi, double summa, double paino, Timestamp paivays, int kauppaId,
        int kayttajaId, int maksutapaId, int bonusId) throws Exception {
        this.id = id;
        this.nimi = nimi;
        this.summa = haeSumma();
        this.paino = haePaino();
        this.paivays = paivays;
        this.kauppaId = kauppaId;
        this.kayttajaId = kayttajaId;
        this.maksutapaId = maksutapaId;
        this.bonusId = bonusId;
        
        this.tuotteet = TuoteLista.haeTuotteetListalle(id);
    }
    
    public static OstoslistaKuitattu haeOstoslistaKuitattu(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM shoppinglistchecked WHERE shoppinglist_id = ? AND account_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            kysely.setInt(2, kayttajaId);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return new OstoslistaKuitattu(tulokset);
            } else {
                return null;
            }

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<OstoslistaKuitattu> haeOstoslistaKuitattuNimella(String hakusana) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaKuitattu> ostoslistat = new ArrayList<OstoslistaKuitattu>();

        try {
            String sql = "SELECT * FROM shoppinglistchecked WHERE name like ? AND account_id = ? ORDER BY time_checked";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, "%" + hakusana + "%");
            kysely.setInt(2, kayttajaId);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaKuitattu o = new OstoslistaKuitattu(tulokset);
                    ostoslistat.add(o);
                }
            } else {
                return null;
            }
            
            return ostoslistat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<OstoslistaKuitattu> haeOstoslistaKuitattuPaivayksellaEnnen(Timestamp hakupaiva) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaKuitattu> ostoslistat = new ArrayList<OstoslistaKuitattu>();

        try {
            String sql = "SELECT * FROM shoppinglistchecked WHERE time_checked < ? AND account_id = ? ORDER BY time_checked";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setTimestamp(1, hakupaiva);
            kysely.setInt(2, kayttajaId);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaKuitattu o = new OstoslistaKuitattu(tulokset);
                    ostoslistat.add(o);
                }
            } else {
                return null;
            }
            
            return ostoslistat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<OstoslistaKuitattu> haeOstoslistaKuitattuPaivayksellaJalkeen(Timestamp hakupaiva) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaKuitattu> ostoslistat = new ArrayList<OstoslistaKuitattu>();

        try {
            String sql = "SELECT * FROM shoppinglistchecked WHERE time_checked > ? AND account_id = ? ORDER BY time_checked";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setTimestamp(1, hakupaiva);
            kysely.setInt(2, kayttajaId);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaKuitattu o = new OstoslistaKuitattu(tulokset);
                    ostoslistat.add(o);
                }
            } else {
                return null;
            }
            
            return ostoslistat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<OstoslistaKuitattu> haeOstoslistaKuitattuKaupalla(int hakukauppa) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaKuitattu> ostoslistat = new ArrayList<OstoslistaKuitattu>();

        try {
            String sql = "SELECT * FROM shoppinglistchecked WHERE shop_id = ? AND account_id = ? ORDER BY time_checked";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, hakukauppa);
            kysely.setInt(2, kayttajaId);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaKuitattu o = new OstoslistaKuitattu(tulokset);
                    ostoslistat.add(o);
                }
            } else {
                return null;
            }
            
            return ostoslistat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<OstoslistaKuitattu> haeKaikkiOstoslistaKuitattu(int hakukayttaja) throws SQLException, NamingException, Exception {
        String sql = "SELECT shoppinglist_id, name, sum, weight, to_char(time_checked, 'YYYY-MM-DD HH24:MI:SS') as time_checked, shop_id FROM shoppinglistchecked WHERE account_id = ? ORDER BY time_checked";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, hakukayttaja);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<OstoslistaKuitattu> ostoslistat = new ArrayList<OstoslistaKuitattu>();
        while (tulokset.next()) {
            OstoslistaKuitattu o = new OstoslistaKuitattu(tulokset.getInt("shoppinglist_id"), tulokset.getString("name"), tulokset.getDouble("sum"),
                tulokset.getDouble("weight"), tulokset.getTimestamp("time_checked"), tulokset.getInt("shop_id"), 
                    tulokset.getInt("account_id"), tulokset.getInt("payment_id"), tulokset.getInt("bonus_id"));
            ostoslistat.add(o);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return ostoslistat;
    }
    
    public static double haeSumma() throws Exception {
        double sum = 0;
        for (Tuote tuote: tuotteet.keySet()) {
            sum += TuoteHinta.haeHintaTuotteelleKaupassa(tuote.getId(), kauppaId);
        }
        return sum;
    }
    
    public static double haePaino() throws NamingException, SQLException {
        double kokPaino = 0;
        for (Tuote tuote : tuotteet.keySet()) {
            kokPaino += tuote.getPaino();
        }     
        return kokPaino;
    }
    
    public void lisaaTuote(int tuoteId) throws Exception {
        TuoteLista t = new TuoteLista(tuoteId, id);
        t.tallenna();
        tuotteet = TuoteLista.haeTuotteetListalle(id);
    }
    
    public void poistaTuote(int tuoteId) throws Exception {
        TuoteLista t = new TuoteLista(tuoteId, id);
        t.poista();
        tuotteet = TuoteLista.haeTuotteetListalle(id);
    }
    
    public boolean muokkaaNimi(String x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistchecked SET name = ? WHERE shoppinglist_id = ? RETURNING name";
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
  
    public boolean muokkaaPaivays(Timestamp x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistchecked SET time_created = ? WHERE shoppinglist_id = ? RETURNING time_checked";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setTimestamp(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.paivays = tulokset.getTimestamp("time_checked");
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
    
    public boolean muokkaaKauppaId(int x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistchecked SET shop_id = ? WHERE shoppinglist_id = ? RETURNING shop_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.kauppaId = tulokset.getInt("shop_id");
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
    
    public boolean muokkaaKayttajaId(int x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistchecked SET account_id = ? WHERE shoppinglist_id = ? RETURNING account_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.kayttajaId = tulokset.getInt("account_id");
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
    
    public boolean muokkaaMaksutapaId(int x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistchecked SET payment_id = ? WHERE shoppinglist_id = ? RETURNING payment_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.maksutapaId = tulokset.getInt("payment_id");
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
    
    public boolean muokkaaBonusId(int x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistchecked SET bonus_id = ? WHERE shoppinglist_id = ? RETURNING bonus_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.bonusId = tulokset.getInt("bonus_id");
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
            String sql = "INSERT INTO shoppinglistchecked(name, sum, weight, time_checked, "
                    + "shop_id, account_id, payment_id, bonus_id) VALUES(?,?,?,?,?,?,?,?) RETURNING shoppinglist_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, nimi);
            kysely.setDouble(2, summa);
            kysely.setDouble(3, paino);
            kysely.setTimestamp(4, paivays);
            kysely.setInt(5, kauppaId);
            kysely.setInt(6, kayttajaId);
            kysely.setInt(7, maksutapaId);
            kysely.setInt(8, bonusId);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                id = tulokset.getInt("shoppinglist_id");
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
            String sql = "DELETE FROM shoppinglistchecked where shoppinglist_id = ?";
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
  
    public HashMap<Tuote, Integer> getTuotteet() {
        return this.tuotteet;
    }
    
    public double getSumma() {
        return this.summa;
    }
  
    public double getPaino() {
        return this.paino;
    }
  
    public Timestamp getPaivays() {
        return this.paivays;
    }
    
    public int getKauppaId() {
        return this.kauppaId;
    }
    
    public int getKayttajaId() {
        return this.kayttajaId;
    }
    
    public int getMaksutapaId() {
        return this.maksutapaId;
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
  
    public void setSumma(double x) {
        this.summa = x;
    }
  
    public void setPaino(double x) {
        this.paino = x;
    }
  
    public void setPaivays(Timestamp x) {
        this.paivays = x;
    }
    
    public void setKauppaId(int x) {
        this.kauppaId = x;
    }
    
    public void setKayttajaId(int x) {
        this.kayttajaId = x;
    }
    
    public void setMaksutapaId(int x) {
        this.maksutapaId = x;
    }
    
    public void setBonusId(int x) {
        this.bonusId = x;
    }
}