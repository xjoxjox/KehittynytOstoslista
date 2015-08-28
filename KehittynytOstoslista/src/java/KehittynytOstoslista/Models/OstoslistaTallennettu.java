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

public class OstoslistaTallennettu {
    private int id;
    private String nimi;
    private HashMap<Tuote, Integer> tuotteet;
    private double summa;
    private double paino;
    private Timestamp paivays;
    private boolean kuitattu;
    private Kauppa kauppa;
    private int kayttajaId;
    
    private OstoslistaTallennettu(ResultSet tulos) throws SQLException, Exception {
        OstoslistaTallennettu o = new OstoslistaTallennettu(
            tulos.getInt("shoppinglist_id"),
            tulos.getString("name"),
            tulos.getDouble("sum"),
            tulos.getDouble("weight"),
            tulos.getTimestamp("time_created"),
            tulos.getBoolean("checked"),
            Kauppa.haeKauppa(tulos.getInt("shop_id")),
            tulos.getInt("account_id")
        );
    }
    
    public OstoslistaTallennettu(int id, String nimi, double summa, double paino, Timestamp paivays, 
            boolean kuitattu, Kauppa kauppa, int kayttajaId) throws Exception {
        this.id = id;
        this.nimi = nimi;
        this.summa = haeSumma(id, kauppa.getId());
        this.paino = haePaino(id);
        this.paivays = paivays;
        this.kuitattu = kuitattu;
        this.kauppa = kauppa;
        this.kayttajaId = kayttajaId;
        
        this.tuotteet = new HashMap<Tuote, Integer>();
    }
    /**
    * Metodilla haetaan ostoslista sen id:llä.
    *
    * @param id ostoslistan id tietokannassa.
    * @throws Exception
    * @return palauttaa id:tä vastaavan ostoslistan.
    */
    public static OstoslistaTallennettu haeOstoslistaTallennettu(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM shoppinglistsaved WHERE shoppinglist_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                return new OstoslistaTallennettu(tulokset);
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
    public static List<OstoslistaTallennettu> haeOstoslistaTallennettuNimella(String hakusana, int kayttaja) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaTallennettu> ostoslistat = new ArrayList<OstoslistaTallennettu>();

        try {
            String sql = "SELECT * FROM shoppinglistsaved WHERE name like ? AND account_id = ? AND checked = FALSE ORDER BY time_created";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, "%" + hakusana + "%");
            kysely.setInt(2, kayttaja);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaTallennettu o = new OstoslistaTallennettu(tulokset);
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
    public static List<OstoslistaTallennettu> haeOstoslistaTallennettuPaivayksellaEnnen(Timestamp hakupaiva, int kayttaja) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaTallennettu> ostoslistat = new ArrayList<OstoslistaTallennettu>();

        try {
            String sql = "SELECT * FROM shoppinglistsaved WHERE time_created < ? AND account_id = ? AND checked = FALSE ORDER BY time_created";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setTimestamp(1, hakupaiva);
            kysely.setInt(2, kayttaja);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaTallennettu o = new OstoslistaTallennettu(tulokset);
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
    public static List<OstoslistaTallennettu> haeOstoslistaTallennettuPaivayksellaJalkeen(Timestamp hakupaiva, int kayttaja) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaTallennettu> ostoslistat = new ArrayList<OstoslistaTallennettu>();

        try {
            String sql = "SELECT * FROM shoppinglistsaved WHERE time_created > ? AND account_id = ? AND checked = FALSE ORDER BY time_created";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setTimestamp(1, hakupaiva);
            kysely.setInt(2, kayttaja);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaTallennettu o = new OstoslistaTallennettu(tulokset);
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
    public static List<OstoslistaTallennettu> haeOstoslistaTallennettuKaupalla(int hakukauppa, int kayttaja) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        List<OstoslistaTallennettu> ostoslistat = new ArrayList<OstoslistaTallennettu>();

        try {
            String sql = "SELECT * FROM shoppinglistsaved WHERE shop_id = ? AND account_id = ? AND checked = FALSE ORDER BY time_created";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, hakukauppa);
            kysely.setInt(2, kayttaja);
            tulokset = kysely.executeQuery();

            if (tulokset.next()) {
                while (tulokset.next()) {
                    OstoslistaTallennettu o = new OstoslistaTallennettu(tulokset);
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
    * Metodilla tarkistetaan onko ostoslistoja, joiden ostokset on tehty annetussa kaupassa.
    *
    * @param kauppa kauppa, jossa ostokset on tehty.
    * @throws Exception
    * @return palauttaa true jos listoja löytyy, muuten false.
    */
    public static boolean onkoOstoslistaTallennettuKaupalla(int kauppa) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "SELECT * FROM shoppinglistsaved WHERE shop_id = ? AND checked = FALSE ORDER BY time_created";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, kauppa);
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
    * Metodilla haetaan kaikki käyttäjän ostoslistat, joita ei ole vielä kuitattu.
    *
    * @param hakukayttaja käyttäjän id tietokannassa.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa listana käyttäjän kaikki ostoslistat, joita ei ole vielä kuitattu.
    */
    public static List<OstoslistaTallennettu> haeKaikkiOstoslistaTallennettuJoitaEiKuitattu(int hakukayttaja) throws SQLException, NamingException, Exception {
        String sql = "SELECT shoppinglist_id, name, sum, weight, to_char(time_created, 'YYYY-MM-DD HH24:MI:SS') as time_created, "
                + "checked, shop_id, account_id FROM shoppinglistsaved WHERE account_id = ? AND checked = FALSE ORDER BY time_created";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, hakukayttaja);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<OstoslistaTallennettu> ostoslistat = new ArrayList<OstoslistaTallennettu>();
        while (tulokset.next()) {
            OstoslistaTallennettu o = new OstoslistaTallennettu(tulokset.getInt("shoppinglist_id"), tulokset.getString("name"), tulokset.getDouble("sum"),
                tulokset.getDouble("weight"), tulokset.getTimestamp("time_created"), tulokset.getBoolean("checked"), 
                    Kauppa.haeKauppa(tulokset.getInt("shop_id")), tulokset.getInt("account_id"));
            ostoslistat.add(o);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return ostoslistat;
    }
    /**
    * Metodilla haetaan kaikki käyttäjän ostoslistat.
    *
    * @param hakukayttaja käyttäjän id tietokannassa.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa listana käyttäjän kaikki ostoslistat.
    */
    public static List<OstoslistaTallennettu> haeKaikkiOstoslistaTallennettu(int hakukayttaja) throws SQLException, NamingException, Exception {
        String sql = "SELECT shoppinglist_id, name, sum, weight, to_char(time_created, 'YYYY-MM-DD HH24:MI:SS') as time_created, "
                + "checked, shop_id, account_id FROM shoppinglistsaved WHERE account_id = ? ORDER BY time_created";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, hakukayttaja);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<OstoslistaTallennettu> ostoslistat = new ArrayList<OstoslistaTallennettu>();
        while (tulokset.next()) {
            OstoslistaTallennettu o = new OstoslistaTallennettu(tulokset.getInt("shoppinglist_id"), tulokset.getString("name"), tulokset.getDouble("sum"),
                tulokset.getDouble("weight"), tulokset.getTimestamp("time_created"), tulokset.getBoolean("checked"), 
                    Kauppa.haeKauppa(tulokset.getInt("shop_id")), tulokset.getInt("account_id"));
            ostoslistat.add(o);
        }   

        try { tulokset.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}

        return ostoslistat;
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
        return Math.round(sum*100)/100.00d;
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
        return Math.round(kokPaino*100)/100.000d;
    }
    /**
    * Metodilla lisätään Tuote ostoslistalle.
    *
    * @param tuoteId tuotteen id tietokannassa.
    * @param listaid ostoslistan id tietokannassa.
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.TuoteLista
    * @throws Exception
    * @return palauttaa true, jos tuotteen lisays onnistui.
    */
    public static boolean lisaaTuote(int tuoteId, int listaid) throws Exception {
        TuoteLista t = new TuoteLista(tuoteId, listaid);
        return t.tallenna();
    }
    /**
    * Metodilla poistetaan Tuote ostoslistalta.
    *
    * @param tuoteId tuotteen id tietokannassa.
    * @param listaid ostoslistan id tietokannassa.
    * @see KehittynytOstoslista.Models.Tuote
    * @see KehittynytOstoslista.Models.TuoteLista
    * @throws Exception
    * @return palauttaa true, jos tuotteen poisto onnistui.
    */
    public static boolean poistaTuote(int tuoteId, int listaid) throws Exception {
        TuoteLista t = new TuoteLista(tuoteId, listaid);
        return t.poista();
    }
    /**
    * Metodilla muokataan ostoslistan nimi.
    *
    * @param x uusi nimi.
    * @param lista ostoslistan id.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public static  boolean muokkaaNimi(String x, int lista) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistsaved SET name = ? WHERE shoppinglist_id = ? RETURNING name";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, x);
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
    * Metodilla muokataan ostoslistan päiväys.
    *
    * @param x uusi päiväys.
    * @param lista ostoslistan id.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public static boolean muokkaaPaivays(Timestamp x, int lista) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistsaved SET time_created = ? WHERE shoppinglist_id = ? RETURNING time_checked";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setTimestamp(1, x);
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
    * Metodilla muokataan onko ostoslista kuitattu.
    *
    * @param x muokattu.
    * @param lista ostoslistan id.
    * @throws SQLException
    * @throws NamingException
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public static boolean muokkaaKuitattu(boolean x, int lista) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinglistsaved SET checked = ? WHERE shoppinglist_id = ? RETURNING checked";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setBoolean(1, x);
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
    * Metodilla muokataan ostoslistaan linkitetty Kauppa.
    *
    * @param x uuden kaupan id.
    * @param lista ostoslistan id.
    * @throws SQLException
    * @throws NamingException
    * @see KehittynytOstoslista.Models.Kauppa
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public static boolean muokkaaKauppaId(int x, int lista) throws NamingException, SQLException, Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistsaved SET shop_id = ? WHERE shoppinglist_id = ? RETURNING shop_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
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
    * Metodilla muokataan ostoslistaan linkitetty Kayttaja.
    *
    * @param x uuden käyttäjän id.
    * @param lista ostoslistan id.
    * @throws SQLException
    * @throws NamingException
    * @see KehittynytOstoslista.Models.Kayttaja
    * @return palauttaa true, jos muokkaus onnistui.
    */
    public static boolean muokkaaKayttajaId(int x, int lista) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            String sql = "UPDATE shoppinlistsaved SET account_id = ? WHERE shoppinglist_id = ? RETURNING account_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, x);
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
    * Metodilla luodaan uusi Ostoslista tietokantaan.
    *
    * @param listanimi uuden listan nimi.
    * @param listakauppa kauppa, johon uusi lista linkitetään.
    * @param listakayttaja käyttäjän id tietokannassa.
    * @throws Exception
    * @see KehittynytOstoslista.Models.Kauppa
    * @see KehittynytOstoslista.Models.Kayttaja
    * @return palauttaa true, jos luonti onnistui.
    */
    public static boolean luoUusiLista(String listanimi, int listakauppa, int listakayttaja) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        
        if(listanimi.equals("") || listanimi.length() < 0 || listanimi.length() > 50) {
            return false;
        }

        try {
            String sql = "INSERT INTO shoppinglistsaved(name, sum, weight, time_created, checked, shop_id, account_id) "
                    + "VALUES(?,0,0,now(),FALSE,?,?) RETURNING shoppinglist_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, listanimi);
            kysely.setInt(2, listakauppa);
            kysely.setInt(3, listakayttaja);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                int listaid = tulokset.getInt("shoppinglist_id");
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
    * Metodilla poistetaan ostoslista tietokannasta id:n perusteella.
    *
    * @param id ostoslistan id tietokannassa.
    * @throws Exception
    * @return palauttaa true, jos poisto onnistui.
    */
    public static boolean poistaLista(int id) throws Exception {
        Connection yhteys = null;
        PreparedStatement kysely = null;

        try {
            String sql = "DELETE FROM shoppinglistsaved where shoppinglist_id = ?";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, id);
            return kysely.execute();
        } finally {
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
            String sql = "INSERT INTO shoppinglistsaved(name, sum, weight, time_created, chechked, shop_id, account_id) "
                    + "VALUES(?,?,?,?,?,?) RETURNING shoppinglist_id";
            yhteys = Yhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, nimi);
            kysely.setDouble(2, summa);
            kysely.setDouble(3, paino);
            kysely.setTimestamp(4, paivays);
            kysely.setBoolean(5, kuitattu);
            kysely.setInt(6, kauppa.getId());
            kysely.setInt(7, kayttajaId);
            tulokset = kysely.executeQuery();
      
            if (tulokset.next()) {
                id = tulokset.getInt("shoppinglist_id");
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
            String sql = "DELETE FROM shoppinglistsaved where shoppinglist_id = ?";
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
  
    public String getNimi()  {
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
    
    public boolean getKuitattu() {
        return this.kuitattu;
    }
    
    public Kauppa getKauppa() {
        return this.kauppa;
    }
    
    public int getKayttajaId() {
        return this.kayttajaId;
    }
  
    public void setId(int x) {
        this.id = x;
    }
    
    public void setNimi(String x) {
        this.nimi = x;
    }
      
    public void setSumma() throws Exception {
        this.summa = haeSumma(id, kauppa.getId());
    }
  
    public void setPaino() throws NamingException, SQLException, Exception {
        this.paino = haePaino(id);
    }
        
    public void setPaivays(Timestamp x) {
        this.paivays = x;
    }
    
    public void setKuitattu(boolean x) {
        this.kuitattu = x;
    }
    
    public void setKauppa(Kauppa x) {
        this.kauppa = x;
    }
    
    public void setKayttajaId(int x) {
        this.kayttajaId = x;
    }
}