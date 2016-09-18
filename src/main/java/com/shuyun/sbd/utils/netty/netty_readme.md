#Netty权威指南第二版读书笔记
##比较实用的系统ChannelHandler总结如下：
####1. 系统编解码框架---ByteToMessageCodec
####2. 通用基于长度的半包解码器---LengthFieldBasedFrameDecoder
####3. 码流日志打印Handler---LoggingHandler
####4. SSL安全认证Handler---SslHandler
####5. 链路空闲检测Handler---IdleStateHandler
####6. 流量整形Handler---ChannelTrafficShapingHandler
####7. Base64编解码---Base64Decoder 和 Base64Encoder

##NioEventLoopGroup
NioEventLoopGroup实际就是Reactor线程池，负责调度和执行客户端的接入，网络读写事件的处理，用户自定义任务和定时任务的执行

##option(ChannelOption.SO_BACKLOG,100)
backlog指定了内核为此套接口排队的最大连接个数，netty默认为100