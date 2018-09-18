package com.johnfnash.learn.chapter3;

public class ThisEscape {

	private int value = 0;
	
	public ThisEscape(EventSource source) {
		source.registerListener(new EventListener() {
			@Override
			public void onEvent(Event e) {
				doSomething(e);
			}
		});
		this.value = 7;
	}
	
	void doSomething(Event e) {
		System.out.println(this.value);
    }
	
	interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }
    
    public static void main(String[] args) {
    	Event event = new Event() {};
    	EventSource source = new EventSource() {
			@Override
			public void registerListener(EventListener e) {
				e.onEvent(event);
			}
		};
    	ThisEscape escape = new ThisEscape(source); 
    	System.out.println(escape.value);
	}
	
}
