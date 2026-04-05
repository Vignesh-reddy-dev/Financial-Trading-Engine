package financial_trading_system;
import java.util.*;

class BPlusTreeNode {
    boolean isLeaf;
    List<Long> keys;
    List<BPlusTreeNode> children;
    BPlusTreeNode nextLeaf;
    List<Trade> trades; // Only for leaf nodes
    SegmentTree segTree; // Integrated Segment Tree

    public BPlusTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.trades = new ArrayList<>();
        this.nextLeaf = null;
    }
}


public class Bplustree {
	 private BPlusTreeNode root;
	    private int order;

	    public Bplustree(int order) {
	        this.order = order;
	        this.root = new BPlusTreeNode(true);
	    }

	    public void insert(Trade trade) {
	        BPlusTreeNode node = findLeafNode(root, trade.getTimestamp());
	        
	        // Find correct position to insert
	        int i = 0;
	        while (i < node.trades.size() && node.trades.get(i).getTimestamp() < trade.getTimestamp()) {
	            i++;
	        }
	        node.trades.add(i, trade);
	        node.keys.add(i, trade.getTimestamp());

	        // Rebuild the segment tree for the leaf node
	        node.segTree = new SegmentTree(node.trades);

	        // Handle node splitting if it's full
	        if (node.trades.size() > order - 1) {
	            split(node);
	        }
	    }

	    private BPlusTreeNode findLeafNode(BPlusTreeNode node, long timestamp) {
	        if (node.isLeaf) {
	            return node;
	        }
	        int i = 0;
	        while (i < node.keys.size() && timestamp >= node.keys.get(i)) {
	            i++;
	        }
	        return findLeafNode(node.children.get(i), timestamp);
	    }

	    private void split(BPlusTreeNode node) {
	        // This is a simplified split implementation.
	        // The full logic for splitting internal nodes is complex.
	        BPlusTreeNode parent = findParent(root, node);
	        if (parent == null) {
	            parent = new BPlusTreeNode(false);
	            parent.children.add(node);
	            root = parent;
	        }

	        int midIndex = node.trades.size() / 2;
	        BPlusTreeNode newNode = new BPlusTreeNode(true);
	        
	        // Move half of the trades to the new node
	        List<Trade> tradesToMove = new ArrayList<>(node.trades.subList(midIndex, node.trades.size()));
	        List<Long> keysToMove = new ArrayList<>(node.keys.subList(midIndex, node.keys.size()));
	        
	        newNode.trades = tradesToMove;
	        newNode.keys = keysToMove;
	        
	        // Remove trades from original node
	        node.trades.subList(midIndex, node.trades.size()).clear();
	        node.keys.subList(midIndex, node.keys.size()).clear();

	        // Update linked list
	        newNode.nextLeaf = node.nextLeaf;
	        node.nextLeaf = newNode;

	        // Rebuild segment trees
	        node.segTree = new SegmentTree(node.trades);
	        newNode.segTree = new SegmentTree(newNode.trades);

	        // Insert new key into parent
	        long newKey = newNode.keys.get(0);
	        int parentIndex = 0;
	        while (parentIndex < parent.keys.size() && newKey > parent.keys.get(parentIndex)) {
	            parentIndex++;
	        }
	        parent.keys.add(parentIndex, newKey);
	        parent.children.add(parentIndex + 1, newNode);
	    }

	    private BPlusTreeNode findParent(BPlusTreeNode node, BPlusTreeNode child) {
	        // Recursive search for the parent node
	        for (BPlusTreeNode currentChild : node.children) {
	            if (currentChild == child) {
	                return node;
	            }
	            if (!currentChild.isLeaf) {
	                BPlusTreeNode parent = findParent(currentChild, child);
	                if (parent != null) {
	                    return parent;
	                }
	            }
	        }
	        return null;
	    }

	    public void rangeQuery(long startTimestamp, long endTimestamp) {
	        BPlusTreeNode currentNode = findLeafNode(root, startTimestamp);
	        
	        int totalVolume = 0;
	        double maxPrice = 0.0;
	        
	        while (currentNode != null) {
	            // Find the start and end indices for the query within the current node
	            int startIndex = 0;
	            while (startIndex < currentNode.trades.size() && currentNode.trades.get(startIndex).getTimestamp() < startTimestamp) {
	                startIndex++;
	            }
	            int endIndex = currentNode.trades.size() - 1;
	            while (endIndex >= 0 && currentNode.trades.get(endIndex).getTimestamp() > endTimestamp) {
	                endIndex--;
	            }

	            if (endIndex < startIndex) {
	                // No trades in this node within the range
	            } else {
	                totalVolume += currentNode.segTree.querySumVolume(startIndex, endIndex);
	                maxPrice = Math.max(maxPrice, currentNode.segTree.queryMaxPrice(startIndex, endIndex));
	            }
	            
	            // If the current node's last key is within the range, we need to check the next node
	            if (currentNode.keys.get(currentNode.keys.size() - 1) >= endTimestamp) {
	                break;
	            }
	            currentNode = currentNode.nextLeaf;
	        }

	        System.out.println("Range Query Results (" + startTimestamp + " to " + endTimestamp + "):");
	        System.out.println("Total Volume: " + totalVolume);
	        System.out.println("Maximum Price: " + maxPrice);
	    }

}
