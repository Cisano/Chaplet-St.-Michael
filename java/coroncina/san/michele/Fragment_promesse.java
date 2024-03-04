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

import androidx.fragment.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class Fragment_promesse extends Fragment{
	static int lay; //da Coroncina indico quale layout promesse caricare

public Fragment_promesse()
{
	/*faccio spegnere lo schermo*/
	((Activity) Coroncina.context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(lay, container, false);

		return view;
	}
}