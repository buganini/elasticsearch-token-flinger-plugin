package org.elasticsearch.plugin.analysis.tokenflinger;

import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;
import tw.com.gamela.TokenFlingerFactory;

import java.util.Map;

import static java.util.Collections.singletonMap;

public class TokenFlingerPlugin extends Plugin implements AnalysisPlugin {
    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
        return singletonMap("token_flinger", TokenFlingerFactory::new);
    }
}
