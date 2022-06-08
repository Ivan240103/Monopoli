/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivandesimone.monopoli;

import ivandesimone.monopoli.plancia.Plancia;
import ivandesimone.monopoli.plancia.Ipotecato;
import ivandesimone.monopoli.plancia.Proprietà;
import java.util.ArrayList;

/**
 *
 * @author Ivan
 */
public class Giocatore {

    public final String NOME, PEDINA;
    private int soldi;
    private int posizione;  //numero della casella su cui si trova
    private boolean inPrigione; //per quando il giocatore è in posizione 10, se è true è in prigione, false è in transito
    private int turniInPrigione;    //conteggio dei turni passati in prigione (perchè a 3 sei costretto a pagare ed uscire)
    ArrayList<Ipotecato> possedimenti = new ArrayList<>();
    //-------- variabili per il numero di ipotecati della stessa categoria --------
    private int nRosa, nBlu, nArancione, nMarrone, nRosso, nGiallo, nVerde, nViola, nStazioni, nSocietà;

    public Giocatore(String NOME, String PEDINA) {
        this.NOME = NOME;
        this.PEDINA = PEDINA;
        this.soldi = 0;
        this.posizione = 0;
        this.inPrigione = false;    //di base il giocatore quando passa è in transito
        this.turniInPrigione = 0;
        this.nRosa = 0;
        this.nBlu = 0;
        this.nArancione = 0;
        this.nMarrone = 0;
        this.nRosso = 0;
        this.nGiallo = 0;
        this.nVerde = 0;
        this.nViola = 0;
        this.nStazioni = 0;
        this.nSocietà = 0;
    }

    public int getTurniInPrigione() {
        return turniInPrigione;
    }

    public void setTurniInPrigione(int turniInPrigione) {
        this.turniInPrigione = turniInPrigione;
    }

    public int getPosizione() {
        return posizione;
    }

    public boolean isInPrigione() {
        return inPrigione;
    }

    public int getnStazioni() {
        return nStazioni;
    }

