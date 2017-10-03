from elasticsearch import Elasticsearch
es = Elasticsearch()

def ptoken(text):
    r = es.indices.analyze(
        index=index_name,
        body={
            "text": text
        },
    )
    print("{} => {}".format(
        text,
        " ".join([t["token"] for t in r["tokens"]])
    ))

index_name = "token-flinger"

es.indices.delete(index=index_name, ignore=[400, 404])
es.indices.create(
    index=index_name,
    ignore=400,
    body={
        "settings": {
            "analysis" : {
                "analyzer" : {
                    "default" : {
                        "tokenizer" : "icu_tokenizer",
                        "filter" : [
                            "token_flinger_with_params"
                        ]
                    }
                },
                "filter": {
                    "token_flinger_with_params": {
                        "type": "token_flinger",
                        "cjk_min_gram": 2,
                        "cjk_max_gram": 3,
                        "unspecific_min_gram": 3,
                        "unspecific_max_gram": 5
                    }
                },
            }
        }
    }
)
ptoken("test abc5566")
ptoken("abc123 Citroën (NFC)")
ptoken("abc123 Citroe\u0308n (NFD)")
ptoken("test阿茲海默症初めまして한글")
