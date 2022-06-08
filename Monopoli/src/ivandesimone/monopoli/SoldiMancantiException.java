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
public class SoldiMancantiException extends Exception {

    public SoldiMancantiException() {
        super("Non ci sono abbastanza soldi.");
    }

}
