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
      accelx = random(-.05);
    }
    if (x < width/2) {
      accelx = random(.05);
    }
    accely = random(-.1, .1);
  }
   
  void display() {
    fill(255, 0, 0);
    rectMode(CORNER);
    rect(x, y, xDim, yDim);
  }
   
  void update(ArrayList<Bullet> bullets, Player player) {
    //Swarm moves randomly towards the player
    x += velx;
    if(vely > originalVely) { //ensures that the swarm does not start moving backwards or too slowly
      y += vely;
    }
    velx += accelx;
    vely += accely;
  }
}
