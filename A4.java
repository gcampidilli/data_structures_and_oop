package a4;
/* NetId(s): zak33, gec83

 * Name(s): Zak Kent, Grace Campidilli
 * What I thought about this assignment:
 *  Enjoyed the challenging content.
 *  Combination of loops and recursion was interesting to work with.
 *  Felt more comprehensive than past assignments, which is a good thing!
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** An instance of FluTree represents the spread of a flu among a Network of people. <br>
 * In this model, each person can "catch" the flu from only a single person. <br>
 * The root of the FluTree is the person who first got the flu. <br>
 * From there, each person in the FluTree is the child of the person who gave <br>
 * them the flu. For example, for the tree:
 *
 * <pre>
 *       A
 *      / \
 *     B   C
 *        / \
 *       D   E
 * </pre>
 *
 * Person A originally got the flu, B and C caught the flu from A, and<br>
 * D and E caught the flu from C.
 *
 * Important note: The name of each person in the flu tree is unique. Check whether two Person's are
 * the same by calling method equals. */
public class FluTree {

    /** Replace "-1" by the time you spent on A2 in hours.<br>
     * Example: for 3 hours 15 minutes, use 3.25<br>
     * Example: for 4 hours 30 minutes, use 4.50<br>
     * Example: for 5 hours, use 5 or 5.0 */
    public static double timeSpent= 6;

    /** The String to be used as a separator in toString() */
    public static final String SEPARATOR= " - ";

    /** The String that marks the start of children in toString() */
    public static final String START_CHILDREN_DELIMITER= "[";

    /** The String that divides children in toString() */
    public static final String DELIMITER= ", ";

    /** The String that marks the end of children in toString() */
    public static final String END_CHILDREN_DELIMITER= "]";

    /** The String that is the space increment in toStringVerbose() */
    public static final String VERBOSE_SPACE_INCREMENT= "\t";

    /** The person at the root of this FluTree. <br>
     * i.e. the person in this node of the FluTree. <br>
     * This field is never null.<br>
     * This is the flu ancestor of everyone in this FluTree: <br>
     * the person who got sick first and indirectly caused everyone in it to get sick. <br>
     * All Person's in a FluTree have different names. There are no duplicates */
    private Person root;

    /** The children of this FluTree node. <br>
     * Each element of this set got the flu from the person at this node. <br>
     * It is the empty set if this node is a leaf. For each child,<br>
     * its root is non-null. */
    private Set<FluTree> children;

    /** Constructor: a new FluTree with root p and no children. <br>
     * Throw an IllegalArgumentException if p is null. */
    public FluTree(Person p) throws IllegalArgumentException {
        if (p == null)
            throw new IllegalArgumentException("Can't construct FluTree with null root");
        root= p;
        children= new HashSet<>();
    }

    /** Constructor: a new FluTree that is a copy of tree p. <br>
     * Tree p and its copy have no node in common (but nodes can share a Person). <br>
     * Throw an IllegalArgumentException if p is null. */
    public FluTree(FluTree p) throws IllegalArgumentException {
        if (p == null)
            throw new IllegalArgumentException("Can't construct copy of null FluTree");
        root= p.root;
        children= new HashSet<>();

        for (FluTree dt : p.children) {
            children.add(new FluTree(dt));
        }
    }

    /** = the person that is at the root of this FluTree. */
    public Person rootPerson() {
        return root;
    }

    /** = the number of direct children of this FluTree. */
    public int childrenSize() {
        return children.size();
    }

    /** = a COPY of the set of children of this FluTree. */
    public Set<FluTree> copyOfChildren() {
        return new HashSet<>(children);
    }

    /** = the FluTree object in this tree whose root is p. <br>
     * (null if p is not in this tree). */
    public FluTree node(Person p) {
        if (root == p) return this; // Base case

        // Recursive case - Return the node with Person p if a child contains it.
        for (FluTree dt : children) {
            FluTree node= dt.node(p);
            if (node != null) return node;
        }

        return null; // p is not in the tree
    }

