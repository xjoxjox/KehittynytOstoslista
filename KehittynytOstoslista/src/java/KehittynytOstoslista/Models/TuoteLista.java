package KehittynytOstoslista.Models;

import KehittynytOstoslista.ServLets.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TuoteLista {
    private static int tuoteId;
    private int listaId;
    
    private TuoteLista(ResultSet tulos) throws SQLException {
        TuoteLista t = new TuoteLista(
            tulos.getInt("product_id"),
            tulos.getInt("shoppinglist_id")
        );
    }

    public TuoteLista(int tuoteId, int listaId) {
        this.tuoteId = tuoteId;
        this.listaId = listaId;
    }
    
    public static HashMap<Tuote, Integer> haeTuotteetListalle(int lista) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        HashMap<Tuote, Integer> tuotteet = new HashMap<Tuote, Integer>();

        try {
            String sql = "SELECT product_id, name, brand, weight FROM product WHERE product_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuoteId);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                Tuote t = new Tuote(tulokset.getInt("product_id"), tulokset.getString("name"), tulokset.getString("brand"),
                    tulokset.getDouble("weight"));
                if (!tuotteet.containsKey(t)) {
                    tuotteet.put(t,1);
                } else {
                    int maara = tuotteet.get(t);
                    maara++;
                    tuotteet.put(t, maara);
                }
            }
            
            return tuotteet;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static boolean lisaaTuote(int tuote, int lista) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO productlist(product_id, shoppinglist_id) VALUES(?,?)";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuote);
            kysely.setInt(2, lista);
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
    
    public static boolean poistaTuote(int tuote, int lista) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM productlist where product_id = ? AND shoppinglist_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuote);
            kysely.setInt(2, lista);
            return kysely.execute();
        } finally {
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }

    public int getTuoteId() {
        return this.tuoteId;
    }
    
    public int getListaId() {
        return this.listaId;
    }

    public void setTuoteId(int x) {
        this.tuoteId = x;
    }
  
    public void setListaId(int x) {
        this.listaId = x;
    }
}