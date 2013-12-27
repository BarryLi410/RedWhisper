class Rusher extends Enemy {
  int bulletAmount;
  int direction;
 
  Rusher(float tempx, float tempy, float tempvelx, float tempvely) {
    super(tempx, tempy, tempvelx, tempvely);
    xDim = 50;
    yDim = 30;
    bulletAmount = 3;
    bulletDelay = 80;
    pointValue = 50;
    lives = 2;
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
    calculate(player, x+xDim/2, y+yDim);
    if (lastBulletFire <=0 && onScreen()) {
      generateBullet(bullets);
      lastBulletFire = bulletDelay;
    }
    lastBulletFire --;
  }
 
  void calculate(Player player, float newx, float newy) {
    //allows the enemy to aim at the player
    float slope = (newy-player.y)/(newx-player.x);
    if (newy> player.y && newx > player.x) {
      direction = int(degrees(atan(slope)));
    }
    if (newy == player.y && newx > player.x) {
      direction = 0;
    }
    if (newy > player.y && newx < player.x) {
      direction = 180 + int(degrees(atan(slope)));
    }
    if (newy == player.y && newx < player.x) {
      direction = 180;
    }
    if (newy < player.y && newx > player.x) {
      direction = 360 + int(degrees(atan(slope)));
    }
    if (newy > player.y && newx == player.x) {
      direction = 90;
    }
    if (newy < player.y && newx < player.x) {
      direction = 180 + int(degrees(atan(slope)));
    }
    if (newy < player.y && newx == player.x) {
      direction = 270;
    }
    //unit circle starts facing right by default, this line corrects that to face up
    direction -= 90;
  }
 
    void generateBullet(ArrayList<Bullet> bullets) {
    //creates tripleshot pattern aimed at player
    for (int bullet = 0; bullet < bulletAmount; bullet++) {
      bullets.add(new Bullet(x+xDim/2, y+yDim/2, 5, direction-10+10*bullet));
    }
  }
}
