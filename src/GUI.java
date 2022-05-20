import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BoxLayout;

public class GUI implements ActionListener, ItemListener {

    private JTextField playerNameField;
    private JCheckBox coinCheckBox;
    private JCheckBox dungeonCheckBox;
    private JCheckBox slayerCheckBox;
    private JLabel pictureLabel;
    private JPanel picturePanel;
    private JLabel coinLabel;
    private JFrame frame;
    private Networking client;
    private Player player;
    private Profile profile;

    public GUI() {
        client = new Networking();
    }

    public void setUpGUI() {
        JPanel textFieldPanel = new JPanel();
        JLabel name = new JLabel("Enter Player's name: ");
        playerNameField = new JTextField(16);
        JButton submitButton = new JButton("Submit");
        JButton clearButton = new JButton("Clear");
        coinCheckBox = new JCheckBox("Coins");
        dungeonCheckBox = new JCheckBox(("Dungeons"));
        slayerCheckBox = new JCheckBox("Slayers");

        textFieldPanel.add(name);
        textFieldPanel.add(playerNameField);
        textFieldPanel.add(submitButton);
        textFieldPanel.add(clearButton);
        textFieldPanel.add(coinCheckBox);
        textFieldPanel.add(dungeonCheckBox);
        textFieldPanel.add(slayerCheckBox);

        frame = new JFrame("Skyblock Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel titlePanel = new JPanel();
        JLabel titleText = new JLabel("Current Player                  ");
        titleText.setFont(new Font("Helvetica", Font.PLAIN, 30)); //Helvetica
        titleText.setForeground(Color.blue);
        titlePanel.add(titleText);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(textFieldPanel);

        frame.add(titlePanel, BorderLayout.NORTH);
        ImageIcon image = new ImageIcon("skyblock.jpeg");
        Image imageData = image.getImage();
        Image scaledImage = imageData.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);
        picturePanel = new JPanel();
        pictureLabel = new JLabel(image);
        coinLabel = new JLabel();
        picturePanel.add(pictureLabel);
        frame.add(picturePanel, BorderLayout.CENTER);

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
        coinCheckBox.addItemListener(this);
        dungeonCheckBox.addItemListener(this);
        slayerCheckBox.addItemListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String text = button.getText();

        if (text.equals("Submit")) {
            String name = playerNameField.getText();
            player = client.getPlayer(name);
            profile = client.getProfile(name);
            pictureLabel.setVisible(false);
            picturePanel = new JPanel();
            frame.add(picturePanel, BorderLayout.CENTER);
//            try {
//                URL imageURL = new URL("https:" + weather.getImageURL());
//                BufferedImage image = ImageIO.read(imageURL);
//                JLabel imageLabel = new JLabel(new ImageIcon(image));
//                temperatureLabel.setText("Temperature: " + weather.getTempF() + "F     Condition: " + weather.getCondition());
//                picturePanel.add(temperatureLabel, BorderLayout.NORTH);
//                picturePanel.add(imageLabel);
//            } catch (Exception ee) {
//                System.out.println(ee.getMessage());
//            }
//        } else if (text.equals("Clear")) {
//            zipCodeField.setText("");
//        }
//
        }
    }
    public void itemStateChanged(ItemEvent e) {

        JCheckBox cb = (JCheckBox) e.getSource();
        String option = cb.getText();

        if (option.equals("Coins")) {
            coinLabel.setText("Profile: " + profile.getCute_name() + " Purse: " + profile.getPurse());
            coinLabel.setVisible(true);
        }
        else if(option.equals("Dungeons")){
            String url = "https://sky.shiiyu.moe/api/v2/dungeons/" + player.getName();
            Networking.makeAPICall(url);
        }
        else if(option.equals("Slayers")){
            String url = "https://sky.shiiyu.moe/api/v2/slayers/" + player.getName();
            Networking.makeAPICall(url);
        }
    }
}