package netty.in.action.chapter.t11_chatserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import java.net.InetSocketAddress;
import javax.net.ssl.SSLContext;

/**
 * 访问地址：https://localhost:4096
 * 
 * @author c.k
 * 
 */
public class SecureChatServer extends ChatServer {

	private final SSLContext context;

	public SecureChatServer(SSLContext context) {
		this.context = context;
	}

	@Override
	protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
		return new SecureChatServerIntializer(group, context);
	}

	/**
	 * 获取SSLContext需要相关的keystore文件，这里没有 关于HTTPS可以查阅相关资料，这里只介绍在Netty中如何使用
	 * 
	 * @return
	 */
	private static SSLContext getSslContext() {
		return null;
	}

	public static void main(String[] args) {
		SSLContext context = getSslContext();
		final SecureChatServer server = new SecureChatServer(context);
		ChannelFuture future = server.start(new InetSocketAddress(4096));
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				server.destroy();
			}
		});
		future.channel().closeFuture().syncUninterruptibly();
	}
}
