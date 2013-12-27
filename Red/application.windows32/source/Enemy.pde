class Enemy extends MoveableObject {
  float velx, vely;
  boolean alive;
  boolean isBoss;
  int pointValue, lives, bulletDelay, lastBulletFire, bulletAmount;
 
  Enemy(float tempx, float tempy, float tempvelx, float tempvely) {
    x = tempx;
    y = tempy;
    xDim = 40;
    yDim = 55;
    velx = tempvelx;
    vely = tempvely;
    alive = true;
    pointValue = 50;
    lives = 2;
    lastBulletFire = 0;
    bulletDelay = 50;
    bulletAmount = 3;
    isBoss = false;
  }
 
  void display() {
    if (onScreen()) {
      fill(255, 0, 0);
      rectMode(CORNER);
      rect(x, y, xDim, yDim);
    }
  }
 
  void update(ArrayList<Bullet> bullets, Player player) {
    //generates bullets and updates movement
    x += velx;
    y += vely;
    if (lastBulletFire <=0 && onScreen()) {
      generateBullet(bullets);
      lastBulletFire = bulletDelay;
    }
    lastBulletFire --;
  }
 
  boolean onScreen() {
    if (x+xDim > 0 && y+yDim > 0 && x < width && y < height) {
      return true;
    }
    else {
      return false;
    }
  }
 
  void generateBullet(ArrayList<Bullet> bullets) {
    //creates bullets in wideshot pattern
    for (int bullet = 0; bullet < bulletAmount; bullet++) {
      bullets.add(new Bullet(x-5+xDim*.5, y-10 + yDim, 5, 160 + bullet*20));
    }
  }
}
