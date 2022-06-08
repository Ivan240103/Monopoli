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
public class GestisciProbabilità {

    /*c'è un array che contiene i 16 casi delle carte (rappresentati dal numero int). Viene estratta una posizione casuale
    e il numero che vi è dentro viene messo dentro un arraylist. Si ottiene così una serie casuale di cases che si ripete*/
    private int array[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};   //array dei case
    private int posArray = array.length;    //numero di case rimasti nell'array

    private ArrayList<Integer> ordineMescolato = new ArrayList<>(); //contiene l'ordine di pescaggio dal mazzetto
    private int posPesca;   //prossima posizione dell'arrayList da cui pescare

    public GestisciProbabilità() {
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

    public void pescaProbabilità() {    //attua l'azione dettata dalla carta
        switch (caseCartaPescata()) {   //il case viene dato dall'arraylist
            default:    //errore, non dovrebbe mai succedere
                System.out.println("Errore - GestisciProbabiità > pescaProbabilità");
                break;
            case 0:
                System.out.println("È maturata la cedola delle vostre azioni: ritirate € 60.");
                break;
            case 1:
                System.out.println("Ereditate da un lontano parente € 250.");
                break;
            case 2:
                System.out.println("Ritornate al Vicolo Corto.");
                break;
            case 3:
                System.out.println("Avete vinto il secondo premio ad un concorso di bellezza: ritirate € 25.");
                break;
            case 4:
                System.out.println("Avete perso una causa: pagate € 250.");
                break;
            case 5:
                System.out.println("È il vostro compleanno: ogni giocatore vi regala € 25.");
                break;
            case 6:
                System.out.println("Rimborso tassa sul reddito: ritirate € 50 dalla banca.");
                break;
            case 7:
                System.out.println("Uscite gratis di prigione, se ci siete: potete conservare questo cartoncino sino al momento di servirvene (non si sa mai!) oppure venderlo.");
                break;
            case 8:
                System.out.println("Siete creditori verso la banca di € 500: ritirateli!");
                break;
            case 9:
                System.out.println("Andate in prigione direttamente e senza pasare dal «Via!»");
                break;
            case 10:
                System.out.println("Avete venduto delle azioni: ricavate € 125.");
                break;
            case 11:
                System.out.println("Pagate una multa di € 25, oppure prendete un cartoncino dagli «imprevisti».");
                break;
            case 12:
                System.out.println("Andate fino al «Via!».");
                break;
            case 13:
                System.out.println("Scade il vostro premio di assicurazione: pagate € 125.");
                break;
            case 14:
                System.out.println("Avete vinto un premio di consolazione alla Lotteria di Merano: ritirate € 250.");
                break;
            case 15:
                System.out.println("Pagate il conto del Dottore: € 125.");
                break;
        }
    }
}
