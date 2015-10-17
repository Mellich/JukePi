package client.serverconnection.exceptions;

import client.serverconnection.impl.YTJBServerConnection.PermissionTuple;

public class PermissionDeniedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PermissionTuple[] pt;
	
	public PermissionDeniedException(PermissionTuple pt) {
		this.pt = new PermissionTuple[1];
		this.pt[0] = pt;
	}
	
	public PermissionDeniedException(PermissionTuple[] pt) {
		this.pt = pt;
	}
	
	public PermissionTuple[] getDeniedPermissions(){
		return pt;
	}
	
	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder("Permission(s) were denied by the server! Wrong pass phrase used by client? Denied permissions:\n");
		sb.append("Permission \t\t Passphrase\n");
		for (PermissionTuple temp : pt){
			sb.append(temp.getPermission().name()+" \t\t "+temp.getPassphrase()+"\n");
		}
		return sb.toString();
	}

}
