import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;

    // Rectangle object represents
    private Rectangle button;
    private Rectangle button2;

    public DrawPanel() {
        button = new Rectangle(145, 260, 160, 26);
        button2 = new Rectangle(140, 350, 160, 26);
        this.addMouseListener(this);
        hand = Card.buildHand();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 135;
        int y = 10;
        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
            // puts it in a 3 by 3 grid
            if (i % 3 == 0 && i != 0)
            {
                y = y + c.getImage().getWidth() + 10;
                x = 135;
            }

            if (c.getHighlight()) {
                // draw the border rectangle around the card
                g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
            }


            // establish the location of the rectangle "hitbox"
            c.setRectangleLocation(x, y);

            g.drawImage(c.getImage(), x, y, null);
            x = x + c.getImage().getWidth() + 10;


        }

        // drawing the bottom button
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("Play Again", 150, 280);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());

        g.drawString("Cards left:", 140, 340);

        g.drawString(" " + Card.cardsInDeck(), 280, 340);


        if (Card.eliminateNumbers() == false){
            g.drawString("No available moves! Game over", 100, 440);
        }
        if (!Card.haveCards()){
            g.drawString("you win", 100, 390);
        }
        g.drawString("Replace Cards", 140, 370 );
        g.drawRect((int)button2.getX(), (int)button2.getY(), (int)button2.getWidth(), (int)button2.getHeight());

    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        // left click
        if (e.getButton() == 1) {
            // if "clicked" is inside the button rectangle
            // aka ---> did you click the button?
            if (button.contains(clicked)) {
                hand = Card.buildHand();
            }
            if (button2.contains(clicked)){
                for (int i = 0; i < hand.size(); i++) {
                    for (int j = i+1; j < hand.size(); j++) {
                        if (hand.get(i).getHighlight() && hand.get(j).getHighlight()){
                            hand.set(i, Card.randomCard());
                            hand.set(j, Card.randomCard());
                        }
                    }
                }
            }

            // go through each card
            // check if any of them were clicked on
            // if it was clicked, flip the card
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
            }
        }

        // right click
        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    if (hand.get(i).getHighlight())
                    {
                         hand.set(i, Card.randomCard());
                    }
                    hand.get(i).flipHighlight();
                }
            }
        }

    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}