# Basic-Kafka-Project
Instructions along with sample code base for basic kafka project

#Source: https://www.udemy.com/apache-kafka/

<p>
<h1> Installation Steps in Mac OS X </h1>
<hr/>
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
  <li>Start Zookeeper: <b>zookeeper-server-start config/zookeeper.properties</b></li>
  <li>Start Kafka: <b>kafka-server-start config/server.properties</b></li>
</ul>
</p>

<p>
<h1>Steps to run the application</h1>
<hr/>
<ul>
  <li>cd <kafka installation directory> </li>
  <li>zookeeper-server-start config/zookeeper.properties</li>
  <li>kafka-server-start config/server.properties </li>  
</ul>
</p>






