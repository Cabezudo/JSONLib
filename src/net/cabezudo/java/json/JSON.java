package net.cabezudo.java.json;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.cabezudo.java.json.annotations.JSONProperty;
import net.cabezudo.java.json.exceptions.EmptyQueueException;
import net.cabezudo.java.json.exceptions.JSONParseException;
import net.cabezudo.java.json.exceptions.NotPropertiesException;
import net.cabezudo.java.json.exceptions.ObjectException;
import net.cabezudo.java.json.exceptions.ReadFileException;
import net.cabezudo.java.json.values.JSONArray;
import net.cabezudo.java.json.values.JSONNull;
import net.cabezudo.java.json.values.JSONObject;
import net.cabezudo.java.json.values.JSONValue;

/**
 * Provide the methods for parse and create JSON objects.
 * <p>
 * JSON class allow you to parse a string with JSON format in to a JSON tree to manipulate. You can also get the string from a file on disk.
 * The JSON class allow you to create a JSON tree from any Java object and create a tree using the references to the object
 * <h4>Parse</h4>
 * <p>
 * You can create from a string a JSON tree that use objects to represent the JSON string. You can take parts of the tree, add elements,
 * delete elements and set new values.
 * <p>
 * Also, you can use a file disk like origin for the data to create the JSON tree.
 * <h4>JSON tree</h4>
 * The JSON tree is a tree formed for objects inherited from {@link net.cabezudo.java.json.JSONValue} that represent the JSON elements.
 * <p>
 * This elements has diferents properties and you hava many methods to work with its. The elements are null null {@link net.cabezudo.java.json.JSONArray JSONArray},
 * {@link net.cabezudo.java.json.values.JSONArray JSONArray}, {@link net.cabezudo.java.json.values.JSONBoolean JSONBoolean}, {@link net.cabezudo.java.json.values.JSONNull JSONNull},
 * {@link net.cabezudo.java.json.values.JSONNumber JSONNumber}, {@link net.cabezudo.java.json.values.JSONObject JSONObject}, and
 * {@link net.cabezudo.java.json.values.JSONString JSONString}. You can get the values from the elements tree using dig methods to reach the
 * element or value you want or navigate through the tree getting the elements and theirs values one by one.
 * <h4>JSON referenced tree</h4>
 * <p>
 * A referenced tree is a normal tree transformed into a small tree using references that you can specify for the objects. The easy way to
 * get a referency tree is using normal objects to create a JSONTree and than create a referenced tree. That is because the referenced
 * information isn't in the JSON string. If you create a tree from Java normal objects or using the elements one by one you can specify the
 * field that going to be used to create the referenced tree. The JSON elements has or can have the reference information but not that JESON
 * trees created parsing a JSON string. In order to set the fiel reference for a object you can use
 * {@link net.cabezudo.java.json.JSONNull#setReferenceFieldName(String referenceFieldName) }.
 *
 * @author <a href="http://cabezudo.net">Esteban Cabezudo</a>
 * @version 1.0
 * @since 1.7
 * @date 10/01/2014
 */
public class JSON {

