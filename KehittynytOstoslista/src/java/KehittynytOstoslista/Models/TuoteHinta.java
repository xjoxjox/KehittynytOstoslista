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

/**
* Luokalla voidaan hakea, tallentaa ja poistaa Tuotteille hintoja eri Kaupoissa.
* 
* @see KehittynytOstoslista.Models.Tuote
* @see KehittynytOstoslista.Models.Kauppa
* @author Johanna
*/

public class TuoteHinta {
    private double hinta;
    private int sijainti;
    private Timestamp paivays;
    private boolean nykyinen;
    private int tuoteId;
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
    /**
    * Metodilla haetaan tuotteelle hinta kaikissa kaupoissa.
    *
    * @param tuote tuotteen id tietokannassa.
    * @throws Exception
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.Kauppa
    * @return palauttaa tuotteen hinnat HashMapissa, missä avaimena kauppa.
    */
    public static HashMap<Kauppa, Double> haeHintaTuotteelle(int tuote) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        HashMap<Kauppa, Double> hinnat = new HashMap<Kauppa,Double>();

        try {
            String sql = "SELECT price, shop_id FROM productprice WHERE product_id = ? AND current_price = TRUE ORDER BY shop_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuote);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                System.out.println(tulokset.getInt("shop_id"));
                hinnat.put(Kauppa.haeKauppa(tulokset.getInt("shop_id")), tulokset.getDouble("price"));
            }
            
            return hinnat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan tuotteelle hinta määritetyssä kaupoissa.
    *
    * @param tuote tuotteen id tietokannassa.
    * @param kauppa kaupan id tietokannassa.
    * @throws Exception
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.Kauppa
    * @return palauttaa tuotteen voimassa olevan hinnan määritetyssä kaupassa.
    */
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
    /**
    * Metodilla haetaan tuotteen kaikki hinnat.
    *
    * @param tuote tuotteen id tietokannassa.
    * @throws Exception
    * @see KehittynytOstoslista.Models.Tuote
    * @return palauttaa tuotteen kaikki hinnat listana.
    */
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
    /**
    * Metodilla haetaan tuotteen kaikki hinnat (nykyinen ja aiemmat) määritetyssä kaupassa.
    *
    * @param tuote tuotteen id tietokannassa.
    * @param kauppa kaupan id tietokannassa.
    * @throws Exception
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.Kauppa
    * @return palauttaa tuotteen kaikki hinnat määritetyssä kaupassa listana.
    */
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
    /**
    * Metodilla muokataan tuotteen hintaa kaupassa.
    *
    * @param x uusi hinta.
    * @throws SQLException
    * @throws NamingException
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.Kauppa
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public boolean muokkaaHinta(double x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE productprice SET price = ? WHERE product_id = ? AND productprice_date = ? AND "
                    + "shop_id = ? RETURNING price";
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
    /**
    * Metodilla muokataan tuotteen sijainti.
    *
    * @param x uusi sijainti.
    * @throws SQLException
    * @throws NamingException
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.Kauppa
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public boolean muokkaaSijainti(int x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE productprice SET location = ? WHERE product_id = ? AND productprice_date = ? AND "
                    + "shop_id = ? RETURNING location";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
            kysely.setInt(2, tuoteId);
            kysely.setTimestamp(3, paivays);
            kysely.setInt(4, kauppaId);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.sijainti = tulokset.getInt("location");
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
    * Metodilla muokataan tuotteen hinnan päiväys.
    *
    * @param x uusi päiväys.
    * @throws SQLException
    * @throws NamingException
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.Kauppa
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public boolean muokkaaPaivays(Timestamp x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE productprice SET productprice_date = ? WHERE product_id = ? AND "
                    + "productprice_date = ? AND shop_id = ? RETURNING productprice_date";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setTimestamp(1, x);
            kysely.setInt(2, tuoteId);
            kysely.setTimestamp(3, paivays);
            kysely.setInt(4, kauppaId);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.paivays = tulokset.getTimestamp("productprice_date");
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
    * Metodilla muokataan tieto onko tuotteen hinta voimassa nyt.
    *
    * @param x true, jos hinta on tällä hetkellä voimassa. Muuten false.
    * @param tuoteid tuotteen id tietokannassa.
    * @param kauppaid kaupan id tietokannassa.
    * @throws SQLException
    * @throws NamingException
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.Kauppa
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public static boolean muokkaaNykyinen(boolean x, int tuoteid, int kauppaid) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE productprice SET current_price = ? WHERE product_id = ? AND "
                    + "shop_id = ? RETURNING current_price";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setBoolean(1, x);
            kysely.setInt(2, tuoteid);
            kysely.setInt(3, kauppaid);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {       
                return tulokset.getBoolean("current_price");
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
    * Metodilla muokataan kauppaa, jossa tuotteella on jokin hinta.
    *
    * @param x uusi kauppa.
    * @throws SQLException
    * @throws NamingException
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.Kauppa
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public boolean muokkaaKauppa(int x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE productprice SET shop_id = ? WHERE product_id = ? AND productprice_date = ? AND shop_id = ? RETURNING shop_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setDouble(1, x);
            kysely.setInt(2, tuoteId);
            kysely.setTimestamp(3, paivays);
            kysely.setInt(4, kauppaId);
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
    /**
    * Metodilla muokataan lisätään uusi hinta tuotteelle.
    *
    * @param lisayshinta uusi hinta.
    * @param lisayssijainti tuotteen sijainti kaupassa.
    * @param lisaystuoteid tuotteen id tietokannassa.
    * @param lisayskauppaid kaupan id, johon hinta halutaan linkittää tietokannassa.
    * @throws Exception
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.Kauppa
    * @return palauttaa true, jos lisäys onnistui.
    */
    public static boolean lisaaTuoteHinta(double lisayshinta, int lisayssijainti, int lisaystuoteid, int lisayskauppaid) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        if (lisayshinta < 0) {
            return false;
        }
        
