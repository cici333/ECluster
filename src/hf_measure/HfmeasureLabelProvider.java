package hf_measure;


import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class HfmeasureLabelProvider implements ITableLabelProvider {

	
	boolean htb, htf;
	HfmeasureLabelProvider(boolean htb, boolean htf){
		this.htb = htb;
		this.htf = htf;
	}

	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int key) {
		// TODO Auto-generated method stub
		ClusterVo c = (ClusterVo)element;
		
		if(!htb && !htf){
			switch(key){
			case 0:
				return c.getClusterid();
			case 1:
				return String.valueOf(c.getProteins().size());
			case 2:
				return String.valueOf(c.getFmeasure());
			case 3:
				return c.getFmeasureFun();
			case 4:
				return String.valueOf(c.getFunnum());
			case 5:
				return String.valueOf(c.getBackground());					
			}
		}
		else if(htb && !htf){
			switch(key){
			case 0:
				return c.getClusterid();
			case 1:
				return String.valueOf(c.getProteins().size());
			case 2:
				return String.valueOf(c.getHfmeasureTb());
			case 3:
				return c.getHfmeasureFun();
			case 4:
				return String.valueOf(c.getHfunnum());
			case 5:
				return String.valueOf(c.getHbackground());
			case 6:
				return String.valueOf(c.getFmeasure());
			case 7:
				return c.getFmeasureFun();
			case 8:
				return String.valueOf(c.getFunnum());
			case 9:
				return String.valueOf(c.getBackground());
					
			}
		}
		else if(!htb && htf){
			switch(key){
			case 0:
				return c.getClusterid();
			case 1:
				return String.valueOf(c.getProteins().size());
			case 2:
				return String.valueOf(c.getHfmeasureTf());
			case 3:
				return c.getHfmeasureFun();
			case 4:
				return String.valueOf(c.getHfunnum());
			case 5:
				return String.valueOf(c.getHbackground());
			case 6:
				return String.valueOf(c.getFmeasure());
			case 7:
				return c.getFmeasureFun();
			case 8:
				return String.valueOf(c.getFunnum());
			case 9:
				return String.valueOf(c.getBackground());
					
			}
		}
		else{
			switch(key){
			case 0:
				return c.getClusterid();
			case 1:
				return String.valueOf(c.getProteins().size());
			case 2:
				return String.valueOf(c.getHfmeasureTb());
			case 3:
				return String.valueOf(c.getHfmeasureTf());
			case 4:
				return c.getHfmeasureFun();
			case 5:
				return String.valueOf(c.getHfunnum());
			case 6:
				return String.valueOf(c.getHbackground());
			case 7:
				return String.valueOf(c.getFmeasure());
			case 8:
				return c.getFmeasureFun();
			case 9:
				return String.valueOf(c.getFunnum());
			case 10:
				return String.valueOf(c.getBackground());
					
			}
		}
		
		
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

}
