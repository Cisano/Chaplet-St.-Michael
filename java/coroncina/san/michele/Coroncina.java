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

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Coroncina extends AppCompatActivity implements OnTouchListener{

static int larg_schermo;
static int alt_schermo;
static int centroY_coroncina;
static int raggioY_coroncina;
static int raggioX_coroncina;
static int raggioGrano;

static Context context;

/*la numerazione è importante perchè è come appare nel drawer*/
final static int SCREEN_CORONCINA = 0;
final static int SCREEN_PROMESSE = 1;
final int Riavvio=2;
final int AudioSetting=3;
final int Vibration=4;
final int Info=5;
static int schermoCorrente=-1;

final static int GR_MICHELE = 40;

static float scale;
static boolean SoComeSiRecita=true;
static boolean start=false;
static boolean parlato=false;
static boolean avanza=false;
static boolean drawViewInit=false;//mi serve perchè se la classe DrawView non è stata inizializzata l'app crasha
static boolean vibrazione=false;

static FragmentManager fm;
ArrayList<Integer> mSelectedItems;
//Dialog dialog;
static boolean bl[] = new boolean[2]; //memorizza la selezione nel dialog dell'audio
Toast toast;

/************************************/
private DrawerLayout mDrawerLayout;
//ListView represents Navigation Drawer
private ListView mDrawerList;
//ActionBarDrawerToggle indicates the presence of Navigation Drawer in the action bar
private ActionBarDrawerToggle mDrawerToggle;
//Title of the action bar
//private String mTitle = "";
/************************************/

@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

		context = this;

    	DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        larg_schermo = displaymetrics.widthPixels;
        alt_schermo = displaymetrics.heightPixels;

        /* set chaplet values*/
        scale = getResources().getDisplayMetrics().density;
        centroY_coroncina=(alt_schermo/2)-convDipPixel(30.0f);//convDipPixel(200.0f);
    	raggioX_coroncina=(larg_schermo/2)-convDipPixel(30.0f);//convDipPixel(120.0f);
    	raggioY_coroncina=(int) (raggioX_coroncina*1.2);//convDipPixel(140.0f);
    	raggioGrano=raggioX_coroncina/19; //convDipPixel(6.0f);
    	//aloneGrano=0;//(int) (raggioGrano/1.1); //convDipPixel(5.0f);

    	fm = getSupportFragmentManager();
//era fm = getFragmentManager();
        //evito che lo schermo ruoti
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    	//allungo il tempo di spegnimento schermo
    	//Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30000);
    	/*************************/
     	getSupportActionBar().setTitle(R.string.app_name);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); //bellow setSupportActionBar(toolbar);
		getSupportActionBar().setCustomView(R.layout.titlebar);

		// Getting reference to the DrawerLayout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		// Getting reference to the ActionBarDrawerToggle
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				/*R.drawable.ic_drawer,*/ R.string.drawer_open,
				R.string.drawer_close) {

			 /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.app_name/*mTitle*/);//da modificare per avere il nome della coroncina sempre
                supportInvalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
                supportInvalidateOptionsMenu();
            }
		};

	 // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);
     // Enabling Home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     // Enabling Up navigation
        getSupportActionBar().setHomeButtonEnabled(true);
		
		// Creating an ArrayAdapter to add items to the listview mDrawerList
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), 
		R.layout.drawer_list_item, getResources().getStringArray(R.array.elementiDelDrawer));

        /*elementiDrawer[0]= getString(R.string.Gesu); 
        ListAdapter adapter = new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, elementiDrawer);*/

		// Setting the adapter on mDrawerList
		mDrawerList.setAdapter(adapter);

		// Setting item click listener for the listview mDrawerList
				mDrawerList.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						// Getting an array of rivers
						//String[] menuItems = getResources().getStringArray(R.array.elementiDelDrawer);

						// Currently selected river
						//if(position<=2) mTitle = menuItems[position];

						// Closing the drawer
						mDrawerLayout.closeDrawer(mDrawerList);
						/*se seleziono lo schermo dove sono già, non fa nulla*/
						if(position+1!=schermoCorrente) visualizza_schermo(position+1);//+1 perchè ho levato la prima voce dal drawer e per farlo combaciare ora aumento di uno
					}
				});
    	/*************************/

	visualizza_schermo(SCREEN_CORONCINA);
    }

