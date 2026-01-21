package execution;

import java.util.HashMap;
import java.util.Map;
import symbols.Symbol;

public class ObjectInstance {
  private final Symbol.ClassSymbol classSymbol;
  private final Map<String, Object> fields = new HashMap<>();

  public ObjectInstance(Symbol.ClassSymbol classSymbol) {
    this.classSymbol = classSymbol;
    initializeFields();
  }

  private void initializeFields() {
    // Initialisiere alle Felder mit Default-Werten (inkl. geerbte)
    Symbol.ClassSymbol current = classSymbol;
    while (current != null) {
      for (var entry : current.getAllFields().entrySet()) {
        String fieldName = entry.getKey();
        Symbol.VariableSymbol field = entry.getValue();
        if (!fields.containsKey(fieldName)) {
          fields.put(fieldName, getDefaultValue(field.type().name()));
        }
      }
      current = current.baseClass();
    }
  }

  private Object getDefaultValue(String typeName) {
    return switch (typeName) {
      case "int" -> 0;
      case "bool" -> false;
      case "char" -> '\0';
      case "string" -> "";
      default -> throw new RuntimeException("Cannot auto-initialize class field: " + typeName);
    };
  }

  public Symbol.ClassSymbol getClassSymbol() {
    return classSymbol;
  }

  public Object getField(String name) {
    if (fields.containsKey(name)) {
      return fields.get(name);
    }
    throw new RuntimeException("Field not found: " + name);
  }

  public void setField(String name, Object value) {
    fields.put(name, value);
  }

  public boolean hasField(String name) {
    return fields.containsKey(name);
  }
}
