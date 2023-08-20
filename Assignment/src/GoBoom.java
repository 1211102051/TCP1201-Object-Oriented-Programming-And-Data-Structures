import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class GoBoom {
    public static void pause() {
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }

    public static void main(String[] args) {
        int playerTurn = 0;
        Player[] players = new Player[4];
        String[] suit = { "c", "d", "h", "s" };
        String[] rank = { "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K", "A" };

        ArrayList<String> deck = new ArrayList<>();
        ArrayList<String> center = new ArrayList<>();
        for (String s : suit) {
            for (String r : rank) {
                deck.add(s + r);
            }
        }

        Collections.shuffle(deck);

        String leadCard = deck.get(0);
        center.add(leadCard);
        deck.remove(0);

        // Determines the first player
        if (leadCard.charAt(1) == 'A' || leadCard.charAt(1) == '5' || leadCard.charAt(1) == '9'
                || leadCard.charAt(1) == 'K') {
            playerTurn = 1;
        } else if (leadCard.charAt(1) == '2' || leadCard.charAt(1) == '6' || leadCard.charAt(1) == 'X') {
            playerTurn = 2;
        } else if (leadCard.charAt(1) == '3' || leadCard.charAt(1) == '7' || leadCard.charAt(1) == 'J') {
            playerTurn = 3;
        } else if (leadCard.charAt(1) == '4' || leadCard.charAt(1) == '8' || leadCard.charAt(1) == 'Q') {
            playerTurn = 4;
        }

        // Distribute cards to players
        for (int i = 0; i < 4; i++) {
            players[i] = new Player();
            for (int j = 0; j < 7; j++) {
                players[i].setCard(deck.get(0));
                deck.remove(0);
            }
        }

        // Gameplay starts here
        int round = 1;
        do {
            int leadPlayer = playerTurn;
            int winner = 0;
            for (int i = 0; i < 4; i++) {
                boolean loop = true;
                do {
                    Scanner scan = new Scanner(System.in);
                    System.out.println("Trick #" + round);
                    for (int j = 0; j < 4; j++) {
                        System.out.println("Player" + (j + 1) + ": " + players[j].getCard());
                    }
                    System.out.println("Center: " + center);
                    System.out.println("Deck: " + deck);
                    System.out.println("Turn: Player" + playerTurn);
                    System.out.print("> ");
                    String input = scan.nextLine();

                    if (center.isEmpty()) leadCard = input; else leadCard = center.get(0);
                    if (players[playerTurn - 1].isEmpty()) {
                        System.out.println("Player" + playerTurn + " has no cards to play. Skipping their turn.");
                        loop = false;
                    } else if (players[playerTurn - 1].contains(input)) {
                        if ((leadCard.charAt(0) == input.charAt(0)) || (leadCard.charAt(1) == input.charAt(1))) {
                            center.add(input);
                            players[playerTurn - 1].remove(input);
                            loop = false;
                        } else {
                            System.out.println("You must play a card with the same rank or suit!");
                            pause();
                        }
                    } else {
                        System.out.println("Invalid input.");
                        pause();
                    }
                } while (loop);
                playerTurn = (playerTurn % 4) + 1;
            }
            if (center.size() == 5)
                center.remove(0);
            int c1 = Arrays.toString(rank).indexOf(center.get(0).charAt(1)) + 1;
            int c2 = Arrays.toString(rank).indexOf(center.get(1).charAt(1)) + 1;
            int c3 = Arrays.toString(rank).indexOf(center.get(2).charAt(1)) + 1;
            int c4 = Arrays.toString(rank).indexOf(center.get(3).charAt(1)) + 1;
            if ((c1 > c2) && (c1 > c3) && (c1 > c4)) {
                winner = leadPlayer;
            } else if ((c2 > c3) && (c2 > c4)) {
                winner = (leadPlayer + 1) % 4;
            } else if (c3 > c4) {
                winner = (leadPlayer + 2) % 4;
            } else {
                winner = (leadPlayer + 3) % 4;
            }
            if (winner == 0)
                winner = 4;
            playerTurn = winner;
            System.out.println(" ");
            System.out.println("*** Player" + winner + " wins Trick #" + round + " ***");
            pause();
            center.clear();
            round++;
        } while (!players[0].isEmpty() && !players[1].isEmpty() && !players[2].isEmpty() && !players[3].isEmpty());
    }
}
