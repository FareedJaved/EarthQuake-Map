package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	// PointFeatures also have a getLocation method
	    }
	    
	    // Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int yellow = color(255, 255, 0);
	    
	    //TODO: Add code here as appropriate
	    // Adding code that takes each location in the earthquake 
	    for (int index = 0; index < earthquakes.size(); index++) {
	    	PointFeature pf = earthquakes.get(index);
	    	Object magnitude = pf.getProperty("magnitude");
	    	Float magFloat = Float.parseFloat(magnitude.toString());
	    	
	    	// calling helper method 
	    	SimplePointMarker markerToAdd = styleMarker(pf, magFloat);
	    	markers.add(markerToAdd);
	    }
	    // adding the list of markers to the map so they get displayed 
	    map.addMarkers(markers); 
	}
	
	// Helper method takes an EarthQuake and its magnitude. 
		// Returns a Marker that is styled depending on the magnitude.  
		private SimplePointMarker styleMarker(PointFeature marky, Float magFloat) {
			// Radius is how large the marker will be 
			// Severity is the color the marker will be
			int radius = 0; 
			int severity = 0; 
		
			// Creating a marker with the parameter's location.
			// Using Processing's color method to generate 
		    // an int that represents the colors yellow, blue and red 
			// to style the marker
		    
		    SimplePointMarker tempMarker = createMarker(marky); 
		    
	    	if (magFloat < 4.0) {
	    		radius = 6; 
	    		severity = color(0, 0, 255); // blue
	    	}
	    	else if (magFloat >= 4.0 && magFloat < 5.0) {
	    		radius = 9;
	    		severity = color(255, 255, 0); // yellow
	    	}
	    	else {
	    		radius = 14;
	    		severity = color(255,0,0); // red
	    	}
	    	
	    	tempMarker.setRadius(radius);
	    	tempMarker.setColor(severity);
			
			return tempMarker;
		}
		
	// TODO: Implement this method and call it from setUp, if it helps	
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	private SimplePointMarker createMarker(PointFeature feature) {
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}
	

	// TODO: Implement this method to draw the key
	// helper method to draw key in GUI
	private void addKey() 
	{	
		// Drawing the key box and 
		// making background color seafoam green
		fill(0, 250, 154);
		rect(10, 50, 175, 255, 7);
		
		// Title 
		String title = "EarthQuake Key";
		fill(50);
		text(title, 60, 53, 70, 80); 
		textAlign(CENTER);
		
		// The different levels of earthquake
		fill(255, 0, 0); // red
		ellipse(30, 115, 18, 18);
		fill(105, 105, 105);
		String eqMessage1 = "5.0+ magnitude";
		text(eqMessage1, 75, 102, 70, 80);
		
		fill(255, 255, 0); // yellow 
		ellipse(30, 170, 13, 13);
		fill(105, 105, 105); 
		String eqMessage2 = "4.0+ magnitude";
		text(eqMessage2, 75, 157, 70, 80);
		
		fill(0, 0, 255);	// blue
		ellipse(30, 225, 10, 10); 
		fill(105, 105, 105);
		String eqMessage3 = "Below 4.0"; 
		text(eqMessage3, 75, 217, 70, 80);
	}
}
