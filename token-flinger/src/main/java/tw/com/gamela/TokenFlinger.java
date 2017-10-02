package tw.com.gamela;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenFlinger extends TokenFilter {
    public static class Config {
        public final static int DEFAULT_UNSPECIFIC_MIN_NGRAM_SIZE = 3;
        public final static int DEFAULT_UNSPECIFIC_MAX_NGRAM_SIZE = 7;

        public int unspecificMinGram;
        public int unspecificMaxGram;

    }

    private char[] curTermBuffer;
    private int curTermLength;
    private int curCodePointCount;
    private int curGramSize;
    private int curPos;
    private int curPosInc;
    private State state;

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final PositionIncrementAttribute posIncAtt;

    private Config config;

    public TokenFlinger(TokenStream tokenStream, Config config){
        super(tokenStream);

        this.config = config;

        posIncAtt = addAttribute(PositionIncrementAttribute.class);
    }

    private Pattern asciiOnly = Pattern.compile("^[A-Za-z]+$");

    @Override
    public boolean incrementToken() throws IOException {
        while (true) {
            if (curTermBuffer == null) {
                if (!input.incrementToken()) {
                    return false;
                } else {
                    curTermBuffer = termAtt.buffer().clone();
                    curTermLength = termAtt.length();
                    curCodePointCount = Character.codePointCount(termAtt, 0, termAtt.length());
                    curGramSize = config.unspecificMinGram;
                    curPos = 0;
                    curPosInc = posIncAtt.getPositionIncrement();
                    state = captureState();

                    CharSequence cs = new CharSequence() {
                        @Override
                        public int length() {
                            return curTermLength;
                        }

                        @Override
                        public char charAt(int i) {
                            return (char) Character.codePointAt(curTermBuffer, i);
                        }

                        @Override
                        public CharSequence subSequence(int i, int i1) {
                            return null;
                        }
                    };

                    if(asciiOnly.matcher(cs).matches()){
                        curTermBuffer = null;
                        return true;
                    }
                }
            }

            if (curGramSize > config.unspecificMaxGram || (curPos + curGramSize) > curCodePointCount) {
                ++curPos;
                curGramSize = config.unspecificMinGram;
            }
            if ((curPos + curGramSize) <= curCodePointCount) {
                restoreState(state);
                final int start = Character.offsetByCodePoints(curTermBuffer, 0, curTermLength, 0, curPos);
                final int end = Character.offsetByCodePoints(curTermBuffer, 0, curTermLength, start, curGramSize);
                termAtt.copyBuffer(curTermBuffer, start, end - start);
                posIncAtt.setPositionIncrement(curPosInc);
                curPosInc = 0;
                curGramSize++;
                return true;
            }
            curTermBuffer = null;
        }
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        curTermBuffer = null;
    }
}