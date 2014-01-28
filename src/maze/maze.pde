//The types of edges each tile can have
static final int PASSAGE = 0;
static final int TUNNEL = 1;
static final int WALL = 3;

//direction names
static final int UP = 10;
static final int DOWN = 11;
static final int LEFT = 12;
static final int RIGHT = 13;
static final int UP_TUNNEL = 14;
static final int DOWN_TUNNEL = 15;
static final int LEFT_TUNNEL = 16;
static final int RIGHT_TUNNEL = 17;
static final int NO_DIRECTION = 18;

int screen_width = 1200;
int screen_height = 600;
int origin = 60;        //the amount of space to leave around the edge of the image
int box_width = 28;     //the width of each box tile, must be an even?

//28 and 9
int maze_width = 32;
int maze_height = 9;

int end_x = 0;
int end_y = 0;

tile[][] maze;

//queue for dijkstra
//stack for building maze
Queue<Point> queue=new LinkedList<Point>();
Stack stack;

private Random generator = new Random();

void setup() {
  size(screen_width, screen_height);

        Luke lol = new Luke();

  makeMaze();
}

void draw() {
}

void keyPressed() {
  if(key == 'M' || key == 'm'){
    makeMaze();
  }
  if(key == 'W'){
    maze_width++;
    makeMaze();
  }
  if(key == 'w'){
    maze_width--;
    if(maze_width<2){maze_width=2;}
    makeMaze();
  }
  if(key == 'H'){
    maze_height++;
    makeMaze();
  }
  if(key == 'h'){
    maze_height--;
    if(maze_height<2){maze_height=2;}
    makeMaze();
  }
  if(key == 'S'){
    box_width+=2;
    makeMaze();
  }
  if(key == 's'){
    box_width-=2;
    if(box_width<10){box_width=10;}
    makeMaze();
  }
}

void dijkstra(){

  if(queue.size()==0){
    return;
  }

  Point p;
  Point working;

  Iterator iterator;
  ArrayList<Point> neighbours;

  p = queue.poll();

  neighbours = maze[p.x][p.y].getPaths();
  iterator = neighbours.iterator();

  if(neighbours.size()!=0){
    while(iterator.hasNext()){
      working = (Point)iterator.next();
      maze[working.x][working.y].visited=true;
      int number_of_walls = maze[working.x][working.y].countWalls();

      if(number_of_walls==3){
        if(working.x==maze_height-1||working.x==0||working.y==maze_width-1||working.y==0){
          end_x=working.x;
          end_y=working.y;
        }
      }
      stack.push(working);
      queue.add(working);
    }
  }
}

void makeMaze(){

    //y-M-D
  generator.setSeed(19091990);

  maze = new tile[maze_height][maze_width];

  //clear the screen
  //set stroke to black
  background(255);
  stroke(0);

  //create the maze to start at 0,0
  tile start = new tile(0,0);
  maze[0][0]=start;

  //Set up the stack, and start the maze
  stack = new Stack();
  stack.push(start);

  //used to store the current working point, and the direction to head
  tile working;
  int direction;

  //while there are points on the stack
  while(!stack.empty()){

    working = (tile)stack.peek();

    direction = maze[working.x][working.y].getRandomUnvisitedNeighbour();

    if(direction!=NO_DIRECTION){

      if(working.x==0&&working.y==0){
        if(direction==DOWN){
          maze[0][0].removeWall(UP);
        }
        else{
          maze[0][0].removeWall(LEFT);
        }
      }

      stack.push(maze[working.x][working.y].makePathway(direction));

    }
    else{
      stack.pop();
    }

  }//end stack loop


  //NOW WE HAVE TO FIND THE END OF THE MAZE
  //THE STACK WILL COUNT ALL VISITED TILES, ONCE ALL VISITED
  stack = new Stack();

  //ADD THE HOME TILE, 0,0
  queue=new LinkedList<Point>();
  queue.add(new Point(0,0));
  maze[0][0].visited=true;

  while(stack.size()!=((maze_width*maze_height)-1)){
    dijkstra();
  }

  if(end_x==maze_height-1){
    maze[end_x][end_y].removeWall(DOWN);
  }
  else if(end_x==0){
    maze[end_x][end_y].removeWall(UP);
  }
  else if(end_y==0){
    maze[end_x][end_y].removeWall(LEFT);
  }
  else{
    maze[end_x][end_y].removeWall(RIGHT);
  }

  drawMaze();

}//end make maze

