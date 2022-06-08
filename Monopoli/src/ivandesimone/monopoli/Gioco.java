/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivandesimone.monopoli;

import java.util.Scanner;

/**
 *
 * @author Ivan
 */
public class Gioco {

    public static void main(String args[]) {
        //istanze
        Scanner in = new Scanner(System.in);
        GestioneGioco gg = new GestioneGioco(); //con la creazione ottiene il numero dei partecipanti al gioco
        GestisciProbabilità gp = new GestisciProbabilità(); //per pescare le carte probabilità
        GestisciImprevisti gi = new GestisciImprevisti();   //per pescare le carte imprevisti

        //variabili del main
        boolean partitaFinita = false;  //condizione per il termine della partita
        boolean turnoFinito;    //condizione per il termine del turno
        boolean doppio;
        int numeroDoppi;    //numero di volte in cui il giocatore ha fatto doppio di fila
        Giocatore giocatoreAttuale;    //giocatore che sta giocano il turno
        boolean x = true;   //condizione uscita ciclo scelte del giocatore

        //PREPARAZIONE DEL GIOCO
        gg.preparaGiocatori();  //preparazione giocatori
        System.out.println("Ecco la situazione iniziale dei " + gg.PARTECIPANTI + " giocatori:\n" + gg.toString());

        System.out.println("Si lanciano i dadi, chi fa il numero maggiore inizia.\n"
                + "In caso di parità comincia chi ha fatto il numero maggiore per primo.\n");
        giocatoreAttuale = gg.decidiChiInizia();
        System.out.println("\nIl primo a giocare sarà " + giocatoreAttuale.NOME + "!");

        //INIZIO DELLA PARTITA
        do {

            //INIZIO DEL TURNO GIOCATORE
            numeroDoppi = 0; //segna quante volte il giocatore ha fatto doppio (tre volte di fila va in prigione)
            do {
                doppio = false; //segna se il giocatore ha fatto doppio lanciando i dadi

                //scelte possibili del giocatore TODO
                do {
                    menuSceltaGiocatore1();
                    int n = in.nextInt();
                    switch (n) {
                        default:    //nessuna azione
                            x = false;
                            break;
                        case 1: //costruisci
                            if (giocatoreAttuale.puoCostruire()) {   //controlla se il giocatore può costruire
                                try {
                                    gg.costruzione();
                                } catch (CaseMancantiException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                System.out.println("Devi possedere tutte le proprietà di almeno un colore per poter costruire.");
                            }
                            break;
                    }
                } while (x);
                x = true;

                if (giocatoreAttuale.isInPrigione()) {    //controlla se il giocatore è in prigione
                    giocatoreAttuale.aumentaTurniInPrigione();
                    //se è in prigione ha tre metodi per uscirne
                    System.out.println("\nCome vuoi uscire di prigione?\n1) Paga € 125. 2) Prova a fare doppio lanciando i dadi. 3) Usa un cartoncino Imprevisti o Probabilità.");
                    switch (in.nextInt()) {
                        case 1: //pagare la somma di 125€
                            System.out.println(giocatoreAttuale.NOME + " ha deciso di pagare i " + giocatoreAttuale.paga(125) + " €.");
                            gg.esciDiPrigione();
                            break;
                        case 3: //usare un cartoncino "esci gratis di prigione"
                            // TODO implementare cartoncino "esci di prigione"
                            break;
                        default:    //lanciare i dati e provare a fare doppio
                            System.out.println(giocatoreAttuale.NOME + " tenterà la sorte cercando di ottenere un doppio!");
                            break;
                    }
                }
                //i dati verranno lanciati indipendentemente dal fatto che il giocatore sia in prigione o ne sia uscito
                int dado1 = Dado.lancia();  //lancio del primo dado
                int dado2 = Dado.lancia();  //lancio del secondo dado
                int sommaDadi = dado1 + dado2;  //totale di caselle da avanzare
                String dadi = "\nLancio dei dadi, sono usciti i numeri " + dado1 + " e " + dado2;
                if (dado1 == dado2) {    //controlla se ha fatto doppio
                    doppio = true;  //segno che dovrà ritirare i dadi
                    numeroDoppi++;  //aumenta il numero di doppi consecutivi
                    dadi += " (doppio!)";
                    if (numeroDoppi == 3) { //se ha fatto tre doppi dritto in prigione
                        System.out.println(dadi + "\nHai fatto doppio per tre volte di fila... Finisci diretto in prigione senza passare dal VIA!");
                        gg.mandaInPrigione();   //sposta il giocatore sulla casella prigione e lo segna come in prigione
                        giocatoreAttuale = gg.avanzaTurno();    //passa il turno al giocatore dopo
                        System.out.println("Fine del turno, il prossimo a giocare è " + giocatoreAttuale.NOME + ".");
                        break;
                    }
                }

                if (giocatoreAttuale.isInPrigione()) { //se il giocatore non è precedentemente uscito di prigione
                    if (doppio) {    //se ha appena fatto doppio è libero e poi avanzerà
                        gg.esciDiPrigione();
                    } else {    //se non ha fatto doppio
                        //se è il suo terzo turno in prigione è costretto a pagare i 125€ ed esce
                        if (giocatoreAttuale.getTurniInPrigione() == 3) {
                            System.out.println(giocatoreAttuale.NOME + " è costretto a pagare i " + giocatoreAttuale.paga(125) + " €.");
                            gg.esciDiPrigione();
                        } else {
                            System.out.println("Niente doppio! Ritenterai al prossimo turno.");
                        }
                    }
                }

                if (!giocatoreAttuale.isInPrigione()) {    //se il giocatore è fuori di prigione avanza e gioca, altrimenti resta dov'è
                    System.out.println(dadi + ". Avanzamento di " + sommaDadi + " caselle...");
                    //avanza il giocatore del numero di caselle stabilito dal lancio dai dadi
                    System.out.println(giocatoreAttuale.NOME + " è finito sulla casella " + gg.avanzaCaselle(sommaDadi));

                    //in base al tipo della casella su cui finisce il giocatore vengono eseguite delle azioni
                    switch (gg.getCasellaAttuale().TIPO) {   //ottiene il tipo della casella di destinazione
                        default:    //caso default, non dovrebbe mai succedere, aggiunto per sicurezza
                            System.out.println("Errore nell'operazione da svolgere in base alla casella di destinazione.");
                            break;
                        case "Neutra":  //casella in cui non occorre fare alcun azione (via, transito, posteggio)
                            System.out.println("Casella neutra. Nessuna operazione richiesta.");
                            break;
                        case "PrigioneTransito":    //casella prigione/transito
                            //dato che ci è finito sopra è in transito, nessuna azione
                            System.out.println("Casella neutra. Nessuna operazione richiesta.");
                            break;
                        case "InPrigione":  //casella in prigione
                            //azione da eseguire: mandare il giocatore in prigione
                            System.out.println("Finisci direttamente in prigione.");
                            gg.mandaInPrigione();   //il giocatore attuale finisce in prigione
                            break;
                        case "Tassa":   //casella tassa (di lusso o patrimoniale)
                            //azione da eseguire: pagare l'importo della tassa
                            //al giocatore vengono rimossi soldi pari all'importo della tassa su cui è capitato
                            System.out.println("Paga " + giocatoreAttuale.paga(gg.getImportoTassa()) + "€ alla banca.");
                            break;
                        case "Imprevisti":  //casella imprevisti
                            //azione da eseguire: pescare la carta imprevisti
                            gi.pescaImprevisto();
                            break;
                        case "Probabilità": //casella probabilità
                            //azione da eseguire: pescare la carta probabilità
                            gp.pescaProbabilità();
                            break;
                        case "Ipotecato":   //casella di un ipotecato
                            if (gg.isDisponibile(gg.getCasellaAttuale())) {  //controlla se la casella è disponibile all'acquisto o è già posseduta
                                //se è libera
                                System.out.print("La proprietà non è posseduta da nessuno, inserisci 1 per acquistarla, 0 per transitare solamente: ");
                                if (in.nextInt() == 1) {
                                    System.out.println(giocatoreAttuale.NOME + " paga la somma di " + gg.acquistaIpotecatoLibero() + "€ per acquistare " + gg.getCasellaAttuale().NOME + ".");
                                } else {
                                    System.out.println(giocatoreAttuale.NOME + " decide di transitare e basta.");
                                }
                            } else {
                                //se è posseduta da qualcuno
                                String proprietario = gg.trovaPossessore(gg.getCasellaAttuale()).NOME;  //proprietario della casella su cui è finito il giocatore
                                int importoDovuto = gg.getImportoIpotecato();   //importo da pagare
                                if (giocatoreAttuale.NOME.equals(proprietario)) { //controlla se è finito su una casella di sua proprietà
                                    System.out.println("L'ipotecato è di sua proprietà.");
                                } else {    //se non lo è deve pagare
                                    //toglie i soldi al giocatore finito sulla casella
                                    System.out.print(giocatoreAttuale.NOME + " paga la cifra di " + giocatoreAttuale.paga(importoDovuto) + " a " + proprietario + ".");
                                    //li dà al giocatore che possiede la casella
                                    gg.trovaPossessore(gg.getCasellaAttuale()).guadagna(importoDovuto);
                                }
                            }
                            break;
                    }
                }

                //altre scelte possibili del giocatore TODO
                do {
                    menuSceltaGiocatore2();
                    int n = in.nextInt();
                    switch (n) {
                        default:    //nessuna azione
                            x = false;
                            break;
                        case 1: //costruisci
                            if (giocatoreAttuale.puoCostruire()) {   //controlla se il giocatore può costruire

                            } else {
                                System.out.println("Devi possedere tutte le proprietà di almeno un colore per poter costruire.");
                            }
                            break;
                    }
                } while (x);
                x = true;

                if (doppio) {   //se ha fatto doppio non si passa alla persona dopo
                    turnoFinito = false;    //segna che il giocatore attuale non ha finito
                    System.out.println("Fine del turno, avendo fatto doppio tocca ancora a " + giocatoreAttuale.NOME + ".");
                } else {
                    turnoFinito = true; //segna che il giocatore attuale ha finito
                    giocatoreAttuale = gg.avanzaTurno();    //passa al giocatore dopo
                    System.out.println("Fine del turno, il prossimo a giocare è " + giocatoreAttuale.NOME + ".");    //passa il turno al giocatore dopo
                }
            } while (!turnoFinito); //continua il ciclo finchè il turno non è concluso
        } while (!partitaFinita);   //continua finchè non termina la partita
    }

    private static void menuSceltaGiocatore1() {
        System.out.println("Inserisci il numero in base all'azione che vuoi compiere:\n"
                + "1) Costruisci una casa/un albergo. 2) Vendi una casa/un albergo. 3) Proponi uno scambio ad un altro giocatore.\n"
                + "0) Lancia i dadi.");
    }

    private static void menuSceltaGiocatore2() {
        System.out.println("Inserisci il numero in base all'azione che vuoi compiere:\n"
                + "1) Costruisci una casa/un albergo. 2) Vendi una casa/un albergo. 3) Proponi uno scambio ad un altro giocatore.\n"
                + "0) Finisci il turno.");
    }
}