    /** Insert c in this FluTree as a child of p and return <br>
     * the FluTree object whose root is the new child. <br>
     * Throw an IllegalArgumentException if:<br>
     * -- c or p is null, or<br>
     * -- c is already in this FluTree, or<br>
     * -- p is not in this FluTree. */
    public FluTree insert(Person c, Person p) throws IllegalArgumentException {
        // TODO 1
        // This method should not be recursive.
        // Use method node(), above, and use no methods that are below.
        // DO NOT traverse the tree twice looking for the same node
        // ---don't duplicate work.
        if (p == null || c == null) throw new IllegalArgumentException();
        if (node(c) != null) throw new IllegalArgumentException();
        FluTree parentTree= node(p);
        if (parentTree == null) throw new IllegalArgumentException();
        FluTree childTree= new FluTree(c);
        childTree.root= c;
        parentTree.children.add(childTree);
        return childTree;
    }

    /** = the number of nodes in this FluTree. <br>
     * Note: If this is a leaf, the size is 1 (just the root) */
    public int size() {
        // TODO 2. This method must be recursive.
        // State whether this is a searching or a counting method: counting

        int sum= 1;
        for (FluTree child : children) {
            sum+= child.size();
        }
        return sum;
    }

    /** = "this flu tree contains a node with person p." */
    public boolean contains(Person p) {
        // TODO 3
        // Note: This may be the first method you write that may test whether two
        // Persons r and p are equal. For all such tests, always use function equals:
        //
        // r.equals(p)
        //
        // If you write this recursively,
        // state whether this is a searching or a counting method: search
        //
        // Recursive definition: This FluTree contains p iff the root of
        // this FluTree is p or if one of p's children contains p.

        if (root.equals(p)) return true;
        for (FluTree child : children) {
            if (child.contains(p)) return true;
        }
        return false;
    }

    /** = the depth at which p occurs in this FluTree, or <br>
     * -1 if p is not in the FluTree. <br>
     * Note: depth(root) is 0. <br>
     * If p is a child of this FluTree node, then depth(p) is 1. etc. Note: if p is null, it is
     * obviously NOT in the tree, but DO NOT<br>
     * put in a special test for it; it would be unnecessary case analysis. */
    public int depth(Person p) {
        // TODO 4
        // Note: Do NOT call function contains(p) to find out whether p is in
        // the tree. Do not have unnecessary calls that traverse the tree.
        // Study the two tutorials on processing recursive calls and use the
        // correct pattern here.
        // State whether this is a searching or a counting method: counting

        // Here is recursive insight:
        // ... Let child c of the root contain p.
        // ... Then the depth of p in the root = (1 + depth of p in c)

        if (root.equals(p)) return 0;

        for (FluTree child : children) {
            int childDepth= child.depth(p);
            if (childDepth != -1) { return 1 + childDepth; }
        }

        return -1;
    }

    /** Return the width of this tree at depth d <br>
     * (i.e. the number of nodes that occur at depth d).<br>
     * Throw an IllegalArgumentException if d < 0.<br>
     * Thus, for the following tree:<br>
     *
     * <pre>
      * Depth  level:
     *  0         A
     *           / \
     *  1       B   C
     *         /   / \
     *  2     D   E   F
     *             \
     *  3           G
     * </pre>
     *
     * A.widthAtDepth(0) = 1, A.widthAtDepth(1) = 2,<br>
     * A.widthAtDepth(2) = 3, A.widthAtDepth(3) = 1, A.widthAtDepth(4) = 0.<br>
     * C.widthAtDepth(0) = 1, C.widthAtDepth(1) = 2, C.widthAtDepth(1) = 2 */
    public int widthAtDepth(int d) throws IllegalArgumentException {
        // TODO 5
        // It may help to read Piazza note @567, Stepwise refinement, when developing widthAtDepth.
        // Do not have calls that unnecessarily traverse the tree, causing the tree to be
        // traversed more than once.
        // State whether this is a searching or a counting method: counting

        // Use this recursive definition:
        // ..... If d = 0, the answer is 1.
        // ..... If d > 0, the answer is: sum of widths of the children at depth d-1.
        if (d < 0) throw new IllegalArgumentException();
        if (d == 0) return 1;
        int sum= 0;
        for (FluTree child : children) {
            sum+= child.widthAtDepth(d - 1);
        }
        return sum;

    }

