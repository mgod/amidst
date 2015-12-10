package amidst.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import amidst.utilities.UpdateInformationRetriever;

public class UpdatePrompt {
	private UpdateInformationRetriever retriever = new UpdateInformationRetriever();
	private MainWindow mainWindow;
	private boolean silent;

	public void checkSilently(MainWindow mainWindow) {
		check(mainWindow, true);
	}

	public void check(MainWindow mainWindow) {
		check(mainWindow, false);
	}

	private void check(MainWindow mainWindow, boolean silent) {
		this.mainWindow = mainWindow;
		this.silent = silent;
		try {
			doCheck();
		} catch (MalformedURLException e) {
			error("Error connecting to update server: Malformed URL.");
		} catch (IOException e) {
			error("Error reading update data.");
		} catch (URISyntaxException e) {
			error("Error parsing update URL.");
		} catch (NullPointerException e) {
			error("Error \"NullPointerException\" in update.");
		}
	}

	private void error(String message) {
		if (!silent) {
			mainWindow.displayError(message);
		}
	}

	private void doCheck() throws IOException, URISyntaxException {
		retriever.check();
		if (!retriever.isSuccessful()) {
			error(retriever.getErrorMessage());
		} else if (getUserChoice()) {
			openUpdateURL();
		}
	}

	private boolean getUserChoice() {
		if (retriever.isNewMajorVersionAvailable()) {
			return mainWindow.askToConfirm("Update Found",
					"A new version was found. Would you like to update?");
		} else if (retriever.isNewMinorVersionAvailable()) {
			return mainWindow.askToConfirm("Update Found",
					"A minor revision was found. Update?");
		} else if (!silent) {
			mainWindow.displayMessage("Updater", "There are no new updates.");
		}
		return false;
	}

	private void openUpdateURL() throws IOException, URISyntaxException {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				desktop.browse(new URI(retriever.getUpdateURL()));
			} else {
				mainWindow.displayError("Unable to open browser page.");
			}
		} else {
			mainWindow.displayError("Unable to open browser.");
		}
	}
}
