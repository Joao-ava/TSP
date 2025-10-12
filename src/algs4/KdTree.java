package algs4;

import java.util.Comparator;

/**
 * Estrutura KdTree a ser implementada pelo aluno.
 */
public class KdTree {
    class Node {
        Node left;
        Node right;
        Point2D value;

        Node(Point2D value) {
            this.value = value;
        }
    }

    Node root;
    private int itemsCount;

    public KdTree() {
        itemsCount = 0;
    }

    public boolean isEmpty() {
        return itemsCount == 0;
    }

    public int size() {
        return itemsCount;
    }

    public void insert(Point2D p) {
        if (itemsCount == 0) {
            this.root = new Node(p);
            itemsCount++;
            return;
        }
        insert(p, root, true);
        itemsCount++;
    }

    private void insert(Point2D p, Node n, boolean isX) {
        Point2D value = n.value;
        Comparator<Point2D> comparator = isX ? Point2D.X_ORDER : Point2D.Y_ORDER;
        if (comparator.compare(value, p) > 0) {
            if (n.right != null) {
                insert(p, n.right, !isX);
                return;
            }
            n.right = new Node(p);
            return;
        }
        if (n.left != null) {
            insert(p, n.left, !isX);
            return;
        }
        n.left = new Node(p);
    }

    public boolean contains(Point2D p) {
        return contains(p, root, true);
    }

    private boolean contains(Point2D p, Node n, boolean isX) {
        Point2D value = n.value;
        if (value.equals(p)) return true;
        Comparator<Point2D> comparator = isX ? Point2D.X_ORDER : Point2D.Y_ORDER;
        if (comparator.compare(value, p) > 0) {
            if (n.right == null) return false;
            return contains(p, n.right, !isX);
        }
        if (n.left == null) return false;
        return contains(p, n.left, !isX);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point is null");
        if (root == null) return null;
        return nearest(p, root, true, root.value);
    }

    private Point2D nearest(Point2D p, Node n, boolean isX, Point2D best) {
        if (n == null) return best;

        Point2D value = n.value;

        double bestDist = p.distanceSquaredTo(best);
        double nodeDist = p.distanceSquaredTo(value);
        if (nodeDist < bestDist) {
            best = value;
        }

        Comparator<Point2D> comparator = isX ? Point2D.X_ORDER : Point2D.Y_ORDER;
        Node nextNode;
        Node otherNode;
        if (comparator.compare(value, p) > 0) {
            nextNode = n.right;
            otherNode = n.left;
        } else {
            nextNode = n.left;
            otherNode = n.right;
        }

        best = nearest(p, nextNode, !isX, best);
        bestDist = p.distanceSquaredTo(best);

        double delta = isX ? (p.x() - value.x()) : (p.y() - value.y());
        if (delta * delta < bestDist) {
            best = nearest(p, otherNode, !isX, best);
        }
        return best;
    }
}
