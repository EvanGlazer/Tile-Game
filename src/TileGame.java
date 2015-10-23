import java.applet.Applet;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.JOptionPane;

public class TileGame extends Applet implements MouseListener
{
	private static final long serialVersionUID = 1L;
	
	Image background;
	int NumRows;
	int[] NumCols;
	Vertex[] VertexList;
	int TotalVertices;
	int vertexCount;
	int[][] AdjacencyMatrix;
	int SelectedCount = 0, SelectedIndex = 0, SelectedColor = 0;
	List li = new List();
	String file = "level1.txt";
	Button submitButton;
	Button restartButton;
	Button newBackground;
	int score;
	int level;
	int bgcount = 0;
	java.util.List<String> stringList = Arrays.asList(new String[]{"background.jpg", "background2.jpg","background4.jpg", "background3.jpg", "background1.jpg",});
	
	void LoadGraph(String file) throws FileNotFoundException
	{
    	// Set up and open the input.txt file.
   		Scanner in = new Scanner(new File(file));
   		level = in.nextInt();
   		NumRows = in.nextInt();
   		NumCols = new int[NumRows];
   		TotalVertices = in.nextInt();
   		vertexCount = TotalVertices;
   		VertexList = new Vertex[TotalVertices];

   		int VertexIndex = 0;
   		for( int Row=0; Row<NumRows; Row++ )
   		{
   			NumCols[Row] = in.nextInt();
   			for( int Col=0; Col<NumCols[Row]; Col++ )
   			{
   				VertexList[VertexIndex] = new Vertex();
   				int x = in.nextInt();
   				int y = in.nextInt();
   				int ci = in.nextInt();
   				VertexList[VertexIndex].setX( 340 + x * 30 );
   				VertexList[VertexIndex].setY( 200 + y * 50 );
   				VertexList[VertexIndex].setColorIndex( ci );
   				VertexList[VertexIndex].setVertexIndex( VertexIndex );
   				VertexIndex++;
   			}
   		}
   		
   		AdjacencyMatrix = new int[TotalVertices][TotalVertices];
   		for( int i=0; i<TotalVertices; i++ )
   		{
   			int Count = 0;
   			int[] RowData = new int[TotalVertices];
   			for( int j=0; j<TotalVertices; j++ )
   			{
   				RowData[j] = in.nextInt();
   				if( RowData[j] == 1 ) Count++;
   			}
   			
   			VertexList[i].setAdjacencyList( new Edge[Count] );
   			int k = 0;
   			for( int j=0; j<TotalVertices; j++ )
   			{
   				if( RowData[j] == 1 )
   				{
   					VertexList[i].getAdjacencyList()[k++] = new Edge( VertexList[j] );
   				}
   			}
   		}
   		
   		// Close the scanner object.
   		in.close();
	}
	
	public void init() 
	{
	
		
		submitButton = new Button("Next Level");
		submitButton.addActionListener(new submitButton());
		submitButton.setEnabled(false);
		
		newBackground = new Button("New Background");
		newBackground.addActionListener(new newBackground());
		newBackground.setEnabled(true);
		
		restartButton = new Button("Restart Game");
		restartButton.addActionListener(new restartButton());
		restartButton.setEnabled(false);
		
		add(restartButton);
		add(submitButton);
		add(newBackground);
        setSize(900,540);

        
        addMouseListener( this );
        background = getImage(getCodeBase(),stringList.get(0));
        try{ LoadGraph(file); } catch(Exception ex){}
	}

	
	
	public void paint (Graphics g)
	{
		super.paint(g);
		g.drawImage(background,0,0,this);
		if( VertexList == null ) return;
		
		for( int v=0; v<VertexList.length; v++ )
		{
			VertexList[v].paint( g );
		}
		
	}
	
