package brickBreaker;

import java.awt.*;

public class Player {
    private BrickBreaker game;
    int height = 10;
    int width = 100;
    Point position = new Point(0, 0);

    public Player(BrickBreaker game) {
        this.game = game;
        position = new Point(0, game.height/2-height-50); // - means bar position is to North
    }

    // Let the ball bounce off when it gets to the wall
    public Point bounceVector(Rectangle hitbox) {
        Point p = new Point(1, 1);
        Rectangle hb_t = new Rectangle(position.x-width/2, position.y-height/2, width, height/3);
        Rectangle hb_b = new Rectangle(position.x-width/2, position.y+height/2-height/3, width, height/3);
        Rectangle hb_l = new Rectangle(position.x-width/2, position.y-height/2, width/10, height);
        Rectangle hb_r = new Rectangle(position.x+width/2-width/10, position.y-height/2, width/10, height);
        if (hb_t.intersects(hitbox) || hb_b.intersects(hitbox)) p.y = -1;
        if (hb_r.intersects(hitbox) || hb_l.intersects(hitbox)) p.x = -1;
        return p;
    }

    // bar
    public void render(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(position.x-width/2, position.y-height/2, width, height);
    }
}
