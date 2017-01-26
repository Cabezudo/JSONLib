package net.cabezudo.json;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://cabezudo.net">Esteban Cabezudo</a>
 * @date 10/01/2014
 * @version 1.0
 * @since 1.7
 */
public class Formatter {

  private static String ident(int n) {
    StringBuilder sb = new StringBuilder(n * 3);
    for (int i = 0; i < n; i++) {
      sb.append("   ");
    }
    return sb.toString();
  }

  /**
   * Add carriages returns and indent the given {@code String} and return the {@code String} with
   * the JSON indented.
   *
   * @param jsonString the JSON {@code String} with the JSON string to indent.
   * @return the JSON string indented.
   */
  public static String indent(String jsonString) {
    StringBuilder sb = new StringBuilder();
    List<String> lines = new ArrayList<>();

    char[] cs = jsonString.toCharArray();
    for (int i = 0; i < cs.length; i++) {
      char c = cs[i];
      do {
        if (c == '{' || c == '[' || c == ',' || c == '\n') {
          sb.append(c);
          String line = sb.toString().trim();
          lines.add(line);
          sb = new StringBuilder();
          break;
        }
        if (c == '}' || c == ']') {
          String line = sb.toString().trim();
          if (line.length() > 0) {
            lines.add(line);
            sb = new StringBuilder();
          }
          line = new Character(c).toString();
          if (i + 1 < cs.length) {
            c = cs[i + 1];
            if (c == ',') {
              line += c;
              i++;
            }
          }
          lines.add(line);
          break;
        }
        sb.append(c);
      } while (false);
    }

    int ident = 0;
    sb = new StringBuilder();
    for (String line : lines) {

      char endChar;
      if (line.length() > 1) {
        endChar = line.charAt(line.length() - 1);
      } else {
        endChar = line.charAt(0);
      }
      do {

        if (endChar == '{' || endChar == '[') {
          sb.append(ident(ident));
          sb.append(line);
          sb.append('\n');
          ident++;
          break;
        }

        if (endChar == '}' || endChar == ']' || line.endsWith("},") || line.endsWith("],")) {
          ident--;
          sb.append(ident(ident));
          sb.append(line);
          sb.append('\n');

          break;
        }

        sb.append(ident(ident));
        sb.append(line);
        sb.append('\n');
      } while (false);
    }
    return sb.toString();
  }

  private Formatter() {
  }
}
