import java.io.StringReader;

import javax.json.Json;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ChatDecoder implements Decoder.Text<ChatModal> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ChatModal decode(String arg0) throws DecodeException {
		ChatModal modal = new ChatModal();
		String message = Json.createReader(new StringReader(arg0)).readObject().toString();
		modal.setMessage(message);
		return modal;
	}

	@Override
	public boolean willDecode(String arg0) {
		boolean isValid = true;
		try{
			Json.createReader(new StringReader(arg0)).readObject();
		} catch(Exception e){
			isValid = false;
			System.out.println(e);
		}
		return isValid;
	}
	
}
