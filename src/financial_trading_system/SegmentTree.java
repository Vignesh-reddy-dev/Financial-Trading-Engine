package financial_trading_system;
import java.util.*;

class SegTreeNode {
    int sumVolume;
    double maxPrice;
    double minPrice;
    int startIndex;
    int endIndex;
    SegTreeNode left, right;

    public SegTreeNode(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.sumVolume = 0;
        this.maxPrice = 0.0;
        this.minPrice = Double.MAX_VALUE;
    }
}


public class SegmentTree {
	private SegTreeNode root;
    private List<Trade> tradesData;

    public SegmentTree(List<Trade> trades) {
        this.tradesData = trades;
        if (!trades.isEmpty()) {
            this.root = buildTree(0, trades.size() - 1);
        }
    }

    private SegTreeNode buildTree(int start, int end) {
        if (start > end) {
            return null;
        }

        SegTreeNode node = new SegTreeNode(start, end);

        if (start == end) {
            Trade trade = tradesData.get(start);
            node.sumVolume = trade.getVolume();
            node.maxPrice = trade.getPrice();
            node.minPrice = trade.getPrice();
            return node;
        }

        int mid = start + (end - start) / 2;
        node.left = buildTree(start, mid);
        node.right = buildTree(mid + 1, end);

        if (node.left != null) {
            node.sumVolume += node.left.sumVolume;
            node.maxPrice = Math.max(node.maxPrice, node.left.maxPrice);
            node.minPrice = Math.min(node.minPrice, node.left.minPrice);
        }
        if (node.right != null) {
            node.sumVolume += node.right.sumVolume;
            node.maxPrice = Math.max(node.maxPrice, node.right.maxPrice);
            node.minPrice = Math.min(node.minPrice, node.right.minPrice);
        }

        return node;
    }

    public int querySumVolume(int queryStart, int queryEnd) {
        return querySumVolume(root, queryStart, queryEnd);
    }

    private int querySumVolume(SegTreeNode node, int queryStart, int queryEnd) {
        if (node == null || queryStart > node.endIndex || queryEnd < node.startIndex) {
            return 0; // No overlap
        }

        if (queryStart <= node.startIndex && queryEnd >= node.endIndex) {
            return node.sumVolume; // Full overlap
        }

        int leftSum = querySumVolume(node.left, queryStart, queryEnd);
        int rightSum = querySumVolume(node.right, queryStart, queryEnd);
        return leftSum + rightSum;
    }

    public double queryMaxPrice(int queryStart, int queryEnd) {
        return queryMaxPrice(root, queryStart, queryEnd);
    }

    private double queryMaxPrice(SegTreeNode node, int queryStart, int queryEnd) {
        if (node == null || queryStart > node.endIndex || queryEnd < node.startIndex) {
            return 0.0; // No overlap, return default min value
        }

        if (queryStart <= node.startIndex && queryEnd >= node.endIndex) {
            return node.maxPrice; // Full overlap
        }
        
        double leftMax = queryMaxPrice(node.left, queryStart, queryEnd);
        double rightMax = queryMaxPrice(node.right, queryStart, queryEnd);
        return Math.max(leftMax, rightMax);
    }

}
