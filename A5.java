package heaps;

/* NetId(s): gec83

 * Name(s): Grace Campidilli
 * What I thought about this assignment:
 *      Definitely helped me to understand this datastructure more. Though I found it
 *      quite challenging.
 *
 */

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;

/** An instance is a max-heap or a min-heap of distinct values of type T <br>
 * with priorities of type double. */
public class Heap<T> {

    /** Replace "-1" by the time you spent on A2 in hours.<br>
     * Example: for 3 hours 15 minutes, use 3.25<br>
     * Example: for 4 hours 30 minutes, use 4.50<br>
     * Example: for 5 hours, use 5 or 5.0 */
    public static double timeSpent= 8.0;

    /** Class Invariant: <br>
     * 1. c[0..size-1] represents a complete binary tree.<br>
     * c[0] is the root; <br>
     * For k > 0, (k-1)/2 (using int division) is the index in c of the parent of c[k]<br>
     * For k >= 0, 2k+1 and 2k+2 are the indexes in c of left and right children of c[k].<br>
     *
     * 2. For k in 0..size-1, c[k] contains the value and its priority. Item object in the heap:
     * (value, priority)
     *
     * 3. The values in c[0..size-1] are all different.
     *
     * sort by priority, not value 4. For k in 1..size-1, <br>
     * .. if isMinHeap, (c[k]'s priority) >= (c[k]'s parent's priority),<br>
     * .. if !isMinHeap, (c[k]'s priority) <= (c[k]'s parent's priority).
     *
     * map and the tree are in sync, meaning:
     *
     * 5. The keys of map are the values in c[0..size-1]. This implies that size = map.size().
     *
     * map.get(value v) returns index of item object with value v 6. if value v is in c[k], then
     * map.get(v) = k. */
    protected final boolean isMinHeap;
    protected Item[] c;
    protected int size;
    protected HashMap<T, Integer> map;

    /** Constructor: an empty heap with capacity 10. <br>
     * It is a min-heap if isMin is true and a max-heap if isMin is false. */
    public Heap(boolean isMin) {
        isMinHeap= isMin;
        c= createItemArray(10);
        map= new HashMap<>();
    }

    /** Add v with priority p to the heap. <br>
     * Throw an illegalArgumentException if v is already in the heap. <br>
     * The expected time is logarithmic and <br>
     * the worst-case time is linear in the size of the heap. */
    public void add(T v, double p) throws IllegalArgumentException {
        // TODO #1: Write this whole method. Note that bubbleUp is not implemented,
        // so calling it has no effect (yet). The first tests of add, using
        // test00Add, ensure that this method maintains fields c and map properly,
        // without worrying about bubbling up.

        // Testing procedures test00Add and test01AddException should work.
        // Look at their specifications.

        // Do NOT call bubbleUp until the class invariant is true
        // (except for the need to bubble up).
        // Calling bubbleUp is the last thing to be done.
        if (map.containsKey(v)) {
            throw new IllegalArgumentException("Item with value v is already in the heap");
        }

        Item newElement= new Item(v, p);

        fixCapacity();
        map.put(v, map.size());
        size++ ;
        c[map.get(v)]= newElement;

        // index of where map element with key 'v' is
        bubbleUp(map.get(v));

    }

    /** If size = length of c, double the length of array c. <br>
     * The worst-case time is proportional to the length of c. */
    protected void fixCapacity() {
        // TODO #2. We ask you to write this method because it shows you
        // how class ArrayList can "increase" the size of its backing array.
        // When its capacity is reached (its backing array is filled), it creates
        // an array of twice the size, copies the old array into it, and
        // uses the new array from then on.

        // Any method that increases the size of the heap must call
        // this method first. MAKE SURE YOU MODIFY METHOD ADD TO DO THIS!

        // After this method is written, these testing procedures should work:
        // test10fixCapacity, test11fixCapacity, and test12fixCapacity

        // The body is most easily written using a method in Collections Framework
        // class Arrays. Look for methods that copy arrays and choose a suitable one.

        // allocate space for new item in c and map
        if (size == c.length) {
            c= Arrays.copyOf(c, size * 2);
        }

    }

    /** Return the size of this heap. <br>
     * This operation takes constant time. */
    public int size() { // Do not change this method
        return size;
    }

