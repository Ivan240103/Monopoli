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
public class Stazione extends Ipotecato {

    public final int RENDITA_1STZ, RENDITA_2STZ, RENDITA_3STZ, RENDITA_4STZ;

    //NOME, SOTTOTIPO, POSIZIONE, VALORE_CONTRATTO
    public Stazione(String NOME, int POSIZIONE) {
        super(NOME, "Stazione", POSIZIONE, 480);
        this.RENDITA_1STZ = 60;
        this.RENDITA_2STZ = 120;
        this.RENDITA_3STZ = 240;
        this.RENDITA_4STZ = 480;
    }
}
