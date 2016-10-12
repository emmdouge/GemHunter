package effects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.douge.gdx.game.objects.AbstractGameObject;

public class AfterImage
{
	public static Node head = null;
	public static Node tail = null;
	
	public final int MAX_NUM_NODES = 6;
	public int currentNumNodes = 0;
	
	public class Node
	{
		public Vector2 position;
		public Vector2 dimension;
		public Vector2 origin;
		public Vector2 scale;
		public float rotation;
		public TextureRegion reg;
		
		public Node nextNode;
		
		public Node(AbstractGameObject object, TextureRegion region)
		{
			position = object.position.cpy();
			dimension = object.dimension.cpy();
			origin = object.origin.cpy();
			scale = object.scale.cpy();
			rotation = 0;
			
			reg = region;
		}
	}
	
	public void addNode(AbstractGameObject object, TextureRegion region)
	{
		Node node = new Node(object, region);
		if(currentNumNodes != MAX_NUM_NODES)
		{
			if(head == null)
			{
				head = node;
				tail = node;
			}
			else
			{
				tail.nextNode = node;
				tail = node;
			}
		}
		else
		{
			Node oldHead = head;
			head = node;
			head.nextNode = oldHead;
			setTail();
		}
		currentNumNodes++;
		if(currentNumNodes > MAX_NUM_NODES)
		{
			currentNumNodes = MAX_NUM_NODES;
		}
	}

	private void setTail() 
	{
		tail = head;
		for(int i = 0; tail != null && i < MAX_NUM_NODES; i++)
		{
			tail = tail.nextNode;
		}
	}
}
