/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivandesimone.monopoli;

import java.util.InputMismatchException;
import java.util.Scanner;
import ivandesimone.monopoli.plancia.Plancia;
import ivandesimone.monopoli.plancia.Casella;
import ivandesimone.monopoli.plancia.Ipotecato;
import ivandesimone.monopoli.plancia.Tassa;
import ivandesimone.monopoli.plancia.Proprietà;
import ivandesimone.monopoli.plancia.Società;
import ivandesimone.monopoli.plancia.Stazione;
import java.util.ArrayList;

/**
 *
 * @author Ivan
 */
public class GestioneGioco {

    Scanner in = new Scanner(System.in);
    Plancia plancia = new Plancia(true);    //crea la plancia per giocare

    public final int PARTECIPANTI;  //numero di giocatori partecipanti
    private Giocatore giocatori[];    //array dei giocatori -> non final perchè quando un giocatore viene eliminato và rimosso TODO
    private int turno;  //posizione nell'array del giocatore a cui tocca
    private ArrayList<String> pedine = new ArrayList<>();   //lista delle pedine tra cui scegliere (inizialmente vuota)

    private int numeroCase;    //case disponibili per la costruzione
    private int numeroAlberghi;    //alberghi disponibili per la costruzione

    public GestioneGioco() {
        this.numeroCase = 32;
        this.numeroAlberghi = 12;

        pedine.add("Cactus");   //aggiunge le pedine alla lista
        pedine.add("Fungo");
        pedine.add("Candela");
        pedine.add("Pera");
        pedine.add("Fiasco di vino");
        pedine.add("Cappello");

        int num = 0;    //riceve il numero di partecipanti al gioco
        do {
            try {
                System.out.print("Quante persone parteciperanno al gioco? (min: 2 - max: 6) ");
                num = in.nextInt();
            } catch (InputMismatchException ex) {
                in.nextLine();
                System.out.println("Devi inserire un numero.");
            }
        } while (num < 2 || num > 6);   //lo chiede finchè non viene inserito un numero valido
        this.PARTECIPANTI = num;

        giocatori = new Giocatore[num]; //inizializza l'array
    }

    protected void preparaGiocatori() {  //prepara i giocatori per l'inizio del gioco
        aggiungiPartecipanti();    //crea i giocatori
        System.out.println("Vengono distribuiti i soldi e gli ipotecati casuali di inizio gioco...\n");
        distribuisci();    //distribuisce i soldi iniziali e assegna i primi ipotecati (casuali)
        System.out.println("Ogni giocatore deve pagare alla banca il valore dei contratti che ha ricevuto.\n");
        pagaContrattiIniziali();   //pagamento del valore dei contratti iniziali
    }

    private void aggiungiPartecipanti() {  //aggiunge i partecipanti all'array di giocatori
        for (int i = 0; i < PARTECIPANTI; i++) {
            in.nextLine();

            System.out.println("Dati del giocatore " + (i + 1) + ":");  //inserire i dati del giocatore
            System.out.print("Nome: ");
            String nome = in.nextLine();

            String pedina = scegliPedina();

            giocatori[i] = new Giocatore(nome, pedina); //creazione ed aggiunta giocatore
        }
    }

    private String scegliPedina() { //ritorna la pedina scelta dall'utente
        int n;
        do {
            System.out.println("Scegli il numero associato alla pedina che vuoi usare:");

            System.out.print("0) " + pedine.get(0));    //mostra le pedine disponibili in output
            for (int i = 1; i < pedine.size(); i++) {
                System.out.print(" - " + i + ") " + pedine.get(i));
            }

            System.out.println(""); //riga bianca per ordinare l'output
            n = in.nextInt();   //posizione nell'array della pedina scelta dall'utente
        } while (n < 0 || n >= pedine.size());  //la scelta non può essere al di fuori delle opzioni
        String scelta = pedine.remove(n);  //salva la pedina scelta
        System.out.println("Selezionata la pedina " + scelta + ".\n");

        return scelta;
    }