    /** Return the route the flu took to get from "here" (the root of <br>
     * this FluTree) to descendent c. If there is no such route, return null.<br>
     * For example, for this tree:<br>
     *
     * <pre>
     * Depth:
     *    0           A
     *               / \
     *    1         B   C
     *             /   / \
     *    2       D   E   F
     *             \
     *    3         G
     * </pre>
     *
     * A.fluRouteTo(E) returns the list [A,C,E].<br>
     * A.fluRouteTo(A) returns the list [A]. <br>
     * A.fluRouteTo(X) returns null.<br>
     * B.fluRouteTo(C) returns null. <br>
     * B.fluRouteTo(D) returns the list [B,D] */
    public List<Person> fluRouteTo(Person c) {
        // TODO 6
        // 0. Calling getParent will give you 0 for this method. It is too inefficient,
        // requiring possible many traversals of the tree.
        //
        // 1. The method must return a List<Person> object. But List is an interface, so
        // use something that implements it. LinkedList<Person> is preferred to ArrayList<Person>,
        // because prepend (or its equivalent) may have to be used.

        // 2. The ONLY case in which this method must create a list object (using
        // a new expression new ArrayList<>() or new LinkedList<>()) is the base
        // case, when c is the root. Do NOT create a new list object using a new-expression
        // in the recursive case.

        // 3. If c is null, there is no route to it, so null should be returned. But do NOT
        // put in a special test for this case; it is unnecessary case analysis.

        // 4. Base Case: The root of this FluTree is c; i.e. the Route is just [c].

        // State whether this is a searching or a counting method: searching

        if (c.equals(root)) {
            List<Person> base= new LinkedList<>();
            base.add(c);
            return base;
        }

        for (FluTree child : children) {
            List<Person> path= child.fluRouteTo(c);
            if (path != null) {
                path.add(0, root);
                return path;
            }
        }

        return null;
    }

    /** If either child1 or child2 is null or is not in this FluTree, return null.<br>
     * Otherwise, return the person at the root of the smallest subtree of this<br>
     * FluTree that contains child1 and child2.<br>
     *
     * Examples. For the following tree (which does not contain H):
     *
     * <pre>
     * Depth:
     *    0      A
     *          / \
     *    1    B   C
     *        /   / \
     *    2  D   E   F
     *        \
     *    3    G
     * </pre>
     *
     * A.commonAncestor(B, A) is A<br>
     * A.commonAncestor(B, B) is B<br>
     * A.commonAncestor(B, C) is A<br>
     * A.commonAncestor(A, C) is A<br>
     * A.commonAncestor(E, F) is C<br>
     * A.commonAncestor(G, F) is A<br>
     * B.commonAncestor(B, E) is null<br>
     * B.commonAncestor(B, A) is null<br>
     * B.commonAncestor(D, F) is null<br>
     * B.commonAncestor(D, H) is null<br>
     * A.commonAncestor(null, C) is null */
    public Person commonAncestor(Person child1, Person child2) {
        // TODO 7
        /* Do not use method getParent(), which is far below. Its use over and over again
         * is inefficient. Calling getParent results in a grade of 0 for this method.
         *
         * Instead, find the flu routes l1 and l2 to the two children. If both are not
         * null, then two things are known:
         * (1) l1[0] = l2[0]
         * (2) the answer l1[i] is the largest i such that l1[0..i] = l2[0..i].
         * If this is not clear, draw an example.
         * The answer, then, can be found using a loop. No recursion is needed.
         *
         * You have a problem of writing this loop efficiently. You can't use a foreach loop
         * on both lists simultaneously. The simplest thing to do is to use List's
         * function toArray twice and then work with the array representations of the lists. */
        List<Person> l1= fluRouteTo(child1);
        List<Person> l2= fluRouteTo(child2);
        if (l1 == null || l2 == null) return null;

        int minLength= Math.min(l1.size(), l2.size());

        for (int i= 1; i < minLength; i++ ) {
            if (!l1.get(i).equals(l2.get(i))) { return l1.get(i - 1); }
        }

        return l1.get(minLength - 1);
    }