void drawMaze(){
  for(int i=0;i<maze_height;i++){
    for(int j=0;j<maze_width;j++){
      tile temp= maze[i][j];
      if(temp !=null){
        temp.display();
      }
    }
  }
}

class tile {

  int up;
  int down;
  int left;
  int right;

  boolean visited;

  int x;                //x coord
  int y;                //y coord

  tile(int x_pos,int y_pos) {
    this.up = down = left = right = WALL;
    this.x = x_pos;
    this.y = y_pos;
    this.visited = false;
  }

  //Get direction to move, empty neighbours or tunnel options!
  int getRandomUnvisitedNeighbour(){

    ArrayList<Integer> available_directions = new ArrayList<Integer>();

    if(x!=0 && maze[x-1][y]==null){
      available_directions.add(UP);
    }
    else if(x>1 && maze[x-2][y]==null && this.up==WALL){
      available_directions.add(UP_TUNNEL);
    }

    if(y<maze_width-1 && maze[x][y+1]==null){
      available_directions.add(RIGHT);
    }
    else if(y<maze_width-2 && maze[x][y+2]==null && this.right==WALL){
      available_directions.add(RIGHT_TUNNEL);
    }

    if(x<maze_height-1 && maze[x+1][y]==null){
      available_directions.add(DOWN);
    }
    else if(x<maze_height-2 && maze[x+2][y]==null && this.down==WALL){
      available_directions.add(DOWN_TUNNEL);
    }

    if(y!=0 && maze[x][y-1]==null){
      available_directions.add(LEFT);
    }
    else if(y>1 && maze[x][y-2]==null  && this.left==WALL){
      available_directions.add(LEFT_TUNNEL);
    }

    if(y<maze_width-1 && maze[x][y+1]==null){
      available_directions.add(RIGHT);
    }
    else if(y<maze_width-2 && maze[x][y+2]==null && this.right==WALL){
      available_directions.add(RIGHT_TUNNEL);
    }

    if(available_directions.size()==0){
      return NO_DIRECTION;
    }

    //decides randomly which of the available elements to select
    return available_directions.get(generator.nextInt(available_directions.size()));

  }

  tile makePathway(int direction){
    removeWall(direction);
    return makeNewTile(direction);
  }

  void removeWall(int direction){

    switch (direction) {
        case UP:
                  this.up=PASSAGE;
                  break;
        case DOWN:
                  this.down=PASSAGE;
                  break;
        case LEFT:
                  this.left=PASSAGE;
                  break;
        case RIGHT:
                  this.right=PASSAGE;
                  break;
        case UP_TUNNEL:
                  this.up=TUNNEL;
                  break;
        case DOWN_TUNNEL:
                  this.down=TUNNEL;
                  break;
        case LEFT_TUNNEL:
                  this.left=TUNNEL;
                  break;
        case RIGHT_TUNNEL:
                  this.right=TUNNEL;
                  break;
    }//end switch

  }//end-remove-wall

  //makes a tile in the direction provided
  tile makeNewTile(int direction){

    int new_x = this.x;
    int new_y = this.y;
    int dir_opposite = direction;

    switch (direction) {
        case UP:           new_x-=1;
                           dir_opposite = DOWN;
                           break;
        case DOWN:         new_x+=1;
                           dir_opposite = UP;
                           break;
        case LEFT:         new_y-=1;
                           dir_opposite = RIGHT;
                           break;
        case RIGHT:        new_y+=1;
                           dir_opposite = LEFT;
                           break;
        case UP_TUNNEL:    new_x-=2;
                           dir_opposite = DOWN_TUNNEL;
                           break;
        case DOWN_TUNNEL:  new_x+=2;
                           dir_opposite = UP_TUNNEL;
                           break;
        case LEFT_TUNNEL:  new_y-=2;
                           dir_opposite = RIGHT_TUNNEL;
                           break;
        case RIGHT_TUNNEL: new_y+=2;
                           dir_opposite = LEFT_TUNNEL;
                           break;
    }//end switch

    tile n = new tile(new_x,new_y);
    n.removeWall(dir_opposite);
    maze[n.x][n.y]=n;
    return n;
  }

