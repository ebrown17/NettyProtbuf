package server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import protobuf.ProtobufMessage;

public class ServerChannelHandler extends ChannelInitializer<SocketChannel> {

	private ServerListener server;
	private static final int WRITE_IDLE_TIME = 10;

	public ServerChannelHandler(ServerListener server) {
		this.server = server;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
	    //TODO implement heartbeat protocol
		/*p.addLast("idleStateHandler", new IdleStateHandler(0, WRITE_IDLE_TIME, 0));
		p.addLast("heartBeatHandler", new ServerHeartbeatHandler());*/
		p.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
		p.addLast("protobufDecoder", new ProtobufDecoder(ProtobufMessage.ProtobufData.getDefaultInstance()));
		p.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
		p.addLast("protobufEncoder", new ProtobufEncoder());
		p.addLast(new ServerDataHandler(server));

		System.out.println("Server channel initialized");

	}

}