  /**
   *
   */
  public static final String SIMPLE_DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  private static Object getFieldValue(Object object, String getterName) {
    Method method;
    Class<?> objectClass = object.getClass();
    try {
      method = objectClass.getMethod(getterName);
    } catch (NoSuchMethodException e) {
      throw new ObjectException("I can't find the getter '" + getterName + "' in the object " + objectClass.getName(), e);
    }
    try {
      return method.invoke(object);
    } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
      throw new ObjectException("Getting the field value using " + getterName + " in the object " + objectClass.getName() + ".", e);
    }
  }

  private static String getGetterName(Field field, String fieldName) {
    Class<?> fieldType = field.getType();
    String getterPrefix;

    if (fieldType == boolean.class
        || fieldType == Boolean.class) {
      getterPrefix = "is";
    } else {
      getterPrefix = "get";
    }
    return getterPrefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
  }

  private static List<Field> getInheritedFields(Class<?> clazz) {
    List<Field> fields = new ArrayList<>();
    for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
      fields.addAll(Arrays.asList(c.getDeclaredFields()));
    }
    return fields;
  }

  /**
   *
   * @param string
   * @return
   * @throws JSONParseException
   */
  public static JSONValue parse(String string) throws JSONParseException {
    Tokens tokens = Tokenizer.tokenize(string);
    JSONValue jsonElement;

    Token token;
    try {
      token = tokens.poll();
    } catch (EmptyQueueException e) {
      throw new JSONParseException("Unexpected end parsing " + string + ".", e, Position.INITIAL);
    }

    TokenType tokenType = token.getType();
    JSONFactory jsonFactory = new JSONFactory();
    Log.debug("Token type: %s.%n", tokenType);

    switch (tokenType) {
      case LEFT_BRACE:
        jsonElement = jsonFactory.getJSONObject(tokens);
        break;
      case LEFT_BRACKET:
        jsonElement = jsonFactory.getJSONArray(tokens);
        break;
      default:
        throw new JSONParseException("Unexpected token: " + token, token.getPosition());
    }
    return jsonElement;
  }

  /**
   *
   * @param filePath
   * @param encoding
   * @return
   * @throws JSONParseException
   * @throws net.cabezudo.java.json.exceptions.ReadFileException
   */
  public static JSONValue parse(Path filePath, String encoding) throws JSONParseException, ReadFileException {
    byte[] data;
    try {
      data = Files.readAllBytes(filePath);
    } catch (IOException e) {
      throw new ReadFileException("Can't read the file " + filePath + ".", e);
    }
    String jsonString;
    try {
      jsonString = new String(data, encoding);
    } catch (UnsupportedEncodingException e) {
      throw new ReadFileException("Bad encoding for file " + filePath + " using " + encoding + ".", e);
    }
    return parse(jsonString);
  }

  public static String toEscapedString(String string) {
    StringBuilder sb = new StringBuilder();
    char[] chars = string.toCharArray();
    for (char c : chars) {
      switch (c) {
        case '"':
          sb.append('\\');
          sb.append(c);
          break;
        case '\b':
          sb.append("\\b");
          break;
        case '\f':
          sb.append("\\f");
          break;
        case '\n':
          sb.append("\\n");
          break;
        case '\r':
          sb.append("\\r");
          break;
        case '\t':
          sb.append("\\t");
          break;
        case '\\':
          sb.append("\\\\");
          break;
        default:
          sb.append(c);
          break;
      }
    }
    return sb.toString();
  }

  public static JSONArray toJSONArray(List<?> list) {
    JSONArray jsonArray = new JSONArray();

    for (Object object : list) {
      jsonArray.add(object);
    }
    return jsonArray;
  }

  public static JSONObject toJSONObject(Object object) {
    return toJSONTree(object).toObject();
  }

  /**
   *
   * @param jsonValue
   * @return
   */
  public static JSONValue toJSONReferencedTree(JSONValue jsonValue) {
    if (jsonValue instanceof JSONObject) {
      JSONObject jsonReferencedObject = new JSONObject();
      JSONObject jsonObject = (JSONObject) jsonValue;

      for (JSONPair jsonPair : jsonObject) {
        JSONElement referencedElement = jsonPair.getValue().getReferencedElement();
        Log.debug("The referenced element returned by getReferencedElement for a %s with the value %s is %s.%n",
            jsonPair.getClass().getName(), jsonPair.toJSON(), referencedElement.toJSON());

        JSONPair newJSONPair = new JSONPair(jsonPair.getKey(), referencedElement);
        jsonReferencedObject.add(newJSONPair);
      }

      return jsonReferencedObject;
    }

    if (jsonValue instanceof JSONArray) {
      JSONArray jsonReferencedArray = new JSONArray();
      JSONArray jsonArray = (JSONArray) jsonValue;

      for (JSONElement jsonArrayElement : jsonArray) {
        JSONElement referencedElement = jsonArrayElement.getReferencedElement();
        Log.debug("The referenced element returned by getReferencedElement for %s is %s.%n", jsonArrayElement.toJSON(), referencedElement.toJSON());
        jsonReferencedArray.add(referencedElement);
      }

      return jsonReferencedArray;
    }

    return (JSONValue) jsonValue.getReferencedElement();
  }

  /**
   *
   * @param object
   * @return
   */
  public static JSONValue toJSONTree(Object object) {
    if (object == null) {
      Log.debug("The object is null. Return a JSONNull object.%n");
      return JSONNull.getValue();
    }
    if (object instanceof JSONValue) {
      Log.debug("Return from toJSONTree() DOING NOTHING because the object is a JSONValue. The object is a %s.%n", object.getClass().getName());
      return (JSONValue) object;
    }
    Log.debug("Using the method toJSONTree() with parameter of type %s in order to get a JSONValue tree.%n", object.getClass().getName());

    JSONValue jsonValue;

    if (Iterable.class.isAssignableFrom(object.getClass())) {
      Log.debug("The parameter %s is ITERABLE.%n", object.getClass().getName());
      Iterable iterable = (Iterable) object;
      JSONArray jsonArray = new JSONArray();
      for (Object child : iterable) {
        Log.debug("Call the method toJSONTree() using the child object %s with the value %s.%n", child.getClass().getCanonicalName(), child);
        jsonValue = toJSONTree(child);
        Log.debug("Add to jsonArray %s the object of type %s with value %s.%n", jsonArray.toJSON(), jsonValue.getClass().getName(), jsonValue);
        jsonArray.add(jsonValue);
      }
      Log.debug("Return the object of type %s with value %s from the method toJSONTree().%n", jsonArray.getClass().getName(), jsonArray.toJSON());
      return jsonArray;
    }
    if (object.getClass().getSimpleName().startsWith("[")) {
      Log.debug("Native array in method toJSONTree for the object of type %s.%n", object.getClass().getName());
      JSONArray jsonArray = new JSONArray();
      Object[] array = (Object[]) object;

      for (Object child : array) {
        Log.debug("Call toJSONTree with child of type %s and value %s.%n", child.getClass().getName(), child);
        jsonValue = toJSONTree(child);
        jsonArray.add(jsonValue);
      }
      Log.debug("Return the object %s with value %s from method toJSONTree().%n", jsonArray.getClass().getName(), jsonArray.toJSON());
      return jsonArray;
    }

    Log.debug("Call the JSON factory with object of type %s.%n", object.getClass().getName());
    jsonValue = JSONFactory.get(object);

    if (jsonValue != null) {
      Log.debug("Return the object %s with value %s from method toJSONTree().%n", jsonValue.getClass().getName(), jsonValue.toJSON());
      return jsonValue;
    }

    Log.debug("Plain object found for %s in method toJSONTree().%n", object.getClass().getName());
    JSONObject jsonObject = new JSONObject();

    Class<?> objectClass = object.getClass();

    List<Field> fieldsInObject = getInheritedFields(objectClass);

    Log.debug("The number of fields in the object %s is %s.%n", object.getClass().getName(), fieldsInObject.size());

    for (Field field : fieldsInObject) {
      String fieldName = field.getName();
      Log.debug("Field name %s.%n", fieldName);

      JSONProperty property;
      Annotation annotation = field.getAnnotation(JSONProperty.class);
      if (annotation != null) {
        Log.debug("Found JSONProperty annotation in field %s.%n", fieldName);
        property = (JSONProperty) annotation;
        String propertyName = property.name();
        if (!JSONProperty.DEFAULT_NAME.equals(propertyName)) {
          fieldName = propertyName;
        }

        String getterName = getGetterName(field, fieldName);
        Object fieldValue = getFieldValue(object, getterName);
        Log.debug("The field value for %s in the object of type %s is %s.%n", fieldName, object.getClass().getName(), fieldValue);

        jsonValue = JSONFactory.get(fieldValue);

        if (jsonValue == null) {
          Log.debug("The value from the factory is null so we try hard.%n");
          Log.debug("Call the method toJSONTree() using the object of type %s with value %s.%n", fieldValue.getClass().getName(), fieldValue);
          jsonValue = toJSONTree(fieldValue);
        } else {
          if (property.dontShowIfNull() && jsonValue instanceof JSONNull) {
            continue;
          }

          if (jsonValue.isNumber() && property.dontShowIfZero() && jsonValue.toInteger() == 0) {
            continue;
          }
        }
        jsonObject.setReferenceFieldName(property.field());
        JSONPair jsonPair = new JSONPair(fieldName, jsonValue);
        Log.debug("Add pair %s to JSONObject %s.%n", jsonPair.toJSON(), jsonObject.toJSON());
        jsonObject.add(jsonPair);
      }
    }

    if (!jsonObject.hasChilds()) {
      throw new NotPropertiesException("The object " + object.getClass().getName() + " doesn't have properties.");
    }

    Log.debug("Return the object of type %s with value %s from the method toJSONTree() for the parameter of type %s.%n",
        jsonObject.getClass().getName(), jsonObject.toJSON(), object.getClass().getName());

    return jsonObject;
  }

  private JSON() {
    // Nothing to do. Just protect the object construction.
  }

}