    /** Swap c[h] and c[k]. <br>
     * Precondition: 0 <= h < heap-size, 0 <= k < heap-size. */
    void swap(int h, int k) {
        assert 0 <= h && h < size && 0 <= k && k < size;
        // TODO 3: When bubbling values up or down, two values,
        // say b[h] and b[k], will have to be swapped. At the same time,
        // the definition of map has to be maintained.
        // In order to always get this right, use method swap to do this.

        // When method swap is correct, testing procedure test13Swap
        // will find no errors.
        //
        // Read the Assignment A5 note about map.put(...).

        Item temp= c[k];
        map.put(c[h].value, k);
        map.put(temp.value, h);

        c[k]= c[h];
        c[h]= temp;

    }

    /** If a value with priority p1 belongs above a value with priority p2 in the heap, <br>
     * return 1.<br>
     * If priority p1 and priority p2 are the same, return 0. <br>
     * If a value with priority p1 should be below a value with priority p2 in the heap,<br>
     * return -1.<br>
     * This is based on what kind of a heap this is, <br>
     * ... E.g. a min-heap, the value with the smallest priority is in the root.<br>
     * ... E.g. a max-heap, the value with the largest priority is in the root. */
    public int compareTo(double p1, double p2) {
        if (p1 == p2) return 0;
        if (isMinHeap) { return p1 < p2 ? 1 : -1; }
        return p1 > p2 ? 1 : -1;
    }

    /** If c[h] should be above c[k] in the heap, return 1. <br>
     * If c[h]'s priority and c[k]'s priority are the same, return 0. <br>
     * If c[h] should be below c[k] in the heap, return -1. <br>
     * This is based on what kind of a heap this is, <br>
     * ... E.g. a min-heap, the value with the smallest priority is in the root. <br>
     * ... E.g. a max-heap, the value with the largest priority is in the root. */
    public int compareTo(int h, int k) {
        return compareTo(c[h].priority, c[k].priority);
    }

    /** If h >= size, return.<br>
     * Otherwise, bubble c[h] up the heap to its right place. <br>
     * Precondition: 0 <= h and, if h < size, <br>
     * ... the class invariant is true, except perhaps that <br>
     * ... c[h] belongs above its parent (if h > 0) in the heap. */
    void bubbleUp(int h) {
        // TODO #4 This method should be called within add in order
        // to bubble a value up to its proper place, based on its priority.

        // Do not use recursion. Use iteration.

        // Use the compareTo methods to test whether value h is in its right place.
        // That way, YOU don't have to worry about whether it is a min- or max-heap!

        // If this method is correct, these testing procedures should not find errors:
        // test15Add_BubbleUp(), test16addMaxHeap_BubbleUp,
        // test16addMaxHeap_BubbleUp, test17addMax_BubbleUpDuplicatePriorities
        if (h >= size) return;
        // Inv: 0 <= h < size and<br>
        // The class invariant is true, except perhaps<br>
        // that c[h] belongs above its parent (if h > 0) in the heap, not below it.

        while (Math.floor((h - 1) / 2) >= 0 &&
            compareTo(c[h].priority, c[(h - 1) / 2].priority) == 1) {
            swap(h, (h - 1) / 2);
            h= (h - 1) / 2;

        }

    }

    /** If this is a min-heap, return the heap value with lowest priority. <br>
     * If this is a max-heap, return the heap value with highest priority.<br>
     * Do not change the heap. <br>
     * This operation takes constant time. <br>
     * Throw a NoSuchElementException if the heap is empty. */
    public T peek() {
        // TODO 5: Do peek. This is an easy one.

        // If this method is correct, these testing procs will show no errors:
        // test25MaxPeek() and test25MinPeek
        if (!map.isEmpty()) { return c[0].value; }
        if (map.isEmpty()) { throw new NoSuchElementException("The heap is empty"); }

        return null;
    }

