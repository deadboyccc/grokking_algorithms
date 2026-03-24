// The graph representing nodes and the distances to their neighbors
private val graph = mapOf(
    "start" to mapOf("a" to 6.0, "b" to 2.0),
    "a" to mapOf("fin" to 1.0),
    "b" to mapOf("a" to 3.0, "fin" to 5.0),
    "fin" to emptyMap()
)

// Set to keep track of nodes we have already processed
private val processed = mutableSetOf<String>()

fun main() {
    // Distance from the start to each node
    val costs = mutableMapOf(
        "a" to 6.0,
        "b" to 2.0,
        "fin" to Double.POSITIVE_INFINITY
    )

    // Table mapping nodes to their parent nodes
    val parents = mutableMapOf<String, String?>(
        "a" to "start",
        "b" to "start",
        "fin" to null
    )

    println("Cost from the start to each node:")
    println(dijkstraAlgorithm(costs, parents))
}

fun dijkstraAlgorithm(
    costs: MutableMap<String, Double>,
    parents: MutableMap<String, String?>
): Map<String, Double> {

    var node = findLowestCostNode(costs)
    
    while (node != null) {
        val cost = costs[node] ?: Double.POSITIVE_INFINITY
        val neighbors = graph[node] ?: emptyMap()
        
        // Check all neighbors of the current node
        for ((neighbor, neighborCost) in neighbors) {
            val newCost = cost + neighborCost
            val currentNeighborCost = costs[neighbor] ?: Double.POSITIVE_INFINITY
            
            // If it's cheaper to reach this neighbor through the current node...
            if (currentNeighborCost > newCost) {
                // ...update the cost for this node
                costs[neighbor] = newCost
                // ...and mark this node as the new parent for the neighbor
                parents[neighbor] = node
            }
        }
        
        // Mark the node as processed so we don't evaluate it again
        processed.add(node)
        
        // Find the next node to process and repeat
        node = findLowestCostNode(costs)
    }
    
    return costs // Expected output: {a=5.0, b=2.0, fin=6.0}
}

private fun findLowestCostNode(costs: Map<String, Double>): String? {
    var lowestCost = Double.POSITIVE_INFINITY
    var lowestCostNode: String? = null

    // Iterate through each node
    for ((node, cost) in costs) {
        // If it's the lowest cost seen so far and hasn't been processed yet...
        if (cost < lowestCost && node !in processed) {
            // ...set it as the new lowest-cost node
            lowestCost = cost
            lowestCostNode = node
        }
    }
    
    return lowestCostNode
}
