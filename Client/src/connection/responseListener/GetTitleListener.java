package connection.responseListener;

import java.util.LinkedList;

import clientinterface.listener.ResponseListener;

public class GetTitleListener implements ResponseListener{

	private LinkedList<String> content;
	
	public GetTitleListener() {
		content = new LinkedList<String>();
	}
	
	@Override
	public void onResponse(String[] response) {
		content.clear();
		for (String s : response)
			content.add(s);
	}
	
	public LinkedList<String> getContent() {
		return content;
	}

}
