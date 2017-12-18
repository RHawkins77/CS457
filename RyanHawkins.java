import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Vector;

public class RyanHawkins extends ClobberBot {
    
    ClobberBotAction currAction, shotAction;
    int myOwnInt;
    static int numOfMe;
    int shotclock;
    private Vector<Bullet> bullets;
    // private Vector<Bots> bullets
    
    private class Bullet {
        double xplus;
        double yplus;
        Point2D pos;
        Point2D oldpos;
        int ID;
        
        boolean dangerous;
        boolean knowDirection;
        
        public boolean dangerous() {
            if (!dangerous)
                return dangerous;
            return (dangerous == checkMovingTowardsMe());
        }
        
        private boolean checkMovingTowardsMe() {
            return false;
        }
    }
    
    public ClobberBotAction takeTurn(WhatIKnow currState) {
        
        if(rand.nextInt(2) == 1){
            
            switch (rand.nextInt(8)) {
                case 1:
                    return new ClobberBotAction(1, ClobberBotAction.UP);
                case 2:
                    return new ClobberBotAction(1, ClobberBotAction.DOWN);
                case 3:
                    return new ClobberBotAction(1, ClobberBotAction.LEFT);
                case 4:
                    return new ClobberBotAction(1, ClobberBotAction.RIGHT);
                case 5:
                    return new ClobberBotAction(1, ClobberBotAction.UP | ClobberBotAction.LEFT);
                case 6:
                    return new ClobberBotAction(1, ClobberBotAction.UP | ClobberBotAction.RIGHT);
                case 7:
                    return new ClobberBotAction(1, ClobberBotAction.DOWN | ClobberBotAction.LEFT);
                case 8:
                    return new ClobberBotAction(1, ClobberBotAction.DOWN | ClobberBotAction.RIGHT);
                default:
                    return new ClobberBotAction(1, ClobberBotAction.DOWN | ClobberBotAction.RIGHT);
            }
        }else {
            switch (findPersonToShootAt(currState)) {
                case 0:
                    return new ClobberBotAction(2, ClobberBotAction.UP);
                case 1:
                    return new ClobberBotAction(2, ClobberBotAction.DOWN);
                case 2:
                    return new ClobberBotAction(2, ClobberBotAction.LEFT);
                case 3:
                    return new ClobberBotAction(2, ClobberBotAction.RIGHT);
                case 4:
                    return new ClobberBotAction(2, ClobberBotAction.UP | ClobberBotAction.LEFT);
                case 5:
                    return new ClobberBotAction(2, ClobberBotAction.UP | ClobberBotAction.RIGHT);
                case 6:
                    return new ClobberBotAction(2, ClobberBotAction.DOWN | ClobberBotAction.LEFT);
                case 7:
                    return new ClobberBotAction(2, ClobberBotAction.DOWN | ClobberBotAction.RIGHT);
                default:
                    return new ClobberBotAction(2, ClobberBotAction.DOWN | ClobberBotAction.RIGHT);
            }
        }
    }
    
    
    public int findPersonToShootAt(WhatIKnow currState) {
        Iterator<BotPoint2D> bit = currState.bots.iterator();
        double lowestID = 0;
        double lowestDistance = 10000000;
        BotPoint2D findBotDirection = new BotPoint2D(lowestDistance, lowestDistance, myOwnInt);
        while (bit.hasNext()) {
            BotPoint2D tempBit = (BotPoint2D) bit.next().clone();
            if (tempBit.distance(currState.me.x, currState.me.y) < lowestDistance) {
                //                System.out.println(tempBit.distance(currState.me.x, currState.me.y));
                //                System.out.println(lowestDistance);
                lowestID = tempBit.getID();
                lowestDistance = tempBit.distance(currState.me.x, currState.me.y);
                findBotDirection = (BotPoint2D) tempBit.clone();
            }
        }
        if (currState.me.x < findBotDirection.x && currState.me.y > findBotDirection.y) {
            //System.out.println(findBotDirection.getID());
            //System.out.println("Shooting to the top right");
            if(rand.nextInt(2) == 1) {
                return 0;
            }
            if(rand.nextInt(2) == 1) {
                return 3;
            }
            return 5;
        } else if (currState.me.x < findBotDirection.getX() && currState.me.y < findBotDirection.y) {
            //            System.out.println(findBotDirection.getID());
            //            System.out.println("Shooting to the top left");
            if(rand.nextInt(2) == 1) {
                return 0;
            }
            if(rand.nextInt(2) == 1) {
                return 2;
            }
            return 4;
        } else if (currState.me.x > findBotDirection.x && currState.me.y > findBotDirection.y) {
            //            System.out.println(findBotDirection.getID());
            //            System.out.println("Shooting to the bottom right");
            if(rand.nextInt(2) == 1) {
                return 1;
            }
            //            if(rand.nextInt(2) == 1) {
            //                return 3;
            //            }
            return 7;
        } else if (currState.me.x > findBotDirection.x && currState.me.y < findBotDirection.y) {
            //            System.out.println(findBotDirection.getID());
            //            System.out.println("Shooting to the bottom left");
            if(rand.nextInt(2) == 1) {
                return 1;
            }
            if(rand.nextInt(2) == 1) {
                return 2;
            }
            return 6;
        } else if (currState.me.x - findBotDirection.x == 0 && currState.me.y < findBotDirection.y) {
            //            System.out.println(findBotDirection.getID());
            //            System.out.println("Shooting Down");
            return 1;
        } else if (currState.me.x - findBotDirection.x == 0 && currState.me.y > findBotDirection.y) {
            //            System.out.println(findBotDirection.getID());
            //            System.out.println("Shooting Up");
            return 0;
        } else if (currState.me.x > findBotDirection.x && currState.me.y - findBotDirection.y == 0) {
            //            System.out.println(findBotDirection.getID());
            //            System.out.println("Shooting left");
            return 2;
        } else if (currState.me.x < findBotDirection.x && currState.me.y - findBotDirection.y == 0) {
            //            System.out.println(findBotDirection.getID());
            //            System.out.println("Shooting Right");
            return 3;
        }
        return 8;
    }
    
