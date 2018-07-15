import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextArea1 implements ActionListener {

	JTextArea text;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TextArea1 gui = new TextArea1();
		gui.go();
	}
	
	public void go() {
		//Creating the necessary JComponents for a basic UI.
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JButton button = new JButton("Just Click it");
		button.addActionListener(this);	//Adding actionListener as the same class.
		text = new JTextArea(10,20);
		text.setLineWrap(true);
		
		//Adding TextArea to a scroller.
		//Enabling vertical scrolling and disable the horizontal scroll.
		JScrollPane scroller = new JScrollPane(text);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//JTextArea is added as a part of JScrollPane in the constructor, i.e., JTextArea is a part of JScrollPane.
		//Hence, we add scroller to the pane and not the TextArea itself.
		panel.add(scroller);
		
		//Placing the JComponents on the panel.
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.getContentPane().add(BorderLayout.SOUTH, button);
		
		//Setting the JFrame size and visibility.
		frame.setSize(300,300);
		frame.setVisible(true);
	}
	
	//Action for buutton click event.
	public void actionPerformed(ActionEvent event) {
		text.append("Button clicked\n");
	}

}
