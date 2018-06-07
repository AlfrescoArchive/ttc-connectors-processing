# BluePrint - Trending Topic Campaigns: Dummy Twitter Connector
This project is an implementation of an Activiti Cloud Connector, 
which is a simple bi-directional 3rd party system-to-system integration using Spring Cloud Streams.
 
This project provides the interaction with an external system that should provide Text and Image processing and 
Sentiment Analysis capabilities. 

This connector provides a placeholder for doing more advanced social media content processing, such as text cleaning,
image recognition and advanced classification of content. It does sentiment analysis based on the Standford NLP library. 

# Run

In order to run this project locally, you need to clone the source code and then run inside the root directory

> mvn -Dserver.port=808x spring-boot:run

**Note**: replace "x" for your desired port number

You can use the following docker-compose file in order to start Rabbit MQ so the service can connect and send messages.



# Endpoints
- GET http://localhost:808x/ -> welcome message



