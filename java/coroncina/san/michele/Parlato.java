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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.HashMap;

public class Parlato implements TextToSpeech.OnInitListener{

    static TextToSpeech myTTS;
    static boolean TTS_assente = false;

    Parlato(Context ctx)
    {
        myTTS = new TextToSpeech(ctx, this);
    }

    //setup TTS
    public void onInit(int initStatus) {
        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS)
            if(myTTS.getLanguage()==null) TTS_assente=true;
        //if(myTTS.getVoice().getLocale()==null) TTS_assente=true;
    }

    //speak the user text
    void speakWords(String speech) {
        //if(!TTS_assente) myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(speech);
        } else {
            ttsUnder20(speech);
        }
    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId=this.hashCode() + "";
        myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

}