    private void distribuisci() {    //distribuisce i soldi ed i primi contratti ai giocatori in base al numero dei partecipanti
        int soldi = 0;
        switch (PARTECIPANTI) {   //in base al numero dei giocatori stabilisce la cifra da assegnare
            case 2:
                soldi = 8750;
                break;
            case 3:
                soldi = 7500;
                break;
            case 4:
                soldi = 6250;
                break;
            case 5:
                soldi = 5000;
                break;
            case 6:
                soldi = 3750;
                break;
        }
        for (int i = 0; i < PARTECIPANTI; i++) {    //assegna i soldi ad ogni giocatore
            giocatori[i].setSoldi(soldi);
        }

        for (int i = 0; i < 9 - PARTECIPANTI; i++) {    //assegna i primi ipotecati (casuali), il numero di contratti corrisponde a 9 - il numero dei giocatori
            for (int j = 0; j < PARTECIPANTI; j++) {
                giocatori[j].aggiungiIpotecato(plancia.fornisciContrattoCasuale());
            }
        }
    }

    private void pagaContrattiIniziali() {  //pagamento dei contratti iniziali
        for (int i = 0; i < PARTECIPANTI; i++) { //ogni giocatore paga il suo
            giocatori[i].pagaContrattiIniziali();
        }
    }

    protected Giocatore decidiChiInizia() {   //lancio dei dadi per vedere chi inizia, ritorna il giocatore che fa il numero maggiore
        int numeroUscito;   //risultato del lancio dei due dadi
        int max = 0;    //numero massimo uscito
        for (int i = 0; i < PARTECIPANTI; i++) {
            numeroUscito = Dado.lancia() + Dado.lancia();
            System.out.println(giocatori[i].NOME + " lancia i dadi: ha fatto " + numeroUscito + ".");
            if (numeroUscito > max) {
                max = numeroUscito;
                turno = i;  //segna la posizione nell'array del giocatore che ha fatto il massimo
            }
        }
        return giocatori[turno];
    }

    protected Giocatore avanzaTurno() {   //passa il turno al giocatore dopo, ritorna il giocatore del turno attuale (appena avanzato)
        turno = (turno + 1) % giocatori.length; //passa al giocatore dopo nell'array, arrivato alla fine riparte dall'inizio
        return giocatori[turno];
    }

    protected String avanzaCaselle(int numeroCaselle) { //avanza il giocatore del numero di caselle, ritorna il nome della casella di destinazione
        return plancia.getCasella(giocatori[turno].avanza(numeroCaselle)).NOME;
    }

    protected Casella getCasellaAttuale() { //ritorna la casella su cui si trova il giocatore attuale
        return plancia.getCasella(giocatori[turno].getPosizione());
    }

    protected void mandaInPrigione() {  //manda il giocatore attuale in prigione
        giocatori[turno].setPosizione(10);  //imposta la sua posizione su 10 (prigione/transito)
        giocatori[turno].setInPrigione(true);   //segna che il giocatore è in prigione e non in transito
    }

    protected void esciDiPrigione() {   //imposta che il giocatore attuale non è più in prigione, è in transito
        giocatori[turno].setInPrigione(false);
        giocatori[turno].setTurniInPrigione(0); //porta il numero di turni passati in prigione a zero
    }

    protected int getImportoTassa() {   //ritorna l'importo da pagare della tassa
        //viene richiamato solo quando il giocatore finisce su di una casella di tipo Tassa
        Tassa t = (Tassa) getCasellaAttuale();  //ottiene la casella e fa il casting a tassa
        return t.IMPORTO;   //ritorna l'importo
    }

