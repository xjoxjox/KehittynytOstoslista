package KehittynytOstoslista.Models;

import KehittynytOstoslista.ServLets.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
* Luokka, joka yhdistää Bonus - olioita ja Ostoslista - olioita.
*
* @see KehittynytOstoslista.Models.Bonus
* @see KehittynytOstoslista.Models.OstoslistaTallennettu
* @see KehittynytOstoslista.Models.OstoslistaKuitattu
* @author Johanna
*/

public class BonusOstot {
    private int bonusId;
    private int listaId;
    
    private BonusOstot(ResultSet tulos) throws SQLException {
        BonusOstot b = new BonusOstot(
            tulos.getInt("bonus_id"),
            tulos.getInt("shoppinglist_id")
        );
    }

    public BonusOstot(int bonusId, int listaId) {
        this.bonusId = bonusId;
        this.listaId = listaId;
    }
    /**
    * Metodilla haetaan summa, joka on käytetty ostoksiin kyseisellä bonuksella.
    *
    * @param bonusId bonuksen id tietokannassa.
    * @throws Exception
    * @return palauttaa summan.
    */
    public static double haeBonuksellaSumma(int bonusId) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        double summa = 0;

        try {
            String sql = "SELECT sum FROM shoppinlistchecked WHERE bonus_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, bonusId);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                summa += tulokset.getDouble("sum");
            }
            
            return summa;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan summa, joka on käytetty ostoksiin kyseisellä bonuksella tietyllä ajanjaksolla.
    *
    * @param hakubonusId bonuksen id tietokannassa.
    * @param paivays1 haun alkupäivämäärä.
    * @param paivays2 haun loppupäivämäärä.
    * @throws Exception
    * @return palauttaa summan.
    */
    public static double haeBonuksetAjalla(int hakubonusId, Timestamp paivays1, Timestamp paivays2) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        double summa = 0;

        try {
            String sql = "SELECT sum FROM shoppinglistchecked WHERE bonus_id = ? AND time_checked < ? AND time_checked > ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, hakubonusId);
            kysely.setTimestamp(2, paivays1);
            kysely.setTimestamp(3, paivays2);
            tulokset = kysely.executeQuery();

            while (tulokset.next()) {
                summa += tulokset.getDouble("sum");
            }

            
            return summa;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla tallennetaan BonusOstot - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos tallennus onnistui.
    */
    public boolean tallenna() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO bonusshopped(bonus_id, shoppinglist_id) VALUES(?,?) RETURNING bonus_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, bonusId);
            kysely.setInt(2, listaId);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                bonusId = tulokset.getInt("bonus_id");
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
    * Metodilla poistetaan BonusOstot - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
    public boolean poista() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM bonusshopped where shoppinglist_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, listaId);
            return kysely.execute();
        } finally {
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }

    public int getBonusId() {
        return this.bonusId;
    }
    
    public int getListaId() {
        return this.listaId;
    }

    public void setBonusId(int x) {
        this.bonusId = x;
    }
  
    public void setListaId(int x) {
        this.listaId = x;
    }
}