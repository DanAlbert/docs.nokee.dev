
package com.example.greeter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;

public class NativeLoader {

	public static void loadLibrary(ClassLoader classLoader, String libName) {
		try {
			System.loadLibrary(libName);
		} catch (UnsatisfiedLinkError ex) {
			URL url = classLoader.getResource(libFilename(libName));
			try {
				File file = Files.createTempFile("jni", "greeter").toFile();
				file.deleteOnExit();
				file.delete();
				try (InputStream in = url.openStream()) {
					Files.copy(in, file.toPath());
				}
				System.load(file.getCanonicalPath());
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}

	private static String libFilename(String libName) {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("win") >= 0) {
			return libName + ".dll";
		} else if (osName.indexOf("mac") >= 0) {
			return "lib" + libName + ".dylib";
		}
		return "lib" + libName + ".so";
	}
}
