package heroes.heroes.MatchComponents.PathFinder;



class Node implements Comparable<Node>{
    int verticalArrayIndex;
    int horizontalArrayIndex;
    double cost = Integer.MAX_VALUE;
    boolean visited =false;
    double SQUARE_ROOT_OF_TWO = 1.44;
    boolean taken;


    public Node( int horizontalArrayIndex, int verticalArrayIndex, boolean taken) {
        this.verticalArrayIndex = verticalArrayIndex;
        this.horizontalArrayIndex = horizontalArrayIndex;
        this.taken = taken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return verticalArrayIndex == node.verticalArrayIndex &&
                horizontalArrayIndex == node.horizontalArrayIndex;
    }

    public boolean calculateCost(Node node, boolean straight){
        if(this.visited || this.taken){
            return false;
        }
        double cost;
        if(straight) {
            cost = node.cost + 1;
        }else {
            cost = node.cost + SQUARE_ROOT_OF_TWO;
        }
        if (cost<this.cost ) {
            this.cost = cost;
            return true;
        }
        if(cost == this.cost){
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Node o) {
        if (this.cost>o.cost) return 1;
        else if(this.cost==o.cost) {
            return 1;
        }
        else return -1;
    }
}
