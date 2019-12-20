

import static org.junit.Assert.*;

import java.util.*;
import java.util.function.BiPredicate;

import org.junit.Test;

import java.util.Random;

public class StudentTest {

    @Test
    /**
     * Start with this test. Make appropriate changes to BinarySearchTree.Node.
     */
    public void nodeProperties() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        assertTrue(bst.isEmpty());
        BinarySearchTree<Integer>.Node p;
        p = bst.new Node(5);
        assertNull(p.left);
        assertNull(p.right);
        assertNull(p.parent);
        assertTrue(5 == p.data);
        assertTrue(5 == p.get());
        assertEquals(0, p.height);
        assertTrue(p.isLeaf());
        p = bst.new Node(6, p, null);
        assertTrue(6 == p.data);
        assertTrue(6 == p.get());
        p.updateHeight();
        assertEquals(1, p.height);
        p = bst.new Node(7, null, p);
        assertTrue(7 == p.data);
        assertTrue(7 == p.get());
        p.updateHeight();
        assertEquals(2, p.height);
        p.left = bst.new Node(8);
        assertTrue(8 == p.left.data);
        assertTrue(8 == p.left.get());
        p.updateHeight();
        assertEquals(2, p.height);
        p.left.left = p.right;
        p.left.updateHeight();
        assertEquals(2, p.height);
        p.updateHeight();
        assertEquals(3, p.height);
    }

    @Test
    /**
     * When you're ready to run this test, remove the // from the above line.
     */
    public void insertSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        assertTrue(bst.isEmpty());
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        int n = 0;
        for (Integer key : a) {
            bst.insert(key);
            n++;
            assertEquals(n, bst.size());
        }
        /**
         *       4
         *     /  \
         *    /    \
         *   0      8
         *    \    / \
         *     2  6   10
         */
        assertTrue(4 == bst.root.data);
        assertTrue(0 == bst.root.left.data);
        assertTrue(2 == bst.root.left.right.data);
        assertTrue(8 == bst.root.right.data);
        assertTrue(6 == bst.root.right.left.data);
        assertTrue(10 == bst.root.right.right.data);

        assertEquals(2, bst.height());
        assertTrue(verifyParentPointers(bst.root));
    }

    @Test
    public void clearSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        assertTrue(bst.isEmpty());
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        for (Integer key : a)
            bst.insert(key);
        bst.clear();
        assertEquals(0, bst.size());
        assertEquals(-1, bst.height());
        assertNull(bst.root);
        for (Integer key : a)
            bst.insert(key);
        assertEquals(a.length, bst.size());
        assertNotNull(bst.root);
        assertEquals(2, bst.height());
    }

    @Test
    public void insertReturnBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        assertTrue(bst.isEmpty());
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        for (Integer key : a)
            assertEquals(key, bst.insert(key).get());
        assertEquals(2, bst.height());
    }

    @Test
    public void skinnyBST() {
        BinarySearchTree<String> bst =
                new BinarySearchTree<>((String x, String y) -> x.compareTo(y) < 0);
        String[] a = new String[]{"ape", "boa", "cat", "dog", "emu", "fox", "gnu", "hog"};
        for (String key : a)
            bst.insert(key);
        assertEquals("ape", bst.root.data);
        assertEquals("boa", bst.root.right.data);
        assertEquals("cat", bst.root.right.right.data);
        assertEquals("dog", bst.root.right.right.right.data);
        assertEquals("emu", bst.root.right.right.right.right.data);
        assertEquals("fox", bst.root.right.right.right.right.right.data);
        assertEquals("gnu", bst.root.right.right.right.right.right.right.data);
        assertEquals("hog", bst.root.right.right.right.right.right.right.right.data);
        assertEquals(7, bst.height());
    }

    @Test
    public void parentsSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        for (Integer key : a)
            bst.insert(key);
        assertTrue(null == bst.root.parent);
        assertTrue(bst.root == bst.root.left.parent);
        assertTrue(bst.root.left == bst.root.left.right.parent);
        assertTrue(bst.root == bst.root.right.parent);
        assertTrue(bst.root.right == bst.root.right.left.parent);
        assertTrue(bst.root.right == bst.root.right.right.parent);
    }

    @Test
    public void searchSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        for (Integer key : a)
            bst.insert(key);
        Location<Integer> loc;
        for (Integer key : a) {
            assertTrue(bst.contains(key));
            assertFalse(bst.contains(key + 1));
            loc = bst.search(key);
            assertNotNull(loc);
            assertEquals(key, loc.get());
            loc = bst.search(key + 1);
            assertNull(bst.search(key + 1));
        }
    }

    @Test
    public void heightSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        for (Integer key : a) {
            bst.insert(key);
            assertTrue(verifyHeights(bst.root));
        }
        bst.insert(7);
        assertTrue(verifyHeights(bst.root));
    }

    @Test
    public void removeSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        int[] a = {4, 8, 0, 2, 6, 10};
        for (Integer key : a)
            bst.insert(key);
        int size = a.length;
        for (Integer key : a) {
            bst.remove(key);
            size--;
            assertEquals(size, bst.size());
            assertFalse(bst.contains(key));
            // Removing a key not in the tree should do nothing.
            bst.remove(key + 1);
            assertEquals(size, bst.size());
            assertFalse(bst.contains(key));
            bst.remove(key);
            assertEquals(size, bst.size());
            assertFalse(bst.contains(key));
        }
        assertTrue(bst.isEmpty());
        assertTrue(verifyParentPointers(bst.root));
    }

    @Test
    public void dupsSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        for (Integer key : a)
            bst.insert(key);
        // Attempts to insert a duplicate key should be ignored.
        for (Integer key : a) {
            bst.insert(key);
            assertEquals(6, bst.size());
        }
        // Attempts to insert a previously removed key should reuse the dirty node.
        int size = a.length, dirtyCount = 0;
        for (Integer key : a) {
            bst.remove(key);
            size--;
            dirtyCount++;
            assertEquals(size, bst.size());
            assertFalse(bst.contains(key));
        }
        assertTrue(bst.isEmpty());
        size = 0;
        for (Integer key : a) {
            bst.insert(key);
            dirtyCount--;
            size++;
            assertEquals(size, bst.size());
        }
    }

    @Test
    public void keysSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        for (Integer key : a)
            bst.insert(key);
        List<Integer> ks = bst.keys();
        assertEquals(a.length, ks.size());
        for (int i = 1; i < ks.size(); i++)
            assertTrue(ks.get(i - 1) <= ks.get(i));
        bst.clear();
        assertTrue(bst.keys().isEmpty());
    }

    @Test
    public void beforeBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        assertTrue(bst.isEmpty());
        int[] a = new int[]{4, 5, 8, 9, 3, 2, 7, 6, 1};
        int n = a.length;
        for (Integer key : a)
            bst.insert(key);
        assertNull(bst.search(1).getBefore());
        for (int i = 2; i <= n; i++)
            assertTrue(i - 1 == bst.search(i).getBefore().get());
    }

    @Test
    public void afterBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        assertTrue(bst.isEmpty());
        int[] a = new int[]{4, 5, 8, 9, 3, 2, 7, 6, 1};
        int n = a.length;
        for (Integer key : a)
            bst.insert(key);
        for (int i = 1; i < n; i++)
            assertTrue(i + 1 == bst.search(i).getAfter().get());
        assertNull(bst.search(n).getAfter());
    }

    @Test
    public void bigBeforeBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        Random gen = new Random();
        List<Integer> a = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            a.add(gen.nextInt(i + 1), i);
        for (Integer x : a) {
            bst.insert(x);
        }
        a.sort((Integer x, Integer y) -> x < y ? -1 : (x == y) ? 0 : 1);
        BinarySearchTree<Integer>.Node n = bst.search(a.get(a.size() - 1));
        for (int i = a.size() - 1; i != -1; --i) {
            assertEquals(a.get(i), n.get());
            n = n.getBefore();
        }
    }

    @Test
    public void bigAfterBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        Random gen = new Random();
        List<Integer> a = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            a.add(gen.nextInt(i + 1), i);
        for (Integer x : a) {
            bst.insert(x);
        }
        a.sort((Integer x, Integer y) -> x < y ? -1 : (x == y) ? 0 : 1);
        BinarySearchTree<Integer>.Node n = bst.search(a.get(0));
        for (int i = 0; i != a.size(); ++i) {
            assertEquals(a.get(i), n.get());
            n = n.getAfter();
        }
    }

    /**********************************************************************************
     * Add test cases for insert and remove on a tree with 100 randomly generated keys.
     */

    /**********************************************************************************/

    private <K> int get_height(BinarySearchTree<K>.Node p) {
        if (p == null) return -1;
        else return p.height;
    }

    private <K> boolean verifyHeights(BinarySearchTree<K>.Node p) {
        if (p == null)
            return true;
        int h1 = get_height(p.left);
        int h2 = get_height(p.right);
        return p.height == 1 + Math.max(h1, h2) &&
                verifyHeights(p.left) && verifyHeights(p.right);
    }

    private <K> boolean verifyAVL(BinarySearchTree<K>.Node p) {
        if (p == null)
            return true;
        int h1 = get_height(p.left);
        int h2 = get_height(p.right);
        if (Math.abs(h1 - h2) > 1) {
            System.out.println("Not AVL");
            System.out.println(p);
            return false;
        } else
            return verifyAVL(p.left) && verifyAVL(p.right);
    }

    private <K> boolean verifyParentPointers(BinarySearchTree<K>.Node root) {
        if (root == null)
            return true;
        else if (root.parent != null)
            return false;
        else
            return verifyParentPointersHelper(root, root.left) &&
                verifyParentPointersHelper(root, root.right);
    }

    private <K> boolean verifyParentPointersHelper(BinarySearchTree<K>.Node p, BinarySearchTree<K>.Node q) {
        if (q == null)
            return true;
        else if (q.parent != p)
            return false;
        else
            return verifyParentPointersHelper(q, q.left) && verifyParentPointersHelper(q, q.right);
    }

    private <K>
    boolean verifyOrderingProperty(BinarySearchTree<K>.Node p, BiPredicate<K, K> lessThan) {
        if (p == null) return true;
        K key = p.data;
        return allLessThan(p.left, key, lessThan) && allGreaterThan(p.right, key, lessThan)
                && verifyOrderingProperty(p.left, lessThan) && verifyOrderingProperty(p.right, lessThan);
    }

    private <K>
    boolean allGreaterThan(BinarySearchTree<K>.Node p, K x, BiPredicate<K, K> lessThan) {
        if (p == null) return true;
        return !lessThan.test(p.data, x)
                && allGreaterThan(p.left, x, lessThan) && allGreaterThan(p.right, x, lessThan);
    }

    private <K>
    boolean allLessThan(BinarySearchTree<K>.Node p, K x, BiPredicate<K, K> lessThan) {
        if (p == null) return true;
        return lessThan.test(p.data, x)
                && allLessThan(p.left, x, lessThan) && allLessThan(p.right, x, lessThan);
    }

}
