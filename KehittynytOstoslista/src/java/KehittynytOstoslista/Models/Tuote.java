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

    public Tuote(int id, String nimi, String valmistaja, double paino, int alakategoriaId) {
        this.id = id;
        this.nimi = nimi;
        this.valmistaja = valmistaja;
        this.paino = paino;
        this.alakategoriaId = alakategoriaId;
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
  
    public static List<Tuote> getTuotteet() throws SQLException, NamingException {
        String sql = "SELECT id, name, brand, weight, subcategory from kayttajat";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Tuote> tuotteet = new ArrayList<Tuote>();
        while (tulokset.next()) {
            Tuote k = new Tuote(tulokset.getInt("id"), tulokset.getString("name"), tulokset.getString("brand"),
                tulokset.getDouble("weight"), tulokset.getInt("subcategory"));
            tuotteet.add(k);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return tuotteet;
    }
}