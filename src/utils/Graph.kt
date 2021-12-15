class Graph<T>(edges: List<Pair<T, T>>) {
    private val nodes: MutableSet<T> = mutableSetOf()
    private val transitions: MutableMap<T, MutableSet<T>> = mutableMapOf()

    init {
        edges.forEach { (from, to) ->
            addNode(from)
            addNode(to)
            addTransition(from, to)
        }
    }

    private fun addNode(node: T) {
        val isNew = nodes.add(node);
        if (isNew) {
            transitions[node] = mutableSetOf()
        }
    }

    private fun addTransition(nodeFrom: T, nodeTo: T) {
        transitions[nodeFrom]?.add(nodeTo)
        transitions[nodeTo]?.add(nodeFrom)
    }

    fun getAllPaths(isAvailable: (T, MutableList<T>) -> Boolean, nodeA: T, nodeB: T, path: MutableList<T> = mutableListOf()): List<List<T>> {
        val paths = mutableListOf<List<T>>()
        path.add(nodeA)
        if (nodeA == nodeB) {
            paths.add(path)
            return paths
        } else {
            val availableNodes = transitions[nodeA]?.filter { node -> isAvailable(node, path)}
            if (availableNodes != null) {
                for (node in availableNodes) {
                    paths.addAll(getAllPaths(isAvailable, node, nodeB, path.toMutableList()))
                }
            }
        }
        return paths
    }
}
