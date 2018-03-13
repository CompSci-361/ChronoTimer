package simulation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import core.Chronotimer;
import core.RaceType;
import core.SensorType;

public class Simulator {
	static Chronotimer chrono = new Chronotimer();

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner stdin = new Scanner(System.in);
		/*
		 * POWER - Toggles the power 
		 * EXIT - Exits the simulator 
		 * RESET - Resets the chronotimer 
		 * TIME - <int:int:double> 
		 * DNF - Gives a runner the DNF event 
		 * CANCEL - Cancels a runner 
		 * TOG - toggles that channel 
		 * TRIG -triggers that channel 
		 * START - TRIG on channel 1 
		 * FINISH - TRIG on channel 2
		 * Is broken into reading from a file and reading from the command line
		 */
		System.out.println("Would you like to read from a file [y,n]: ");

		String decision = stdin.nextLine().trim();
		if (decision.equals("y")) {
			// The name of the file to open.
			String fileName;
			System.out.println("Enter the name of the file to open: ");
			fileName = stdin.nextLine().trim();

			// This will reference one line at a time
			String line = null;

			try {
				// FileReader reads text files in the default encoding.
				FileReader fileReader = new FileReader(fileName);
				
				// Always wrap FileReader in BufferedReader.
				@SuppressWarnings("resource")
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while ((line = bufferedReader.readLine()) != null) {

					String delims = "\\s+";
					String[] tokens = line.split(delims);
					String time = tokens[0];
					String cmd = tokens[1];
					String param = null;
					String extra = null;
					if (tokens.length == 3)
						param = tokens[2];
					if (tokens.length > 3){
						param = tokens[2];
						extra = tokens[3];
					}
					
					String[] token = time.split(":");
					chrono.setTime(Integer.parseInt(token[0]), Integer.parseInt(token[1]), Double.parseDouble(token[2]));
					
					processCommands(time, cmd, param, extra);
				}
			} catch (FileNotFoundException ex) {
				System.out.println("Unable to open file '" + fileName + "'");
			} catch (IOException ex) {
				System.out.println("Error reading file '" + fileName + "'");
				// Or we could just do this:
				// ex.printStackTrace();
			} catch (Exception ex) {
				System.err.println(ex.toString());
			}
		} else {
			while(true){
				System.out.println("Enter a command: ");
				String line = stdin.nextLine();
				String delims = "[ ]+";
				String[] tokens = line.split(delims);
				String cmd = tokens[0];
				String param = null;
				String extra = null;
				
				if (tokens.length == 2)
					param = tokens[1];
				if (tokens.length > 2){
					param = tokens[1];
					extra = tokens[2];
				}
				
				processCommands(null, cmd, param, extra);
			}
		}

	}
	/**
	 * Only should be called in the Simulator
	 * Based on the value of parameters, will execute different commands
	 * @param time time of command, only used for reading from file
	 * @param cmd - The Command
	 * @param param - The Parameter of the Command, further specifications
	 * @param extra - Extra needs to be used when reading in an extra long command, like COMM
	 */
	public static void processCommands(String time, String cmd, String param, String extra) {
		switch (cmd) {
			case "POWER": {
				chrono.togglePower();
				boolean value = chrono.getIsPoweredOn();
				System.out.println("Power is " + (value ? "enabled" : "disabled"));
				break;
			}
			case "EXIT": {
				System.out.println("Exiting Simulator.");
				System.exit(0);
				break;
			}
			case "RESET": {
				chrono.reset();
				System.out.println("Reset Chronotimer.");
				break;
			}
			case "TIME": {
				String[] token = param.split(":");
				chrono.setTime(Integer.parseInt(token[0]), Integer.parseInt(token[1]), Double.parseDouble(token[2]));
				System.out.println("Current Time is " + chrono.getTime());
				break;
			}
			case "DNF": {
				chrono.dnf();
				break;
			}
			case "CANCEL": {
				chrono.cancel();
				break;
			}
			case "TOG": {
				chrono.toggleChannel(Integer.parseInt(param));
				boolean value = chrono.getChannelIsEnabled(Integer.parseInt(param));
				System.out.println("Channel " + param + " is " + (value ? "enabled" : "disabled"));
				break;
			}
			case "CONN":{
				chrono.setConnect(Integer.parseInt(extra), SensorType.valueOf(param));
				System.out.println(chrono.getSensorType(Integer.parseInt(extra)) + " is connected to channel " + extra);
				break;
			}
			case "DISC":{
				chrono.setDisconnect(Integer.parseInt(param));
				System.out.println(chrono.getSensorType(Integer.parseInt(param)) + " is connected to channel " + param);
				break;
			}
			case "TRIG": {
				System.out.println("Trigger Channel " + param);
				chrono.triggerChannel(Integer.parseInt(param));
				break;
			}
			case "PRINT":{
				System.out.println("Finish queue: \n");
				chrono.print();
				break;
			}
			case "NEWRUN":{
				System.out.println("New Run selected is " + chrono.getRaceType());
				chrono.newRun();
				break;
			}
			case "ENDRUN":{
				chrono.endRun();
				break;
			}
			case "EVENT":{
				chrono.setRaceType(RaceType.valueOf(param));
				break;
			}
			case "NUM":{
				chrono.addRacer(Integer.parseInt(param));
				System.out.println("Current racers in wait queue are: ");
				for(int i = 0; i < chrono.getCurrentRun().getCurrentWaitingRacers().length; i++){
					System.out.print("Racer" + chrono.getCurrentRun().getCurrentWaitingRacers()[i].getBibNumber() + " ");
				}
				System.out.println();
				break;
			}
			case "START": {
				chrono.start();
				System.out.println("Trigger Channel " + 1);
				break;
			}
			case "FINISH": {
				chrono.finish();
				System.out.println("Trigger Channel " + 2);
				break;
			}
			case "EXPORT":{
				//TODO
				break;
			}
		}
	}
}