    protected void setSoldi(int soldi) {
        this.soldi = soldi;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public void setInPrigione(boolean inPrigione) {
        this.inPrigione = inPrigione;
    }

    protected int avanza(int numeroDadi) { //avanza del numero di caselle passato, restituisce la posizione della casella di arrivo
        posizione += numeroDadi;
        if (posizione >= 40) {  //dopo il 39 riparte da zero
            //se oltrepassa la 39esima casella vuol dire che è passato dal via (+500€)
            System.out.println("Passaggio dal via: guadagni 500 €!");
            guadagna(500);
            posizione = posizione % 40;
        }
        return posizione;
    }

    protected void aggiungiIpotecato(Ipotecato i) { //aggiunge un ipotecato ai possedimenti
        possedimenti.add(i);    //aggiunge l'ipotecato ai possedimenti
        ordinaPerPosizione();   //ordina la lista
        //in base al tipo aumenta il contatore, che serve per stabilire quanto un giocatore debba pagare se finisce su quella casella
        switch (i.SOTTOTIPO) {
            default:    //errore, non dovrebbe mai capitare
                System.out.println("Errore - Giocatore > aggiungiIpotecato");
                break;
            case "Società": //casella società
                nSocietà++;
                break;
            case "Stazione":    //casella stazione
                nStazioni++;
                break;
            case "Proprietà":
                //se è una proprietà, il contatore va in base al colore
                switch (((Proprietà) i).COLORE) {
                    default:    //errore, non dovrebbe mai capitare
                        System.out.println("Errore - Giocatore > aggiungiIpotecato");
                        break;
                    case "Rosa":
                        nRosa++;
                        break;
                    case "Blu":
                        nBlu++;
                        break;
                    case "Arancione":
                        nArancione++;
                        break;
                    case "Marrone":
                        nMarrone++;
                        break;
                    case "Rosso":
                        nRosso++;
                        break;
                    case "Giallo":
                        nGiallo++;
                        break;
                    case "Verde":
                        nVerde++;
                        break;
                    case "Viola":
                        nViola++;
                        break;
                }
                break;
        }
    }

    protected int paga(int somma) { //sottrae una somma di denaro, restituisce la somma sotratta
        int tr;
        if (somma <= soldi) {    //se ha abbastanza soldi vengono scalati dal conto
            tr = somma;
            soldi -= somma;
        } else { //altrimenti
            // TODO non ci sono abbastanza soldi
            tr = soldi;
        }
        return tr;
    }

    protected void guadagna(int somma) {    //guadagna una somma di denaro (aumenta i soldi del giocatore)
        soldi += somma;
    }

    protected void pagaContrattiIniziali() {    //viene richiamato solo all'inizio del gioco per pagare i contratti distribuiti casualmente
        for (Ipotecato i : possedimenti) {
            paga(i.VALORE_CONTRATTO);
        }
    }

    protected boolean isPosseduto(Ipotecato i) {    //controlla se possiede l'ipotecato passato, ritorna true se lo possiede altrimenti false
        boolean tr = false; //inizialmente presuppone che non sia posseduto
        for (Ipotecato iList : possedimenti) {  //cerca nell'ArrayList dei possedimenti se c'è
            if (iList.NOME.equals(i.NOME)) {
                tr = true;  //se lo trova lo segna per ritornarlo
                break;
            }
        }
        return tr;
    }

    protected boolean possedutiTutti(Ipotecato i) { //ritorna true se il giocatore possiede tutti gli ipotecati della categoria di quello passato
        //viene richiamato solo per gli ipotecati
        boolean tr = false; //inizialmente presuppone non li abbia tutti
        switch (i.SOTTOTIPO) {  //controlla il sottotipo (società, stazione o proprietà)
            default:    //errore, non dovrebbe mai capitare
                System.out.println("Errore - Giocatore > possedutiTutti");
                break;
            case "Società": //società: per averle tutte deve averne 2
                tr = nSocietà == 2;
                break;
            case "Stazione":    //stazioni: per averle tutte deve averne 4
                tr = nStazioni == 4;
                break;
            case "Proprietà":   //proprietà: le controlla in base al colore
                switch (((Proprietà) i).COLORE) {   //controllo del colore dell'ipotecato passato (castato a Proprietà)
                    default:    //errore, non dovrebbe mai capitare
                        System.out.println("Errore - Giocatore > possedutiTutti");
                        break;
                    case "Rosa":    //le rosa sono 2
                        tr = nRosa == 2;
                        break;
                    case "Blu": //le blu sono 3
                        tr = nBlu == 3;
                        break;
                    case "Arancione":   //le arancioni sono 3
                        tr = nArancione == 3;
                        break;
                    case "Marrone": //le marroni sono 3
                        tr = nMarrone == 3;
                        break;
                    case "Rosso":   //le rosse sono 3
                        tr = nRosso == 3;
                        break;
                    case "Giallo":  //le gialle sono 3
                        tr = nGiallo == 3;
                        break;
                    case "Verde":   //le verdi sono 3
                        tr = nVerde == 3;
                        break;
                    case "Viola":   //le viola sono due
                        tr = nViola == 2;
                        break;
                }
                break;
        }
        return tr;
    }

    protected void aumentaTurniInPrigione() {   //aumenta di 1 il numero di turni passati in prigione
        turniInPrigione++;
    }

    protected boolean puoCostruire() {  //ritorna true se il giocatore può costruire (ha almeno un colore completo di proprietà)
        return nRosa == 2 || nBlu == 3 || nArancione == 3 || nMarrone == 3 || nRosso == 3 || nGiallo == 3 || nVerde == 3 || nViola == 2;
    }

    protected String possedimentiSuCuiCostruire() { //ritorna l'elenco delle proprietà su cui può costruire
        String tr = "";
        for (Ipotecato i : possedimenti) {
            if (i.SOTTOTIPO.equals("Proprietà")) {  //solo proprietà, no stazioni no società
                if (possedutiTutti(i)) {
                    i = (Proprietà) i;
                    tr += i + "\n";
                }
            }
        }
        return tr;
    }

    protected void costruisciCasa(int posizione) {  //costruisce una casa sulla proprietà della posizione passata
        for (Ipotecato i : possedimenti) {
            if (posizione == i.POSIZIONE) {  //cerca la posizione tra i possedimenti, se non c'è vuol dire che ha sbagliato numero
                ((Proprietà) i).costruisciCasa();
                break;
            }
        }
    }

    private void ordinaPerPosizione() {    //ordina la lista dei possedimenti per posizione delle caselle nella plancia
        for (int i = 0; i < possedimenti.size() - 1; i++) {
            for (int j = 0; j < possedimenti.size() - 1 - i; j++) {
                if (possedimenti.get(j).POSIZIONE > possedimenti.get(j + 1).POSIZIONE) {
                    Ipotecato app = possedimenti.get(j + 1);
                    possedimenti.set(j + 1, possedimenti.get(j));
                    possedimenti.set(j, app);
                }
            }
        }
    }

    private String possedimentiToString() { //elenco degli ipotecati posseduti (solo nome)
        String tr = "";
        for (Ipotecato i : possedimenti) {
            tr += "    " + i.POSIZIONE + ") " + i.NOME + "\n";
        }
        return tr;
    }

    @Override
    public String toString() {
        Plancia p = new Plancia(false);
        String tr = NOME + " ( " + PEDINA + " ) = casella: " + p.getCasella(posizione).NOME + ", saldo: " + soldi + " €\nPossiede:\n" + possedimentiToString();
        return tr;
    }

}
