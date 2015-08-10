package KehittynytOstoslista.Models;

import KehittynytOstoslista.ServLets.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class Tuote {
    private int id;
    private String nimi;
    private String valmistaja;
    private double paino;
    
    private Tuote(ResultSet tulos) throws SQLException {
        Tuote t = new Tuote(
            tulos.getInt("product_id"),
            tulos.getString("name"),
            tulos.getString("brand"),
            tulos.getDouble("weight")
        );
    }

    public Tuote(int id, String nimi, String valmistaja, double paino) {
        this.id = id;
        this.nimi = nimi;
        this.valmistaja = valmistaja;
        this.paino = paino;
    }
    
    public static Tuote haeTuote(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT product_id, name, brand, weight FROM product WHERE product_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                Tuote t = new Tuote(tulokset);
                t.setId(tulokset.getInt("product_id"));
                t.setNimi(tulokset.getString("name"));
                t.setValmistaja(tulokset.getString("brand"));
                t.setPaino(tulokset.getDouble("weight"));
                return t;
            } else {
                return null;
            }

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<Tuote> haeTuotteet(String hakusana) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Tuote> tuotteet = new ArrayList<Tuote>();

        try {
            String sql = "SELECT product_id, name, brand, weight"
                    + " FROM product WHERE name LIKE ? OR brand LIKE ? ORDER BY name";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, "%" + hakusana + "%");
            kysely.setString(2, "%" + hakusana + "%");
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                Tuote t = new Tuote(tulokset);
                t.setId(tulokset.getInt("product_id"));
                t.setNimi(tulokset.getString("name"));
                t.setValmistaja(tulokset.getString("brand"));
                t.setPaino(tulokset.getDouble("weight"));
                tuotteet.add(t);
            }
            
            return tuotteet;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<Tuote> haeKaikkiTuotteet() throws SQLException, NamingException {
        String sql = "SELECT product_id, name, brand, weight FROM product ORDER by name";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        List<Tuote> tuotteet = new ArrayList<Tuote>();
        
        while (tulokset.next()) {
            Tuote t = new Tuote(tulokset);
            t.setId(tulokset.getInt("product_id"));
            t.setNimi(tulokset.getString("name"));
            t.setValmistaja(tulokset.getString("brand"));
            t.setPaino(tulokset.getDouble("weight"));
            tuotteet.add(t);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return tuotteet;
    }
    
    public boolean muokkaaNimi(String x) throws SQLException, NamingException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE product SET name = ? WHERE product_id = ? RETURNING name";
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
  
    public boolean muokkaaValmistaja(String x) throws SQLException, NamingException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE product SET brand = ? WHERE product_id = ? RETURNING brand";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.valmistaja = tulokset.getString("brand");
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
  
    public boolean muokkaaPaino(double x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE product SET weight = ? WHERE product_id = ? RETURNING weight";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setDouble(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.paino = tulokset.getDouble("weight");
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
    
    public static boolean lisaaTuote(String lisaysnimi, String lisaysvalmistaja, double lisayspaino) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        if(lisaysnimi.length() > 50 || lisaysnimi.length() < 0 || lisaysnimi.equals("")) {
            return false;
        }       
        if(lisaysvalmistaja.length() > 50 || lisaysvalmistaja.length() < 0 || lisaysvalmistaja.equals("")) {
            return false;
        }
        if(lisayspaino < 0) {
            return false;
        }

        try {
            String sql = "INSERT INTO product(name, brand, weight) VALUES(?,?,?) RETURNING product_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, lisaysnimi);
            kysely.setString(2, lisaysvalmistaja);
            kysely.setDouble(3, lisayspaino);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                Tuote t = haeTuote(tulokset.getInt("product_id"));
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
    
    public static boolean poistaTuote(int poistoId) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM product where product_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, poistoId);
            return kysely.execute();
        } finally {
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public boolean tallenna() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO product(name, brand, weight) VALUES(?,?,?) RETURNING product_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, nimi);
            kysely.setString(2, valmistaja);
            kysely.setDouble(3, paino);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                id = tulokset.getInt("product_id");
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
            String sql = "DELETE FROM product where product_id = ?";
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
  
    public String getValmistaja() {
        return this.valmistaja;
    }
  
    public double getPaino() {
       return this.paino;
    }
  
    public void setId(int x) {
        this.id = x;
    }
    
    public void setNimi(String x) {
        this.nimi = x;
    }
    
    public void setValmistaja(String x) {
        this.valmistaja = x;
    }
    
    public void setPaino(double x) {
        this.paino = x;
    }
}