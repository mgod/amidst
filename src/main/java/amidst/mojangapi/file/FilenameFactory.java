package amidst.mojangapi.file;

import java.io.File;

import amidst.documentation.Immutable;

@Immutable
public enum FilenameFactory {
	;

	private static final String REMOTE_PREFIX = "https://s3.amazonaws.com/Minecraft.Download/versions/";
	private static final String MIDDLE_SERVER = "/minecraft_server.";
	private static final String MIDDLE_CLIENT = "/";
	private static final String JAR_FILE_EXTENSION = ".jar";
	private static final String JSON_FILE_EXTENSION = ".json";

	public static File getClientJsonFile(File prefix, String versionId) {
		return getClientFile(prefix, versionId, JSON_FILE_EXTENSION);
	}

	public static File getClientJarFile(File prefix, String versionId) {
		return getClientFile(prefix, versionId, JAR_FILE_EXTENSION);
	}

	private static File getClientFile(File prefix, String versionId, String fileExtension) {
		return new File(prefix, getClientLocation("", versionId, fileExtension));
	}

	public static String getRemoteClientJson(String versionId) {
		return getClientJson(REMOTE_PREFIX, versionId);
	}

	public static String getClientJson(String prefix, String versionId) {
		return getClientLocation(prefix, versionId, JSON_FILE_EXTENSION);
	}

	public static String getRemoteClientJar(String versionId) {
		return getClientJar(REMOTE_PREFIX, versionId);
	}

	public static String getClientJar(String prefix, String versionId) {
		return getClientLocation(prefix, versionId, JAR_FILE_EXTENSION);
	}

	private static String getClientLocation(String prefix, String versionId, String fileExtension) {
		return prefix + versionId + MIDDLE_CLIENT + versionId + fileExtension;
	}

	public static String getRemoteServerJar(String versionId) {
		return getServerJar(REMOTE_PREFIX, versionId);
	}

	public static String getServerJar(String prefix, String versionId) {
		return getServerLocation(prefix, versionId, JAR_FILE_EXTENSION);
	}

	private static String getServerLocation(String prefix, String versionId, String fileExtension) {
		return prefix + versionId + MIDDLE_SERVER + versionId + fileExtension;
	}
}
