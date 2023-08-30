package utils.pojos.catalog;
import org.apache.commons.chain.Catalog;

import java.util.ArrayList;
import java.util.List;
public class CatalogData
{
    private ArrayList<CatalogCategories> categories;

    public void setCategories(ArrayList<CatalogCategories> categories){
        this.categories = categories;
    }
    public ArrayList<CatalogCategories> getCategories(){
        return this.categories;
    }
}