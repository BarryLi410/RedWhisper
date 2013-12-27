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
  void display() {
    if (onScreen()) {
      fill(255, 0, 0);
      ellipseMode(CORNER);
      ellipse(x, y, xDim, yDim);
    }
  }
  void update(ArrayList<Bullet> bullets, Player player) {
    calculateVel(direction, velocity);
    x+=xvel;
    y+=yvel;
  }
  void calculateVel(int direction, float velocity) {
    xvel = cos(radians(direction))*velocity;
    yvel = sin(radians(direction))*velocity;
  }
 
  boolean onScreen() {
    if (x+xDim > 0 && y+yDim > 0 && x < width && y < height) {
      return true;
    }
    else {
      return false;
    }
  }
 
  boolean hitDetect(MoveableObject object) {
    if (x+xDim > object.x && x < object.x + object.xDim && y+yDim > object.y && y < object.y + object.yDim) {
      return true;
    }
    else {
      return false;
    }
  }
}