    /** Return true iff this is equal to ob.<br>
     * 1. If this and ob are not of the same class, they are not equal, so return false.<br>
     * 2. Two FluTrees are equal if<br>
     * -- (1) they have the same root Person object (use ==) AND<br>
     * -- (2) their children sets are the same size AND<br>
     * -- (3) their children sets are equal.<br>
     * ------ Let the two children sets be s1 and s2. ------ Since their sizes are equal, this
     * requires:<br>
     * -------- for every FluTree t1 in s1 there is a FluTree<br>
     * -------- t2 in s2 for which t1.equals(t2) is true.<br>
     *
     * -- Otherwise the two FluTrees are not equal.<br>
     * Do not use any of the toString functions to write equals(). <br>
     * Do not use Set's functions equals, contains, or containsAll. */
    @Override
    public boolean equals(Object ob) {
        // TODO 8
        // The specification outlines what must be done in detail. Two points to discuss:
        // (1) Stay away from nested ifs as much as possible! Instead, as soon as it is
        // determined that a property for equality is not met (or is met), return false (or true)!
        // So the structure could be:

        // if (property 1 not met) return false;
        // if (property 2 not met) return false;
        // etc.

        // (2) The difficult part is testing this:
        // ... for every FluTree t1 in s1, there is a FluTree t2
        // ... in s2 for which t1.equals(t2) is true.
        //
        // We suggest you write a helper method with this specification:
        // /** Return true iff t1 equals some member of s2 */
        // private boolean help(FluTree t1, Set<FluTree> s2)
        //
        // Note that this method is going to call equals, like this: s2.equals(...).
        // That is a call to the method you are trying to write! We have
        // mutual recursion: equals calls help, which calls equals, which call help ...
        // But when thinking about what a call does, USE THE SPECIFICATION to understand
        // what it does.
        //
        // Hint about checking whether each child of one tree equals SOME
        // tree of the other tree's children.
        // First, you have to check them all until you find an equal one (or
        // return false if you don't.)
        // Second, A child of one tree cannot equal more than one child of
        // tree because the names of Person's are all unique;
        // there are no duplicates.
        if (ob == null || ob.getClass() != getClass()) { return false; }
        FluTree f= (FluTree) ob;
        if (!root.equals(f.root)) return false;
        if (childrenSize() != f.childrenSize()) return false;
        for (FluTree child : children) {
            if (!f.containsTree(child)) return false;
        }
        return true;

    }

    /** Return true iff this FluTree contains some object child<br>
     * in children such that child.equals(target) */
    private boolean containsTree(FluTree target) {
        for (FluTree child : children) {
            if (child.equals(target)) return true;
        }

        return false;
    }

    /* ========================================================================
     * ========================================================================
     * ========================================================================
     * Do not use the methods written below. They are used to calculate data
     * for the GUI. Feel free to read/study them. */

    /** Return the maximum depth of this FluTree, <br>
     * i.e. the longest path from the root to a leaf.<br>
     * Example. If this FluTree is a leaf, return 0. */
    public int maxDepth() {
        int maxDepth= 0;
        for (FluTree dt : children) {
            maxDepth= Math.max(maxDepth, dt.maxDepth() + 1);
        }
        return maxDepth;
    }

    /** Return the immediate parent of c (null if c is not in this FluTree).<br>
     * Thus, for the following tree:
     *
     * <pre>
     * Depth:
     *    0      A
     *          / \
     *    1    B   C
     *        /   / \
     *    2  D   E   F
     *        \
     *    3    G
     * </pre>
     *
     * A.getParent(E) returns C.<br>
     * C.getParent(E) returns C.<br>
     * A.getParent(B) returns A.<br>
     * E.getParent(F) returns null. */
    public Person getParent(Person c) {
        // Base case
        for (FluTree dt : children) {
            if (dt.root == c) return root;
        }

        // Recursive case - ask children to look
        for (FluTree dt : children) {
            Person parent= dt.getParent(c);
            if (parent != null) return parent;
        }

        return null; // Not found
    }

    /** Return the maximum width of all the widths in this tree, <br>
     * i.e. the maximum value that could be returned from widthAtDepth for this tree. */
    public int maxWidth() {
        return maxWidthImplementationTwo(this);
    }

    /** Simple implementation of maxWidth. <br>
     * Relies on widthAtDepth. <br>
     * Takes time proportional to the square of the size of the t. */
    static int maxWidthImplementationOne(FluTree t) {
        int width= 0;
        int depth= t.maxDepth();
        for (int i= 0; i <= depth; i++ ) {
            width= Math.max(width, t.widthAtDepth(i));
        }
        return width;
    }

    /** Better implementation of maxWidth. Caches results in an array. <br>
     * Takes time proportional to the size of t. */
    static int maxWidthImplementationTwo(FluTree t) {
        // For each integer d, 0 <= d <= maximum depth of t, store in
        // widths[d] the number of nodes at depth d in t.
        // The calculation is done by calling recursive procedure addToWidths.
        int[] widths= new int[t.maxDepth() + 1];   // initially, contains 0's
        t.addToWidths(0, widths);

        int max= 0;
        for (int width : widths) {
            max= Math.max(max, width);
        }
        return max;
    }

