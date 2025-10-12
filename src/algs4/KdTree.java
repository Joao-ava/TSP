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
        double compare = isX ? (p.x() - value.x()) : (p.y() - value.y());
        if (compare < 0) {
            // p < value, vai para a esquerda
            if (n.left != null) {
                insert(p, n.left, !isX);
                return;
            }
            n.left = new Node(p);
        } else {
            // p >= value, vai para a direita
            if (n.right != null) {
                insert(p, n.right, !isX);
                return;
            }
            n.right = new Node(p);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point is null");
        return contains(p, root, true);
    }

    private boolean contains(Point2D p, Node n, boolean isX) {
        if (n == null) return false;
        Point2D value = n.value;
        if (value.equals(p)) return true;

        double compare = isX ? (p.x() - value.x()) : (p.y() - value.y());
        if (compare < 0) {
            // p < value, busca na esquerda
            return contains(p, n.left, !isX);
        } else {
            // p >= value, busca na direita
            return contains(p, n.right, !isX);
        }
    }

    public Point2D nearest(Point2D p) {
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

        Node nextNode;
        Node otherNode;

        double compare = isX ? (p.x() - value.x()) : (p.y() - value.y());
        if (compare < 0) {
            // p < value, lado bom é a esquerda
            nextNode = n.left;
            otherNode = n.right;
        } else {
            // p >= value, lado bom é a direita
            nextNode = n.right;
            otherNode = n.left;
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
