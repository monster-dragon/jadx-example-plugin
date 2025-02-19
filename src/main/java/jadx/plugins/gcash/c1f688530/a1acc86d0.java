package jadx.plugins.gcash.c1f688530;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

/**
 * Abstract class a1acc86d0 provides the base functionality for handling string retrieval
 * from a database file. It includes methods for reading and parsing the database file,
 * as well as retrieving strings based on their identifiers.
 *
 * <p>This class is designed to be extended by other classes that require string
 * retrieval functionality. It handles the initialization of the database and provides
 * utility methods for converting file content to byte arrays and parsing integer values
 * from the byte array.
 *
 * <p>Subclasses should implement their own specific logic for string retrieval
 * by extending this class.
 */
public abstract class a1acc86d0 {
    private final byte[] db;
    private final int strings_db_index_size;

	/**
	 * Protected constructor to initialize the database and index size.
	 *
	 * @throws IOException if an I/O error occurs during initialization
	 */
    protected a1acc86d0() throws IOException {
        this.db = getDb();
        this.strings_db_index_size = this.g642ff278(0);
    }

	/**
	 * Retrieves the string associated with the given identifier.
	 *
	 * @param s the string identifier
	 * @return the corresponding string
	 */
    public String _internal_getString(String s) {
        return this.v981a6acd(Integer.parseInt(s));
    }

	/**
	 * Parses an integer value from the byte array at the specified index.
	 *
	 * @param v the index in the byte array
	 * @return the parsed integer value
	 */
    public int g642ff278(int v) {
        byte[] arr_b = this.db;
        return arr_b[v] & 0xFF | (arr_b[v + 3] << 24 | (arr_b[v + 2] & 0xFF) << 16 | (arr_b[v + 1] & 0xFF) << 8);
    }

	/**
	 * Retrieves the string at the specified index in the byte array.
	 *
	 * @param v the index of the string
	 * @return the corresponding string
	 */
    public String v981a6acd(int v) {
        int v1 = (v - 1) * 8 + 4;
        int v2 = this.g642ff278(v1);
        int v3 = this.g642ff278(v1 + 4);
        return new String(this.db, this.strings_db_index_size + 4 + v2, v3 - 1, StandardCharsets.UTF_8);
    }

	/**
	 * Converts the content of a file to a byte array.
	 *
	 * @param filePath the path to the file
	 * @return the byte array representation of the file content
	 * @throws IOException if an I/O error occurs during file reading
	 */
    public static byte[] fileToByteArray(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        String[] values = content.split(",");
        byte[] byteArray = new byte[values.length];

        for (int i = 0; i < values.length; i++) {
            byteArray[i] = Byte.parseByte(values[i].trim());
        }

        return byteArray;
    }

	/**
	 * Retrieves the database byte array from the specified file.
	 *
	 * @return the database byte array
	 * @throws IOException if an I/O error occurs during file reading
	 */
    public static byte[] getDb() throws IOException {
        return fileToByteArray("src/main/resources/gcash/string_db.txt");
    };
}

