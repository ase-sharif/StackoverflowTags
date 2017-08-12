import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import config.Configuration;
import sql.MysqlProxy;

/**
 * The TagReader class
 *
 * @author Alireza Aghamohammadi
 */
public class TagReader {
  private final static String QUERY = "SELECT " + Configuration.COLUMN_NAME.get() + " FROM " + Configuration.TABLE_NAME.get() + " WHERE " + Configuration.COLUMN_NAME.get() + " IS NOT NULL";
  private final static Path PATH_FILE = Paths.get("tags.txt");
  private final static String LT = "<";
  private final static String GT = ">";
  private final static String SPACE = " ";
  private final static String EMPTY = "";

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   * @throws SQLException the sql exception
   * @throws IOException  the io exception
   */
  public static void main(String[] args) throws SQLException, IOException {
    TagReader reader = new TagReader();
    reader.word2vec();

  }

  /**
   * Write tags to file.
   *
   * @param tags the tags
   */
  private void writeTagsToFile(String tags) throws IOException {
    // clean tags, remove < and > from tags.
    tags = tags.replace(LT, SPACE).replace(GT, SPACE).replaceAll("(?<!\\S)" + Configuration.TAG_FILTER.get() + "(?!\\S)", EMPTY).trim();
    if (tags.length() > 0) {
      tags = tags + "\n";
      Files.write(PATH_FILE, tags.getBytes(), StandardOpenOption.APPEND);
    }
  }

  private void createFile() throws IOException {
    if (!Files.exists(PATH_FILE)) {
      Files.createFile(PATH_FILE);
    } else {
      Files.delete(PATH_FILE);
      Files.createFile(PATH_FILE);
    }
  }

  /**
   * Fetch all tags.
   *
   * @throws SQLException the sql exception
   */
  private void fetchAllTags() throws SQLException, IOException {
    MysqlProxy dbProxy = new MysqlProxy();
    dbProxy.connect();
    Statement st = dbProxy.getConnection().createStatement();
    ResultSet resultSet = st.executeQuery(QUERY);
    while (resultSet.next()) {
      String tags = resultSet.getString(Configuration.COLUMN_NAME.get());
      writeTagsToFile(tags);
    }
  }
}
