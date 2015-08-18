package KehittynytOstoslista.Models;

import KehittynytOstoslista.ServLets.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.NamingException;

/**
* @author Johanna
*/

public class OstoslistaKuitattu {
    private int id;
    private int listaid;
    private String nimi;
    private HashMap<Tuote, Integer> tuotteet;
    private double summa;
    private double paino;
    private Timestamp paivays;
    private int kauppaId;
    private int kayttajaId;
    private int maksutapaId;
    private int bonusId;
    
    private OstoslistaKuitattu(ResultSet tulos) throws SQLException, Exception {
        OstoslistaKuitattu o = new OstoslistaKuitattu(
            tulos.getInt("shoppinglistchecked_id"),
            tulos.getInt("shoppinglist_id"),
            tulos.getString("name"),
            tulos.getDouble("sum"),
            tulos.getDouble("weight"),
            tulos.getTimestamp("time_checked"),
            tulos.getInt("shop_id"),
            tulos.getInt("account_id"),
            tulos.getInt("payment_id"),
            tulos.getInt("bonus_id")
        );
    }

    public OstoslistaKuitattu(int id, int listaid, String nimi, double summa, double paino, Timestamp paivays, int kauppaId,
        int kayttajaId, int maksutapaId, int bonusId) throws Exception {
        this.id = id;
        this.listaid = listaid;
        this.nimi = nimi;
        this.paivays = paivays;
        this.kauppaId = kauppaId;
        this.kayttajaId = kayttajaId;
        this.maksutapaId = maksutapaId;
        this.bonusId = bonusId;
        
        this.tuotteet = TuoteLista.haeTuotteetListalle(id);
    }
    /**
    * Metodilla haetaan ostoslista sen id:llä.
    *
    * @param id ostoslistan id tietokannassa.
    * @throws Exception
    * @return palauttaa id:tä vastaavan ostoslistan.
    */
    public static OstoslistaKuitattu haeOstoslistaKuitattu(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM shoppinglistchecked WHERE shoppinglistchecked_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return new OstoslistaKuitattu(tulokset);
            } else {
                return null;
            }

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan ostoslistoja hakusanalla.
    *
    * @param hakusana hakusana jolla ostoslistoja haetaan tietokannasta.
    * @param kayttaja ostoslistan luoneen käyttäjän id tietokannassa.
    * @throws Exception
    * @return palauttaa listan ostoslistoista, jotka löytyvät hakusanalla.
    */
    public static List<OstoslistaKuitattu> haeOstoslistaKuitattuNimella(String hakusana, int kayttaja) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaKuitattu> ostoslistat = new ArrayList<OstoslistaKuitattu>();