    /** For each node of this FluTree, which is at some depth d in this FluTree,<br>
     * add 1 to widths[depth + d]. */
    private void addToWidths(int depth, int[] widths) {
        widths[depth]++ ;        // the root of this FluTree is at depth d = 0
        for (FluTree dt : children) {
            dt.addToWidths(depth + 1, widths);
        }
    }

    /** Better implementation of maxWidth. Caches results in a HashMap. <br>
     * Takes time proportional to the size of t. */
    static int maxWidthImplementationThree(FluTree t) {
        // For each possible depth d >= 0 in tree t, widthMap will contain the
        // entry (d, number of nodes at depth d in t). The calculation is
        // done using recursive procedure addToWidthMap.

        // For each integer d, 0 <= d <= maximum depth of t, add to
        // widthMap an entry <d, 0>.
        HashMap<Integer, Integer> widthMap= new HashMap<>();
        for (int d= 0; d <= t.maxDepth() + 1; d++ ) {
            widthMap.put(d, 0);
        }

        t.addToWidthMap(0, widthMap);

        int max= 0;
        for (Integer w : widthMap.values()) {
            max= Math.max(max, w);
        }
        return max;
    }

    /** For each node of this FluTree, which is at some depth d in this FluTree,<br>
     * add 1 to the value part of entry <depth + d, ...> of widthMap. */
    private void addToWidthMap(int depth, HashMap<Integer, Integer> widthMap) {
        widthMap.put(depth, widthMap.get(depth) + 1);  // the root is at depth d = 0
        for (FluTree dt : children) {
            dt.addToWidthMap(depth + 1, widthMap);
        }
    }

    /** Return a (single line) String representation of this FluTree.<br>
     * If this FluTree has no children (it is a leaf), return the root's substring.<br>
     * Otherwise, return<br>
     * ... root's substring + SEPARATOR + START_CHILDREN_DELIMITER + each child's<br>
     * ... toString, separated by DELIMITER, followed by END_CHILD_DELIMITER.<br>
     *
     * Make sure there is not an extra DELIMITER following the last child.<br>
     *
     * Finally, make sure to use the static final fields declared at the top of<br>
     * FluTree.java.<br>
     *
     * Thus, for the following tree:
     *
     * <pre>
     * Depth level:
     *   0         A
     *            / \
     *   1        B  C
     *           /  / \
     *   2      D  E   F
     *           \
     *   3        G
     *
     * A.toString() should print:
     * (A) - HEALTHY - [(C) - HEALTHY - [(F) - HEALTHY, (E) - HEALTHY - [(G) - HEALTHY]], (B) - HEALTHY - [(D) - HEALTHY]]
     *
     * C.toString() should print:
     * (C) - HEALTHY - [(F) - HEALTHY, (E) - HEALTHY - [(G) - HEALTHY]]
     * </pre>
     */
    @Override
    public String toString() {
        if (children.isEmpty()) return root.toString();
        String s= root.toString() + SEPARATOR + START_CHILDREN_DELIMITER;
        for (FluTree dt : children) {
            s= s + dt.toString() + DELIMITER;
        }
        return s.substring(0, s.length() - 2) + END_CHILDREN_DELIMITER;
    }

    /** Return a verbose (multi-line) string representing this FluTree. */
    public String toStringVerbose() {
        return toStringVerbose(0);
    }

    /** Return a verbose (multi-line) string representing this FluTree.<br>
     * Each human in the tree is on its own line, with indentation representing<br>
     * what each human is a child of.<br>
     * indent is the the amount of indentation to put before this line.<br>
     * Should increase on recursive calls to children to create the above pattern.<br>
     * Thus, for the following tree:
     *
     * <pre>
     * Depth level:
     *   0         A
     *            / \
     *   1       B   C
     *          /   / \
     *   2     D   E   F
     *          \
     *   3       G
     *
     * A.toStringVerbose(0) should return:
     * (A) - HEALTHY
     * (C) - HEALTHY
     * (F) - HEALTHY
     * (E) - HEALTHY
     * (G) - HEALTHY
     * (B) - HEALTHY
     * (D) - HEALTHY
     * </pre>
     *
     * Make sure to use VERBOSE_SPACE_INCREMENT for indentation. */
    private String toStringVerbose(int indent) {
        String s= "";
        for (int i= 0; i < indent; i++ ) {
            s= s + VERBOSE_SPACE_INCREMENT;
        }
        s= s + root.toString();

        if (children.isEmpty()) return s;

        for (FluTree dt : children) {
            s= s + "\n" + dt.toStringVerbose(indent + 1);
        }
        return s;
    }

}
