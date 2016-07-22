package org.opencb.bionetdb.app.cli;

import org.opencb.bionetdb.core.api.NetworkDBAdaptor;
import org.opencb.bionetdb.core.config.DatabaseConfiguration;
import org.opencb.bionetdb.core.neo4j.Neo4JNetworkDBAdaptor;
import org.opencb.commons.datastore.core.Query;
import org.opencb.commons.datastore.core.QueryResult;

/**
 * Created by imedina on 28/09/15.
 */
public class QueryCommandExecutor extends CommandExecutor {

    private CliOptionsParser.QueryCommandOptions queryCommandOptions;

    public QueryCommandExecutor(CliOptionsParser.QueryCommandOptions queryCommandOptions) {
        super(queryCommandOptions.commonOptions.logLevel, queryCommandOptions.commonOptions.conf);

        this.queryCommandOptions = queryCommandOptions;
    }

    @Override
    public void execute() {

        try {
            if (queryCommandOptions.database == null || queryCommandOptions.database.isEmpty()) {
                queryCommandOptions.database = "unknown";

                DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(queryCommandOptions.database, null);
                databaseConfiguration.setHost(queryCommandOptions.host);
                databaseConfiguration.setPort(queryCommandOptions.port);
                databaseConfiguration.setUser(queryCommandOptions.user);
                databaseConfiguration.setPassword(queryCommandOptions.password);

                configuration.getDatabases().add(databaseConfiguration);
            }

            NetworkDBAdaptor networkDBAdaptor = new Neo4JNetworkDBAdaptor(queryCommandOptions.database, configuration);

//            networkDBAdaptor.get(null, null);
            if (queryCommandOptions.betweenness) {
                Query query = new Query("id", queryCommandOptions.id);
//                query.put("nodeLabel", queryCommandOptions.nodeType);
                query.put(NetworkDBAdaptor.NetworkQueryParams.TYPE.key(), queryCommandOptions.nodeType);

                networkDBAdaptor.betweenness(query);
            }

            if (queryCommandOptions.clusteringCoeff) {
                Query query = new Query("id", queryCommandOptions.id);
                query.put("nodeLabel", queryCommandOptions.nodeType);

                QueryResult queryResult = networkDBAdaptor.clusteringCoefficient(query);
                System.out.println("queryResult = " + queryResult);
            }

            Query query = new Query();
            if (queryCommandOptions.id != null && !queryCommandOptions.id.isEmpty()) {
//                Query query = new Query(NetworkDBAdaptor.NetworkQueryParams.PE_ID.key(), queryCommandOptions.id);
                query.put(NetworkDBAdaptor.NetworkQueryParams.PE_ID.key(), queryCommandOptions.id);

            }

            if (org.apache.commons.lang3.StringUtils.isNotEmpty(queryCommandOptions.nodeType)) {
                query.put(NetworkDBAdaptor.NetworkQueryParams.TYPE.key(), queryCommandOptions.nodeType);
            }

            if (org.apache.commons.lang3.StringUtils.isNotEmpty(queryCommandOptions.cellularLocation)) {
                query.put(NetworkDBAdaptor.NetworkQueryParams.PE_CELLOCATION.key(), queryCommandOptions.cellularLocation);
            }
            networkDBAdaptor.getNodes(query, null);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