        try {
            String sql = "SELECT * FROM shoppinglistchecked WHERE name like ? AND account_id = ? ORDER BY time_checked";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, "%" + hakusana + "%");
            kysely.setInt(2, kayttaja);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaKuitattu o = new OstoslistaKuitattu(tulokset);
                    ostoslistat.add(o);
                }
            } else {
                return null;
            }
            
            return ostoslistat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan ostoslistoja, jotka on luotu annettua päiväystä aiemmin.
    *
    * @param hakupaiva päiväys, jota ennen luodut ostoslistat halutaan hakea.
    * @param kayttaja ostoslistan luoneen käyttäjän id tietokannassa.
    * @throws Exception
    * @return palauttaa listan ostoslistoista, jotka löytyvät hakuehdoilla.
    */
    public static List<OstoslistaKuitattu> haeOstoslistaKuitattuPaivayksellaEnnen(Timestamp hakupaiva, int kayttaja) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaKuitattu> ostoslistat = new ArrayList<OstoslistaKuitattu>();

        try {
            String sql = "SELECT * FROM shoppinglistchecked WHERE time_checked < ? AND account_id = ? ORDER BY time_checked";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setTimestamp(1, hakupaiva);
            kysely.setInt(2, kayttaja);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaKuitattu o = new OstoslistaKuitattu(tulokset);
                    ostoslistat.add(o);
                }
            } else {
                return null;
            }
            
            return ostoslistat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan ostoslistoja, jotka on luotu annetun päiväyksen jälkeen.
    *
    * @param hakupaiva päiväys, jonka jälkeen luodut ostoslistat halutaan hakea.
    * @param kayttaja ostoslistan luoneen käyttäjän id tietokannassa.
    * @throws Exception
    * @return palauttaa listan ostoslistoista, jotka löytyvät hakuehdoilla.
    */
    public static List<OstoslistaKuitattu> haeOstoslistaKuitattuPaivayksellaJalkeen(Timestamp hakupaiva, int kayttaja) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaKuitattu> ostoslistat = new ArrayList<OstoslistaKuitattu>();

        try {
            String sql = "SELECT * FROM shoppinglistchecked WHERE time_checked > ? AND account_id = ? ORDER BY time_checked";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setTimestamp(1, hakupaiva);
            kysely.setInt(2, kayttaja);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaKuitattu o = new OstoslistaKuitattu(tulokset);
                    ostoslistat.add(o);
                }
            } else {
                return null;
            }
            
            return ostoslistat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan ostoslistoja, jonka ostokset on tehty annetussa kaupassa.
    *
    * @param hakukauppa kauppa, jossa ostokset on tehty.
    * @param kayttaja ostoslistan luoneen käyttäjän id tietokannassa.
    * @throws Exception
    * @return palauttaa listan ostoslistoista, jotka löytyvät hakuehdoilla.
    */
    public static List<OstoslistaKuitattu> haeOstoslistaKuitattuKaupalla(int hakukauppa, int kayttaja) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaKuitattu> ostoslistat = new ArrayList<OstoslistaKuitattu>();

        try {
            String sql = "SELECT * FROM shoppinglistchecked WHERE shop_id = ? AND account_id = ? ORDER BY time_checked";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, hakukauppa);
            kysely.setInt(2, kayttaja);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaKuitattu o = new OstoslistaKuitattu(tulokset);
                    ostoslistat.add(o);
                }
            } else {
                return null;
            }
            
            return ostoslistat;

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan kaikki käyttäjän ostoslistat.
    *
    * @param hakukayttaja käyttäjän id tietokannassa.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa listana käyttäjän kaikki ostoslistat.
    */
    public static List<OstoslistaKuitattu> haeKaikkiOstoslistaKuitattu(int hakukayttaja) throws SQLException, NamingException, Exception {
        String sql = "SELECT shoppinglistchecked_id, shoppinglist_id, name, sum, weight, to_char(time_checked, 'YYYY-MM-DD HH24:MI:SS') as time_checked, shop_id FROM shoppinglistchecked WHERE account_id = ? ORDER BY time_checked";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, hakukayttaja);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<OstoslistaKuitattu> ostoslistat = new ArrayList<OstoslistaKuitattu>();
        while (tulokset.next()) {
            OstoslistaKuitattu o = new OstoslistaKuitattu(tulokset.getInt("shoppinglistchecked_id"), tulokset.getInt("shoppinglist_id"), tulokset.getString("name"), tulokset.getDouble("sum"),
                tulokset.getDouble("weight"), tulokset.getTimestamp("time_checked"), tulokset.getInt("shop_id"), 
                    tulokset.getInt("account_id"), tulokset.getInt("payment_id"), tulokset.getInt("bonus_id"));
            ostoslistat.add(o);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return ostoslistat;
    }
    /**
    * Metodilla haetaan ostoslistalle sen pohjana olevan ostoslistan id.
    *
    * @param id ostoslistan id tietokannassa.
    * @throws Exception
    * @see KehittynytOstoslista.Models.OstoslistaTallennettu
    * @return palauttaa pohjana olevan ostoslistan id:n.
    */
    public static int haeOstoslistalleOstoslistaTallennettuId(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT shoppinglist_id FROM shoppinglistchecked WHERE shoppinglistchecked_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return tulokset.getInt("shoppinglist_id");
            } else {
                return 0;
            }

        } finally {
            try { tulokset.close(); } catch (Exception e) {  }
            try { kysely.close(); } catch (Exception e) {  }
            try { yhteys.close(); } catch (Exception e) {  }
        }
    }
    /**
    * Metodilla haetaan ostoslistalla olevien tuotteiden kokonaissumma.
    *
    * @param listaid ostoslistan id tietokannassa.
    * @param kauppaid ostoslistaan linkitetyn kaupan id tietokannassa.
    * @throws Exception
    * @return palauttaa summan.
    */
    public static double haeSumma(int listaid, int kauppaid) throws Exception {
        double sum = 0;
        HashMap<Tuote, Integer> tuotteet = TuoteLista.haeTuotteetListalle(listaid);
        for (Tuote tuote: tuotteet.keySet()) {
            for(int i = 0; i < tuotteet.get(tuote); i++) {
                sum += TuoteHinta.haeHintaTuotteelleKaupassa(tuote.getId(), kauppaid);
            }
        }
        return sum;
    }
    /**
    * Metodilla haetaan ostoslistalla olevien tuotteiden kokonaispaino.
    *
    * @param listaid ostoslistan id tietokannassa.
    * @throws SQLException
    * @throws NamingException
    * @throws Exception
    * @return palauttaa painon.
    */
    public static double haePaino(int listaid) throws NamingException, SQLException, Exception {
        double kokPaino = 0;
        HashMap<Tuote, Integer> tuotteet = TuoteLista.haeTuotteetListalle(listaid);
        for (Tuote tuote : tuotteet.keySet()) {
            for(int i = 0; i < tuotteet.get(tuote); i++) {
                kokPaino += tuote.getPaino();
            }
        }     
        return kokPaino;
    }
    /**
    * Metodilla muokataan ostoslistan nimi.
    *
    * @param x uusi nimi.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public boolean muokkaaNimi(String x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistchecked SET name = ? WHERE shoppinglistchecked_id = ? RETURNING name";
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
    /**
    * Metodilla muokataan ostoslistan päiväys.
    *
    * @param x uusi päiväys.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public boolean muokkaaPaivays(Timestamp x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistchecked SET time_created = ? WHERE shoppinglistchecked_id = ? RETURNING time_checked";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setTimestamp(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.paivays = tulokset.getTimestamp("time_checked");
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
    * Metodilla muokataan ostoslistaan linkitetty Kauppa.
    *
    * @param x uuden kaupan id.
    * @throws SQLException
    * @throws NamingException
    * @see KehittynytOstoslista.Models.Kauppa
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public boolean muokkaaKauppaId(int x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinglistchecked SET shop_id = ? WHERE shoppinglistchecked_id = ? RETURNING shop_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.kauppaId = tulokset.getInt("shop_id");
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
    * Metodilla muokataan ostoslistaan linkitetty Kayttaja.
    *
    * @param x uuden käyttäjän id.
    * @throws SQLException
    * @throws NamingException
    * @see KehittynytOstoslista.Models.Kayttaja
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public boolean muokkaaKayttajaId(int x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistchecked SET account_id = ? WHERE shoppinglistchecked_id = ? RETURNING account_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.kayttajaId = tulokset.getInt("account_id");
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
    * Metodilla muokataan ostoslistaan linkitetty maksutapa.
    *
    * @param x uuden maksutavan id.
    * @throws SQLException
    * @throws NamingException
    * @see KehittynytOstoslista.Models.Maksutapa
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public boolean muokkaaMaksutapaId(int x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistchecked SET payment_id = ? WHERE shoppinglistchecked_id = ? RETURNING payment_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.maksutapaId = tulokset.getInt("payment_id");
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
    * Metodilla muokataan ostoslistaan linkitetty Bonus.
    *
    * @param x uuden bonuksen id.
    * @throws SQLException
    * @throws NamingException
    * @see KehittynytOstoslista.Models.Bonus
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public boolean muokkaaBonusId(int x) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistchecked SET bonus_id = ? WHERE shoppinglistchecked_id = ? RETURNING bonus_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
            kysely.setInt(2, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                this.bonusId = tulokset.getInt("bonus_id");
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
    * Metodilla luodaan uusi Ostoslista tietokantaan.
    *
    * @param listanimi ostoslistan nimi.
    * @param lista kuitattavan tallennetun ostoslistan id tietokannassa.
    * @param listakauppa kauppa, johon uusi lista linkitetään.
    * @param listakayttaja käyttäjän id tietokannassa.
    * @param listamaksutapa käytetyn maksutavan id tietokannassa.
    * @param listabonus käytetyn bonuksen id tietokannassa.
    * @throws Exception
    * @see KehittynytOstoslista.Models.OstoslistaTallennettu
    * @see KehittynytOstoslista.Models.Kauppa
    * @see KehittynytOstoslista.Models.Kayttaja
    * @see KehittynytOstoslista.Models.Maksutapa
    * @see KehittynytOstoslista.Models.Bonus
    * @return palauttaa true, jos luonti onnistui.
    */
    public static boolean luoUusiLista(String listanimi, int lista, int listamaksutapa, int listakauppa, 
            int listakayttaja, int listabonus) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        double listasumma = OstoslistaTallennettu.haeSumma(lista, listakauppa);
        double listapaino = OstoslistaTallennettu.haePaino(lista);
        
        OstoslistaTallennettu.muokkaaKuitattu(true, lista);

        try {
            String sql = "INSERT INTO shoppinglistchecked(shoppinglist_id, name, sum, weight, time_checked, "
                    + "shoppinglist_id, shop_id, account_id, payment_id, bonus_id) VALUES(?,?,?,?,now(),?,?,?,?,?) RETURNING shoppinglistchecked_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, lista);
            kysely.setString(2, listanimi);
            kysely.setDouble(3, listasumma);
            kysely.setDouble(4, listapaino);
            kysely.setInt(5, lista);
            kysely.setInt(6, listakauppa);
            kysely.setInt(7, listakayttaja);
            kysely.setInt(8, listamaksutapa);
            kysely.setInt(9, listabonus);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                int listaid = tulokset.getInt("shoppinglistchecked_id");
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
    * Metodilla tallennetaan Ostoslista - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos tallennus onnistui.
    */
    public boolean tallenna() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "INSERT INTO shoppinglistchecked(shoppinglist_id, name, sum, weight, time_checked, "
                    + "shop_id, account_id, payment_id, bonus_id) VALUES(?,?,?,?,?,?,?,?,?) RETURNING shoppinglistchecked_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, listaid);
            kysely.setString(2, nimi);
            kysely.setDouble(3, summa);
            kysely.setDouble(4, paino);
            kysely.setTimestamp(5, paivays);
            kysely.setInt(6, kauppaId);
            kysely.setInt(7, kayttajaId);
            kysely.setInt(8, maksutapaId);
            kysely.setInt(9, bonusId);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                id = tulokset.getInt("shoppinglistchecked_id");
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
    * Metodilla poistetaan Ostoslista - olio.
    *
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
    public boolean poista() throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM shoppinglistchecked where shoppinglistchecked_id = ?";
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
  
    public HashMap<Tuote, Integer> getTuotteet() {
        return this.tuotteet;
    }
    
    public double getSumma() {
        return this.summa;
    }
  
    public double getPaino() {
        return this.paino;
    }
  
    public Timestamp getPaivays() {
        return this.paivays;
    }
    
    public int getKauppaId() {
        return this.kauppaId;
    }
    
    public int getKayttajaId() {
        return this.kayttajaId;
    }
    
    public int getMaksutapaId() {
        return this.maksutapaId;
    }
    
    public int getBonusId() {
        return this.bonusId;
    }
  
    public void setId(int x) {
        this.id = x;
    }
  
    public void setNimi(String x) {
        this.nimi = x;
    }
  
    public void setSumma(double x) {
        this.summa = x;
    }
  
    public void setPaino(double x) {
        this.paino = x;
    }
  
    public void setPaivays(Timestamp x) {
        this.paivays = x;
    }
    
    public void setKauppaId(int x) {
        this.kauppaId = x;
    }
    
    public void setKayttajaId(int x) {
        this.kayttajaId = x;
    }
    
    public void setMaksutapaId(int x) {
        this.maksutapaId = x;
    }
    
    public void setBonusId(int x) {
        this.bonusId = x;
    }
}