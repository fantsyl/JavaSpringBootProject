package com.example.demo.infrastructure.handler;

import com.example.demo.infrastructure.util.JsonToString;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.SneakyThrows;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

public class JsonToStringDeserializerHandler extends JsonSerializer<Object> implements ContextualSerializer {

  private boolean isRounding;
  private int scale;
  private RoundingMode mode;
  private boolean isDecimalFormat;
  private int maximumFractionDigits;
  private int minimumFractionDigits;
  private boolean isGroupingUsed;

  protected boolean getIsRounding(BeanProperty property) {
    return property.getAnnotation(JsonToString.class).isRounding();
  }

  protected int getScale(BeanProperty property) {
    return property.getAnnotation(JsonToString.class).scale();
  }

  protected RoundingMode getMode(BeanProperty property) {
    return property.getAnnotation(JsonToString.class).mode();
  }

  protected boolean getIsDecimalFormat(BeanProperty property) {
    return property.getAnnotation(JsonToString.class).isDecimalFormat();
  }

  protected int getMaximumFractionDigits(BeanProperty property) {
    return property.getAnnotation(JsonToString.class).maximumFractionDigits();
  }

  protected int getMinimumFractionDigits(BeanProperty property) {
    return property.getAnnotation(JsonToString.class).minimumFractionDigits();
  }

  protected boolean getIsGroupingUsed(BeanProperty property) {
    return property.getAnnotation(JsonToString.class).isGroupingUsed();
  }

  @Override
  public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) {
  }

  @SneakyThrows
  @Override
  public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
    isRounding = getIsRounding(property);
    scale = getScale(property);
    mode = getMode(property);
    isDecimalFormat = getIsDecimalFormat(property);
    maximumFractionDigits = getMaximumFractionDigits(property);
    minimumFractionDigits = getMinimumFractionDigits(property);
    isGroupingUsed = getIsGroupingUsed(property);

    JavaType type = property.getType();

    if (type.isTypeOrSubTypeOf(BigDecimal.class)) {
      return new BigDecimalJsonDeserializer();
    }

    if (type.isTypeOrSubTypeOf(Double.class)) {
      return new DoubleJsonDeserializer();
    }

    if (type.isTypeOrSubTypeOf(Integer.class)) {
      return new IntegerJsonDeserializer();
    }

    if (type.isTypeOrSubTypeOf(Long.class)) {
      return new LongJsonDeserializer();
    }

    if (type.isTypeOrSubTypeOf(Byte.class)) {
      return new ByteJsonDeserializer();
    }

    if (type.isTypeOrSubTypeOf(Boolean.class)) {
      return new BooleanJsonDeserializer();
    }

    return null;
  }

  void writeStringFormat(JsonGenerator gen, BigDecimal value) throws IOException {
    if (isRounding) {
      value = value.setScale(scale, mode);
    }

    if (isDecimalFormat) {
      DecimalFormat df = new DecimalFormat();
      df.setMaximumFractionDigits(maximumFractionDigits);
      df.setMinimumFractionDigits(minimumFractionDigits);
      df.setGroupingUsed(isGroupingUsed);
      gen.writeString(df.format(value));
    } else {
      gen.writeString(String.valueOf(value));
    }

  }

  private class BigDecimalJsonDeserializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      if (Objects.isNull(value)) {
        serializers.defaultSerializeNull(gen);
      } else {
        writeStringFormat(gen, value);
      }
    }
  }

  private class DoubleJsonDeserializer extends JsonSerializer<Double> {

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      if (Objects.isNull(value)) {
        serializers.defaultSerializeNull(gen);
      } else {
        writeStringFormat(gen, BigDecimal.valueOf(value));
      }
    }
  }

  private static class IntegerJsonDeserializer extends JsonSerializer<Integer> {

    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      if (Objects.isNull(value)) {
        serializers.defaultSerializeNull(gen);
      } else {
        gen.writeString(String.valueOf(value));
      }
    }
  }

  private static class LongJsonDeserializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      if (Objects.isNull(value)) {
        serializers.defaultSerializeNull(gen);
      } else {
        gen.writeString(String.valueOf(value));
      }
    }
  }

  private static class ByteJsonDeserializer extends JsonSerializer<Byte> {

    @Override
    public void serialize(Byte value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      if (Objects.isNull(value)) {
        serializers.defaultSerializeNull(gen);
      } else {
        gen.writeString(String.valueOf(value));
      }
    }
  }

  private static class BooleanJsonDeserializer extends JsonSerializer<Boolean> {

    @Override
    public void serialize(Boolean value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      if (Objects.isNull(value)) {
        serializers.defaultSerializeNull(gen);
      } else {
        gen.writeString(String.valueOf(value));
      }
    }
  }

}
