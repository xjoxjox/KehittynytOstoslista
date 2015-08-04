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

public class OstoslistaTallennettu {
    private static int id;
    private String nimi;
    private static HashMap<Tuote, Integer> tuotteet;
    private double summa;
    private double paino;
    private Timestamp paivays;
    private int kauppaId;
    private int kayttajaId;
    
    private OstoslistaTallennettu(ResultSet tulos) throws SQLException, Exception {
        OstoslistaTallennettu o = new OstoslistaTallennettu(
            tulos.getInt("shoppinglist_id"),
            tulos.getString("name"),
            tulos.getDouble("sum"),
            tulos.getDouble("weight"),
            tulos.getTimestamp("time_created"),
            tulos.getInt("shop_id"),
            tulos.getInt("account_id")
        );
    }

    public OstoslistaTallennettu(int id, String nimi, double summa, double paino, Timestamp paivays, int kauppaId, int kayttajaId) throws Exception {
        this.id = id;
        this.nimi = nimi;
        this.summa = haeSumma();
        this.paino = haePaino();
        this.paivays = paivays;
        this.kauppaId = kauppaId;
        this.kayttajaId = kayttajaId;
        
        this.tuotteet = new HashMap<Tuote, Integer>();
    }
    
    public static OstoslistaTallennettu haeOstoslistaTallennettu(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM shoppinglistsaved WHERE shoppinglist_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return new OstoslistaTallennettu(tulokset);
            } else {
                return null;
            }

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<OstoslistaTallennettu> haeOstoslistaTallennettuNimella(String hakusana) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaTallennettu> ostoslistat = new ArrayList<OstoslistaTallennettu>();

        try {
            String sql = "SELECT * FROM shoppinglistsaved WHERE name like %?%";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, hakusana);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaTallennettu o = new OstoslistaTallennettu(tulokset);
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
    
    public static List<OstoslistaTallennettu> haeOstoslistaTallennettuPaivayksellaEnnen(Timestamp hakupaiva) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaTallennettu> ostoslistat = new ArrayList<OstoslistaTallennettu>();

        try {
            String sql = "SELECT * FROM shoppinglistsaved WHERE time_created < %?%";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setTimestamp(1, hakupaiva);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaTallennettu o = new OstoslistaTallennettu(tulokset);
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
    
    public static List<OstoslistaTallennettu> haeOstoslistaTallennettuPaivayksellaJalkeen(Timestamp hakupaiva) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaTallennettu> ostoslistat = new ArrayList<OstoslistaTallennettu>();

        try {
            String sql = "SELECT * FROM shoppinglistsaved WHERE time_created > %?%";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setTimestamp(1, hakupaiva);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaTallennettu o = new OstoslistaTallennettu(tulokset);
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
    
    public static List<OstoslistaTallennettu> haeOstoslistaTallennettuKaupalla(int hakukauppa) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaTallennettu> ostoslistat = new ArrayList<OstoslistaTallennettu>();

        try {
            String sql = "SELECT * FROM shoppinglistsaved WHERE shop_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, hakukauppa);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaTallennettu o = new OstoslistaTallennettu(tulokset);
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
    
    public static List<OstoslistaTallennettu> haeKaikkiOstoslistaTallennettu(int hakukayttaja) throws SQLException, NamingException, Exception {
        String sql = "SELECT shoppinglist_id, name, sum, weight, time_created, shop_id from shoppinglistsaved WHERE account_id = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, hakukayttaja);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<OstoslistaTallennettu> ostoslistat = new ArrayList<OstoslistaTallennettu>();
        while (tulokset.next()) {
            OstoslistaTallennettu o = new OstoslistaTallennettu(tulokset.getInt("shoppinglist_id"), tulokset.getString("name"), tulokset.getDouble("sum"),
                tulokset.getDouble("weight"), tulokset.getTimestamp("time_created"), tulokset.getInt("shop_id"), tulokset.getInt("account_id"));
            ostoslistat.add(o);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return ostoslistat;
    }
    
    public static double haeSumma() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        double sum = 0;

        try {
            for (Tuote tuote: tuotteet.keySet()) {
                String sql = "SELECT price FROM productprice WHERE product_id like = ? AND current_price = TRUE";
                yhteys = Yhteys.getYhteys();
                kysely = yhteys.prepareStatement(sql);
                kysely.setInt(1, tuote.getId());
                tulokset = kysely.executeQuery();

                while (tulokset.next()) {
                    sum += tulokset.getDouble("price");
                }
            }
            
            return sum;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static double haePaino() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        double kokPaino = 0;

        try {
            for (Tuote tuote: tuotteet.keySet()) {
                String sql = "SELECT weight FROM product WHERE product_id like = ?";
                yhteys = Yhteys.getYhteys();
                kysely = yhteys.prepareStatement(sql);
                kysely.setInt(1, tuote.getId());
                tulokset = kysely.executeQuery();

                while (tulokset.next()) {
                    kokPaino += tulokset.getDouble("weight");
                }
            }
            
            return kokPaino;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public boolean lisaaTuote(int tuoteId) throws Exception {
        boolean lisays = TuoteLista.lisaaTuote(tuoteId, id);
        tuotteet = TuoteLista.haeTuotteetListalle(id);
        return lisays;
    }
    
    public boolean poistaTuote(int tuoteId) throws Exception {
        boolean poisto = TuoteLista.poistaTuote(tuoteId, id);
        tuotteet = TuoteLista.haeTuotteetListalle(id);
        return poisto;
    }
    
    public boolean tallenna() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO shoppinglistsaved(name, sum, weight, time_created, shop_id, account_id) "
                    + "VALUES(?,?,?,?,?,?) RETURNING shoppinglist_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, nimi);
            kysely.setDouble(2, summa);
            kysely.setDouble(3, paino);
            kysely.setTimestamp(4, paivays);
            kysely.setInt(5, kauppaId);
            kysely.setInt(6, kayttajaId);
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
            String sql = "DELETE FROM shoppinglistsaved where shoppinglist_id = ?";
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
}