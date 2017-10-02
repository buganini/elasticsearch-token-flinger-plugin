Token-Flinger try to identify the type of token then apply proper filter on it.

## Current implementation

Type | Filter
-----|-------
Latin | Unchanged
Other | NGram


## Example

Input | Output
------|-------
test abc5566 | test abc abc5 abc55 bc5 bc55 bc556 c55 c556 c5566 556 5566 566
abc123 Citroën (NFC) | abc abc1 abc12 bc1 bc12 bc123 c12 c123 123 Citroën NFC
abc123 Citroën (NFD) | abc abc1 abc12 bc1 bc12 bc123 c12 c123 123 Citroën NFD

-----------------------------
This project is sponsored by [Gamela Enterprise Co., Ltd.](https://www.gamela.com.tw)