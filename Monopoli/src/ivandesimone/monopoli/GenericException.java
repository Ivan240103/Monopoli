/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivandesimone.monopoli;

/**
 *
 * @author Ivan
 */
public class GenericException extends Exception {

    //eccezione generica da lanciare dove serve (in qualsiasi situazione)
    public GenericException() {
        super("Errore indefinito, l'avanzamento della partita potrebbe essere compromesso.");
    }

}
