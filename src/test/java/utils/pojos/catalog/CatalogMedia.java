package utils.pojos.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CatalogMedia {
    private String type;
    private String caption;
    private String path;
    private String alternative;
}
