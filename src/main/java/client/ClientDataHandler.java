package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protobuf.ProtobufMessage;

public class ClientDataHandler extends SimpleChannelInboundHandler<ProtobufMessage.ProtobufData>{
	
	private ChannelHandlerContext cTx;
	private ClientConnector client;
	private final Logger logger = LoggerFactory.getLogger("client.ClientDataHandler");
	private final static ProtobufMessage.ProtobufData heartbeat = ProtobufMessage.ProtobufData.newBuilder().setDataString("HeartBeat").build();
	
	public ClientDataHandler(ClientConnector client){
		this.client=client;
		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ProtobufMessage.ProtobufData msg) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {       
        cTx = ctx;
        logger.info("channelActive Client connected to remote peer");
        client.resetRetryCount();            	
    }
	
	@Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		client.setConnection(false);
		logger.info("channelInactive Client disconnected from remote peer");
        client.connect();
    	
    }
	
	public void sendData(int count){
		if(client.isConnected()){
			logger.debug("sendData sending... {} ", count);
			ProtobufMessage.ProtobufData data = ProtobufMessage.ProtobufData.newBuilder().setDataString("Test").setDataNumber(count).build();
			cTx.writeAndFlush(data);
		}		
		
	}
	
	public void sendheartBeat(){
			logger.debug("sendheartBeat sending... {} ", heartbeat);
			cTx.writeAndFlush(heartbeat);
	}
	

}
