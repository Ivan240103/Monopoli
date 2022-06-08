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
public class Ipotecato extends Casella {

    public final int VALORE_CONTRATTO, VALORE_IPOTECARIO;
    public final String SOTTOTIPO;  //proprietà, società o stazione
    public final int POSIZIONE;  //numero della casella nella plancia

    //NOME, TIPO
    public Ipotecato(String NOME, String SOTTOTIPO, int POSIZIONE, int VALORE_CONTRATTO) {
        super(NOME, "Ipotecato");
        this.POSIZIONE = POSIZIONE;
        this.VALORE_CONTRATTO = VALORE_CONTRATTO;
        this.VALORE_IPOTECARIO = VALORE_CONTRATTO / 2;
        this.SOTTOTIPO = SOTTOTIPO;
    }
}
