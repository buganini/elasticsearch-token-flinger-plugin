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
                        "tokenizer" : "standard",
                        "filter" : [
                            "token_flinger_with_params"
                        ]
                    }
                },
                "filter": {
                    "token_flinger_with_params": {
                        "type": "token_flinger",
                        "unspecific_min_gram": 3,
                        "unspecific_max_gram": 5
                    }
                },
            }
        }
    }
)
ptoken("test abc5566")
ptoken("abc123 Citroën")
