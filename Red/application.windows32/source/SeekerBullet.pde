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
  void display() {
    if (onScreen()) {
      fill(75, 0, 130);
      ellipseMode(CORNER);
      ellipse(x, y, xDim, yDim);
    }
  }
  void update(ArrayList<Bullet> bullets, Player player) {
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
   
  void calculate(Player player, float newx, float newy) {
    //allows the bullet to track the player
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
    direction += 180;
  }
}
