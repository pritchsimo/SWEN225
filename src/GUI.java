import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GUI extends JFrame {

    private GUI gui = this;

    public List<String> playerOptions = new ArrayList<>();
    public List<String> weaponOptions = new ArrayList<>();
    public List<String> roomOptions = new ArrayList<>();

    //menu things
    private JMenuBar menubar;
    private JMenu options;
    private JMenuItem newGameMenuItem;
    private JMenuItem exitMenuItem;


    public GUI(){
        initUI();
    }

    private void initUI(){

        createMenuBar();
        createBoard();
        listSetup();

        setTitle("Cluedo");
        setSize(500, 700);
        setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {    //confirms user does want to leave
            @Override
            public void windowClosing(WindowEvent e) {
                int a=JOptionPane.showConfirmDialog(gui,"Are you sure?");
                if(a==JOptionPane.YES_OPTION){
                    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });

        newGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGamePane();
            }
        });
    }


    private void createMenuBar() {

        menubar = new JMenuBar();
        //ImageIcon exitIcon = new ImageIcon("src/main/resources/exit.png");

        options = new JMenu("Options");
        //file.setMnemonic(KeyEvent.VK_F);

        newGameMenuItem = new JMenuItem("New Game");
        //newGameMenuItem.setMnemonic(KeyEvent.VK_E);
        //exitMenuItem.setToolTipText("Exit application");
//        newGameMenuItem.addActionListener((ActionEvent event) -> {
//            System.exit(0);
//        });
        options.add(newGameMenuItem);

        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setMnemonic(KeyEvent.VK_E);
        //exitMenuItem.setToolTipText("Exit application");
        exitMenuItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        options.add(exitMenuItem);

        menubar.add(options);
        setJMenuBar(menubar);
    }

    private void createBoard(){
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();

        topPanel.setBackground(Color.gray);    //need to createBoard
        bottomPanel.add(topPanel);

        bottomPanel.setBorder(new EmptyBorder(new Insets(20, 20, 150, 20)));

        add(bottomPanel);
        pack();
    }

    private void newGamePane(){
        int numPlayers;
        while (true){
            numPlayers = Integer.parseInt(JOptionPane.showInputDialog(this, "How many players? (2-6)"));
            if (numPlayers < 2 || numPlayers > 6){
                JOptionPane.showMessageDialog(this,"Please enter a valid number of players","Wrong Input",JOptionPane.WARNING_MESSAGE);
            } else {
                break;
            }
        }

        boolean playersLeft[] = new boolean[6];
        Arrays.fill(playersLeft, true);

        for (int i = 0; i < numPlayers; i++) {
            JTextField playerName = new JTextField(10);
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Player Name:"));
            myPanel.add(playerName);
            ButtonGroup bg = new ButtonGroup();

            List<JRadioButton> buttons = new ArrayList<>();

            for (int j = 0; j < 6; j++){
                JRadioButton player = new JRadioButton(playerOptions.get(j));
                buttons.add(player);
                bg.add(player);
                myPanel.add(player);

                player.setEnabled(playersLeft[j]);
            }
            int result;
            while (true){
                result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Player " + (i + 1), JOptionPane.OK_CANCEL_OPTION);
                if (!playerName.getText().equals("") && (buttons.get(0).isSelected() || buttons.get(1).isSelected() || buttons.get(2).isSelected() ||
                        buttons.get(3).isSelected() || buttons.get(4).isSelected() || buttons.get(5).isSelected())){
                    break;
                } else if (result == JOptionPane.CANCEL_OPTION){
                    return;
                }else {
                    JOptionPane.showMessageDialog(this,"Please enter a name and pick player","Wrong Input",JOptionPane.WARNING_MESSAGE);
                }

            }

            if (result == JOptionPane.OK_OPTION) {
                String s = "Player " + (i + 1) + "'s name: " + playerName.getText() + " Player Character: ";
                for (int j = 0; j < 6; j++){
                    JRadioButton b = buttons.get(j);
                    if (b.isSelected()){
                        s += playerOptions.get(j);
                        playersLeft[j] = false;
                    }
                }
                System.out.println(s);     //temporary
            } else {
                return;
            }
        }
    }

    private void showDiceRoll(){

    }

    private void listSetup(){
        playerOptions.add("Miss Scarlett");
        playerOptions.add("Col. Mustard");
        playerOptions.add("Mrs. White");
        playerOptions.add("Mr. Green");
        playerOptions.add("Mrs. Peacock");
        playerOptions.add("Prof. Plum");

        weaponOptions.add("Candlestick");
        weaponOptions.add("Dagger");
        weaponOptions.add("Lead Pipe");
        weaponOptions.add("Revolver");
        weaponOptions.add("Rope");
        weaponOptions.add("Spanner");

        roomOptions.add("Kitchen");
        roomOptions.add("Ballroom");
        roomOptions.add("Conservatory");
        roomOptions.add("Dining Room");
        roomOptions.add("Billiard Room");
        roomOptions.add("Library");
        roomOptions.add("Study");
        roomOptions.add("Hall");
        roomOptions.add("Lounge");
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}
