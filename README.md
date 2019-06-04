# Sample-Kafka-Application
Instructions along with sample code base for basic kafka project

#Source: https://www.udemy.com/apache-kafka/

<p>
<h1> Installation Steps in Mac OS X </h1>
<ul>
  <li>Install brew if needed: /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)" </li> 
  <li> Download and Setup Java 8 JDK: 
      <ol>
        <li>brew tap caskroom/versions</li>
        <li>brew cask install java8</li>
      </ol>
  </li>
  <li>Download & Extract the Kafka binaries from https://kafka.apache.org/downloads</li>
  <li>Install Kafka commands using brew: <b>brew install kafka</b></li>
  <li>Create data/zookeeper, data/kafka in <kafka_2.1…> folder</li>
  <li>Edit Zookeeper & Kafka configs using a text editor
      <ol>
        <li>zookeeper.properties: dataDir=/your/path/to/data/zookeeper</li>
        <li>server.properties: log.dirs=/your/path/to/data/kafka</li>
    </ol>
  </li>
</ul>
</p>

<p>
<h1>Sample Command Lines</h1>
<ul>
  <li>Start Zookeeper (run in installed kafka directory): <b>zookeeper-server-start config/zookeeper.properties</b></li>
  <li>Start Kafka (run in installed kafka directory): <b>kafka-server-start config/server.properties</b></li>  
    <li> Kafka Topics 
      <ol>
        <li>Create Kafka Topic: <b>kafka-topics --zookeeper 127.0.0.1:2181 --topic topic1 --create --partitions 3 --replication-factor 1</b></li>
        <li>List Kafka Topics: <b>kafka-topics --zookeeper 127.0.0.1:2181 --list</b></li>
        <li>Describe Topic: <b>kafka-topics --zookeeper 127.0.0.1:2181 --topic topicName --describe</b></li>
        <li>Delete Topic: <b>kafka-topics --zookeeper 127.0.0.1:2181 --topic topicName --delete</b></li> 
      </ol>
    </li>
    <li> Kafka Producer Consumer
      <ol>
        <li>produce message for a particular topic: <b>kafka-console-producer --broker-list 127.0.0.1:9092 --topic topic1</b></li>
        <li>Consume message from  topic from beginning: <b>kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic topic1 --from-beginning </b></li>
        <li>Consume with consumer group: <b>kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic topic1 --group consumer-group-1</b></li>
        <li>List all consumer groups: <b>kafka-consumer-groups --bootstrap-server localhost:9092 --list</b></li>
      </ol>
    </li> 
</ul>
</p>






