import java.io.IOException;
import java.util.HashSet;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/echo", encoders = {ChatEncoder.class}, decoders = {ChatDecoder.class})
public class ChatEcho {
	private static HashSet<Session> usersList = new HashSet<Session>();
	
	@OnOpen
	public void open(Session session){
		usersList.add(session);
	}
	
	@OnMessage
	public void message(Session session, ChatModal incomingChat) throws IOException, EncodeException{
		ChatModal outgoingChat = new ChatModal();
		String username = (String)session.getUserProperties().get("name");
		if(username == null){
			outgoingChat.setName("System");
			outgoingChat.setMessage("You are now connected as " + outgoingChat.getName());
			session.getUserProperties().put("name", outgoingChat.getName());
			session.getBasicRemote().sendObject(outgoingChat);
		} else{
			outgoingChat.setName(username);
			outgoingChat.setMessage(incomingChat.getMessage());
			for(Session user : usersList){
				user.getBasicRemote().sendObject(outgoingChat);
			}
		}
		
	}
}
