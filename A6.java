
package a6;

/* NetId(s):gec83
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import graph.Edge;
import graph.Node;

/** attempt at coding shortest-path algorithm */
public class A6 {

    /** Replace "-1" by the time you spent on A2 in hours.<br>
     * Example: for 3 hours 15 minutes, use 3.25<br>
     * Example: for 4 hours 30 minutes, use 4.50<br>
     * Example: for 5 hours, use 5 or 5.0 */
    public static double timeSpent= -1;

    /** Return the shortest path from node v to node last <br>
     * ---or the empty list if a path does not exist. <br>
     * Note: The empty list is a list with 0 elements ---it is not "null". */
    public static List<Node> shortestPath(Node v, Node last) {
        /* TODO Implement this method.
         * Read the A6 assignment handout for all details.
         * Remember, the graph is undirected.
         * Finally, you will need to declare a HashMap. See the handout for details */

        // Contains an entry for each node in the frontier set. The priority of a node
        // is the length of the shortest known path from v to the node using only settled
        // node except for the last node, which is in F
        Heap<Node> F= new Heap<>(true);

        // Hashmap contains both Settled and Frontier sets
        // maps to node data, which contains information about shortest distance and back pointers
        HashMap<Node, NodeData> mapSF= new HashMap<>();

        mapSF.put(v, new NodeData(null, 0));
        F.add(v, 0.0);

        while (F.size > 0) {
            // need to know path(v) in order to obtain v's neighbors
            Node f= F.poll();

            // all shortest paths have been calculated
            // now put shortest path of node of interest into list
            if (f == last) {
                // we have to have visited at least once
                LinkedList<Node> shortPath= new LinkedList<>();
                shortPath.add(last);
                Node r= mapSF.get(last).bkptr;
                while (r != null) {
                    shortPath.addFirst(r);
                    r= mapSF.get(r).bkptr;
                }
                return shortPath;
            }

            for (Edge t : f.getExits()) {
                Node w= t.getOther(f);
                int dW;
                // make sure that w has been processed
                // if statement addresses far

                NodeData getF= mapSF.get(F);
                int tLength= t.length;

                if (!mapSF.containsKey(w)) { // w is in Far-Off Set
                    dW= mapSF.get(f).dist + t.length; // d[w] = d[f] + wgt(f, w)
                    mapSF.put(w, new NodeData(f, dW)); // bk[w] = f;
                    F.add(w, dW); // add w to F

                } else if (mapSF.get(f).dist + t.length < mapSF.get(w).dist) {
                    dW= mapSF.get(f).dist + t.length;
                    mapSF.get(w).dist= dW;
                    mapSF.get(w).bkptr= f;
                    F.changePriority(w, dW);
                }
            }

            for (Edge t : f.getExits()) {
                Node w= t.getOther(f);
                int dW;
                // make sure that w has been processed
                // if statement addresses far

                NodeData getF= mapSF.get(F);
                int tLength= t.length;

                if (!mapSF.containsKey(w)) { // w is in Far-Off Set
                    dW= getF + tLength; // d[w] = d[f] + wgt(f, w)
                    mapSF.put(w, new NodeData(f, dW)); // bk[w] = f;
                    F.add(w, dW); // add w to F

                } else if (mapSF.get(f).dist + t.length < mapSF.get(w).dist) {
                    dW= mapSF.get(f).dist + t.length;
                    mapSF.get(w).dist= dW;
                    mapSF.get(w).bkptr= f;
                    F.changePriority(w, dW);
                }
            }
        }

        // we have all the possible paths to f

        // no path from v to last --it's best to leave this at the end of the method
        return new LinkedList<>();
    }

    private int getDist(HashMap SF, Node f, Edge t) {
        return SF.get(f).dist + t.length;
    }

    /** An instance contains information about a node: <br>
     * the Distance of this node from the start node and <br>
     * its Backpointer: the previous node on a shortest path <br>
     * from the first node to this node (null for the start node). */
    private static class NodeData {
        /** shortest known distance from the start node to this one. */
        private int dist;
        /** backpointer on path (with shortest known distance) from start node to this one */
        private Node bkptr;

        /** Constructor: an instance with dist d from the start node and<br>
         * backpointer p. */
        private NodeData(Node p, int d) {
            dist= d;     // Distance from start node to this one.
            bkptr= p;    // Backpointer on the path (null if start node)
        }

        /** return a representation of this instance. */
        @Override
        public String toString() {
            return "dist " + dist + ", bckptr " + bkptr;
        }
    }

    /** = the path from the start node to node last.<br>
     * Precondition: mapSF contains all the necessary information about<br>
     * mapSF passed so that we have a way to backtrack through the path <br>
     * Node last passed as our "starting place" aka the node we are <br>
     * travelling to ............. the path. */
    public static List<Node> path(HashMap<Node, NodeData> mapSF, Node last) {
        List<Node> path= new LinkedList<>();
        Node p= last;
        // invariant: All the nodes from p's successor to node last are in
        // path, in reverse order.
        while (p != null) {
            path.add(0, p);
            p= mapSF.get(p).bkptr;
        }
        return path;
    }

    /** Return the sum of the weights of the edges on path p. <br>
     * Calculates distance Precondition: pa contains at least 1 node. <br>
     * If 1 node, it's a path of length 0, i.e. with no edges. */
    public static int pathSum(List<Node> p) {
        synchronized (p) {
            Node w= null;
            int sum= 0;
            // invariant: if w is null, n is the start node of the path.<br>
            // .......... if w is not null, w is the predecessor of n on the path.
            // .......... sum = sum of weights on edges from first node to v
            for (Node n : p) {
                if (w != null) sum= sum + w.getEdge(n).length;
                w= n;
            }
            return sum;
        }
    }

}
