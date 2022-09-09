package train.simulation;

import java.util.ArrayList;
import java.util.LinkedList;

import train.model.Route;
import train.model.Segment;
import train.view.TrainView;

public class TrainSimulation {

    public static void main(String[] args) {
    	

    	
    	
        TrainView view = new TrainView();
        
        SegmentAssistant segmentAssist = new SegmentAssistant();
        
    	Route route = view.loadRoute();
    	ArrayList<Segment> trainList = new ArrayList<Segment>();
        Segment first = route.next();
        for (int i = 0; i < 3; i++) {
        	trainList.add(first);
        	first.enter();
        	first = route.next();
	        
	    }
    }
}

/*
you need write code to
• create a queue,
• load a route, view.loadRoute()
• read three segments from the route, route.next()
• store them in your queue, and
• mark them as busy. segment.enter()
To get the train moving, your code will need to repeatedly ------------------------ här är du
• read the next segment from the route, route.next()
• mark it as busy, head.enter()
• add it to the head of your queue,
• remove the tail Segment from your queue, and
• mark the removed segment as free. tail.exit()
*/