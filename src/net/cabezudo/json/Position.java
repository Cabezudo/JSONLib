/**
 * MIT License
 *
 * Copyright (c) 2017 Esteban Cabezudo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.cabezudo.json;

/**
 * @author <a href="http://cabezudo.net">Esteban Cabezudo</a>
 * @version 1.00, 09/16/2016
 */
public class Position {

  public static final Position INITIAL = new Position(1, 1);
  private final int row;
  public final int line;

  Position(int line, int row) {
    this.line = line;
    this.row = row;
  }

  /**
   * Return a primitive {@code int} with the line number of the position.
   *
   * @return the line number for the position.
   */
  public int getLine() {
    return line;
  }

  /**
   * Return a primitive {@code int} with the row number of the position.
   *
   * @return the row number for the position.
   */
  public int getRow() {
    return row;
  }

  @Override
  public String toString() {
    return " Line " + getLine() + ", row " + getRow();
  }
}
