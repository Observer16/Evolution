package utils.pojos.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CatalogCategory {
    private Integer id;
    private String type;
    private String code;
    private String title;
    private Integer nodeId;
    private Object parentNodeId;
    private CatalogMedia media;
    private Integer amount;
}
