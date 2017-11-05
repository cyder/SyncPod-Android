# contributing
このプロジェクトに参加する前に以下を必ず読むこと。

## commit messageの注意
このフォーマットに従うこと: `"[commit type] コミットの概要"`

commit typeは以下のものを使用すること:
* **fix**: バグ修正
* **hotfix**: 重大なバグ修正
* **add**: 関数やファイルの追加
* **update**: バグ修正ではない関数の変更
* **clean**: 整理 (リファクタリングなど)
* **change**: 仕様の変更
* **disable**: 無効化 (コメントアウトなど)
* **remove**: 関数やファイルの削除
* **upgrade**: バージョンアップ
* **revert**: 事前のコミットをキャンセル

## Issue / Pull Requestの注意
### Issueのタグ使い分け
Issueには必ず以下のいずれかを付与し、状況が変化した場合はそれに合わせて変更すること。
* **standy by**: 待機中。コンフリクトの危険がある、仕様が固まっていない等、まだこのIssueの対応をしてほしくないとき。
* **to do**: 対応待ち。
* **doing**: 現在誰かが対応しているとき。

また、状況に応じて以下のタグを付与する。
* **question**: 他の人の意見を聞きたいとき。
* **hot fix**: 修正が緊急を要する時。

また、Issueをcloseする際、必要に応じて以下のタグを付与する。
* **duplicate**: 他のIssueと重複した場合。
* **wontfix**: 修正の必要が無い場合。

### Pull Requestのタグ使い分け
Pull Requestには必ず以下のいずれかを付与し、状況が変化した場合はそれに合わせて変更すること。
* **wait for review**: レビュー待ち。
* **review complete**: レビュー済み。
* **WIP**: 作業途中。

### 作業の流れ
1. 作業するIssueを決定し、自分をAssigneesに紐付け、タグを **doing** に変更する。
2. developmentブランチからブランチを作成する。その際、ブランチ名は `<Issue番号>-<簡単なIssueの説明>` とする。
3. 内容に応じコミットを行う。上記のコミットメッセージのルールに従うこと。また、コミットの直前に自動フォーマット（Mac: `⌘⌥L`, Windows: `Ctrl+Alt+L`）を適応する。
4. originにpushし、Pull Requestを作成する。その際、Pull Requestのコメント欄の最初に `close #<Issue番号>` と記述すること。 **wait for review** のタグを設定し、Reviewersを指定する。
5. 指定された人がレビューを行い、問題がなければ最後にレビューを行った者がマージする。
