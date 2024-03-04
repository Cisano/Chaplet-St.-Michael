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

import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.os.Vibrator;

import static android.content.Context.VIBRATOR_SERVICE;

public class Fragment_coroncina extends Fragment implements OnTouchListener{
static TextView txtvPreghiera;
TableLayout RequestSeLaSai;
TableLayout TheEnd;
LinearLayout layoutDraw;
DrawView disegnaVista= new DrawView(Coroncina.context);//!!!!!!!!!!!!!!!sembra essere questo a generare l'errore al rientro
static Thread thread;
static boolean running=false;//era a true da rimettere se non va il thread o altro
static boolean terminata=false;
static boolean puoiParlare =false;
boolean sblocco=true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.coroncina, container, false);
		view.setOnTouchListener(this);
		
		/************ Evito che lo schermo si spenga **************/
		((Activity) Coroncina.context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    /************ Evito che lo schermo si spenga **************/

		//disegnaVista= new DrawView(Coroncina.context);

		/*creo il layout dove la daw disegna la coroncina*/
		layoutDraw = (LinearLayout) view.findViewById(R.id.layout);
		//layoutDraw.addView(new DrawView(Coroncina.context));
		layoutDraw.addView(disegnaVista);
		//imageView1.addView(new DrawView(Coroncina.this)/*.getShadowEffect()*/);
		
		txtvPreghiera = (TextView) view.findViewById(R.id.textViewPreghiera);
		txtvPreghiera.setVisibility(View.INVISIBLE);

		RequestSeLaSai = (TableLayout) view.findViewById(R.id.RequestSeLaSai);
		TheEnd = (TableLayout) view.findViewById(R.id.TheEnd);
		RequestSeLaSai.setVisibility(View.INVISIBLE);
		TheEnd.setVisibility(View.INVISIBLE);

		if((DrawView.contatore==0)&&(!Coroncina.start)) RequestSeLaSai.setVisibility(View.VISIBLE);
		else if (DrawView.contatore>Coroncina.GR_MICHELE -1) TheEnd.setVisibility(View.VISIBLE);

		final Button NoButton = (Button) view.findViewById(R.id.NoButton);
        final Button YesButton = (Button) view.findViewById(R.id.YesButton);

        final Button End = (Button) view.findViewById(R.id.End);

        NoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	RequestSeLaSai.setVisibility(View.INVISIBLE);
            	Coroncina.SoComeSiRecita=false;
            	Coroncina.start=true;
            	running=true;
				terminata=false;
				puoiParlare =true;//necessaria altrimenti non dice di farsi il segno della croce
            	disegnaVista.invalidate();//invalido la vista e la ridisegno
            }
        });

        YesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	RequestSeLaSai.setVisibility(View.INVISIBLE);
            	Coroncina.SoComeSiRecita=true;
            	Coroncina.start=true;
            	running=true;
				terminata=false;
				puoiParlare =true;//necessaria altrimenti non dice di farsi il segno della croce
            	disegnaVista.invalidate();//invalido la vista e la ridisegno
            	//verificare se non va in loop
            }
        });

        End.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {Coroncina.inizializzaCoroncina(Coroncina.SCREEN_CORONCINA);
            TheEnd.setVisibility(View.INVISIBLE);
            RequestSeLaSai.setVisibility(View.VISIBLE);
            FragmentTransaction ft = Coroncina.fm.beginTransaction();
		    ft.replace(R.id.content_frame, new Fragment_coroncina());
		    ft.commit();
		    disegnaVista.invalidate();//invalido la vista e la ridisegno‎
            }
        });
		return view;
    }

public boolean onTouch(View v, MotionEvent event) {
		//int x = (int)event.getX();
	if((RequestSeLaSai.isShown()) || (TheEnd.isShown())) return false;//se sono mostrati la richiesta se la sai o la fine, non abilito il tocco per andare avanti
	if (terminata) { Parlato.myTTS.stop(); //serve perchè quando appare il pulsante fine parlerebbe ancora
		TheEnd.setVisibility(View.VISIBLE);
		return false; }
	if (event.getAction() == android.view.MotionEvent.ACTION_UP)
		//evito che inavvertitamente si avanti di 2 grani
		if(sblocco) { sblocco=false; DrawView.contatore++; puoiParlare=true; disegnaVista.invalidate();
			// Get instance of Vibrator from current Context

			if(Coroncina.vibrazione) vibra();

			(new Handler()).postDelayed(new Runnable() {
				public void run() {
					sblocco=true;
				}
			},1000); }
		return true;
	}

	/*** gestisco la vibrazione ***/
	void vibra()
	{
		Vibrator vibr = (Vibrator) Coroncina.context.getSystemService(VIBRATOR_SERVICE);
		if((DrawView.contatore==1)||
				(DrawView.contatore==5) ||
				(DrawView.contatore==9) ||
				(DrawView.contatore==13) ||
				(DrawView.contatore==17) ||
				(DrawView.contatore==21) ||
				(DrawView.contatore==25) ||
				(DrawView.contatore==29) ||
				(DrawView.contatore==33)) vibr.vibrate(200);
		else vibr.vibrate(50);
	}

/******** gestisco la vita del thread *********/
public void gestioneVitaThread() {
	//if((Coroncina.avanza)&&(Coroncina.parlato))
	//{
		System.out.println("thread fragment vita");
		if(thread.getState() == Thread.State.NEW) {
			running=true;
			thread.start();
		}
		else
			if (thread.getState() == Thread.State.TERMINATED) {
				creoThread();
				running=true;
				thread.start();
			}
	//}
}
/******** gestisco la vita del thread *********/

/******** creo il thread **********************/
public void creoThread()
{
	thread = new Thread() {
		@Override //ci va qui???? prima non c'era
		public void run() {
           while (running) {
               try {
               	((Activity) Coroncina.context).runOnUiThread(new Runnable() {
                       @Override
                        public void run() {
                        	if((Coroncina.avanza) && (Coroncina.parlato) && (!terminata) && Coroncina.start/*(DrawView.contatore>0)*/)
    		                    if(/*!DrawView */ !Parlato.myTTS.isSpeaking()) {

    		                    DrawView.contatore++;
    		                    //disegnaVista.visualizza_preghiera(DrawView.contatore);
                                    puoiParlare=true;
									//System.out.println("thread in azione");
    		                    disegnaVista.invalidate();
   		                    }
                       }
                    });
                    Thread.sleep(2000);
                    System.out.println("thread fragment coroncina "+DrawView.contatore);
                } catch (InterruptedException e) {
                   e.printStackTrace();
               }
            }
        
      }
  };
}

@Override
public void onPause()
{
	System.out.println("pause fragment coroncina");
    super.onPause();  // Always call the superclass method first
    if(Parlato.myTTS.isSpeaking()) Parlato.myTTS.stop();
    running=false;
    //System.out.println("uscito");
}
@Override
public void onResume()
{
	gestioneVitaThread();
	super.onResume();
	System.out.println("resume fragment coroncina "+running);
}
@Override
public void onStop()
{
	super.onStop();
	System.out.println("stop fragment coroncina");
	if(Parlato.myTTS.isSpeaking()) Parlato.myTTS.stop();
	running=false;
}
@Override
public void onStart()
{
	creoThread();
	super.onStart();
	//System.out.println("start fragment coroncina "+running);
}

}
