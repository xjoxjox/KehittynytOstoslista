package KehittynytOstoslista.Models;

import KehittynytOstoslista.ServLets.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TuoteLista {
    private int tuoteId;
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
        
        HashMap<Integer, Integer> tuotteetint = new HashMap<Integer, Integer>();
        HashMap<Tuote, Integer> tuotteet = new HashMap<Tuote, Integer>();

        try {
            String sql = "SELECT product_id FROM productlist WHERE shoppinglist_id = ? ORDER BY product_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, lista);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                int t = tulokset.getInt("product_id");
                if (!tuotteetint.containsKey(t)) {
                    tuotteetint.put(t,1);
                } else {
                    int maara = tuotteetint.get(t);
                    maara++;
                    tuotteetint.put(t, maara);
                }
            }
            
            for(int tuoteid : tuotteetint.keySet()) {
                tuotteet.put(Tuote.haeTuote(tuoteid), tuotteetint.get(tuoteid));
            }
            
            return tuotteet;

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
            String sql = "INSERT INTO productlist(product_id, shoppinglist_id) VALUES(?,?)";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuoteId);
            kysely.setInt(2, listaId);
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
    
    public static boolean lisaaTuoteListalle(int tuote, int lista) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO productlist(product_id, shoppinglist_id) VALUES(?,?) RETURNING productlist_id";
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
    
    public static boolean poistaTuoteListalta(int tuote, int lista) throws Exception {
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
    
    public boolean poista() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM productlist where product_id = ? AND shoppinglist_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuoteId);
            kysely.setInt(2, listaId);
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