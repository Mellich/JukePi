package client.serverconnection.exceptions;

import client.serverconnection.impl.YTJBServerConnection.PermissionTuple;

public class PermissionDeniedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PermissionTuple[] pt;
	private String reason;
	
	public PermissionDeniedException(PermissionTuple pt,String reason) {
		this.pt = new PermissionTuple[1];
		this.pt[0] = pt;
		this.reason = reason;
	}
	
	public PermissionDeniedException(PermissionTuple[] pt,String reason) {
		this.pt = pt;
		this.reason = reason;
	}
	
	public PermissionTuple[] getDeniedPermissions(){
		return pt;
	}
	
	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder("Permission(s) couldn't be granted! Reason:"+reason+" \n");
		sb.append("Permission \t\t Passphrase \n");
		for (PermissionTuple temp : pt){
			sb.append(temp.getPermission().name()+" \t\t "+temp.getPassphrase()+"\n");
		}
		return sb.toString();
	}

}
