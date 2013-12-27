class Player extends MoveableObject {
  float velocity;
  boolean alive = true;
  int bulletDelay, lastBulletFire, lives, invincibility;
 
  Player(float tempx, float tempy, int tempxDim, int tempyDim, float tempvel) {
    x = tempx;
    y = tempy;
    xDim = tempxDim;
    yDim = tempyDim;
    velocity = tempvel;
    bulletDelay = 10;
    lastBulletFire = 0;
    lives = 3;
    invincibility = 0;
  }
  void display() {
    //displays the character if they are alive
    if (alive) {
      if (invincibility <= 0) {
        fill(255);
      }
      else if (invincibility > 0) {
        fill(100);
      }
      rectMode(CENTER);
      rect(x, y, xDim, yDim);
      ellipseMode(CENTER);
      fill(255, 0, 0);
      ellipse(x, y, 5, 5);
    }
  }
  void update() {
    if (lives == 0) {
      alive = false;
    }
    if (x > width) {
      x = -xDim;
    }
    if (x+xDim < 0) {
      x = width;
    }
    if (y + yDim/2 + velocity > height) {
      y = height - yDim/2;
    }
    lastBulletFire --;
    invincibility --;
  }
  boolean hitDetect(MoveableObject object) {
    //detects whether or not the center point of the player is hitting an object
    if (x > object.x && x < object.x + object.xDim &&
      y > object.y && y < object.y + object.yDim) {
      return true;
    }
    else {
      return false;
    }
  }
  void generateBullet(ArrayList<Bullet> bullets) {
    //creates a bullet
    if (lastBulletFire <=0) {
      bullets.add(new Bullet(x-5, y+10, 10, 0));
      lastBulletFire = bulletDelay;
    }
  }
}
