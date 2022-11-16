package de.hsos.prog3.danibloc.ab04;/* Eine schrecklich verschachtelte Klasse zur einfachen Darstellung
 * und Verschiebung verschiedener geometrischer Objekte. Der merkwuerdige
 * Programmierstil erlaubt es, dass diese Klasse fuer Programmieranfaenger
 * wie eine Klasse aussieht. Generell wird von geschachtelten Klassen
 * strikt abgeraten.
 * Die Nutzung der Klasse erfolgt vollstaendig auf eigene Gefahr.
 *
 * Version 1.02  2.11.2012
 * 1.0: Erste Veroeffentlichung der Variante MID
 * 1.02: Methoden abwischen() und zuruecksetzen(), das urspruengliche
 *       abwischen() voneinander getrennt
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Klasse zur einfachen Darstellung elementarer graphischer
 * Elemente (Punkt, Linie, Rechteck, Kreis), die auf einer
 * graphischen Fl&auml;che angezeigt werden. Neben der
 * reinen Anzeige gibt es M&ouml;glichkeiten, die Elemente
 * mit der Maus verschiebbar zu machen und bearbeitbar zu
 * machen. Die zugeh&ouml;rigen Methoden haben zwei
 * Parameter ein Objekt, das bei einer &Auml;nderung
 * informiert werden soll, und ein String, mit dem es
 * m&ouml;glich ist Objekte zu unterscheiden. Wird ein
 * graphisches Element mit diesen beiden Parametern an ein
 * Interaktionsbrett &uuml;bergeben, k&ouml;nnen mit der
 * Nutzung dieser Parameter die Elemente verschoben und
 * gel&ouml;scht werden. <br>
 *
 * <a name="Nutzung"> Will man die
 * Maussteuerungsm&ouml;glichkeiten nutzen</a>, muss das mit
 * dem graphischen Element &uuml;bergebene Objekt eine oder
 * mehrere der folgenden Methoden implementieren, die dann
 * vom Interaktionsbrett bei einer Mausaktion aufgerufen
 * werden.
 * <ul>
 * <li>
 * <code> public Boolean mitMausVerschoben(String name, int x, int y) </code>
 * <br>
 * Das Objekt wird informiert, dass ein graphisches Element
 * mit Namen name an die Position (x,y) verschoben wurde,
 * die zugeh&ouml;rige Mausbewegung ist beendet. Mit dem
 * R&uuml;ckgabewert kann man mitteilen, ob die Verschiebung
 * &uuml;berhaupt gew&uuml;nscht ist (true) oder nicht
 * (false).</li>
 *
 * <li>
 * <code> public Boolean mitMausAngeklickt(String name, int x, int y)</code>
 * <br>
 * Das Objekt wird informiert, dass ein graphisches Element
 * mit Namen name an der Position (x,y) gerade angeklickt
 * wurde, die zugeh&ouml;rige Mausbewegung beginnt gerade.
 * Mit dem R&uuml;ckgabewert kann man mitteilen, ob eine
 * Bearbeitung (konkret eine Verschiebung) &uuml;berhaupt
 * gew&uuml;nscht ist (true) oder nicht (false).</li>
 * <li>
 * <code> public Boolean mitMausLosgelassen(String name, int x, int y)</code>
 * <br>
 * <p>
 * Das Objekt wird informiert, dass ein graphisches Element
 * mit Namen name gerade an die Position (x,y) verschoben
 * und an dieser Position losgelassen wurde, die
 * zugeh&ouml;rige Mausbewegung endet gerade. Mit dem
 * R&uuml;ckgabewert kann man mitteilen, ob das Ablegen des
 * Elements an dieser Stelle &uuml;berhaupt gew&uuml;nscht
 * ist (true) oder nicht (false). Der sicherlich selten
 * genutzte Fall false hat nur Auswirkungen, wenn der Nutzer
 * zum n&auml;chsten Zeitpunkt auf eine Stelle klickt, an
 * der sich kein ausw&auml;hlbares graphisches Element
 * befinde. Dann wird das zuletzt benutzte Element genutzt
 * und z. B. wieder verschoben.</li>
 * </ul>
 * <p>
 * Objekte k&ouml;nnen dem Interaktionsbrett mitteilen, dass
 * sie &uuml;ber gedr&uuml;ckte Tasten informiert werden
 * wollen. Hierzu dient die Methode
 * <code>willTasteninfo(<zuInformierendesObjekt>)</code>.
 * Das zu informierende Objekt muss dann eine Methode der
 * folgenden Form realisieren: <br>
 * <code>public void tasteGedrueckt(String s)</code> <br>
 * <br>
 * <br>
 * <p>
 * Weiterhin bieten Objekte der Klasse ein einfaches
 * Stoppuhr- Objekt, das gestartet und gestoppt werden kann.
 */
public class Interaktionsbrett {

