import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;


public class BeatBox {
	JPanel mainPanel;
	ArrayList<JCheckBox> checkBoxList;
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	JFrame frame;
	
	//List of instruments we are gonna have on the GUI.
	String[] instrumentName = { "Bass Drum", "Closed Hi-Hat", "Open Hi-Hat",
								"Acoustic Snare", "Crash Cymbal", "Hand Clap", 
								"High Tom", "Hi Bongo", "Maracas", "Whistle", 
								"Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", 
								"High Agogo", "Open Hi Conga"};
	
	//List of integers representing the actual key/sound of an instrument. This example, different drum beats.
	int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63}; 
	
	public static void main(String[] args) {
		BeatBox box = new BeatBox();
		box.buildGUI();
	}
	
	public void buildGUI() {
		frame = new JFrame("Beat Boxer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		checkBoxList = new ArrayList<>();
		Box buttonBox = new Box(BoxLayout.Y_AXIS);
		
		JButton start = new JButton("Start");
		start.addActionListener(new MyStartListener());
		buttonBox.add(start);
		
		JButton stop = new JButton("Stop");
		stop.addActionListener(new MyStopListener());
		buttonBox.add(stop);
		
		JButton upTempo = new JButton("Tempo Up");
		start.addActionListener(new MyUpTempoListener());
		buttonBox.add(upTempo);
		
		JButton downTempo = new JButton("Tempo Down");
		stop.addActionListener(new MyDownTempoListener());
		buttonBox.add(downTempo);
		
		Box nameBox = new Box(BoxLayout.Y_AXIS);
		
		for(int i = 0; i< 16; i++) {
			nameBox.add(new Label(instrumentName[i]));
		}
		
		background.add(BorderLayout.EAST,buttonBox);
		background.add(BorderLayout.WEST,nameBox);
		
		frame.getContentPane().add(background);
		
		GridLayout grid = new GridLayout(16, 16);
		grid.setVgap(1);
		grid.setHgap(2);
		mainPanel = new JPanel(grid);
		background.add(BorderLayout.CENTER,mainPanel);
		
		for(int i = 0; i <256;  i++) {
			JCheckBox a = new JCheckBox();
			a.setSelected(false);
			checkBoxList.add(a);
			mainPanel.add(a);
		}
		
		setUpMidi();
		
		frame.setBounds(50,50,300,300);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void setUpMidi() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}//Close main
	
	public void buildTrackAndStart() {
		int[] trackList = null;
		
		sequence.deleteTrack(track);
		track = sequence.createTrack();
		
		
		for(int i  =0; i < 16; i++) {
			trackList = new int[16];
			
			int key = instruments[i];
			
			for(int j = 0; j <16; j++) {
				JCheckBox jc = (JCheckBox) checkBoxList.get(j = (16*i));
				if(jc.isSelected()) {
					trackList[j] = key;
				}else {
					trackList[j] = 0;
				}
			}//Close Inner for
			
			makeTracks(trackList);
			track.add(makeEvent(176,1,127,0,16));
		}// close outer for
		
		track.add(makeEvent(192,9,1,0,15));
		try {
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		}catch(Exception e) {e.printStackTrace();}
	}//close buildTrackAndStart
	
	public class MyStartListener implements ActionListener{
		public void actionPerformed(ActionEvent a) {
			buildTrackAndStart();
		}
	}//close MyStartListener
	
	public class MyStopListener implements ActionListener{
		public void actionPerformed(ActionEvent a) {
			sequencer.stop();
		}
	}//close MyStopListener
	
	public class MyUpTempoListener implements ActionListener{
		public void actionPerformed(ActionEvent a) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 1.03));
		}
		
	}//close MyUpTempoListener
		
	public class MyDownTempoListener implements ActionListener{
		public void actionPerformed(ActionEvent a) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 0.97));
		}	
	}//close myDownTempoListener
		
	public void makeTracks(int[] list) {
		for(int i = 0; i < 16; i++) {
			int key = list[i];
			
			if(key != 0) {
				track.add(makeEvent(144,9,key,100,i));
				track.add(makeEvent(128,9,key,100, i+1));
			}
		}
	}
	
	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event = new MidiEvent(a, tick);
		}catch(Exception e) {e.printStackTrace();}
		return event;
	}
}