public void visualizza_schermo(int numero)
{

	//if(Lista.qualeCoroncina==1) setActionBarTitle(getString(R.string.nomeCoroncinaMisericordia));
	switch (numero)
	{
        case SCREEN_CORONCINA:
			if (fm != null) {
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.content_frame, new Fragment_coroncina());
				ft.commit();
				schermoCorrente= SCREEN_CORONCINA;
				DrawView.validante=true;
			}
			break;

		case SCREEN_PROMESSE:
			if (fm != null) {
				Fragment_promesse.lay=R.layout.promesse_sanmichele;

			    FragmentTransaction ft = fm.beginTransaction();
			    ft.replace(R.id.content_frame, new Fragment_promesse());
			    ft.commit();
			    schermoCorrente = SCREEN_PROMESSE;
			}
			break;

	     case Riavvio:
	    	 //numero=schermoPrecedente;//per evitare il refresh in tutti gli schermi se riselezionati
	    	 AlertDialog.Builder avviso_riavvio_coroncina = new AlertDialog.Builder(this);
	    	 avviso_riavvio_coroncina.setTitle(R.string.Attenzione);
	    	 avviso_riavvio_coroncina.setMessage(R.string.vuoiRicominciare).setCancelable(false)
	         .setPositiveButton(R.string.YesButton, new DialogInterface.OnClickListener() {
	             public void onClick(DialogInterface dialog, int id) {
	            	 //drawViewInit mi serve perchè se la classe DrawView non è stata inizializzata l'app crasha
	            	 if(drawViewInit) if(Parlato.myTTS.isSpeaking()) { Parlato.myTTS.stop(); /*Fragment_coroncina.running=false;*/ }
	            	 inizializzaCoroncina(SCREEN_CORONCINA); schermoCorrente=-1;
	            	 visualizza_schermo(SCREEN_CORONCINA);
	             }
	         })
	         .setNegativeButton(R.string.NoButton, new DialogInterface.OnClickListener() {
	             public void onClick(DialogInterface dialog, int id) {
	                  dialog.cancel();
	             }
	         });
	    	 avviso_riavvio_coroncina.show();
	       break;

	     case AudioSetting:
	    	 //visualizzao la scelta del'audio solo se android è da ice cream in poi
	    	 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	    	 mostraDialog();
	    	 else { toast = Toast.makeText(this.context, R.string.not_available, Toast.LENGTH_LONG);
	    	 toast.show(); }
	    	 break;

		case Info: mostraDialogInfo(); break;

		case Vibration:
			if(vibrazione) vibrazione=false; else vibrazione=true;
			break;

	     default:  break;
	}

}
/*end main class*/

/**********************************************/

//public Dialog onCreateDialog(Bundle savedInstanceState) 
void mostraDialog()
{
  mSelectedItems = new ArrayList<Integer>();  // Where we track the selected items
  //boolean bl[] = new boolean[2];//due perchè 2 sono gli elementi che appaiono selezionabili
  
  AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
  // Set the dialog title
  builder.setTitle(R.string.AudioSetting)
  // Specify the list array, the items to be selected by default (null for none),
  // and the listener through which to receive callbacks when items are selected
         .setMultiChoiceItems(R.array.preferenzeDialog, bl/*null*/,
                    new DialogInterface.OnMultiChoiceClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                 if (isChecked) {
                     // If the user checked the item, add it to the selected items
                     mSelectedItems.add(which);
                     bl[which]=true;
                     //System.out.println("parlato "+parlato+" avanza "+avanza);
                	 
                 } else if (mSelectedItems.contains(which)) {
                     // Else, if the item is already in the array, remove it 
                     mSelectedItems.remove(Integer.valueOf(which));
                     bl[which]=false;
                 }
             }
         })
// Set the action buttons
         .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int id) {
            	 parlato=bl[0]; avanza=bl[1];
            	 if(!parlato) if(Parlato.myTTS.isSpeaking()) Parlato.myTTS.stop();
            	 //Fragment_coroncina.gestioneVitaThread();
            }
         })
         .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int id) {
             }
         });

  //return builder.create();
  builder.show();
}

/***************************************************/
void mostraDialogInfo()
{
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage(R.string.Contributi)
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					//do things
				}
			});
	AlertDialog alert = builder.create();
	alert.show();

}
/***********************************************/

static int convDipPixel(float valoreDip)
{
	return (int) (valoreDip * scale + 0.5f);
}

/* richiamato quando seleziono Riavvia*/
static void inizializzaCoroncina(int screen)
{
	DrawView.contatore=0;
	SoComeSiRecita=true;
	start=false;
}
/*imposto titolo barra*/
public void setActionBarTitle(String title) {
		getSupportActionBar().setTitle(title);
	}
/*imposto titolo barra*/

/* Metodi per il drawer */
@Override
protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    // Sync the toggle state after onRestoreInstanceState has occurred.
    mDrawerToggle.syncState();
}

@Override
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Pass the event to ActionBarDrawerToggle, if it returns
    // true, then it has handled the app icon touch event
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    // Handle your other action bar items...
    return super.onOptionsItemSelected(item);
}
/* Metodi per il drawer */

public boolean onTouch(View v, MotionEvent event) {
	// TODO Auto-generated method stub
	return false;
}
@Override
protected
void onPostResume()
{
	super.onPostResume();
	System.out.println("manage resumed");
}

@Override
protected
void onStop()
{
	super.onStop();
	System.out.println("manage stop");
	//DrawView.t.cancel();
	//Fragment_coroncina.running=false; vedere se serve
}

	@Override
	public void onBackPressed() {
		//schermoCorrente=-1;
		if(schermoCorrente== SCREEN_PROMESSE) {
			Intent intent = new Intent(
					getApplicationContext(),
					Coroncina.class
			);
			startActivity(intent);
			this.finish();
		}
		//inizializza la coroncina attualmente visualizzata. necessario altrimenti se rientro in un'altra, me la trovo già iniziata
		else {
			inizializzaCoroncina(SCREEN_CORONCINA);
			schermoCorrente=-1;
			Parlato.myTTS.stop();//da verificare se serve qui
			Fragment_coroncina.running=false;
			finish();
		}
	}
}