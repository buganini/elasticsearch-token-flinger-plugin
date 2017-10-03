package tw.com.gamela;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;

public class TokenFlingerFactory extends AbstractTokenFilterFactory {
    TokenFlinger.Config config = new TokenFlinger.Config();

    public TokenFlingerFactory(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super(indexSettings, name, settings);

        config.cjkMinGram = settings.getAsInt("cjk_min_gram", TokenFlinger.Config.DEFAULT_CJK_MIN_NGRAM_SIZE);
        config.cjkMaxGram = settings.getAsInt("cjk_max_gram", TokenFlinger.Config.DEFAULT_CJK_MAX_NGRAM_SIZE);

        config.unspecificMinGram = settings.getAsInt("unspecific_min_gram", TokenFlinger.Config.DEFAULT_UNSPECIFIC_MIN_NGRAM_SIZE);
        config.unspecificMaxGram = settings.getAsInt("unspecific_max_gram", TokenFlinger.Config.DEFAULT_UNSPECIFIC_MAX_NGRAM_SIZE);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new TokenFlinger(tokenStream, config);
    }
}
