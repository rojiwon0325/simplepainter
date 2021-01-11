import javax.swing.JFrame;

public class SimplePainter{
	public static void main(String[] args) {
		JFrame frame = new JFrame("SIMPLE PAINTER");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.setContentPane(new SimplePainterView());
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
