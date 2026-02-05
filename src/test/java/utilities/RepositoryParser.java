package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RepositoryParser {
    private FileInputStream fileInputStream;
    private String fileName;
    private Properties properties;

    public RepositoryParser(String fileName) throws IOException {
        this.fileName = fileName;
        fileInputStream = new FileInputStream(this.fileName);
        properties.load(fileInputStream);
    }

    public String getData(String key){
        return properties.getProperty(key);
    }
}
