package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;

public class V2__INDEX_DATABASE extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        String url = context.getConnection().getMetaData().getURL();
        String database = "pulsewsc7d004881d924624903afe81641a5d8d";
        String tenantName = "67051f9afc2fe811f805d30f";
        String jdbcURL = StringUtils.replace(url, "test", database);
        String insertQuery = "INSERT INTO INDEX_DATABASE values ('%s','Active','postgresql','org.postgresql.Driver','%s','test','test','%s','')";

        String query = String.format(insertQuery, tenantName, jdbcURL, tenantName);

        try (PreparedStatement statement = context.getConnection()
                .prepareStatement(query)) {
            statement.execute();
        }

    }
}