    private JFrame rahmen = new JFrame("Interaktionsbrett");
    //private static final long serialVersionUID = 1L;
    private static final Dimension DIM = new Dimension(380,
            500);
    private JLabel meldung = new JLabel(
            "Java-untypisches Zeichenbrett",
            SwingConstants.CENTER);
    private MalFlaeche brett = new MalFlaeche(this);
    private List<Geo> statisch = new ArrayList<Geo>();
    private HashMap<Paar, Geo> beweglicheObjekte = new HashMap<Paar, Geo>();
    private Geo selektiert = null;
    private int selektiertXOffset; // Abstand vom x-Klickpunkt
    // zur x-Koordinate
    // des gew&auml;hlten Objekts
    private int selektiertYOffset; // Abstand vom y-Klickpunkt
    // zur y-Koordinate
    // des gew&auml;hlten Objekts
    private Paar eigentuemer = null; // zugehoeriger Key zum
    // Wert selektiert
    private List<Paar> dragListener = new ArrayList<Paar>();
    private List<Paar> klickListener = new ArrayList<Paar>();
    private List<Paar> dropListener = new ArrayList<Paar>();
    private List<Object> tastaturListener = new ArrayList<Object>();
    private JLabel uhranzeige = new JLabel("0000 ");
    private Uhr uhrThread = new Uhr();
    private double zoom = 1d;
    private JTextField zoomfaktor = new JTextField("" + zoom);

    private final String ANGEKLICKT = "mitMausAngeklickt";
    private final String GEZOGEN = "mitMausVerschoben";
    private final String LOSGELASSEN = "mitMausLosgelassen";
    private final String TASTE = "tasteGedrueckt";

    /**
     * Konstruktor zum Erzeugen eines Interaktionsbretts. Das
     * Brett wird sofort nach der Erstellung angezeigt.
     */
    public Interaktionsbrett() {

        brett.setLayout(new BorderLayout());
        brett.setDoubleBuffered(true);

        SwingUtilities.invokeLater(new Runnable() {
            // @Override
            public void run() {
                rahmen = new JFrame("Interaktionsbrett");
                rahmen.add(brett);
                rahmen.setBackground(Color.WHITE);
                rahmen.setMinimumSize(DIM);
                rahmen.setPreferredSize(DIM);
                rahmen.setMaximumSize(DIM);

                zoomfaktor.addActionListener(new ActionListener() {

                    //@Override
                    public void actionPerformed(ActionEvent arg0) {
                        double z;
                        try {
                            z = Double.parseDouble(zoomfaktor.getText());
                        } catch (NumberFormatException e) {
                            z = 0d;
                        }
                        if (z > 0 && z < 30) {
                            zoom = z;
                        } else
                            zoomfaktor.setText("" + zoom);
                        if (rahmen != null) {
                            rahmen.repaint();
                        }
                        rahmen.requestFocus();
                    }
                });
                JPanel unten = new JPanel(new BorderLayout());
                uhranzeige.setFont(new Font(Font.MONOSPACED,
                        Font.BOLD,
                        12));
                unten.add(uhranzeige, BorderLayout.WEST);

                Dimension zdim = new Dimension(40, 20);
                zoomfaktor.setPreferredSize(zdim);
                JPanel zoomanzeige = new JPanel(
                        new GridLayout(1, 2));
                zoomanzeige.add(new JLabel("Zoom:"));
                zoomanzeige.add(zoomfaktor);
                unten.add(zoomanzeige, BorderLayout.EAST);

                unten.add(meldung);
                rahmen.add(unten, BorderLayout.SOUTH);

                rahmen
                        .setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Toolkit tk = Toolkit.getDefaultToolkit();
                tk.setDynamicLayout(true);
                rahmen.setVisible(true);
            }
        });
        pause(500);
        new Thread(uhrThread).start();

    }

    /**
     * Methode zum Starten der eingeblendeten Stoppuhr.
     */
    public void starteUhr() {
        uhrThread.starteUhr();
    }

    /**
     * Methode zum Ablesen der bisher seit dem Start
     * verbrauchten Zeit.
     *
     * @return Zeit in Millisekunden, die seit dem letzten
     * Aufruf von starteUhr() vergangen ist
     */
    public int leseUhr() {
        return uhrThread.leseUhr();
    }

    /**
     * Methode zum Stoppen der Stoppuhr.
     *
     * @return gemessener Wert in Millisekunden
     */
    public int stoppeUhr() {
        return uhrThread.stoppeUhr();
    }

