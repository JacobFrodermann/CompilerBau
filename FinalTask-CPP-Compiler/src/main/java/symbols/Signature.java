package symbols;

import ast.Type;
import java.util.List;
import java.util.Objects;

public record Signature(String name, List<Type> paramTypes, List<Boolean> paramRefs) {

  public Signature {
    paramTypes = List.copyOf(paramTypes);
    paramRefs = List.copyOf(paramRefs);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Signature other)) return false;

    if (!name.equals(other.name)) return false;
    if (paramTypes.size() != other.paramTypes.size()) return false;

    for (int i = 0; i < paramTypes.size(); i++) {
      if (!paramTypes.get(i).name().equals(other.paramTypes.get(i).name())) {
        return false;
      }
      if (!paramRefs.get(i).equals(other.paramRefs.get(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(name);
    for (int i = 0; i < paramTypes.size(); i++) {
      result = 31 * result + paramTypes.get(i).name().hashCode();
      result = 31 * result + (paramRefs.get(i) ? 1 : 0);
    }
    return result;
  }

  // Für Debug/Logging
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(name);
    sb.append("(");
    for (int i = 0; i < paramTypes.size(); i++) {
      if (i > 0) sb.append(", ");
      sb.append(paramTypes.get(i).name());
      if (paramRefs.get(i)) sb.append("&");
    }
    sb.append(")");
    return sb.toString();
  }
}
