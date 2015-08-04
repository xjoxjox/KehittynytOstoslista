package KehittynytOstoslista.Models;

import KehittynytOstoslista.ServLets.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class Alakategoria {
    private int id;
    private String kuvaus;
    private int kategoriaId;
    
    private Alakategoria(ResultSet tulos) throws SQLException {
        Alakategoria a = new Alakategoria(
            tulos.getInt("subcategory_id"),
            tulos.getString("description"),
            tulos.getInt("category_id")
        );
    }

    public Alakategoria(int id, String kuvaus, int kategoriaId) {
        this.id = id;
        this.kuvaus = kuvaus;
        this.kategoriaId = kategoriaId;
    }
    
    public static Alakategoria haeAlakategoria(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM subcategory WHERE subcategory_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return new Alakategoria(tulokset);
            } else {
                return null;
            }

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<Alakategoria> haeAlakategoriat(String hakusana) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<Alakategoria> alakategoriat = new ArrayList<Alakategoria>();

        try {
            String sql = "SELECT * FROM subcategory WHERE description like %?%";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, hakusana);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                Alakategoria a = new Alakategoria(tulokset);
                alakategoriat.add(a);
            }

            
            return alakategoriat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    
    public static List<Alakategoria> haeKaikkiAlakategoriat() throws SQLException, NamingException {
        String sql = "SELECT subcategory_id, description, category_id from subcategory";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Alakategoria> alakategoriat = new ArrayList<Alakategoria>();
        
        while (tulokset.next()) {
            Alakategoria a = new Alakategoria(tulokset.getInt("subcategory_id"), tulokset.getString("description"), tulokset.getInt("category_id"));
            alakategoriat.add(a);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return alakategoriat;
    }
    
    public boolean tallenna() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO subcategory(description) VALUES(?) RETURNING subcategory_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, kuvaus);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                id = tulokset.getInt("subcategory_id");
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
            String sql = "DELETE FROM subcategory where subcategory_id = ?";
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
  
    public String getKuvaus() {
        return this.kuvaus;
    }
    
    public int getKategoriaId() {
        return this.kategoriaId;
    }

    public void setId(int x) {
        this.id = x;
    }
  
    public void setKuvaus(String x) {
        this.kuvaus = x;
    }
    
    public void setKategoriaId(int x) {
        this.kategoriaId = x;
    }
}
