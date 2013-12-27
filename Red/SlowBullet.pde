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
  void update(ArrayList<Bullet> bullets, Player player) {
    calculateVel(direction, velocity);
    x+=xvel;
    y+=yvel;
    if (velocity > slowVelocity) {
      velocity -= slowIncrement;
    }
  }
}