	public void nextLevelCheck()
	{
		// if the total vertices is 0 left then next level
		submitButton.setEnabled(true);
		stringList.remove(0);
		level = 2;
		
	}
	   private class submitButton implements ActionListener
	    {
	        @Override
	        public void actionPerformed(ActionEvent e) 
	        {
	        	switch(level)
	    		{
	    		case 1:
	    			file = "level1.txt";
	    			level = 1;
	    			try{ LoadGraph(file); } catch(Exception ex){}
	    			init();
	    			repaint();
	    			break;
	    		case 2:
	    			file = "level2.txt";
	    			try{ LoadGraph(file); } catch(Exception ex){}
	    			level = 2;
	    			init();
	    			repaint();
	    			break;
	    		
	    			default:
	    				file = "level2.txt";
	    				level = 2;
	    				try{ LoadGraph(file); } catch(Exception ex){}
	    				init();
		    			repaint();
	    				break;
	    		}
	        }
	    }
	   private class restartButton implements ActionListener
	    {
	        @Override
	        public void actionPerformed(ActionEvent e) 
	        {
	        	file = "level2.txt";
	        	try{ LoadGraph(file); } catch(Exception ex){}
	
	        }
	    }
	   private class newBackground implements ActionListener
	    {
	        @Override
	        public void actionPerformed(ActionEvent e) 
	        {
	        	try{
	        	 bgcount++;
	        	 background = getImage(getCodeBase(),stringList.get(bgcount));
	        	 repaint();
	        	}
	        	catch(Exception e1)
	        	{
	        		bgcount = 0;
	        	}
	        }
	    }
	public void islandCheck()
	{
		// The vertex we will start at
		Vertex start = null;
		// Find the first visible node and make it start
		for(Vertex v : VertexList)
		{
			if(v.isVisible())
			{
				start = v;
				break;
			}
		}
		
		// in case we have a null start
		if (start == null)
			return;
		
		LinkedList<Vertex> queue = new LinkedList();
		// setting the visited flag and counter
		start.setVisited(true);
		int counter = 1;
		
		queue.add(start);
		
		while(!queue.isEmpty())
		{
			// gets the first item from vertex
			Vertex element = queue.poll();
			
			// visit each adjacent vertex then set items
			for(Edge e : element.getAdjacencyList())
			{
				if(!e.getTargetVertex().isVisited() && e.getTargetVertex().isVisible())
				{
					e.getTargetVertex().setVisited(true);
					counter++;
					queue.add(e.getTargetVertex());	
				}	
			}

		}
		// island check
		if (counter < vertexCount)
		{
			JOptionPane.showMessageDialog(null, "You have an island. You lose 2 points! ", "Penalty", JOptionPane.ERROR_MESSAGE);
			score -= 2;
			restartButton.setEnabled(true);
			
		}
		
		// reset the visited flags
		for(Vertex v : VertexList)
		{
			if(v.isVisible())
			{
				v.setVisited(false);
			}
			
		}
		
		
	}
		
		
	

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent ms)
	{
		
		for( int v=0; v<VertexList.length; v++ )
		{
			
			if( VertexList[v].ClickedMe( ms.getX(), ms.getY()) )
			{
				int VertexIndex = VertexList[v].getVertexIndex();
				int ColorIndex = VertexList[v].getColorIndex();

				boolean Found = false;
				for( int t=0; t<VertexList[v].getAdjacencyList().length; t++ )
				{
					if( SelectedCount == 1 &&
						VertexList[v].getAdjacencyList()[t].getTargetVertex().getVertexIndex() == SelectedIndex &&
							ColorIndex == SelectedColor )
					{
						
						Found = true;
						break;
					}
				}
				
				if( Found || SelectedCount == 0 )
				{
					SelectedCount++;
					if( SelectedCount == 1 )
					{
						vertexCount--;
						SelectedIndex = v;
						SelectedColor = ColorIndex; 
					}
					VertexList[v].setSelected( true );
					if( SelectedCount == 2 )
					{
						for( int i=0; i<TotalVertices; i++ )
						{
							if( VertexList[i].isSelected() ) 
							{
								vertexCount--;
								VertexList[i].setVisible( false );
								VertexList[i].setSelected( false );
								for(Vertex game : VertexList)
								{
									game.setScore(1);
									repaint();
									break;
								}
								
							}
						}
						SelectedCount = 0;
					}
					
					islandCheck();
					if(vertexCount == -7)
					{
						nextLevelCheck();
					}
					repaint();
					break;
				}
				
			}
	
		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
}
