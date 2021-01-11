import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MenuController{
	
	SimplePainterView parent;
	Font fnt;
	
	private JPanel menuPanel;
	private JPanel optionPanel;
	private JPanel messagePanel;
	
	private JButton btnMenuArray[];
	private ButtonListener buttonL;
	
	private JTextField txtSize;
	private JButton btnColorChooser;
	private JCheckBox chkFill;
	private MyListener ActionL;
	
	private JLabel nowMode;
	private JLabel nowSize;
	private JLabel nowFill;
	//private JLabel now
	
	public MenuController(SimplePainterView _parent)
	{
		parent = _parent;
		fnt = new Font("Verdana", Font.BOLD, 16);
		menuPanel = new JPanel();
		menuPanel.setBounds(10,520,400,200);
		menuPanel.setBackground(Color.white);
		menuPanel.setBorder(BorderFactory.createTitledBorder("MENU"));
		menuPanel.setLayout(new GridLayout(2,4));
		parent.add(menuPanel);
		
		optionPanel = new JPanel();
		optionPanel.setBounds(410,520,200,200);
		optionPanel.setBackground(Color.white);
		optionPanel.setBorder(BorderFactory.createTitledBorder("OPTION"));
		optionPanel.setLayout(new GridLayout(3,1));
		parent.add(optionPanel);
		
		messagePanel = new JPanel();
		messagePanel.setBounds(610,520,200,200);
		messagePanel.setBackground(Color.white);
		messagePanel.setBorder(BorderFactory.createTitledBorder("MESSAGE"));
		messagePanel.setLayout(new GridLayout(3,2));
		parent.add(messagePanel);
		
		ActionL = new MyListener();
		buttonL = new ButtonListener();
		btnMenuArray = new JButton[8];
		for(int i = 0; i < 8 ; i++)
		{
			btnMenuArray[i] = new JButton(Constants.MENU[i]);
			btnMenuArray[i].setForeground(Constants.HOVERING[0]);
			menuPanel.add(btnMenuArray[i]);
			btnMenuArray[i].addMouseListener(buttonL);
		}
		
		btnColorChooser = new JButton("COLOR CHOOSER");
		btnColorChooser.setForeground(Constants.HOVERING[0]);
		btnColorChooser.addActionListener(ActionL);
		optionPanel.add(btnColorChooser);
		
		txtSize = new JTextField();
		txtSize.setFont(fnt);
		txtSize.addActionListener(ActionL);
		optionPanel.add(txtSize);
		
		chkFill = new JCheckBox("FILL");
		chkFill.setFont(fnt);
		chkFill.setVisible(false);
		chkFill.addActionListener(ActionL);
		optionPanel.add(chkFill);
		
		nowMode = new JLabel("Mode: DOT", SwingConstants.CENTER);
		nowMode.setFont(fnt);
		messagePanel.add(nowMode);
		nowSize = new JLabel("Size: 10", SwingConstants.CENTER);
		nowSize.setFont(fnt);
		messagePanel.add(nowSize);
		nowFill = new JLabel("Not FILL", SwingConstants.CENTER);
		nowFill.setFont(fnt);
		messagePanel.add(nowFill);
		nowFill.setVisible(false);
	}
	
	private class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object b = e.getSource();
			if(b == btnColorChooser) {
				Color color = JColorChooser.showDialog(parent, "COLOR CHOOSER", DrawController.getSelectedColor());
				if(color == null) {color = DrawController.getSelectedColor();}
				DrawController.setSelectedColor(color);
				nowMode.setForeground(color);
				nowSize.setForeground(color);
				nowFill.setForeground(color);
			}
			else if(b == txtSize) {
				DrawController.setSize(DrawController.getDataSize());
				if(Integer.parseInt(txtSize.getText()) > 100) {
					DrawController.setSize(100);
				}
				else {
					DrawController.setSize(Integer.parseInt(txtSize.getText()));
				}
				txtSize.setText("");
				nowSize.setText("Size: "+DrawController.getDataSize());
			}
			else if(b == chkFill) {
				DrawController.setFill(chkFill.isSelected());
				if(chkFill.isSelected()) {
					DrawController.setFill(true);
					nowFill.setText("FILL");
				}
				else {
					DrawController.setFill(false);
					nowFill.setText("Not FILL");
				}
			}
			
		}
		
	}
	private class ButtonListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			JButton btn = (JButton)e.getSource();

			for(int i = 0 ; i < 8 ; i++) {
				if(btn == btnMenuArray[i]) {
					if(i < 6) {
						nowMode.setText("Mode: "+Constants.MENU[i]);
						DrawController.setDrawMode(i);
						nowSize.setVisible(true);
						if(i == Constants.CLEARBOX) {nowSize.setVisible(false);}
						if(i == Constants.RECT || i == Constants.OVAL) {
							chkFill.setVisible(true);
							nowFill.setVisible(true);
						}
						else {
							chkFill.setVisible(false);
							nowFill.setVisible(false);
						}
						DrawController.setSize(DrawController.getDataSize());
						nowSize.setText("Size: "+DrawController.getDataSize());
					}
					else if(i == Constants.UNDO) {DrawController.unDo();}
					else if(i == Constants.CLEAR) {DrawController.clear();}
				}
			}
			
		}

		public void mouseEntered(MouseEvent e) {
			JButton btn = (JButton)e.getSource();
			btn.setForeground(Constants.HOVERING[1]);
		}

		public void mouseExited(MouseEvent e) {
			JButton btn = (JButton)e.getSource();
			btn.setForeground(Constants.HOVERING[0]);
		}
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
}
