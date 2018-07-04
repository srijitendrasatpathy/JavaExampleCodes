import javax.sound.midi.*;

public class MiniMusicPlayer1 implements ControllerEventListener{
	
	public static void main(String[] args) {
		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			
			//Creating a custom event to listen to.
			int[] eventToListen = {127};
			MiniMusicPlayer1 player = new MiniMusicPlayer1();
			sequencer.addControllerEventListener(player, eventToListen);
			
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();
			
			//Creating Note-ON(144) and Note-OFF sequences to create a track.
			for(int i = 5; i < 100; i+=4) {
				
				//Adding the custom event after Note-ON.
				//Both the controller event and Note-ON happens at the same tick.
				//We are adding a controller event since, we cannot listen to Note-ON/OFF events.
				track.add(makeEvent(144,1,i,100,i));
				track.add(makeEvent(176, 1, 127, 0, i)); //Controller Event
				track.add(makeEvent(128,1,i,100,i+2));
			}
			
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(220);
			sequencer.start();
			
		}catch(Exception e) {e.printStackTrace();}
	}
	
	//Need to implement the controlChange method.
	public void controlChange(ShortMessage event) {
		System.out.println("la");
	}
	
	public static MidiEvent makeEvent(int comd,int chan,int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new  ShortMessage();
			a.setMessage(comd,chan,one,two);
			event = new MidiEvent(a, tick);
			
		}catch(Exception e) {}
		return event;
	}
}
