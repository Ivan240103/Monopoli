/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivandesimone.monopoli.plancia;

/**
 *
 * @author Ivan
 */
public class Società extends Ipotecato {

    public final int RENDITA_1SCT, RENDITA_2SCT;

    //NOME, SOTTOTIPO, POSIZIONE, VALORE_CONTRATTO
    public Società(String NOME, int POSIZIONE) {
        super(NOME, "Società", POSIZIONE, 380);
        this.RENDITA_1SCT = 50;
        this.RENDITA_2SCT = 250;
    }

}
