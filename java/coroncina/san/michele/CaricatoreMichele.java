/** Code by Cisano Carmelo
 This file is part of Chaplet of St. Michael Arcangelo

 It is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Chaplet of St. Michael is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Chaplet of St. Michael. If not, see <http://www.gnu.org/licenses/>.
 **/
package coroncina.san.michele;

import android.graphics.Color;
import android.view.View;

class CaricatoreMichele {
    Grano[] gr = new Grano[Coroncina.GR_MICHELE];
    float gradi=(float) 270.0;
    Parlato parla = new Parlato(Coroncina.context);

    CaricatoreMichele()
    {
        float gradi_radianti = (float) (gradi / 180 * Math.PI);
        float XX =(((Coroncina.larg_schermo/2))+(Coroncina.raggioX_coroncina*((float)Math.cos(gradi_radianti))));
        float YY =(Coroncina.centroY_coroncina+(Coroncina.raggioY_coroncina*((float)Math.sin(gradi_radianti))));

        int id=0;//id del grano

        for(int l=0; l<9; l++)
        {
            if(l!=0) gradi+=6.64;

            for(int k=0; k<4; k++)
            {
                gradi_radianti = (float) (gradi / 180 * Math.PI);
                float X =(((Coroncina.larg_schermo/2))+(Coroncina.raggioX_coroncina*((float)Math.cos(gradi_radianti))));
                float Y =(Coroncina.centroY_coroncina+(Coroncina.raggioY_coroncina*((float)Math.sin(gradi_radianti))));
                if(k==0)
                {
                    gr[id]= new Grano(X, Y, Color.WHITE);
                    //gr[id].grano.setShadowLayer(Coroncina.aloneGrano, 0, 0, Color.WHITE);
                    gradi+=(6.64*2); id++;
                }
                else
                {
                    gr[id]= new Grano(X,Y, Color.rgb(255,204,0));
                    gradi+=6.64; id++;
                }
            }
        }
        gr[id++]= new Grano(XX,
                (YY+((Coroncina.raggioGrano*2)*3)),
                Color.rgb(255,204,0));

        gr[id++]= new Grano(XX,
                (YY+((Coroncina.raggioGrano*2)*4)),
                Color.rgb(255,204,0));

        gr[id++]= new Grano(XX,
                (YY+((Coroncina.raggioGrano*2)*5)),
                Color.rgb(255,204,0));

        gr[id++]= new Grano(XX,
                (YY+((Coroncina.raggioGrano*2)*6)),
                Color.rgb(255,204,0));
     /*se torno da una schermata mi ritrovo la situazione precedente*/
        if(DrawView.contatore!=0){
            for(id=0;id<DrawView.contatore;id++) {
                gr[id].grano.setColor(Color.RED);
                //gr[id].grano.setShadowLayer(Coroncina.aloneGrano, 0, 0, Color.rgb(240,147,0));
                }}
	/*se torno da una schermata mi ritrovo la situazione precedente*/

    } //FINE INIZIALIZZA GRANI CORONCINA

    public void sgrana(int i)
    {
        //System.out.println("switch  "+i);
        switch(i) {
            case 0:
                Fragment_coroncina.txtvPreghiera.setTextColor(Color.YELLOW);
                Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.textViewSegnoCroce) + "\n\n"
                        + Coroncina.context.getString(R.string.oDio) + "\n" + Coroncina.context.getString(R.string.gloria) + "\n\n"
                        + Coroncina.context.getString(R.string.micheleDifendici));
                break;
            case 1:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);
                /*rimetto di colore bianco la textview*/
                Fragment_coroncina.txtvPreghiera.setTextColor(Color.WHITE);
                /*passo la preghiera, ogni volta anche se l'utente non la visualizza, alla textview così se l'audio è impostato sa cosa dire*/
                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.primaInvocazione) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 5:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.secondaInvocazione) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 9:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.terzaInvocazione) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 13:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.quartaInvocazione) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 17:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.quintaInvocazione) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 21:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.sestaInvocazione) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 25:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.settimaInvocazione) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 29:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.ottavaInvocazione) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 33:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.nonaInvocazione) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 37:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.onoreSanMichele) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 38:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.onoreSanGabriele) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 39:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.onoreSanRaffaele) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 40:
                if (!Coroncina.SoComeSiRecita)
                    Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.onoreAngeloCustode) + "\n\n"
                        + Coroncina.context.getString(R.string.padreNostro));
                gr[i - 1].grano.setColor(Color.RED);
                break;
            case 41:
                Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                if (!Coroncina.SoComeSiRecita) Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.preghieraFinale));
                else Fragment_coroncina.txtvPreghiera.setText(Coroncina.context.getString(R.string.preghieraOrazione));
                break;
            case 42:
                //parla.myTTS.stop(); /*thread.stop();*/ /*t.cancel(); isRunning=false;*/
                Fragment_coroncina.txtvPreghiera.setTextColor(Color.YELLOW);
                Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                Fragment_coroncina.txtvPreghiera.setText(R.string.textViewSegnoCroce);
                Fragment_coroncina.terminata=true; break;

            default:
                if(!Coroncina.SoComeSiRecita) Fragment_coroncina.txtvPreghiera.setVisibility(View.VISIBLE);
                else Fragment_coroncina.txtvPreghiera.setVisibility(View.INVISIBLE);

                Fragment_coroncina.txtvPreghiera.setText(R.string.aveMaria);
                gr[i-1].grano.setColor(Color.RED);
                break;
        }
        /*usato da tutti i grani della coroncina se l'audio è attivato */
        if((Coroncina.parlato) && Fragment_coroncina.puoiParlare) { Fragment_coroncina.puoiParlare =false; parla.speakWords(Fragment_coroncina.txtvPreghiera.getText().toString()); }
        /*usato da tutti i grani della coroncina se l'audio è attivato */
    }

}