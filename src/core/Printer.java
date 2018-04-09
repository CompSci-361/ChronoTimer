package core;

import java.util.ArrayList;

public class Printer {

	private static ArrayList<PrintMessageActionListener> messageListeners = new ArrayList<PrintMessageActionListener>();
	
	public static class PrintMessageActionListenerEventArgs {
		private String messageProperty = null;
		public PrintMessageActionListenerEventArgs(Object args, String message) {
			messageProperty = message;
		}
		
		public String getMessage() {
			return messageProperty;
		}
	}
	
	public static abstract class PrintMessageActionListener {
		public abstract void onPrintMessageReceived(PrintMessageActionListenerEventArgs args);
	}
	
	/**
	 * Print individual race
	 * @param racer
	 */
	public static void printRacer(Racer racer){
		System.out.println(racer.toString());
		
		for(PrintMessageActionListener action : messageListeners) {
			action.onPrintMessageReceived(new PrintMessageActionListenerEventArgs(null, racer.toString()));
		}
	}
	
	/**
	 * Print all races run
	 * Only prints finished Racers
	 * --Might want to change to print all Racers
	 * @param run
	 */
	public static void printRun(Run run){
		System.out.println(run.toString());
		
		for(PrintMessageActionListener action : messageListeners) {
			action.onPrintMessageReceived(new PrintMessageActionListenerEventArgs(null, run.toString()));
		}
	}
	
	public static void printMessage(String msg) {
		System.out.println(msg);
		
		for(PrintMessageActionListener action : messageListeners) {
			action.onPrintMessageReceived(new PrintMessageActionListenerEventArgs(null, msg));
		}
	}
	
	public static void addPrintMessageActionListener(PrintMessageActionListener listener) {
		messageListeners.add(listener);
	}
}
