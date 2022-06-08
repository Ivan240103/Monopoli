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
public class Dado {

    protected static int lancia() {    //ritorna un numero casuale tra 1 e 6
        return (int) (Math.random() * 6 + 1);
    }
}
