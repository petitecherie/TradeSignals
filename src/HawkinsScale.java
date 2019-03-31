import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class HawkinsScale extends JFrame implements IStringByString {
	/**
	 * JFrame SWING-based class, which shows line by line Dr David R Hawkins's
	 * Levels of Consciousness <a
	 * href>https:http://www.spiritualteachers.org/david-hawkins/></a>
	 * <p>
	 * A user can:
	 * <ul>
	 * <li>step by step to see each level, by pressing Button "Step+",and test
	 * yourself, at what level are his feelings and emotions;
	 * <li>to return to the level below by pressing Button "Step-";
	 * <li>to read about Hawkins's Levels of Consciousness in Frame "i";
	 * <li>to choose Language;
	 * </ul>
	 * <p>
	 * 
	 * @autor Elena Smagina <a href =
	 *        https://www.xing.com/profile/Elena_Smagina/cv /a>
	 * @version 1.1
	 */
	private static final long serialVersionUID = 1L;
	private static List<JLabel> shownStringsList = new ArrayList<JLabel>();
	private static List<String> allStringsList = new ArrayList<String>();// tam
																			// hranitsa
																			// ves
																			// text
																			// kotorij
																			// postrocno
																			// nugen
	private static List<String> stringsListInfo = new ArrayList<String>();
	private static List<String> out = new ArrayList<String>();// subbuffer to
																// get strings
																// from text

	static Color colorField = new Color(224, 234, 244);
	static Color color = new Color(160, 200, 235);
	private static Image image = Toolkit.getDefaultToolkit()
			.getImage("C:/Wohnung/Eclipse-Swing-0119/Hawkins/src/resources/hawckinsIcon.jpg");
	final static Font font = new Font("Verdana", Font.PLAIN, 20);

	static Locale rus = new Locale("ru", "RU");
	static Locale de = Locale.GERMANY;
	static Locale en = Locale.ENGLISH;
	static ResourceBundle rb = ResourceBundle.getBundle("resources/MyResources", Locale.getDefault());
	final static JPanel labelPanel = new JPanel();

	public static void createGUI() {

		ResourceBundle rb = ResourceBundle.getBundle("resources/MyResources", Locale.getDefault());// nado
		JFrame frame = new JFrame(rb.getString("nameFrame"));
		frame.setIconImage(image);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel buttonsPanel = new JPanel();
		Component rigidArea = Box.createRigidArea(new Dimension(520, 10));// "empty
																			// space"
																			// //
																			// platz"
		JMenuBar toolbar = new JMenuBar();
		toolbar.setLayout((new FlowLayout(FlowLayout.LEFT, 10, 5)));
		JMenuItem infoItem = new JMenuItem("?");
		infoItem.setFont(font);
		JTextPane infoText = new JTextPane();
		toolbar.add(infoItem);
		buttonsPanel.add(toolbar);
		toolbar.add(rigidArea);

		JButton plusButton = new JButton(rb.getString("stepPlus"));
		plusButton.setFont(font);
		plusButton.setFocusPainted(false);
		buttonsPanel.add(plusButton);
		toolbar.add(plusButton);

		JButton minusButton = new JButton(rb.getString("stepMinus"));
		minusButton.setFont(font);
		minusButton.setFocusable(false);
		buttonsPanel.add(minusButton);
		toolbar.add(minusButton);

		buttonsPanel.add(rigidArea);// add empty space

		// organize menu to switch language
		JLabel changeLang = new JLabel(rb.getString("changeLang"), new ImageIcon(rb.getString("flag")), 2);
		toolbar.add(rigidArea);
		toolbar.add(changeLang);
		String[] elements = new String[] { " RU", " EN", " DE" };
		JComboBox<String> comboLangChange = new JComboBox<String>(elements);
		comboLangChange.setPreferredSize(new Dimension(50, 35));
		comboLangChange.setSelectedIndex(Integer.valueOf(rb.getString("selectedLangIndex")));
		toolbar.add(comboLangChange);

		comboLangChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String selectedLang = (String) comboLangChange.getSelectedItem();

				if (selectedLang.equals(" DE")) {
					frame.dispose();
					changeLocale(de);
				} else if (selectedLang.equals(" EN")) {
					frame.dispose();
					changeLocale(en);
				} else {
					frame.dispose();
					changeLocale(rus);
				}
			}
		});

		final JScrollPane scrollPane = new JScrollPane(labelPanel);
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		labelPanel.setBackground(colorField);
		// to add string
		plusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int number = shownStringsList.size() + 1;
				JLabel label = new JLabel(allStringsList.get(number));
				shownStringsList.add(label);
				label.setFont(font);
				labelPanel.add(label);
				scrollPane.revalidate();// to clear Panel
				labelPanel.repaint();
			}
		});
		// to delete string
		minusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (shownStringsList.size() > 0) {
					int index = shownStringsList.size() - 1;
					JLabel label = shownStringsList.remove(index);
					labelPanel.remove(label);
					labelPanel.repaint();
					scrollPane.revalidate();
				}
			}
		});
		// INFO frame
		infoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog infoFrame = new JDialog();
				infoFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				infoFrame.setLocation(250, 180);
				infoFrame.setTitle(rb.getString("info"));
				infoFrame.setSize(700, 350);
				infoFrame.setBackground(color);
				infoFrame.setVisible(true);
				infoFrame.setResizable(false);
				infoFrame.add(infoText);

				infoText.setBorder(new EmptyBorder(30, 30, 30, 30));
				infoText.setContentType("text/html");
				infoText.setEditable(false);

				try {
					IStringByString.getStrings(rb.getString("fileNameInfo"), stringsListInfo);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				infoText.setText(stringsListInfo.get(0));
				stringsListInfo.removeAll(stringsListInfo);
			}
		});

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(toolbar, BorderLayout.NORTH);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setBackground(colorField);
		frame.setPreferredSize(new Dimension(1010, 570));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		Locale.setDefault(rus);
		toStart();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				createGUI();
			}
		});
	}

	// to change Locale
	static void changeLocale(Locale currentLocale) {
		clearData();
		Locale.setDefault(currentLocale);
		toStart();
		createGUI();
	}

	// to clear all Lists
	static void clearData() {
		shownStringsList.removeAll(shownStringsList);
		allStringsList.removeAll(allStringsList);
		stringsListInfo.removeAll(stringsListInfo);
		out.removeAll(out);
		labelPanel.removeAll();
		labelPanel.repaint();
	}

	// to start application
	static void toStart() {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("resources/MyResources", Locale.getDefault());
			IStringByString.getStrings(rb.getString("fileName"), allStringsList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		out.removeAll(out);
	}
}