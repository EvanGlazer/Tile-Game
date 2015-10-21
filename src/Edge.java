
public class Edge
{
	Vertex TargetVertex;

	public Edge( Vertex targetVertex )
	{
		TargetVertex = targetVertex;
	}
	
	public Vertex getTargetVertex()
	{
		return TargetVertex;
	}

	public void setTargetVertex(Vertex targetVertex)
	{
		TargetVertex = targetVertex;
	}
	
}