    /**
     * Methode, um Bearbeitung f&uuml;r eine kurze in
     * Millisekunden angegebene Zeit anzuhalten.
     *
     * @param milli Zeit in Millisekunden, die der Programmablauf
     *              mindestens angehalten werden soll
     */
    public void pause(int milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            // egal
        }
    }

    /**
     * Methode zur &Auml;nderung des in der Fu&szlig;zeile
     * angezeigten Textes.
     *
     * @param text neuer anzuzeigender Text
     */
    public void textZeigen(String text) {
        meldung.setText(text);
    }

    /**
     * Methode zur Erzeugung einer ganzahligen Zufallszahl
     * zwischen (einschlie&szlig;lich) den &uuml;bergebenen
     * Grenzen. Es wird erwartet und nicht gepr&uuml;ft, dass
     * der Endwert nicht kleiner als der Startwert ist.
     *
     * @param start minimal m&ouml;glicher Zufallswert
     * @param ende  maximal m&ouml;glicher Zufallswert
     * @return zuf&auml;lliger Wert zwischen start und ende
     * (auch diese beiden Werte sind m&ouml;glich
     */
    public int zufall(int start, int ende) {
        return start
                + (int) (Math.random() * (ende - start + 1));
    }

    /**
     * Berechnet die L&auml;nge eines Textes f&uuml;r eine
     * m&ouml;gliche graphische Ausgabe. Sollte man z. B.
     * Textumrandungen basteln, ist zu beachten, dass Rand an
     * beiden Seiten hinzugef&uuml;gt wird.
     *
     * @param text Text dessen Ausgabel&auml;nge in Pixel
     *             bestimmt werden soll
     * @return L&auml;nge des &uuml;bergebenen Textes in Pixel
     */
    public int textlaenge(String text) {
        return brett.getFontMetrics(brett.getFont())
                .stringWidth(text);
    }

    /**
     * Methode zum Zeichnen eines neuen Punktes. Der Punkt
     * kann sp&auml;ter nicht mehr gel&ouml;scht werden.
     *
     * @param x x-Koordinate des Punktes (beginnend von links
     *          nach rechts)
     * @param y y-Koordinate des Punktes (beginnend von oben
     *          nach unten)
     */
    public void neuerPunkt(int x, int y) {
        statisch.add(new Punkt(x, y));
        rahmen.repaint();
    }

    /**
     * Methode zum Zeichnen eines neuen Rechtecks. Das
     * Rechteck kann sp&auml;ter nicht mehr gel&ouml;scht
     * werden.
     *
     * @param x      x-Koordinate der linken oberen Ecke des
     *               Rechtecks
     * @param y      y-Koordinate der linken oberen Ecke des
     *               Rechtecks
     * @param breite Breite des Rechtecks (in Richtung x-Achse)
     * @param hoehe  H&ouml;he des Rechtecks (in Richtung y-Achse)
     */
    public void neuesRechteck(int x, int y,
                              int breite,
                              int hoehe) {
        statisch.add(new Rechteck(x, y, breite, hoehe));
        rahmen.repaint();
    }

    /**
     * Methode zum Zeichnen eines neuen Kreises. Der Kreis
     * kann sp&auml;ter nicht mehr gel&ouml;scht werden.
     *
     * @param x      x-Koordinate des Mittelpunkt des Kreises
     * @param y      y-Koordinate des Mittelpunkt des Kreises
     * @param radius Radius des Kreises
     */
    public void neuerKreis(int x, int y,
                           int radius) {
        statisch.add(new Kreis(x, y, radius));
        rahmen.repaint();
    }

    /**
     * Methode zum Zeichnen einer neuen Linie. Die Linie kann
     * sp&auml;ter nicht mehr gel&ouml;scht werden. Die Linie
     * verbindet zwei Punkte, die als Parameter jeweils mit x-
     * und y-Wert &uuml;bergeben werden.
     *
     * @param x1 x-Koordinate des ersten Punkts der Linie
     * @param y1 y-Koordinate des ersten Punkts der Linie
     * @param x2 x-Koordinate des zweiten Punkts der Linie
     * @param y2 y-Koordinate des zweiten Punkts der Linie
     */
    public void neueLinie(int x1, int y1, int x2,
                          int y2) {
        statisch.add(new Linie(x1, y1, x2, y2));
        rahmen.repaint();
    }

    /**
     * Methode zur Ausgabe eines Textes. Der als Parameter mit
     * x- und y-Wert &uuml;bergebene Punkt legt in x-Richtung
     * die Basislinie des Textes fest. Alle Buchstaben ohne
     * Unterl&auml;nge (z. B. A, a, b, x) werden oberhalb der
     * Linie dargestellt. Buchstaben mit Unterl&auml;nge (z.
     * B. p, q, y) durchsto&szlig;en diese Linie nach unten.
     *
     * @param x    x-Koordinate des Startpunkts des Textes
     * @param y    x-Koordinate des Startpunkts des Textes, legt
     *             auch die Basislinie des Textes fest
     * @param text auszugebender Text
     */
    public void neuerText(int x, int y, String text) {
        statisch.add(new Text(x, y, text));
        rahmen.repaint();
    }

    /**
     * Methode zum Zeichnen eines neuen Punktes, der
     * ver&auml;ndert und dessen Nutzung beobachtet werden
     * kann. Der Punkt kann sp&auml;ter beliebig bearbeitet
     * werden, wie es in der <a href="#Nutzung"> Beschreibung
     * der Klasse Interaktionsbrett </a> gezeigt wird.
     *
     * @param quelle Objekt, das informiert werden soll, falls
     *               dieser Punkt bearbeitet wird (h&auml;ufig wird
     *               this &uuml;bergeben).
     * @param name   Name des Objekts, der zus&auml;tzlich mit an
     *               das zu informierende Objekt &uuml;bergeben
     *               wird. Das Paar (quelle,name) sollte eindeutig
     *               sein.
     * @param x      x-Koordinate des Punktes (beginnend von links
     *               nach rechts)
     * @param y      y-Koordinate des Punktes (beginnend von oben
     *               nach unten)
     */
    public void neuerPunkt(Object quelle, String name,
                           int x, int y) {
        Paar schluessel = new Paar(quelle, name);
        beweglicheObjekte.put(schluessel, new Punkt(x, y));
        willBenachrichtigtWerden(schluessel);
        rahmen.repaint();
    }

    /**
     * Methode zum Zeichnen eines neuen Rechtecks, das
     * ver&auml;ndert und dessen Nutzung beobachtet werden
     * kann. Das Rechteck kann sp&auml;ter beliebig bearbeitet
     * werden, wie es in der <a href="#Nutzung"> Beschreibung
     * der Klasse Interaktionsbrett </a> gezeigt wird.
     *
     * @param quelle Objekt, das informiert werden soll, falls
     *               dieses Rechteck bearbeitet wird (h&auml;ufig
     *               wird this &uuml;bergeben).
     * @param name   Name des Objekts, der zus&auml;tzlich mit an
     *               das zu informierende Objekt &uuml;bergeben
     *               wird. Das Paar (quelle,name) sollte eindeutig
     *               sein.
     * @param x      x-Koordinate der linken oberen Ecke des
     *               Rechtecks
     * @param y      y-Koordinate der linken oberen Ecke des
     *               Rechtecks
     * @param breite Breite des Rechtecks (in Richtung x-Achse)
     * @param hoehe  H&ouml;he des Rechtecks (in Richtung y-Achse)
     */
    public void neuesRechteck(Object quelle, String name,
                              int x, int y,
                              int breite, int hoehe) {
        Paar schluessel = new Paar(quelle, name);
        beweglicheObjekte.put(schluessel, new Rechteck(x, y,
                breite, hoehe));
        willBenachrichtigtWerden(schluessel);
        rahmen.repaint();
    }

    /**
     * Methode zum Zeichnen eines neuen Kreises, der
     * ver&auml;ndert und dessen Nutzung beobachtet werden
     * kann. Der Kreis kann sp&auml;ter beliebig bearbeitet
     * werden, wie es in der <a href="#Nutzung"> Beschreibung
     * der Klasse Interaktionsbrett </a> gezeigt wird.
     *
     * @param quelle Objekt, das informiert werden soll, falls
     *               dieser Kreis bearbeitet wird (h&auml;ufig wird
     *               this &uuml;bergeben).
     * @param name   Name des Objekts, der zus&auml;tzlich mit an
     *               das zu informierende Objekt &uuml;bergeben
     *               wird. Das Paar (quelle,name) sollte eindeutig
     *               sein.
     * @param x      x-Koordinate des Mittelpunkt des Kreises
     * @param y      y-Koordinate des Mittelpunkt des Kreises
     * @param radius Radius des Kreises
     */
    public void neuerKreis(Object quelle, String name,
                           int x, int y,
                           int radius) {
        Paar schluessel = new Paar(quelle, name);
        beweglicheObjekte.put(schluessel, new Kreis(x, y,
                radius));
        willBenachrichtigtWerden(schluessel);
        rahmen.repaint();
    }

    /**
     * Methode zum Zeichnen einer neuen Linie, die
     * ver&auml;ndert und deren Nutzung mit der Maus
     * beobachtet werden kann. Die Linie kann sp&auml;ter
     * beliebig bearbeitet werden, wie es in der <a
     * href="#Nutzung"> Beschreibung der Klasse
     * Interaktionsbrett </a> gezeigt wird.
     *
     * @param quelle Objekt, das informiert werden soll, falls
     *               diese Linie bearbeitet wird (h&auml;ufig wird
     *               this &uuml;bergeben).
     * @param name   Name des Objekts, der zus&auml;tzlich mit an
     *               das zu informierende Objekt &uuml;bergeben
     *               wird. Das Paar (quelle,name) sollte eindeutig
     *               sein.
     * @param x1     x-Koordinate des ersten Punkts der Linie
     * @param y1     y-Koordinate des ersten Punkts der Linie
     * @param x2     x-Koordinate des zweiten Punkts der Linie
     * @param y2     y-Koordinate des zweiten Punkts der Linie
     */
    public void neueLinie(Object quelle, String name,
                          int x1, int y1,
                          int x2, int y2) {
        Paar schluessel = new Paar(quelle, name);
        beweglicheObjekte.put(schluessel, new Linie(x1, y1, x2,
                y2));
        willBenachrichtigtWerden(schluessel);
        rahmen.repaint();
    }

    /**
     * Methode zum Zeichnen eines neuen Textes, der
     * ver&auml;ndert und dessen Nutzung beobachtet werden
     * kann. Der Text kann sp&auml;ter beliebig bearbeitet
     * werden, wie es in der <a href="#Nutzung"> Beschreibung
     * der Klasse Interaktionsbrett </a> gezeigt wird. Der als
     * Parameter mit x- und y-Wert &uuml;bergebene Punkt legt
     * in x-Richtung die Basislinie des Textes fest. Alle
     * Buchstaben ohne Unterl&auml;nge (z. B. A, a, b, x)
     * werden oberhalb der Linie dargestellt. Buchstaben mit
     * Unterl&auml;nge (z. B. p, q, y) durchsto&szlig;en diese
     * Linie nach unten.
     *
     * @param quelle Objekt, das informiert werden soll, falls
     *               dieser Text bearbeitet wird (h&auml;ufig wird
     *               this &uuml;bergeben).
     * @param name   Name des Objekts, der zus&auml;tzlich mit an
     *               das zu informierende Objekt &uuml;bergeben
     *               wird. Das Paar (quelle,name) sollte eindeutig
     *               sein.
     * @param x      x-Koordinate des Startpunkts des Textes
     * @param y      y-Koordinate des Startpunkts des Textes, legt
     *               auch die Basislinie des Textes fest
     * @param text   auszugebender Text
     */
    public void neuerText(Object quelle, String name,
                          int x, int y,
                          String text) {
        Paar schluessel = new Paar(quelle, name);
        beweglicheObjekte.put(schluessel, new Text(x, y, text));
        willBenachrichtigtWerden(schluessel);
        rahmen.repaint();
    }

    /**
     * Ein mit den Parametern quelle und name vorher erzeugtes
     * graphisches Element wird gel&ouml;scht.
     *
     * @param quelle Objekt, das zusammen mit einem zu erzeugenden
     *               graphischen Element &uuml;bergeben wurde
     * @param name   identifizierender Text, der zusammen mit einem
     *               zu erzeugenden graphischen Element
     *               &uuml;bergeben wurde. Das Paar (quelle, name)
     *               soll ein vorher erzeugtes graphisches Element
     *               eindeutig identifizieren.
     */
    public void loescheObjekt(Object quelle, String name) {
        Paar schluessel = new Paar(quelle, name);
        beweglicheObjekte.remove(schluessel);
        dragListener.remove(schluessel);
        klickListener.remove(schluessel);
        dropListener.remove(schluessel);
        rahmen.repaint();
    }

    /**
     * Ein mit den Parametern quelle und name vorher erzeugtes
     * graphisches Element wird auf eine neue Position
     * gesetzt.
     *
     * @param quelle Objekt, das zusammen mit einem zu erzeugenden
     *               graphischen Element &uuml;bergeben wurde
     * @param name   identifizierender Text, der zusammen mit einem
     *               zu erzeugenden graphischen Element
     *               &uuml;bergeben wurde. Das Paar (quelle name)
     *               soll ein vorher erzeugtes graphisches Element
     *               eindeutig identifizieren.
     * @param x      neue x-Koordniate des graphischen Elements
     * @param y      neue y-Koordniate des graphischen Elements
     */
    public void verschiebeObjektNach(Object quelle,
                                     String name, int x,
                                     int y) {
        Paar schluessel = new Paar(quelle, name);
        Geo objekt = beweglicheObjekte.get(schluessel);
        if (objekt != null) {
            objekt.verschieben(x, y);
        }
        rahmen.repaint();
    }

    /**
     * Objekte k&ouml;nnen an ein Interaktionsbrett so
     * &uuml;bergeben werden, dass sie informiert werden, wenn
     * eine Taste gedr&uuml;ckt wurde. Das Objekt muss dazu
     * eine Methode der Form<br>
     * <code>public void tasteGedrueckt(String s)</code> <br>
     * realisieren, wobei die gedr&uuml;ckte Taste dann im
     * &uuml;bergebenen String steht. Neben den &uuml;blichen
     * Zeichen sind auch folgende Texte m&ouml;glich: <br>
     * "F1"-"F12" f&uuml;r die Funktionstasten (man beachte
     * eventuelle Probleme, wenn die Tasten von anderen
     * Programmen belegt sind.<br>
     * "Eingabe" f&uuml;r die Eingabe- oder Return-Taste (auch
     * Enter-Taste)<br>
     * "Strg" f&uuml;r eine der Strg-Tasten (auch CTRL-Tasten
     * genannt)<br>
     * "R&uuml;cktaste" f&uuml;r die R&uuml;ckw&auml;rtstaste
     * (Backspace) <br>
     * "Einfg" f&uuml;r die Einfg-Taste (Einf&uuml;gen) <br>
     * "Pos 1" f&uuml;r die Pos1-Taste <br>
     * "Ende" f&uuml;r die Ende-Taste <br>
     * "Bild auf" f&uuml;r die Bild nach oben-Taste<br>
     * "Bild ab" f&uuml;r die Bild nach unten-Taste <br>
     * "ESC" f&uuml;r die Escape-Taste (ESC-Taste) <br>
     * "Pause" f&uuml;r die Pause-Taste <br>
     * "Links" f&uuml;r die Pfeiltaste nach links<br>
     * "Rechts" f&uuml;r die Pfeiltaste nach rechts<br>
     * "Oben" f&uuml;r die Pfeiltaste nach oben<br>
     * "Unten" f&uuml;r die Pfeiltaste nach unten<br>
     * <br>
     * Von der Verwendung der Tabulator-, Windows- und
     * Alt-Tasten wird abgeraten, da sie u. a. Nebeneffekte im
     * Programm haben k&ouml;nnen.
     *
     * @param o Objekt, dass informiert werden m&ouml;chte,
     *          wenn im Interaktionsbrett eine Taste
     *          gedr&uuml;ckt wurde
     */
    public void willTasteninfo(Object o) {
        for (Method meth : o.getClass().getMethods()) {
            if (meth.getName().equals(TASTE)) {
                Class<?>[] typen = meth.getParameterTypes();
                boolean ok = (typen.length == 1);
                if (ok && typen[0] == String.class)
                    tastaturListener.add(o);
            }
        }
    }

    /**
     * Methode mit der im wesentlichen der Urzustand des
     * Interaktionsbretts wieder hergestellt wird. Alle
     * graphischen Elemente werden gel&ouml;scht und
     * Einstellungen zur&uuml;ckgesetzt. Die Methode soll
     * nicht zum Verschieben von Objekten durch L&ouml;schen
     * und Neuzeichnen genutzt werden, da hier Fehler
     * auftreten k&ouml;nnen. Hierzu ist die Methode
     * <code>verschiebeObjektNach(.,.)</code> zu nutzen.
     */
    public void zuruecksetzen() {
        statisch.clear();
        beweglicheObjekte.clear();
        selektiert = null;
        eigentuemer = null;
        dragListener.clear();
        klickListener.clear();
        dropListener.clear();
        tastaturListener.clear();
        stoppeUhr();
        uhranzeige.setText("0000 ");
        meldung.setText("");
        zoomfaktor.setText("1");
        zoom = 1d;
        rahmen.setSize(DIM);
        rahmen.repaint();
    }

    /**
     * Methode zum L&ouml;schen
     * der gezeichneten Elemente, dazu werden auch alle
     * Verbindungen und Referenzen,
     * die zu Objekten bestehen, gel&ouml;scht.
     */
    /* neu ab Version 1.02 */
    public void abwischen() {
        statisch.clear();
        beweglicheObjekte.clear();
        selektiert = null;
        eigentuemer = null;
        dragListener.clear();
        klickListener.clear();
        dropListener.clear();
        rahmen.repaint();
    }

    // //////////////////////////////////////////////////////
    // intern
    // //////////////////////////////////////////////////////

    private void neuMalen(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.scale(zoom, zoom);
        g.fillRect(0, 0, (int) (rahmen.getWidth() / zoom),
                (int) (rahmen.getHeight() / zoom));
        g.setColor(Color.BLACK);

        try {
            for (Geo geo : statisch) {
                geo.zeichnen(g);
            }
        } catch (Exception e) { // u. a.
            // ConcurrentModificationException
        }

        try {
            for (Geo geo : beweglicheObjekte.values()) {
                geo.zeichnen(g);
            }
        } catch (Exception e) { // u. a.
            // ConcurrentModificationException
        }
        brett.requestFocus(); // damit immer auf Tasten reagiert
        // wird
    }

    private void willBenachrichtigtWerden(Paar schluessel) {
        if (willBenachrichtigtWerden(schluessel.getObjekt(),
                GEZOGEN)) {
            dragListener.add(schluessel);
        }
        if (willBenachrichtigtWerden(schluessel.getObjekt(),
                ANGEKLICKT)) {
            klickListener.add(schluessel);
        }
        if (willBenachrichtigtWerden(schluessel.getObjekt(),
                LOSGELASSEN)) {
            dropListener.add(schluessel);
        }
    }

    private boolean willBenachrichtigtWerden(Object o,
                                             String aktion) {
        for (Method meth : o.getClass().getMethods()) {
            if (meth.getName().equals(aktion)) {
                Class<?>[] typen = meth.getParameterTypes();
                boolean ok = (typen.length == 3);
                if (ok && typen[0] == String.class
                        && typen[1] == int.class
                        && typen[2] == int.class)
                    return true;
            }
        }
        return false;
    }

    private MouseEvent klickTransformieren(MouseEvent e) {
        return new MouseEvent(e.getComponent(), e.getID(),
                e.getWhen(),
                e.getModifiers(), (int) (e.getX() / zoom),
                (int) (e.getY() / zoom), e.getClickCount(), false,
                e.getButton());
    }

    class Paar {
        private Object objekt;
        private String text;

        public Paar(Object objekt, String text) {
            super();
            this.objekt = objekt;
            this.text = text;
        }

        public Object getObjekt() {
            return objekt;
        }

        public void setObjekt(Object objekt) {
            this.objekt = objekt;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result
                    + ((objekt == null) ? 0 : objekt.hashCode());
            result = prime * result
                    + ((text == null) ? 0 : text.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Paar other = (Paar) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (objekt == null) {
                if (other.objekt != null)
                    return false;
            } else if (!objekt.equals(other.objekt))
                return false;
            if (text == null) {
                if (other.text != null)
                    return false;
            } else if (!text.equals(other.text))
                return false;
            return true;
        }

        private Interaktionsbrett getOuterType() {
            return Interaktionsbrett.this;
        }

    }

    class Uhr implements Runnable {

        private long uhr;
        private boolean uhrLaeuft = false;

        //@Override
        public void run() {
            while (true) {
                if (uhrLaeuft) {
                    long zeit = ((new Date()).getTime()) - uhr;
                    String anzeige = (zeit / 1000) + " ";
                    for (int i = anzeige.length(); i < 5; i++)
                        anzeige = "0" + anzeige;
                    uhranzeige.setText(anzeige);
                }
                try {
                    Thread.sleep(480);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void starteUhr() {
            uhr = (new Date()).getTime();
            uhranzeige.setText("0000 ");
            uhrLaeuft = true;
        }

        public int stoppeUhr() {
            uhrLaeuft = false;
            return (int) (((new Date()).getTime()) - uhr);
        }

        public int leseUhr() {
            return (int) (((new Date()).getTime()) - uhr);
        }

    }

    class MalFlaeche extends JPanel implements MouseListener,
            MouseMotionListener, KeyListener {
        private static final long serialVersionUID = 1L;
        private Interaktionsbrett brettpanel;

        public MalFlaeche(Interaktionsbrett brett) {
            this.brettpanel = brett;
            setOpaque(true);
            setBackground(Color.WHITE);
            setForeground(Color.WHITE);
            // setFocusable(true);
            addMouseListener(this);
            addMouseMotionListener(this);
            addKeyListener(this);
        }

        @Override
        public void paintComponent(Graphics g) {
            brettpanel.neuMalen((Graphics2D) g);
        }

        // @Override
        public void mouseClicked(MouseEvent e) {
            // System.out.println("clicked");
        }

        // @Override
        public void mouseEntered(MouseEvent e) {
            // System.out.println("entered");
        }

        // @Override
        public void mouseExited(MouseEvent e) {
            // System.out.println("exited");
        }

        // @Override
        public void mousePressed(MouseEvent ev) {
            MouseEvent e = klickTransformieren(ev);
            for (Geo g : beweglicheObjekte.values()) {
                if (g.istEnthalten(e.getX(), e.getY())) {
                    selektiert = g;
                    eigentuemer = sucheEigentuemer(g);
                    selektiertXOffset = g.getX() - e.getX();
                    selektiertYOffset = g.getY() - e.getY();
                }
            }
            benachrichtigen(klickListener, ANGEKLICKT, false);

        }

        private void benachrichtigen(List<Paar> liste,
                                     String methode,
                                     boolean abschaltenMit) {
            if (selektiert != null && eigentuemer != null
                    && liste.contains(eigentuemer)) {
                try {
                    Object erg = eigentuemer
                            .getObjekt()
                            .getClass()
                            .getMethod(methode, String.class,
                                    int.class,
                                    int.class)
                            .invoke(eigentuemer.getObjekt(),
                                    eigentuemer.getText(), selektiert.getX(),
                                    selektiert.getY());
                    try {
                        if (((Boolean) erg) == abschaltenMit) {
                            selektiert = null;
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    rahmen.repaint();

                } catch (Exception e1) {
                    selektiert = null;
                    e1.printStackTrace();
                }
            } else {
                selektiert = null;
            }
        }

        private Paar sucheEigentuemer(Geo g) {
            for (Paar o : beweglicheObjekte.keySet())
                if (beweglicheObjekte.get(o) == g)
                    return o;
            return null;
        }

        // @Override
        public void mouseReleased(MouseEvent e) {
            benachrichtigen(dropListener, LOSGELASSEN, true);
        }

        // @Override
        public void mouseDragged(MouseEvent ev) {
            MouseEvent e = klickTransformieren(ev);
            if (selektiert != null) {
                selektiert.verschieben(
                        e.getX() + selektiertXOffset, e.getY()
                                + selektiertYOffset);
                benachrichtigen(dragListener, GEZOGEN, false);
            }
            super.repaint();
        }

        // @Override
        public void mouseMoved(MouseEvent arg0) {
            // nicht genutzt
        }

        // folgende Methode funktioniert nur, wenn das
        // System auf "deutsch" gestellt ist
        private String sondertastenanalyse(KeyEvent ev) {
            String ergebnis = "";
            int id = ev.getID();
            if (id != KeyEvent.KEY_TYPED) {
                ergebnis = KeyEvent.getKeyText(ev.getKeyCode());
            }
            // Abbruch bei bestimmten Tasten, die nicht
            // mitgeteilt werden sollen, da sie z. B. nur
            // bei der Eingabe von Gro&szlig;buchstaben beachtet
            // werden sollen
            if (ergebnis.equals("Umschalt")
                    || ergebnis.equals("Unknown keyCode: 0x0")
                    || ergebnis.equals("Kleiner als")
                    || ergebnis.equals("Leertaste")
                    || ergebnis
                    .equals("Umschalttaste Gro\u00DF-/Kleinschreibung")
                    || ergebnis.equals("Rollsperre")
                    || ergebnis.equals("Abbrechen")
                    || ergebnis.equals("Alt")
                    || ergebnis.equals("Comma")
                    || ergebnis.equals("Period")
                    || ergebnis.equals("Minus")
                    || ergebnis.equals("Plus")
                    || ergebnis.equals("Nummernzeichen")
                    || ergebnis.equals("Akut (Dead)")
                    || ergebnis.equals("Zirkumflex (Dead)")
                    || ergebnis.startsWith("Tastenblock")
                    || ergebnis.startsWith("NumPad")) {
                return "";
            }
            return ergebnis;
        }

        // @Override
        public void keyPressed(KeyEvent ev) {
            String ergebnis = sondertastenanalyse(ev);
            if (ergebnis.length() > 1) {
                tastendruckBenachrichtigen(ergebnis);
            }
        }

        private void tastendruckBenachrichtigen(String ergebnis) {
            try {
                for (Object o : tastaturListener) {
                    try {
                        o.getClass().getMethod(TASTE, String.class)
                                // .invoke(o, "" + ev.getKeyChar());
                                .invoke(o, "" + ergebnis);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            } catch (Exception e) { // wegen
                // ConcurrentModificationException
            }
        }

        // @Override
        public void keyReleased(KeyEvent arg0) {
            // nicht genutzt
        }

        // @Override
        public void keyTyped(KeyEvent ev) {
            if (sondertastenanalyse(ev).length() == 0) {
                int zeichen = (int) ev.getKeyChar();
                if (zeichen != 27 && zeichen != 8 && zeichen != 10
                        && zeichen != 127) {
                    tastendruckBenachrichtigen("" + ev.getKeyChar());
                }
            }
        }
    }

    /*
     * Klasse definiert einen Punkt mit x- und y-Koordinate,
     * der als Basispunkt von allen abgeleiteten graphischen
     * Elementen genutzt wird
     */
    abstract class Geo {
        protected int x;
        protected int y;

        public Geo(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }

        public void verschieben(int xx, int yy) {
            this.x = xx;
            this.y = yy;
        }

        abstract public boolean istEnthalten(int xx, int yy);

        abstract public void zeichnen(Graphics2D g);

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    class Text extends Geo {
        private String text;
        private int hoehe;
        private int breite;

        public Text(int x, int y, String text) {
            super(x, y);
            this.text = text;
            this.hoehe = rahmen.getFontMetrics(rahmen.getFont())
                    .getHeight();
            this.breite = rahmen.getFontMetrics(rahmen.getFont())
                    .stringWidth(
                            text);
        }

        @Override
        public boolean istEnthalten(int x1, int y1) {
            return x1 >= super.x - 2 && x1 <= super.x + breite + 2
                    && y1 <= super.y + 2 && y1 >= super.y - hoehe + 2;
        }

        @Override
        public void zeichnen(Graphics2D g) {
            g.drawString(text, super.x, super.y);
        }

    }

    class Linie extends Geo {
        // erwartet wird, dass x2>=x gilt
        private int x2;
        private int y2;
        private double steigung;

        public Linie(int x, int y, int x2, int y2) {
            super(x, y);
            this.x2 = x2;
            this.y2 = y2;
            int diffx = this.x2 - super.x;
            int diffy = this.y2 - super.y;
            if (diffx != 0)
                steigung = diffy * 1d / diffx;
        }

        @Override
        public void verschieben(int dx, int dy) {
            int diffx = dx - super.x;
            int diffy = dy - super.y;
            super.verschieben(dx, dy);
            this.x2 = this.x2 + diffx;
            this.y2 = this.y2 + diffy;
        }

        @Override
        public boolean istEnthalten(int x1, int y1) {
            if (super.x == this.x2 && super.y == this.y2) {
                return x1 >= this.x - 2 && x1 < this.x + 2
                        && y1 >= this.y - 2
                        && y1 < this.y + 2;
            }
            if (super.x == this.x2) {
                return x1 >= super.x - 2 && x1 < super.x + 2
                        && y1 >= super.y
                        && y1 <= this.y2;
            }
            double b = -steigung * super.x + super.y;
            double erwartetY = steigung * x1 + b;
            return x1 >= super.x && x1 <= this.x2
                    && y1 >= erwartetY - 2
                    && y1 <= erwartetY + 2;
        }

        @Override
        public void zeichnen(Graphics2D g) {
            g.drawLine(super.x, super.y, this.x2, this.y2);
        }
    }

    class Kreis extends Geo {
        private int radius;

        public Kreis(int x, int y, int radius) {
            super(x, y);
            this.radius = radius;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        @Override
        public boolean istEnthalten(int x1, int y1) {
            int xdiff = (this.x + radius) - x1;
            int ydiff = (this.y + radius) - y1;

            return xdiff * xdiff + ydiff * ydiff <= radius
                    * radius;
        }

        @Override
        public void zeichnen(Graphics2D g) {
            g.drawOval(this.x, this.y, this.radius * 2,
                    this.radius * 2);
        }
    }

    class Rechteck extends Geo {
        private int breite;
        private int hoehe;

        public Rechteck(int x, int y, int breite, int hoehe) {
            super(x, y);
            this.breite = breite;
            this.hoehe = hoehe;
        }

        public int getBreite() {
            return breite;
        }

        public void setBreite(int breite) {
            this.breite = breite;
        }

        public int getHoehe() {
            return hoehe;
        }

        public void setHoehe(int hoehe) {
            this.hoehe = hoehe;
        }

        public int oben() {
            return this.y;
        }

        public int unten() {
            return oben() + hoehe;
        }

        public int links() {
            return this.x;
        }

        public int rechts() {
            return links() + breite;
        }

        public int mitteInY() {
            return y+breite/2;
        }

        public int mitteInX() {
            return (x+breite)/2;
        }

        public void verschiebe(int dx, int dy) {
            this.x += dx;
            this.y += dy;

        }

        public void verschiebeNach(int x, int y) {

        }

        public boolean ueberschneidet(Rechteck other) {
            if (this.oben() + breite < other.unten() || this.unten() > other.oben() + breite) {
                return false;
            }

            if (this.oben() + this.rechts() < other.unten() + this.rechts() || this.links() + hoehe > other.rechts()) {
                return false;
            }
            return true;
        }


        @Override
        public boolean istEnthalten(int x1, int y1) {
            return x1 >= this.x && x1 < this.x + this.breite
                    && y1 >= this.y
                    && y1 < this.y + this.hoehe;
        }

        @Override
        public void zeichnen(Graphics2D g) {
            g.drawRect(this.x, this.y, this.breite, this.hoehe);
        }
    }

    class Punkt extends Geo {

        public Punkt(int x, int y) {
            super(x, y);
        }

        @Override
        public boolean istEnthalten(int x1, int y1) {
            return x1 >= this.x - 2 && x1 < this.x + 2
                    && y1 >= this.y - 2
                    && y1 < this.y + 2;
        }

        @Override
        public void zeichnen(Graphics2D g) {
            g.drawLine(this.x, this.y, this.x, this.y);
        }
    }
}
