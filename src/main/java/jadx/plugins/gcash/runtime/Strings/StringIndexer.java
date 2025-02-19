package jadx.plugins.gcash.runtime.Strings;

import java.io.IOException;

import jadx.plugins.gcash.c1f688530.a1acc86d0;


/**
 * The StringIndexer class provides a mechanism to retrieve strings based on a given identifier.
 * It extends the {@link a1acc86d0} class and implements a singleton pattern to ensure a single instance.
 */
public class StringIndexer extends a1acc86d0 {
    private static volatile StringIndexer instance;

	/**
	 * Protected constructor to prevent instantiation from outside the class.
	 *
	 * @throws IOException if an I/O error occurs during initialization
	 */
    protected StringIndexer() throws IOException {
    }

	/**
	 * Retrieves the string associated with the given identifier.
	 *
	 * @param s the string identifier
	 * @return the corresponding string
	 * @throws IOException if an I/O error occurs during string retrieval
	 */
    public static String _getString(String s) throws IOException {
        return StringIndexer.getInstance()._internal_getString(s);
    };

	/**
	 * Returns the singleton instance of the StringIndexer class.
	 *
	 * @return the singleton instance
	 * @throws IOException if an I/O error occurs during instance creation
	 */
    private static StringIndexer getInstance() throws IOException {
        if (instance == null) {
            synchronized (StringIndexer.class) {
                if (instance == null) {
                    instance = new StringIndexer();
                }
            }
        }
        return instance;
    }
}

