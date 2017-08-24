package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protobuf.ProtobufMessage;

public class ClientDataHandler extends SimpleChannelInboundHandler<ProtobufMessage.ProtobufData> {

  private ChannelHandlerContext ctx;
  private ClientConnector client;
  private final Logger logger = LoggerFactory.getLogger("client.ClientDataHandler");
  private final static ProtobufMessage.ProtobufData heartbeat =
      ProtobufMessage.ProtobufData.newBuilder().setDataString("HeartBeat").build();

  public ClientDataHandler(ClientConnector client) {
    this.client = client;

  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ProtobufMessage.ProtobufData msg) throws Exception {

    logger.info("channelRead0 > {} sent: {}", client.getHost(), msg.toString().replace("\n", ""));

  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    this.ctx = ctx;
    logger.info("channelActive > Client connected to {} on port {}", client.getHost(), client.getPort());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {


  }

  public void sendData(int count) {
    if (client.isActive() && ctx.channel().isWritable()) {
      logger.debug("sendData > sending... {} ", count);
      ProtobufMessage.ProtobufData data =
          ProtobufMessage.ProtobufData.newBuilder().setDataString("Test").setDataNumber(count).build();
      ctx.writeAndFlush(data);
      data = null;
    }

  }

  public void sendheartBeat() {
    if (client.isActive() && ctx.channel().isWritable()) {
      logger.debug("sendheartBeat > sending... {} ", heartbeat);
      ctx.writeAndFlush(heartbeat);
    }

  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    logger.warn("Exception in connection from {} cause {}", client.getHost(), cause.toString(),cause);
    ctx.close();
  }


}
