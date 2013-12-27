class Diamond extends Enemy {
  float accelx, accely;
  Diamond(float tempx, float tempy, float tempvelx, float tempvely, float tempaccelx, float tempaccely) {
    super(tempx, tempy, tempvelx, tempvely);
    accelx = tempaccelx;
    accely = tempaccely;
    xDim = 30;
    yDim = 30;
    bulletAmount = 4;
    pointValue = 30;
    lives = 2;
    bulletDelay = 100;
  }
 
  void display() {
    //a diamond shape
    if (onScreen()) {
      fill(255, 0, 0);
      quad(x, y+yDim/2, x+xDim/2, y, x+xDim, y+yDim/2, x+xDim/2, y+yDim);
    }
  }
 
  void update(ArrayList<Bullet> bullets, Player player) {
    //generates bullets and updates movement
    x += velx;
    y += vely;
    velx += accelx;
    vely += accely;
    if (lastBulletFire <=0 && onScreen()) {
      generateBullet(bullets);
      lastBulletFire = bulletDelay;
    }
    lastBulletFire --;
  }
   
  void generateBullet(ArrayList<Bullet> bullets) {
    //creates bullets in diamond pattern
    for (int bullet = 0; bullet < bulletAmount; bullet++) {
      bullets.add(new SlowBullet(x+xDim*.5, y + yDim*.5, 6, 2, bullet*90+45, .2));
    }
  }
}