    /** If h < 0 or size <= h, return.<br>
     * Otherwise, Bubble c[h] down in heap until the class invariant is true. <br>
     * If there is a choice to bubble down to both the left and right children <br>
     * (because their priorities are equal), choose the left child. <br>
     *
     * Precondition: If 0 <= h < size, the class invariant is true except that <br>
     * perhaps c[h] belongs below one or both of its children. */
    void bubbleDown(int h) {
        // TODO 6: DO NOT USE RECURSION. Use iteration.
        // When this method is correct, these testing procedures will find no errors:
        // test30MinBubbledown, test31MaxBubble..., and test31MinBubble...
        if (0 > h || h >= size) return;

        int child= 2 * h + 1;

        while (child < size) {
            // set lesser child to whichever child has the lowest priority
            int lesserChild;
            if (child != size - 1) {
                lesserChild= child + 1 == size ||
                    compareTo(c[child].priority, c[child + 1].priority) == 1 ? child : child + 1;
            } else {
                lesserChild= child;
            }

            if (compareTo(c[h].priority, c[lesserChild].priority) != -1) { return; }
            swap(h, lesserChild);
            h= lesserChild;
            child= 2 * h + 1;
        }

    }

    /** If this is a min-heap, remove and return heap value with lowest priority. <br>
     * If this is a max-heap, remove and return heap value with highest priority. <br>
     * Expected time: logarithmic. Worst-case time: linear in the size of the heap.<br>
     * Throw a NoSuchElementException if the heap is empty. */
    public T poll() {
        // TODO 7: When this method correct, these testing procedure will find no errors:
        // test32Poll_BubbleDown_NoDups,
        // test33Poll, test34Poll, test40DuplicatePriorities
        if (map.isEmpty()) throw new NoSuchElementException("The heap is empty");

        // store topmost item
        Item a= c[0];
        // remove topmost item by its key
        map.remove(c[0].value);
        // decrease size of map
        size-- ;

        if (size > 1) {
            c[0]= c[c.length - 1];
            map.put(c[size].value, 0);
            // bubbleDown makes appropriate swaps for both map and c
            bubbleDown(0);
        } else { // if there are no more elements
            map.clear();

        }

        return a.value;

    }

    /** Change the priority of value v to p. <br>
     * Expected time: logarithmic. Worst-case time: linear in the size of the heap.<br>
     * Throw an IllegalArgumentException if v is not in the heap. */
    public void changePriority(T v, double p) {
        // TODO 8: When this method correct, these testing procedure will find no errors.
        // test50MaxchangePriority, test50MinchangePriority
        if (!map.containsKey(v)) throw new IllegalArgumentException();

        int index= map.get(v);

        Item oldP= c[index];
        c[index].priority= p;

        if (compareTo(oldP.priority, c[index].priority) == 1) {
            bubbleDown(index);
        } else if (compareTo(oldP.priority, c[index].priority) == -1) {
            bubbleUp(index);
        }

    }

    /** Return the heap values (only, not the priorities) in form [5, 3, 2]. */
    public String toStringValues() {
        StringBuilder resb= new StringBuilder("[");
        for (int h= 0; h < size; h= h + 1) {
            if (h > 0) resb.append(", ");
            resb.append(c[h].value);
        }
        return resb.append(']').toString();
    }

    /** Return the heap priorities in form [5.0, 3.0, 2.0]. */
    public String toStringPriorities() {
        StringBuilder resb= new StringBuilder("[");
        for (int h= 0; h < size; h= h + 1) {
            if (h > 0) resb.append(", ");
            resb.append(c[h].priority);
        }
        return resb.append(']').toString();
    }

    /** Create and return an array of size m. <br>
     * This is necessary because generics and arrays don't interoperate nicely. <br>
     * A student in CS2110 would not be expected to know about the need for this <br>
     * method and how to write it. <br>
     * We searched the web to find out how to do it. */
    Item[] createItemArray(int m) {
        return (Item[]) Array.newInstance(Item.class, m);
    }

    /** An object of class Item houses a value and a priority. */
    class Item {
        protected T value;             // The value
        protected double priority;   // The priority

        /** An instance with value v and priority p. */
        protected Item(T v, double p) {
            value= v;
            priority= p;
        }

        /** Return a representation of this object. */
        @Override
        public String toString() {
            return "(" + value + ", " + priority + ")";
        }

        /** = "this and ob are of the same class and have equal val and priority fields." */
        @Override
        public boolean equals(Object ob) {
            if (ob == null || getClass() != ob.getClass()) return false;
            Item obe= (Item) ob;
            return value == obe.value && priority == obe.priority;
        }
    }
}