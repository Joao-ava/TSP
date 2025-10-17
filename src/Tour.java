import algs4.KdTree;
import algs4.Point2D;
import algs4.StdDraw;
import algs4.StdOut;

import java.util.HashMap;

/**
 * Template da classe Tour para a heurística do vizinho mais próximo.
 *
 * Primeira etapa sugerida:
 *  - implemente os métodos de lista encadeada e chame {@link #insertNearestNaive(Point2D)}.
 * Segunda etapa:
 *  - implemente a classe algs4.KdTree e utilize {@link #insertNearestKd(Point2D)}
 *    para acelerar a busca do vizinho mais próximo.
 */
public class Tour {

    private static class Node {
        private Point2D point;
        private Node next;

        public Node(Point2D point) {
            this.point = point;
            this.next = null;
        }
    }

    private HashMap<Point2D, Node> nodes = new HashMap<>();
    private Node start;
    private int count;
    private final boolean useKdTree;
    private KdTree kdTree;

    public Tour() {
        this(false);
    }

    public Tour(boolean useKdTree) {
        this.useKdTree = useKdTree;
        this.start = null;
        this.count = 0;
        if (useKdTree) {
            kdTree = new KdTree();
        }
    }

    public Tour(Point2D a, Point2D b, Point2D c, Point2D d) {
        this(); 
        insertNearestNaive(a);
        insertNearestNaive(b);
        insertNearestNaive(c);
        insertNearestNaive(d);
    }

    public int size() {
        if (useKdTree) {
            return kdTree.size();
        } else {
            return count;
        }
    }

    public double length() {
        double distance = 0d;
        Node current = start;
        while (current.next != start) {
            distance += current.point.distanceTo(current.next.point);
            current = current.next;
        }
        distance += current.point.distanceTo(current.next.point);
        return distance;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (start == null)
            return "(Tour vazio)";

        Node current = start;
        do {
            sb.append(current.point.toString()).append("\n");
            current = current.next;
        } while (current != start);

        return sb.toString();
    }

    public void draw() {
        if (start == null || start.next == start)
            return;

        Node current = start;
        do {
            current.point.drawTo(current.next.point);
            current = current.next;
        } while (current != start);
    }

    public void insertNearest(Point2D p) {
        if (useKdTree) {
            insertNearestKd(p);
        } else {
            insertNearestNaive(p);
        }
    }

    /**
     * Versão ingênua: percorre toda a lista, calcula a distância para cada nó
     * usando {@link Point2D#distanceTo(Point2D)} e insere o novo ponto após o vizinho
     * mais próximo encontrado.
     */
    public void insertNearestNaive(Point2D p) {
        Node newNode = new Node(p);

        if (start == null) {
            newNode.next = newNode;
            start = newNode;
        } else {
            Node current = start;
            Node nearest = start;
            double minDist = p.distanceTo(start.point);

            do {
                double dist = p.distanceTo(current.point);
                if (dist < minDist) {
                    minDist = dist;
                    nearest = current;
                }
                current = current.next;
            } while (current != start);

            newNode.next = nearest.next;
            nearest.next = newNode;
        }
        count++;
    }

    /**
     * Versão otimizada: utiliza o {@link KdTree} para localizar rapidamente o
     * ponto mais próximo e insere o novo nó na lista. Requer que a classe
     * algs4.KdTree esteja totalmente implementada.
     */
    public void insertNearestKd(Point2D p) {
        if (start == null) {
            start = new Node(p);
            start.next = start;
            count++;
            nodes.put(p, start);
            kdTree.insert(p);
            return;
        }
        Point2D nearest = kdTree.nearest(p);
        Node current = nodes.get(nearest);
        Node newNode = new Node(p);
        newNode.next = current.next;
        current.next = newNode;
        count++;
        kdTree.insert(p);
        nodes.put(p, newNode);
    }

    public static void main(String[] args) {
        Tour tour = new Tour();
        tour.insertNearest(new Point2D(1.0, 1.0));
        tour.insertNearest(new Point2D(1.0, 4.0));
        tour.insertNearest(new Point2D(4.0, 4.0));
        tour.insertNearest(new Point2D(4.0, 1.0));

        StdOut.println("# de pontos = " + tour.size());
        StdOut.println("Comprimento = " + tour.length());
        StdOut.println(tour);

        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);
        tour.draw();
    }
}
