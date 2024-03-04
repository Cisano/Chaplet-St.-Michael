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
import android.graphics.Paint;

import java.io.FileOutputStream;

class Grano
{
	Paint grano = new Paint();
	float CoordX;
	float CoordY;

Grano(float x, float y, int color)
	{	
		grano.setAntiAlias(true);
		grano.setColor(color);
		CoordX=x;
		CoordY=y;
}
}//end class