package com.johnfnash.learn.activeness.cooperation.solve;

public class Test {

	public static void main(String[] args) {
		Dispatcher dispatcher = new Dispatcher();
		Taxi taxi = new Taxi(dispatcher);
		taxi.setLocation(new Point(0, 0));
		taxi.setDestination(new Point(50, 50));
		dispatcher.addTaxi(taxi);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				Point p;
				while(i<50) {
					p = taxi.getLocation();
					taxi.setLocation(new Point(p.getX()+1, p.getY()+1));
					i++;
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(dispatcher.getAvailables() == 0) {
					dispatcher.printLocations();
				}
			}
		}).start();
	}

}
