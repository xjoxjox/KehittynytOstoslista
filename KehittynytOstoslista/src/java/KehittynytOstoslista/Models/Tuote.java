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
    private int alakategoriaId;
    
    private Tuote(ResultSet tulos) throws SQLException {
        Tuote t = new Tuote(
            tulos.getInt("product_id"),
            tulos.getString("name"),
            tulos.getString("brand"),
            tulos.getDouble("weight"),
            tulos.getInt("subcategory_id")
        );
    }

    public Tuote(int id, String nimi, String valmistaja, double paino, int alakategoriaId) {
        this.id = id;
        this.nimi = nimi;
        this.valmistaja = valmistaja;
        this.paino = paino;
        this.alakategoriaId = alakategoriaId;
    }
    
    public static Tuote haeTuote(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM product WHERE product_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return new Tuote(tulokset);
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
            String sql = "SELECT * FROM product WHERE name like %?% OR brand like %?%";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, hakusana);
            kysely.setString(2, hakusana);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    Tuote t = new Tuote(tulokset);
                    tuotteet.add(t);
                }
            } else {
                return null;
            }
            
            return tuotteet;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<Tuote> haeKaikkiTuotteet() throws SQLException, NamingException {
        String sql = "SELECT product_id, name, brand, weight, subcategory_id from product";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Tuote> tuotteet = new ArrayList<Tuote>();
        while (tulokset.next()) {
            Tuote k = new Tuote(tulokset.getInt("product_id"), tulokset.getString("name"), tulokset.getString("brand"),
                tulokset.getDouble("weight"), tulokset.getInt("subcategory_id"));
            tuotteet.add(k);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return tuotteet;
    }
    
    public boolean tallenna() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO product(name, brand, weight, subgategory_id) VALUES(?,?,?,?) RETURNING id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, nimi);
            kysely.setString(2, valmistaja);
            kysely.setDouble(3, paino);
            kysely.setInt(4, alakategoriaId);
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
            String sql = "DELETE FROM product where id = ?";
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
  
    public int getAlakategoriaId() {
        return this.alakategoriaId;
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
  
    public void setAlakategoriaId(int x) {
        this.alakategoriaId = x;
    }
}