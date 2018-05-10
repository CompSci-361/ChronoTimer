# Chronotimer

## Instructions for Use

### Server Instructions

The server should be started prior to starting the GUI application or simulator. To enable the server, run the ServerApplication.java file. To access the results, go to localhost:8000.

### GUI Instructions

The GUI can be started from Gui.java. From there, the ChronoTimer can be used in the following order:

Power On: Activates reset, time, and the racetypes. 

Select RaceType: Options include individual run, parallel individual run, group run, and parallel group run. Selecting a racetype will allow the user to start a new run.

New Run: Allows the race to begin. Enables the channels and updates the run number. Racers can now be added. Time and racetype cannot be changed.

Enable Channels: Channels can be toggled on/off. Sensors (Gate, Pad, Eye, Trip) can be connected to enabled channels. 

Add Racers: Racer numbers can be entered using the keypad. The plus sign will enter the racer. The minus sign will delete a character from the text boxes.

Trigger Channels 1-8 Start and Finish: When pressed, records the time and updates the wait, running, and finish queues.

Print Run: Prints the current run of all racers in the run.

End Run: Sends results of the current race to the server.

Functions: Only applicable to individual runs. Swap - swaps the first two racers in the running queue. DNF sets the first racer's start time to -1. Clear ### clears the specified racer from the wait queue. Cancel takes the first racer out of the running queue and returns them to the wait queue.

### Racetype Instructions

#### Individual
Only channels 1 and 2 are used. Racers can be added and removed using the keypad and the function keys. Trigger 1 will record the start time for the first waiting racer. Trigger 2 will record the finish time for the first running racer.

#### Parallel Individual
Only channels 1-4 are used. Added racers are alternated between channels 1 & 2. Channels 1 & 3 record start times for first waiting racers. Channels 3 & 4 record the finish times for the first running racers.

#### Group
Only channels 1 & 2 are used. This run type has two cases: bib numbers are known prior to the start, bib numbers are not known prior to the start. 

If bib numbers are known prior to the start of the race, then they are added using the keypad prior to triggering channel 1, which sets the group start time. Each racer will then be added to the wait queue, and trigger 1 will set all the racers to their running state. Trigger 2 will record a series of finish times, not tied to a specific racer. The racers can then be given finish times by using the keypad to enter their bib numbers in order.

If bib numbers are not known prior to the start of the race, then trigger 1 is activated, setting the group start time. Trigger 2 will create a racer with a placeholder bib number. The racers can then be given bib numbers by using the keypad to enter their bib numbers in order.

#### Parallel Group
All channels are used. Trigger 1 sets the group start time for all racers. Triggers 1-8 set the finish time for the racer corresponding to that channel.

## Sprint 1

This sprint implements a chronotimer and simulator to track individual races. 

## Sprint 2

This sprint updates the chronotimer and simulator to track another type of race, parallel individual. Parallel triggers start on channel 3 and alternate between 1 and 3.

## Sprint 3

This sprint implements a user interface and updates the chronotimer and simulator to track group run racetypes. Users may need to install WindowBuilder.

## Sprint 4

This sprint implements the final racetype, parallel group, along with a server to display the results. 
