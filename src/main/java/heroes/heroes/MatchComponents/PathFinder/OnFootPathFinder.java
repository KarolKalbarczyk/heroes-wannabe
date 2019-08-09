package heroes.heroes.MatchComponents.PathFinder;

import heroes.heroes.MatchComponents.Field;
import org.springframework.stereotype.Component;

import java.util.PriorityQueue;

@Component
public class OnFootPathFinder implements PathFinder {

    public double calculateDistance(Field[][] fields, Field start, Field end){
        Graph graph = new Graph(fields);
        return graph.dijkstra(start,end);
    }

}

