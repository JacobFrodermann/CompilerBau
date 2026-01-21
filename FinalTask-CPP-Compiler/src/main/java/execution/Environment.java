package execution;

import java.util.HashMap;
import java.util.Map;

public class Environment {
  private final Map<String, Object> values = new HashMap<>();
  private final Environment parent;

  public Environment(Environment parent) {
    this.parent = parent;
  }

  public void define(String name, Object value) {
    values.put(name, value);
  }

  public Object get(String name) {
    if (values.containsKey(name)) {
      return values.get(name);
    }
    if (parent != null) {
      return parent.get(name);
    }
    throw new RuntimeException("Undefined variable: " + name);
  }

  public void set(String name, Object value) {
    if (values.containsKey(name)) {
      values.put(name, value);
      return;
    }
    if (parent != null) {
      parent.set(name, value);
      return;
    }
    throw new RuntimeException("Undefined variable: " + name);
  }
}
