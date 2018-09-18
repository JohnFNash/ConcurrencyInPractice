package com.johnfnash.learn.chapter3;

public class SafeListener {

	private int value = 0;
	
	private final EventListener listener;
	
	private SafeListener() {
		listener = new EventListener() {
			@Override
			public void onEvent(Event e) {
				doSomething(e);
			}
		};
		
		this.value = 7;
	}
	
	public static SafeListener newInstance(EventSource source) {
		SafeListener safe = new SafeListener();
		source.registerListener(safe.listener);
		return safe;
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
		SafeListener safe = SafeListener.newInstance(source);
    	System.out.println(safe.value);
	}
    
}
