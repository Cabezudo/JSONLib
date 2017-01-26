package net.cabezudo.json.values;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.cabezudo.json.Position;

/**
 * A {@link JSONBoolean} is an object extended from {@link JSONValue} object in order to represent a
 * boolean that can be used to create JSON structures.
 *
 * @author <a href="http://cabezudo.net">Esteban Cabezudo</a>
 * @version 1.0
 * @since 1.7
 * @date 10/01/2014
 */
public class JSONBoolean extends JSONValue<JSONBoolean> {

  /**
   * A {@code JSONBoolean} {@code false} object.
   */
  public static final JSONBoolean FALSE = new JSONBoolean(false, null);
  /**
   * A {@code JSONBoolean} {@code true} object.
   */
  public static final JSONBoolean TRUE = new JSONBoolean(true, null);

  private final Boolean value;

  /**
   * Return a {@code JSONBoolean} that correspond with the {@code boolean} value passed.
   *
   * @param value a {@code boolean} value.
   * @return Return a {@code JSONBoolean}.
   */
  public static JSONBoolean get(boolean value) {
    if (value) {
      return TRUE;
    } else {
      return FALSE;
    }
  }

  private JSONBoolean(boolean value, Position position) {
    super(position);
    this.value = value;
  }

  /**
   * Compares this {@code JSONBoolean} with another.
   *
   * @param jsonBoolean the {@code JSONBoolean} to be compared.
   * @return zero if this object represents the same boolean value as the argument; a positive value
   * if this object represents {@code true} and the argument represents {@code false}; and a
   * negative value if this object represents {@code false} and the argument represents {@code true}
   */
  @Override
  public int compareTo(JSONBoolean jsonBoolean) {
    return value.compareTo(jsonBoolean.toBoolean());
  }

  /**
   * Return the referenced element for {@code this} object. For a {@code JSONBoolean} object,
   * {@code this} object and the referenced version is the same.
   *
   * @return {@code this} object.
   */
  @Override
  public JSONBoolean toReferencedElement() {
    return this;
  }

  /**
   * Returns whether the element is a {@code JSONBoolean} or not.
   *
   * @return {@code true}.
   */
  @Override
  public boolean isBoolean() {
    return true;
  }

  /**
   * Convert {@code this} object to a {@code BigDecimal} object.
   *
   * @return a {@code BigDecimal} object with a value of {@code 1} it {@code this} represent
   * {@code true}; {@code 0} otherwise.
   */
  @Override
  public BigDecimal toBigDecimal() {
    int numericValue = value ? 1 : 0;
    return BigDecimal.valueOf(numericValue);
  }

  /**
   * Convert {@code this} object to a {@code BigInteger} object.
   *
   * @return a {@code BigInteger} object with a value of {@code 1} it {@code this} represent
   * {@code true}; {@code 0} otherwise.
   */
  @Override
  public BigInteger toBigInteger() {
    int numericValue = value ? 1 : 0;
    return BigInteger.valueOf(numericValue);
  }

  /**
   * Convert {@code this} object to a {@code Boolean} object.
   *
   * @return a {@code Boolean.TRUE} object it {@code this} represent {@code true}; {@code 0}
   * otherwise.
   */
  @Override
  public Boolean toBoolean() {
    return value;
  }

  /**
   * Convert {@code this} object to a {@code Byte} object.
   *
   * @return a {@code Byte} object with a value of {@code 1} it {@code this} represent {@code true};
   * {@code 0} otherwise.
   */
  @Override
  public Byte toByte() {
    return (byte) (value ? 1 : 0);
  }

  /**
   * Convert {@code this} object to a {@code Character} object.
   *
   * @return a {@code Character} object with a value of {@code '1'} it {@code this} represent
   * {@code true}; {@code '0'} otherwise.
   */
  @Override
  public Character toCharacter() {
    return (value ? '1' : '0');
  }

  /**
   * Convert {@code this} object to a {@code Double} object.
   *
   * @return a {@code Double} object with a value of {@code 1} it {@code this} represent
   * {@code true}; {@code 0} otherwise.
   */
  @Override
  public Double toDouble() {
    return (double) (value ? 1 : 0);
  }

  /**
   * Convert {@code this} object to a {@code Float} object.
   *
   * @return a {@code Float} object with a value of {@code 1} it {@code this} represent
   * {@code true}; {@code 0} otherwise.
   */
  @Override
  public Float toFloat() {
    return (float) (value ? 1 : 0);
  }

  /**
   * Convert {@code this} object to a {@code Integer} object.
   *
   * @return a {@code Integer} object with a value of {@code 1} it {@code this} represent
   * {@code true}; {@code 0} otherwise.
   */
  @Override
  public Integer toInteger() {
    return (value ? 1 : 0);
  }

  /**
   * Convert {@code this} object to a {@code String} object.
   *
   * @return a {@code String} object with a value of {@code true} it {@code this} represent
   * {@code true}; {@code false} otherwise.
   */
  @Override
  public String toJSON() {
    return value ? "true" : "false";
  }

  /**
   * Convert {@code this} object to a {@link JSONArray} object.
   *
   * @return a {@link JSONArray} object with only {@code this} element.
   */
  @Override
  public JSONArray toJSONArray() {
    JSONArray jsonArray = new JSONArray();
    jsonArray.add(this);
    return jsonArray;
  }

  /**
   * Convert {@code this} object to a {@link JSONString} object using {@code toString()} method.
   *
   * @return a {@link JSONString} object.
   */
  @Override
  public JSONString toJSONString() {
    return new JSONString(value.toString(), getPosition());
  }

  /**
   * Convert {@code this} object to a {@code List} of {@link JSONValue} objects.
   *
   * @return a {@code List} of {@link JSONValue} with {@code this} element.
   */
  @Override
  public List<JSONValue> toList() {
    List<JSONValue> list = new ArrayList<>();
    list.add(this);
    return list;
  }

  /**
   * Convert {@code this} object to a {@code Byte} object.
   *
   * @return a {@code Byte} object with a value of {@code 1} it {@code this} represent {@code true};
   * {@code 0} otherwise.
   */
  @Override
  public Long toLong() {
    return (long) (value ? 1 : 0);
  }

  /**
   * Convert {@code this} object to a {@code Byte} object.
   *
   * @return a {@code Byte} object with a value of {@code 1} it {@code this} represent {@code true};
   * {@code 0} otherwise.
   */
  @Override
  public Short toShort() {
    return (short) (value ? 1 : 0);
  }

  /**
   * Convert {@code this} object to an array of {@code String} objects.
   *
   * @return an array of {@code String} object with one element converted using {@code toString()}
   * method.
   */
  @Override
  public String[] toStringArray() {
    String[] s = new String[1];
    s[0] = value.toString();
    return s;
  }
}
