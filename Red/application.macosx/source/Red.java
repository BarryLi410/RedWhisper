import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Red extends PApplet {

//copyright Barry Li 2013
 
ArrayList<Bullet> bullets;
ArrayList<Bullet> playerBullets;
WaveHandler waves;
int bombCooldown, lastBomb, bombNumber;
boolean[] keyCheck = {
  false, false, false, false, false, false, false
};
Player player;
 
public void setup() {
  size(600, 600);
  reset();
}
public void draw() {
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
public void keyPressed() {
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
 
public void keyReleased() {
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
 
public void reset() {
  bullets = new ArrayList<Bullet>();
  playerBullets = new ArrayList<Bullet>();
  waves = new WaveHandler();
  bombCooldown = 30;
  lastBomb = 0;
  bombNumber = 3;
  player = new Player(300, 400, 30, 40, 5);
}
 
public void bomb() {
  fill(255);
  rect(0, 0, width, height);
  bullets.clear();
  if (!waves.bossWave) {
    waves.enemies.clear();
  }
}
 
public void handleCollision() {
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
 
  public void display() {
    if (onScreen()) {
      fill(255, 0, 0);
      rectMode(CORNER);
      rect(x, y, xDim, yDim);
      fill(75, 0, 130);
      rect(x, y+yDim-armY, armX, armY);
      rect(x+xDim-armX, y+yDim-armY, armX, armY);
    }
  }
 
  public void update(ArrayList<Bullet> bullets, Player player) {
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
 
  public void waveMotionCannon(ArrayList<Bullet> bullets, float xOrigin, float yOrigin, boolean clockWise) {
    //creates large radial wave of bullets
    bulletAmount = 36;
    shotCounter+=2;
    for (int bullet = 0; bullet < bulletAmount; bullet++) {
        bullets.add(new Bullet(xOrigin, yOrigin, 3, bullet*(360/bulletAmount)+ shotCounter));
    }
  }
 
  public void scatterShot(ArrayList<Bullet> bullets, float xOrigin, float yOrigin) {
    //creates tripleshot pattern aimed at player
    int spread = 10;
    bulletAmount = 5;
    for (int bullet = 0; bullet < bulletAmount; bullet++) {
      bullets.add(new Bullet(xOrigin, yOrigin, 5, direction-floor(bulletAmount/2)*spread+spread*bullet));
    }
  }
 
  public void seekerShot(ArrayList<Bullet> bullets, float xOrigin, float yOrigin) {
    //fires seekerbullets
    bulletAmount = 5;
    for (int bullet = 0; bullet < bulletAmount; bullet++) {
      bullets.add(new SeekerBullet(xOrigin, yOrigin, 4, PApplet.parseInt(random(14, 22))*10, PApplet.parseInt(random(1, 5))*10, .2f));
    }
  }
}
class Bullet extends MoveableObject {
  boolean alive;
  int direction;
  float velocity, xvel, yvel;
 
  Bullet(float tempx, float tempy, float tempvel, int tempdir) {
    alive = true;
    x = tempx;
    y = tempy;
    velocity = tempvel;
    direction = tempdir-90;
    xDim = 10;
    yDim = 10;
  }
  public void display() {
    if (onScreen()) {
      fill(255, 0, 0);
      ellipseMode(CORNER);
      ellipse(x, y, xDim, yDim);
    }
  }
  public void update(ArrayList<Bullet> bullets, Player player) {
    calculateVel(direction, velocity);
    x+=xvel;
    y+=yvel;
  }
  public void calculateVel(int direction, float velocity) {
    xvel = cos(radians(direction))*velocity;
    yvel = sin(radians(direction))*velocity;
  }
 
  public boolean onScreen() {
    if (x+xDim > 0 && y+yDim > 0 && x < width && y < height) {
      return true;
    }
    else {
      return false;
    }
  }
 
  public boolean hitDetect(MoveableObject object) {
    if (x+xDim > object.x && x < object.x + object.xDim && y+yDim > object.y && y < object.y + object.yDim) {
      return true;
    }
    else {
      return false;
    }
  }
}
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
 
  public void display() {
    //a diamond shape
    if (onScreen()) {
      fill(255, 0, 0);
      quad(x, y+yDim/2, x+xDim/2, y, x+xDim, y+yDim/2, x+xDim/2, y+yDim);
    }
  }
 
  public void update(ArrayList<Bullet> bullets, Player player) {
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
   
  public void generateBullet(ArrayList<Bullet> bullets) {
    //creates bullets in diamond pattern
    for (int bullet = 0; bullet < bulletAmount; bullet++) {
      bullets.add(new SlowBullet(x+xDim*.5f, y + yDim*.5f, 6, 2, bullet*90+45, .2f));
    }
  }
}
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
 
  public void display() {
    if (onScreen()) {
      fill(255, 0, 0);
      rectMode(CORNER);
      rect(x, y, xDim, yDim);
    }
  }
 
  public void update(ArrayList<Bullet> bullets, Player player) {
    //generates bullets and updates movement
    x += velx;
    y += vely;
    if (lastBulletFire <=0 && onScreen()) {
      generateBullet(bullets);
      lastBulletFire = bulletDelay;
    }
    lastBulletFire --;
  }
 
  public boolean onScreen() {
    if (x+xDim > 0 && y+yDim > 0 && x < width && y < height) {
      return true;
    }
    else {
      return false;
    }
  }
 
  public void generateBullet(ArrayList<Bullet> bullets) {
    //creates bullets in wideshot pattern
    for (int bullet = 0; bullet < bulletAmount; bullet++) {
      bullets.add(new Bullet(x-5+xDim*.5f, y-10 + yDim, 5, 160 + bullet*20));
    }
  }
}
abstract class MoveableObject {
  float x, y;
  int xDim, yDim;
   
  public abstract void display();
}
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
  public void display() {
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
  public void update() {
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
  public boolean hitDetect(MoveableObject object) {
    //detects whether or not the center point of the player is hitting an object
    if (x > object.x && x < object.x + object.xDim &&
      y > object.y && y < object.y + object.yDim) {
      return true;
    }
    else {
      return false;
    }
  }
  public void generateBullet(ArrayList<Bullet> bullets) {
    //creates a bullet
    if (lastBulletFire <=0) {
      bullets.add(new Bullet(x-5, y+10, 10, 0));
      lastBulletFire = bulletDelay;
    }
  }
}
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
 
    public void display() {
    if (onScreen()) {
      fill(255, 0, 0);
      ellipseMode(CORNER);
      ellipse(x, y, xDim, yDim);
    }
  }
 
    public void update(ArrayList<Bullet> bullets, Player player) {
    //generates bullets and updates movement
    x += velx;
    y += vely;
    if (lastBulletFire <=0 && onScreen()) {
      generateBullet(bullets);
      lastBulletFire = bulletDelay;
    }
    lastBulletFire --;
  }
 
    public void generateBullet(ArrayList<Bullet> bullets) {
    //creates bullets in a spiral pattern
    for (int bullet = 0; bullet < bulletNumber; bullet++) {
      bullets.add(new Bullet(x+xDim/2, y+yDim/2, 5, (bulletCounter%bulletAmount)*(360/bulletAmount)+(bullet*90)));
    }
    bulletCounter++;
  }
}
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
 
    public void display() {
    if (onScreen()) {
      fill(255, 0, 0);
      rectMode(CORNER);
      rect(x, y, xDim, yDim);
    }
  }
 
    public void update(ArrayList<Bullet> bullets, Player player) {
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
 
  public void calculate(Player player, float newx, float newy) {
    //allows the enemy to aim at the player
    float slope = (newy-player.y)/(newx-player.x);
    if (newy> player.y && newx > player.x) {
      direction = PApplet.parseInt(degrees(atan(slope)));
    }
    if (newy == player.y && newx > player.x) {
      direction = 0;
    }
    if (newy > player.y && newx < player.x) {
      direction = 180 + PApplet.parseInt(degrees(atan(slope)));
    }
    if (newy == player.y && newx < player.x) {
      direction = 180;
    }
    if (newy < player.y && newx > player.x) {
      direction = 360 + PApplet.parseInt(degrees(atan(slope)));
    }
    if (newy > player.y && newx == player.x) {
      direction = 90;
    }
    if (newy < player.y && newx < player.x) {
      direction = 180 + PApplet.parseInt(degrees(atan(slope)));
    }
    if (newy < player.y && newx == player.x) {
      direction = 270;
    }
    //unit circle starts facing right by default, this line corrects that to face up
    direction -= 90;
  }
 
    public void generateBullet(ArrayList<Bullet> bullets) {
    //creates tripleshot pattern aimed at player
    for (int bullet = 0; bullet < bulletAmount; bullet++) {
      bullets.add(new Bullet(x+xDim/2, y+yDim/2, 5, direction-10+10*bullet));
    }
  }
}
class SeekerBullet extends Bullet {
  int delay;
  float originalVel;
  float accel;
  boolean accelerate;
  SeekerBullet(float tempx, float tempy, float tempvel, int tempdir, int tempdelay, float tempaccel) {
    super(tempx, tempy, tempvel, tempdir);
    xDim = 20;
    yDim = 20;
    delay = tempdelay;
    originalVel = tempvel;
    accel = tempaccel;
    accelerate = true;
  }
  public void display() {
    if (onScreen()) {
      fill(75, 0, 130);
      ellipseMode(CORNER);
      ellipse(x, y, xDim, yDim);
    }
  }
  public void update(ArrayList<Bullet> bullets, Player player) {
    calculateVel(direction, velocity);
    x+=xvel;
    y+=yvel;
    delay--;
    if (delay == 0) {
      accelerate = false;
    }
    if (delay == -60) {
      calculate(player, x, y);
      accelerate = true;
    }
    if (accelerate && velocity < originalVel) {
      velocity += accel;
    }
    else if (!accelerate && velocity > 0) {
      velocity -= accel;
    }
    else if (velocity < 0) {
      velocity = 0;
    }
  }
   
  public void calculate(Player player, float newx, float newy) {
    //allows the bullet to track the player
    float slope = (newy-player.y)/(newx-player.x);
    if (newy> player.y && newx > player.x) {
      direction = PApplet.parseInt(degrees(atan(slope)));
    }
    if (newy == player.y && newx > player.x) {
      direction = 0;
    }
    if (newy > player.y && newx < player.x) {
      direction = 180 + PApplet.parseInt(degrees(atan(slope)));
    }
    if (newy == player.y && newx < player.x) {
      direction = 180;
    }
    if (newy < player.y && newx > player.x) {
      direction = 360 + PApplet.parseInt(degrees(atan(slope)));
    }
    if (newy > player.y && newx == player.x) {
      direction = 90;
    }
    if (newy < player.y && newx < player.x) {
      direction = 180 + PApplet.parseInt(degrees(atan(slope)));
    }
    if (newy < player.y && newx == player.x) {
      direction = 270;
    }
    //unit circle starts facing right by default, this line corrects that to face up
    direction += 180;
  }
}
class SlowBullet extends Bullet {
  //a bullet that rapidly slows down after being fired
  float slowVelocity, slowIncrement;
 
  SlowBullet(float tempx, float tempy, float tempvel, float tempslowvel, int tempdir, float tempslowincrement) {
    super(tempx, tempy, tempvel, tempdir);
    alive = true;
    slowVelocity = tempslowvel;
    slowIncrement = tempslowincrement;
    direction = tempdir-90;
    xDim = 10;
    yDim = 10;
  }
  public void update(ArrayList<Bullet> bullets, Player player) {
    calculateVel(direction, velocity);
    x+=xvel;
    y+=yvel;
    if (velocity > slowVelocity) {
      velocity -= slowIncrement;
    }
  }
}
class Swarm extends Enemy {
  float accelx, accely, originalVely;
   
  Swarm(float tempx, float tempy, float tempvelx, float tempvely) {
    super(tempx, tempy, tempvelx, tempvely);
    xDim = 20;
    yDim = 20;
    originalVely = tempy;
    pointValue = 10;
    lives = 1;
    if (x >= width/2) {
      accelx = random(-.05f);
    }
    if (x < width/2) {
      accelx = random(.05f);
    }
    accely = random(-.1f, .1f);
  }
   
  public void display() {
    fill(255, 0, 0);
    rectMode(CORNER);
    rect(x, y, xDim, yDim);
  }
   
  public void update(ArrayList<Bullet> bullets, Player player) {
    //Swarm moves randomly towards the player
    x += velx;
    if(vely > originalVely) { //ensures that the swarm does not start moving backwards or too slowly
      y += vely;
    }
    velx += accelx;
    vely += accely;
  }
}
class WaveHandler {
  ArrayList<Enemy> enemies;
  int wave;
  int points;
  boolean bossWave, victory;
  //This contains the data on all enemies
  Enemy[][] waveList= {
    {
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f)
      }
      ,
    {
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -100, 0, .5f), new Swarm(185, -100, 0, .5f), new Swarm(285, -100, 0, .5f), new Swarm(385, -100, 0, .5f), new Swarm(485, -100, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
    }
    ,
    {
      new Diamond(-100, 0, 1, 2, 0, -.01f), new Diamond(-100, 60, 1.5f, 2, 0, -.01f), new Diamond(-100, 120, 2, 2, 0, -.01f),
      new Diamond(width+100, 0, -1, 2, 0, -.01f), new Diamond(width+100, 60, -1.5f, 2, 0, -.01f), new Diamond(width+100, 120, -2, 2, 0, -.01f),
      new Diamond(0, -100, 1, 3, 0, -.01f), new Diamond(width*.25f, -100, 1, 3, 0, -.01f), new Diamond(width*.75f, -100, -1, 3, 0, -.01f), new Diamond(width, -100, -1, 3, 0, -.01f),
    }
    ,
    {
      new Enemy(85, -100, 0, 2), new Enemy(185, -100, 0, 2), new Enemy(385, -100, 0, 2), new Enemy(485, -100, 0, 2),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
      new Swarm(85, -200, 0, .5f), new Swarm(185, -200, 0, .5f), new Swarm(285, -200, 0, .5f), new Swarm(385, -200, 0, .5f), new Swarm(485, -200, 0, .5f),
    }
    ,
    {
      new Rusher(-100, 0, 1, 1), new Rusher(-100, 60, 1.5f, 1), new Rusher(-100, 120, 2, 1), new Rusher(-100, 180, 2.5f, 1), new Rusher(-100, 240, 3, 1)
      }
      ,
    {
      new Rusher(width+100, 0, -1, 1), new Rusher(width+100, 60, -1.5f, 1), new Rusher(width+100, 120, -2, 1), new Rusher(width+100, 180, -2.5f, 1), new Rusher(width+100, 240, -3, 1)
      }
      ,
    {
      new Diamond(width*.1f, -100, 1, 3, 0, -.01f), new Diamond(width*.2f, -100, -1, 3, 0, -.01f), new Diamond(width*.3f, -100, 1, 3, 0, -.01f), new Diamond(width*.4f, -100, -1, 3, 0, -.01f),
      new Diamond(width*.5f, -200, 1, 3, 0, -.01f), new Diamond(width*.6f, -200, -1, 3, 0, -.01f), new Diamond(width*.7f, -200, 1, 3, 0, -.01f), new Diamond(width*.8f, -200, -1, 3, 0, -.01f),
      new Diamond(width*.1f, -300, 1, 3, 0, -.01f), new Diamond(width*.2f, -300, -1, 3, 0, -.01f), new Diamond(width*.3f, -300, 1, 3, 0, -.01f), new Diamond(width*.4f, -300, -1, 3, 0, -.01f),
      new Diamond(width*.5f, -400, 1, 3, 0, -.01f), new Diamond(width*.6f, -400, -1, 3, 0, -.01f), new Diamond(width*.7f, -300, 1, 3, 0, -.01f), new Diamond(width*.8f, -400, -1, 3, 0, -.01f),
    }
    ,
    {
      new Rotator(85, -200, 0, 2), new Rotator(485, -200, 0, 2)
      }
      ,
    {
      new BossOne(200, -200, 0, 2, 50)
      }
    };
 
    WaveHandler() {
      wave = 0;
      points = 0;
      enemies = new ArrayList<Enemy>();
      victory = false;
    }
 
  public void spawnWave() {
    //spawns the next wave if the previous wave was killed
    if (enemies.size() == 0) {
      if (!(wave == waveList.length)) {
        for (int i = 0; i < waveList[wave].length; i++) {
          if (waveList[wave][i].isBoss) {
            bossWave = true;
          }
          else {
            bossWave = false;
          }
          enemies.add(waveList[wave][i]);
        }
      }
      else if (wave >= waveList.length) {
        victory = true;
      }
      wave ++;
    }
  }
  public void update(ArrayList<Bullet> bullets, Player player) {
    //walks through the list of enemies
    for (int i = enemies.size()-1; i >= 0; i--) {
      Enemy enemy = enemies.get(i);
      enemy.update(bullets, player);
      enemy.display();
      if (enemy.lives == 0) {
        enemy.alive = false;
      }
      if (enemy.y > height*1.3f || enemy.x < -height*.5f || enemy.x > height*1.5f || enemy.y < -height) {
        //removes enemies if they move too far offscreen
        enemies.remove(i);
      }
      if (enemy.alive == false) {
        points += enemy.pointValue;
        enemies.remove(i);
      }
    }
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Red" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
