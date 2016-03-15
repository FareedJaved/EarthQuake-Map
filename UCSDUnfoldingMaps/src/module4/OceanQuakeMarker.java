package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** Implements a visual marker for ocean earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Fareed Javed
 *
 */
public class OceanQuakeMarker extends EarthquakeMarker {
	
	public OceanQuakeMarker(PointFeature quake) {
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = false;
	}
	

	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		// Drawing a centered square for Ocean earthquakes
		// DO NOT set the fill color.  That will be set in the EarthquakeMarker
		// class to indicate the depth of the earthquake.
		// Simply draw a centered square.
		
		// HINT: Notice the radius variable in the EarthquakeMarker class
		// and how it is set in the EarthquakeMarker constructor
		
		// TODO: Implement this method
		float depth = getDepth();
		
		if (depth < THRESHOLD_LIGHT)
			pg.rect(x, y, 4, 4, radius);
		
		else if (depth >= THRESHOLD_LIGHT && depth < THRESHOLD_MODERATE)
			pg.rect(x, y, 7, 7, radius);
		
		else if (depth >= THRESHOLD_MODERATE && depth < THRESHOLD_INTERMEDIATE)
			pg.rect(x, y, 11, 11, radius);
		
		else if (depth >= THRESHOLD_INTERMEDIATE && depth < THRESHOLD_DEEP)
			pg.rect(x, y, 15, 15, radius);
		
		else 
			pg.rect(x, y, 19, 19, radius);
		
	}

}
