package utils.pojos.catalog;

import org.apache.commons.chain.Catalog;

public class CatalogRoot
{
    private CatalogData data;

    public void setData(CatalogData data){
        this.data = data;
    }
    public CatalogData getData(){
        return this.data;
    }
}