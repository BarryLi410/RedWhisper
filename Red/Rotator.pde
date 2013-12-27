class Rotator extends Enemy {
  int bulletCounter, bulletNumber;
 
  Rotator(float tempx, float tempy, float tempvelx, float tempvely) {
    super(tempx, tempy, tempvelx, tempvely);
    xDim = 50;
    yDim = 50;
    bulletAmount = 18;
    bulletNumber = 4;
    pointValue = 80;
    lives = 3;
    bulletDelay = 5;
    bulletCounter = 0;
  }
 
    void display() {
    if (onScreen()) {
      fill(255, 0, 0);
      ellipseMode(CORNER);
      ellipse(x, y, xDim, yDim);
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
 
    void generateBullet(ArrayList<Bullet> bullets) {
    //creates bullets in a spiral pattern
    for (int bullet = 0; bullet < bulletNumber; bullet++) {
      bullets.add(new Bullet(x+xDim/2, y+yDim/2, 5, (bulletCounter%bulletAmount)*(360/bulletAmount)+(bullet*90)));
    }
    bulletCounter++;
  }
}
