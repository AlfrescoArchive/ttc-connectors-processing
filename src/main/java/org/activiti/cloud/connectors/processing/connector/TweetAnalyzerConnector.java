package org.activiti.cloud.connectors.processing.connector;

import java.util.HashMap;
import java.util.Map;

import org.activiti.cloud.connectors.processing.analyzer.NLP;
import org.activiti.cloud.connectors.starter.channels.IntegrationResultSender;
import org.activiti.cloud.connectors.starter.model.IntegrationRequestEvent;
import org.activiti.cloud.connectors.starter.model.IntegrationResultEvent;
import org.activiti.cloud.connectors.starter.model.IntegrationResultEventBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static net.logstash.logback.marker.Markers.append;

@Component
@EnableBinding(ProcessingConnectorChannels.class)
public class TweetAnalyzerConnector {

    private final Logger logger = LoggerFactory.getLogger(TweetAnalyzerConnector.class);
    @Value("${spring.application.name}")
    private String appName;

    private final IntegrationResultSender integrationResultSender;

    public TweetAnalyzerConnector(IntegrationResultSender integrationResultSender) {
        this.integrationResultSender = integrationResultSender;
    }

    @StreamListener(value = ProcessingConnectorChannels.TWITTER_ANALYZER_CONSUMER)
    public void analyzeEnglishTweet(IntegrationRequestEvent event) throws InterruptedException {

        String tweet = String.valueOf(event.getVariables().get("text"));

        logger.info(append("service-name",
                           appName),
                    ">>> About to run the Sentiment Analisys over:" + tweet);

        // based on http://rahular.com/twitter-sentiment-analysis/

        int sentiment = NLP.findSentiment(tweet);
        String attitude = "neutral";
        if (sentiment >= 3) {
            attitude = "positive";
        } else if (sentiment < 1) {
            attitude = "negative";
        }

        Map<String, Object> results = new HashMap<>();
        results.put("attitude",
                    attitude);
        results.put("matched",
                    "true");

        logger.info(append("service-name",
                           appName),
                    ">>> Analyzed tweet with sentiment " + results.get("attitude"));

        Message<IntegrationResultEvent> message = IntegrationResultEventBuilder.resultFor(event)
                .withVariables(results)
                .buildMessage();

        integrationResultSender.send(message);
    }
}
