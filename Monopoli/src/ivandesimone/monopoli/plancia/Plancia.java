/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivandesimone.monopoli.plancia;

import java.util.ArrayList;

/**
 *
 * @author Ivan
 */
public class Plancia {

    private final Casella plancia[] = { //crea la plancia all'avvio del gioco
        new Via(), //0
        new VicoloCorto(),
        new Probabilità(),
        new VicoloStretto(),
        new TassaPatrimoniale(),
        new StazioneSud(), //5
        new BastioniGranSasso(),
        new Imprevisti(),
        new VialeMonterosa(),
        new VialeVesuvio(),
        new PrigioneTransito(), //10
        new ViaAccademia(),
        new SocietàElettrica(),
        new CorsoAteneo(),
        new PiazzaUniversità(),
        new StazioneOvest(), //15
        new ViaVerdi(),
        new Probabilità(),
        new CorsoRaffaello(),
        new PiazzaDante(),
        new PosteggioGratuito(), //20
        new ViaMarcoPolo(),
        new Imprevisti(),
        new CorsoMagellano(),
        new LargoColombo(),
        new StazioneNord(), //25
        new VialeCostantino(),
        new VialeTraiano(),
        new SocietàAcquaPotabile(),
        new PiazzaGiulioCesare(),
        new InPrigione(), //30
        new ViaRoma(),
        new CorsoImpero(),
        new Probabilità(),
        new LargoAugusto(),
        new StazioneEst(), //35
        new Imprevisti(),
        new VialeDeiGiardini(),
        new TassaDiLusso(),
        new ParcoDellaVittoria()};

    private ArrayList<Ipotecato> contrattiDisponibili = new ArrayList<>();

    public Plancia(boolean planciaPerGiocare) { //se è l'oggetto per giocare crea la lista dei contratti acquistabili, sennò non spreca memoria
        if (planciaPerGiocare) {
            contrattiDisponibili.add(new VicoloCorto());
            contrattiDisponibili.add(new VicoloStretto());
            contrattiDisponibili.add(new StazioneSud());
            contrattiDisponibili.add(new BastioniGranSasso());
            contrattiDisponibili.add(new VialeMonterosa());
            contrattiDisponibili.add(new VialeVesuvio());
            contrattiDisponibili.add(new ViaAccademia());
            contrattiDisponibili.add(new SocietàElettrica());
            contrattiDisponibili.add(new CorsoAteneo());
            contrattiDisponibili.add(new PiazzaUniversità());
            contrattiDisponibili.add(new StazioneOvest());
            contrattiDisponibili.add(new ViaVerdi());
            contrattiDisponibili.add(new CorsoRaffaello());
            contrattiDisponibili.add(new PiazzaDante());
            contrattiDisponibili.add(new ViaMarcoPolo());
            contrattiDisponibili.add(new CorsoMagellano());
            contrattiDisponibili.add(new LargoColombo());
            contrattiDisponibili.add(new StazioneNord());
            contrattiDisponibili.add(new VialeCostantino());
            contrattiDisponibili.add(new VialeTraiano());
            contrattiDisponibili.add(new SocietàAcquaPotabile());
            contrattiDisponibili.add(new PiazzaGiulioCesare());
            contrattiDisponibili.add(new ViaRoma());
            contrattiDisponibili.add(new CorsoImpero());
            contrattiDisponibili.add(new LargoAugusto());
            contrattiDisponibili.add(new StazioneEst());
            contrattiDisponibili.add(new VialeDeiGiardini());
            contrattiDisponibili.add(new ParcoDellaVittoria());
        }
    }

    public Ipotecato fornisciContrattoCasuale() {    //all'inizio del gioco ogni partecipante riceve un certo numero di contratti casuali
        int p = (int) (Math.random() * contrattiDisponibili.size()); //scegli la posizione di un contratto a caso da restituire
        return contrattiDisponibili.remove(p); //lo salva e poi lo elimina
    }

    public Casella getCasella(int posizione) {  //restituisce la casella data la sua posizione
        return plancia[posizione];
    }

    public boolean isDisponibile(Casella c) {   //controlla se la casella passata è disponibile all'acquisto, ritorna true se lo è, false se è già posseduta da un altro giocatore
        boolean tr = false; //inizialmente la presuppone non disponibile
        Ipotecato i = (Ipotecato) c;    //fa il casting ad Ipotecato
        for (Ipotecato iList : contrattiDisponibili) {  //cerca nell'ArrayList se è presente (se c'è vuol dire che è disponibile)
            if (iList.NOME.equals(i.NOME)) {
                tr = true;  //se la trova lo segna per ritornarlo
                break;
            }
        }
        return tr;    //controlla se è nell'ArrayList degli ipotecati disponibili
    }

}
