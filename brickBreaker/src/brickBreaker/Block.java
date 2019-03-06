package brickBreaker;

import java.awt.*;

public class Block {
    public Point position = new Point(0, 0);
    public int width = 70;
    public int height = 30;
    public Color mainColor = Color.blue.darker();

    //Calculate in which direction the ball should bounce off
    public Point bounceVector(Rectangle hitbox) {
        Point p = new Point(1, 1);
        Rectangle hb_t = new Rectangle(position.x, position.y, width, height/3);
        Rectangle hb_b = new Rectangle(position.x, position.y+height-height/3, width, height/3);
        Rectangle hb_l = new Rectangle(position.x, position.y, width/10, height);
        Rectangle hb_r = new Rectangle(position.x+width-width/10, position.y, width/10, height);
        if (hb_t.intersects(hitbox) || hb_b.intersects(hitbox)) p.y = -1;
        if (hb_r.intersects(hitbox) || hb_l.intersects(hitbox)) p.x = -1;
        return p;
    }

    // Make the block
    public void render(Graphics g) {
        g.setColor(mainColor);
        g.fillRect(position.x, position.y, width, height);

        for (int i = 0; i < height/4; i++) {
            // Make 3D version block
            g.setColor(mainColor.darker());
            g.drawLine(position.x+i, position.y+height-i, position.x+width-1, position.y+height-i); // bottom
            g.drawLine(position.x+width-1-i, position.y+i, position.x+width-1-i, position.y+height); // right
            g.setColor(mainColor.brighter());
            g.drawLine(position.x, position.y+i, position.x+width-1-i, position.y+i); // top
            g.drawLine(position.x+i, position.y+height-i, position.x+i, position.y); // left
        }
    }
}
