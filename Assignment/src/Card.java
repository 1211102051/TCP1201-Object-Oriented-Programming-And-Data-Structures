import java.util.Collections;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.File;

public class Card implements Serializable {
    private String suit;
    private String rank;
    private ImageIcon image;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
        this.image = new ImageIcon("." + File.separator + "img" + File.separator + suit + rank + ".png");
    }

    public char getSuit() {
        return suit.charAt(0);
    }

    public char getRank() {
        return rank.charAt(0);
    }

    public ImageIcon getImage() {
        return image;
    }

    public int getValue() {
        int i = 0;
        if (this.rank.equals("2")) {
            i = 2;
            return i;
        }
        else if (this.rank.equals("3")) {
            i = 3;
            return i;
        }
        else if (this.rank.equals("4")) {
            i = 4;
            return i;
        }
        else if (this.rank.equals("5")) {
            i = 5;
            return i;
        }
        else if (this.rank.equals("6")) {
            i = 6;
            return i;
        }
        else if (this.rank.equals("7")) {
            i = 7;
            return i;
        }
        else if (this.rank.equals("8")) {
            i = 8;
            return i;
        }
        else if (this.rank.equals("9")) {
            i = 9;
            return i;
        }
        else if (this.rank.equals("X")) {
            i = 10;
            return i;
        }
        else if (this.rank.equals("J")) {
            i = 11;
            return i;
        }
        else if (this.rank.equals("Q")) {
            i = 12;
            return i;
        }
        else if (this.rank.equals("K")) {
            i = 13;
            return i;
        }
        else if (this.rank.equals("A")) {
            i = 14;
            return i;
        }
        return 0;
    }

    @Override
    public String toString() {
        return suit + rank;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Card)) return false;
        Card card = (Card) o;
        if (this.suit.charAt(0) == card.getSuit() && this.rank.charAt(0) == card.getRank()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return suit.hashCode() + rank.hashCode();
    }
}

class Deck implements Serializable {
    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>(52);
        String[] suit = { "c", "d", "h", "s" };
        String[] rank = { "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K", "A" };
        for (String s : suit) {
            for (String r : rank) {
                deck.add(new Card(s, r));
            }
        }
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card peek() {
        if (deck.isEmpty()) {
            return null;
        }
        return deck.get(0);
    }
    
    public Card pop() {
        if (deck.isEmpty()) {
            return null;
        }
        return deck.remove(0);
    }

    public void push(Card card) {
        deck.add(card);
    }
}
