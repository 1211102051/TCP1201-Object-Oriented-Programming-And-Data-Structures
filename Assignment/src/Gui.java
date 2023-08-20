import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.HashMap;
import java.util.ArrayList;

class Game implements ActionListener, MouseListener {
    static Deck deck;
    static Card leadCard;
    static int trick = 1; //trick
    static int playerTurn = 0;
    static ArrayList<Card> center;
    static HashMap<Integer, Player> players;

    JFrame frame = new JFrame();
    JLayeredPane bg = new JLayeredPane();
    JPanel score = new JPanel();
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    JPanel p4 = new JPanel();
    JPanel c = new JPanel(); //center
    JLabel ccard = new JLabel(); //center card
    JLabel p1s = new JLabel(); //score
    JLabel p2s = new JLabel(); //score
    JLabel p3s = new JLabel(); //score
    JLabel p4s = new JLabel(); //score
    JButton draw = new JButton("Draw");
    JMenuBar mb = new JMenuBar();
    JMenu fmenu = new JMenu("File");
    JMenuItem load = new JMenuItem("Load");
    JMenuItem save = new JMenuItem("Save");
    JMenuItem exit = new JMenuItem("Exit");


    Game() {
        init(); //initialize background data

        draw.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
        exit.addActionListener(this);

        p1.addMouseListener(this);
        p2.addMouseListener(this);
        p3.addMouseListener(this);
        p4.addMouseListener(this);

        //frame.addWindowListener(this);

        frame.setLayout(new BorderLayout());
        score.setLayout(new BoxLayout(score, BoxLayout.PAGE_AXIS));
        p1.setLayout(new FlowLayout());
        p2.setLayout(new FlowLayout());
        p3.setLayout(new FlowLayout());
        p4.setLayout(new FlowLayout());

        p1s.setText("Player 1: " + players.get(0).getPoints());
        p2s.setText("Player 2: " + players.get(1).getPoints());
        p3s.setText("Player 3: " + players.get(2).getPoints());
        p4s.setText("Player 4: " + players.get(3).getPoints());

        p1s.setFont(new Font("Sansserif", Font.PLAIN, 20));
        p2s.setFont(new Font("Sansserif", Font.PLAIN, 20));
        p3s.setFont(new Font("Sansserif", Font.PLAIN, 20));
        p4s.setFont(new Font("Sansserif", Font.PLAIN, 20));

        score.setBounds(12, 12, 125, 125);
        draw.setBounds(500, 630, 100, 50);
        p1.setBounds(150, 12, 1066, 120); //782
        p2.setBounds(150, 132, 1066, 120);
        p3.setBounds(150, 252, 1066, 120);
        p4.setBounds(150, 372, 1066, 120);
        c.setBounds(615, 500, 160, 200);

        score.setBackground(Color.white);
        bg.setBackground(new Color(0, 153, 0));
        p1.setBackground(new Color(0, 153, 0));
        p2.setBackground(new Color(0, 153, 0));
        p3.setBackground(new Color(0, 153, 0));
        p4.setBackground(new Color(0, 153, 0));
        c.setBackground(new Color(0, 153, 0));

        ccard.setIcon(new ImageIcon(leadCard.getImage().getImage().getScaledInstance(156, 195, Image.SCALE_DEFAULT)));

        bg.setOpaque(true);

        bg.setPreferredSize(new Dimension(1366, 768));

        ArrayList<Card> p1hand = new ArrayList<Card>(players.get(0).getHand());
        ArrayList<Card> p2hand = new ArrayList<Card>(players.get(1).getHand());
        ArrayList<Card> p3hand = new ArrayList<Card>(players.get(2).getHand());
        ArrayList<Card> p4hand = new ArrayList<Card>(players.get(3).getHand());
        JLabel[] p1card = new JLabel[players.get(0).size()];
        for (int i = 0; i < p1card.length; i++) {
            p1card[i] = new JLabel(new ImageIcon(p1hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
            p1card[i].setName(Character.toString(p1hand.get(i).getSuit()) + Character.toString(p1hand.get(i).getRank()));
            p1.add(p1card[i]);
        }

        JLabel[] p2card = new JLabel[players.get(1).size()];
        for (int i = 0; i < p2card.length; i++) {
            p2card[i] = new JLabel(new ImageIcon(p2hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
            p2card[i].setName(Character.toString(p2hand.get(i).getSuit()) + Character.toString(p2hand.get(i).getRank()));
            p2.add(p2card[i]);
        }

        JLabel[] p3card = new JLabel[players.get(2).size()];
        for (int i = 0; i < p3card.length; i++) {
            p3card[i] = new JLabel(new ImageIcon(p3hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
            p3card[i].setName(Character.toString(p3hand.get(i).getSuit()) + Character.toString(p3hand.get(i).getRank()));
            p3.add(p3card[i]);
        }

        JLabel[] p4card = new JLabel[players.get(3).size()];
        for (int i = 0; i < p4card.length; i++) {
            p4card[i] = new JLabel(new ImageIcon(p4hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
            p4card[i].setName(Character.toString(p4hand.get(i).getSuit()) + Character.toString(p4hand.get(i).getRank()));
            p4.add(p4card[i]);
        }

        score.add(p1s);
        score.add(p2s);
        score.add(p3s);
        score.add(p4s);

        c.add(ccard);

        bg.add(score);
        bg.add(draw);
        bg.add(p1);
        bg.add(p2);
        bg.add(p3);
        bg.add(p4);
        bg.add(c);

        fmenu.add(save);fmenu.add(load);fmenu.add(exit);
        mb.add(fmenu);

        frame.add(bg, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.green);
        frame.setSize(1366, 768);
        frame.setVisible(true);
        frame.setJMenuBar(mb);

    }

    static void init() {
        deck = new Deck();
        center = new ArrayList<Card>();
        players = new HashMap<>();
        
        deck.shuffle();

        for (int i = 0; i < 4; i++) {
            players.put(i, new Player());
            for (int j = 0; j < 7; j++) {
                players.get(i).add(deck.peek());
                deck.pop();
            }
        }

        leadCard = deck.peek();
        center.add(leadCard);
        deck.pop();

        // Determines the first player
        if (leadCard.getRank() == 'A' || leadCard.getRank() == '5' || leadCard.getRank() == '9'
                || leadCard.getRank() == 'K') {
            playerTurn = 1;
        } else if (leadCard.getRank() == '2' || leadCard.getRank() == '6' || leadCard.getRank() == 'X') {
            playerTurn = 2;
        } else if (leadCard.getRank() == '3' || leadCard.getRank() == '7' || leadCard.getRank() == 'J') {
            playerTurn = 3;
        } else if (leadCard.getRank() == '4' || leadCard.getRank() == '8' || leadCard.getRank() == 'Q') {
            playerTurn = 4;
        }

        System.out.println("Leadcard = " + leadCard.getSuit() + " " + leadCard.getRank()); //debug
        System.out.println("PlayerTurn = " + playerTurn); //debug
        printData();

    }

    public void resetRound() {
        trick = 1;
        deck = new Deck();
        center = new ArrayList<Card>();
        players.get(0).getHand().clear();
        players.get(1).getHand().clear();
        players.get(2).getHand().clear();
        players.get(3).getHand().clear();
        
        p1.removeAll();
        p2.removeAll();
        p3.removeAll();
        p4.removeAll();

        p1s.setText("Player 1: " + players.get(0).getPoints());
        p2s.setText("Player 2: " + players.get(1).getPoints());
        p3s.setText("Player 3: " + players.get(2).getPoints());
        p4s.setText("Player 4: " + players.get(3).getPoints());

        deck.shuffle();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                players.get(i).add(deck.peek());
                deck.pop();
            }
        }

        leadCard = deck.peek();
        center.add(leadCard);
        deck.pop();

        // Determines the first player
        if (leadCard.getRank() == 'A' || leadCard.getRank() == '5' || leadCard.getRank() == '9'
                || leadCard.getRank() == 'K') {
            playerTurn = 1;
        } else if (leadCard.getRank() == '2' || leadCard.getRank() == '6' || leadCard.getRank() == 'X') {
            playerTurn = 2;
        } else if (leadCard.getRank() == '3' || leadCard.getRank() == '7' || leadCard.getRank() == 'J') {
            playerTurn = 3;
        } else if (leadCard.getRank() == '4' || leadCard.getRank() == '8' || leadCard.getRank() == 'Q') {
            playerTurn = 4;
        }

        ArrayList<Card> p1hand = new ArrayList<Card>(players.get(0).getHand());
        ArrayList<Card> p2hand = new ArrayList<Card>(players.get(1).getHand());
        ArrayList<Card> p3hand = new ArrayList<Card>(players.get(2).getHand());
        ArrayList<Card> p4hand = new ArrayList<Card>(players.get(3).getHand());

        JLabel[] p1card = new JLabel[players.get(0).size()];
        for (int i = 0; i < p1card.length; i++) {
            p1card[i] = new JLabel(new ImageIcon(p1hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
            p1card[i].setName(Character.toString(p1hand.get(i).getSuit()) + Character.toString(p1hand.get(i).getRank()));
            p1.add(p1card[i]);
        }

        JLabel[] p2card = new JLabel[players.get(1).size()];
        for (int i = 0; i < p2card.length; i++) {
            p2card[i] = new JLabel(new ImageIcon(p2hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
            p2card[i].setName(Character.toString(p2hand.get(i).getSuit()) + Character.toString(p2hand.get(i).getRank()));
            p2.add(p2card[i]);
        }

        JLabel[] p3card = new JLabel[players.get(2).size()];
        for (int i = 0; i < p3card.length; i++) {
            p3card[i] = new JLabel(new ImageIcon(p3hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
            p3card[i].setName(Character.toString(p3hand.get(i).getSuit()) + Character.toString(p3hand.get(i).getRank()));
            p3.add(p3card[i]);
        }

        JLabel[] p4card = new JLabel[players.get(3).size()];
        for (int i = 0; i < p4card.length; i++) {
            p4card[i] = new JLabel(new ImageIcon(p4hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
            p4card[i].setName(Character.toString(p4hand.get(i).getSuit()) + Character.toString(p4hand.get(i).getRank()));
            p4.add(p4card[i]);
        }

        ccard.setIcon(new ImageIcon(leadCard.getImage().getImage().getScaledInstance(156, 195, Image.SCALE_DEFAULT)));
        System.out.println("Leadcard = " + leadCard.getSuit() + " " + leadCard.getRank()); //debug
        System.out.println("PlayerTurn = " + playerTurn); //debug
        printData();
    }

    public void update() {
        if (center.size() == 4 && trick != 1) {
            int c1 = center.get(0).getValue();
            int c2 = center.get(1).getValue();
            int c3 = center.get(2).getValue();
            int c4 = center.get(3).getValue();

            if ((c1 > c2) && (c1 > c3) && (c1 > c4)) {
                playerTurn = playerTurn;
            } else if ((c2 > c3) && (c2 > c4)) {
                playerTurn = (playerTurn + 1) % 4;
            } else if (c3 > c4) {
                playerTurn = (playerTurn + 2) % 4;
            } else {
                playerTurn = (playerTurn + 3) % 4;
            }

            if (playerTurn == 0) {
                playerTurn = 4;
            }
            ccard.setIcon(null);
            System.out.println("*** Player" + playerTurn + " wins Trick #" + trick + " ***");
            trick++;
            deck.push(center.get(0));
            deck.push(center.get(1));
            deck.push(center.get(2));
            deck.push(center.get(3));
            center.clear();
        }

        if (center.size() == 5) {
            deck.push(center.get(0));
            center.remove(0);
            int c1 = center.get(0).getValue();
            int c2 = center.get(1).getValue();
            int c3 = center.get(2).getValue();
            int c4 = center.get(3).getValue();

            if ((c1 > c2) && (c1 > c3) && (c1 > c4)) {
                playerTurn = playerTurn;
            } else if ((c2 > c3) && (c2 > c4)) {
                playerTurn = (playerTurn + 1) % 4;
            } else if (c3 > c4) {
                playerTurn = (playerTurn + 2) % 4;
            } else {
                playerTurn = (playerTurn + 3) % 4;
            }

            if (playerTurn == 0) {
                playerTurn = 4;
            }

            ccard.setIcon(null);
            System.out.println("*** Player" + playerTurn + " wins Trick #" + trick + " ***");
            trick++;
            deck.push(center.get(0));
            deck.push(center.get(1));
            deck.push(center.get(2));
            deck.push(center.get(3));
            center.clear();
        }

        if (players.get(0).getHand().isEmpty() || players.get(1).getHand().isEmpty() || players.get(2).getHand().isEmpty() || players.get(3).getHand().isEmpty()) {
            calculatescore();
            if (players.get(0).getPoints() >= 100 || players.get(1).getPoints() >= 100 || players.get(2).getPoints() >= 100 || players.get(3).getPoints() >= 100) {
                int p1score = players.get(0).getPoints();
                int p2score = players.get(1).getPoints();
                int p3score = players.get(2).getPoints();
                int p4score = players.get(3).getPoints();
                int winner = 0;
                if ((p1score < p2score) && (p1score < p3score) && (p1score < p4score)) {
                    winner = 1;
                }
                else if ((p2score < p1score) && (p2score < p3score) && (p2score < p4score)) {
                    winner = 2;
                }
                else if ((p3score < p1score) && (p3score < p2score) && (p3score < p4score)) {
                    winner = 3;
                }
                else if ((p4score < p1score) && (p4score < p2score) && (p4score < p3score)) {
                    winner = 4;
                }
                int d = JOptionPane.showConfirmDialog(frame, "Player " + winner + " has won. Do you want to restart the game");
                if (d == JOptionPane.YES_OPTION) {
                    ccard.setIcon(null);
                    p1.removeAll();
                    p2.removeAll();
                    p3.removeAll();
                    p4.removeAll();
                    init();
                    ArrayList<Card> p1hand = new ArrayList<Card>(players.get(0).getHand());
                    ArrayList<Card> p2hand = new ArrayList<Card>(players.get(1).getHand());
                    ArrayList<Card> p3hand = new ArrayList<Card>(players.get(2).getHand());
                    ArrayList<Card> p4hand = new ArrayList<Card>(players.get(3).getHand());
                    JLabel[] p1card = new JLabel[players.get(0).size()];
                    for (int i = 0; i < p1card.length; i++) {
                        p1card[i] = new JLabel(new ImageIcon(p1hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
                        p1card[i].setName(Character.toString(p1hand.get(i).getSuit()) + Character.toString(p1hand.get(i).getRank()));
                        p1.add(p1card[i]);
                    }

                    JLabel[] p2card = new JLabel[players.get(1).size()];
                    for (int i = 0; i < p2card.length; i++) {
                        p2card[i] = new JLabel(new ImageIcon(p2hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
                        p2card[i].setName(Character.toString(p2hand.get(i).getSuit()) + Character.toString(p2hand.get(i).getRank()));
                        p2.add(p2card[i]);
                    }

                    JLabel[] p3card = new JLabel[players.get(2).size()];
                    for (int i = 0; i < p3card.length; i++) {
                        p3card[i] = new JLabel(new ImageIcon(p3hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
                        p3card[i].setName(Character.toString(p3hand.get(i).getSuit()) + Character.toString(p3hand.get(i).getRank()));
                        p3.add(p3card[i]);
                    }

                    JLabel[] p4card = new JLabel[players.get(3).size()];
                    for (int i = 0; i < p4card.length; i++) {
                        p4card[i] = new JLabel(new ImageIcon(p4hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
                        p4card[i].setName(Character.toString(p4hand.get(i).getSuit()) + Character.toString(p4hand.get(i).getRank()));
                        p4.add(p4card[i]);
                    }
                    ccard.setIcon(new ImageIcon(leadCard.getImage().getImage().getScaledInstance(156, 195, Image.SCALE_DEFAULT)));
                    p1s.setText("Player 1: " + players.get(0).getPoints());
                    p2s.setText("Player 2: " + players.get(1).getPoints());
                    p3s.setText("Player 3: " + players.get(2).getPoints());
                    p4s.setText("Player 4: " + players.get(3).getPoints());
                    ccard.revalidate();
                    ccard.repaint();
                    frame.revalidate();
                    frame.repaint();
                    return;
                }
                else if (d == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
            resetRound();
        }

        frame.revalidate();
        frame.repaint();
        return;
    }

    public void calculatescore() {
        if (players.get(0).isEmpty()) {
            players.get(1).setPoints();
            players.get(2).setPoints();
            players.get(3).setPoints();
            return;
        }
        else if (players.get(1).isEmpty()) {
            players.get(0).setPoints();
            players.get(2).setPoints();
            players.get(3).setPoints();
            return;
        }
        else if (players.get(2).isEmpty()) {
            players.get(0).setPoints();
            players.get(1).setPoints();
            players.get(3).setPoints();
            return;
        }
        else if (players.get(3).isEmpty()) {
            players.get(0).setPoints();
            players.get(1).setPoints();
            players.get(2).setPoints();
            return;
        }
        p1s.setText("Player 1: " + players.get(0).getPoints());
        p2s.setText("Player 2: " + players.get(1).getPoints());
        p3s.setText("Player 3: " + players.get(2).getPoints());
        p4s.setText("Player 4: " + players.get(3).getPoints());
        frame.revalidate();
        frame.repaint();
    }

    public void draw() {
        if (deck.getDeck().isEmpty()) {
            System.out.println("There's no more card to draw.");
            System.out.println("Skipping turn...");
            if (playerTurn == 1 || playerTurn == 2 || playerTurn == 3) {
                playerTurn++;
            }
            else if (playerTurn == 4) {
                playerTurn = 1;
            }
            return;
        }
        Card drawnCard = deck.peek();
        deck.pop();
        players.get(playerTurn - 1).add(drawnCard);
        System.out.println("draw card " + drawnCard);
        JLabel label = new JLabel(new ImageIcon(drawnCard.getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
        label.setName(drawnCard.toString());
        if (playerTurn == 1) {
            p1.add(label);
        }
        else if (playerTurn == 2) {
            p2.add(label);
        }
        else if (playerTurn == 3) {
            p3.add(label);
        }
        else if (playerTurn == 4) {
            p4.add(label);
        }
        frame.revalidate();
        System.out.println(deck.getDeck());
        update();
        return;
    }

    public void save() {
        ArrayList<Object> save = new ArrayList<Object>();
        save.add(deck);
        save.add(leadCard); 
        save.add(trick); 
        save.add(playerTurn); 
        save.add(center);
        save.add(players);
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        int i = fc.showSaveDialog(null);
        if (i == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            FileOutputStream fOut = null;
            //FileWriter fw = null;
            if (file == null) {
                return;
            }
            if (!file.getName().toLowerCase().endsWith(".ser")) {
                file = new File(file.getParentFile(), file.getName() + ".ser");
            }
            try {
                fOut = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fOut);
                out.writeObject(save);
                out.close();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }

    public void load() {
        ArrayList<Object> data = new ArrayList<Object>();
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        int i = fc.showOpenDialog(null);
        if (i == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            String filepath = f.getPath();
            FileInputStream fIn = null;
            try {
                fIn = new FileInputStream(f);
                ObjectInputStream in = new ObjectInputStream(fIn);
                data = (ArrayList<Object>) in.readObject();
                in.close();
                fIn.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
        deck = (Deck) data.get(0);
        leadCard = (Card) data.get(1);
        trick = (int) data.get(2);
        playerTurn = (int) data.get(3);
        center = (ArrayList<Card>) data.get(4);
        players = (HashMap<Integer, Player>) data.get(5);
        loadData();
        return;
    }

    public void loadData() {
        
        p1.removeAll();
        p2.removeAll();
        p3.removeAll();
        p4.removeAll();

        p1s.setText("Player 1: " + players.get(0).getPoints());
        p2s.setText("Player 2: " + players.get(1).getPoints());
        p3s.setText("Player 3: " + players.get(2).getPoints());
        p4s.setText("Player 4: " + players.get(3).getPoints());

        ArrayList<Card> p1hand = new ArrayList<Card>(players.get(0).getHand());
        ArrayList<Card> p2hand = new ArrayList<Card>(players.get(1).getHand());
        ArrayList<Card> p3hand = new ArrayList<Card>(players.get(2).getHand());
        ArrayList<Card> p4hand = new ArrayList<Card>(players.get(3).getHand());

        JLabel[] p1card = new JLabel[players.get(0).size()];
        for (int i = 0; i < p1card.length; i++) {
            p1card[i] = new JLabel(new ImageIcon(p1hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
            p1card[i].setName(Character.toString(p1hand.get(i).getSuit()) + Character.toString(p1hand.get(i).getRank()));
            p1.add(p1card[i]);
        }

        JLabel[] p2card = new JLabel[players.get(1).size()];
        for (int i = 0; i < p2card.length; i++) {
            p2card[i] = new JLabel(new ImageIcon(p2hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
            p2card[i].setName(Character.toString(p2hand.get(i).getSuit()) + Character.toString(p2hand.get(i).getRank()));
            p2.add(p2card[i]);
        }

        JLabel[] p3card = new JLabel[players.get(2).size()];
        for (int i = 0; i < p3card.length; i++) {
            p3card[i] = new JLabel(new ImageIcon(p3hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
            p3card[i].setName(Character.toString(p3hand.get(i).getSuit()) + Character.toString(p3hand.get(i).getRank()));
            p3.add(p3card[i]);
        }

        JLabel[] p4card = new JLabel[players.get(3).size()];
        for (int i = 0; i < p4card.length; i++) {
            p4card[i] = new JLabel(new ImageIcon(p4hand.get(i).getImage().getImage().getScaledInstance(63, 100, Image.SCALE_DEFAULT)));
            p4card[i].setName(Character.toString(p4hand.get(i).getSuit()) + Character.toString(p4hand.get(i).getRank()));
            p4.add(p4card[i]);
        }

        if (center.size() == 0) {
            ccard.setIcon(null);
        } else {
            ccard.setIcon(new ImageIcon(center.get(center.size() - 1).getImage().getImage().getScaledInstance(156, 195, Image.SCALE_DEFAULT)));
        }

        frame.revalidate();
        frame.repaint();
        System.out.println("Leadcard = " + leadCard.getSuit() + " " + leadCard.getRank()); //debug
        System.out.println("PlayerTurn = " + playerTurn); //debug
        printData();
    }

    public static void printData() {
        System.out.println("Player 1: " + players.get(0).getHand());
        System.out.println("Player 2: " + players.get(1).getHand());
        System.out.println("Player 3: " + players.get(2).getHand());
        System.out.println("Player 4: " + players.get(3).getHand());
        System.out.println("Deck: " + deck.getDeck());
        System.out.println("Center: " + center);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == draw) {
            draw();
        }
        else if (e.getSource() == save) {
            save();
        }
        else if (e.getSource() == load) {
            load();
        }
        else if (e.getSource() == exit) {
            System.exit(0);
        }
    }

    public void mouseClicked(MouseEvent e) {
        String cardname = "";
        Card card;
        if (e.getSource() == p1) {
            if (playerTurn != 1) {
                System.out.println("not ur card");
                return;
            }
            Point p = e.getPoint();
            Component cmp = p1.getComponentAt(p);
            if (cmp instanceof JLabel) {
                JLabel label = (JLabel) cmp;
                cardname = label.getName();
                card = new Card(cardname.substring(0, 1), cardname.substring(1, 2));
                if (center.size() == 0 && playerTurn == 1) {
                    players.get(0).remove(card);
                    p1.remove(label);
                    ccard.setIcon(new ImageIcon(card.getImage().getImage().getScaledInstance(156, 195, Image.SCALE_DEFAULT)));
                    center.add(card);
                    playerTurn++;
                    frame.revalidate();
                    frame.repaint();
                    printData();
                    update();
                    return;
                }
                if (center.get(0).getSuit() != card.getSuit() && center.get(0).getRank() != card.getRank()) {
                    System.out.println("You must play a card with the same rank or suit!");
                    return;
                }
                if (players.get(0).remove(card)) {
                    p1.remove(label);
                    ccard.setIcon(new ImageIcon(card.getImage().getImage().getScaledInstance(156, 195, Image.SCALE_DEFAULT)));
                    center.add(card);
                    playerTurn++;
                }
            }
        }
        else if (e.getSource() == p2) {
            if (playerTurn != 2) {
                System.out.println("not ur card");
                return;
            }
            Point p = e.getPoint();
            Component cmp = p2.getComponentAt(p);
            if (cmp instanceof JLabel) {
                JLabel label = (JLabel) cmp;
                cardname = label.getName();
                card = new Card(cardname.substring(0, 1), cardname.substring(1, 2));
                if (center.size() == 0 && playerTurn == 2) {
                    players.get(1).remove(card);
                    p2.remove(label);
                    ccard.setIcon(new ImageIcon(card.getImage().getImage().getScaledInstance(156, 195, Image.SCALE_DEFAULT)));
                    center.add(card);
                    playerTurn++;
                    frame.revalidate();
                    frame.repaint();
                    printData();
                    update();
                    return;
                }
                if (center.get(0).getSuit() != card.getSuit() && center.get(0).getRank() != card.getRank()) {
                    System.out.println("You must play a card with the same rank or suit!");
                    return;
                }
                if (players.get(1).remove(card)) {
                    p2.remove(label);
                    ccard.setIcon(new ImageIcon(card.getImage().getImage().getScaledInstance(156, 195, Image.SCALE_DEFAULT)));
                    center.add(card);
                    playerTurn++;
                }
            }
        }
        else if (e.getSource() == p3) {
            if (playerTurn != 3) {
                System.out.println("not ur card");
                return;
            }
            Point p = e.getPoint();
            Component cmp = p3.getComponentAt(p);
            if (cmp instanceof JLabel) {
                JLabel label = (JLabel) cmp;
                cardname = label.getName();
                card = new Card(cardname.substring(0, 1), cardname.substring(1, 2));
                if (center.size() == 0 && playerTurn == 3) {
                    players.get(2).remove(card);
                    p3.remove(label);
                    ccard.setIcon(new ImageIcon(card.getImage().getImage().getScaledInstance(156, 195, Image.SCALE_DEFAULT)));
                    center.add(card);
                    playerTurn++;
                    frame.revalidate();
                    frame.repaint();
                    printData();
                    update();
                    return;
                }
                if (center.get(0).getSuit() != card.getSuit() && center.get(0).getRank() != card.getRank()) {
                    System.out.println("You must play a card with the same rank or suit!");
                    return;
                }
                if (players.get(2).remove(card)) {
                    p3.remove(label);
                    ccard.setIcon(new ImageIcon(card.getImage().getImage().getScaledInstance(156, 195, Image.SCALE_DEFAULT)));
                    center.add(card);
                    playerTurn++;
                }
            }
        }
        else if (e.getSource() == p4) {
            if (playerTurn != 4) {
                System.out.println("not ur card");
                return;
            }
            Point p = e.getPoint();
            Component cmp = p4.getComponentAt(p);
            if (cmp instanceof JLabel) {
                JLabel label = (JLabel) cmp;
                cardname = label.getName();
                card = new Card(cardname.substring(0, 1), cardname.substring(1, 2));
                if (center.size() == 0 && playerTurn == 4) {
                    players.get(3).remove(card);
                    p4.remove(label);
                    ccard.setIcon(new ImageIcon(card.getImage().getImage().getScaledInstance(156, 195, Image.SCALE_DEFAULT)));
                    center.add(card);
                    playerTurn = 1;
                    frame.revalidate();
                    frame.repaint();
                    printData();
                    update();
                    return;
                }
                if (center.get(0).getSuit() != card.getSuit() && center.get(0).getRank() != card.getRank()) {
                    System.out.println("You must play a card with the same rank or suit!");
                    return;
                }
                if (players.get(3).remove(card)) {
                    p4.remove(label);
                    ccard.setIcon(new ImageIcon(card.getImage().getImage().getScaledInstance(156, 195, Image.SCALE_DEFAULT)));
                    center.add(card);
                    playerTurn = 1;
                }
            }
        }

        frame.revalidate();
        frame.repaint();
        printData();
        update();
    }

    public void mouseEntered(MouseEvent e) {  
        //mouse entered  
    }

    public void mouseExited(MouseEvent e) {  
        //mouse exited  
    }

    public void mousePressed(MouseEvent e) {  
        //mouse pressed
    }

    public void mouseReleased(MouseEvent e) {  
        //mouse release
    }
}

class Menu implements ActionListener {

    JFrame frame = new JFrame("Go Boom");
    JLabel title = new JLabel("Go Boom");
    JButton play = new JButton("Play");
    JButton exit = new JButton("Exit");
    JPanel panel = new JPanel();

    Menu() {

        title.setBounds(70, 30, 500, 500);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        title.setVerticalAlignment(JLabel.TOP);

        play.setBounds(90, 170, 180, 100);
        play.addActionListener(this);

        exit.setBounds(90, 280, 180, 100);
        exit.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setLayout(null);
        panel.setBounds(180, 0, 360, 480);
        panel.add(title);
        panel.add(play);
        panel.add(exit);

        frame.add(panel, BorderLayout.CENTER);
        frame.setLayout(new BorderLayout());
        frame.setSize(720, 480);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play) {
            Game game = new Game();
        }
        else if (e.getSource() == exit) {
            System.exit(0);
        }
    }

}

public class Gui{
 
    public static void main(String[] args) {
        Menu main = new Menu();
    }
}