    protected int getImportoIpotecato() {   //restituisce l'importo da pagare della casella attuale
        int tr = 0;
        Ipotecato i = (Ipotecato) getCasellaAttuale();  //casella attuale, è per forza un ipotecato
        //controlla se il possessore dell'ipotecato possiede tutti i contratti di quella categoria (o colore nel caso delle proprietà)
        if (!(trovaPossessore(getCasellaAttuale()).possedutiTutti(i))) {
            switch (i.SOTTOTIPO) {   //in base al tipo di ipotecato l'importo cambia
                default:    //errore, non dovrebbe mai capitare
                    System.out.println("Errore - GestioneGioco > getImportoIpotecato");
                    break;
                case "Società": //se non ha tutte le società vuol dire che ne ha una sola
                    tr = ((Società) i).RENDITA_1SCT;
                    break;
                case "Stazione":    //se non ha tutte le stazioni ne ha minimo 1 e massimo 3
                    switch (trovaPossessore(getCasellaAttuale()).getnStazioni()) {  //controlla il numero di stazioni possedute
                        default:    //errore, non dovrebbe mai capitare
                            System.out.println("Errore - GestioneGioco > getImportoIpotecato");
                            break;
                        case 1: //1 stazione
                            tr = ((Stazione) i).RENDITA_1STZ;
                            break;
                        case 2: //2 stazioni
                            tr = ((Stazione) i).RENDITA_2STZ;
                            break;
                        case 3: //3 stazioni
                            tr = ((Stazione) i).RENDITA_3STZ;
                            break;
                    }
                    break;
                case "Proprietà":   //se non ha tutte le proprietà di un colore si paga la cifra base
                    tr = ((Proprietà) i).RENDITA_TERRENO;
                    break;
            }
        } else {    //se invece li ha tutti di una categoria o colore
            switch (i.SOTTOTIPO) {   //in base al tipo l'importo cambia
                default:    //errore, non dovrebbe mai capitare
                    System.out.println("Errore - GestioneGioco > getImportoIpotecato");
                    break;
                case "Società": //se ha tutte le società vuol dire che ne ha 2
                    tr = ((Società) i).RENDITA_2SCT;
                    break;
                case "Stazione":    //se ha tutte le stazioni vuol dire che ne ha 4
                    tr = ((Stazione) i).RENDITA_4STZ;
                    break;
                case "Proprietà":   //se ha tutte le proprietà di un colore
                    if (((Proprietà) i).hasAlbergo()) { //controlla se vi è costruito un albergo sopra
                        tr = ((Proprietà) i).RENDITA_ALBERGO;
                    } else {
                        //se non c'è l'albergo controlla il numero di case
                        switch (((Proprietà) i).getNumeroCase()) {
                            default:    //errore, non dovrebbe mai capitare
                                System.out.println("Errore - GestioneGioco > getImportoIpotecato");
                                break;
                            case 0: //con zero case si paga la cifra normale raddoppiata
                                tr = ((Proprietà) i).RENDITA_TERRENO * 2;
                                break;
                            case 1: //1 casa
                                tr = ((Proprietà) i).RENDITA_1CASA;
                                break;
                            case 2: //2 case
                                tr = ((Proprietà) i).RENDITA_2CASE;
                                break;
                            case 3: //3 case
                                tr = ((Proprietà) i).RENDITA_3CASE;
                                break;
                            case 4: //4 case
                                tr = ((Proprietà) i).RENDITA_4CASE;
                                break;
                        }
                    }
                    break;
            }
        }
        return tr;  //ritorna l'importo
    }

    protected int acquistaIpotecatoLibero() {   //acquista l'ipotecato su cui è finito, ritorna la cifra pagata
        Ipotecato i = (Ipotecato) getCasellaAttuale();  //casting da casella ad ipotecato
        int tr = giocatori[turno].paga(i.VALORE_CONTRATTO); //pagamento del valore di contratto
        giocatori[turno].aggiungiIpotecato(i);  //aggiunge l'ipotecato alla lista possedimenti del giocatore
        return tr;
    }

    protected boolean isDisponibile(Casella c) {    //ritorna true se la casella passata è disponibile all'acquisto, false se è già posseduta
        return plancia.isDisponibile(c);
    }

    protected Giocatore trovaPossessore(Casella c) {    //ritorna il giocatore che possiede l'ipotecato (da usare quando si sa che l'ipotecato è posseduto da qualcuno)
        Giocatore tr = null;
        Ipotecato i = (Ipotecato) c;
        for (Giocatore g : giocatori) { //scorre l'array dei giocatori alla ricerca del possessore
            if (g.isPosseduto(i)) {
                tr = g; //segna il possessore
                break;
            }
        }
        return tr;
    }

    protected void costruzione() throws CaseMancantiException {
        if (numeroCase > 0) {
            System.out.println("Inserisci il numero corrispondente alla proprietà su cui costruire:\n" + giocatori[turno].possedimentiSuCuiCostruire());
            int pos = in.nextInt();
            giocatori[turno].costruisciCasa(pos);
        } else {
            throw new CaseMancantiException();
        }
    }

    @Override
    public String toString() {
        String tr = "";
        for (int i = 0; i < PARTECIPANTI; i++) {
            tr += giocatori[i];
        }
        return tr;
    }
}
