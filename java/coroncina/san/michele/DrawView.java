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

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

public class DrawView extends View /*implements TextToSpeech.OnInitListener*/ {

	Paint grano = new Paint();
	Paint filo = new Paint();
	Paint croce= new Paint();
	Paint medaglietta= new Paint();//vedere se si possono unire i 2 paint
	private int coordY_Crocifisso;//COORDINATA Y DEL CROCIFISSO scelta in base a dove finisce il filo dritto
	static int contatore=0;

	static boolean validante=false;
	boolean scalato=false; //verifica se gli sfondi sono stati già scalati

	BitmapScaler scaler;
	
	Bitmap bmp0;
	int fattRiduzione;
	//static Thread thread;
	//Parlato parla = new Parlato(Coroncina.context);
	CaricatoreMichele loaderM;

	private int[] immaginiSfondo = {
			R.drawable.michele_sfondo,
	};

public DrawView(Context context) {
	super(context);

	loaderM=new CaricatoreMichele();

	Coroncina.drawViewInit=true;

	filo.setAntiAlias(true);
	filo.setStyle(Paint.Style.STROKE);
	filo.setColor(Color.GRAY);
	filo.setStrokeWidth(3.00f/*1.80f*/);//spessore filo
	grano.setAntiAlias(true);
	croce.setColor(Color.WHITE);
	//croce.setShadowLayer(Coroncina.aloneGrano, 0, 0, Color.WHITE);
	medaglietta.setAntiAlias(true);
	medaglietta.setColor(Color.WHITE);
	
	fattRiduzione=Coroncina.larg_schermo;
	//this.setWillNotDraw(true);
}

@Override
public void onDraw(Canvas canvas)
{
	imposta_sfondo_coroncina(canvas, 0);
			disegna_filo_michele(canvas);
			/*rendo il grano corrente rosso e visualizzo la preghiera annessa*/
			if (Coroncina.start)
				loaderM.sgrana(contatore); //visualizza_preghiera(contatore); //loader.sgrana(contatore);
			disegno_coroncina_michele(canvas);
			disegna_medaglia_michele(canvas);

	if(validante) { invalidate(); validante=false; System.out.println("invalidato"); }
}

/***************************/
/* set chaplet background */
/**************************/
	void imposta_sfondo_coroncina(Canvas canvas, int i)
	{
		if(!scalato)
		try {
			scaler = new BitmapScaler(getResources(), immaginiSfondo[i], (fattRiduzione));
			bmp0=scaler.getScaled();
			scalato=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		canvas.drawBitmap(bmp0, 0, 0, null);
	}

/*draw cstring of chaplet*/
	void disegna_filo_michele(Canvas canvas)
	{
		canvas.drawOval(new RectF
						((Coroncina.larg_schermo/2)-Coroncina.raggioX_coroncina,
								(Coroncina.centroY_coroncina)-Coroncina.raggioY_coroncina,
								(Coroncina.larg_schermo/2)+Coroncina.raggioX_coroncina,
								(Coroncina.centroY_coroncina)+Coroncina.raggioY_coroncina),
				filo);
		canvas.drawLine(loaderM.gr[0].CoordX, loaderM.gr[0].CoordY, loaderM.gr[0].CoordX, loaderM.gr[Coroncina.GR_MICHELE -1].CoordY+(Coroncina.raggioGrano*3), filo);
	}


/*draw grain of chaplet*/
	void disegno_coroncina_michele(Canvas canvas)
	{
		canvas.drawCircle(loaderM.gr[0].CoordX, loaderM.gr[0].CoordY, ((float)(Coroncina.raggioGrano*1.6)), loaderM.gr[0].grano);
		for(int id=1;id<40;id++)
			canvas.drawCircle(loaderM.gr[id].CoordX, loaderM.gr[id].CoordY, Coroncina.raggioGrano, loaderM.gr[id].grano);
		if(contatore<41) canvas.drawText("Grano "+(contatore+1), (Coroncina.larg_schermo/2)-Coroncina.raggioX_coroncina,(Coroncina.centroY_coroncina)+Coroncina.raggioY_coroncina, medaglietta);
		else canvas.drawText("Grano "+contatore, (Coroncina.larg_schermo/2)-Coroncina.raggioX_coroncina,(Coroncina.centroY_coroncina)+Coroncina.raggioY_coroncina, medaglietta);
	}

/*draw medal of chaplet*/
void disegna_medaglia_michele(Canvas canvas)
{
	int i=Coroncina.GR_MICHELE -1;
	canvas.drawOval(new RectF
					(loaderM.gr[i].CoordX-Coroncina.raggioGrano*3, loaderM.gr[i].CoordY+(Coroncina.raggioGrano*3),
							loaderM.gr[i].CoordX+(Coroncina.raggioGrano*3), loaderM.gr[i].CoordY+(Coroncina.raggioGrano*11)),
			medaglietta);

	/*map image on medal*/
	Drawable texture=getResources().getDrawable(R.drawable.michele_medaglietta);
	texture.setBounds((int)loaderM.gr[i].CoordX-Coroncina.raggioGrano*3, (int)loaderM.gr[i].CoordY+(Coroncina.raggioGrano*3),
			(int)loaderM.gr[i].CoordX+(Coroncina.raggioGrano*3), (int)loaderM.gr[i].CoordY+(Coroncina.raggioGrano*11));
	texture.draw(canvas);
		/*mappo sulla medaglietta una immagine*/

}

}