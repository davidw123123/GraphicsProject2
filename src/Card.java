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

    public String getSymbolValue(){
        if (getValue().equals("J")){
            return "J";
        }
        if (getValue().equals("K")){
            return "K";
        }
        if (getValue().equals("Q")){
            return "Q";
        }
        return null;
    }
    public int getIntValue() {
        if (getValue().equals("A")){
            return 1;
        }
        if (getValue().equals("02")){
            return 2;
        }
        if (getValue().equals("03")){
            return 3;
        }
        if (getValue().equals("04")){
            return 4;
        }
        if (getValue().equals("05")){
            return 5;
        }
        if (getValue().equals("06")){
            return 6;
        }
        if (getValue().equals("07")){
            return 7;
        }
        if (getValue().equals("08")){
            return 8;
        }
        if (getValue().equals("09")){
            return 9;
        }
        if (getValue().equals("10")){
            return 10;
        }
        return 0;
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
//        boolean lose = false;
//        int value1 = 0;
//        while (!lose){
//            for (int i = 0; i < hand.size(); i++) {
//            value1 = 0;
//            Card card1 = hand.get(i);
//            if(card1.getValue().equals("A")) {
//                value1 = 1;
//            }else if (card1.getIntValue() > 0){
//                value1 = Integer.parseInt(card1.getValue());
//            }
//            for (int j = i+1; j < hand.size(); j++) {
//                Card card2 = hand.get(j);
//                int value2 = 0;
//                if (card2.getValue().equals("A")){
//                    value2 = 1;
//                } else if (card2.getIntValue() > 0){
//                    value2 = Integer.parseInt(card2.getValue());
//                }
//
//                if (value1 + value2 == 11){
//                    lose = false;
//                    System.out.println("value 2: "+ value2);
//                    System.out.println("value 1: "+value1);
//                } else if (j < hand.size()) {
//                    lose = true;
//                }
//            }
//            }
//        }
//        return lose;

        boolean elimnateNums = false;
        for (int i = 0; i < hand.size(); i++) {
            int value1 = hand.get(i).getIntValue();
            for (int j = i+1; j < hand.size(); j++) {
                int value2 = hand.get(j).getIntValue();
                if (value1 + value2 == 11){
                    elimnateNums = true;
                    return elimnateNums;

                } else elimnateNums = false;
            }
        }
        return elimnateNums;
    }

    public static boolean eliminateSymbols(){
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
