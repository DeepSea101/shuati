/*
DFS
建造一个List类型的一维数组，每个元素是一个List。元素i包含所有修完course i 后可以修的课。
用hasCycle这个函数去DFS遍历，检测是否有cycle，有的话就can't finish。
hasCycle这个函数会借助一个status数组进行DFS。若课程i已经访问过（Visited），就不要再访问，这是dfs with memoization。如果发现当前这个status[cur]的状态
是正在访问，说明它已经走了一圈也没走到Visited，所有找到了一个cycle，则立即返回不能完成课程。否则是Init的话，则标记为Visiting。并在整个graph[cur]这
个List访问完后，要将status[cur]标记为Visited.
*/
class Solution {
	private static enum Status {Init, Visiting, Visited};

	public boolean canFinish(int numCourses, int[][] prerequisites) {
		// graph[i] is a list containing all the courses we can learn after we learned course i
		List<Integer>[] graph = new ArrayList[numCourses];
		Status[] status = new Status[numCourses];

		// initialize the graph
		for (int i = 0; i < numCourses; i++)
		{
			graph[i] = new ArrayList<Integer>();
			status[i] = Status.Init;
		}

		for (int[] pair: prerequisites) graph[pair[1]].add(pair[0]);

		for (int j = 0; j < numCourses; j++)
		{
			if (status[j] != Status.Visited)
				if (hasCycle(graph, status, j)) return false;
		}
        return true;
	}

	private boolean hasCycle(List<Integer>[] graph, Status[] status, int cur)
	{
		if (status[cur] == Status.Visiting) return true; // we meet a cycle
		status[cur] = Status.Visiting;
		for (int next : graph[cur])
		{
			if (status[next] != Status.Visited)
				if (hasCycle(graph, status, next)) return true; // we meet a cycle
		}
		status[cur] = Status.Visited;
		return false;
	}
}