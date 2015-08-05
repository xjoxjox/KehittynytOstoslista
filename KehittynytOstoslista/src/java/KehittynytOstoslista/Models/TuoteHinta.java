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

public class TuoteHinta {
    private double hinta;
    private int sijainti;
    private Timestamp paivays;
    private boolean nykyinen;
    private static int tuoteId;
    private int kauppaId;
    
    private TuoteHinta(ResultSet tulos) throws SQLException {
        TuoteHinta t = new TuoteHinta(
            tulos.getDouble("price"),
            tulos.getInt("location"),
            tulos.getTimestamp("productprice_date"),
            tulos.getBoolean("current_price"),
            tulos.getInt("product_id"),
            tulos.getInt("shop_id")
        );
    }

    public TuoteHinta(double hinta, int sijainti, Timestamp paivays, boolean nykyinen, int tuoteId, int kauppaId) {
        this.hinta = hinta;
        this.sijainti = sijainti;
        this.paivays = paivays;
        this.nykyinen = nykyinen;
        this.tuoteId = tuoteId;
        this.kauppaId = kauppaId;
    }
    
    public static HashMap<Integer, Double> haeHintaTuotteelle(int tuote) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        HashMap<Integer, Double> hinnat = new HashMap<Integer,Double>();

        try {
            String sql = "SELECT price, shop_id FROM productprice WHERE product_id = ? AND current_price = TRUE ORDER BY shop_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuote);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                hinnat.put(tulokset.getInt("shop_id"), tulokset.getDouble("price"));
            }
            
            return hinnat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static double haeHintaTuotteelleKaupassa(int tuote, int kauppa) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        double tulos = 0;

        try {
            String sql = "SELECT price FROM productprice WHERE product_id = ? AND shop_id = ?  AND current_price = TRUE";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuote);
            kysely.setInt(2, kauppa);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                tulos = tulokset.getDouble("price");
            }
            
            return tulos;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<Double> haeTuotteenKaikkiHinnat(int tuote) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Double> hinnat = new ArrayList<Double>();

        try {
            String sql = "SELECT price FROM productprice WHERE product_id = ? ORDER BY productprice_date";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuote);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                double tulos = tulokset.getDouble("price");
                hinnat.add(tulos);
            }
            
            return hinnat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<Double> haeTuotteenKaikkiHinnatKaupassa(int tuote, int kauppa) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Double> hinnat = new ArrayList<Double>();

        try {
            String sql = "SELECT price FROM productprice WHERE product_id = ? AND shop_id = ? ORDER BY productprice_date";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuote);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                double tulos = tulokset.getDouble("price");
                hinnat.add(tulos);
            }
            
            return hinnat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public boolean muokkaaHinta(double x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE productprice SET price = ? WHERE product_id = ? AND productprice_date = ? AND shop_id = ? RETURNING price";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setDouble(1, x);
            kysely.setInt(2, tuoteId);
            kysely.setTimestamp(3, paivays);
            kysely.setInt(4, kauppaId);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.hinta = tulokset.getDouble("price");
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
            String sql = "INSERT INTO productprice(price, location, productprice_date, current_price"
                    + "product_id, shop_id) VALUES(?,?,?,?,?,?)";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setDouble(1, hinta);
            kysely.setInt(2, sijainti);
            kysely.setTimestamp(3, paivays);
            kysely.setBoolean(4, nykyinen);
            kysely.setInt(5, tuoteId);
            kysely.setInt(6, kauppaId);
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
    
    public boolean poista() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM productprice where product_id = ? AND productprice_date = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuoteId);
            kysely.setTimestamp(1, paivays);
            return kysely.execute();
        } finally {
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }

    public int getTuoteId() {
        return this.tuoteId;
    }

    public void setTuoteId(int x) {
        this.tuoteId = x;
    }
}