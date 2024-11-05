import java.util.*;

class Card {
    private String value;

    public Card(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String removeSuit() {
        return value.substring(0, value.length() - 1);
    }

    public int getValueIndex() {
        String[] level = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String nosuit = removeSuit();
        for (int i = 0; i < level.length; i++) {
            if (nosuit.equals(level[i])) {
                return i;
            }
        }
        return -1;
    }
}

class Player {
    private Queue<Card> deck;

    public Player() {
        this.deck = new LinkedList<>();
    }

    public void addToDeck(Card card) {
        deck.add(card);
    }

    public Queue<Card> getDeck() {
        return deck;
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public Card removeCard() {
        return deck.remove();
    }
}

public class Solution {
    public static void main(String[] args) {
        // Initializes the two players and what won from the war battles
        int[] rounds = new int[1];
        rounds[0] = 0;
        Player player1 = new Player();
        Player player2 = new Player();
        Queue<Card> wardeck1 = new LinkedList<>();
        Queue<Card> wardeck2 = new LinkedList<>();
        Scanner scan = new Scanner(System.in);

        // Gets the size of the first deck and then adds all the cards to the deck
        int deck1size = scan.nextInt();
        scan.nextLine();
        for (int i = 0; i < deck1size; i++) {
            player1.addToDeck(new Card(scan.nextLine()));
        }

        // Gets the size of the second deck and then adds all the cards to the deck
        int deck2size = scan.nextInt();
        scan.nextLine();
        for (int i = 0; i < deck2size; i++) {
            player2.addToDeck(new Card(scan.nextLine()));
        }

        while (true) {
            // If the first deck is empty says the second player won
            if (player1.isEmpty()) {
                System.out.println("2 " + rounds[0]);
                break;
            }
            // If the first deck is empty says the first player won
            else if (player2.isEmpty()) {
                System.out.println("1 " + rounds[0]);
                break;
            } else {
                // if no one won then runs the function to compare the next two cards and start another round
                compareCard(player1.removeCard(), player2.removeCard(), player1.getDeck(), player2.getDeck(), wardeck1, wardeck2, rounds);
            }
        }
    }

    // Compares the cards and puts them into the correct deck after a win
    public static void compareCard(Card card, Card card2, Queue<Card> deck1, Queue<Card> deck2, Queue<Card> wardeck1, Queue<Card> wardeck2, int[] rounds) {
        int cardVal = card.getValueIndex();
        int cardVal2 = card2.getValueIndex();

        // sees which of the cards are better this if is if the first card is bigger than the second one
        if (cardVal > cardVal2) {
            // adds the cards in order by first emptying the wardeck in case a war happened then the player one card then the other wardeck then the last card then increases the round by 1
            while (!wardeck1.isEmpty()) {
                deck1.add(wardeck1.remove());
            }
            deck1.add(card);

            while (!wardeck2.isEmpty()) {
                deck1.add(wardeck2.remove());
            }
            deck1.add(card2);
            rounds[0] = rounds[0] + 1;
        }
        // sees which of the cards are better this if is if the second card is bigger than the first one
        else if (cardVal2 > cardVal) {
            // adds the cards in order by first emptying the wardeck in case a war happened then the player one card then the other wardeck then the last card then increases the round by 1
            while (!wardeck1.isEmpty()) {
                deck2.add(wardeck1.remove());
            }

            deck2.add(card);
            rounds[0] = rounds[0] + 1;

            while (!wardeck2.isEmpty()) {
                deck2.add(wardeck2.remove());
            }
            deck2.add(card2);
        }
        // it knows the cards are the same value so then it adds the four cards in order to the wardeck queue for both wardecks then calls the main function again to compare the card
        else {
            try {
                wardeck1.add(card);
                for (int i = 0; i < 3; i++) {
                    wardeck1.add(deck1.remove());
                }
                wardeck2.add(card2);
                for (int i = 0; i < 3; i++) {
                    wardeck2.add(deck2.remove());
                }
                compareCard(deck1.remove(), deck2.remove(), deck1, deck2, wardeck1, wardeck2, rounds);
            }
            // If either of the decks are empty an error will occur and then it's a tie
            catch (Exception e) {
                System.out.println("PAT");
                System.exit(0);
            }
        }
    }
}
