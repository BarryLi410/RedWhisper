//copyright Barry Li 2013
 
ArrayList<Bullet> bullets;
ArrayList<Bullet> playerBullets;
WaveHandler waves;
int bombCooldown, lastBomb, bombNumber;
boolean[] keyCheck = {
  false, false, false, false, false, false, false
};
Player player;
 
void setup() {
  size(600, 600);
  reset();
}
void draw() {
  background(0);
  if (keyCheck[6]) {
    reset();
  }
  if (player.alive == true && !waves.victory) {
    player.display();
    player.update();
    waves.spawnWave();
    waves.update(bullets, player);
    handleCollision();
    fill(255);
    text("Points:" + waves.points, 0, height);
    text("Lives:" + player.lives, 0, height-20);
    text("Bombs:" + bombNumber, 0, height-40);
    if (keyCheck[0]) {
      player.x -= player.velocity;
    }
    if (keyCheck[1]) {
      player.x += player.velocity;
    }
    if (keyCheck[2]) {
      player.y -= player.velocity;
    }
    if (keyCheck[3]) {
      player.y += player.velocity;
    }
    if (keyCheck[4]) {
      player.generateBullet(playerBullets);
    }
    if (keyCheck[5]) {
      if (lastBomb < 0 && bombNumber != 0) {
        bomb();
        bombNumber--;
        lastBomb = bombCooldown;
      }
    }
    lastBomb --;
  }
  else if (waves.victory) {
    fill(255);
    text("Congratulations.", width/2-80, height/2);
    text("You have defeated the shapes.", width/2-80, height/2 +20);
    text("Score:" + waves.points, width/2-80, height/2+40);
    text("More to come...", width/2-80, height-20);
  }
  else {
    fill(255);
    text("GAME OVER", width/2-20, height/2);
    text("Score:" + waves.points, width/2-2, height/2+20);
  }
}
void keyPressed() {
  if (key == 'a' || key == 'A' || (key==CODED && keyCode == LEFT)) {
    keyCheck[0] = true;
  }
  else if (key == 'd' || key == 'D' || (key==CODED && keyCode == RIGHT)) {
    keyCheck[1] = true;
  }
  else if (key == 'w' || key == 'W' || (key==CODED && keyCode == UP)) {
    keyCheck[2] = true;
  }
  else if (key == 's' || key == 'S' || (key==CODED && keyCode == DOWN)) {
    keyCheck[3] = true;
  }
  else if (key == ' ') {
    keyCheck[4] = true;
  }
  else if (key == 'b' || key == 'B') {
    keyCheck[5] = true;
  }
  else if (key == 'r' || key == 'R') {
    keyCheck[6] = true;
  }
}
 
void keyReleased() {
  if (key == 'a' || key == 'A' || (key==CODED && keyCode == LEFT)) {
    keyCheck[0] = false;
  }
  else if (key == 'd' || key == 'D' || (key==CODED && keyCode == RIGHT)) {
    keyCheck[1] = false;
  }
  else if (key == 'w' || key == 'W' || (key==CODED && keyCode == UP)) {
    keyCheck[2] = false;
  }
  else if (key == 's' || key == 'S' || (key==CODED && keyCode == DOWN)) {
    keyCheck[3] = false;
  }
  else if (key == ' ') {
    keyCheck[4] = false;
  }
  else if (key == 'b' || key == 'B') {
    keyCheck[5] = false;
  }
  else if (key == 'r' || key == 'R') {
    keyCheck[6] = false;
  }
}
 
void reset() {
  bullets = new ArrayList<Bullet>();
  playerBullets = new ArrayList<Bullet>();
  waves = new WaveHandler();
  bombCooldown = 30;
  lastBomb = 0;
  bombNumber = 3;
  player = new Player(300, 400, 30, 40, 5);
}
 
void bomb() {
  fill(255);
  rect(0, 0, width, height);
  bullets.clear();
  if (!waves.bossWave) {
    waves.enemies.clear();
  }
}
 
void handleCollision() {
  for (int i = bullets.size()-1; i >= 0; i--) {
    Bullet bullet = bullets.get(i);
    bullet.update(bullets, player);
    bullet.display();
    if (!bullet.alive || bullet.y > height) {
      //removes the bullet if it dies or leaves the screen
      bullets.remove(i);
    }
    if (player.hitDetect(bullet) && player.invincibility <= 0) {
      //kills the player and bullet if they collide
      player.lives -= 1;
      bullet.alive = false;
      player.invincibility = 180;
    }
  }
  for (int i = playerBullets.size()-1; i >= 0; i--) {
    Bullet bullet = playerBullets.get(i);
    bullet.update(bullets, player);
    bullet.display();
    for (int j = waves.enemies.size()-1; j >= 0; j--) {
      Enemy enemy = waves.enemies.get(j);
      if (bullet.hitDetect(enemy)) {
        //hurts enemies when your bullets collide
        bullet.alive = false;
        enemy.lives -= 1;
      }
    }
    if (!bullet.alive || bullet.y < 0) {
      //removes a bullet when it leaves the screen
      playerBullets.remove(i);
    }
  }
  for (int i = waves.enemies.size()-1; i >= 0 ; i--) {
    Enemy enemy = waves.enemies.get(i);
    if (player.hitDetect(enemy) && player.invincibility <= 0) {
      //hurts both player and enemy if they collide
      player.lives -= 1;
      if(enemy.isBoss == false) {
        enemy.alive = false;
      }
      player.invincibility = 180;
    }
  }
}
