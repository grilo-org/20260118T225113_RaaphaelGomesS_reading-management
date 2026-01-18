package tech.gomes.reading.management.indicator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TemplateStatusIndicator {
    IN_ANALYZE ("Analisando"),
    VERIFIED ("Verificado"),
    DECLINE ("Recusado"),
    INACTIVE ("Desativado");

    private String value;


}
