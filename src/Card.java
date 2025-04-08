import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Card {
    private String suit;
    private String value;
    private String imageFileName;
    private String backImageFileName;
    private boolean show;
    private BufferedImage image;
    private Rectangle cardBox;
    private boolean highlight;
    public static ArrayList<Card> hand = new ArrayList<>();
    public static ArrayList<Card> deck = new ArrayList<>();
    public static ArrayList<Card> replacedCards = new ArrayList<>();


    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        this.imageFileName = "images/card_"+suit+"_"+value+".png";
        this.show = true;
        this.backImageFileName = "images/card_back.png";

        // which image should I be showing for the card?
        // the front image, or the back image
        this.image = readImage();
        this.cardBox = new Rectangle(-100, -100, image.getWidth(), image.getHeight());
        this.highlight = false;
    }

    public Rectangle getCardBox() {
        return cardBox;
    }

    public String getSuit() {
        return suit;
    }

    public void setRectangleLocation(int x, int y) {
        cardBox.setLocation(x, y);
    }

    public String getValue() {
        return value;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String toString() {
        return suit + " " + value;
    }

    public void flipCard() {
        show = !show;
        this.image = readImage();
    }

    public void flipHighlight() {
        highlight = !highlight;
    }

    public boolean getHighlight() {
        return highlight;
    }

    public BufferedImage getImage() {
        return image;
    }

    //a BufferedImage object us an object that represents qan image file to be drawn on the screen
    public BufferedImage readImage() {
        try {
            BufferedImage image;
            // if this is true, show the front
            // otherwise show the back
            if (show) {
                image = ImageIO.read(new File(imageFileName));
            }
            else {
                image = ImageIO.read(new File(backImageFileName));
            }
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public static ArrayList<Card> buildDeck() {
        deck = new ArrayList<>();
        ArrayList<Card> deck = new ArrayList<Card>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] values = {"02", "03", "04", "05", "06", "07", "08", "09", "10", "A", "J", "K", "Q"};
        for (String s : suits) {
            for (String v : values) {
                Card c = new Card(s, v);
                deck.add(c);
                Card.deck.add(c);
            }
        }
        return deck;
    }

    public static ArrayList<Card> buildHand() {
        ArrayList<Card> deck = Card.buildDeck();
        ArrayList<Card> hand = new ArrayList<Card>();
        for (int i = 0; i < 9; i++) {
            int r = (int)(Math.random()*deck.size());
            Card c = deck.remove(r);
            hand.add(c);
            Card.hand.add(c);
        }
        for (int i = 0; i < Card.hand.size(); i++) {
            Card.deck.remove(Card.hand.get(i));
        }
        return hand;
    }
    public static Card randomCard() {

            int r = (int)(Math.random()*deck.size());
            Card c = deck.remove(r);
            hand.add(c);
            replacedCards.add(c);
            return c;
    }

    public static boolean eliminateNumbers(){
        boolean lose = false;
        for (int i = 0; i < hand.size(); i++) {
            for (int j = i+1; j < hand.size(); j++) {
                Card card1 = hand.get(i);
                Card card2 = hand.get(j);
                   try {
                       if (Integer.parseInt(card1.getValue()) + Integer.parseInt(card2.getValue()) == 11) {
                           lose = false;
                       } else
                           lose = true;
                   } catch (Exception e){
                       lose = false;
                   }
            }
        }
        return lose;
    }

    public static boolean haveCards()
    {
        if (cardsInDeck()==0){
            return false;
        }
        return true;
    }
    public static int cardsInDeck(){
        return deck.size();
    }

}
