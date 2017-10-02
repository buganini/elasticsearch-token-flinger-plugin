Token-Flinger try to identify the type of token then apply proper filter on it.

## Current implementation

Type | Filter
-----|-------
Ascii Only | Unchanged
Other | NGram


## Example

Input | Output
------|-------
test abc5566 | test abc abc5 abc55 bc5 bc55 bc556 c55 c556 c5566 556 5566 566
abc123 Citroën | abc abc1 abc12 bc1 bc12 bc123 c12 c123 123 Cit Citr Citro itr itro itroë tro troë troën roë roën oën
```


-----------------------------
This project is sponsored by [Gamela Enterprise Co., Ltd.](https://www.gamela.com.tw)