  //for a tile, return the points that can be accessed by it
  //used in dijkstra's implementation
  ArrayList<Point> getPaths(){
    ArrayList<Point> available_directions = new ArrayList<Point>();

    if(up==PASSAGE&&x!=0){
      if(maze[x-1][y].visited==false)
        available_directions.add(new Point(x-1,y));
    }
    else if(up==TUNNEL&&x>1){
      if(maze[x-2][y].visited==false)
        available_directions.add(new Point(x-2,y));
    }

    if(down==PASSAGE){
      if(maze[x+1][y].visited==false)
        available_directions.add(new Point(x+1,y));
    }
    else if(down==TUNNEL){
      if(maze[x+2][y].visited==false)
        available_directions.add(new Point(x+2,y));
    }

    if(left==PASSAGE&&y!=0){
      if(maze[x][y-1].visited==false)
        available_directions.add(new Point(x,y-1));
    }
    else if(left==TUNNEL&&y>1){
      if(maze[x][y-2].visited==false)
        available_directions.add(new Point(x,y-2));
    }

    if(right==PASSAGE){
      if(maze[x][y+1].visited==false)
        available_directions.add(new Point(x,y+1));
    }
    else if(right==TUNNEL){
      if(maze[x][y+2].visited==false)
        available_directions.add(new Point(x,y+2));
    }

    return available_directions;
  }

  //the number of walls this tile has
  //3 walls means its a dead end
  int countWalls()
  {
    int walls = 0;
    if(up==WALL)
      walls++;
    if(down==WALL)
      walls++;
    if(left==WALL)
      walls++;
    if(right==WALL)
      walls++;

    return walls;
  }

  //prints
  void display() {

    //stupid bug where i accidentally rotated y and x, lol
    int adjusted_x = (y*(box_width))+origin;
    int adjusted_y = (x*(box_width))+origin;

    //top_left points
    int xx = adjusted_x;
    int yy = adjusted_y;

    noStroke();
    rectMode(CENTER);
    fill(0);

    //box width must be greater than 10 and even
    int line_thickness = ((box_width)/10)*2;

    int line_length = (box_width)-(2*line_thickness);

    int offset = ((box_width)/2)-line_thickness;

    if(right!=WALL){
      rect(adjusted_x+offset+(line_thickness/2), adjusted_y-offset, line_thickness*2, line_thickness);
      rect(adjusted_x+offset+(line_thickness/2), adjusted_y+offset, line_thickness*2, line_thickness);
    }
    else{
      rect(adjusted_x+offset, adjusted_y, line_thickness, line_length+line_thickness);
    }

    if(left!=WALL){
      rect(adjusted_x-offset-(line_thickness/2), adjusted_y+offset, line_thickness*2, line_thickness);
      rect(adjusted_x-offset-(line_thickness/2), adjusted_y-offset, line_thickness*2, line_thickness);
    }
    else{
      rect(adjusted_x-offset, adjusted_y, line_thickness, line_length+line_thickness);
    }

    if(up!=WALL){
      rect(adjusted_x-offset, adjusted_y-offset-(line_thickness/2), line_thickness, line_thickness*2);
      rect(adjusted_x+offset, adjusted_y-offset-(line_thickness/2), line_thickness, line_thickness*2);
    }
    else{
      rect(adjusted_x, adjusted_y-offset, line_length+line_thickness, line_thickness);
    }

    if(down!=WALL){
      rect(adjusted_x+offset, adjusted_y+offset+(line_thickness/2), line_thickness, line_thickness*2);
      rect(adjusted_x-offset, adjusted_y+offset+(line_thickness/2), line_thickness, line_thickness*2);
    }
    else{
      rect(adjusted_x, adjusted_y+offset, line_length+line_thickness, line_thickness);
    }

  }//end display

}//end tile class

//own Point class
class Point{
  int x, y;

  Point( int x, int y ){
   this.x = x;
   this.y = y;
  }
}


