import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphEvent;
import org.gephi.graph.api.GraphListener;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;


public class GraphEditActionListener implements GraphListener{

	private GraphModel graphModel;
	private ProjectController projectController;
	private Workspace workspace;
	private	PreviewController previewController;
	
	
	public GraphEditActionListener()
	{
		
	}
	
	public GraphEditActionListener(Workspace workspace, ProjectController projectController, PreviewController previewController){
		this.workspace = workspace;
		this.projectController = projectController;
		this.previewController = previewController;
		
		this.InitGraphModel();
	}
	
	
	private void InitGraphModel(){
		//Get a graph model - it exists because we have a workspace
		graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		graphModel.addGraphListener(this);
	}

	public void SetProjectController(ProjectController projectController){
		if (this.projectController != projectController){
			this.projectController = projectController;
		}
	}
	
	public void SetPreviewController(PreviewController previewController){
		if (this.previewController != previewController){
			this.previewController = previewController;
		}			
	}
	
	public GraphModel GetGraphModel(){
		return graphModel;
	}
		
	@Override
	public void graphChanged(GraphEvent arg0) {
		// TODO Auto-generated method stub
		//if (previewController != null){
						
			
			//graphModel.setVisibleView(arg0.getSource());
			//previewController.refreshPreview(workspace);
		//}
		final Node n = arg0.getData().addedNodes()[0];
		
		new Thread()
		{
		    public void run() {
		        try {
					Thread.sleep(20000);
					Graph graph = graphModel.getDirectedGraph();
					graph.getNodes().toArray();

					graph.removeNode(n);
					previewController.refreshPreview(workspace);
					
				} 
		        catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        catch(Exception e){
		        	e.printStackTrace();
		        }
		    }
		}.start();
	}
	
	
	public Graph AddEdge(Edge edge){
		Graph graph = graphModel.getDirectedGraph();
		graph.addEdge(edge);
				
		return graph;
	}

	public Graph AddNode(Node node, String nodeLabel){
		node.getNodeData().setLabel(nodeLabel);
		Graph graph = graphModel.getDirectedGraph();
		graph.addNode(node);
				
		return graph;
	}
}
