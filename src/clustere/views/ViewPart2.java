package clustere.views;

import java.util.Set;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.wuxuehong.bean.Node;
import com.wuxuehong.bean.Paramater;

public class ViewPart2 extends ViewPart {

	private static Text text;
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
        text = new Text(parent,SWT.MULTI|SWT.WRAP|SWT.V_SCROLL);
	}

	public void setContent(Node node){
		text.setText("");
		text.append("NodeID:\t"+node.getNodeID()+"\n");
		text.append("Neighbours:\t"+getString(node.getNeighbours())+"\n");
		text.append("Annotation:\t"+Paramater.currentProteinFunction.get(node.getNodeID().toUpperCase())+"\n");
	}
	
	/**
	 * @author TangYu
	 * @modify_date: 2014年9月1日 下午3:36:23
	 * @param neighbors
	 * @return
	 */	
	public String getString(Set<Node> neighbors){
		String result = "";
		for(Node n : neighbors){
			result += n.getNodeID();
		}
		return result;
	}
	
	public static void refresh(){
		if(text==null||text.isDisposed())return;
		text.setText("");
	}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
