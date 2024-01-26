package com.tfg.skilltree.testutil;

import com.tfg.skilltree.testutil.exception.FileProblemException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.commons.io.IOUtils;

public final class FileHelper {

  public File getFile(String fileName) {
    URL url = this.getClass().getResource(fileName);
    return new File(url.getFile());
  }

  public static String getContent(String fileName) {
    final String content;
    try {
      content = IOUtils.toString(
              Objects.requireNonNull(FileHelper.class.getResourceAsStream(fileName)),
              StandardCharsets.UTF_8
      );
    } catch (IOException e) {
      throw new FileProblemException(fileName, e);
    }
    return content;
  }
}

