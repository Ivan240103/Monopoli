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
public class Proprietà extends Ipotecato {

    public final String COLORE;
    public final int COSTO_EDIFICIO;
    public final int RENDITA_TERRENO;
    public final int RENDITA_1CASA, RENDITA_2CASE, RENDITA_3CASE, RENDITA_4CASE;
    public final int RENDITA_ALBERGO;
    private int numeroCase;
    private boolean albergo;    //true se vi è costruito sopra un albergo, false se non c'è

    //NOME, SOTTOTIPO, POSIZIONE, VALORE_CONTRATTO
    public Proprietà(String NOME, int POSIZIONE, int VALORE_CONTRATTO, String COLORE, int COSTO_EDIFICIO, int RENDITA_TERRENO, int RENDITA_1CASA, int RENDITA_2CASE, int RENDITA_3CASE, int RENDITA_4CASE, int RENDITA_ALBERGO) {
        super(NOME, "Proprietà", POSIZIONE, VALORE_CONTRATTO);
        this.COLORE = COLORE;
        this.COSTO_EDIFICIO = COSTO_EDIFICIO;
        this.RENDITA_TERRENO = RENDITA_TERRENO;
        this.RENDITA_1CASA = RENDITA_1CASA;
        this.RENDITA_2CASE = RENDITA_2CASE;
        this.RENDITA_3CASE = RENDITA_3CASE;
        this.RENDITA_4CASE = RENDITA_4CASE;
        this.RENDITA_ALBERGO = RENDITA_ALBERGO;
        this.numeroCase = 0;
        this.albergo = false;
    }

    public int getNumeroCase() {
        return numeroCase;
    }

    public boolean hasAlbergo() {
        return albergo;
    }

    public void costruisciCasa() {
        numeroCase++;
    }

    public void vendiCasa() {
        numeroCase--;
    }

    public void costruisciAlbergo() {
        albergo = true;
    }

    public void vendiAlbergo() {
        albergo = false;
    }

    @Override
    public String toString() {
        return POSIZIONE + ") " + NOME + " (" + COLORE + ") = Rendita terreno: " + RENDITA_TERRENO + ", Rendita 1 casa: " + RENDITA_1CASA + ", Rendita 2 case: " + RENDITA_2CASE
                + ", Rendita 3 case: " + RENDITA_3CASE + ", Rendita 4 case: " + RENDITA_4CASE + ", Rendita albergo: " + RENDITA_ALBERGO;
    }

}
