package KehittynytOstoslista.Models;

import KehittynytOstoslista.ServLets.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TuoteKategoria {
    private static int tuoteId;
    private static int alakategoriaId;
    
    private TuoteKategoria(ResultSet tulos) throws SQLException {
        TuoteLista t = new TuoteLista(
            tulos.getInt("product_id"),
            tulos.getInt("subcategory_id")
        );
    }

    public TuoteKategoria(int tuoteId, int alakategoriaId) {
        this.tuoteId = tuoteId;
        this.alakategoriaId = alakategoriaId;
    }
    
    public static List<Alakategoria> haeTuotteenAlakategoriat(int tuote) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Alakategoria> alakategoriat = new ArrayList<Alakategoria>();

        try {
            String sql = "SELECT subcategory_id, description FROM subcategory WHERE subcategory_id = "
                    + "(SELECT subcategory_id FROM productcategory WHERE product_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuote);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                Alakategoria a = new Alakategoria(tulokset.getInt("subcategory_id"), tulokset.getString("description"));
                alakategoriat.add(a);
            }
            
            return alakategoriat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<Tuote> haeAlakategorianTuotteet(int alakategoria) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Tuote> tuotteet = new ArrayList<Tuote>();

        try {
            String sql = "SELECT product_id, name, brand, weight FROM product WHERE product_id = "
                    + "(SELECT product_id FROM productcategory WHERE subcategory_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, alakategoria);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                Tuote t = new Tuote(tulokset.getInt("product_id"), tulokset.getString("name"), tulokset.getString("brand"), tulokset.getDouble("weight"));
                tuotteet.add(t);
            }
            
            return tuotteet;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static boolean tallenna() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO productcategory(product_id, shoppinglist_id) VALUES(?,?)";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuoteId);
            kysely.setInt(2, alakategoriaId);
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
    
    public static boolean poista() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM productcategory where product_id = ? AND subcategory_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuoteId);
            kysely.setInt(2, alakategoriaId);
            return kysely.execute();
        } finally {
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }

    public int getTuoteId() {
        return this.tuoteId;
    }
    
    public int getAlakategoriaId() {
        return this.alakategoriaId;
    }

    public void setTuoteId(int x) {
        this.tuoteId = x;
    }
  
    public void setAlakategoriaId(int x) {
        this.alakategoriaId = x;
    }
}