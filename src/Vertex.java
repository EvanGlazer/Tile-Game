import java.awt.Color;
import java.awt.Graphics;


public class Vertex
{
	static Color[] Colors = { Color.red, Color.blue, Color.green, Color.yellow, Color.cyan, Color.gray, Color.magenta, Color.orange  };
	int ColorIndex = 0;
	boolean Visible = true;
	int VertexIndex = 0;
	Edge[] AdjacencyList = new Edge[0];
	int Width = 40, Height = 40;
	int X = 0, Y = 0;
	
	boolean Selected = false;

	public boolean isSelected() 
	{
		return Selected;
	}

	public void setSelected(boolean selected) 
	{
		Selected = selected;
	}

	public int getColorIndex()
	{
		return ColorIndex;
	}

	public void setColorIndex(int colorIndex)
	{
		ColorIndex = colorIndex & 7;
	}
	
	public boolean isVisible()
	{
		return Visible;
	}

	public void setVisible(boolean visible)
	{
		Visible = visible;
	}

	public int getVertexIndex()
	{
		return VertexIndex;
	}

	public void setVertexIndex(int vertexIndex)
	{
		VertexIndex = vertexIndex;
	}

	public Edge[] getAdjacencyList()
	{
		return AdjacencyList;
	}

	public void setAdjacencyList(Edge[] adjacencyList)
	{
		AdjacencyList = adjacencyList;
	}
	
	public int getX()
	{
		return X;
	}

	public void setX(int x)
	{
		X = x;
	}

	public int getY()
	{
		return Y;
	}

	public void setY(int y)
	{
		Y = y;
	}

	public int getWidth()
	{
		return Width;
	}

	public void setWidth(int width)
	{
		Width = width;
	}

	public int getHeight()
	{
		return Height;
	}

	public void setHeight(int height)
	{
		Height = height;
	}

	public void paint(Graphics g)
	{
		if( !Visible ) return;
		
		g.setColor( Colors[ColorIndex] );
		
		if( Selected ) g.setColor( Color.white );
		
		g.fillRect( X - ( Width / 2 ), Y - ( Height / 2 ),
			Width, Height );
		g.setColor( Color.black );
		g.drawRect( X - ( Width / 2 ), Y - ( Height / 2 ),
				Width, Height );
		g.drawString( "" + VertexIndex, X - 6, Y + 6 );
	}
	
	public boolean ClickedMe( int x, int y )
	{
		if( !Visible ) return( false );
		
		if( x >= ( X - ( Width / 2 ) ) &&
			y >= ( Y - ( Height / 2 ) ) &&
			
			x < ( X - ( Width / 2 ) ) + Width &&
			y < ( Y - ( Height / 2 ) ) + Height )
		{
			return( true );
		}

		return( false );
	}

}
