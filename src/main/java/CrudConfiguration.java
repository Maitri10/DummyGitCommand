import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
@Getter
@Setter
public class CrudConfiguration extends Configuration {
    @JsonProperty("message")
    private String message;

//    @Valid
//    @NotNull
//    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("url")
    private String url;



    @JsonProperty("database")
    public DataSourceFactory database;
    //public DataSourceFactory getDataSourceFactory() {
//        return database;
//    }

//    @JsonProperty("database")
//    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
//        this.database = dataSourceFactory;
//    }


}
