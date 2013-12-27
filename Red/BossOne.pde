class BossOne extends Rusher { //Rusher contains the calculate function, which we may need
  float destination;
  int armX, armY; //dimensions of the arms
  int shotCounter, shotCounter2, lastBulletFire2, bulletDelay2;
  BossOne(float tempx, float tempy, float tempvelx, float tempvely, float stopdest) {
    super(tempx, tempy, tempvelx, tempvely);
    isBoss = true;
    destination = stopdest;
    xDim = 200;
    yDim = 150;
    lives = 80;
    pointValue = 500;
    armX = 40;
    armY = 100;
    shotCounter = 0;
    lastBulletFire2 = 0;
    bulletDelay2 = 20;
  }
 
  void display() {
    if (onScreen()) {
      fill(255, 0, 0);
      rectMode(CORNER);
      rect(x, y, xDim, yDim);
      fill(75, 0, 130);
      rect(x, y+yDim-armY, armX, armY);
      rect(x+xDim-armX, y+yDim-armY, armX, armY);
    }
  }
 
  void update(ArrayList<Bullet> bullets, Player player) {
    if (y < destination) {
      //makes the boss invincible until it moves to the location it should be in
      lives = 100;
      y += vely;
    }
    if (onScreen() && y >= destination) {
      if (lastBulletFire <=0) {
        if (lives >= 60) {
          bulletDelay = 20;
          waveMotionCannon(bullets, x+armX/2, y+yDim, true);
        }
        if (lives < 60 && lives > 20) {
          bulletDelay = 90;
          seekerShot(bullets, x+armX/2, y+yDim);
        }
        if (lives <= 20) {
          bulletDelay = 20;
          waveMotionCannon(bullets, x+armX/2, y+yDim, true);
        }
        lastBulletFire = bulletDelay;
      }
      if (lastBulletFire2 <=0) {
        if (lives < 60 && lives > 20) {
          bulletDelay2 = 40;
          calculate(player, x+xDim-armX/2, y+yDim);
          scatterShot(bullets, x+xDim-armX/2, y+yDim);
        }
        if (lives <= 20) {
          bulletDelay2 = 20;
          waveMotionCannon(bullets, x+xDim-armX/2, y+yDim, false);
        }
        lastBulletFire2 = bulletDelay2;
      }
      lastBulletFire --;
      lastBulletFire2 --;
    }
  }
 
  void waveMotionCannon(ArrayList<Bullet> bullets, float xOrigin, float yOrigin, boolean clockWise) {
    //creates large radial wave of bullets
    bulletAmount = 36;
    shotCounter+=2;
    for (int bullet = 0; bullet < bulletAmount; bullet++) {
        bullets.add(new Bullet(xOrigin, yOrigin, 3, bullet*(360/bulletAmount)+ shotCounter));
    }
  }
 
  void scatterShot(ArrayList<Bullet> bullets, float xOrigin, float yOrigin) {
    //creates tripleshot pattern aimed at player
    int spread = 10;
    bulletAmount = 5;
    for (int bullet = 0; bullet < bulletAmount; bullet++) {
      bullets.add(new Bullet(xOrigin, yOrigin, 5, direction-floor(bulletAmount/2)*spread+spread*bullet));
    }
  }
 
  void seekerShot(ArrayList<Bullet> bullets, float xOrigin, float yOrigin) {
    //fires seekerbullets
    bulletAmount = 5;
    for (int bullet = 0; bullet < bulletAmount; bullet++) {
      bullets.add(new SeekerBullet(xOrigin, yOrigin, 4, int(random(14, 22))*10, int(random(1, 5))*10, .2));
    }
  }
}
