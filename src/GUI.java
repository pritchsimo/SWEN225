import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GUI extends JFrame {

    private GUI gui = this;

    public List<String> playerOptions = new ArrayList<>();
    public List<String> weaponOptions = new ArrayList<>();
    public List<String> roomOptions = new ArrayList<>();

    //menu
    private JMenuBar menubar;
    private JMenu options;
    private JMenuItem newGameMenuItem;
    private JMenuItem exitMenuItem;

    //display
    private JPanel board;
    private ImageIcon boardPic;

    private JSplitPane split;
    private JPanel controls;
    private ImageIcon dicePic1;
    private ImageIcon dicePic2;
    private JTextArea textOutputArea;
    private JScrollPane scroll;

    //Tabs
    private JDialog cardPane;

    //Cluedo
    private Cluedo cluedo;


    public GUI(){
        initUI();
    }

    private void initUI(){

        this.setLayout(new BorderLayout());

        createMenuBar();
        createBoard();
        createInterface();
        listSetup();

        this.add(board, BorderLayout.CENTER);
        //this.add(controls, BorderLayout.SOUTH);
        this.add(split, BorderLayout.SOUTH);

        setTitle("Cluedo");
        setSize(800, 835);
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
    }


    private void createMenuBar() {

        menubar = new JMenuBar();
        options = new JMenu("Options");

        newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGamePane();
                if (cluedo != null){
                    gameRun();
                }
            }
        });
        options.add(newGameMenuItem);

        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setMnemonic(KeyEvent.VK_E);
        exitMenuItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        options.add(exitMenuItem);

        menubar.add(options);
        setJMenuBar(menubar);
    }

    private void createBoard(){
        board = new JPanel();
        //board.setPreferredSize(new Dimension(500, 500));
        board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));
        Border edge = BorderFactory.createEmptyBorder(15, 70, 5, 5);
        board.setBorder(edge);

        boardPic = new ImageIcon("DiceImages/Board.png");
        JLabel boardLabel = new JLabel(boardPic);
        board.add(boardLabel);

        board.setBackground(Color.gray);    //need to create board

    }

    private void createInterface(){
        split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setDividerSize(5);
        split.setContinuousLayout(true);
        split.setResizeWeight(1);
        split.setBorder(BorderFactory.createEmptyBorder());

        controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.LINE_AXIS));
        Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        controls.setBorder(edge);

        dicePic1 = new ImageIcon("DiceImages/3.jpg");    //need to change so that they can be updated
        dicePic2 = new ImageIcon("DiceImages/2.jpg");
        JLabel dice1 = new JLabel(dicePic1);
        JLabel dice2 = new JLabel(dicePic2);

        controls.add(dice1);
        controls.add(dice2);

        controls.add(Box.createRigidArea(new Dimension(30, 0)));

        JPanel navigation = new JPanel();    //direction buttons
        navigation.setMaximumSize(new Dimension(150, 60));
        navigation.setLayout(new GridLayout(2, 3));

        JButton upwards = new JButton("\u2191");
        upwards.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //go upwards
            }
        });
        JButton left = new JButton("\u2190");
        left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //go left
            }
        });
        JButton downwards = new JButton("\u2193");
        downwards.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //go downwards
            }
        });
        JButton right = new JButton("\u2192");
        right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //go right
            }
        });

        navigation.add(new JLabel(""));
        navigation.add(upwards);
        navigation.add(new JLabel(""));
        navigation.add(left);
        navigation.add(downwards);
        navigation.add(right);

        controls.add(navigation);
        controls.add(Box.createRigidArea(new Dimension(20, 0)));

        JButton showCardsButton = new JButton("Cards");     //cards button
        showCardsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                cardPane();
            }
        });

        controls.add(showCardsButton);
        controls.add(Box.createRigidArea(new Dimension(20, 0)));

        JButton evidenceButton = new JButton("Evidence");    //evidence button
        evidenceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //display evidence
            }
        });

        controls.add(evidenceButton);
        controls.add(Box.createRigidArea(new Dimension(20, 0)));

        JButton makeSuggestion = new JButton("Make Suggestion");     //cards button
        makeSuggestion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //make suggestion
            }
        });

        controls.add(makeSuggestion);
        controls.add(Box.createRigidArea(new Dimension(20, 0)));

        JButton makeAccusation = new JButton("Make Accusation");    //evidence button
        makeAccusation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //make accusation
            }
        });

        controls.add(makeAccusation);

        textOutputArea = new JTextArea(2, 0);
        textOutputArea.setLineWrap(true);
        textOutputArea.setWrapStyleWord(true); // pretty line wrap.
        textOutputArea.setEditable(false);
        scroll = new JScrollPane(textOutputArea);
        DefaultCaret caret = (DefaultCaret) textOutputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        split.setTopComponent(controls);
        split.setBottomComponent(scroll);



    }

    private void newGamePane(){
        int numPlayers;
        while (true){
            String input = JOptionPane.showInputDialog(this, "How many players? (2-6)");
            if (input == null) return;
            numPlayers = Integer.parseInt(input);
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
                cluedo = new Cluedo();
                for (int j = 0; j < 6; j++){
                    JRadioButton b = buttons.get(j);
                    if (b.isSelected()){
                        cluedo.addPlayer(playerOptions.get(j), playerName.getText());
                        playersLeft[j] = false;
                    }
                }
                cluedo.playerSetup();
                cluedo.setup(false);


                //add players to board

            } else {
                return;
            }
        }
    }

    private void cardPane() {
        cardPane = new JDialog(this, "My Cards");
        JButton exit = new JButton("Close");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPane.dispose();
            }
        });

        if (cluedo == null) return;

        String[] cards = new String[cluedo.getMove().getCards().size()];
        for (int i = 0; i < cluedo.getMove().getCards().size(); i++) {
            cards[i] = " " + cluedo.getMove().getCards().get(i).getName();
        }

        cardPane.add(new JList(cards));

        JPanel panel = new JPanel();
        panel.add(exit);
        cardPane.add(panel, BorderLayout.SOUTH);
        cardPane.setSize(300, 500);
        cardPane.setLocationRelativeTo(null);
        cardPane.setVisible(true);
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

    private void gameRun(){

    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });

    }
}
