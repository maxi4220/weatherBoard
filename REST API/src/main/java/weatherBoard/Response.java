package weatherBoard;

public class Response {
	public String status;
	public Object data;
	
	public Response(String status, Object data) {
		this.status = status;
		this.data = data;		
	}
}
