package pl.chudziudgi.lifesteal.database.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ClassTypeAdapter extends TypeAdapter<Class<?>> {

    @Override
    public void write(JsonWriter out, Class<?> value) throws IOException {
        out.value(value.getName());
    }

    @Override
    public Class<?> read(JsonReader in) throws IOException {
        try {
            return Class.forName(in.nextString());
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }
}
