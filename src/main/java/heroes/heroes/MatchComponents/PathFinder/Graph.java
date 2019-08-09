package heroes.heroes.MatchComponents.PathFinder;

import heroes.heroes.MatchComponents.Field;

import java.util.PriorityQueue;

class Graph{
    Node[][] array;
    int verticalSize;
    int horizontalSize;
    PriorityQueue<Node> queue = new PriorityQueue<>();
    final boolean STRAIGHT =true;
    final boolean SLANT = false;

    public Graph( Field[][] fields) {
        this.array = new Node[fields.length][fields.length];
        for (int i = 0; i <array.length ; i++) {
            for (int j = 0; j <array.length ; j++) {
                this.array[i][j] = new Node(i,j,fields[i][j].isTaken());
            }
        }
        verticalSize = array.length;
        horizontalSize = array[0].length;
    }

    public double dijkstra(Field start,Field end){
        System.out.println("calculating");
        Node root = array[start.getRow()][start.getColumn()];
        Node goal = array[end.getRow()][end.getColumn()];
        root.cost = 0;
        checkNeighbours(root.horizontalArrayIndex,root.verticalArrayIndex);
        while(!root.equals(goal)){
            root = queue.peek();
            queue.remove();
            checkNeighbours(root.horizontalArrayIndex,root.verticalArrayIndex);
        }
        System.out.println(root.cost);
        return root.cost;
    }

    public void checkNeighbour(int horizontalIndex,int verticalIndex,
                               int horizontalModifier, int verticalModifier,
                               Node node){
        int horizontalValue = horizontalIndex-horizontalModifier;
        int verticalValue = verticalIndex-verticalModifier;
        if(horizontalValue>=horizontalSize || verticalValue>=verticalSize
                || horizontalValue<0 || verticalValue<0){
            return;
        }
        Node vertice =  array[horizontalValue][verticalValue];
        if(horizontalModifier == 0 && verticalModifier == 0){
            return;
        }
        if(horizontalModifier !=0 && verticalModifier !=0){
            if(vertice.calculateCost(node,SLANT)){
                queue.add(vertice);
            }
        }else {
            if(vertice.calculateCost(node,STRAIGHT)){
                queue.add(vertice);
            }
        }
    }

    public void checkNeighbours(int horizontalIndex, int verticalIndex){
        Node node = array[horizontalIndex][verticalIndex];
        for (int i = -1; i <=1 ; i++) {
            for (int j = -1; j <=1 ; j++) {
                checkNeighbour(horizontalIndex,verticalIndex,i,j,node);
            }
        }
        node.visited = true;
    }
}
