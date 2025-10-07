import algs4.KdTree;
import algs4.Point2D;
import algs4.StdDraw;
import algs4.StdOut;

/**
 * Template da classe Tour para a heurística do vizinho mais próximo.
 *
 * Primeira etapa sugerida:
 *  - implemente os métodos de lista encadeada e chame {@link #insertNearestNaive(Point)}.
 * Segunda etapa:
 *  - implemente a classe algs4.KdTree e utilize {@link #insertNearestKd(Point)}
 *    para acelerar a busca do vizinho mais próximo.
 */
public class Tour {

    private static class Node {
        private Point point;
        private Node next;
    }

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

    public Tour(Point a, Point b, Point c, Point d) {
        this();
        throw new UnsupportedOperationException("Implementar construtor de depuração");
    }

    public int size() {
        throw new UnsupportedOperationException("Implementar size()");
    }

    public double length() {
        throw new UnsupportedOperationException("Implementar length()");
    }

    public String toString() {
        throw new UnsupportedOperationException("Implementar toString()");
    }

    public void draw() {
        throw new UnsupportedOperationException("Implementar draw()");
    }

    public void insertNearest(Point p) {
        if (useKdTree) {
            insertNearestKd(p);
        } else {
            insertNearestNaive(p);
        }
    }

    /**
     * Versão ingênua: percorre toda a lista, calcula a distância para cada nó
     * usando {@link Point#distanceTo(Point)} e insere o novo ponto após o vizinho
     * mais próximo encontrado.
     */
    public void insertNearestNaive(Point p) {
        throw new UnsupportedOperationException("Implementar insertNearestNaive(Point)");
    }

    /**
     * Versão otimizada: utiliza o {@link KdTree} para localizar rapidamente o
     * ponto mais próximo e insere o novo nó na lista. Requer que a classe
     * algs4.KdTree esteja totalmente implementada.
     */
    public void insertNearestKd(Point p) {
        throw new UnsupportedOperationException("Implementar insertNearestKd(Point)");
    }

    public static void main(String[] args) {
        Tour tour = new Tour();
        tour.insertNearest(new Point(1.0, 1.0));
        tour.insertNearest(new Point(1.0, 4.0));
        tour.insertNearest(new Point(4.0, 4.0));
        tour.insertNearest(new Point(4.0, 1.0));

        StdOut.println("# de pontos = " + tour.size());
        StdOut.println("Comprimento = " + tour.length());
        StdOut.println(tour);

        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);
        tour.draw();
    }
}
