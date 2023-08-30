package utils.pojos.catalog;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor

public class CatalogData {
    private ArrayList<CatalogCategory> categories;
}
