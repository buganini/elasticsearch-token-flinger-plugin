Token-Flinger try to identify the type of token then apply proper filter on it.

## Current implementation

Type | Filter
-----|-------
Latin | Unchanged
Other | NGram


## Example (test.py)

Input | Output
------|-------
test abc5566 | test abc abc5 abc55 bc5 bc55 bc556 c55 c556 c5566 556 5566 566
abc123 Citroën (NFC) | abc abc1 abc12 bc1 bc12 bc123 c12 c123 123 Citroën NFC
abc123 Citroën (NFD) | abc abc1 abc12 bc1 bc12 bc123 c12 c123 123 Citroën NFD
test中文測試初めまして한글 | test 中 中文 文 測 測試 試 初 初め め ま まし まして し して て 한 한글 글

-----------------------------
This project is sponsored by [Gamela Enterprise Co., Ltd.](https://www.gamela.com.tw)
