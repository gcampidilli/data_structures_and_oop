package diver;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import graph.Node;
import graph.NodeStatus;
import graph.ScramState;
import graph.SeekState;
import graph.SewerDiver;

public class McDiver extends SewerDiver {

    /** Get to the ring in as few steps as possible. Once there, <br>
     * McDiver must return from this function in order to pick<br>
     * it up. If McDiver continues to move after finding the ring rather <br>
     * than returning, it will not count.<br>
     * If McDiver returns from this function while not standing on top of the ring, <br>
     * it will count as a failure.
     *
     * There is no limit to how many steps McDiver can take, but you will receive<br>
     * a score bonus multiplier for finding the ring in fewer steps.
     *
     * At every step, McDiver knows only the current tile's ID and the ID of all<br>
     * open neighbor tiles, as well as the distance to the ring at each of <br>
     * these tiles (ignoring walls and obstacles).
     *
     * In order to get information about the current state, use functions<br>
     * currentLocation(), neighbors(), and distanceToRing() in state.<br>
     * You know McDiver is standing on the ring when distanceToRing() is 0.
     *
     * Use function moveTo(long id) in state to move McDiver to a neighboring<br>
     * tile by its ID. Doing this will change state to reflect your new position.
     *
     * A suggested first implementation that will always find the ring, but <br>
     * likely won't receive a large bonus multiplier, is a depth-first walk. <br>
     * Some modification is necessary to make the search better, in general. */
    @Override
    public void seek(SeekState state) {
        // TODO : Find the ring and return.
        // DO NOT WRITE ALL THE CODE HERE. DO NOT MAKE THIS METHOD RECURSIVE.
        // Instead, write your method elsewhere, with a good specification,
        // and call it from this one.

        dfsWalk(state);

    }

    /** HS */
    private HashSet<Long> map= new HashSet<>();

    private Heap<NodeStatus> sortNeighbors(Collection<NodeStatus> c) {
        Heap<NodeStatus> sortedNeighbors= new Heap<>(true);

        for (NodeStatus n : c) {
            sortedNeighbors.add(n, n.getDistanceToRing());
        }

        return sortedNeighbors;
    }

    /* The Diver is standing on a Tile u given by State s.<br>
     * Visit every node reachable along paths of unvisited nodes from Tile u<br>
     * until Ring Tile is found.
     *
     * End with Diver standing on Ring Tile.<br>
     * Return true if ring is found
     * Precondition: u is unvisited. */
    private boolean dfsWalk(SeekState s) {
        long u= s.currentLocation();
        map.add(u);

        Heap<NodeStatus> ns= sortNeighbors(s.neighbors());

        while (ns.size > 0) {
            NodeStatus n= ns.poll();
            long id= n.getId();
            if (!map.contains(id)) {
                s.moveTo(id);
                if (s.distanceToRing() == 0) {
                    return true;
                } else {
                    if (dfsWalk(s) == true) { return true; }
                    s.moveTo(u);
                }
            }
        }
        return false;
    }

    /** Scram --get out of the sewer system before the steps are all used, trying to <br>
     * collect as many coins as possible along the way. McDiver must ALWAYS <br>
     * get out before the steps are all used, and this should be prioritized above<br>
     * collecting coins.
     *
     * You now have access to the entire underlying graph, which can be accessed<br>
     * through ScramState. currentNode() and getExit() will return Node objects<br>
     * of interest, and getNodes() will return a collection of all nodes on the graph.
     *
     * You have to get out of the sewer system in the number of steps given by<br>
     * stepsToGo(); for each move along an edge, this number is <br>
     * decremented by the weight of the edge taken.
     *
     * Use moveTo(n) to move to a node n that is adjacent to the current node.<br>
     * When n is moved-to, coins on node n are automatically picked up.
     *
     * McDiver must return from this function while standing at the exit. Failing <br>
     * to do so before steps run out or returning from the wrong node will be<br>
     * considered a failed run.
     *
     * Initially, there are enough steps to get from the starting point to the<br>
     * exit using the shortest path, although this will not collect many coins.<br>
     * For this reason, a good starting solution is to use the shortest path to<br>
     * the exit. */

    /** Heap with Node Make hashmap with Node, NodeStatus; <br>
     * Frontier set = neighbors first item in
     *
     *
     * the hashmap is current node mcdiver is standing on aka tile w ring for each neighbor of
     * current node, check whichever has the connecting edge with the shortest length Now there is
     * the new beginning node, continue to explore the neighbors of the current node until we have
     * reached the end how do we know we have reached the end? if statement! compare iD of current
     * node to that of getExit() node linkedlist for the shortest path -> continually get
     * backpointers from hashmap (Edge var src/dest) until hashmap has no more backpointers **/
    @Override
    public void scram(ScramState state) {
        // TODO: Get out of the sewer system before the steps are used up.
        // DO NOT WRITE ALL THE CODE HERE. Instead, write your method elsewhere,
        // with a good specification, and call it from this one.
        // We say this because it makes it easier for you to try different
        // possibilities, always keeping at least one method that always scrams
        // in the prescribed number of steps.
        shortestScram(state);

    }

    // create maxHeap with neighbors based on coin value
    private Heap<Node> sortCoinValue(Set<Node> c) {
        Heap<Node> sortedCoins= new Heap<>(false);

        for (Node n : c) {
            sortedCoins.add(n, n.getTile().coins());
        }
        System.out.println(sortedCoins.toStringPriorities());
        return sortedCoins;
    }

    // obtain t/f value on whether moving to next node is possible
    private boolean checkNumSteps(ScramState s, Node neighbor) {
        Node current= s.currentNode();
        List<Node> toExit= A6.shortestPath(neighbor, s.exit());
        System.out.println("neighbor to exit dist: " + A6.pathSum(toExit));

        return current.getEdge(neighbor).length() + A6.pathSum(toExit) < s.stepsToGo();
    }

    HashSet<Node> scramMap= new HashSet<>();

    // recursive method that moves mcDiver
    public void shortestScram(ScramState state) {
        Node exit= state.exit();
        Node current= state.currentNode();
        List<Node> shortPath= A6.shortestPath(current, exit);
        Set<Node> neighbors= current.getNeighbors();
        Heap<Node> heapNeighbors= sortCoinValue(neighbors);
        System.out.println("Inside shortestscram" + heapNeighbors.toStringPriorities());

        Node nextNode;

        while (heapNeighbors.size > 0) {
            nextNode= heapNeighbors.poll();
            System.out.println("polled");
            // && !scramMap.contains(nextNode)
            if (checkNumSteps(state, nextNode)) {
                state.moveTo(nextNode);
                scramMap.add(nextNode);

                System.out.println("moved to");
                System.out.println("took coins");

                shortestScram(state);
            }
        }

        /** Node n= shortPath.get(1); int i= 2; while (n != Exit) { // if (checkNumSteps(state, ))
         * state.moveTo(n); n= shortPath.get(i); i= i + 1; } state.moveTo(Exit); **/
    }

    private HashMap<Node, List<Node>> getMasterMap(ScramState state) {
        Node current= state.currentNode();

        HashMap<Node, List<Node>> masterMap= new HashMap<>();
        for (Node n : state.allNodes()) {
            masterMap.put(n, A6.shortestPath(current, n));
        }

        return masterMap;
    }

}
