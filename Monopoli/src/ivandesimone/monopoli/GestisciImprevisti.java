/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivandesimone.monopoli;

import java.util.ArrayList;

/**
 *
 * @author Ivan
 */
public class GestisciImprevisti {

    /*c'è un array che contiene i 16 casi delle carte (rappresentati dal numero int). Viene estratta una posizione casuale
    e il numero che vi è dentro viene messo dentro un arraylist. Si ottiene così una serie casuale di cases che si ripete*/
    private int array[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};   //array dei case
    private int posArray = array.length;    //numero di case rimasti nell'array

    private ArrayList<Integer> ordineMescolato = new ArrayList<>(); //contiene l'ordine di pescaggio dal mazzetto
    private int posPesca;   //prossima posizione dell'arrayList da cui pescare

    public GestisciImprevisti() {
        for (int i = 0; i < 16; i++) {  //preleva casualmente i 16 numeri e li inserisce nell'ArrayList
            ordineMescolato.add(prelevaValore((int) (Math.random() * posArray)));
        }
        this.posPesca = 0;
    }

    private int prelevaValore(int pos) {    //preleva il numero dall'array in posizione scelta e lo ritorna
        int tr = array[pos];    //numero da ritornare
        for (int i = pos; i < posArray - 1; i++) {  //scala gli altri numeri per chiudere il buco
            array[i] = array[i + 1];
        }
        posArray--; //diminuisce il numero di numeri rimasti nell'array
        return tr;
    }

    private int caseCartaPescata() {    //ritorna il numero del case da attuare
        int tr = ordineMescolato.get(posPesca); //ottiene il numero del case prelevando dall'arraylist in posizione di pescaggio
        posPesca = (posPesca + 1) % ordineMescolato.size(); //sposta la posizione da cui pescare a quella dopo. Arrivato alla fine ricomincia da 0
        return tr;
    }

    public void pescaImprevisto() { //attua l'azione dettata dalla carta
        switch (caseCartaPescata()) {   //il case da usare va in base all'arraylist
            default:    //errore, non dovrebbe mai succedere
                System.out.println("Errore - GestisciImprevisti > pescaImprevisto");
                break;
            case 0:
                System.out.println("Andate in prigione direttamente e senza pasare dal «Via!»");
                break;
            case 1:
                System.out.println("La banca vi paga gli interessi del vostro Conto Corrente, ritirate € 125.");
                break;
            case 2:
                System.out.println("Multa di € 40 per aver guidato senza patente.");
                break;
            case 3:
                System.out.println("Andate sino a Via Accademia: se passate dal «Via!» ritirate € 500.");
                break;
            case 4:
                System.out.println("Andate sino al Largo Colombo: se passate dal «Via!» ritirate € 500.");
                break;
            case 5:
                System.out.println("Avete tutti i vostri stabili da riparare: pagare € 60 per ogni casa e € 250 per ogni albergo.");
                break;
            case 6:
                System.out.println("Fate tre passi indietro (con tanti auguri!).");
                break;
            case 7:
                System.out.println("Maturano le cedole delle vstre cartelle di rendita, ritirate € 375.");
                break;
            case 8:
                System.out.println("Andate fino al Parco della Vittoria.");
                break;
            case 9:
                System.out.println("Matrimonio in famiglia: spese impreviste € 375.");
                break;
            case 10:
                System.out.println("Avete vinto un terno al lotto: ritirate € 250.");
                break;
            case 11:
                System.out.println("Andate avanti sino al «Via!».");
                break;
            case 12:
                System.out.println("Versate € 50 per beneficenza.");
                break;
            case 13:
                System.out.println("Dovete pagare un contributo di miglioria stradale, € 100 per ogni casa, € 250 per ogni albergo che possedete.");
                break;
            case 14:
                System.out.println("Uscite gratis di prigione, se ci siete: potete conservare questo cartoncino sino al momento di servirvene (non si sa mai!) oppure venderlo.");
                break;
            case 15:
                System.out.println("Andate alla Stazione Nord: se passate dal «Via!» ritirate € 500.");
                break;
        }
    }
}
