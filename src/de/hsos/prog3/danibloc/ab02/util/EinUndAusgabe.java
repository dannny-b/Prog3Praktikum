package de.hsos.prog3.danibloc.ab02.util;/* Einfache Klasse, um direkt Eingaben von der Tastatur/
 * Konsole zu lesen. Die Methoden fangen Fehler ab und geben bei
 * falschen Eingaben "Standardwerte" zur&uuml;ck.
 * @author kleuker
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EinUndAusgabe {

  private static BufferedReader in = new BufferedReader(
      new InputStreamReader(System.in));

  /**
   * Konstruktor zur Erzeugung eines Objekts zur Ein- und
   * Ausgabe.
   */
  public EinUndAusgabe() {
  }

  /**
   * Methode zum Lesen eines Textes von der Konsole, der
   * &uuml;ber die Tastatur eingegeben wird. Die Eingabe
   * endet mit der Return-Taste und darf Leerzeichen
   * enthalten.
   * 
   * @return eingegebener Text
   */
  public String leseString() {
    String ergebnis;
    try {
      ergebnis = in.readLine();
    } catch (IOException e) {
      ergebnis = "";
    }
    return ergebnis;
  }

  /**
   * Methode zum Lesen einer ganzen Zahl von der Konsole,
   * die &uuml;ber die Tastatur eingegeben wird. Die Eingabe
   * endet mit der Return-Taste. Sollte es sich bei der
   * Eingabe um keinen g&uuml;ltigen Wert handeln, wird -1
   * zur&uuml;ckgegeben.
   * 
   * @return eingegebene Zahl
   */
  public int leseInteger() {
    int ergebnis;
    try {
      ergebnis = Integer.decode(leseString()).intValue();
    } catch (NumberFormatException e) {
      ergebnis = -1;
    }

    return ergebnis;
  }

  /**
   * Methode zum Lesen einer Float-Zahl von der Konsole, die
   * &uuml;ber die Tastatur eingegeben wird. Die Eingabe
   * endet mit der Return-Taste. Sollte es sich bei der
   * Eingabe um keinen g&uuml;ltigen Wert handeln, wird -1
   * zur&uuml;ckgegeben.
   * 
   * @return eingegebene Zahl
   */
  public float leseFloat() {
    float ergebnis;
    try {
      ergebnis = Float.valueOf(leseString()).floatValue();
    } catch (NumberFormatException e) {
      ergebnis = -1f;
    }

    return ergebnis;
  }

  /**
   * Methode zum Lesen einer Double-Zahl von der Konsole,
   * die &uuml;ber die Tastatur eingegeben wird. Die Eingabe
   * endet mit der Return-Taste. Sollte es sich bei der
   * Eingabe um keinen g&uuml;ltigen Wert handeln, wird -1
   * zur&uuml;ckgegeben.
   * 
   * @return eingegebene Zahl
   */
  public double leseDouble() {
    double ergebnis;
    try {
      ergebnis = Double.valueOf(leseString()).doubleValue();
    } catch (NumberFormatException e) {
      ergebnis = -1d;
    }

    return ergebnis;
  }

  /**
   * Methode zum Lesen eines Wahrheitswertes von der
   * Konsole, der &uuml;ber die Tastatur eingegeben wird.
   * Die Eingabe endet mit der Return-Taste. Sollte es sich
   * bei der Eingabe nicht um "true" handeln, wird false
   * zur&uuml;ckgegeben.
   * 
   * @return eingegebener Wahrheitswert
   */
  public boolean leseBoolean() {
    boolean ergebnis;
    try {
      ergebnis = Boolean.valueOf(leseString())
          .booleanValue();
    } catch (NumberFormatException e) {
      ergebnis = false;
    }

    return ergebnis;
  }

  /**
   * Methode zur Ausgabe eines &uuml;bergebenen Objektes,
   * genauer wird auf diesem Objekt die toString()-Methode
   * ausgef&uuml;hrt. Es
   * wird kein Zeilenumbruch angeh&auml;ngt.
   * 
   * @param obj
   *          auszugebendes Objekt
   */
  public void ausgeben(Object obj) {
    System.out.print(obj);
  }

  /**
   * Methode zur C-formatierten Ausgabe verschiedener
   * Variablen und Textelemente
   * 
   * @param text
   *          auszugebender Text, der Spezialzeichen wie %d
   *          als Platzhalter f&uuml;r auszugebene Variablen
   *          enthalten kann
   * @param objekte
   *          Objekte, die an Stelle der Platzhalter im Text
   *          ausgegeben werden sollen
   */
  public void formatiertAusgeben(String text,
      Object... objekte) {
    System.out.printf(text, objekte);
  }

  /**
   * Methode zur Erzeugung einer ganzahligen Zufallszahl
   * zwischen (einschlie&szlig;lich) den &uuml;bergebenen
   * Grenzen. Es wird erwartet und nicht gepr&uuml;ft, dass
   * der Endwert nicht kleiner als der Startwert ist.
   * 
   * @param start
   *          minimal m&ouml;glicher Zufallswert
   * @param ende
   *          maximal m&ouml;glicher Zufallswert
   * @return zuf&auml;lliger Wert zwischen start und ende
   *         (auch diese beiden Werte sind m&ouml;glich
   */
  public int zufall(Integer start, Integer ende) {
    return start
        + (int) (Math.random() * (ende - start + 1));
  }
}