    Vector<Bullet> getDangerousBullets(WhatIKnow currstate) {
        int x, y;
        ImmutablePoint2D pt;
        Bullet bul;
        Vector<Bullet> badones = new Vector<Bullet>();
        
        x = 0;
        y = 0;
        
        while (x < currstate.bullets.size() && y < bullets.size()) {
            pt = (ImmutablePoint2D) (currstate.bullets.get(x));
            bul = bullets.get(y);
            assert (pt.getID() >= bul.ID); // We shouldn't see an id less than what we're already storing
            if (pt.getID() > bul.ID) {
                // Add new bullet
            } else {
                if (!bullets.get(y).knowDirection) {
                    // Figure out direction
                }
                if (!bul.dangerous) {
                    x++;
                    y++;
                } else {
                }
            }
        }
        return null;
    }
    
    public RyanHawkins(Clobber game) {
        super(game);
        myOwnInt = numOfMe++;
        mycolor = Color.CYAN;
    }
    
    public void myOwnMethod() {
        System.out.println("Unit " + myOwnInt + " reporting, sir.");
    }
    
    /** Here's an example of how to read teh WhatIKnow data structure */
    private void showWhatIKnow(WhatIKnow currState) {
        System.out.println("My id is " + ((ImmutablePoint2D) (currState.me)).getID() + ", I'm at position ("
                           + currState.me.getX() + ", " + currState.me.getY() + ")");
        System.out.print("Bullets: ");
        Iterator<BulletPoint2D> it = currState.bullets.iterator();
        while (it.hasNext()) {
            ImmutablePoint2D p = (ImmutablePoint2D) (it.next());
            System.out.print(p + ", ");
        }
        System.out.println();
        
        System.out.print("Bots: ");
        Iterator<BotPoint2D> bit = currState.bots.iterator();
        while (bit.hasNext()) {
            System.out.print(bit.next() + ", ");
        }
        System.out.println();
    }
    
    public String toString() {
        return "RyanHawkinsBot! by Ryan Hawkins";
    }
}

