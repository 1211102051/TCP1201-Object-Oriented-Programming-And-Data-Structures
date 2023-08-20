import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;

public class Player implements Serializable {
    private HashSet<Card> hand;
    private int points;

    public Player() {
        this.hand = new HashSet<Card>();
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints() {
        Card card;
        Iterator<Card> itr = hand.iterator();
        while(itr.hasNext()) {
            card = itr.next();
            this.points += card.getValue();
        }
        return;
    }

    public HashSet<Card> getHand() {
        return hand;
    }

    public void add(Card x) {
        hand.add(x);
    }

    public boolean contains(Card x) {
        return hand.contains(x);
    }

    public int indexOf(Card x) {
        ArrayList<Card> handArray = new ArrayList<Card>(hand);

        return handArray.indexOf(x);
    }

    public boolean isEmpty() {
        return hand.isEmpty();
    }

    public boolean remove(Card x) {
        Card card;
        Iterator<Card> itr = hand.iterator();
        while(itr.hasNext()) {
            card = itr.next();
            if (card.equals(x)) {
                itr.remove();
                hand.remove(card);
                return true;
            }
        }
        return false;
    }

    public int size() {
        return hand.size();
    }
}