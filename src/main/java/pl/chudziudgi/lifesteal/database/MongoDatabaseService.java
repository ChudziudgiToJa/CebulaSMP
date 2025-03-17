package pl.chudziudgi.lifesteal.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

@Getter
public final class MongoDatabaseService {

    private final MongoDatabase mongoDatabase;
    private final MongoClient mongoClient;

    public MongoDatabaseService() {
        MongoClientURI clientURI = new MongoClientURI("mongodb://admin:admin@144.76.76.68:25557");

        this.mongoClient = new MongoClient(clientURI);
        this.mongoDatabase = this.mongoClient.getDatabase("lifesteal");
    }
}
