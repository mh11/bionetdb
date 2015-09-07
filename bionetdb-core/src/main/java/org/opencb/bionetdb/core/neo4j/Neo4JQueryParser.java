package org.opencb.bionetdb.core.neo4j;

import org.opencb.bionetdb.core.api.NetworkDBAdaptor;
import org.opencb.datastore.core.Query;
import org.opencb.datastore.core.QueryOptions;

/**
 * Created by imedina on 03/09/15.
 */
public class Neo4JQueryParser {

    public String parse(Query query, QueryOptions options) {

        StringBuilder cypherQuery = new StringBuilder();

        if (query.get(NetworkDBAdaptor.NetworkQueryParams.PE_ID.key()) != null && !query.getString(NetworkDBAdaptor.NetworkQueryParams.PE_ID.key()).isEmpty()) {
            cypherQuery.append("MATCH (" + query.get(NetworkDBAdaptor.NetworkQueryParams.PE_ID.key()) + ") -[:XREF] - (NODE)");
            cypherQuery.append("");
        }

        if (query.get(NetworkDBAdaptor.NetworkQueryParams.PE_TYPE.key()) != null && !query.getString(NetworkDBAdaptor.NetworkQueryParams.PE_TYPE.key()).isEmpty()) {
            cypherQuery.append("MATCH (" + query.get(NetworkDBAdaptor.NetworkQueryParams.PE_TYPE.key()) + ") -[:XREF] - (NODE)");
            cypherQuery.append("");
        }

        return cypherQuery.toString();
    }

}