        boolean tulos = muokkaaNykyinen(false, lisaystuoteid, lisayskauppaid);

        try {
            String sql = "INSERT INTO productprice(price, location, productprice_date, current_price,"
                    + " product_id, shop_id) VALUES(?,?,now(),TRUE,?,?) RETURNING price";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setDouble(1, lisayshinta);
            kysely.setInt(2, lisayssijainti);
            kysely.setInt(3, lisaystuoteid);
            kysely.setInt(4, lisayskauppaid);
            tulokset = kysely.executeQuery();

            return tulokset.next();
             
        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla tallennetaan TuoteHinta - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos tallennus onnistui.
    */
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
    /**
    * Metodilla poistetaan tuotteen hinta id:n perusteella.
    *
    * @param id tuotteen id tietokannassa.
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
    public static boolean poistaTuoteHinta(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM productprice where product_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            return kysely.execute();
        } finally {
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla poistetaan TuoteHinta - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
    public boolean poista() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM productprice where product_id = ? AND productprice_date = ? AND shop_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuoteId);
            kysely.setTimestamp(2, paivays);
            kysely.setInt(3, kauppaId);
            return kysely.execute();
        } finally {
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }

    public double getHinta() {
        return this.hinta;
    }
    
    public int getSijainti() {
        return this.sijainti;
    }
    
    public Timestamp getPaivays() {
        return this.paivays;
    }
    
    public boolean getNykyinen() {
        return this.nykyinen;
    }
    
    public int getTuoteId() {
        return this.tuoteId;
    }
    
    public int getKauppaId() {
        return this.kauppaId;
    }
    
    public void setHinta(double x) {
        this.hinta = x;
    }
    
    public void setSijainti(int x) {
        this.sijainti = x;
    }

    public void setPaivays(Timestamp x) {
        this.paivays= x;
    }
    
    public void setNykyinen(boolean x) {
        this.nykyinen = x;
    }
    public void setTuoteId(int x) {
        this.tuoteId = x;
    }
    
    public void setKauppaId(int x) {
        this.kauppaId = x;
    }
}