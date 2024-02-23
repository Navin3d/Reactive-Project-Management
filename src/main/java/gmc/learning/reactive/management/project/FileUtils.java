package gmc.learning.reactive.management.project;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {
	
	public static void deleteFilesByName(String directoryPath, String fileNameToDelete) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null)
            for (File file : files)
                if (file.isFile() && file.getName().equals(fileNameToDelete))
                    if (file.delete())
                        log.info("File " + file.getName() + " deleted successfully.");
                    else
                    	log.error("Failed to delete file " + file.getName());
    }

}
