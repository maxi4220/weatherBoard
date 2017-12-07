package weatherBoard;

public class Response {
	public String status;
	public Object data;
	
	public Response(String status, Object data) {
		String aux;
		this.status = status;
		if(data.getClass()==String.class) {
			aux = (String)data;
			aux = aux.replaceAll("\"", "\\\"");
			data = aux;
		}
		this.data = data;
	}
}
