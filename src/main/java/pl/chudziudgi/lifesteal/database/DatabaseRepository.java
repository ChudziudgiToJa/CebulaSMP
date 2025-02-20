package pl.chudziudgi.lifesteal.database;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.model.Filters;
import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.database.repository.CrudRepository;
import pl.chudziudgi.lifesteal.database.repository.Identifiable;

@Getter
public class DatabaseRepository<ID, T extends Identifiable<ID>> implements CrudRepository<ID, T> {

    private static final Executor EXECUTOR = Executors.newFixedThreadPool(4);
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseRepository.class);
    private final String documentName;
    private final String collection;
    private final Class<T> type;
    protected Gson gson;

    public DatabaseRepository(Class<T> type, String documentName, String collection) {
        this(type, documentName, collection, SurvivalPlugin.GSON);
    }

    public DatabaseRepository(Class<T> type, String documentName, String collection, Gson gson) {
        this.type = type;
        this.documentName = documentName;
        this.collection = collection;
        this.gson = gson;
    }


    @Override
    public void update(T object, ID field, UpdateType updateType) {
        if (updateType.equals(UpdateType.CREATE)) {
            EXECUTOR.execute(() -> SurvivalPlugin.getInstance()
                    .getMongoDatabaseService()
                    .getMongoDatabase()
                    .getCollection(this.collection)
                    .insertOne(Document.parse(SurvivalPlugin.GSON.toJson(object))));
            return;
        }
        if (updateType.equals(UpdateType.UPDATE)) {
            EXECUTOR.execute(() -> SurvivalPlugin.getInstance()
                    .getMongoDatabaseService()
                    .getMongoDatabase()
                    .getCollection(this.collection)
                    .findOneAndUpdate(new Document(this.documentName, field),
                            new Document("$set", Document.parse(SurvivalPlugin.GSON.toJson(object)))));

            return;
        }
        if (updateType.equals(UpdateType.REMOVE)) {
            EXECUTOR.execute(() -> SurvivalPlugin.getInstance()
                    .getMongoDatabaseService()
                    .getMongoDatabase()
                    .getCollection(this.collection)
                    .findOneAndDelete(new Document(this.documentName, field)));
        }
    }

    public void removeRawMany(String nickName, int id) {
        EXECUTOR.execute(() -> SurvivalPlugin.getInstance()
                .getMongoDatabaseService()
                .getMongoDatabase()
                .getCollection(this.collection)
                .deleteMany(new Document(this.documentName, nickName).append("id", id)));
    }

    @Override
    public Collection<T> saveAll(Collection<T> objects) {
        return null;
    }

    @Override
    public T find(ID id) {
        return SurvivalPlugin.GSON.fromJson(SurvivalPlugin.getInstance().getMongoDatabaseService().getMongoDatabase().getCollection(this.collection)
                .find(Filters.eq(this.documentName, id)).first().toJson(JsonWriterSettings.builder().build()), this.type);
    }

    @Override
    public Collection<T> findAll() {
        Collection<T> collection = new HashSet<>();
        SurvivalPlugin.getInstance().getMongoDatabaseService().getMongoDatabase().getCollection(this.collection).find().forEach((Block<? super Document>) (Document document) -> {
            T object = SurvivalPlugin.GSON.fromJson(document.toJson(JsonWriterSettings.builder().build()), this.getType());
            collection.add(object);
        });

        LOGGER.info("Loaded {} {}", collection.size(), this.collection);

        return collection;
    }

    @Override
    public boolean delete(T object) {
        return false;
    }

}
