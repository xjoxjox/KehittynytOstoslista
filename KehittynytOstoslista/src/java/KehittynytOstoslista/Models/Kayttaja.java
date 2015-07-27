package KehittynytOstoslista.Models;
import KehittynytOstoslista.ServLets.Yhteys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class Kayttaja {
    private int id;
    private String tunnus;
    private String salasana;

    public Kayttaja(int id, String tunnus, String salasana) {
        this.id = id;
        this.tunnus = tunnus;
        this.salasana = salasana;
    }

    public int getId() {
        return this.id;
    }
  
    public String getTunnus() {
        return this.tunnus;
    }
  
    public String getSalasana() {
        return this.salasana;
    }
  
    public void setId(int x) {
        this.id = x;
    }
  
    public void setTunnus(String x) {
        this.tunnus = x;
    }
  
    public void setSalasana(String x) {
        this.salasana = x;
    }
  
    public static List<Kayttaja> getKayttajat() throws SQLException, NamingException {
        String sql = "SELECT account_id, username, password from account";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kayttaja> kayttajat = new ArrayList<Kayttaja>();
        while (tulokset.next()) {
            Kayttaja k = new Kayttaja(tulokset.getInt("account_id"), tulokset.getString("username"), tulokset.getString("password"));
            kayttajat.add(k);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return kayttajat;
    }
}