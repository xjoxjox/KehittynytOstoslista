package KehittynytOstoslista.Models;

import KehittynytOstoslista.ServLets.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
* Luokassa voidaan hakea ja poistaa Tuotteita, joita on Ostoslistoilla.
* 
* @see KehittynytOstoslista.Models.Tuote
* @see KehittynytOstoslista.Models.OstoslistaTallennettu
* @author Johanna
*/

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
    /**
    * Metodilla haetaan ostoslistan kaikki tuotteet.
    *
    * @param lista ostoslistan id tietokannassa.
    * @throws Exception
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.OstoslistaTallennettu
    * @return palauttaa Tuotteet ja niiden määrät HashMapissa, jossa avaimena tuote.
    */
    public static HashMap<Tuote, Integer> haeTuotteetListalle(int lista) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        HashMap<Tuote, Integer> tuotteet = new HashMap<Tuote, Integer>();

        try {
            String sql = "SELECT product_id, COUNT(shoppinglist_id) as count FROM productlist WHERE shoppinglist_id = ? "
                    + "GROUP BY product_id ORDER BY product_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, lista);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                int t = tulokset.getInt("product_id");
                int maara = tulokset.getInt("count");
                Tuote tuote = Tuote.haeTuote(t);
                tuotteet.put(tuote, maara);
            }
            
            return tuotteet;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla tarkustetaan onko tuote jollakin ostoslistalla.
    *
    * @param tuote tuotteen id tietokannassa.
    * @throws Exception
    * @return palauttaa true, jos tuote löytyy joltakin ostoslistalta.
    */
    public static boolean onkoTuoteListalla(int tuote) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT shoppinglist_id FROM productlist WHERE product_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, tuote);
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
    * Metodilla tallennetaan TuoteLista - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos tallennus onnistui.
    */
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
    /**
    * Metodilla lisätään tuote ostoslistalle.
    *
    * @param tuote tuotteen id tietokannassa.
    * @param lista ostoslistan id tietokannassa.
    * @throws Exception
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.OstoslistaTallennettu
    * @return palauttaa true, jos lisäys onnistui.
    */
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
    /**
    * Metodilla poistetaan tuote ostoslistalta.
    *
    * @param tuote tuotteen id tietokannassa.
    * @param lista ostoslistan id tietokannassa.
    * @throws Exception
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.OstoslistaTallennettu
    * @return palauttaa true, jos poisto onnistui.
    */
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
    /**
    * Metodilla poistetaan ostoslistan tuotteet.
    *
    * @param lista ostoslistan id tietokannassa.
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
    public static boolean poistaLista(int lista) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM productlist where shoppinglist_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, lista);
            return kysely.execute();
        } finally {
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla poistetaan TuoteLista - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
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