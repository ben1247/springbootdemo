package com.shuyun.sbd.utils.netty.marshalling;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Component:
 * Description:
 * Date: 16/8/28
 *
 * @author yue.zhang
 */
public final class MarshallingCodeCFactory {

    /**
     * 创建JBoss Marshalling 解码器 MarshallingDecoder
     *
     * @return
     */
    public static MarshallingDecoder buildMarshallingDecoder() {

        // serial 表示创建的是java序列化工厂对象，它由jboss-marshalling-serial-XXX.jar提供
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory,configuration);
        // 1024 表示单个消息序列化后的最大长度
        MarshallingDecoder decoder = new MarshallingDecoder(provider,1024);
        return decoder;

    }

    /**
     * 创建JBoss Marshalling 编码器 MarshallingEncoder
     *
     * @return
     */
    public static MarshallingEncoder buildMarshallingEncoder() {

        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory,configuration);
        // MarshallingEncoder 用于将实现序列化接口的POJ对象序列化为二进制数组
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }
}
