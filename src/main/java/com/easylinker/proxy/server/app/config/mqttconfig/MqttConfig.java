package com.easylinker.proxy.server.app.config.mqttconfig;

import com.easylinker.proxy.server.app.config.mqttconfig.adapter.EMqttPahoMessageDrivenChannelAdapter;
import com.easylinker.proxy.server.app.config.mqttconfig.handler.ClientCmdReplyMessageHandler;
import com.easylinker.proxy.server.app.config.mqttconfig.handler.ClientOnAndOfflineWillMessageHandler;
import com.easylinker.proxy.server.app.config.mqttconfig.handler.InMessageHandler;
import com.easylinker.proxy.server.app.config.mqttconfig.handler.RealTimeMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;

/**
 * mqtt核心配置
 * Created by wwhai on 2018/3/14.
 */
@Configuration
public class MqttConfig {
    @Value("${emq.host}")
    private String LOCALHOST_EMQ_URL;
    @Value("${emq.username}")
    private String LOCALHOST_EMQ_USERNAME;
    @Value("${emq.password}")
    private String LOCALHOST_EMQ_PASSWORD;
    Logger logger = LoggerFactory.getLogger(MqttConfig.class);

    @Autowired
    ClientOnAndOfflineWillMessageHandler clientOnAndOfflineWillMessageHandler;
    @Autowired
    InMessageHandler inMessageHandler;
    @Autowired
    ClientCmdReplyMessageHandler clientCmdReplyMessageHandler;
    @Autowired
    RealTimeMessageHandler realTimeMessageHandler;


    /**
     * mqtt 的工厂  用来创建mqtt连接
     *
     * @return
     */

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs(LOCALHOST_EMQ_URL);
        factory.setUserName(LOCALHOST_EMQ_USERNAME);
        factory.setPassword(LOCALHOST_EMQ_PASSWORD);
        return factory;
    }

    /**
     * 监控设备盒子上下线
     *
     * @return
     */
    @Bean("MqttClientOnOrOffLineMessageListenerInbound")
    public MessageProducerSupport getMqttClientOnOrOffLineMessageListener() {
        MqttPahoMessageDrivenChannelAdapter adapter = new EMqttPahoMessageDrivenChannelAdapter(
                "MqttClientOnOrOffLineMessageListenerInbound",
                mqttClientFactory());
        adapter.addTopic("$SYS/brokers/+/clients/+/#");//监控设备消息上下线
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        return adapter;
    }


    /**
     * 监控设备盒子上下线发进来的消息监听器
     *
     * @return
     */
    @Bean("MqttClientOnOrOffLineMessageListener")
    public IntegrationFlow mqttClientOnOrOffLineMessageListenerInFlow() {
        return IntegrationFlows.from(getMqttClientOnOrOffLineMessageListener())
                .handle(clientOnAndOfflineWillMessageHandler)
                .get();
    }


    /**
     * 盒子发进来的消息消息接收
     *
     * @return
     */
    @Bean("MqttClientInMessageListenerInbound")
    public MessageProducerSupport getMqttClientInMessageListener() {
        MqttPahoMessageDrivenChannelAdapter adapter = new EMqttPahoMessageDrivenChannelAdapter(
                "MqttClientInMessageListenerInbound",
                mqttClientFactory());
        //OUT/DEVICE/DEFAULT_USER/DEFAULT_GROUP/ID  为客户端SUB的TOPIC
        adapter.addTopic("IN/DEVICE/+/+/#");//监控设备publish的消息
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        return adapter;

    }


    /**
     * 监控设备盒子发进来的消息接受处理器
     *
     * @return
     */
    @Bean("MqttClientInMessageListenerInFlow")
    public IntegrationFlow mqttClientInMessageListenerInFlow() {
        return IntegrationFlows.from(getMqttClientInMessageListener())
                .handle(inMessageHandler)
                .get();
    }


    /**
     * 安装一个CMD命令回复topic监控器
     *
     * @return
     */

    @Bean("ClientCmdReplyMessageHandler")
    public MqttPahoMessageDrivenChannelAdapter getClientCmdReplyMessageHandler() {
        MqttPahoMessageDrivenChannelAdapter adapter = new EMqttPahoMessageDrivenChannelAdapter(
                "ClientCmdReplyMessageHandler",
                mqttClientFactory());
        //CMD/IN/所有命令回复
        adapter.addTopic("CMD/IN/#");//监控设备接到命令回复的消息
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        return adapter;
    }

    /**
     * CMD命令监控消息接收器
     *
     * @return
     */
    @Bean("clientCmdReplyMessageInflow")
    public IntegrationFlow mqttClientCmdReplyMessageInflow() {
        return IntegrationFlows.from(getClientCmdReplyMessageHandler())
                .handle(clientCmdReplyMessageHandler)
                .get();
    }


    /**
     * 实时消息
     *
     * @return
     */

    @Bean("RealTimeMessageHandler")
    public MqttPahoMessageDrivenChannelAdapter getRealTimeMessageHandler() {
        MqttPahoMessageDrivenChannelAdapter adapter = new EMqttPahoMessageDrivenChannelAdapter(
                "RealTimeMessageHandler",
                mqttClientFactory());
        adapter.addTopic("OUT/REAL_TIME/#");//实时消息
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        return adapter;
    }

    /**
     *
     * @return
     */

    @Bean("mqttRealTimeMessageInflow")
    public IntegrationFlow mqttRealTimeMessageInflow() {
        return IntegrationFlows.from(getRealTimeMessageHandler())
                .handle(realTimeMessageHandler)
                .get();
    }

}
