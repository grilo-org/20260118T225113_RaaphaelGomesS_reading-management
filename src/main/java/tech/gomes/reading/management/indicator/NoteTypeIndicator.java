package tech.gomes.reading.management.indicator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum NoteTypeIndicator {
    QUICK ("Rápida"),
    REFERENCE ("Referência"),
    PERMANENT ("Permanente");

    private String value;

    public static NoteTypeIndicator getTypeByName(String name) {

        for (NoteTypeIndicator indicator : NoteTypeIndicator.values()) {
            if (indicator.value.equalsIgnoreCase(name)) {
                return indicator;
            }
        }
        return null;
    }
}
