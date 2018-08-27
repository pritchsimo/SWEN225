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
    private BoardPanel boardPanel;

    private JSplitPane split;
    private JPanel controls;
    private JTextArea textOutputArea;
    private JScrollPane scroll;

    //Panes
    private JDialog cardPane;
    private JDialog evidencePane;
    private JDialog suggestionPane;
    private JDialog accusationPane;
    private JDialog refutePane;

    //Cluedo
    private Cluedo cluedo;
    private Player current;
    private int movesRemaining;


    public GUI() {
        initUI();
    }

    private void initUI() {

        this.setLayout(new BorderLayout());

        createMenuBar();
        createBoard();
        createInterface();
        listSetup();

        this.add(board, BorderLayout.CENTER);
        this.add(split, BorderLayout.SOUTH);

        setTitle("Cluedo");
        setSize(770, 835);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {    //confirms user does want to leave
            @Override
            public void windowClosing(WindowEvent e) {        //checks user does want to exit
                int a = JOptionPane.showConfirmDialog(gui, "Are you sure?");
                if (a == JOptionPane.YES_OPTION) {
                    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });

        this.addMouseListener(new MouseAdapter() {    //used for exiting rooms
            public void mouseReleased(MouseEvent e) {
                if (current != null){
                    if (current.getRoom() != null && movesRemaining > 0){
                        Room r = current.getRoom();
                        for (Point doorPos: r.getDoorPos()){
                            if (e.getX()-50 >= (int) (doorPos.x*23.75)+1 && e.getX()-50 <= (int) (doorPos.x*23.75)+24.75 &&
                                    e.getY()-65 >= (int) (doorPos.y*23.75)+1 && e.getY()-65 <= (int) (doorPos.y*23.75)+24.75) {
                                current.getRoom().playerLeave(current);
                                current.setRoom(null);
                                current.setCoords(doorPos);
                                textOutputArea.append("It is " + current.getPlayerName() + "'s (" + current.getCharacterName() +
                                        ") turn. You have " + movesRemaining + " moves remaining.");
                                movesRemaining--;
                                boardPanel.repaint();
                            }
                        }

                    }
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

    private void createBoard() {
        board = new JPanel();
        board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));
        Border edge = BorderFactory.createEmptyBorder(15, 50, 5, 5);
        board.setBorder(edge);

        boardPanel = new BoardPanel();
        board.add(boardPanel);

    }

    private void createInterface() {
        split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setDividerSize(5);
        split.setContinuousLayout(true);
        split.setResizeWeight(1);
        split.setBorder(BorderFactory.createEmptyBorder());

        controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.LINE_AXIS));
        Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        controls.setBorder(edge);

        //direction buttons
        JPanel navigation = new JPanel();
        navigation.setMaximumSize(new Dimension(150, 60));
        navigation.setLayout(new GridLayout(2, 3));

        JButton upwards = new JButton("\u2191");
        upwards.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                movePlayer("upwards");
            }
        });
        JButton left = new JButton("\u2190");
        left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                movePlayer("left");
            }
        });
        JButton downwards = new JButton("\u2193");
        downwards.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                movePlayer("downwards");
            }
        });
        JButton right = new JButton("\u2192");
        right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                movePlayer("right");
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

        //other buttons
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
                evidencePane();
            }
        });
        controls.add(evidenceButton);
        controls.add(Box.createRigidArea(new Dimension(20, 0)));

        JButton makeSuggestion = new JButton("Make Suggestion");     //cards button
        makeSuggestion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                suggestionPane();
            }
        });
        controls.add(makeSuggestion);
        controls.add(Box.createRigidArea(new Dimension(20, 0)));

        JButton makeAccusation = new JButton("Make Accusation");    //evidence button
        makeAccusation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                accusationPane();
            }
        });
        controls.add(makeAccusation);
        controls.add(Box.createRigidArea(new Dimension(20, 0)));

        JButton endTurn = new JButton("End Turn");
        endTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                setNextMove();
            }
        });
        controls.add(endTurn);

        //textArea at the bottom
        textOutputArea = new JTextArea(2, 0);
        textOutputArea.setLineWrap(true);
        textOutputArea.setWrapStyleWord(true);
        textOutputArea.setEditable(false);
        scroll = new JScrollPane(textOutputArea);
        DefaultCaret caret = (DefaultCaret) textOutputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        split.setTopComponent(controls);
        split.setBottomComponent(scroll);


    }

    private void movePlayer(String direction){
        if (current != null){
            if (movesRemaining == 0){
                textOutputArea.append("Unable to move. You have " + movesRemaining + " moves remaining.\n");
                return;
            }
            if (current.translate(direction, 1)){
                boardPanel.repaint();
                movesRemaining--;

                textOutputArea.append("It is " + current.getPlayerName() + "'s (" + current.getCharacterName() + ") turn. You have " + movesRemaining + " moves remaining.");
                if (current.getRoom() != null){
                    textOutputArea.append(" Click on a door square to exit room.\n");
                } else {
                    textOutputArea.append("\n");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Go to options and start a new game.", "Start New Game", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void newGamePane() {
        int numPlayers;
        while (true) {
            String input = JOptionPane.showInputDialog(this, "How many players? (2-6)");
            if (input == null) return;
            try {
                numPlayers = Integer.parseInt(input);
                if (numPlayers < 2 || numPlayers > 6) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number of players", "Wrong Input", JOptionPane.WARNING_MESSAGE);
                } else {
                    break;
                }
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Please enter a valid number of players", "Wrong Input", JOptionPane.WARNING_MESSAGE);
            }
        }

        boolean playersLeft[] = new boolean[6];
        Arrays.fill(playersLeft, true);

        cluedo = new Cluedo();

        for (int i = 0; i < numPlayers; i++) {
            JTextField playerName = new JTextField(10);
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Player Name:"));
            myPanel.add(playerName);
            ButtonGroup bg = new ButtonGroup();

            List<JRadioButton> buttons = new ArrayList<>();

            for (int j = 0; j < 6; j++) {
                JRadioButton player = new JRadioButton(playerOptions.get(j));
                buttons.add(player);
                bg.add(player);
                myPanel.add(player);
                player.setEnabled(playersLeft[j]);
            }
            int result;
            while (true) {
                result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Player " + (i + 1), JOptionPane.OK_CANCEL_OPTION);
                if (!playerName.getText().equals("") && (buttons.get(0).isSelected() || buttons.get(1).isSelected() || buttons.get(2).isSelected() ||
                        buttons.get(3).isSelected() || buttons.get(4).isSelected() || buttons.get(5).isSelected())) {
                    break;
                } else if (result == JOptionPane.CANCEL_OPTION) {
                    cluedo = null;
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a name and pick player", "Wrong Input", JOptionPane.WARNING_MESSAGE);
                }

            }

            if (result == JOptionPane.OK_OPTION) {

                for (int j = 0; j < 6; j++) {
                    JRadioButton b = buttons.get(j);
                    if (b.isSelected()) {
                        cluedo.addPlayer(playerOptions.get(j), playerName.getText());
                        playersLeft[j] = false;
                    }
                }
            } else {
                cluedo = null;
                return;
            }
        }
        cluedo.playerSetup();
        cluedo.setup(false);

        boardPanel.setPlayers(cluedo.getPlayers());
        setNextMove();
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

        if (cluedo == null) {
            JOptionPane.showMessageDialog(this, "Go to options and start a new game.", "Start New Game", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<String> playerCards = new ArrayList<>();
        List<String> weaponCards = new ArrayList<>();
        List<String> roomCards = new ArrayList<>();
        List<Card> allCards = current.getCards();
        List<String> cardTemp = new ArrayList<>();
        for (int i = 0; i < allCards.size(); i++) {
            if (allCards.get(i).getType().equals("Player")) {
                playerCards.add(allCards.get(i).getName());
            } else if (allCards.get(i).getType().equals("Weapon")){
                weaponCards.add(allCards.get(i).getName());
            } else {
                roomCards.add(allCards.get(i).getName());
            }
        }
        cardTemp.add("Player Cards: ");
        cardTemp.addAll(playerCards);
        cardTemp.add(" ");
        cardTemp.add("Weapon Cards: ");
        cardTemp.addAll(weaponCards);
        cardTemp.add(" ");
        cardTemp.add("Room Cards: ");
        cardTemp.addAll(roomCards);
        String[] cards = cardTemp.toArray(new String[cardTemp.size()]);

        cardPane.add(new JList(cards));

        JPanel panel = new JPanel();
        panel.add(exit);
        cardPane.add(panel, BorderLayout.SOUTH);
        cardPane.setSize(300, 500);
        cardPane.setLocationRelativeTo(null);
        cardPane.setVisible(true);
    }

    private void evidencePane() {
        evidencePane = new JDialog(this, "My Evidence");
        JButton exit = new JButton("Close");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evidencePane.dispose();
            }
        });

        if (cluedo == null) {
            JOptionPane.showMessageDialog(this, "Go to options and start a new game.", "Start New Game", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int base1 = playerOptions.size();
        int base2 = base1 + weaponOptions.size();
        int base3 = base2 + roomOptions.size();
        String[] evidence = new String[base1 + weaponOptions.size() + roomOptions.size() + 5];
        evidence[0] = "Players: ";
        for (int i = 0; i < playerOptions.size(); i++) {
            if (current.getKnownEvidence().contains(playerOptions.get(i))) {
                evidence[i + 1] = String.format("[X] %s", playerOptions.get(i));
            } else {
                evidence[i + 1] = String.format("[   ] %s", playerOptions.get(i));
            }
        }
        evidence[base1 += 1] = " ";

        evidence[base1 += 1] = "Weapons: ";
        for (int i = 0; i < weaponOptions.size(); i++) {
            if (current.getKnownEvidence().contains(weaponOptions.get(i))) {
                evidence[i + base1 + 1] = String.format("[X] %s", weaponOptions.get(i));
            } else {
                evidence[i + base1 + 1] = String.format("[   ] %s", weaponOptions.get(i));
            }
        }
        evidence[base2 += 3] = " ";

        evidence[base2 += 1] = "Rooms: ";
        for (int i = 0; i < roomOptions.size(); i++) {
            if (current.getKnownEvidence().contains(roomOptions.get(i))) {
                evidence[i + base2 + 1] = String.format("[X] %s", roomOptions.get(i));
            } else {
                evidence[i + base2 + 1] = String.format("[   ] %s", roomOptions.get(i));
            }
        }

        evidencePane.add(new JList(evidence));

        JPanel panel = new JPanel();
        panel.add(exit);
        evidencePane.add(panel, BorderLayout.SOUTH);
        evidencePane.setSize(300, 700);
        evidencePane.setLocationRelativeTo(null);
        evidencePane.setVisible(true);
    }

    private void suggestionPane() {
        List<String> suggestion = new ArrayList<>();
        suggestionPane = new JDialog(this, "Make a Suggestion");

        if (cluedo == null) {
            JOptionPane.showMessageDialog(this, "Go to options and start a new game.", "Start New Game", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (current.getRoom() == null){
            JOptionPane.showMessageDialog(this, "You cannot make a suggestion when you're not in a room", "Make Suggestion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Radio Grid
        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.gridx = 0;
        panelConstraints.gridy = GridBagConstraints.RELATIVE;
        panelConstraints.anchor = GridBagConstraints.WEST;

        //Panel Grid
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 35;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JPanel layout = new JPanel();
        layout.setLayout(new GridBagLayout());

        //Player layout
        List<JRadioButton> playerButtons = new ArrayList<>();
        JLabel playerSuggestLabel = new JLabel( " Which Player?");
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridBagLayout());
        ButtonGroup playerGroup = new ButtonGroup();
        playerPanel.add(playerSuggestLabel, panelConstraints);
        for (int i = 0; i < playerOptions.size(); i++) {
            JRadioButton button = new JRadioButton(playerOptions.get(i));
            playerPanel.add(button, panelConstraints);
            playerGroup.add(button);
            playerButtons.add(button);
        }
        layout.add(playerPanel, gbc);

        //Weapon Layout
        gbc.gridy = 1;
        List<JRadioButton> weaponButtons = new ArrayList<>();
        JLabel weaponSuggestLabel = new JLabel(" Which Weapon?");
        JPanel weaponPanel = new JPanel();
        weaponPanel.setLayout(new GridBagLayout());
        ButtonGroup weaponGroup = new ButtonGroup();
        weaponPanel.add(weaponSuggestLabel, panelConstraints);
        for (int i = 0; i < weaponOptions.size(); i++) {
            JRadioButton button = new JRadioButton(weaponOptions.get(i));
            weaponPanel.add(button, panelConstraints);
            weaponGroup.add(button);
            weaponButtons.add(button);
        }
        layout.add(weaponPanel, gbc);

        //Room Layout (Already Selected)
        gbc.gridy = 2;
        JLabel roomSuggestLabel = new JLabel(" Which room?");
        JPanel roomPanel = new JPanel();
        roomPanel.setLayout(new GridBagLayout());
        ButtonGroup roomGroup = new ButtonGroup();
        roomPanel.add(roomSuggestLabel, panelConstraints);
        for (int i = 0; i < roomOptions.size(); i++) {
            JRadioButton button = new JRadioButton(roomOptions.get(i));
            roomPanel.add(button, panelConstraints);
            roomGroup.add(button);
            if (roomOptions.get(i) != current.getRoom().getName()) {
                button.setEnabled(false);
            } else button.setSelected(true);
        }
        layout.add(roomPanel, gbc);

        suggestionPane.add(layout, BorderLayout.WEST);

        JButton exit = new JButton("Close");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                suggestionPane.dispose();
            }
        });

        JButton submit = new JButton("Make Suggestion");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < playerButtons.size(); i++) {
                    if (playerButtons.get(i).isSelected()) {
                        suggestion.add(playerOptions.get(i));
                        for (Player p: boardPanel.getPlayers()){       //send player to room being suggested
                            if (p.getCharacterName().equals(playerOptions.get(i))){
                                current.getRoom().playerEnter(p);
                                p.setRoom(current.getRoom());
                                boardPanel.repaint();
                            }
                        }
                    }
                }
                for (int i = 0; i < weaponButtons.size(); i++) {
                    if (weaponButtons.get(i).isSelected()) {
                        suggestion.add(weaponOptions.get(i));
                    }
                }
                suggestion.add(current.getRoom().getName());
                
                current.setCurrentSuggestion(suggestion);
                doRefuteSuggestion(suggestion);

                suggestionPane.dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.add(submit);
        panel.add(exit);
        suggestionPane.add(panel, BorderLayout.SOUTH);
        suggestionPane.setSize(300, 750);
        suggestionPane.setLocationRelativeTo(null);
        suggestionPane.setVisible(true);
    }

    private void accusationPane() {
        accusationPane = new JDialog(this, "Make an Accusation");
        if (cluedo == null) {
            JOptionPane.showMessageDialog(this, "Go to options and start a new game.", "Start New Game", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Radio Grid
        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.gridx = 0;
        panelConstraints.gridy = GridBagConstraints.RELATIVE;
        panelConstraints.anchor = GridBagConstraints.WEST;

        //Panel Grid
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 35;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JPanel layout = new JPanel();
        layout.setLayout(new GridBagLayout());

        //Player layout
        List<JRadioButton> playerButtons = new ArrayList<>();
        JLabel playerSuggestLabel = new JLabel( " Which Player?");
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridBagLayout());
        ButtonGroup playerGroup = new ButtonGroup();
        playerPanel.add(playerSuggestLabel, panelConstraints);
        for (int i = 0; i < playerOptions.size(); i++) {
            JRadioButton button = new JRadioButton(playerOptions.get(i));
            playerButtons.add(button);
            playerPanel.add(button, panelConstraints);
            playerGroup.add(button);
        }
        layout.add(playerPanel, gbc);

        //Weapon Layout
        gbc.gridy = 1;
        List<JRadioButton> weaponButtons = new ArrayList<>();
        JLabel weaponSuggestLabel = new JLabel(" Which Weapon?");
        JPanel weaponPanel = new JPanel();
        weaponPanel.setLayout(new GridBagLayout());
        ButtonGroup weaponGroup = new ButtonGroup();
        weaponPanel.add(weaponSuggestLabel, panelConstraints);
        for (int i = 0; i < weaponOptions.size(); i++) {
            JRadioButton button = new JRadioButton(weaponOptions.get(i));
            weaponButtons.add(button);
            weaponPanel.add(button, panelConstraints);
            weaponGroup.add(button);
        }
        layout.add(weaponPanel, gbc);

        //Room Layout
        gbc.gridy = 2;
        List<JRadioButton> roomButtons = new ArrayList<>();
        JLabel roomSuggestLabel = new JLabel(" Which room?");
        JPanel roomPanel = new JPanel();
        roomPanel.setLayout(new GridBagLayout());
        ButtonGroup roomGroup = new ButtonGroup();
        roomPanel.add(roomSuggestLabel, panelConstraints);
        for (int i = 0; i < roomOptions.size(); i++) {
            JRadioButton button = new JRadioButton(roomOptions.get(i));
            roomButtons.add(button);
            roomPanel.add(button, panelConstraints);
            roomGroup.add(button);
        }
        layout.add(roomPanel, gbc);

        accusationPane.add(layout, BorderLayout.WEST);

        JButton exit = new JButton("Close");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accusationPane.dispose();
            }
        });

        List<String> accusation = new ArrayList<>();

        JButton submit = new JButton("Make Accusation");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < playerButtons.size(); i++) {
                    if (playerButtons.get(i).isSelected()) {
                        accusation.add(playerOptions.get(i));
                    }
                }
                for (int i = 0; i < roomButtons.size(); i++) {
                    if (roomButtons.get(i).isSelected()) {
                        accusation.add(roomOptions.get(i));
                    }
                }
                for (int i = 0; i < weaponButtons.size(); i++) {
                    if (weaponButtons.get(i).isSelected()) {
                        accusation.add(weaponOptions.get(i));
                    }
                }

                if (cluedo.accuse(accusation)){
                    JOptionPane.showMessageDialog(accusationPane, current.getPlayerName() + " made the correct accusation and won the game!", "Game Won", JOptionPane.INFORMATION_MESSAGE);
                    current = null;
                    cluedo = null;
                } else {
                    JOptionPane.showMessageDialog(accusationPane, current.getPlayerName() + " made the wrong accusation and has been removed from the game.",
                            "Wrong Accusation", JOptionPane.INFORMATION_MESSAGE);
                    cluedo.getPlayers().remove(current);
                    if (cluedo.getPlayers().isEmpty()){
                        JOptionPane.showMessageDialog(accusationPane, "Every player has been eliminated. Game over!",
                                "Game Over", JOptionPane.INFORMATION_MESSAGE);
                        current = null;
                        cluedo = null;
                    }
                    setNextMove();
                }

                accusationPane.dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.add(submit);
        panel.add(exit);

        accusationPane.add(panel, BorderLayout.SOUTH);
        accusationPane.setSize(300, 750);
        accusationPane.setLocationRelativeTo(null);
        accusationPane.setVisible(true);
    }

    private void doRefuteSuggestion(List<String> suggestion) {
        for (Player p : current.getNextPlayers()) {
            if (!p.refutableCards(suggestion).isEmpty()) {
                refute(p, suggestion);
                setNextMove();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "No players could refute your suggestion", "Suggestion", JOptionPane.INFORMATION_MESSAGE);
        setNextMove();
    }

    private void refute(Player p, List<String> suggestion) {
        refutePane = new JDialog(this, "Refute Suggestion");
        //Radio Grid
        GridBagConstraints radioConstraints = new GridBagConstraints();
        radioConstraints.gridx = 0;
        radioConstraints.gridy = GridBagConstraints.RELATIVE;
        radioConstraints.anchor = GridBagConstraints.WEST;

        //Panel Grid
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JPanel layout = new JPanel();
        layout.setLayout(new GridBagLayout());

        //Refutable Cards
        List<Card> refutables = p.refutableCards(suggestion);

        //Refution Radio Buttons
        List<JRadioButton> buttons = new ArrayList<>();
        JLabel makeRefuteLabel = new JLabel("Pick a card to refute " + p.getPlayerName());
        JPanel refutionPane = new JPanel();
        refutionPane.setLayout(new GridBagLayout());
        ButtonGroup buttonGroup = new ButtonGroup();
        refutionPane.add(makeRefuteLabel);
        for (Card c : refutables) {
            JRadioButton button = new JRadioButton(c.getName());
            refutionPane.add(button, radioConstraints);
            buttonGroup.add(button);
            buttons.add(button);
        }
        layout.add(refutionPane, gbc);

        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int j = 0; j < buttons.size(); j++) {
                    JRadioButton b = buttons.get(j);
                    if (b.isSelected()) {
                        current.getKnownEvidence().add(refutables.get(j).getName());
                        JOptionPane.showMessageDialog(refutePane, p.getPlayerName() + " refuted " + current.getCharacterName() + "'s suggestion with their " +
                                refutables.get(j).getName() + " card.", "Refute", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                refutePane.dispose();
            }
        });

        refutePane.add(layout, BorderLayout.WEST);
        refutePane.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        refutePane.addWindowListener(new WindowAdapter() {    //confirms user does want to leave
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(refutePane, "You must pick a card to refute", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel panel = new JPanel();
        panel.add(submit);

        refutePane.add(panel, BorderLayout.SOUTH);
        refutePane.setSize(300, 750);
        refutePane.setLocationRelativeTo(null);
        refutePane.setVisible(true);
    }

    private void listSetup() {
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

    private void setNextMove(){
        if (cluedo == null) {
            JOptionPane.showMessageDialog(this, "Go to options and start a new game.", "Start New Game", JOptionPane.WARNING_MESSAGE);
            return;
        }
        current = cluedo.getMove();
        int dice1 = (int) (Math.random() * 5) + 1;
        int dice2 = (int) (Math.random() * 5) + 1;
        movesRemaining = dice1+dice2;

        textOutputArea.append("It is " + current.getPlayerName() + "'s (" + current.getCharacterName() + ") turn. You roll a " + movesRemaining + ".\n");
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });

    }
}
