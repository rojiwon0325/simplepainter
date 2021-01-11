import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SimplePainterView extends JPanel{
	
	public SimplePainterView()
	{
		setPreferredSize(new Dimension(820,730));
		setLayout(null);
		setBackground(Color.white);
		
		add(DrawController.getInstance());
		new MenuController(this);
		
	}
}
