package connection.responseListener;

import java.util.LinkedList;

import clientinterface.listener.ResponseListener;

public class UpdateWishlistListener implements ResponseListener{

	LinkedList<String> wishlist;
	
	public UpdateWishlistListener() {
		wishlist = new LinkedList<String>();
	}
	
	@Override
	public void onResponse(String[] response) {
		wishlist.clear();
		for (String s : response)
			wishlist.add(s);
	}
	
	public LinkedList<String> getWishlist() {
		return wishlist;
	}
}
