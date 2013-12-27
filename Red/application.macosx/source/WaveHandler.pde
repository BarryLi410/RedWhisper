class WaveHandler {
  ArrayList<Enemy> enemies;
  int wave;
  int points;
  boolean bossWave, victory;
  //This contains the data on all enemies
  Enemy[][] waveList= {
    {
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5)
      }
      ,
    {
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -100, 0, .5), new Swarm(185, -100, 0, .5), new Swarm(285, -100, 0, .5), new Swarm(385, -100, 0, .5), new Swarm(485, -100, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
    }
    ,
    {
      new Diamond(-100, 0, 1, 2, 0, -.01), new Diamond(-100, 60, 1.5, 2, 0, -.01), new Diamond(-100, 120, 2, 2, 0, -.01),
      new Diamond(width+100, 0, -1, 2, 0, -.01), new Diamond(width+100, 60, -1.5, 2, 0, -.01), new Diamond(width+100, 120, -2, 2, 0, -.01),
      new Diamond(0, -100, 1, 3, 0, -.01), new Diamond(width*.25, -100, 1, 3, 0, -.01), new Diamond(width*.75, -100, -1, 3, 0, -.01), new Diamond(width, -100, -1, 3, 0, -.01),
    }
    ,
    {
      new Enemy(85, -100, 0, 2), new Enemy(185, -100, 0, 2), new Enemy(385, -100, 0, 2), new Enemy(485, -100, 0, 2),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
      new Swarm(85, -200, 0, .5), new Swarm(185, -200, 0, .5), new Swarm(285, -200, 0, .5), new Swarm(385, -200, 0, .5), new Swarm(485, -200, 0, .5),
    }
    ,
    {
      new Rusher(-100, 0, 1, 1), new Rusher(-100, 60, 1.5, 1), new Rusher(-100, 120, 2, 1), new Rusher(-100, 180, 2.5, 1), new Rusher(-100, 240, 3, 1)
      }
      ,
    {
      new Rusher(width+100, 0, -1, 1), new Rusher(width+100, 60, -1.5, 1), new Rusher(width+100, 120, -2, 1), new Rusher(width+100, 180, -2.5, 1), new Rusher(width+100, 240, -3, 1)
      }
      ,
    {
      new Diamond(width*.1, -100, 1, 3, 0, -.01), new Diamond(width*.2, -100, -1, 3, 0, -.01), new Diamond(width*.3, -100, 1, 3, 0, -.01), new Diamond(width*.4, -100, -1, 3, 0, -.01),
      new Diamond(width*.5, -200, 1, 3, 0, -.01), new Diamond(width*.6, -200, -1, 3, 0, -.01), new Diamond(width*.7, -200, 1, 3, 0, -.01), new Diamond(width*.8, -200, -1, 3, 0, -.01),
      new Diamond(width*.1, -300, 1, 3, 0, -.01), new Diamond(width*.2, -300, -1, 3, 0, -.01), new Diamond(width*.3, -300, 1, 3, 0, -.01), new Diamond(width*.4, -300, -1, 3, 0, -.01),
      new Diamond(width*.5, -400, 1, 3, 0, -.01), new Diamond(width*.6, -400, -1, 3, 0, -.01), new Diamond(width*.7, -300, 1, 3, 0, -.01), new Diamond(width*.8, -400, -1, 3, 0, -.01),
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
 
  void spawnWave() {
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
  void update(ArrayList<Bullet> bullets, Player player) {
    //walks through the list of enemies
    for (int i = enemies.size()-1; i >= 0; i--) {
      Enemy enemy = enemies.get(i);
      enemy.update(bullets, player);
      enemy.display();
      if (enemy.lives == 0) {
        enemy.alive = false;
      }
      if (enemy.y > height*1.3 || enemy.x < -height*.5 || enemy.x > height*1.5 || enemy.y < -height) {
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
